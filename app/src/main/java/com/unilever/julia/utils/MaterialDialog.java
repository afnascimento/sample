package com.unilever.julia.utils;

import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;
import android.view.View;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andre.nascimento on 27/10/2017.
 */

public class MaterialDialog {

    public interface OnClickListener {
        void onClick(@NonNull DialogInterface dialog);
    }

    public interface OnSingleItemListener {
        void onClick(@NonNull DialogInterface dialog, int selected);
    }

    private final Context context;

    private View viewLayout;

    private String title;

    private CharSequence message;

    private Integer styleMessage;

    private String textNegative;

    private String textPositive;

    private OnClickListener listenerPositive;

    private OnClickListener listenerNegative;

    private OnSingleItemListener singleItemListener;

    private List<String> singleItems = new ArrayList<>();

    private int selectedSingle;

    public MaterialDialog setSingleItems(List<String> items, String selected) {
        this.singleItems.clear();
        this.singleItems.addAll(items);
        this.selectedSingle = 0;
        if (!singleItems.isEmpty()) {
            try {
                selectedSingle = singleItems.indexOf(selected);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public MaterialDialog(Context context) {
        this.context = context;
    }

    public MaterialDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public MaterialDialog setMessage(@Nullable String message) {
        this.message = message;
        return this;
    }

    public MaterialDialog setMessage(@Nullable CharSequence message) {
        this.message = message;
        return this;
    }

    public MaterialDialog setStyleMessage(@StyleRes int styleRes) {
        this.styleMessage = styleRes;
        return this;
    }

    public MaterialDialog setNegativeButton(String textNegative) {
        this.textNegative = textNegative;
        return this;
    }

    public MaterialDialog setNegativeButton(String textNegative, OnClickListener listener) {
        this.textNegative = textNegative;
        this.listenerNegative = listener;
        return this;
    }

    public MaterialDialog setPositiveButton(String textPositive) {
        this.textPositive = textPositive;
        return this;
    }

    public MaterialDialog setPositiveButton(String textPositive, OnClickListener listener) {
        this.textPositive = textPositive;
        this.listenerPositive = listener;
        return this;
    }

    public MaterialDialog setPositiveButton(String textPositive, OnSingleItemListener listener) {
        this.textPositive = textPositive;
        this.singleItemListener = listener;
        return this;
    }

    public MaterialDialog setView(View viewLayout) {
        this.viewLayout = viewLayout;
        return this;
    }

    public AlertDialog show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (StringUtils.isNotBlank(title)) {
            builder.setTitle(title);
        }

        if (StringUtils.isNotBlank(message)) {
            builder.setMessage(message);
        }

        if (viewLayout != null) {
            builder.setView(viewLayout);
        }

        if (!singleItems.isEmpty()) {
            String [] items = singleItems.toArray(new String[singleItems.size()]);
            builder.setSingleChoiceItems(items, selectedSingle, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selectedSingle = which;
                }
            });
        }

        if (StringUtils.isNotBlank(textNegative)) {
            builder.setNegativeButton(textNegative, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (listenerNegative == null) {
                        dialog.dismiss();
                    } else {
                        listenerNegative.onClick(dialog);
                    }
                }
            });
        }

        if (StringUtils.isNotBlank(textPositive)) {
            builder.setPositiveButton(textPositive, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (singleItemListener != null) {
                        singleItemListener.onClick(dialog, selectedSingle);
                    } else if (listenerPositive == null) {
                        dialog.dismiss();
                    } else {
                        listenerPositive.onClick(dialog);
                    }
                }
            });
        }

        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        return alertDialog;
    }
}
