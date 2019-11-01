package com.miniproject.energ.ui.registration;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

import android.os.Handler;
import android.os.Looper;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miniproject.energ.R;
import com.miniproject.energ.data.dao.UserDao;
import com.miniproject.energ.data.model.UserModel;
import com.miniproject.energ.ui.base.BaseFragment;
import com.miniproject.energ.ui.login.fragment.LoginFragment;
import com.miniproject.energ.ui.registration.listener.CreateAccountListener;
import com.miniproject.energ.ui.product.ProductActivity;
import com.miniproject.energ.ui.registration.asyntask.CreateAccountTask;
import com.miniproject.energ.utils.Constant;
import com.miniproject.energ.utils.SharedPreferencesUtil;
import com.miniproject.energ.utils.Utils;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateAccountFragment extends BaseFragment {

    private String TAG = getClass().getSimpleName();
    @Inject
    SharedPreferencesUtil sharedPreferencesUtil;
    @Inject
    UserDao userDao;
    @Inject
    Executor executor;
    private Unbinder unbinder;
    private Context mContext;
    FragmentManager fragmentManager;

    @BindView(R.id.ca_base_lay)
    LinearLayout ca_base_lay;
    @BindView(R.id.title_lbl)
    TextView title_lbl;
    @BindView(R.id.ca_lbl)
    TextView ca_lbl;
    @BindView(R.id.username_layout)
    LinearLayout username_layout;
    @BindView(R.id.userName_etext)
    EditText userName_etext;
    @BindView(R.id.userEmail_layout)
    LinearLayout userEmail_layout;
    @BindView(R.id.email_etext)
    EditText email_etext;
    @BindView(R.id.userMobile_layout)
    LinearLayout userMobile_layout;
    @BindView(R.id.mobile_etext)
    EditText mobile_etext;
    @BindView(R.id.password_layout)
    LinearLayout password_layout;
    @BindView(R.id.password_etext)
    EditText password_etext;
    @BindView(R.id.cpassword_layout)
    LinearLayout cpassword_layout;
    @BindView(R.id.cpassword_etext)
    EditText cpassword_etext;
    @BindView(R.id.create_acc_btn)
    Button create_acc_btn;
    @BindView(R.id.have_acc_lay)
    LinearLayout have_acc_lay;
    @BindView(R.id.have_acc_tview)
    TextView have_acc_tview;

    public CreateAccountFragment() {
        // Required empty public constructor
    }

    private void configureDagger() {
        AndroidSupportInjection.inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureDagger();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_account, container, false);
        unbinder = ButterKnife.bind(this, view);
        mContext = getContext();
        fragmentManager = getFragmentManager();
        initUi();
        return view;
    }

    /**
     * initialize the UI
     */
    private void initUi() {
        removeStatusBar();
        setFont();
        Utils.usPhoneCode(mobile_etext);
        cpassword_etext.setImeOptions(EditorInfo.IME_ACTION_DONE);
        cpassword_etext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if ((keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) || (i == EditorInfo.IME_ACTION_DONE)) {
                    create_acc_btn.performClick();
                    return true;
                }
                return false;
            }
        });

        SpannableString dont_acc_span = new SpannableString("Already have an account? Login");
        dont_acc_span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.hint_color)), 0, 25, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        dont_acc_span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 25, dont_acc_span.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ClickableSpan create_account_clickable_span = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                setFragment(new LoginFragment());
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        dont_acc_span.setSpan(create_account_clickable_span, 25, dont_acc_span.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        have_acc_tview.setText(dont_acc_span);
        have_acc_tview.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * set the font family
     */
    private void setFont() {
        Utils.setLightFonts(mContext, new TextView[]{ca_lbl});
        Utils.setBoldFonts(mContext, new TextView[]{title_lbl, create_acc_btn});
        Utils.setRegularFonts(mContext, new TextView[]{email_etext, password_etext, cpassword_etext,
                userName_etext, have_acc_tview, mobile_etext});
    }

    /**
     * load the Login Fragment
     */
    private void setFragment(Fragment fragment) {
        //For initially add stack for backpress event
        fragmentManager.popBackStack(Constant.BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fragment_right_in, R.anim.fragment_left_out, R.anim.fragment_left_in, R.anim.fragment_right_out);
        fragmentTransaction
                .replace(R.id.login_frame, fragment)
                .commit();
    }

    /**
     * implementing the click action
     */
    @OnClick({R.id.create_acc_btn, R.id.ca_base_lay})
    void buttonAction(View view) {
        switch (view.getId()) {
            case R.id.ca_base_lay:
                hideKeyboardFrom(mContext, ca_base_lay);
                break;
            case R.id.create_acc_btn:
                String userName = userName_etext.getText().toString();
                String password = password_etext.getText().toString();
                String cpassword = cpassword_etext.getText().toString();
                String emailId = email_etext.getText().toString();
                String mobile = mobile_etext.getText().toString();
                if (!TextUtils.isEmpty(userName)) {
                    if (!TextUtils.isEmpty(emailId)) {
                        if (Utils.isValidEmail(emailId)) {
                            if (!TextUtils.isEmpty(mobile) && mobile.length() == 14) {
                                if (!TextUtils.isEmpty(password) || !TextUtils.isEmpty(cpassword)) {
                                    if (password.equals(cpassword)) {
                                        UserModel userModel = new UserModel();
                                        userModel.setEmailId(emailId);
                                        userModel.setUserName(userName);
                                        userModel.setPassword(password);
                                        userModel.setMobile(mobile);

                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.i(TAG, "run: " + userName);
                                                if (checkExistUser(emailId, userDao.getAllUser())) {
                                                    Utils.showCustomAlert(mContext, getString(R.string.alert),
                                                            "User already exist!", "invalid_user");
                                                    email_etext.setText("");
                                                    password_etext.setText("");
                                                    cpassword_etext.setText("");
                                                    mobile_etext.setText("");
                                                    email_etext.requestFocus();
                                                } else {
                                                    userDao.createUser(userModel);
                                                    sharedPreferencesUtil.saveUserEmail(Constant.USER_EMail, emailId);
                                                    sharedPreferencesUtil.saveUserName(Constant.USER_NAME, userName);
                                                    sharedPreferencesUtil.saveUserMobile(Constant.USER_MOBILE, mobile);
                                                    Intent intent = new Intent(mContext, ProductActivity.class);
                                                    startActivity(intent);
                                                    getActivity().finish();
                                                }
                                            }
                                        });

                                    } else
                                        Utils.showCustomAlert(mContext, "Alert", "Incorrect Password. Please try again!", "password_mismatch");
                                } else toastMessage("Enter password.");
                            } else
                                Utils.setError(mobile_etext, "Invalid Mobile number.");
                        } else
                            Utils.setError(email_etext, "Invalid EmailID.");
                    } else
                        Utils.setError(email_etext, "Enter EmailID.");
                } else
                    Utils.setError(userName_etext, "Enter Username.");

        }
    }

    private boolean checkExistUser(String emailId, List<UserModel> userModelList) {
        for (int i = 0; i < userModelList.size(); i++) {
            if (userModelList.get(i).getEmailId().equals(emailId))
                return true;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
