package com.wang.avi.indicator;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by Jack on 2015/10/16.
 */
public class BallPulseIndicator extends BaseIndicatorController{

    public static final float SCALE=1.0f;

    //控制每个圆的scale x ,y
    private float[] scaleFloats=new float[]{SCALE,
            SCALE,
            SCALE};



    @Override
    public void draw(Canvas canvas, Paint paint) {
        float circleSpacing=4;
        //计算小圆点的半径
        float radius=(Math.min(getWidth(),getHeight())-circleSpacing*2)/6;
        //计算第一个点 中心点的 x
        float x = getWidth()/ 2-(radius*2+circleSpacing);
        //计算第一个点 中心点的 y
        float y=getHeight() / 2;
        for (int i = 0; i < 3; i++) {
            canvas.save();//为了保存上一个绘制的点
            //每绘制一个点,需提前移动canvas 移动的距离等于两个圆心之间的距离 (radius*2 + circleSpacing)
            float translateX=x+(radius*2)*i+circleSpacing*i;
            canvas.translate(translateX, y);
            //初始化的时候保证圆圈 是完整没有scale的,在执行createAnimation动画值变化的每一刻addUpdateListener,都重新调整每个圆圈的大小
            canvas.scale(scaleFloats[i], scaleFloats[i]);
            //画圆
            canvas.drawCircle(0, 0, radius, paint);
            canvas.restore();//为了保存上一个绘制的点
        }
    }

    @Override
    public void createAnimation() {
        int[] delays=new int[]{120,240,360};
        for (int i = 0; i < 3; i++) {
            final int index=i;

            ValueAnimator scaleAnim=ValueAnimator.ofFloat(1,0.3f,1);
            scaleAnim.setDuration(750);
            scaleAnim.setRepeatCount(-1);//循环
            scaleAnim.setStartDelay(delays[i]);
            scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //每次值value值变化的时候 重设对应 位置圆的scale值
                    scaleFloats[index] = (float) animation.getAnimatedValue();
                    postInvalidate();//通知重绘
                }
            });
            scaleAnim.start();
        }
    }

}
