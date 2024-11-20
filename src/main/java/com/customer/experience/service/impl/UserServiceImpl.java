package com.customer.experience.service.impl;

import com.customer.experience.dto.UserDetailsDto;
import com.customer.experience.model.User;
import com.customer.experience.repository.UserRepository;
import com.customer.experience.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public void createUser(UserDetailsDto userDetailsDto) {
        User user = new User();
        user.setName(userDetailsDto.getName());
        user.setEmail(userDetailsDto.getEmail());
        user.setPhone(userDetailsDto.getPhone());
        user.setAddress(userDetailsDto.getAddress());

        userRepository.save(user);
    }
}
