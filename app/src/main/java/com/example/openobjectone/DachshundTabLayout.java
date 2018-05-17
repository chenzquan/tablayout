package com.example.openobjectone;

import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.openobjectone.AnimatedIndicator.DachshundIndicator;

public class DachshundTabLayout extends TabLayout implements ViewPager.OnPageChangeListener{

    private static final int DEFAULT_HEIGHT_DP = 6;

    private int indicatorColor;//颜色
    private int indicatorHeight;//高度

    private int currentPosition;

    private boolean centerAlign;

    private ViewPager viewPager;

    private LinearLayout tabStrip;

    private AnimatedIndicatorType animatedIndicatorType;  //enum

    private AnimatedIndicatorInterface animatedIndicator;  //接口


    int startXLeft, endXLeft, startXCenter, endXCenter, startXRight, endXRight;


    public DachshundTabLayout(Context context) {
        this(context, null);
    }

    public DachshundTabLayout(Context context, AttributeSet attrs) {
       // super(context, attrs);
        this(context,attrs,0);
    }

    public DachshundTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        super.setSelectedTabIndicatorHeight(0);

        tabStrip = (LinearLayout) super.getChildAt(0);



        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.DachshundTabLayout);

        this.indicatorHeight = a.getDimensionPixelSize(R.styleable.DachshundTabLayout_ddIndicatorHeight,HelperUtils.dpToPx(DEFAULT_HEIGHT_DP));
        this.indicatorColor = a.getColor(R.styleable.DachshundTabLayout_ddIndicatorColor, Color.WHITE);
        this.centerAlign = a.getBoolean(R.styleable.DachshundTabLayout_ddCenterAlign,false);
        this.animatedIndicatorType = AnimatedIndicatorType.values()[a.getInt(R.styleable.DachshundTabLayout_ddAnimatedIndicator, 0)];  //枚举的用法】
        this.animatedIndicatorType = AnimatedIndicatorType.DACHSHUND;
        a.recycle();
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if(centerAlign){
            View firstTab = ((ViewGroup)getChildAt(0)).getChildAt(0);
            View lastTab = ((ViewGroup)getChildAt(0)).getChildAt(((ViewGroup)getChildAt(0)).getChildCount()-1);
            ViewCompat.setPaddingRelative(getChildAt(0),(getWidth()/2) - (firstTab.getWidth() / 2),0,(getWidth() / 2) - (lastTab.getWidth() / 2),0);
        }

        setupAnimatedIndicator();

    }

    private void setupAnimatedIndicator(){
        //      Log.i("sfsdfdsf",animatedIndicatorType.toString());
        switch (animatedIndicatorType){
            case DACHSHUND:
                setAnimatedIndicator(new DachshundIndicator(this));
                break;
            case LINE_FADE:
                break;
            case LINE_MOVE:
                break;
            case POINT_FADE:
                break;
            case POINT_MOVE:
                break;
            default:
                break;
        }
    }



    public AnimatedIndicatorInterface getAnimatedIndicator() {
        return animatedIndicator;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }



    public void setAnimatedIndicator(AnimatedIndicatorInterface animatedIndicator){ //设置 Indicator的高度和颜色
        this.animatedIndicator = animatedIndicator;

        animatedIndicator.setSelectedTabIndicatorColor(indicatorColor);
        animatedIndicator.setSelectedTabIndicatorHeight(indicatorHeight);
        invalidate();
    }


    @Override
    public void setSelectedTabIndicatorColor(int color) {
        this.indicatorColor = color;
        if(animatedIndicator!=null){
            animatedIndicator.setSelectedTabIndicatorColor(color);
            invalidate();
        }
    }

    @Override
    public void setSelectedTabIndicatorHeight(int height) {
       this.indicatorHeight = height;
       if(animatedIndicator!=null){
            animatedIndicator.setSelectedTabIndicatorHeight(height);
            invalidate();
       }
    }

    public void setCenterAlign(boolean centerAlign){
        this.centerAlign = centerAlign;

        requestLayout();
    }



    @Override
    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        super.setupWithViewPager(viewPager,true);
        //TODO
        if (viewPager != null) {
            if (viewPager != this.viewPager) {
                viewPager.removeOnPageChangeListener(this);
                viewPager.addOnPageChangeListener(this);
            }
        }
    }

    @Override
    public void setupWithViewPager(@Nullable ViewPager viewPager, boolean autoRefresh) {
        super.setupWithViewPager(viewPager, autoRefresh);

        if(viewPager!=null){
            if(viewPager!=this.viewPager){
                viewPager.removeOnPageChangeListener(this);
                viewPager.addOnPageChangeListener(this);
            }
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(animatedIndicator!=null){  //调用animatedIndicator里面的draw
            animatedIndicator.draw(canvas);
        }

    }

    public float getChildXLeft(int position) {
        if(tabStrip.getChildAt(position) != null)
            return (tabStrip.getChildAt(position).getX());
        else
            return 0;
    }

    public float getChildXCenter(int position){
        if(tabStrip.getChildAt(position)!=null){
            return (tabStrip.getChildAt(position).getX() + tabStrip.getChildAt(position).getWidth()/2);
        }else{
            return 0;
        }
    }
    public float getChildXRight(int position) {
        if(tabStrip.getChildAt(position) != null)
            return (tabStrip.getChildAt(position).getX() + tabStrip.getChildAt(position).getWidth());
        else
            return 0;
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {//实现动画效果
        if ((position > currentPosition) || (position + 1 < currentPosition)) {
            currentPosition = position;
        }

        if (position != currentPosition) {

            startXLeft = (int) getChildXLeft(currentPosition);
            startXCenter = (int) getChildXCenter(currentPosition);
            startXRight = (int) getChildXRight(currentPosition);

            endXLeft = (int) getChildXLeft(position);
            endXRight = (int) getChildXRight(position);
            endXCenter = (int) getChildXCenter(position);

            if (animatedIndicator != null) {
                animatedIndicator.setIntValues(startXLeft, endXLeft,
                        startXCenter, endXCenter,
                        startXRight, endXRight);
                animatedIndicator.setCurrentPlayTime((long) ((1 - positionOffset) * (int) animatedIndicator.getDuration()));
            }

        } else {

            startXLeft = (int) getChildXLeft(currentPosition);
            startXCenter = (int) getChildXCenter(currentPosition);
            startXRight = (int) getChildXRight(currentPosition);

            if (tabStrip.getChildAt(position + 1) != null) {

                endXLeft = (int) getChildXLeft(position + 1);
                endXCenter = (int) getChildXCenter(position + 1);
                endXRight = (int) getChildXRight(position + 1);

            }else{
                endXLeft = (int) getChildXLeft(position);
                endXCenter = (int) getChildXCenter(position);
                endXRight = (int) getChildXRight(position);
            }

            if(animatedIndicator != null){
                animatedIndicator.setIntValues(startXLeft, endXLeft,
                        startXCenter, endXCenter,
                        startXRight, endXRight);
                animatedIndicator.setCurrentPlayTime((long) (positionOffset * (int) animatedIndicator.getDuration()));
            }

        }

        if (positionOffset == 0) {
            currentPosition = position;
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}
