package com.ismhac.jspace.config.security.userPrincipal;

import com.ismhac.jspace.exception.NotFoundException;
import com.ismhac.jspace.model.BaseUser;
import com.ismhac.jspace.repository.BaseUserRepository;
import com.ismhac.jspace.util.response.Status;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailServiceImpl implements CustomUserDetailService{

    private final BaseUserRepository baseUserRepository;
    @Override
    public UserDetails loadUserByEmail(String email) {
        UserPrincipal userPrincipal = (UserPrincipal) baseUserRepository.findBaseUserByEmail(email)
                .orElseThrow(()-> new NotFoundException(Status.BASE_USER_NOT_FOUND_EMAIL));

        return userPrincipal;
    }
}
