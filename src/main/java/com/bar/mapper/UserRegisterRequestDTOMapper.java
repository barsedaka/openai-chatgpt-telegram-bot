package com.bar.mapper;

import com.bar.dto.UserRegisterRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class UserRegisterRequestDTOMapper {

    public UserRegisterRequestDTO mapToDTO(String username, String password) {
        return new UserRegisterRequestDTO(username, password);
    }
}
