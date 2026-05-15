package com.JDevRohanY.FirstClubMembershipProgram.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreateUserResponseDto {
    String id;
    String name;
    String email;
    String membershipStatus;
    Date createdAt;
}
