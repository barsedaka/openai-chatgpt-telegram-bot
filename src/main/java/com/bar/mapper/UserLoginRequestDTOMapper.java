package com.bar.mapper;

import com.bar.dto.UserLoginRequestDTO;
import com.bar.dto.UserRegisterRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class UserLoginRequestDTOMapper {

    public UserLoginRequestDTO mapToDTO(String username, String password) {
        return new UserLoginRequestDTO(username, password);
    }
}
