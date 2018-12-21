package com.hqumath.keyboard;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout mMain_ll;//主布局
    private EditText mNormal_ed;//普通
    private EditText mNum_ed;//自定义
    private EditText mPwd1_ed;//随机
    private EditText mPwd2_ed;//随机

    private KeyboardUtil keyboardUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
        initData();
    }

    protected void initView() {
        mMain_ll = findViewById(R.id.main_ll);
        mNormal_ed = findViewById(R.id.normal_ed);
        mNum_ed = findViewById(R.id.num_ed);
        mPwd1_ed = findViewById(R.id.pwd1_ed);
        mPwd2_ed = findViewById(R.id.pwd2_ed);

    }

    protected void initListener() {
        keyboardUtil = new KeyboardUtil(this, mMain_ll);
        keyboardUtil.initKeyboard(MyKeyboardView.KEYBOARDTYPE_Num, mNum_ed);
        keyboardUtil.initKeyboard(MyKeyboardView.KEYBOARDTYPE_Pwd, mPwd1_ed, mPwd2_ed);

    }

    protected void initData() {

    }

    /**
     * 隐藏自定义键盘
     */
    @Override
    public void onBackPressed() {
        if (keyboardUtil != null)
            keyboardUtil.hide();
    }
}
