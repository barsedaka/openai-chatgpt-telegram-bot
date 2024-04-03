package com.bar.security;

import com.bar.dto.UserLoginRequestDTO;
import com.bar.dto.UserLoginResponseDTO;
import com.bar.dto.UserRegisterRequestDTO;
import com.bar.dto.UserResponseDTO;
import com.bar.entity.AppUserEntity;
import com.bar.repository.AppUserEntityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthService {

    private final AppUserEntityRepository appUserEntityRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Transactional
    public UserResponseDTO register(UserRegisterRequestDTO request) throws Exception {
        if (appUserEntityRepository.existsByUsername(request.getUsername())) {
            throw new Exception("Username is taken!");
        }

        AppUserEntity user = new AppUserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        appUserEntityRepository.save(user);

        log.info("user saved successfully!");

        return UserResponseDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    @Transactional
    public UserLoginResponseDTO authenticate(UserLoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("User signed in successfully!");

        String token = jwtUtil.generateToken(authentication);

        return new UserLoginResponseDTO(token);
    }
}
