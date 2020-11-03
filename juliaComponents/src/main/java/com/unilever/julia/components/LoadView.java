package com.unilever.julia.components;

import android.content.Context;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.unilever.julia.components.enums.IconEnums;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by andre.nascimento on 20/10/2017.
 */
public class LoadView extends ConstraintLayout {

    public LoadView(Context context) {
        this(context, null);
    }

    public LoadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private ProgressWheel loading;
    private ViewGroup contentAlert;
    private ImageView image;
    private JuliaTextViewIcon textViewIcon;
    private TextView title;
    private TextView message;
    private Button button;

    public LoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.julia_load_view, this);
        loading = findViewById(R.id.pgLoad);
        contentAlert = findViewById(R.id.contentAlert);
        image = findViewById(R.id.imgError);
        textViewIcon = findViewById(R.id.textViewIcon);
        title = findViewById(R.id.tvTitle);
        message = findViewById(R.id.tvMessage);
        button = findViewById(R.id.btnRefresh);
    }

    public void show() {
        setVisibility(VISIBLE);
    }

    public void hide() {
        setVisibility(GONE);
    }

    public void showLoading() {
        show();
        loading.setVisibility(VISIBLE);
        contentAlert.setVisibility(GONE);
    }

    public void hideLoading() {
        show();
        loading.setVisibility(GONE);
        contentAlert.setVisibility(VISIBLE);
    }

    private void updateView(@DrawableRes int imageRes,
                            @Nullable String hexIcon,
                            @NonNull CharSequence textTitle,
                            @NonNull CharSequence textMessage,
                            @Nullable CharSequence textButton,
                            @Nullable final OnClickListener listener) {
        if (StringUtils.isNotEmpty(hexIcon)) {
            textViewIcon.setIcon(hexIcon);
            textViewIcon.setVisibility(View.VISIBLE);
            image.setVisibility(View.GONE);
        } else {
            image.setImageResource(imageRes);
            image.setVisibility(View.VISIBLE);
            textViewIcon.setVisibility(View.GONE);
        }

        if (StringUtils.isEmpty(textTitle)) {
            title.setVisibility(GONE);
        } else {
            title.setText(textTitle);
            title.setVisibility(VISIBLE);
        }
        if (StringUtils.isEmpty(textMessage)) {
            message.setVisibility(GONE);
        } else {
            message.setText(textMessage);
            message.setVisibility(VISIBLE);
        }
        if (StringUtils.isNotBlank(textButton)) {
            button.setText(textButton);
        }
        if (listener == null) {
            button.setVisibility(View.GONE);
        } else {
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRefreshViewError();
                }
            });
        }
        hideLoading();
    }

    private void updateView(@DrawableRes int imageRes, @NonNull CharSequence textTitle, @NonNull CharSequence textMessage) {
        updateView(imageRes, null, textTitle, textMessage, null, null);
    }

    private void updateView(@DrawableRes int imageRes, @NonNull CharSequence textTitle, @NonNull CharSequence textMessage, @NonNull final OnClickListener listener) {
        updateView(imageRes, null, textTitle, textMessage, null, listener);
    }

    public void showOffline(@NonNull final OnClickListener listener) {
        showOffline(
                getContext().getText(R.string.julia_load_offline_title),
                getContext().getText(R.string.julia_load_offline_text),
                listener
        );
    }

    public void showOffline(@NonNull CharSequence textTitle, @NonNull CharSequence textMessage, @NonNull final OnClickListener listener) {
        updateView(
                R.drawable.ic_julia_load_wifi_off,
                textTitle,
                textMessage,
                listener
        );
    }

    public void showError(@NonNull CharSequence textTitle, @NonNull CharSequence textMessage, @NonNull final OnClickListener listener) {
        updateView(
                R.drawable.ic_julia_load_cancel,
                textTitle,
                textMessage,
                listener
        );
    }

    public void showEmpty(@NonNull CharSequence textTitle, @NonNull CharSequence textMessage, @NonNull CharSequence textButton, @NonNull final OnClickListener listener) {
        updateView(
                R.drawable.ic_julia_load_empty,
                null,
                textTitle,
                textMessage,
                textButton,
                listener
        );
    }

    public void showEmpty(@NonNull CharSequence textTitle, @NonNull CharSequence textMessage) {
        updateView(
                R.drawable.ic_julia_load_empty,
                textTitle,
                textMessage
        );
    }

    public void showCustom(@NonNull IconEnums icon,
                           @NonNull CharSequence textTitle,
                           @NonNull CharSequence textMessage) {
        updateView(
                0,
                icon.getIconHexa(),
                textTitle,
                textMessage,
                null,
                null
        );
    }

    public void showCustom(@DrawableRes int imageRes,
                           @Nullable String hexIcon,
                           @NonNull CharSequence textTitle,
                           @NonNull CharSequence textMessage,
                           @Nullable CharSequence textButton,
                           @Nullable final OnClickListener listener) {
        updateView(
                imageRes,
                hexIcon,
                textTitle,
                textMessage,
                textButton,
                listener
        );
    }

    public interface OnClickListener {
        void onRefreshViewError();
    }
}

