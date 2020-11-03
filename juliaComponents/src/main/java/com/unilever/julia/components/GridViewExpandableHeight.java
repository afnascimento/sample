/*
 * Odoo, Open Source Management Solution
 * Copyright (C) 2012-today Odoo SA (<http:www.odoo.com>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http:www.gnu.org/licenses/>
 *
 */

package com.unilever.julia.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class GridViewExpandableHeight extends GridView {

    //private boolean expanded = false;

    public GridViewExpandableHeight(Context context) {
        super(context);
    }

    public GridViewExpandableHeight(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViewExpandableHeight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK, MeasureSpec.AT_MOST));
        getLayoutParams().height = getMeasuredHeight();

//        if (isExpanded()) {
//            int expandSpec = MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK,
//                    MeasureSpec.AT_MOST);
//            super.onMeasure(widthMeasureSpec, expandSpec);
//
//            ViewGroup.LayoutParams params = getLayoutParams();
//            params.height = getMeasuredHeight();
//        } else {
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        }
    }

    /*
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isExpanded() {
        return expanded;
    }
    */
}
