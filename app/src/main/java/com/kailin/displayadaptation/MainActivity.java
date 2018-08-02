package com.kailin.displayadaptation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DimenRes;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 获取dimen文件中对应的dp值 并转换成正确的dp值
     *
     * @param context 上下文
     * @param id      dimen中的dp值
     * @return 正确的dp值
     */
    public static float getDimen(Context context, @DimenRes int id) {
        return context.getResources().getDimension(id) / context.getResources().getDisplayMetrics().density;
    }
}
