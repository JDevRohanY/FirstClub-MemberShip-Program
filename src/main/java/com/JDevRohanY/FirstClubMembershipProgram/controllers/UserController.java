package com.JDevRohanY.FirstClubMembershipProgram.controllers;

import com.JDevRohanY.FirstClubMembershipProgram.dtos.CreateUserRequestDto;
import com.JDevRohanY.FirstClubMembershipProgram.dtos.CreateUserResponseDto;
import com.JDevRohanY.FirstClubMembershipProgram.models.User;
import com.JDevRohanY.FirstClubMembershipProgram.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<CreateUserResponseDto> createUser(@RequestBody CreateUserRequestDto requestDto) {
        User user = userService.createUser(requestDto.getName(), requestDto.getEmail());

        CreateUserResponseDto responseDto = new CreateUserResponseDto();
        responseDto.setId(user.getId());
        responseDto.setName(user.getName());
        responseDto.setEmail(user.getEmail());
        responseDto.setMembershipStatus(user.getMembershipStatus().toString());
        responseDto.setCreatedAt(user.getCreatedAt());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
