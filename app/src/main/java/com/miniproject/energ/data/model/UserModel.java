package com.miniproject.energ.data.model;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Created by Rajesh Pradeep G on 21-10-2019
 */
@Entity(indices = {@Index(value = {"emailId"}, unique = true)})
public class UserModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int userId;
    private String emailId;
    private String userName;
    private String password;
    private String mobile;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
