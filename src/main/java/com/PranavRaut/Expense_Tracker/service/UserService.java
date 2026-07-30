package com.PranavRaut.Expense_Tracker.service;

import com.PranavRaut.Expense_Tracker.entity.User;
import com.PranavRaut.Expense_Tracker.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    public User getByusername(String username){
        return userRepository.findByUserName(username);
    }

    public User editUser(ObjectId id , User Newuser){
        User OldUser = userRepository.findById(id).orElse(null);
        if (OldUser != null){
            OldUser.setUsername(Newuser.getUsername() != null && !Newuser.getUsername().isBlank() ? Newuser.getUsername() : OldUser.getUsername());
            OldUser.setPassword(Newuser.getPassword() != null && !Newuser.getPassword().isBlank() ? Newuser.getPassword() : OldUser.getPassword());
            OldUser.setEmail(Newuser.getEmail() != null && !Newuser.getEmail().isBlank() ? Newuser.getEmail() : OldUser.getEmail());

            userRepository.save(OldUser);
            return OldUser;
        }

        return null;
    }

    public boolean deleteUser (String username){
        User byUserName = userRepository.findByUserName(username);
        if(byUserName != null){
            userRepository.delete(byUserName);
            return true;
        }
        else {
            return false;
        }
    }

}
