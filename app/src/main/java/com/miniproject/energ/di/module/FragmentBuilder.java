package com.miniproject.energ.di.module;

import com.miniproject.energ.ui.login.fragment.ForgotPasswordFragment;
import com.miniproject.energ.ui.product.fragment.ContactUsFragment;
import com.miniproject.energ.ui.product.fragment.ProductDescriptionFragment;
import com.miniproject.energ.ui.product.fragment.ProfileFragment;
import com.miniproject.energ.ui.registration.CreateAccountFragment;
import com.miniproject.energ.ui.login.fragment.LoginFragment;
import com.miniproject.energ.ui.product.fragment.ProductFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Rajesh Pradeep G on 25-10-2019
 */
@Module
public abstract class FragmentBuilder {

    @ContributesAndroidInjector
    abstract LoginFragment contributeLoginFragment();

    @ContributesAndroidInjector
    abstract CreateAccountFragment contributeCreateAccountFragment();

    @ContributesAndroidInjector
    abstract ProductFragment contributeProductFragment();

    @ContributesAndroidInjector
    abstract ForgotPasswordFragment contributeForgotPasswordFragment();

    @ContributesAndroidInjector
    abstract ProductDescriptionFragment contributeProductDescriptionFragment();

    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();

    @ContributesAndroidInjector
    abstract ContactUsFragment contributeContactUsFragment();

}
