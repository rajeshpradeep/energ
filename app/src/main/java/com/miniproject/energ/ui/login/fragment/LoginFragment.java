package com.miniproject.energ.ui.login.fragment;


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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miniproject.energ.R;
import com.miniproject.energ.data.dao.UserDao;
import com.miniproject.energ.data.model.UserModel;
import com.miniproject.energ.ui.base.BaseFragment;
import com.miniproject.energ.ui.product.ProductActivity;
import com.miniproject.energ.ui.registration.CreateAccountFragment;
import com.miniproject.energ.utils.Constant;
import com.miniproject.energ.utils.SharedPreferencesUtil;
import com.miniproject.energ.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment {

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
    UserModel userModel;

    @BindView(R.id.title_lbl)
    TextView title_lbl;
    @BindView(R.id.login_lbl)
    TextView login_lbl;
    @BindView(R.id.login_base_lay)
    LinearLayout login_base_lay;
    @BindView(R.id.username_layout)
    LinearLayout username_layout;
    @BindView(R.id.email_icon)
    ImageView email_icon;
    @BindView(R.id.email_etext)
    EditText email_etext;
    @BindView(R.id.password_layout)
    LinearLayout password_layout;
    @BindView(R.id.password_icon)
    ImageView password_icon;
    @BindView(R.id.password_etext)
    EditText password_etext;
    @BindView(R.id.forgot_password_tview)
    TextView forgot_password_tview;
    @BindView(R.id.login_btn)
    Button login_btn;
    @BindView(R.id.dont_have_acc_tview)
    TextView dont_have_acc_tview;

    public LoginFragment() {
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
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
        email_etext.requestFocus();
        showSoftKeyboard(mContext, email_etext);
        password_etext.setImeOptions(EditorInfo.IME_ACTION_DONE);
        password_etext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if ((keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) || (i == EditorInfo.IME_ACTION_DONE)) {
                    login_btn.performClick();
                    return true;
                }
                return false;
            }
        });

        SpannableString dont_acc_span = new SpannableString("Don't have account? Create Account");
        dont_acc_span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.hint_color)), 0, 18, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        dont_acc_span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 20, dont_acc_span.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ClickableSpan create_account_clickable_span = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                setFragment(new CreateAccountFragment());
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        dont_acc_span.setSpan(create_account_clickable_span, 20, dont_acc_span.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        dont_have_acc_tview.setText(dont_acc_span);
        dont_have_acc_tview.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * set the font family
     */
    void setFont() {
        Utils.setLightFonts(mContext, new TextView[]{login_lbl});
        Utils.setBoldFonts(mContext, new TextView[]{title_lbl, login_btn});
        Utils.setRegularFonts(mContext, new TextView[]{email_etext, password_etext, forgot_password_tview, dont_have_acc_tview});
    }

    public void setFragment(Fragment fragment) {
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        //For initially add stack for backpress event
//        fragmentManager.popBackStack(Constant.BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(Constant.BACK_STACK_ROOT_TAG);
        fragmentTransaction.setCustomAnimations(R.anim.fragment_right_in, R.anim.fragment_left_out, R.anim.fragment_left_in, R.anim.fragment_right_out);
        fragmentTransaction
                .replace(R.id.login_frame, fragment)
                .commit();
    }

    /**
     * implementing the click action
     */
    @OnClick({R.id.login_btn, R.id.forgot_password_tview, R.id.login_base_lay})
    void buttonAction(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                String password = password_etext.getText().toString();
                String emailId = email_etext.getText().toString();
                    if (!TextUtils.isEmpty(emailId)) {
                        if (Utils.isValidEmail(emailId)) {
                            if (!TextUtils.isEmpty(password)) {

                                new  Handler().postAtTime(() -> userModel = userDao.getUser(emailId, password), 200);

                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.i(TAG, "run: " + emailId);
                                        if(userModel != null) {
                                            sharedPreferencesUtil.saveUserEmail(Constant.USER_EMail, userModel.getEmailId());
                                            sharedPreferencesUtil.saveUserName(Constant.USER_NAME, userModel.getUserName());
                                            sharedPreferencesUtil.saveUserMobile(Constant.USER_MOBILE, userModel.getMobile());
                                            Intent intent = new Intent(mContext, ProductActivity.class);
                                            startActivity(intent);
                                            getActivity().finish();
                                        } else {
                                            Utils.showCustomAlert(mContext, getString(R.string.alert),
                                                    "Incorrect Username / Password. Please try again",
                                                    "invalid_login");
                                            password_etext.setText("");
                                            email_etext.setFocusable(true);
                                        }
                                    }
                                });
                            } else
                                Utils.setError(email_etext, "Enter password.");
                        } else
                            Utils.setError(email_etext, "Invalid EmailID.");
                    } else
                        Utils.setError(email_etext, "Enter EmailID.");
                break;
            case R.id.forgot_password_tview:
                toastMessage("Login");
                break;
            case R.id.login_base_lay:
                hideKeyboardFrom(mContext, login_base_lay);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
