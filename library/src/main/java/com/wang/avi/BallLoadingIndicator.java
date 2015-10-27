package com.wang.avi;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.wang.avi.indicator.BaseIndicatorController;

/**
 * Created by TuYong N1007
 * On 2015/10/27
 * At 11:34
 */
public class BallLoadingIndicator extends BaseIndicatorController {

    float[] mTranslationY = new float[3];
    int ballMarging = 5;

    @Override
    public void draw(Canvas canvas, Paint paint) {
        int radius = (getWidth() - ballMarging*2)/12;
        int tranX = 2*radius + ballMarging;
        int x = getWidth()/ 2-(radius*2+ballMarging);//起始点为包括了坐标的margin
        for(int i =0 ; i < 3; i++){
            canvas.save();
            canvas.translate(tranX*i + x,mTranslationY[i]);
            canvas.drawCircle(0,0,radius,paint);
            canvas.restore();
        }
    }

    @Override
    public void createAnimation() {
        int height = (getWidth() - ballMarging*2)/6;
        float[] delays = new float[]{80,180,280};
        for(int i =0 ; i < 3; i++) {
            final int index = i;
            ValueAnimator tranY = ValueAnimator.ofFloat(getHeight()/2,getHeight()/2 - height*2,getHeight()/2);
            tranY.setDuration(600);
            tranY.setStartDelay((long) delays[i]);
            tranY.setRepeatCount(-1);
            tranY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mTranslationY[index] = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            tranY.start();
        }
    }
}
