package com.miniproject.energ.data.dao;

import com.miniproject.energ.data.model.UserModel;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Created by Rajesh Pradeep G on 23-10-2019
 */
@Dao
public interface UserDao {

    @Query("SELECT * FROM UserModel")
    List<UserModel> getAllUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createUser(UserModel userModel);

    @Query("SELECT * FROM USERMODEL WHERE emailId == :emailId AND password == :password" )
    UserModel getUser(String emailId, String password);

    @Query("SELECT * FROM USERMODEL WHERE emailId == :emailId" )
    UserModel checkExistingUser(String emailId);

    @Query("UPDATE UserModel SET password= :password WHERE emailId = :emailId")
    void updateUserPassword(String password, String emailId);

    @Query("DELETE FROM UserModel")
    void deleteAllUser();

}
