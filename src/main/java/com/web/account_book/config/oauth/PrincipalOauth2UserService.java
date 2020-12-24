package com.web.account_book.config.oauth;

import com.web.account_book.config.auth.PrincipalDetails;
import com.web.account_book.config.oauth.provider.FacebookUserInfo;
import com.web.account_book.config.oauth.provider.GoogleUserInfo;
import com.web.account_book.config.oauth.provider.NaverUserInfo;
import com.web.account_book.config.oauth.provider.OAuth2UserInfo;
import com.web.account_book.model.User;
import com.web.account_book.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.Map;


@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest){
//        System.out.println("userRequest:"+userRequest);
//        System.out.println("getClientRegistration:"+userRequest.getClientRegistration());
//        System.out.println("getAccessToken:"+userRequest.getAccessToken());
//
//        System.out.println("loadUser:"+super.loadUser((userRequest)).getAttributes());

        OAuth2User oAuth2User = super.loadUser(userRequest);
//        System.out.println("getAttributes"+oAuth2User.getAttributes());


        OAuth2UserInfo auth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            System.out.println("구글 로그인");
            auth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")){
            System.out.println("페이스북 로그인");
            auth2UserInfo = new FacebookUserInfo(oAuth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            System.out.println("네이버 로그인");
            auth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes());
        }else{
            System.out.println("구글, 페이스북, 네이버만 로그인 가능");
        }

        String provide = auth2UserInfo.getProvider();
        String providerId = auth2UserInfo.getProviderId();
        String username = auth2UserInfo.getProvider()+"_"+auth2UserInfo.getProviderId();
        String password = bCryptPasswordEncoder.encode("겟인데어");
        String email = auth2UserInfo.getEmail();
        String role = "ROLE_USER";

        User userEntity = userRepository.findByUsername(username);
        if(userEntity == null){
            System.out.println("로그인이 최초입니다. 자동 회원가입을 진행합니다.");
            userEntity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provide(provide)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        }else{
            System.out.println("로그인을 이미 하셨습니다.");
        }
        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
}
