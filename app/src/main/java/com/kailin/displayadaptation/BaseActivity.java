package com.kailin.displayadaptation;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

/**
 * 项目名：  DisplayAdaptation
 * 包名：    com.kailin.displayadaptation
 * 创建者：  Mars
 * 创建时间：2018/7/24 17:15
 * 描述：
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCustomDensity(this, getApplication());
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);

        //inMultiWindow为true，表示Activity进入分屏模式
        setCustomDensity(this, getApplication());
    }

    private static float sNoncompatDensity;
    private static float sNoncompatScaledDensity;

    /**
     * 以设计图宽度是360dp，以宽维度来适配
     * 需要在onCreate调用本方法 必须在setContentView之前
     * 如果要适配分屏则还需要在onMultiWindowModeChanged调用本方法
     *
     * @param activity    activity
     * @param application application
     */
    private static void setCustomDensity(Activity activity, final Application application) {
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();

        if (sNoncompatDensity == 0) {
            sNoncompatDensity = appDisplayMetrics.density;
            sNoncompatScaledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    //监听字体切换
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }

        //density  = 宽 / 设定的dp值
        final float targetDensity = appDisplayMetrics.widthPixels / 360;
        //文字缩放倍率 现不启用 故为sNoncompatDensity  启用后则随系统变化而变化 需谨慎
        final float targetScaledDensity = sNoncompatDensity /* targetDensity *(sNoncompatScaledDensity / sNoncompatDensity)*/;
        //转成dpi dpi =  density * 160;
        final int targetDensityDpi = (int) (160 * targetDensity);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaledDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }

}
