package com.base.base.service.security;

import com.base.base.dao.request.SignUpRequest;
import com.base.base.dao.request.SigninRequest;
import com.base.base.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request) throws Exception;

    JwtAuthenticationResponse signin(SigninRequest request);
}
