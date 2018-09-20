package com.custom.simplekeyboardview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText numberEt;
    private Button popBtn;
    private KeyboardPopupWindow keyboardPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        numberEt = findViewById(R.id.numberEt);
        popBtn = findViewById(R.id.popBtn);
        keyboardPopupWindow = new KeyboardPopupWindow(MainActivity.this, getWindow().getDecorView(),numberEt);
        popBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (keyboardPopupWindow != null) {
                    keyboardPopupWindow.show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (keyboardPopupWindow != null) {
            keyboardPopupWindow.releaseResources();
        }
        super.onDestroy();
    }
}
