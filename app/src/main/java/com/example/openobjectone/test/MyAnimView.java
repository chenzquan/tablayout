package com.example.openobjectone.test;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;

public class MyAnimView extends View {

    private static final float RADIUS = 50f;

    private Context context;

    private Point currentPoint;

    private Paint mPaint;

    private String color321;

    public String getColor() {
        return color321;
    }

    public void setColor2(String color) {
        this.color321 = color;
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }

    public MyAnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }


    @Override
    protected void onDraw(Canvas canvas) {  //currentPoint 空的话 就创一个 然后启动动画 不是空的话 就重新画一个圆
        super.onDraw(canvas);

        if(currentPoint==null){
            currentPoint = new Point(RADIUS,RADIUS);
            drawCircle(canvas);
            startAnimation();
        }else{
            drawCircle(canvas);
        }

    }

    private void startAnimation() {

        Point startPoint = new Point(getWidth()/2,RADIUS);
        Point endPoint = new Point(getWidth()/2,getHeight() - RADIUS);

        ValueAnimator valueAnimator = ValueAnimator.ofObject(new PointEvaluator(),startPoint,endPoint);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });     //更新

//        valueAnimator.setDuration(5000);
//        valueAnimator.start();
        valueAnimator.setInterpolator(new DecelerateAccelerateInterpolator());

        ObjectAnimator anim2 = ObjectAnimator.ofObject(this, "color2", new ColorEvaluator(),
                "#000000", "#FFFF00");
        AnimatorSet animSet = new AnimatorSet();

        animSet.play(valueAnimator).with(anim2);
        animSet.setDuration(4000);
        animSet.start();
    }

    private void drawCircle(Canvas canvas) {

        float x = currentPoint.getX();
        float y = currentPoint.getY();
        canvas.drawCircle(x,y,RADIUS,mPaint);

    }
}
