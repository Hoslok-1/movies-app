package com.example.moviesappspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/users")

public class UserController {
    @Autowired
    public UserService userService;
    @Autowired
    public UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<List<User>>(userService.allUsers(), HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<String> addUser(@RequestBody User user){
        if(userService.isEmailAlreadyInUse(user.getEmail())){
            return ResponseEntity.badRequest().body("Email is already in use");
        }
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
}
