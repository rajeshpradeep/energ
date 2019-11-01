package com.miniproject.energ.ui.login.fragment;


import android.content.Context;
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

import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miniproject.energ.R;
import com.miniproject.energ.data.dao.UserDao;
import com.miniproject.energ.ui.base.BaseFragment;
import com.miniproject.energ.utils.Constant;
import com.miniproject.energ.utils.SharedPreferencesUtil;
import com.miniproject.energ.utils.Utils;

import java.util.concurrent.Executor;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotPasswordFragment extends BaseFragment {

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

    @BindView(R.id.fp_base_lay)
    LinearLayout fp_base_lay;
    @BindView(R.id.title_lbl)
    TextView title_lbl;
    @BindView(R.id.forgot_password_lbl)
    TextView forgot_password_lbl;
    @BindView(R.id.userEmail_layout)
    LinearLayout userEmail_layout;
    @BindView(R.id.email_etext)
    EditText email_etext;
    @BindView(R.id.forgot_password_btn)
    EditText forgot_password_btn;
    @BindView(R.id.have_acc_lay)
    LinearLayout have_acc_lay;
    @BindView(R.id.have_acc_tview)
    TextView have_acc_tview;

    public ForgotPasswordFragment() {
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
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
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
        Utils.setLightFonts(mContext, new TextView[]{forgot_password_lbl});
        Utils.setBoldFonts(mContext, new TextView[]{title_lbl, forgot_password_btn});
        Utils.setRegularFonts(mContext, new TextView[]{email_etext, have_acc_tview});
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
    @OnClick({R.id.forgot_password_btn, R.id.fp_base_lay})
    void buttonAction(View view) {
        switch (view.getId()) {
            case R.id.ca_base_lay:
                hideKeyboardFrom(mContext, fp_base_lay);
                break;
            case R.id.forgot_password_btn:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
