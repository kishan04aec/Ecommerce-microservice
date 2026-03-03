package com.ecommerce.user.controllers;

import lombok.RequiredArgsConstructor;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers()
    {
        return new ResponseEntity<>(userService.fetchAllUsers(),HttpStatus.OK);

    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id){
        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(()-> (ResponseEntity.notFound().build()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody UserRequest updatedUser)
    {
          boolean updated = userService.updateUser(id,updatedUser);
        if(updated)
        {
          return ResponseEntity.ok("user updated successfuly");
        }
        else
        {
          return ResponseEntity.notFound().build();
        }
    }


    @PostMapping()
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest)
    {

        userService.addUser(userRequest);
      return ResponseEntity.ok("User created successfully");
    }
}
