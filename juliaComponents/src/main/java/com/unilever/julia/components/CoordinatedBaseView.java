package com.unilever.julia.components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout.AttachedBehavior;
import androidx.core.view.ViewCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

public class CoordinatedBaseView extends RelativeLayout implements AttachedBehavior {

    private boolean mIsHiding;

    private OnChangeVisibilityListener onChangeVisibilityListener;

    public CoordinatedBaseView(Context context) {
        super(context);
    }

    public CoordinatedBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CoordinatedBaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @NonNull
    @Override
    public CoordinatorLayout.Behavior getBehavior() {
        return new CoordinatedAttachedBehavior();
    }

    public void hide() {
        if (!this.mIsHiding && this.getVisibility() == VISIBLE) {
            if (ViewCompat.isLaidOut(this) && !this.isInEditMode()) {
                this.animate()
                        .scaleX(0.0F)
                        .scaleY(0.0F)
                        .alpha(0.0F)
                        .setDuration(200L)
                        .setInterpolator(new FastOutSlowInInterpolator())
                        .setListener(new AnimatorListenerAdapter() {
                            public void onAnimationStart(Animator animation) {
                                CoordinatedBaseView.this.mIsHiding = true;
                                CoordinatedBaseView.this.setVisibility(VISIBLE);
                            }

                            public void onAnimationCancel(Animator animation) {
                                CoordinatedBaseView.this.mIsHiding = false;
                            }

                            public void onAnimationEnd(Animator animation) {
                                CoordinatedBaseView.this.mIsHiding = false;
                                CoordinatedBaseView.this.setVisibility(GONE);
                            }
                        });
            } else {
                this.setVisibility(GONE);
            }
        }
    }

    public void show() {
        if (this.getVisibility() != VISIBLE) {
            if (ViewCompat.isLaidOut(this) && !this.isInEditMode()) {
                this.setAlpha(0.0F);
                this.setScaleY(0.0F);
                this.setScaleX(0.0F);
                this.animate().scaleX(1.0F).scaleY(1.0F).alpha(1.0F).setDuration(200L).setInterpolator(new FastOutSlowInInterpolator()).setListener(new AnimatorListenerAdapter() {
                    public void onAnimationStart(Animator animation) {
                        CoordinatedBaseView.this.setVisibility(VISIBLE);
                    }
                });
            } else {
                this.setVisibility(VISIBLE);
                this.setAlpha(1.0F);
                this.setScaleY(1.0F);
                this.setScaleX(1.0F);
            }
        }
    }

    public interface OnChangeVisibilityListener {
        void onHide();
        void onShow();
    }

    public void setOnChangeVisibilityListener(OnChangeVisibilityListener onChangeVisibilityListener) {
        this.onChangeVisibilityListener = onChangeVisibilityListener;
    }

    public OnChangeVisibilityListener getOnChangeVisibilityListener() {
        return onChangeVisibilityListener;
    }
}