package com.unilever.julia.components;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

class CoordinatorBehaviorUtils {

    static class ViewGroupUtils {
        private static final ViewGroupUtils.ViewGroupUtilsImpl IMPL;

        ViewGroupUtils() {
        }

        static void offsetDescendantRect(ViewGroup parent, View descendant, Rect rect) {
            IMPL.offsetDescendantRect(parent, descendant, rect);
        }

        static void getDescendantRect(ViewGroup parent, View descendant, Rect out) {
            out.set(0, 0, descendant.getWidth(), descendant.getHeight());
            offsetDescendantRect(parent, descendant, out);
        }

        static {
            int version = Build.VERSION.SDK_INT;
            if(version >= 11) {
                IMPL = new ViewGroupUtils.ViewGroupUtilsImplHoneycomb();
            } else {
                IMPL = new ViewGroupUtils.ViewGroupUtilsImplBase();
            }
        }

        public static class ViewGroupUtilsImplHoneycomb implements ViewGroupUtils.ViewGroupUtilsImpl {
            private ViewGroupUtilsImplHoneycomb() {
            }

            public void offsetDescendantRect(ViewGroup parent, View child, Rect rect) {
                ViewGroupUtilsHoneycomb.offsetDescendantRect(parent, child, rect);
            }
        }

        public static class ViewGroupUtilsImplBase implements ViewGroupUtils.ViewGroupUtilsImpl {
            private ViewGroupUtilsImplBase() {
            }

            public void offsetDescendantRect(ViewGroup parent, View child, Rect rect) {
                parent.offsetDescendantRectToMyCoords(child, rect);
            }
        }

        public interface ViewGroupUtilsImpl {
            void offsetDescendantRect(ViewGroup var1, View var2, Rect var3);
        }
    }

    public static class ViewGroupUtilsHoneycomb {
        private static final ThreadLocal<Matrix> sMatrix = new ThreadLocal<>();
        private static final ThreadLocal<RectF> sRectF = new ThreadLocal<>();
        private static final Matrix IDENTITY = new Matrix();

        ViewGroupUtilsHoneycomb() {
        }

        static void offsetDescendantRect(ViewGroup group, View child, Rect rect) {
            Matrix m = sMatrix.get();
            if(m == null) {
                m = new Matrix();
                sMatrix.set(m);
            } else {
                m.set(IDENTITY);
            }

            offsetDescendantMatrix(group, child, m);
            RectF rectF = sRectF.get();
            if(rectF == null) {
                rectF = new RectF();
            }

            rectF.set(rect);
            m.mapRect(rectF);
            rect.set((int)(rectF.left + 0.5F), (int)(rectF.top + 0.5F), (int)(rectF.right + 0.5F), (int)(rectF.bottom + 0.5F));
        }

        static void offsetDescendantMatrix(ViewParent target, View view, Matrix m) {
            ViewParent parent = view.getParent();
            if (parent instanceof View && parent != target) {
                View vp = (View)parent;
                offsetDescendantMatrix(target, vp, m);
                m.preTranslate((float)(-vp.getScrollX()), (float)(-vp.getScrollY()));
            }

            m.preTranslate((float)view.getLeft(), (float)view.getTop());
            if(!view.getMatrix().isIdentity()) {
                m.preConcat(view.getMatrix());
            }
        }
    }
}
