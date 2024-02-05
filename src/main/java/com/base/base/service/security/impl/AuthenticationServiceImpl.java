package com.base.base.service.security.impl;

import com.base.base.dao.request.SignUpRequest;
import com.base.base.dao.request.SigninRequest;
import com.base.base.dao.response.JwtAuthenticationResponse;
import com.base.base.entity.User;
import com.base.base.repository.UserRepository;
import com.base.base.enumaration.Role;
import com.base.base.service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.base.base.service.security.AuthenticationService;

import static com.base.base.utils.VerifyPassword.passwordValidator;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl  implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) throws Exception {
        try {
            passwordValidator(request.getPassword());
            var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                    .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER).build();
            userRepository.save(user);
            var jwt = jwtService.generateToken(user);
            return JwtAuthenticationResponse.builder().token(jwt).build();
        } catch (Exception e) {
            throw new Exception("Erro: " + e.getMessage());
        }
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

}
