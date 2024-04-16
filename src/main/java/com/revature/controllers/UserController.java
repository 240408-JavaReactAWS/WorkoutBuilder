package com.revature.controllers;

import com.revature.exceptions.InvalidCredentialsException;
import com.revature.exceptions.NoSuchUserException;
import com.revature.exceptions.UsernameAlreadyTakenException;
import com.revature.models.User;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService us;

    @Autowired
    public UserController(UserService us) {
        this.us = us;
    }

    @PostMapping("register")
    public ResponseEntity<User> registerNewUserHandler(@RequestBody User user){
        User savedUser;

        try{
            savedUser = us.createUser(user.getUsername(), user.getPassword());
        } catch (UsernameAlreadyTakenException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<User> loginNewUserHandler(@RequestBody User user){
        User loggedInUser;

        try{
            loggedInUser = us.logInUser(user.getUsername(), user.getPassword());
        } catch (InvalidCredentialsException | NoSuchUserException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
    }
}
