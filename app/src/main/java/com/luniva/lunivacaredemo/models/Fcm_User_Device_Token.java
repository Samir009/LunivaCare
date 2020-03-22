package com.luniva.lunivacaredemo.models;

public class Fcm_User_Device_Token {

    private String id;
    private String token;

    public Fcm_User_Device_Token() {
    }

    public Fcm_User_Device_Token(String id, String token) {
        this.id = id;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
