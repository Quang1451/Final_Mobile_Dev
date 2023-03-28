package vn.edu.tdtu.finalexam;

import java.io.Serializable;

public class Account implements Serializable {
    String fullName, phoneNumber, password;
    public Account() {}

    public Account(String fullName, String phoneNumber, String password) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }
}
