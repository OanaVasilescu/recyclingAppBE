package recycle.greenlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recycle.greenlife.model.User;
import recycle.greenlife.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id){
        return userService.getUserById(id);
    }

    @PostMapping("/users/login")
    public ResponseEntity<User> verifyUserLogin(@RequestBody User user){ return userService.verifyUserLogin(user);}

    @PostMapping("/users")
    public ResponseEntity<String> addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User user){
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/users")
    public ResponseEntity<String> deleteAllUsers(){
        return userService.deleteAllUsers();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id){
        return userService.deleteUser(id);
    }
}
