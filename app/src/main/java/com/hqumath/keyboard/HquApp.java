package com.hqumath.keyboard;

import android.app.Application;

/**
 * ****************************************************************
 * 文件名称: HquApp
 * 作    者: Created by gyd
 * 创建时间: 2018/12/21 21:29
 * 文件描述:
 * 注意事项:
 * ****************************************************************
 */

public class HquApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //屏幕适配方案，根据ui图修改,屏幕最小宽度375dp
        Density.setDensity( this, 375f);
    }
}
