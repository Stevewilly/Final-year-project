package com.example.drake.stamploadproject.Class;

/**
 * Created by drake on 4/22/2017.
 */

public class ClientRegistrationDetails {
    public int client_id;
    public String client_username;
    public String client_password;
    public String client_email;
    public String client_dob;
    public String client_phone;
    public ClientRegistrationDetails(){

    }
    public ClientRegistrationDetails(String username, String password, String email, String Dob,String phone){
        this.client_username = username;
        this.client_email = email;
        this.client_password = password;
        this.client_dob = Dob;
        this.client_phone = phone;
    }

    public ClientRegistrationDetails(int id, String username, String password, String email, String Dob, String phone){
        this.client_id = id;
        this.client_username = username;
        this.client_email = email;
        this.client_password = password;
        this.client_dob = Dob;
        this.client_phone = phone;
    }
    public int getClient_id(){
        return this.client_id;
    }
    public String getClient_username(){return this.client_username;}
    public String getClient_password(){
        return this.client_password;
    }
    public String getClient_email(){return this.client_email;}
    public String getClient_dob(){return this.client_dob;}

    public String getClient_phone() {
        return client_phone;
    }

    public void setClient_phone(String client_phone) {
        this.client_phone = client_phone;
    }

    public void setClient_id(int client_id){
        this.client_id = client_id;
    }

    public void setClient_username(String client_username) {this.client_username = client_username;}
    public void setClient_email(String client_email) {
        this.client_username = client_email;
    }
    public void setClient_password(String client_password){this.client_password = client_password;}
    public void setClient_dob(String client_dob){
        this.client_dob = client_dob;
    }
}
