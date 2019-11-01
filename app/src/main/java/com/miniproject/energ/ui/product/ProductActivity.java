package com.miniproject.energ.ui.product;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.miniproject.energ.R;
import com.miniproject.energ.ui.base.BaseActivity;
import com.miniproject.energ.ui.login.LoginActivity;
import com.miniproject.energ.ui.product.fragment.ContactUsFragment;
import com.miniproject.energ.ui.product.fragment.ProductDescriptionFragment;
import com.miniproject.energ.ui.product.fragment.ProductFragment;
import com.miniproject.energ.ui.product.fragment.ProfileFragment;
import com.miniproject.energ.utils.Constant;
import com.miniproject.energ.utils.SharedPreferencesUtil;
import com.miniproject.energ.utils.Utils;

import javax.inject.Inject;

public class ProductActivity extends BaseActivity implements HasSupportFragmentInjector {

    private String TAG = getClass().getSimpleName();
    FragmentManager fragmentManager;

    /*For Fragment */
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    SharedPreferencesUtil sharedPreferencesUtil;

    public void configureDagger() {
        AndroidInjection.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureDagger();
        setContentView(R.layout.activity_product);
        fragmentManager = getSupportFragmentManager();
        initUI();
    }

    /**
     * initialize the UI
     * */
    private void initUI() {
        setStatusBar();
        setFragment(new ProductFragment());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    /**
     * load the landing fragment*/
    public void setFragment(Fragment fragment) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
        //For initially add stack for backpress event
        fragmentManager.popBackStack(Constant.BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager
                .beginTransaction()
                .add(R.id.product_frame, fragment)
                .commit();
    }

    /**
     * set Other fragment
     * */
    public void setOtherFragment(Fragment fragment) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
        //For initially add stack for backpress event
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(Constant.BACK_STACK_ROOT_TAG);
        fragmentTransaction.setCustomAnimations(R.anim.fragment_right_in, R.anim.fragment_left_out, R.anim.fragment_left_in, R.anim.fragment_right_out);
        fragmentTransaction
                .replace(R.id.product_frame, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.product_frame);
        if (fragmentManager.getBackStackEntryCount() > 1)
            fragmentManager.popBackStackImmediate();
        else if(currentFragment instanceof ProfileFragment)
            setFragment(new ProductFragment());
        else if(currentFragment instanceof ContactUsFragment)
            setFragment(new ProductFragment());
        else if(currentFragment instanceof ProductDescriptionFragment)
            setFragment(new ProductFragment());
        else
            Utils.showCustomAlert(this, getString(R.string.alert), "Are you sure to exit?", "app_close");
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.profile_menu:
                setOtherFragment(new ProfileFragment());
                return true;
            case R.id.contact_us:
                setOtherFragment(new ContactUsFragment());
                return true;
            case R.id.logout:
                sharedPreferencesUtil.deleteAllSession();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
