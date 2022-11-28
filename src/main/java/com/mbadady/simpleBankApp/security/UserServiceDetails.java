package com.mbadady.simpleBankApp.security;

import com.mbadady.simpleBankApp.dao.UserRepository;
import com.mbadady.simpleBankApp.model.Role;
import com.mbadady.simpleBankApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.SecondaryTable;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceDetails implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceDetails(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String emailOrPhoneNumber) throws UsernameNotFoundException {
        User user = userRepository.findByEmailIdOrPhoneNumber(emailOrPhoneNumber, emailOrPhoneNumber).orElseThrow(()->
            new UsernameNotFoundException("User not found with email or phone number "+ emailOrPhoneNumber)
        );
        return new org.springframework.security.core.userdetails.User(user.getEmailId(), user.getPassword(),
                mapToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapToAuthorities(Set<Role> roles){
      return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
