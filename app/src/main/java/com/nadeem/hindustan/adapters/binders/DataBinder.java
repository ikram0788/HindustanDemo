package com.nadeem.hindustan.adapters.binders;

/**
 * Created by Mohd Ikram on 06-02-2018.
 */

public class DataBinder<T> extends ConditionalDataBinder<T> {
    public DataBinder(int bindingVariable, int layoutId) {
        super(bindingVariable, layoutId);
    }

    @Override
    public boolean canHandle(T model) {
        return true;
    }
}