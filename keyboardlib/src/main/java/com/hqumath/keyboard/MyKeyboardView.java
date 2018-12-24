package com.hqumath.keyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.PopupWindow;

import java.util.List;
import java.util.Random;

/**
 * ****************************************************************
 * 文件名称: MyKeyboardView
 * 作    者: Created by gyd
 * 创建时间: 2018/11/29 17:20
 * 文件描述: 自定义键盘，支持多种键盘切换
 * 注意事项: 密码输入
 * ****************************************************************
 */
public class MyKeyboardView extends KeyboardView {
    public static final int KEYBOARDTYPE_Num = 0;//数字键盘
    public static final int KEYBOARDTYPE_Pwd = 1;//数字键盘（密码）
    public static final int KEYBOARDTYPE_ABC = 2;//字母键盘
    public static final int KEYBOARDTYPE_Symbol = 4;//符号键盘
    private final String strLetter = "abcdefghijklmnopqrstuvwxyz";//字母

    private EditText mEditText;
    private PopupWindow mWindow;

    private Keyboard keyboardNum;
    private Keyboard keyboardPwd;
    private Keyboard keyboardABC;
    private Keyboard keyboardSymbol;

    public boolean isSupper = false;//字母键盘 是否大写
    public boolean isPwd = false;//数字键盘 是否随机

    public MyKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyKeyboardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void init(EditText editText, PopupWindow window, int keyBoardType) {
        this.mEditText = editText;
        this.mWindow = window;
        if (keyBoardType == KEYBOARDTYPE_Pwd)
            isPwd = true;
        setEnabled(true);
        setPreviewEnabled(false);
        setOnKeyboardActionListener(mOnKeyboardActionListener);
        setKeyBoardType(keyBoardType);
    }

    public EditText getEditText() {
        return mEditText;
    }

    /**
     * 设置键盘类型
     */
    public void setKeyBoardType(int keyBoardType) {
        switch (keyBoardType) {
            case KEYBOARDTYPE_Num:
                if (keyboardNum == null)
                    keyboardNum = new Keyboard(getContext(), R.xml.keyboard_number);
                setKeyboard(keyboardNum);
                break;
            case KEYBOARDTYPE_ABC:
                if (keyboardABC == null)
                    keyboardABC = new Keyboard(getContext(), R.xml.keyboard_abc);
                setKeyboard(keyboardABC);
                break;
            case KEYBOARDTYPE_Pwd:
                if (keyboardPwd == null)
                    keyboardPwd = new Keyboard(getContext(), R.xml.keyboard_number);
                randomKey(keyboardPwd);
                setKeyboard(keyboardPwd);
                break;
            case KEYBOARDTYPE_Symbol:
                if (keyboardSymbol == null)
                    keyboardSymbol = new Keyboard(getContext(), R.xml.keyboard_symbol);
                setKeyboard(keyboardSymbol);
                break;
        }
    }

    private OnKeyboardActionListener mOnKeyboardActionListener = new OnKeyboardActionListener() {

        @Override
        public void onPress(int primaryCode) {

        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = mEditText.getText();
            int start = mEditText.getSelectionStart();
            switch (primaryCode) {
                case Keyboard.KEYCODE_DELETE://回退
                    if (editable != null && editable.length() > 0) {
                        if (start > 0) {
                            editable.delete(start - 1, start);
                        }
                    }
                    break;
                case Keyboard.KEYCODE_SHIFT://大小写切换
                    changeKey();
                    setKeyBoardType(KEYBOARDTYPE_ABC);
                    break;
                case Keyboard.KEYCODE_CANCEL:// 隐藏
                case Keyboard.KEYCODE_DONE:// 确认
                    mWindow.dismiss();
                    break;
                case 123123://切换数字键盘
                    if (isPwd) {
                        setKeyBoardType(KEYBOARDTYPE_Pwd);
                    } else {
                        setKeyBoardType(KEYBOARDTYPE_Num);
                    }
                    break;
                case 456456://切换字母键盘
                    if (isSupper)//如果当前为大写键盘，改为小写
                        changeKey();
                    setKeyBoardType(KEYBOARDTYPE_ABC);
                    break;
                case 789789://切换符号键盘
                    setKeyBoardType(KEYBOARDTYPE_Symbol);
                    break;
                case 666666://人名分隔符·
                    editable.insert(start, "·");
                    break;
                default://字符输入
                    editable.insert(start, Character.toString((char) primaryCode));
            }
        }

        @Override
        public void onText(CharSequence text) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };

    /**
     * 键盘大小写切换
     */
    private void changeKey() {
        List<Keyboard.Key> keylist = keyboardABC.getKeys();
        if (isSupper) {// 大写切小写
            for (Keyboard.Key key : keylist) {
                if (key.label != null && strLetter.contains(key.label.toString().toLowerCase())) {
                    key.label = key.label.toString().toLowerCase();
                    key.codes[0] = key.codes[0] + 32;
                }
            }
        } else {// 小写切大写
            for (Keyboard.Key key : keylist) {
                if (key.label != null && strLetter.contains(key.label.toString().toLowerCase())) {
                    key.label = key.label.toString().toUpperCase();
                    key.codes[0] = key.codes[0] - 32;
                }
            }
        }
        isSupper = !isSupper;
    }

    /**
     * 数字键盘随机
     * code 48-57 (0-9)
     */
    public void randomKey(Keyboard pLatinKeyboard) {
        int[] ayRandomKey = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Random random = new Random();
        for (int i = 0; i < ayRandomKey.length; i++) {
            int a = random.nextInt(ayRandomKey.length);
            int temp = ayRandomKey[i];
            ayRandomKey[i] = ayRandomKey[a];
            ayRandomKey[a] = temp;
        }
        List<Keyboard.Key> pKeyLis = pLatinKeyboard.getKeys();
        int index = 0;
        for (int i = 0; i < pKeyLis.size(); i++) {
            int code = pKeyLis.get(i).codes[0];
            if (code >= 48 && code <= 57) {
                pKeyLis.get(i).label = ayRandomKey[index] + "";
                pKeyLis.get(i).codes[0] = 48 + ayRandomKey[index];
                index++;
            }
        }
    }
}