package com.PranavRaut.Expense_Tracker.security;

import com.PranavRaut.Expense_Tracker.entity.User;
import com.PranavRaut.Expense_Tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (user != null) {
            String[] roles = (user.getRoles() != null && !user.getRoles().isEmpty())
                    ? user.getRoles().toArray(new String[0])
                    : new String[]{"USER"}; // Default fallback role

            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    .authorities("USER")
                    .build();
        }

        throw new UsernameNotFoundException("User not found ");
    }
}


