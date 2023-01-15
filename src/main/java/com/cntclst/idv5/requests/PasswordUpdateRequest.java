package com.cntclst.idv5.requests;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class PasswordUpdateRequest {

    private Integer id;
    private String userName;
    private String password;

    public Integer getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
