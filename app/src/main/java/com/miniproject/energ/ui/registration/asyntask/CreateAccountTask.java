package com.miniproject.energ.ui.registration.asyntask;

import android.content.Context;
import android.content.SharedPreferences;

import com.miniproject.energ.data.model.UserModel;
import com.miniproject.energ.ui.registration.listener.CreateAccountListener;

import javax.inject.Inject;

/**
 * Created by Rajesh Pradeep G on 24-10-2019
 */
public class CreateAccountTask {

    private String TAG = getClass().getSimpleName();
    @Inject
    SharedPreferences sharedPreferences;

    UserModel userModel;
    Context mContext;
    CreateAccountListener createAccountListener;

    public CreateAccountTask(Context mContext, UserModel userModel, CreateAccountListener createAccountListener) {
        this.userModel = userModel;
        this.mContext = mContext;
        this.createAccountListener = createAccountListener;
        responseTask();
    }

    private void responseTask() {
        createAccountListener.accountCreated("Success");
    }
}
