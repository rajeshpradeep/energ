package com.miniproject.energ.ui.product.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
public class ContactUsFragment extends BaseFragment {

    private String TAG = getClass().getSimpleName();
    /*@Inject
    SharedPreferencesUtil sharedPreferencesUtil;
    @Inject
    UserDao userDao;
    @Inject
    Executor executor;*/
    private Unbinder unbinder;
    private Context mContext;
    FragmentManager fragmentManager;

    @BindView(R.id.contact_info_lbl)
    TextView contact_info_lbl;
    @BindView(R.id.name_lbl)
    TextView name_lbl;
    @BindView(R.id.name_val)
    TextView name_val;
    @BindView(R.id.addr_lbl)
    TextView addr_lbl;
    @BindView(R.id.addr_val)
    TextView addr_val;
    @BindView(R.id.mobile_lbl)
    TextView mobile_lbl;
    @BindView(R.id.mobile_val)
    TextView mobile_val;

    public ContactUsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
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
        String name = "R.Ananda Kumar";
        String address = "R.K. Chemicals, \n1A - Krishna complex, \nYercaud main road, \nChinnakollapatty,\nSalem - 636008.";
        String phone = "(875) 424-2121";

        name_val.setText(name);
        addr_val.setText(address);
        mobile_val.setText(phone);
    }

    /**
     * set the font family
     */
    private void setFont() {
        Utils.setBoldFonts(mContext, new TextView[]{contact_info_lbl, mobile_val, addr_val, name_val});
        Utils.setRegularFonts(mContext, new TextView[]{addr_lbl, name_lbl, mobile_lbl});
    }

    /**
     * implementing the click action
     */
    @OnClick({R.id.mobile_val})
    void buttonAction(View view) {
        if (view.getId() == R.id.mobile_val) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "8754242121"));// Initiates the Intent
            startActivity(intent);
        }
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
