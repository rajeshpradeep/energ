package com.miniproject.energ.di.component;

import com.miniproject.energ.di.module.ActivityBuilder;
import com.miniproject.energ.di.module.ApplicationModule;
import com.miniproject.energ.di.module.FragmentBuilder;
import com.miniproject.energ.ui.base.MyApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by Rajesh Pradeep G on 25-10-2019
 * We mark this interface with the @Component annotation.
 * And we define all the modules that can be injected.
 * Note that we provide AndroidSupportInjectionModule.class
 * here. This class was not created by us.
 * It is an internal class in Dagger 2.
 * Provides our activities and fragments with given module.
 *
 */
@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, ApplicationModule.class, ActivityBuilder.class,
        FragmentBuilder.class})
public interface ApplicationComponent {
    /* We will call this builder interface from our custom Application class.
     * This will set our application object to the AppComponent.
     * So inside the AppComponent the application instance is available.
     * So this application instance can be accessed by our modules
     * */
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(MyApplication application);

        Builder applicationModule(ApplicationModule appModule);

        ApplicationComponent build();
    }

    void inject(MyApplication application);
}
