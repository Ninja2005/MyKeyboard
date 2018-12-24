package com.hqumath.keyboard;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * ****************************************************************
 * 文件名称: MainActivity
 * 作    者: Created by gyd
 * 创建时间: 2018/11/26 17:12
 * 文件描述: 自定义键盘demo
 * 注意事项:
 * ****************************************************************
 */

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout mMain_ll;//主布局
    private EditText mNormal_ed;//系统键盘
    private EditText mNum_ed;//自定义键盘
    private EditText mPwd1_ed;//随机键盘1
    private EditText mPwd2_ed;//随机键盘2

    private KeyboardUtil keyboardUtil;//自定义键盘PopupWindow

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
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
        keyboardUtil.initKeyboard(MyKeyboardView.KEYBOARDTYPE_Num, mNum_ed);//数字键盘
        keyboardUtil.initKeyboard(MyKeyboardView.KEYBOARDTYPE_Pwd, mPwd1_ed, mPwd2_ed);//随机键盘
    }

    /**
     * 点击返回按钮，隐藏自定义键盘
     */
    @Override
    public void onBackPressed() {
        if (keyboardUtil != null && keyboardUtil.hide())
            return;
        super.onBackPressed();
    }

    /**
     * 点击空白处隐藏键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (SoftKeyboardUtil.isTouchView(filterViewByIds(), ev))//过滤的EditText,不做处理
                    return super.dispatchTouchEvent(ev);
                View v = getCurrentFocus();
                if (SoftKeyboardUtil.isFocusEditText(v, systemEditViewIds())//当前焦点在系统键盘EditText
                        && !SoftKeyboardUtil.isTouchView(systemEditViewIds(), ev)) {//且没有触摸在系统键盘EditText
                    SoftKeyboardUtil.hideInputForce(MainActivity.this, v);//隐藏系统键盘
                    v.clearFocus();//清空焦点
                } else if (SoftKeyboardUtil.isFocusEditText(v, customEditViewIds())//当前焦点在自定义键盘EditText
                        && !SoftKeyboardUtil.isTouchView(customEditViewIds(), ev)) {//且没有触摸在自定义键盘EditText
                    if (keyboardUtil != null)//隐藏自定义键盘
                        keyboardUtil.hide();
                    v.clearFocus();//清空焦点
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 自定义键盘的EditText，点击空白将会隐藏自定义键盘
     *
     * @return id 数组
     */
    public View[] customEditViewIds() {
        return new View[]{mNum_ed, mPwd1_ed, mPwd2_ed};
    }

    /**
     * 系统键盘的EditText，点击空白将会隐藏系统键盘
     *
     * @return id 数组
     */
    public View[] systemEditViewIds() {
        return new View[]{mNormal_ed};
    }

    /**
     * 过滤的EditText，点击空白将不会隐藏软键盘
     *
     * @return id 数组
     */
    public View[] filterViewByIds() {
        return null;
    }
}
