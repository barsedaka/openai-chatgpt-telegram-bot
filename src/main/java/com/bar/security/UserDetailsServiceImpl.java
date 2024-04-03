package com.bar.security;

import com.bar.entity.AppUserEntity;
import com.bar.repository.AppUserEntityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserEntityRepository appUserEntityRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUserEntity> user = appUserEntityRepository.findUserEntityByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Can't find user by username: " + username);
        }

        return new User(user.get().getUsername(), user.get().getPassword(), new ArrayList<>());
    }
}
