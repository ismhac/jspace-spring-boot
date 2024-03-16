package com.ismhac.jspace.config.security.oauth2;

import com.ismhac.jspace.dto.auth.AuthenticationResponse;
import com.ismhac.jspace.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuth2Service {

//    private final UserRepository userRepository;
//    private final OAuth2InfoRepository oAuth2InfoRepository;
//    private final RoleRepository roleRepository;

    @NonFinal
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.scope}")
    private String scope;

    @Transactional(rollbackFor = Exception.class)
    public AuthenticationResponse<Object> loginWithOauth2(OAuth2User oAuth2User, String roljjj) {
//        try {
//            /* case 1: User has entered email but does not have oauth2 information */
//            Optional<User> getUserByEmail = userRepository.findUserByEmail(oAuth2User.getAttribute("email"));
//            if(getUserByEmail.isPresent()){
//                OAuth2InfoId oAuth2InfoId = OAuth2InfoId.builder()
//                        .user(getUserByEmail.get())
//                        .build();
//                OAuth2Info oAuth2Info = oAuth2InfoRepository.findById(oAuth2InfoId).orElseGet(()->{
//                    OAuth2Info newOAuth2Info = OAuth2Info.builder()
//                            .id(oAuth2InfoId)
//                            .email(oAuth2User.getAttribute("email"))
//                            .name(oAuth2User.getAttribute("name"))
//                            .picture(oAuth2User.getAttribute("picture"))
//                            .build();
//                    return oAuth2InfoRepository.save(newOAuth2Info);
//                });
//
//                UserDetails userDetails = getUserByEmail.get();
//                String accessToken = tokenService.generateAccessToken(userDetails);
//                String refreshToken = tokenService.generateRefreshToken(userDetails);
//
//                log.info(String.format("User %s login success", userDetails.getUsername()));
//
//                return LoginResponse.<Map<String, Object>>builder()
//                        .accessToken(accessToken)
//                        .refreshToken(refreshToken)
////                    .information(information)
//                        .build();
//            } else {
//                Role role = roleRepository.findRoleByCode(RoleCode.CANDIDATE).get();
//                User user = User.builder()
//                        .role(role)
//                        .username(oAuth2User.getAttribute("email"))
//                        .email(oAuth2User.getAttribute("email"))
//                        .build();
//
//                User savedUser = userRepository.save(user);
//
//                OAuth2InfoId oAuth2InfoId = OAuth2InfoId.builder()
//                        .user(savedUser)
//                        .build();
//
//                OAuth2Info oAuth2Info = oAuth2InfoRepository.findById(oAuth2InfoId).orElseGet(()-> {
//                    OAuth2Info newOAuth2Info = OAuth2Info.builder()
//                            .id(oAuth2InfoId)
//                            .email(oAuth2User.getAttribute("email"))
//                            .name(oAuth2User.getAttribute("name"))
//                            .picture(oAuth2User.getAttribute("picture"))
//                            .build();
//                    return oAuth2InfoRepository.save(newOAuth2Info);
//                });
//
//                UserDetails userDetails = savedUser;
//                String accessToken = tokenService.generateAccessToken(userDetails);
//                String refreshToken = tokenService.generateRefreshToken(userDetails);
//
//                log.info(String.format("User %s login success", userDetails.getUsername()));
//
//                return LoginResponse.<Map<String, Object>>builder()
//                        .accessToken(accessToken)
//                        .refreshToken(refreshToken)
////                    .information(information)
//                        .build();
//            }
//        }catch (BadRequestException exception){
//            throw new BadRequestException(ErrorCode.WRONG_USERNAME_OR_PASSWORD);
//        }
        return null;
    }

    public String createGoogleLoginUrl() {
        String redirectUri = "http://localhost:8081/jspace-service/login/oauth2/code/google";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://accounts.google.com/o/oauth2/v2/auth/oauthchooseaccount")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .queryParam("scope", scope)
                .queryParam("state", SecurityUtils.generateNewState())
                .queryParam("nonce", SecurityUtils.generateNewNonce());
        return builder.toUriString();
    }
}
