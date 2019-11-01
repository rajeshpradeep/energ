package com.miniproject.energ.ui.login;

import android.content.Intent;
import android.os.Bundle;

import com.miniproject.energ.R;
import com.miniproject.energ.ui.base.BaseActivity;
import com.miniproject.energ.ui.login.fragment.LoginFragment;
import com.miniproject.energ.ui.product.ProductActivity;
import com.miniproject.energ.ui.registration.CreateAccountFragment;
import com.miniproject.energ.utils.Constant;
import com.miniproject.energ.utils.SharedPreferencesUtil;
import com.miniproject.energ.utils.Utils;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class LoginActivity extends BaseActivity implements HasSupportFragmentInjector {

    private String TAG = getClass().getSimpleName();
    FragmentManager fragmentManager;

    /*For Fragment */
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    @Inject
    SharedPreferencesUtil sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureDagger();
        if(sharedPreferences.getUserEmail(Constant.USER_EMail) != null) {
            Intent intent = new Intent(this, ProductActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_login);
            fragmentManager = getSupportFragmentManager();
            initUI();
        }
    }

    /**
     * Configure the Dagger
     * */
    public void configureDagger() {
        AndroidInjection.inject(this);
    }

    /**
     * initialize the UI
     * */
    private void initUI() {
//        removeStatusBar();
        setFragment(new LoginFragment());
    }

    /**
     * load the landing fragment
     * */
    public void setFragment(Fragment fragment) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
        //For initially add stack for backpress event
        fragmentManager.popBackStack(Constant.BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager
                .beginTransaction()
                .add(R.id.login_frame, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.login_frame);
        if (fragmentManager.getBackStackEntryCount() > 1)
            fragmentManager.popBackStackImmediate();
        else if(currentFragment instanceof CreateAccountFragment)
            setFragment(new LoginFragment());
        else
            Utils.showCustomAlert(this, getString(R.string.alert), "Are you sure to exit?", "app_close");
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

}
