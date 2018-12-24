package com.hqumath.keyboard;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * ****************************************************************
 * 文件名称: KeyboardTool
 * 作    者: Created by gyd
 * 创建时间: 2018/12/24 18:49
 * 文件描述: 键盘隐藏工具
 * 注意事项:
 * ****************************************************************
 */
public class KeyboardTool {
    /**
     * 隐藏键盘
     *
     * @param v     焦点所在View
     * @param views 输入框
     * @return true代表焦点在edit上
     */
    public static boolean isFocusEditText(View v, View... views) {
        if (v instanceof EditText && views != null && views.length > 0) {
            for (View view : views) {
                if (v == view) {
                    return true;
                }
            }
        }
        return false;
    }

    //是否触摸在指定view上面,对某个控件过滤
    public static boolean isTouchView(View[] views, MotionEvent ev) {
        if (views == null || views.length == 0) return false;
        int[] location = new int[2];
        for (View view : views) {
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (ev.getX() > x && ev.getX() < (x + view.getWidth())
                    && ev.getY() > y && ev.getY() < (y + view.getHeight())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 隐藏软键盘,这种方式参数为activity
     */
    public static void hideInputForce(Activity activity, View currentFocusView) {
        if (activity == null || currentFocusView == null)
            return;
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(currentFocusView.getWindowToken(), 0);
    }
}
