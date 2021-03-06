package com.jnu.sudokudos.jsudoku;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jnu.sudokudos.util.DensityUtils;

import java.util.ArrayList;
import java.util.List;

public class Grid extends RelativeLayout {

    private int TEXT_SIZE = DensityUtils.dp2px(getContext(), 40);

    private List<List<TextView>> mTextArrays;

    public Grid(Context context) {
        this(context, null);
    }

    public Grid(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Grid(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        int padding = DensityUtils.dp2px(context, 1);
        setPadding(padding, padding, padding, padding);
        mTextArrays = new ArrayList<>();

        // 九宫格中的9个textView
        for (int i = 0; i < 3; i++) {
            List<TextView> viewList = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                // 设置tv的基本属性
                TextView textView = new TextView(context);
                textView.setWidth(TEXT_SIZE);
                textView.setHeight(TEXT_SIZE);
                textView.setBackgroundColor(Color.WHITE);
                textView.setId(View.generateViewId());
                textView.setGravity(Gravity.CENTER);
//                textView.setText(String.format("(%1$d,%2$d)", i, j));
                // 添加tv到Grid中（因为Grid是一个布局）
                addView(textView);
                // 一级list存一行tv
                viewList.add(textView);
                // 工具tv的位置(i, j)设置tv的布局参数
                LayoutParams params = (LayoutParams) textView.getLayoutParams();
                if (j == 0) {
                    if (i == 0) {
                        params.addRule(RelativeLayout.ALIGN_PARENT_START);
                        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    } else if (i == 1) {
                        params.addRule(RelativeLayout.BELOW, mTextArrays.get(0).get(0).getId());
                        params.topMargin = DensityUtils.dp2px(context,1);
                    } else {
                        params.addRule(RelativeLayout.BELOW, mTextArrays.get(1).get(0).getId());
                        params.topMargin = DensityUtils.dp2px(context,1);
                    }
                } else if (j == 1) {
                    params.addRule(RelativeLayout.RIGHT_OF, viewList.get(j - 1).getId());
                    params.addRule(ALIGN_TOP, viewList.get(j - 1).getId());
                    params.leftMargin = DensityUtils.dp2px(context,1);
                } else {
                    params.addRule(RelativeLayout.RIGHT_OF, viewList.get(j - 1).getId());
                    params.addRule(ALIGN_TOP, viewList.get(j - 1).getId());
                    params.leftMargin = DensityUtils.dp2px(context,1);
                }
            }
            // 二级list
            mTextArrays.add(viewList);
        }
        setBackgroundColor(Color.BLACK);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = DensityUtils.dp2px(getContext(), 6 + 3 * 40);
        super.onMeasure(size, size);
//        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    public List<List<TextView>> getTextArrays(){
        return this.mTextArrays;
    }
}
