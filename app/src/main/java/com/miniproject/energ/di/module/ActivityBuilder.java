package com.miniproject.energ.di.module;

import com.miniproject.energ.ui.login.LoginActivity;
import com.miniproject.energ.ui.product.ProductActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Rajesh Pradeep G on 25-10-2019
 */
@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector
    abstract ProductActivity bindProducActivity();
}
