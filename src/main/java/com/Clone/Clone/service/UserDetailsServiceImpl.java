package com.Clone.Clone.service;

import com.Clone.Clone.model.User;
import com.Clone.Clone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User>userOptional=userRepository.findByUsername(username);
        User user =userOptional.orElseThrow(()->new UsernameNotFoundException("No user "+
                "Found with userName"+ username));
        return  new org.springframework.security.
                core.userdetails.User(user.getUsername(),user.getPassword(),user.isEnable(),true,true,
                true,getAuthorities("USER"));
    }
    private Collection<?  extends GrantedAuthority> getAuthorities(String role){
        return  Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}
