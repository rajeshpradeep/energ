package com.miniproject.energ.ui.login.asyntask;

import android.content.Context;
import android.content.SharedPreferences;

import com.miniproject.energ.data.model.UserModel;

import javax.inject.Inject;

/**
 * Created by Rajesh Pradeep G on 29-10-2019
 */
public class LoginTask {
    private String TAG = getClass().getSimpleName();
    @Inject
    SharedPreferences sharedPreferences;

    UserModel userModel;
    Context mContext;

}
