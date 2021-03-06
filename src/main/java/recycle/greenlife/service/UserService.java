package recycle.greenlife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.data.mongodb.core.mapping.Document;
import recycle.greenlife.model.User;
import recycle.greenlife.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = new ArrayList<User>();
            userRepository.findAll().forEach(users::add);
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("Error while getting all users:" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<User> getUserById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            System.out.println("No user found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<User> verifyUserLogin(User givenUser) {
        Optional<User> user;
        String username = givenUser.getUsername();
        String password = givenUser.getPassword();
        String email = givenUser.getEmail();
        if (password == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        if (username == null) {
            if (email == null)
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            user = Optional.ofNullable(userRepository.findByEmail(email));
        } else {
            user = Optional.ofNullable(userRepository.findByUsername(username));
        }
        if (user.isPresent()) {
            User foundUser = user.get();
            String savedPassword = foundUser.getPassword();
            if (savedPassword.equals(givenUser.getPassword()))
                return new ResponseEntity<>(user.get(), HttpStatus.OK);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            System.out.println("No user found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> addUser(User user) {
        try {
            //TODO verify if user already exists
            User savedUser = userRepository.save(new User(user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName()));
            return new ResponseEntity<>("User saved successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("The user could not be added. Error:" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<User> updateUser(UUID id, User userData) {
        //TODO: verify if new data is already taken
        Optional<User> oldUserData = userRepository.findById(id);
        if (oldUserData.isPresent()) {
            User updatedUser = oldUserData.get();
            updatedUser.setUsername(userData.getUsername());
            updatedUser.setEmail(userData.getEmail());
            updatedUser.setFirstName(userData.getFirstName());
            updatedUser.setLastName(userData.getLastName());
            updatedUser.setPassword(userData.getPassword());
            return new ResponseEntity<>(userRepository.save(updatedUser), HttpStatus.OK);
        } else {
            System.out.println("No such user found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> deleteAllUsers() {
        try {
            userRepository.deleteAll();
            return new ResponseEntity<>("Users successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Users could not be deleted. Error: " + e.getMessage());
            return new ResponseEntity<>("Users could not be deleted. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteUser(UUID id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>("User " + id + " successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("User " + id + " could not be deleted. Error: " + e.getMessage());
            return new ResponseEntity<>("User " + id + " could not be deleted. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
