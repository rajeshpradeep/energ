package com.miniproject.energ.ui.product.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class ProfileFragment extends BaseFragment {

    private String TAG = getClass().getSimpleName();
    @Inject
    SharedPreferencesUtil sharedPreferencesUtil;
    /*@Inject
    UserDao userDao;
    @Inject
    Executor executor;*/
    private Unbinder unbinder;
    private Context mContext;
    FragmentManager fragmentManager;

    @BindView(R.id.personal_info_lbl)
    TextView personal_info_lbl;
    @BindView(R.id.username_lbl)
    TextView username_lbl;
    @BindView(R.id.username_val)
    TextView username_val;
    @BindView(R.id.email_lbl)
    TextView email_lbl;
    @BindView(R.id.email_val)
    TextView email_val;
    @BindView(R.id.mobile_lbl)
    TextView mobile_lbl;
    @BindView(R.id.mobile_val)
    TextView mobile_val;

    public ProfileFragment() {
        // Required empty public constructor
    }

    private void configureDagger() {
        AndroidSupportInjection.inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureDagger();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
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
        setStatusBar();
        setFont();

        if(!TextUtils.isEmpty(sharedPreferencesUtil.getUserEmail(Constant.USER_EMail)) &&
                !TextUtils.isEmpty(sharedPreferencesUtil.getUserName(Constant.USER_NAME))) {
            username_val.setText(sharedPreferencesUtil.getUserName(Constant.USER_NAME));
            email_val.setText(sharedPreferencesUtil.getUserEmail(Constant.USER_EMail));
            mobile_val.setText(sharedPreferencesUtil.getUsermobile(Constant.USER_MOBILE));
        }
    }

    /**
     * set the font family
     */
    private void setFont() {
        Utils.setBoldFonts(mContext, new TextView[]{personal_info_lbl, mobile_val, email_val, username_val});
        Utils.setRegularFonts(mContext, new TextView[]{email_lbl, username_lbl, mobile_lbl});
    }

    /**
     * load the Fragment
     */
    private void setFragment(Fragment fragment) {
        //For initially add stack for backpress event
        fragmentManager.popBackStack(Constant.BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fragment_right_in, R.anim.fragment_left_out, R.anim.fragment_left_in, R.anim.fragment_right_out);
        fragmentTransaction
                .replace(R.id.product_frame, fragment)
                .commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
