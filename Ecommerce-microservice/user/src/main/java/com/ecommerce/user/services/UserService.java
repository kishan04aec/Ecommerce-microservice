package com.ecommerce.user.services;

import lombok.RequiredArgsConstructor;
import com.ecommerce.user.dto.AddressDto;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.model.Address;
import com.ecommerce.user.model.User;
import com.ecommerce.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;



    public List<UserResponse> fetchAllUsers()
  {
      List<User> userList = userRepository.findAll();
      return userRepository.findAll().stream()
              .map(this::mapToUserresponse)
              .collect(Collectors.toList());
  }

    public void addUser(UserRequest userRequest)
    {
       User user = new User();
       updateUserfromRequest(user,userRequest);
        userRepository.save(user);

    }



    public Optional<UserResponse> fetchUser(String id)
    {
        return userRepository.findById(id)
                .map(this::mapToUserresponse);


    }
    public boolean updateUser(@PathVariable String id, UserRequest updatedUser)
    {

        return userRepository.findById(id)
                .map(user -> {updateUserfromRequest(user,updatedUser);
                userRepository.save(user);
                return true;
                }).orElse(false);

    }

    private void updateUserfromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        if(userRequest.getAddress() != null) {
            Address address = new Address();
            address.setCity(userRequest.getAddress().getCity());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setState(userRequest.getAddress().getState());
            address.setStreet(userRequest.getAddress().getStreet());
            address.setZip(userRequest.getAddress().getZip());
            user.setAddress(address);
        }
    }


    private UserResponse mapToUserresponse(User user)
    {
        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setLastName(user.getLastName());
        response.setFirstName(user.getFirstName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        if(user.getAddress() != null) {
            AddressDto addressDto = new AddressDto();
            addressDto.setCity(user.getAddress().getCity());
            addressDto.setCountry(user.getAddress().getCountry());
            addressDto.setState(user.getAddress().getState());
            addressDto.setStreet(user.getAddress().getStreet());
            addressDto.setZip(user.getAddress().getZip());
            response.setAddress(addressDto);
        }
        return response;
    }
}
