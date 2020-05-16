package com.example.mymess;

public class User {
    String user_id;
    String email_id;
    String first_name;
    String last_name;

    public User() {}

    public User(String id, String email, String f_name, String l_name) {
        this.user_id = id;
        this.email_id = email;
        this.first_name = f_name;
        this.last_name = l_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getEmail_id() {
        return email_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }
}
