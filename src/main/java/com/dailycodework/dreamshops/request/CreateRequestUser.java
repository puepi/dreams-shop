package com.dailycodework.dreamshops.request;

import lombok.Data;

@Data
public class CreateRequestUser {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
