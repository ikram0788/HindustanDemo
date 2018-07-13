package com.nadeem.hindustan.adapters.binders;

/**
 * Created by Mohd Ikram on 06-02-2018.
 */

public abstract class ConditionalDataBinder<T> extends ItemBinderBase<T>
{
    public ConditionalDataBinder(int bindingVariable, int layoutId)
    {
        super(bindingVariable, layoutId);
    }

    public abstract boolean canHandle(T model);

    public  boolean canHandle(int layoutId)
    {
        return this.layoutId == layoutId;
    }
}