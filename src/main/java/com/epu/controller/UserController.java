package com.epu.controller;

import com.epu.exeptions.UserException;
import com.epu.models.User;
import com.epu.repository.UserRepository;
import com.epu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public User createUser(@RequestBody User user){

        User savedUser = userService.registerUser(user);

        return savedUser;
    }

    @GetMapping("/api/users")
    public List<User> getUsers(){
        List<User> users = userRepository.findAll();
        return users;
    }

    @GetMapping("/api/users/{userId}")
    public User getUserById(@PathVariable("userId") Integer id) throws UserException {
        User user = userService.findUserById(id);
        return user;
    }

    @PutMapping("/api/users")
    public User updateUser(@RequestHeader("Authorization") String jwt, @RequestBody User user) throws UserException {
        User reqUser = userService.findUserByJwt(jwt);
        User updatedUser = userService.updateUser(user, reqUser.getId());
        return updatedUser;
    }

//    @DeleteMapping("users/{userId}")
//    public String deleteUser(@PathVariable("userId") Integer userId) throws Exception {
//        Optional<User> user = userRepository.findById(userId);
//        if (user.isEmpty()){
//            throw new Exception("Không tồn tại người dùng có id là " + userId);
//        }
//        userRepository.delete(user.get());
//        return "Xóa thành công người dùng có id " + userId;
//    }

    @PutMapping("/api/users/follow/{userId2}")
    public User followUserHandler(@RequestHeader("Authorization") String jwt, @PathVariable Integer userId2) throws UserException {
        User reqUser = userService.findUserByJwt(jwt);
        User user = userService.followUser(reqUser.getId(), userId2);
        return user;
    }

    @GetMapping("/api/users/search")
    public List<User> searchUser(@RequestParam("query") String query){
        List<User> users = userService.searchUser(query);
        return users;
    }

    @GetMapping("/api/users/profile")
    public User getUserFromToken(@RequestHeader("Authorization") String jwt) {
        User user = userService.findUserByJwt(jwt);
        user.setPassword(null);
        return user;
    }

}
