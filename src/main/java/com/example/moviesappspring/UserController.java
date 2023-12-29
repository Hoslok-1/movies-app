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

    @PostMapping(value = "/login")
    public ResponseEntity<String> loginUser(@RequestBody User user){
        try {

            if (!userService.isEmailAlreadyInUse(user.getEmail())) {
                return ResponseEntity.badRequest().body("User not found");
            }

            User existingUser = userRepository.findByEmail(user.getEmail());

            if (!existingUser.getPassword().equals(user.getPassword())) {
                return ResponseEntity.badRequest().body("Incorrect password");
            }

            return ResponseEntity.ok("Login successful");
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("internal server error");
        }
    }
}
