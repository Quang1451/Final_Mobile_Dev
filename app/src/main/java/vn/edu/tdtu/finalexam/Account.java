package vn.edu.tdtu.finalexam;

import java.io.Serializable;

public class Account implements Serializable {
    String fullName, code, phoneNumber, password;

    public Account() {}

    public Account(String fullName, String code, String phoneNumber, String password) {
        this.fullName = fullName;
        this.code = code;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getCode() {
        return code;
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
