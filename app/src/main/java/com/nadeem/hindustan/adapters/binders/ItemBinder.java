package com.nadeem.hindustan.adapters.binders;

/**
 * Created by Mohd Ikram on 06-02-2018.
 */

public interface ItemBinder<T> {
    int getLayoutRes(T model);

    int getBindingVariable(T model);
}
