package com.example.demo.ServicesImplementations;

import com.example.demo.Entities.User;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("userService")
public class UserServiceImpl implements UserService  {
    @Autowired
    UserRepository userRepo;
    @Override
    public User findUserById(long id) {
        return userRepo.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id: %1d is not found!", id)));
    }
}
