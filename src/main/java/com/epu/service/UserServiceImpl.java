package com.epu.service;

import com.epu.config.JwtProvider;
import com.epu.exeptions.UserException;
import com.epu.models.User;
import com.epu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(User user) {
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setPassword(user.getPassword());
        newUser.setGender(user.getGender());
        newUser.setId(user.getId());

        User savedUser = userRepository.save(newUser);
        return savedUser;
    }

    @Override
    public User findUserById(Integer userId) throws UserException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            return user.get();
        }
        throw new UserException("Không tồn tại người dùng có userId là " + userId);
    }

    @Override
    public User findByUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public User followUser(Integer reqUserId, Integer userId2) throws UserException {
        User reqUser = findUserById(reqUserId);
        User user2 = findUserById(userId2);

        user2.getFollowers().add(reqUser.getId());
        reqUser.getFollowings().add(user2.getId());

        userRepository.save(reqUser);
        userRepository.save(user2);

        return reqUser;
    }

    @Override
    public User updateUser(User user, Integer userId) throws UserException {
        Optional<User> user1 = userRepository.findById(userId);

        if (user1.isEmpty()){
            throw new UserException("Không tồn tại người dùng có id là " + userId);
        }

        User oldUser = user1.get();

        if (user.getFirstName() != null){
            oldUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null){
            oldUser.setLastName(user.getLastName());
        }
        if (user.getEmail() != null){
            oldUser.setEmail(user.getEmail());
        }
        if (user.getGender() != null){
            oldUser.setGender(user.getGender());
        }

        User updateUser = userRepository.save(oldUser);

        return updateUser;
    }

    @Override
    public List<User> searchUser(String query) {
        return userRepository.searchUser(query);
    }

    @Override
    public User findUserByJwt(String jwt) {
        String email = JwtProvider.getEmailFromJwtToken(jwt);
        User user = userRepository.findByEmail(email);
        return user;
    }
}
