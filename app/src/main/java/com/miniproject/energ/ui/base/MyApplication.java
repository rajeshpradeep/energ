package com.miniproject.energ.ui.base;

import android.app.Activity;
import android.app.Application;

import com.miniproject.energ.di.component.ApplicationComponent;
import com.miniproject.energ.di.component.DaggerApplicationComponent;
import com.miniproject.energ.di.module.ApplicationModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by Rajesh Pradeep G on 22-10-2019
 */
public class MyApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationComponent appComponent = DaggerApplicationComponent.builder()
                .application(this)
                .applicationModule(new ApplicationModule(this))
                .build();

        appComponent.inject(this);
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

}
