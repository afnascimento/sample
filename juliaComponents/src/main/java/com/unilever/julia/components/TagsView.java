package com.unilever.julia.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class TagsView extends RelativeLayout {

    public TagsView(Context context) {
        this(context, null);
    }

    public TagsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private TextView tvItemSelValue;

    private View btnDelete;

    public TagsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.tags_view, this);

        tvItemSelValue = findViewById(R.id.tvItemSelValue);
        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onDeleteClick();
                }
            }
        });
    }

    public void setLabel(@NonNull CharSequence text) {
        tvItemSelValue.setText(text);
    }

    public interface Listener {
        void onDeleteClick();
    }

    private Listener mListener;

    public void setListener(@NonNull Listener listener) {
        this.mListener = listener;
    }
}