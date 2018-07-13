package com.nadeem.hindustan.adapters.binders;

/**
 * Created by Mohd Ikram on 06-02-2018.
 */

public class ItemBinderBase<T> implements ItemBinder<T>
{
    protected final int bindingVariable;
    protected final int layoutId;

    public ItemBinderBase(int bindingVariable, int layoutId)
    {
        this.bindingVariable = bindingVariable;
        this.layoutId = layoutId;
    }

    public int getLayoutRes(T model)
    {
        return layoutId;
    }

    public int getBindingVariable(T model)
    {
        return bindingVariable;
    }
}