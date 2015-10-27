package com.wang.avi.indicator;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by Jack on 2015/10/19.
 * 这个方法略叼,比之前的用Span实现的容易理解的多
 */
public class BallPulseSyncIndicator extends BaseIndicatorController {

    float[] translateYFloats=new float[3];

    @Override
    public void draw(Canvas canvas, Paint paint) {
        float circleSpacing=4;
        float radius=(getWidth()-circleSpacing*2)/12;
        float x = getWidth()/ 2-(radius*2+circleSpacing);
        for (int i = 0; i < 3; i++) {
            canvas.save();
            float translateX=x+(radius*2)*i+circleSpacing*i;
            canvas.translate(translateX, translateYFloats[i]);
            canvas.drawCircle(0, 0, radius, paint);
            canvas.restore();
        }
    }

    @Override
    public void createAnimation() {
        float circleSpacing=4;
        float radius=(getWidth()-circleSpacing*2)/6;
        int[] delays=new int[]{70,180,290};
        for (int i = 0; i < 3; i++) {
            final int index=i;
            //控制Y距离变化的范围
            ValueAnimator scaleAnim=ValueAnimator.ofFloat(getHeight()/2,getHeight()/2-radius*2,getHeight()/2);
            scaleAnim.setDuration(600);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay(delays[i]);
            scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    translateYFloats[index] = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            scaleAnim.start();
        }
    }

}
