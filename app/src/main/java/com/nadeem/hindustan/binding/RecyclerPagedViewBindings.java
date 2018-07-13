package com.nadeem.hindustan.binding;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import com.nadeem.hindustan.adapters.RecyclerPagedAdapter;
import com.nadeem.hindustan.adapters.binders.ItemBinder;
import com.nadeem.hindustan.database.entities.Merchant;
import com.nadeem.hindustan.handlers.ClickHandler;
import com.nadeem.hindustan.handlers.LongClickHandler;
import com.nadeem.hindustan.handlers.RecyclerChieldClickHandler;

/**
 * Created by Mohd Ikram on 06-02-2018.
 */


public class RecyclerPagedViewBindings {
    private static final int KEY_HEADER = -122;
    private static final int KEY_ITEMS = -123;
    private static final int KEY_CLICK_HANDLER = -124;
    private static final int KEY_LONG_CLICK_HANDLER = -125;
    private static final int KEY_CHIELD_CLICK_HANDLER = -126;
    private static final int KEY_FOOTER = -127;

    @SuppressWarnings("unchecked")
    @BindingAdapter("pagedItems")
    public static <T> void setPagedList(RecyclerView recyclerView, LiveData<PagedList<Merchant>> items) {
        RecyclerPagedAdapter adapter = (RecyclerPagedAdapter) recyclerView.getAdapter();
        adapter.setFooterEnabled(true);
        if (adapter != null) {
            adapter.setPagedList(items.getValue());
        } else {
            recyclerView.setTag(KEY_ITEMS, items.getValue());
        }
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter("pagedItemViewBinder")
    public static <Merchant> void setPagedItemViewBinder(RecyclerView recyclerView, ItemBinder<Merchant> itemViewMapper) {
        PagedList<Merchant> items = (PagedList) recyclerView.getTag(KEY_ITEMS);
        ClickHandler<Merchant> clickHandler = (ClickHandler<Merchant>) recyclerView.getTag(KEY_CLICK_HANDLER);
        RecyclerChieldClickHandler<Merchant> chieldClickHandler = (RecyclerChieldClickHandler<Merchant>) recyclerView.getTag(KEY_CHIELD_CLICK_HANDLER);
        RecyclerPagedAdapter adapter = new RecyclerPagedAdapter(itemViewMapper, items);
        if (clickHandler != null) {
            adapter.setClickHandler(clickHandler);
        }
        if (chieldClickHandler != null) {
            adapter.setChieldClickHandler(chieldClickHandler);
        }
        recyclerView.setAdapter(adapter);
    }
    @SuppressWarnings("unchecked")
    @BindingAdapter("pagedClickHandler")
    public static <T> void setHandler(RecyclerView recyclerView, ClickHandler<T> handler) {
        RecyclerPagedAdapter adapter = (RecyclerPagedAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setClickHandler(handler);
        } else {
            recyclerView.setTag(KEY_CLICK_HANDLER, handler);
        }
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter("pagedLongClickHandler")
    public static <T> void setHandler(RecyclerView recyclerView, LongClickHandler<T> handler) {
        RecyclerPagedAdapter adapter = (RecyclerPagedAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setLongClickHandler(handler);
        } else {
            recyclerView.setTag(KEY_LONG_CLICK_HANDLER, handler);
        }
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter("pagedChieldClickHandler")
    public static <T> void setHandler(RecyclerView recyclerView, RecyclerChieldClickHandler<T> chieldClickHandler) {
        RecyclerPagedAdapter adapter = (RecyclerPagedAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setChieldClickHandler(chieldClickHandler);
        } else {
            recyclerView.setTag(KEY_CHIELD_CLICK_HANDLER, chieldClickHandler);
        }
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter("pagedItemViewBinder")
    public static <T> void setItemViewBinder(RecyclerView recyclerView, ItemBinder<T> itemViewMapper) {
        PagedList<Merchant> items = (PagedList<Merchant>) recyclerView.getTag(KEY_ITEMS);
        ClickHandler<T> clickHandler = (ClickHandler<T>) recyclerView.getTag(KEY_CLICK_HANDLER);
        RecyclerChieldClickHandler<T> chieldClickHandler = (RecyclerChieldClickHandler<T>) recyclerView.getTag(KEY_CHIELD_CLICK_HANDLER);
        RecyclerPagedAdapter adapter = new RecyclerPagedAdapter(itemViewMapper, items);
        if (clickHandler != null) {
            adapter.setClickHandler(clickHandler);
        }
        if (chieldClickHandler != null) {
            adapter.setChieldClickHandler(chieldClickHandler);
        }
        recyclerView.setAdapter(adapter);
    }

}