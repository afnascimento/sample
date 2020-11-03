package com.unilever.julia.components;

import android.graphics.Rect;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CoordinatedAttachedBehavior extends CoordinatorLayout.Behavior<CoordinatedBaseView> {

    private static final boolean SNACKBAR_BEHAVIOR_ENABLED = true;
    //static {
    //    SNACKBAR_BEHAVIOR_ENABLED = Build.VERSION.SDK_INT >= 11;
    //}

    private Rect mTmpRect;

    CoordinatedAttachedBehavior() {
    }

    @Override
    public boolean layoutDependsOn(@NotNull CoordinatorLayout parent, @NotNull CoordinatedBaseView child, @NotNull View dependency) {
        return SNACKBAR_BEHAVIOR_ENABLED && dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NotNull CoordinatorLayout parent, @NotNull CoordinatedBaseView child, @NotNull View dependency) {
        if (dependency instanceof Snackbar.SnackbarLayout) {
            this.updateFabTranslationForSnackbar(parent, child, dependency);
        } else if(dependency instanceof AppBarLayout) {
            this.updateFabVisibility(parent, (AppBarLayout)dependency, child);
        }
        return false;
    }

    @Override
    public void onDependentViewRemoved(@NotNull CoordinatorLayout parent, @NotNull CoordinatedBaseView child, @NotNull View dependency) {
        if (dependency instanceof Snackbar.SnackbarLayout && child.getTranslationY() != 0.0F) {
            ViewCompat.animate(child)
                    .translationY(0.0F)
                    .scaleX(1.0F)
                    .scaleY(1.0F)
                    .alpha(1.0F)
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .setListener(null);
        }
    }

    private boolean updateFabVisibility(CoordinatorLayout parent, AppBarLayout appBarLayout, CoordinatedBaseView child) {
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)child.getLayoutParams();
        if (lp.getAnchorId() != appBarLayout.getId()) {
            return false;
        } else {
            if (this.mTmpRect == null) {
                this.mTmpRect = new Rect();
            }

            Rect rect = this.mTmpRect;
            CoordinatorBehaviorUtils.ViewGroupUtils.getDescendantRect(parent, appBarLayout, rect);
            if (rect.bottom <= getMinimumHeightForVisibleOverlappingContent(appBarLayout)) {
                child.hide();

                if (child.getOnChangeVisibilityListener() != null)
                    child.getOnChangeVisibilityListener().onHide();
            } else {
                child.show();

                if (child.getOnChangeVisibilityListener() != null)
                    child.getOnChangeVisibilityListener().onShow();
            }

            return true;
        }
    }

    private int getMinimumHeightForVisibleOverlappingContent(AppBarLayout appBarLayout) {
        int minHeight = ViewCompat.getMinimumHeight(appBarLayout);
        if (minHeight != 0) {
            return minHeight * 2;
        } else {
            int childCount = appBarLayout.getChildCount();
            return childCount >= 1?ViewCompat.getMinimumHeight(appBarLayout.getChildAt(childCount - 1)) * 2 :0;
        }
    }

    private void updateFabTranslationForSnackbar(CoordinatorLayout parent, CoordinatedBaseView child, View dependency) {
        if (child.getVisibility() == View.VISIBLE) {
            float translationY = this.getFabTranslationYForSnackbar(parent, child);
            child.setTranslationY(translationY);
        }
    }

    private float getFabTranslationYForSnackbar(CoordinatorLayout parent, CoordinatedBaseView child) {
        float minOffset = 0.0F;
        List dependencies = parent.getDependencies(child);
        int i = 0;

        for(int z = dependencies.size(); i < z; ++i) {
            View view = (View)dependencies.get(i);
            if(view instanceof Snackbar.SnackbarLayout && parent.doViewsOverlap(child, view)) {
                minOffset = Math.min(minOffset, view.getTranslationY() - (float)view.getHeight());
            }
        }

        return minOffset;
    }

    public boolean onLayoutChild(@NotNull CoordinatorLayout parent, @NotNull CoordinatedBaseView child, int layoutDirection) {
        List dependencies = parent.getDependencies(child);
        int i = 0;

        for (int count = dependencies.size(); i < count; ++i) {
            View dependency = (View)dependencies.get(i);
            if(dependency instanceof AppBarLayout && this.updateFabVisibility(parent, (AppBarLayout)dependency, child)) {
                break;
            }
        }

        parent.onLayoutChild(child, layoutDirection);
        this.offsetIfNeeded(parent, child);
        return true;
    }

    private void offsetIfNeeded(CoordinatorLayout parent, CoordinatedBaseView child) {
        Rect padding = new Rect(child.getPaddingLeft(), child.getPaddingTop(), child.getPaddingRight(), child.getPaddingBottom());
        if(padding.centerX() > 0 && padding.centerY() > 0) {
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)child.getLayoutParams();
            int offsetTB = 0;
            int offsetLR = 0;
            if (child.getRight() >= parent.getWidth() - lp.rightMargin) {
                offsetLR = padding.right;
            } else if (child.getLeft() <= lp.leftMargin) {
                offsetLR = -padding.left;
            }

            if (child.getBottom() >= parent.getBottom() - lp.bottomMargin) {
                offsetTB = padding.bottom;
            } else if (child.getTop() <= lp.topMargin) {
                offsetTB = -padding.top;
            }

            child.offsetTopAndBottom(offsetTB);
            child.offsetLeftAndRight(offsetLR);
        }
    }
}