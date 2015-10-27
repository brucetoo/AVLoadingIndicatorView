package com.wang.avi.indicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.animation.LinearInterpolator;

import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by Jack on 2015/10/19.
 */
public class BallTrianglePathIndicator extends BaseIndicatorController {

    float[] translateX=new float[3],translateY=new float[3];

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < 3; i++) {
            canvas.save();
            canvas.translate(translateX[i], translateY[i]);
            canvas.drawCircle(0, 0, getWidth() / 10, paint);
            canvas.restore();
        }
    }

    @Override
    public void createAnimation() {
        float startX=getWidth()/5;
        float startY=getWidth()/5;
        /**
         * 这个动画的效果看起来特别复杂,其实只是在x,y轴上移动的距离不同产生的效果
         * 画一个矩形来分析下发现很简单
         * ---------------------------
         * ------------A---------------
         * ---------------------------
         * ---------------------------
         * ---------------------------
         * ---------------------------
         * ---------------------------
         * ----C-----------------B----
         * ---------------------------
         * ---------------------------
         * A B C分别代表3个点,
         * A的运动轨迹就是 A->B->C 在根据A,B,C的坐标就能把运动的坐标点确定下来
         * B的运动轨迹就是 B->C->A 在根据A,B,C的坐标就能把运动的坐标点确定下来
         * C的运动轨迹就是 C->A->B 在根据A,B,C的坐标就能把运动的坐标点确定下来
         *
         */
        for (int i = 0; i < 3; i++) {
            final int index=i;
            //第一个点的运行距离 : 1/2,4/5,1/5,1/2 做一个循环  初始位置x为 1/2处
            ValueAnimator translateXAnim=ValueAnimator.ofFloat(getWidth()/2,getWidth()-startX,startX,getWidth()/2);
            if (i==1){
                //第二个点的运行距离 : 4/5,1/5,1/2,4/5 做一个循环  初始位置x为 4/5 处
                translateXAnim=ValueAnimator.ofFloat(getWidth()-startX,startX,getWidth()/2,getWidth()-startX);
            }else if (i==2){
                //第三个点的运行距离 : 1/5,1/2,4/5,1/5 做一个循环  初始位置x为 1/5 处
                translateXAnim=ValueAnimator.ofFloat(startX,getWidth()/2,getWidth()-startX,startX);
            }
            //第一个点的运行距离 : 1/5,4/5,4/5,1/5 做一个循环  初始位置y为 1/5处
            ValueAnimator translateYAnim=ValueAnimator.ofFloat(startY,getHeight()-startY,getHeight()-startY,startY);
            if (i==1){
                //第二个点的运行距离 : 4/5,4/5,1/5,4/5 做一个循环  初始位置y为 4/5处
                translateYAnim=ValueAnimator.ofFloat(getHeight()-startY,getHeight()-startY,startY,getHeight()-startY);
            }else if (i==2){
                //第三个个点的运行距离 : 4/5,1/5,4/5,4/5 做一个循环  初始位置y为 4/5处
                translateYAnim=ValueAnimator.ofFloat(getHeight()-startY,startY,getHeight()-startY,getHeight()-startY);
            }

            translateXAnim.setDuration(2000);
            translateXAnim.setInterpolator(new LinearInterpolator());
                translateXAnim.setRepeatCount(-1);
            translateXAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    translateX [index]= (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            translateXAnim.start();

            translateYAnim.setDuration(2000);
            translateYAnim.setInterpolator(new LinearInterpolator());
            translateYAnim.setRepeatCount(-1);
            translateYAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    translateY [index]= (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            translateYAnim.start();
        }
    }


}
