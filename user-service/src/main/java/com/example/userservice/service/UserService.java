package com.example.userservice.service;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO getUser(String id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null)
            return new UserDTO(user);
        else
            return null;

    }


    public List<UserDTO> getAllUsers() {

        List<User> users = userRepository.findAll();
        List<UserDTO> usersDTO = new ArrayList<>();

        for (User user : users) {
            usersDTO.add(new UserDTO(user));
        }

        return usersDTO;
    }

    public List<UserDTO> getAllPublicProfiles(){
        List<User> users = userRepository.findAll();
        List<UserDTO> usersDTO = new ArrayList<>();

        for(User user : users){
            if(user.isPublic)
                usersDTO.add(new UserDTO(user));
        }

        return usersDTO;
    }

    public UserDTO addUser(UserDTO newUserDTO) {

        if (!usernameExists(newUserDTO.username)) {
            User newUser = new User(newUserDTO);
            userRepository.save(newUser);
            return new UserDTO(newUser);
        } else
            return null;

    }

    private boolean usernameExists(String username) {
        List<User> users = userRepository.findAll();
        boolean usernameExists = false;

        for (User user : users)
            if (user.username.equals(username)) {
                usernameExists = true;
                break;
            }
        return usernameExists;
    }


    public boolean updateUser(UserDTO updateUserDTO) {
        boolean status = userRepository.existsById(updateUserDTO.id);

        if (status) {
            User userToUpdate = userRepository.findById(updateUserDTO.id).orElse(null);
            assert userToUpdate != null;
            userToUpdate.firstName = updateUserDTO.firstName;
            userToUpdate.lastName = updateUserDTO.lastName;
            userToUpdate.username = updateUserDTO.username;
            userToUpdate.password = updateUserDTO.password;
            userToUpdate.address = updateUserDTO.address;
            userToUpdate.email = updateUserDTO.email;
            userToUpdate.dateOfBirth = updateUserDTO.dateOfBirth;
            userToUpdate.phoneNumber = updateUserDTO.phoneNumber;
            userToUpdate.gender = updateUserDTO.gender;
            userToUpdate.isPublic = updateUserDTO.isPublic;

            userToUpdate.profile = updateUserDTO.profile;

            userRepository.save(userToUpdate);
        }

        return status;

    }

    public boolean deleteUser(String id) {
        boolean status = userRepository.existsById(id);
        if (status)
            userRepository.deleteById(id);

        return status;

    }

}
