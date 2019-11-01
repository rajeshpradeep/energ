package com.miniproject.energ.di.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Rajesh Pradeep G on 25-10-2019
 */
@Module(includes = {RoomModule.class, SharedPreferenceModule.class})
public class ApplicationModule {
    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Application providesApplication() {
        return application;
    }
}
