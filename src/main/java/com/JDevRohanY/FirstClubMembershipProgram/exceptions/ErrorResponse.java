package com.JDevRohanY.FirstClubMembershipProgram.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    String error;
    int code;
}