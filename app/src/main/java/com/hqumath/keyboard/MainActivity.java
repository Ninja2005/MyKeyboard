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
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (SoftKeyboardUtil.isTouchView(filterViewByIds(), ev))
                    return super.dispatchTouchEvent(ev);
                if (hideSoftByEditViewIds() == null || hideSoftByEditViewIds().length == 0)
                    return super.dispatchTouchEvent(ev);
                View v = getCurrentFocus();
                if (SoftKeyboardUtil.isFocusEditText(v, hideSoftByEditViewIds())) {
                    //TODO 从普通键盘到自定义键盘  不会隐藏普通键盘
                    if (SoftKeyboardUtil.isTouchView(hideSoftByEditViewIds(), ev))
                        return super.dispatchTouchEvent(ev);
                    //隐藏键盘
                    SoftKeyboardUtil.hideInputForce(this, v);
                    if (getKeyboardUtil() != null)
                        getKeyboardUtil().hide();
                    SoftKeyboardUtil.clearViewFocus(v, hideSoftByEditViewIds());
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 传入EditText的View
     * 没有传入的EditText不做处理
     *
     * @return id 数组
     */
    public View[] hideSoftByEditViewIds() {
        return new View[]{mNormal_ed, mNum_ed, mPwd1_ed, mPwd2_ed};
    }

    /**
     * 传入要过滤的View
     * 过滤之后点击将不会有隐藏软键盘的操作
     *
     * @return id 数组
     */
    public View[] filterViewByIds() {
        return null;
    }

    /**
     * 获取自定义键盘,用来隐藏自定义键盘
     *
     * @return
     */
    public KeyboardUtil getKeyboardUtil() {
        return keyboardUtil;
    }
}
