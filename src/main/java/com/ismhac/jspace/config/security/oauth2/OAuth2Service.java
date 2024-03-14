package com.ismhac.jspace.config.security.oauth2;

import com.ismhac.jspace.exception.BadRequestException;
import com.ismhac.jspace.exception.ErrorCode;
import com.ismhac.jspace.model.OAuth2Info;
import com.ismhac.jspace.model.User;
import com.ismhac.jspace.model.primaryKey.OAuth2InfoId;
import com.ismhac.jspace.repository.OAuth2InfoRepository;
import com.ismhac.jspace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

@RequiredArgsConstructor
public class OAuth2Service extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final OAuth2InfoRepository oAuth2InfoRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        return super.loadUser(userRequest);
    }

//    private OAuth2User checking(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
//        CustomOAuth2User customOAuth2User = new CustomOAuth2User();
//        String email = customOAuth2User.getAttributes("email");
//    }
}
