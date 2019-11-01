package com.miniproject.energ.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miniproject.energ.R;
import com.miniproject.energ.ui.product.ProductActivity;

import org.json.JSONObject;

/**
 * Created by Rajesh Pradeep G on 21-10-2019
 */
public class Utils {

    public static Typeface type;

    public static void setBlackFonts(Context context, TextView[] textViews) {
        type = Typeface.createFromAsset(context.getAssets(), "fonts/NunitoSans-Black");
        for (TextView textView : textViews) {
            textView.setTypeface(type);
        }
    }

    public static void setBlackItalicFonts(Context context, TextView[] textViews) {
        type = Typeface.createFromAsset(context.getAssets(), "fonts/NunitoSans-BlackItalic.ttf");
        for (TextView textView : textViews) {
            textView.setTypeface(type);
        }
    }

    public static void setBoldFonts(Context context, TextView[] textViews) {
        type = Typeface.createFromAsset(context.getAssets(), "fonts/NunitoSans-Bold.ttf");
        for (TextView textView : textViews) {
            textView.setTypeface(type);
        }
    }

    public static void setBoldItalicFonts(Context context, TextView[] textViews) {
        type = Typeface.createFromAsset(context.getAssets(), "fonts/NunitoSans-BoldItalic.ttf");
        for (TextView textView : textViews) {
            textView.setTypeface(type);
        }
    }

    public static void setRegularFonts(Context context, TextView[] textViews) {
        type = Typeface.createFromAsset(context.getAssets(), "fonts/NunitoSans-Regular.ttf");
        for (TextView textView : textViews) {
            textView.setTypeface(type);
        }
    }

    public static void setItalicFonts(Context context, TextView[] textViews) {
        type = Typeface.createFromAsset(context.getAssets(), "fonts/NunitoSans-Italic.ttf");
        for (TextView textView : textViews) {
            textView.setTypeface(type);
        }
    }

    public static void setLightFonts(Context context, TextView[] textViews) {
        type = Typeface.createFromAsset(context.getAssets(), "fonts/NunitoSans-Light.ttf");
        for (TextView textView : textViews) {
            textView.setTypeface(type);
        }
    }

    public static void setSemiBoldFonts(Context context, TextView[] textViews) {
        type = Typeface.createFromAsset(context.getAssets(), "fonts/NunitoSans-SemiBold.ttf");
        for (TextView textView : textViews) {
            textView.setTypeface(type);
        }
    }

    public static void setSemiItalicFonts(Context context, TextView[] textViews) {
        type = Typeface.createFromAsset(context.getAssets(), "fonts/NunitoSans-SemiBoldItalic.ttf");
        for (TextView textView : textViews) {
            textView.setTypeface(type);
        }
    }

    public static boolean isValidEmail(CharSequence email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public static void setError(EditText editText, String errorMsg) {
        editText.requestFocus();
        editText.setError(errorMsg);
    }

    /**
     * Custom Alert
     */
    public static void showCustomAlert(final Context context, String title, String message, final String from) {

        Dialog dialog = new Dialog(context, R.style.custom_dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.common_custom_dialog);

        TextView dialog_text = (TextView) dialog.findViewById(R.id.title);
        TextView dialog_message = (TextView) dialog.findViewById(R.id.subtitle);
        TextView dialog_positive = (TextView) dialog.findViewById(R.id.dialogButtonOK);
        TextView dialog_negative = (TextView) dialog.findViewById(R.id.dialogButtonNO);
        LinearLayout parent = (LinearLayout) dialog.findViewById(R.id.dialog_parent_layout);
        View dialog_separator = (View) dialog.findViewById(R.id.separator);

        Utils.setBoldFonts(context, new TextView[]{dialog_text, dialog_positive, dialog_negative});
        Utils.setRegularFonts(context, new TextView[]{dialog_message});

        if (!title.isEmpty())
            dialog_text.setText(title);
        else {
            dialog_text.setVisibility(View.GONE);
            int paddingDp = 25, paddingBottom = 15;
            float density = context.getResources().getDisplayMetrics().density;
            int paddingPixel = (int) (paddingDp * density);
            int paddingPixel2 = (int) (paddingBottom * density);
            dialog_message.setPadding(paddingPixel, paddingPixel, paddingPixel, paddingPixel2);
        }

        if (!TextUtils.isEmpty(message))
            dialog_message.setText(message);
        else
            dialog_message.setVisibility(View.GONE);

        if (from.equals("create_acc")) {
            dialog_negative.setVisibility(View.GONE);
            dialog_separator.setVisibility(View.VISIBLE);
            dialog_positive.setText("OK");
            dialog_positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProductActivity.class);
                    context.startActivity(intent);
                    dialog.cancel();
                    dialog.dismiss();

                }
            });
        } else if (from.equals("app_close")) {
            dialog_negative.setVisibility(View.VISIBLE);
            dialog_separator.setVisibility(View.VISIBLE);
            dialog_positive.setText("OK");
            dialog_negative.setText("Cancel");
            dialog_positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                    dialog.dismiss();
                    ((Activity) context).finishAffinity();
                }
            });
            dialog_negative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                    dialog.dismiss();
                }
            });
        } else {
            dialog_negative.setVisibility(View.GONE);
            dialog_separator.setVisibility(View.GONE);
            dialog_positive.setText("OK");
            dialog_positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                    dialog.dismiss();

                }
            });
        }

        dialog.setCancelable(false);
        dialog.show();
    }

    public static void usPhoneCode(EditText phone_etext) {
        phone_etext.addTextChangedListener(new PhoneNumberFormattingTextWatcher() {
            private boolean backspacingFlag = false;
            private boolean editedFlag = false;
            private int cursorComplement;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                cursorComplement = s.length() - phone_etext.getSelectionStart();
                if (count > after) {
                    backspacingFlag = true;
                } else {
                    backspacingFlag = false;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // nothing to do here =D
            }

            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                String phone = string.replaceAll("[^\\d]", "");
                if (!editedFlag) {
                    if (phone.length() >= 6 && !backspacingFlag) {
                        editedFlag = true;
                        String ans = "(" + phone.substring(0, 3) + ") " + phone.substring(3, 6) + "-" + phone.substring(6);
                        phone_etext.setText(ans);
                        phone_etext.setSelection(phone_etext.getText().length() - cursorComplement);
                    } else if (phone.length() >= 3 && !backspacingFlag) {
                        editedFlag = true;
                        String ans = "(" + phone.substring(0, 3) + ") " + phone.substring(3);
                        phone_etext.setText(ans);
                        phone_etext.setSelection(phone_etext.getText().length() - cursorComplement);
                    }
                } else {
                    editedFlag = false;
                }
            }
        });
    }
}
