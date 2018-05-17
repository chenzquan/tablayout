package com.example.openobjectone;

import android.graphics.Canvas;
import android.support.annotation.ColorInt;

public interface AnimatedIndicatorInterface {
    long DEFAULT_DURATION = 500;//默认的持续时间
    void setSelectedTabIndicatorColor(@ColorInt int color);//定制颜色
    void setSelectedTabIndicatorHeight(int height);//定制高度
    void setIntValues(int startXLeft, int endXLeft,
                      int startXCenter, int endXCenter,
                      int startXRight, int endXRight);//目标标签和当前的X位置
    void setCurrentPlayTime(long currentPlayTime);//动画的当前播放时间

    /**
     * Make your drawing calls here.
     * Call invalidate() when you need to redraw.
     *
     * @param canvas DachshundTabLayout canvas.
     */
    void draw(Canvas canvas); //绘画

    /**
     * Override it, if you want to work with animators, to make setCurrentPlayTime method work.
     *
     * @return the duration of your animator. Currently supporting only DEFAULT_DURATION.
     */
    long getDuration();  //返回动画持续的时间
}
