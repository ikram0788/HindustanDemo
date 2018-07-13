package com.nadeem.hindustan.handlers;

import android.view.View;

/**
 * Created by Mohd Ikram on 21-09-2017.
 */

public interface RecyclerChieldClickHandler<T> {
    void onClick(T viewModel, View v, int position);
}
