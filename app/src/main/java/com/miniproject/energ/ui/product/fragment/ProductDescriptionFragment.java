package com.miniproject.energ.ui.product.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.util.Util;
import com.miniproject.energ.R;
import com.miniproject.energ.data.dao.UserDao;
import com.miniproject.energ.data.model.ProductModel;
import com.miniproject.energ.ui.base.BaseFragment;
import com.miniproject.energ.ui.product.adapter.ViewPagerAdapter;
import com.miniproject.energ.utils.Utils;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDescriptionFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    private String TAG = getClass().getSimpleName();
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    UserDao userDao;
    @Inject
    Executor executor;
    private Unbinder unbinder;
    private Context mContext;
    private FragmentManager fragmentManager;
    Bundle bundle;
    ProductModel productModel;
    ArrayList<Integer> productImages;
    private int dotscount;
    private ImageView[] dots;

    @BindView(R.id.viewpager_llay)
    LinearLayout viewpager_llay;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.sliderDots)
    LinearLayout sliderDots;
    @BindView(R.id.product_name_lay)
    LinearLayout product_name_lay;
    @BindView(R.id.product_name_lbl)
    TextView product_name_lbl;
    @BindView(R.id.product_name_val)
    TextView product_name_val;

    @BindView(R.id.product_desc_lay)
    LinearLayout product_desc_lay;
    @BindView(R.id.product_desc_lbl)
    TextView product_desc_lbl;
    @BindView(R.id.product_desc_val)
    TextView product_desc_val;

    @BindView(R.id.product_dosage_lay)
    LinearLayout product_dosage_lay;
    @BindView(R.id.product_dosage_lbl)
    TextView product_dosage_lbl;
    @BindView(R.id.product_dosage_val)
    TextView product_dosage_val;

    @BindView(R.id.product_rc_lay)
    LinearLayout product_rc_lay;
    @BindView(R.id.product_rc_lbl)
    TextView product_rc_lbl;
    @BindView(R.id.product_rc_val)
    TextView product_rc_val;

    @BindView(R.id.product_comp_lay)
    LinearLayout product_comp_lay;
    @BindView(R.id.product_comp_lbl)
    TextView product_comp_lbl;
    @BindView(R.id.product_comp_val)
    TextView product_comp_val;

    @BindView(R.id.product_mrp_lay)
    LinearLayout product_mrp_lay;
    @BindView(R.id.product_mrp_lbl)
    TextView product_mrp_lbl;
    @BindView(R.id.product_mrp_val)
    TextView product_mrp_val;

    @BindView(R.id.contact_us_btn)
    Button contact_us_btn;

    /**
     * configure the dagger
     * */
    private void configureDagger() {
        AndroidSupportInjection.inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureDagger();
        setHasOptionsMenu(true);
        bundle = getArguments();
        productImages = new ArrayList<>();
//        productModel = new ProductModel();
        productModel = (ProductModel) bundle.getSerializable("product_detail");
        productImages = bundle.getIntegerArrayList("product_img");
    }

    public ProductDescriptionFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_description, container, false);
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
        initViewpager();


        product_name_val.setText(productModel.getProductName());
        product_desc_val.setText(productModel.getProductDescription());
        product_dosage_val.setText(productModel.getProductDosage());
        product_rc_val.setText(productModel.getProductRecommendedCrops());
        product_comp_val.setText(productModel.getProductCompositions());
        product_mrp_val.setText(productModel.getProductMRP());


    }

    /*
    * set font
    * */
    private void setFont() {
        Utils.setBoldFonts(mContext, new TextView[] {product_name_val, product_desc_val, product_dosage_val, product_rc_val,
                product_comp_val, product_mrp_val, contact_us_btn});
        Utils.setRegularFonts(mContext, new TextView[] {product_name_lbl, product_desc_lbl, product_dosage_lbl, product_rc_lbl,
                product_comp_lbl, product_mrp_lbl});
    }

    private void initViewpager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(mContext, productImages);
        viewpager.setAdapter(viewPagerAdapter);
        viewpager.setOnPageChangeListener(ProductDescriptionFragment.this);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(mContext);
            dots[i].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDots.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.active_dot));

    }

    /**
     * implementing the click action
     */
    @OnClick({R.id.contact_us_btn})
    void buttonAction(View view) {
        if (view.getId() == R.id.contact_us_btn) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "8754242121"));// Initiates the Intent
            startActivity(intent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for(int i = 0; i< dotscount; i++){
            dots[i].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.non_active_dot));
        }
        dots[position].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.active_dot));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
