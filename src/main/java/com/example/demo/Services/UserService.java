package com.example.demo.Services;

import com.example.demo.Entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
//    List<User> userList();
    User findUserById(long id) ;
//    void saveUser(User user);
}
