package com.custom.simplekeyboardview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText numberEt;
    private KeyboardPopupWindow keyboardPopupWindow;
    private boolean isUiCreated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        numberEt = findViewById(R.id.numberEt);
        keyboardPopupWindow = new KeyboardPopupWindow(MainActivity.this, getWindow().getDecorView(), numberEt);
//        numberEt.setInputType(InputType.TYPE_NULL);//该设置会导致光标不可见
        numberEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideInput(MainActivity.this, getWindow().getDecorView());//传递整个窗口的view，而不是具体某个
                if (keyboardPopupWindow != null) {
                    keyboardPopupWindow.show();
                }
            }
        });
        numberEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(TAG, "数字输入框是否有焦点——>" + hasFocus);
                if (keyboardPopupWindow != null && isUiCreated) {//很重要，Unable to add window -- token null is not valid; is your activity running?
                    // 需要等待页面创建完成后焦点变化才去显示自定义键盘
                    keyboardPopupWindow.refreshKeyboardOutSideTouchable(!hasFocus);
                }
                hideInput(MainActivity.this, getWindow().getDecorView());//传递整个窗口的view，而不是具体某个
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        isUiCreated = true;
    }

    private void hideInput(Context context, View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    protected void onDestroy() {
        if (keyboardPopupWindow != null) {
            keyboardPopupWindow.releaseResources();
        }
        super.onDestroy();
    }
}
