package ir.greencode.advicelawAndroid.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ir.greencode.advicelawAndroid.R;


public class SwipeButton extends RelativeLayout {
    private ImageView slidingButton;
    private float initialX;
    private boolean active;
    private int initialButtonWidth;
    private TextView centerText;

    private Drawable disabledDrawable;
    private Drawable enabledDrawable;
    ChangeState myListener;
    public interface ChangeState{
      void   onCallActive();
      void  onCallCancel();
    }
    public void onChangeListener(ChangeState changeState){
        myListener = changeState;
    }
    public SwipeButton(Context context) {
        super(context);

        init(context, null, -1, -1);
    }

    public SwipeButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs, -1, -1);
    }

    public SwipeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs, defStyleAttr, -1);
    }

    @TargetApi(21)
    public SwipeButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }
    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

            RelativeLayout background = new RelativeLayout(context);
            LayoutParams layoutParamsView = new LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParamsView.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

            background.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_rounded));

            addView(background, layoutParamsView);

        final TextView centerText = new TextView(context);
        this.centerText = centerText;

        LayoutParams layoutParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        centerText.setText("به راست بکشید"); //add any text you need
        centerText.setTextColor(getResources().getColor(R.color.white));
        centerText.setPadding(35, 35, 35, 35);
        background.addView(centerText, layoutParams);


        final ImageView swipeButton = new ImageView(context);
        this.slidingButton = swipeButton;
        disabledDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_call_white_24dp);
        enabledDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_call_end_white_24dp);
        slidingButton.setImageDrawable(disabledDrawable);
        slidingButton.setPadding(40, 40, 40, 40);

        LayoutParams layoutParamsButton = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        layoutParamsButton.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        layoutParamsButton.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        swipeButton.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_button));
        swipeButton.setImageDrawable(disabledDrawable);
        addView(swipeButton, layoutParamsButton);
        setOnTouchListener(getButtonTouchListener());

    }
    private void moveButtonBack() {
        final ValueAnimator positionAnimator =
                ValueAnimator.ofFloat(slidingButton.getX(), 0);
        positionAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        positionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float x = (Float) positionAnimator.getAnimatedValue();
                slidingButton.setX(x);
            }
        });

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(
                centerText, "alpha", 1);

        positionAnimator.setDuration(200);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimator, positionAnimator);
        animatorSet.start();
    }
    private void collapseButton() {
        final ValueAnimator widthAnimator = ValueAnimator.ofInt(
                slidingButton.getWidth(),
                initialButtonWidth);

        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams params =  slidingButton.getLayoutParams();
                params.width = (Integer) widthAnimator.getAnimatedValue();
                slidingButton.setLayoutParams(params);
            }
        });

        widthAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                active = false;
                LayoutParams layoutParamsButton = new LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);


                slidingButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_button));

                slidingButton.setImageDrawable(disabledDrawable);
                myListener.onCallCancel();
            }
        });

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(
                centerText, "alpha", 1);

        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.playTogether(objectAnimator, widthAnimator);
        animatorSet.start();
    }
    private void expandButton() {
        final ValueAnimator positionAnimator =
                ValueAnimator.ofFloat(slidingButton.getX(),0);
        positionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float x = (Float) positionAnimator.getAnimatedValue();
                slidingButton.setX(x);
            }
        });


        final ValueAnimator widthAnimator = ValueAnimator.ofInt(
                slidingButton.getWidth(),
                getWidth());

        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams params = slidingButton.getLayoutParams();
                params.width = (Integer) widthAnimator.getAnimatedValue();
                slidingButton.setLayoutParams(params);
            }
        });


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                active = true;



                slidingButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_button_red));
                slidingButton.setImageDrawable(enabledDrawable);
                myListener.onCallActive();
            }
        });

        animatorSet.playTogether(positionAnimator, widthAnimator);
        animatorSet.start();
    }
    private OnTouchListener getButtonTouchListener() {
        return new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        moveAction(event);
                        //Movement logic here
                        return true;
                    case MotionEvent.ACTION_UP:
                        ActionUp(event);
                        return true;
                }

                return false;
            }
        };
    }

    private void ActionUp(MotionEvent event) {
        if (active) {
            collapseButton();
        } else {
            initialButtonWidth = slidingButton.getWidth();

            if (slidingButton.getX() + slidingButton.getWidth() > getWidth() * 0.85) {
                expandButton();
            } else {
                moveButtonBack();
            }
        }
    }

    private void moveAction(MotionEvent event){
        if (initialX == 0) {
            initialX = slidingButton.getX();
        }
        if (event.getX() > initialX + slidingButton.getWidth() / 2 &&
                event.getX() + slidingButton.getWidth() / 2 < getWidth()) {
            slidingButton.setX(event.getX() - slidingButton.getWidth() / 2);
            centerText.setAlpha(1 - 1.3f * (slidingButton.getX() + slidingButton.getWidth()) / getWidth());
        }

        if  (event.getX() + slidingButton.getWidth() / 2 > getWidth() &&
                slidingButton.getX() + slidingButton.getWidth() / 2 < getWidth()) {
            slidingButton.setX(getWidth() - slidingButton.getWidth());
        }

        if  (event.getX() < slidingButton.getWidth() / 2 &&
                slidingButton.getX() > 0) {
            slidingButton.setX(0);
        }
    }

}
