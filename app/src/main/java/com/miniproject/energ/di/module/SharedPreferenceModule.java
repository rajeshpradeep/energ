package com.miniproject.energ.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.miniproject.energ.utils.Constant;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Rajesh Pradeep G on 25-10-2019
 */
@Module
public class SharedPreferenceModule {
    //Provide SharedPreference for activity or fragment
    @Singleton
    @Provides
    public SharedPreferences provideSharedPreferences(Application application) {
        return application.getSharedPreferences(Constant.SESSION_NAME,
                Context.MODE_PRIVATE);
    }
}
