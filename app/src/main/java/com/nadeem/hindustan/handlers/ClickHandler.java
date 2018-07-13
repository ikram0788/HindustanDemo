package com.nadeem.hindustan.handlers;

import android.view.View;

/**
 * Created by Mohd Ikram on 21-09-2017.
 */

public interface ClickHandler<T> {
    void onClick(T viewModel, View v);
    //void onClick11(T viewModel, View v);
}
