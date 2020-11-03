package com.unilever.julia.ui.component;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

public interface IAutoCompleteAdapter {//<T extends IAutoCompleteModel> {

    View getView(@NonNull IAutoCompleteModel model, @Nullable View convertView, @NonNull ViewGroup parent);
}
