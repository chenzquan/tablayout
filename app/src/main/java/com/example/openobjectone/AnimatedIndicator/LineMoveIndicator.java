package com.example.openobjectone.AnimatedIndicator;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.animation.LinearInterpolator;

import com.example.openobjectone.AnimatedIndicatorInterface;
import com.example.openobjectone.DachshundTabLayout;

public class LineMoveIndicator implements AnimatedIndicatorInterface,ValueAnimator.AnimatorUpdateListener{

    private Paint mPaint;

    private RectF rectF;

    private int height;
    private int edgeRadius;
    private int leftX,rightX;

    private ValueAnimator valueAnimatorLeft;
    private ValueAnimator valueAnimatorRight;

    private LinearInterpolator linearInterpolator;



    private DachshundTabLayout dachshundTabLayout;

    public LineMoveIndicator(DachshundTabLayout dachshundTabLayout){
        this.dachshundTabLayout = dachshundTabLayout;

        valueAnimatorLeft = new ValueAnimator();
        valueAnimatorLeft.setDuration(DEFAULT_DURATION);
        valueAnimatorLeft.addUpdateListener(this);
        valueAnimatorLeft.setInterpolator(linearInterpolator);

        valueAnimatorRight = new ValueAnimator();
        valueAnimatorRight.setDuration(DEFAULT_DURATION);
        valueAnimatorRight.addUpdateListener(this);
        valueAnimatorRight.setInterpolator(linearInterpolator);

        rectF = new RectF();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);


        leftX = (int) dachshundTabLayout.getChildXLeft(dachshundTabLayout.getCurrentPosition());
        rightX = (int) dachshundTabLayout.getChildXRight(dachshundTabLayout.getCurrentPosition());

        edgeRadius = -1;
    }


    @Override
    public void setSelectedTabIndicatorColor(int color) {
        mPaint.setColor(color);
    }

    @Override
    public void setSelectedTabIndicatorHeight(int height) {
        this.height = height;
        if(edgeRadius == -1)
            edgeRadius = height;
    }

    @Override
    public void setIntValues(int startXLeft, int endXLeft, int startXCenter, int endXCenter, int startXRight, int endXRight) {
        valueAnimatorLeft.setIntValues(startXLeft, endXLeft);
        valueAnimatorRight.setIntValues(startXRight, endXRight);
    }

    @Override
    public void setCurrentPlayTime(long currentPlayTime) {
        valueAnimatorLeft.setCurrentPlayTime(currentPlayTime);
        valueAnimatorRight.setCurrentPlayTime(currentPlayTime);
    }

    @Override
    public void draw(Canvas canvas) {
        rectF.top = dachshundTabLayout.getHeight() - height;
        rectF.left = leftX + height/2;
        rectF.right = rightX - height/2;
        rectF.bottom = dachshundTabLayout.getHeight();
        canvas.drawRoundRect(rectF,edgeRadius,edgeRadius,mPaint);
    }

    @Override
    public long getDuration() {
        return valueAnimatorLeft.getDuration();
    }



    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        leftX = (int) valueAnimatorLeft.getAnimatedValue();
        rightX = (int) valueAnimatorRight.getAnimatedValue();

    }
}
