package com.PranavRaut.Expense_Tracker.Controller;


import com.PranavRaut.Expense_Tracker.entity.User;
import com.PranavRaut.Expense_Tracker.security.CustomUserDetailsService;
import com.PranavRaut.Expense_Tracker.service.UserService;
import com.PranavRaut.Expense_Tracker.util.JWTutil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class publicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailService;

    @Autowired
    private JWTutil jwTutil;


    @GetMapping("/health-check")
    public String healthCheck(){
        return "All Good";
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createuser (@RequestBody User user){
        try {

            userService.saveNewUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginuser (@RequestBody User user){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername() , user.getPassword()));
            UserDetails userDetails = customUserDetailService.loadUserByUsername(user.getUsername());
            String jwt = jwTutil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error("Exception occurred while createAuthToken ");
            return new ResponseEntity<>("Incorrect Username or Password",HttpStatus.BAD_REQUEST);
        }

    }
}
