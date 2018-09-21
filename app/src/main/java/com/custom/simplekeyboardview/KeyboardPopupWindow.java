package com.custom.simplekeyboardview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;


public class KeyboardPopupWindow extends PopupWindow {
    private static final String TAG = "KeyboardPopupWindow";
    private Context context;
    private View anchorView;
    private EditText editText;

    public KeyboardPopupWindow(Context context, View anchorView, EditText editText) {
        this.context = context;
        this.anchorView = anchorView;
        this.editText = editText;
        if (context == null || anchorView == null) {
            return;
        }
        initConfig();
        initView();
    }

    private void initConfig() {
        setOutsideTouchable(false);
        setFocusable(false);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void refreshKeyboardOutSideTouchable(boolean isTouchable) {
        setOutsideTouchable(isTouchable);
        if (!isTouchable) {
            Log.d(TAG, "执行show");
            show();
        } else {
            Log.d(TAG, "执行dismiss");
            dismiss();
        }
    }

    private void initView() {
        final View view = LayoutInflater.from(context).inflate(R.layout.keyboadview, null);
        initKeyboardView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setContentView(view);
    }

    private void initKeyboardView(View view) {
        LinearLayout dropdownLl = view.findViewById(R.id.dropdownLl);
        dropdownLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //给数字键设置点击监听
        int[] commonButtonIds = new int[]{R.id.button00, R.id.button01, R.id.button02, R.id.button03, R.id.button04,
                R.id.button05, R.id.button06, R.id.button07, R.id.button08, R.id.button09};
        for (int i = 0; i < commonButtonIds.length; i++) {
            final Button button = view.findViewById(commonButtonIds[i]);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editText.setText(editText.getText().toString() + button.getText());
                    editText.setSelection(editText.getText().toString().length());
                }
            });
        }

        //给点键设置点击监听
        view.findViewById(R.id.buttonDot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText().toString() + ".");
                editText.setSelection(editText.getText().toString().length());
            }
        });

        //给叉键设置点击监听
        view.findViewById(R.id.buttonCross).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = editText.getText().toString().length();
                if (length > 0) {
                    editText.setText(editText.getText().toString().substring(0, length - 1));
                    editText.setSelection(length - 1);
                }
            }
        });


    }


    public void show() {
        if (!isShowing() && anchorView != null) {
            this.showAtLocation(anchorView, Gravity.BOTTOM, 0, 0);
        }
    }

    public void refreshViewAndShow(Context context, View anchorView, EditText editText) {
        this.context = context;
        this.anchorView = anchorView;
        this.editText = editText;
        if (context == null || anchorView == null) {
            return;
        }
        show();
    }

    public void releaseResources() {
        this.dismiss();
        context = null;
        anchorView = null;
    }

}
