package com.JDevRohanY.FirstClubMembershipProgram.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends BaseModel{
    String name;
    String email;
    MembershipStatus membershipStatus;
}
