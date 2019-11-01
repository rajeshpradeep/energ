package com.miniproject.energ.di.module;

import android.app.Application;

import com.miniproject.energ.data.dao.UserDao;
import com.miniproject.energ.data.database.LocalDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Rajesh Pradeep G on 25-10-2019
 */
@Module
public class RoomModule {

    @Provides
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    /*
     * The method returns the Database object
     * */
    @Provides
    @Singleton
    LocalDatabase provideLocalDatabase(Application application) {
        LocalDatabase localDatabase;
        return localDatabase = Room.databaseBuilder(application,
                LocalDatabase.class, "energ-db")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }

    /**
     * We need the UserDao Module
     * */
    @Provides
    @Singleton
    UserDao provideEvidenceDao(LocalDatabase localDatabase) {
        return localDatabase.userDao();
    }
}
