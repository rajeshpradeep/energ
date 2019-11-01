package com.miniproject.energ.data.database;

import com.miniproject.energ.data.dao.UserDao;
import com.miniproject.energ.data.model.UserModel;
import com.miniproject.energ.utils.Converters;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


/**
 * Created by Rajesh Pradeep G on 14-10-2019
 */
@Database(entities = {UserModel.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class LocalDatabase extends RoomDatabase {

    public abstract UserDao userDao();

}
