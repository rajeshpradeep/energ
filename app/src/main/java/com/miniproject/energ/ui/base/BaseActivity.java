package com.miniproject.energ.ui.base;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.os.Build;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.miniproject.energ.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Rajesh Pradeep G on 22-10-2019
 */
public class BaseActivity extends AppCompatActivity {

    /** request the editText it will open the keyboard*/
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    /** hide the keyboard
     * @param view which has focus*/
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Shows soft keyboard.
     *
     * @param editText EditText which has focus
     */
    public void showSoftKeyboard(Context context, EditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }

    /**
     * show the alert message
     * @param toast is the message
     */
    public void toastMessage(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    /**
     * set the status bar as transparent
     */
    public void setTransparentStatusBar () {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * set the status bar as color primary
     */
    public void setStatusBar() {

        try {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * set the status bar as white color
     */
    public void setWhiteStatusBar() {
        try {
            // Show the status bar.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                View decorView = getWindow().getDecorView();
                int uiOptions = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                decorView.setSystemUiVisibility(uiOptions);
            } else {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.white));
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove Status bar
     */
    public void removeStatusBar() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

    }

    /**
     * disable the edittext when open the number picker from YourInfo and WhosConcern screen
     * @param editText is focus*/
    public void disableEdittextInput(EditText editText) {
        editText.setInputType(InputType.TYPE_NULL);
        editText.setTextIsSelectable(false);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return true;  // Blocks input from hardware keyboards.
            }
        });
    }
}
