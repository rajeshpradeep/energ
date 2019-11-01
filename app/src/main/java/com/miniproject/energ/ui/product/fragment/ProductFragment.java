package com.miniproject.energ.ui.product.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.miniproject.energ.R;
import com.miniproject.energ.data.dao.UserDao;
import com.miniproject.energ.data.model.ProductModel;
import com.miniproject.energ.ui.base.BaseFragment;
import com.miniproject.energ.ui.product.adapter.ProductAdapter;
import com.miniproject.energ.ui.product.listener.ProductListener;
import com.miniproject.energ.utils.Constant;
import com.miniproject.energ.utils.Utils;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import static androidx.recyclerview.widget.LinearLayoutManager.VERTICAL;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends BaseFragment implements ProductListener {

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
    private ProductAdapter productAdapter;
    private ProductModel productModel;
    ArrayList<ProductModel> productList;
    ArrayList<Integer> productImgList;

    @BindView(R.id.product_rview)
    RecyclerView product_rview;

    @BindView(R.id.product_lbl)
    TextView product_lbl;
    @BindView(R.id.grid_img)
    ImageView grid_img;
    @BindView(R.id.list_img)
    ImageView list_img;

    public ProductFragment() {
        // Required empty public constructor
    }

    /**
     * configure the dagger
     */
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
        View view = inflater.inflate(R.layout.fragment_product, container, false);
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

        productImgList = new ArrayList<>();
        productImgList.add(R.drawable.energ);
        productImgList.add(R.drawable.fresh);
        productImgList.add(R.drawable.microhume);
        productImgList.add(R.drawable.akshaya);
        productImgList.add(R.drawable.flowerplus);
        productImgList.add(R.drawable.nutrigain);
        productImgList.add(R.drawable.shark);
        productImgList.add(R.drawable.red);
        productImgList.add(R.drawable.top);
        productImgList.add(R.drawable.bravo);
        productImgList.add(R.drawable.progro);
        productImgList.add(R.drawable.cur_off);
        productImgList.add(R.drawable.super_pottasium);

        productList = new ArrayList<>();
        productList.add(new ProductModel("Ener-G", "Improves crop yield by root growth and improves uptake and translocation of micro and macro nutrients including early maturity.",
                "1 to 2 kg per Acre", "All Crops", "Humic Acid 90% Fulvic Acid and Natural Minerals",
                "Rs.200/kg"));
        productList.add(new ProductModel("Fresh", "Increase plants shoot, root, stem Elongates. Increase the Microbial activity in the soil. Improve the soil aeration, porosity and water holding capacity. Improve the root hair formation thereby increase the intake nutrients from the soil. Balance the soil PH as well as acidic or alkaline. Increase the photosynthesis and Chlorophyll formation. Increase flowering and reduces products. Increase yield up to 20%.",
                "4kg of Fresh with minimum 10 times of irrigation water per acre for short term crop. \n8kg of Fresh with minimum 10 times of irrigation water per acre for long term crop.",
                "All Crops", "Macro, Secondary and Micro Nutrients, Amino, Sea Extract, Vitamins, Proteins & Minerals, Natural plant growth Hormones, Enzymes, Humic, Carbohydrates.",
                "Rs.70/kg"));
        productList.add(new ProductModel("Microhume", "Microhume improves crop yield by root growth and improving uptake and translocation of micro and macro nutrients including early maturity.",
                "Mix 3 to 5 ml of Microhume 1 liter of water and spray.", "All Crops.",
                "Humic Acid, Amino Acid, Fulvic Acid and minerals", "Rs.160/liter"));
        productList.add(new ProductModel("Akshaya", "Creates more flowering and helps to more yield.",
                "Mix 2 to 3 ml in liter of water and spray before flowering period once in 20 days.",
                "Flowers, Chilies, Paddy, Cotton, Mango & Vegetables.",
                "Nitro Benzene-20%, Solvent-80%", "Rs.150/liter"));
        productList.add(new ProductModel("Flower Plus", "Flower Plus creates more flowering and helps to more yield.",
                "Mix 2 to 3 ml in liter of water and spray before flowering period once in 20 days.",
                "Flowers, Chilies, Paddy, Cotton, Mango  & Vegetables, Flowering Stimulation, Growth Stimulation", "Protein Hydrolyzed, Vitamins", "Rs.150/liter"));
        productList.add(new ProductModel("Nutrigain", "Nutrigain is a natural combination of Micro Nutrients, helps to increase the intake of dissolved nutrients, which reduces the fertilizer runoff. Nutrigain increase the chlorophyll formation, photosynthesis process, it helps to prevent the nutrients heat stress and deficiency. Nutrigain thus improve the immune system of the plants. Nutrigain increase the quality and the quantity of the yields products .Nutrigain can be mixed with all fertilizers for use on all crops.",
                "2.5 ml per liter of water", "All Crops",
                "Boron, Copper, Iron, Manganese, Zinc, Molybdenum", "210/per liter"));
        productList.add(new ProductModel("Shark", "Shark is an advanced bio technology research product containing highly specialized bio components and effective biological product improves the resistance for all pests and maximizing crop yield potential.",
                "100 ml per acre", "All Crops", "", "2100/liter"));
        productList.add(new ProductModel("Red", "Red a product from a mix of natural plant alkaloids extracted from various Indian and western herbs based on organic solvent. The Combinational effect of alkaloids induces effective resistance against target pest.", "1 ml per liter of water",
                "Tomato, Onion, Chilies, Brinjal, Bhendi, Cucurbits, Mango, Pome, Grapes, Jasmine, Jerbera, Rose, Camation",
                "justica adhatoda -25%, Jatropa-25%, Adjuvants-10%, Q.S-40%", "1400/-per liter"));
        productList.add(new ProductModel("Top", "Top advanced is an innovative advanced combination of organic constituent’s source from plants and naturally occurring substances. Effective in buildup of resistance against fruit, school berer DBM and other leaf feeding insects.",
                "0.5-1 ml per liter of water. Follar application", "Brinjal, Cabbage, Vegetables, Flowers and All crops",
                "Allicin-5.0%w/v, Soap of Fatty acid-95%w/v", "8000/-per liter"));
        productList.add(new ProductModel("Bravo", "Bravo advanced is an innovative advanced combination of organic constituent’s source from plants and naturally occurring substances. Effective in buildup of resistance against fruit, school berer DBM and other leaf feeding insects.",
                "0.5-1 ml per liter of water. Follar application", "Brinjal, Cabbage, Vegetables, Flowers and All crops",
                "Allicin-5.0%w/v, Soap of Fatty acid-95%w/v", "8000/-per liter"));
        productList.add(new ProductModel("Progro", "Progro is readily absorbed by the roots and foliage of treated plants, vines, trees and pastures.",
                "Apply 3 ml/Lt Water to fruit and nut trees, vegetables, strawberries, ginger, tomatoes, melons, passion fruit, grapes, cotton and mints.\nApply 2.5 ml/Lt Water to seedlings, herbs, flowers, nurseries, indoor plants, palms, pineapples, bananas and sugarcane.\nApply 2 liters per acre for drenching for all crops.",
                "All crops", "Polysaccharides-50%, Organic solids-52%, O.S-28%", "250/-per liter"));
        productList.add(new ProductModel("Cut off", "An innovative advanced combination of organic constituent's source from plants and naturally occurring substances. Effective in buildup of resistance against fruit,shoot borer DBM and other leaf feeding insects.",
                "15 ml in 15 of L water", "Brinjal, Cabbage, Vegetables, Flowers and For all crops.",
                "Allicin-50% and Soap of fatty acid-95%w/v", "8500/-per liter"));
        productList.add(new ProductModel("Super Potassium f humate", "Improves crop yield by root growth and improves uptake and translocation of micro and macro nutrients including early maturity.",
                "1 to 2 kg per Acre", "All crops",
                "Humic acid 90%, Fulvic acid and Natural minerals", "200/kg"));

        /*initiate with Grid view*/
        setProductGridRecyclerViewAdapter(productImgList, productList);
    }

    /**
     * set font
     */
    private void setFont() {
        Utils.setBoldFonts(mContext, new TextView[]{product_lbl});
    }

    /**
     * implementing the click action
     */
    @OnClick({R.id.list_img, R.id.grid_img})
    void buttonAction(View view) {
        switch (view.getId()) {
            case R.id.grid_img:
                setProductGridRecyclerViewAdapter(productImgList, productList);
                break;
            case R.id.list_img:
                setProductRecyclerViewAdapter(productImgList, productList);
                break;
        }
    }

    private void setProductRecyclerViewAdapter(ArrayList<Integer> productImgList, ArrayList<ProductModel> productList) {

        productAdapter = new ProductAdapter(mContext, productImgList, productList, this);
        product_rview.setLayoutManager(new LinearLayoutManager(mContext, VERTICAL, false));
        product_rview.setHasFixedSize(true);
        product_rview.setNestedScrollingEnabled(false);
        product_rview.setItemAnimator(new DefaultItemAnimator());
        product_rview.setAdapter(productAdapter);
    }

    private void setProductGridRecyclerViewAdapter(ArrayList<Integer> productImgList, ArrayList<ProductModel> productList) {

        productAdapter = new ProductAdapter(mContext, productImgList, productList, this);
        product_rview.setLayoutManager(new GridLayoutManager(mContext, 2));
        product_rview.setHasFixedSize(true);
        product_rview.setNestedScrollingEnabled(false);
        product_rview.setItemAnimator(new DefaultItemAnimator());
        product_rview.setAdapter(productAdapter);
    }

    /**
     * load the Login Fragment
     */
    private void setFragment(Fragment fragment, ArrayList<Integer> productImgList, ProductModel productModel) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("product_detail", productModel);
        bundle.putIntegerArrayList("product_img", productImgList);
        fragment.setArguments(bundle);
        //For initially add stack for backpress event
//        fragmentManager.popBackStack(Constant.BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(Constant.BACK_STACK_ROOT_TAG);
        fragmentTransaction.setCustomAnimations(R.anim.fragment_right_in, R.anim.fragment_left_out, R.anim.fragment_left_in, R.anim.fragment_right_out);
        fragmentTransaction
                .replace(R.id.product_frame, fragment)
                .commit();
    }

    @Override
    public void productItem(int imageItem, ProductModel productModel) {
//        toastMessage(productModel.getProductName());
        ArrayList<Integer> productImages = new ArrayList<>();
        switch (productModel.getProductName()) {
            case "Ener-G":
                productImages.add(R.drawable.energ);
                productImages.add(R.drawable.energ_info);
                setFragment(new ProductDescriptionFragment(), productImages, productModel);
                break;
            case "Fresh":
                productImages.add(R.drawable.fresh);
                productImages.add(R.drawable.fresh_info);
                setFragment(new ProductDescriptionFragment(), productImages, productModel);
                break;
            case "Microhume":
                productImages.add(R.drawable.microhume);
                productImages.add(R.drawable.micro_hume_info);
                setFragment(new ProductDescriptionFragment(), productImages, productModel);
                break;
            case "Akshaya":
                productImages.add(R.drawable.akshaya);
                productImages.add(R.drawable.akshaya_info);
                setFragment(new ProductDescriptionFragment(), productImages, productModel);
                break;
            case "Flower Plus":
                productImages.add(R.drawable.flowerplus);
                productImages.add(R.drawable.flower_plus_info);
                setFragment(new ProductDescriptionFragment(), productImages, productModel);
                break;
            case "Red":
                productImages.add(R.drawable.red);
                productImages.add(R.drawable.energ_info);
                setFragment(new ProductDescriptionFragment(), productImages, productModel);
                break;
            case "Top":
                productImages.add(R.drawable.top);
                productImages.add(R.drawable.top_info);
                setFragment(new ProductDescriptionFragment(), productImages, productModel);
                break;
            case "Bravo":
                productImages.add(R.drawable.bravo);
                productImages.add(R.drawable.energ_info);
                setFragment(new ProductDescriptionFragment(), productImages, productModel);
                break;
            case "Progro":
                productImages.add(R.drawable.progro);
                productImages.add(R.drawable.progro_info);
                setFragment(new ProductDescriptionFragment(), productImages, productModel);
                break;
            case "Cut off":
                productImages.add(R.drawable.cur_off);
                productImages.add(R.drawable.energ_info);
                setFragment(new ProductDescriptionFragment(), productImages, productModel);
                break;
            case "Super Potassium f humate":
                productImages.add(R.drawable.super_pottasium);
                productImages.add(R.drawable.energ_info);
                setFragment(new ProductDescriptionFragment(), productImages, productModel);
                break;

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
