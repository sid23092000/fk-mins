package com.customer.experience.service.impl;

import com.customer.experience.dto.UserDetailsDto;
import com.customer.experience.model.Users;
import com.customer.experience.repository.UserRepository;
import com.customer.experience.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public void createUser(UserDetailsDto userDetailsDto) throws Exception {
        if (userDetailsDto == null) {
            throw new Exception("User details not received in the request");
        }

        Users users = new Users();
        users.setName(userDetailsDto.getName());
        users.setEmail(userDetailsDto.getEmail());
        users.setPhone(userDetailsDto.getPhone());
        users.setAddress(userDetailsDto.getAddress());

        userRepository.save(users);
    }
}
