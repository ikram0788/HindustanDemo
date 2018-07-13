package com.nadeem.hindustan.adapters;

import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.nadeem.hindustan.BR;
import com.nadeem.hindustan.R;
import com.nadeem.hindustan.adapters.binders.ItemBinder;
import com.nadeem.hindustan.database.entities.Merchant;
import com.nadeem.hindustan.handlers.ClickHandler;
import com.nadeem.hindustan.handlers.LongClickHandler;
import com.nadeem.hindustan.handlers.RecyclerChieldClickHandler;

import java.lang.ref.WeakReference;

public class RecyclerPagedAdapter extends PagedListAdapter<Merchant, RecyclerPagedAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private static final int ITEM_MODEL = -124;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    private RecyclerPagedAdapter.WeakReferenceOnListChangedCallback onListChangedCallback;
    private ItemBinder<Merchant> itemBinder;
    private LayoutInflater inflater;
    private ClickHandler<Merchant> clickHandler;
    private RecyclerChieldClickHandler<Merchant> chieldClickHandler;
    private LongClickHandler<Merchant> longClickHandler;
    private ViewModel mViewModel;
    private boolean isHeaderEnabled;
    private boolean isFooterEnabled = false;
    private String hireDate = "";
    private TextView txtFooter;

    protected RecyclerPagedAdapter(@NonNull DiffUtil.ItemCallback<Merchant> diffCallback) {
        super(diffCallback);
    }

    protected RecyclerPagedAdapter(@NonNull AsyncDifferConfig<Merchant> config) {
        super(config);
    }

    public RecyclerPagedAdapter(ItemBinder itemBinder, @Nullable PagedList items) {
        super(DIFF_CALLBACK);
        this.itemBinder = itemBinder;
        this.onListChangedCallback = new RecyclerPagedAdapter.WeakReferenceOnListChangedCallback<>(this);
        submitList(items);
    }
    public void setViewModel(ViewModel viewModel) {
        mViewModel = viewModel;
    }


    @NonNull
    @Override
    public RecyclerPagedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(viewGroup.getContext());
        }
        ViewDataBinding binding = null;
        if (viewType == TYPE_HEADER) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                    R.layout.item_list_merchant, null, false);
        } else if (viewType == TYPE_FOOTER) {


        } else {
            binding = DataBindingUtil.inflate(inflater, viewType, viewGroup, false);
        }
        return new RecyclerPagedAdapter.ViewHolder(binding, viewType);
    }

    @Override
    public int getItemCount() {
        return (super.getItemCount() > 0 && isFooterEnabled) ? super.getItemCount() + 1 : super.getItemCount();

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        //final Guide item = items.get(position);
        if (getItemViewType(position) == TYPE_HEADER) {

        } else if (getItemViewType(position) == TYPE_FOOTER) {
        } else {
            final Merchant item = getItem(position);
            viewHolder.binding.setVariable(itemBinder.getBindingVariable(item), item);
            if (chieldClickHandler != null) {
                viewHolder.binding.setVariable(BR.position, position);
                viewHolder.binding.setVariable(BR.chieldClickHandler, chieldClickHandler);
            }
            if (mViewModel != null) {
                viewHolder.binding.setVariable(BR.viewModel, mViewModel);
                //viewHolder.binding.setVariable(BR.layoutManager, new LinearLayoutManager(viewHolder.binding.getRoot().getContext(), LinearLayoutManager.HORIZONTAL, false));
            }

            viewHolder.binding.getRoot().setTag(ITEM_MODEL, item);
            viewHolder.binding.getRoot().setOnClickListener(this);
            viewHolder.binding.getRoot().setOnLongClickListener(this);

        /*if (item instanceof Item) {
            Item summaryItem = (Item) item;
            if (serviceType != null && serviceType == (summaryItem.getServiceType())) {
                viewHolder.binding.getRoot().setTag(ITEM_MODEL, item);
//                summaryItem.setHeaderItem(true);
            } else {
                viewHolder.binding.getRoot().setTag(ITEM_HEADER, item);
                serviceType = summaryItem.getServiceType();
//                summaryItem.setHeaderItem(false);
            }
        }*/
            viewHolder.binding.executePendingBindings();
        }
    }

    @Override
    public void onClick(View v) {
        if (clickHandler != null) {
            Merchant item = (Merchant) v.getTag(ITEM_MODEL);
            clickHandler.onClick(item, v);
        }
    }


    @Override
    public boolean onLongClick(View v) {
        if (longClickHandler != null) {
            Merchant item = (Merchant) v.getTag(ITEM_MODEL);
            longClickHandler.onLongClick(item);
            return true;
        }
        return false;
    }

    public void setClickHandler(ClickHandler clickHandler) {
        this.clickHandler = clickHandler;
    }

    public void setLongClickHandler(LongClickHandler clickHandler) {
        this.longClickHandler = clickHandler;
    }

    public void setChieldClickHandler(RecyclerChieldClickHandler chieldClickHandler) {
        this.chieldClickHandler = chieldClickHandler;
    }

    @Override
    public int getItemViewType(int position) {
        try {

            if (isPositionHeader(position) && isHeaderEnabled) {
                return TYPE_HEADER;

            } else if (isPositionFooter(position) && isFooterEnabled) {
                return TYPE_FOOTER;
            }

            return itemBinder.getLayoutRes(getItem(position));
            //return TYPE_ITEM;

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return itemBinder.getLayoutRes(getItem(position));
    }

    public void setHeaderEnabled(boolean enabled) {
        isHeaderEnabled = enabled;
    }

    public void setFooterEnabled(boolean enabled) {
        isFooterEnabled = enabled;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position == getItemCount() - 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ViewDataBinding binding;

        ViewHolder(ViewDataBinding binding, int viewType) {
            super(binding.getRoot());
            if (viewType == TYPE_HEADER) {

            } else if (viewType == TYPE_FOOTER) {
                this.binding = binding;
            } else {
                this.binding = binding;
            }
        }
    }

    private static class WeakReferenceOnListChangedCallback<Guide> extends ObservableList.OnListChangedCallback {

        private final WeakReference<RecyclerPagedAdapter> adapterReference;

        public WeakReferenceOnListChangedCallback(RecyclerPagedAdapter bindingRecyclerViewAdapter) {
            this.adapterReference = new WeakReference<>(bindingRecyclerViewAdapter);
        }

        @Override
        public void onChanged(ObservableList sender) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemRangeChanged(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemRangeInserted(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemMoved(fromPosition, toPosition);
            }
        }

        @Override
        public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
            RecyclerView.Adapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.notifyItemRangeRemoved(positionStart, itemCount);
            }
        }
    }

    public static final DiffUtil.ItemCallback<Merchant> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Merchant>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Merchant oldUser, @NonNull Merchant newUser) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldUser.getId() == newUser.getId();
//                    return oldUser.hashCode() == newUser.hashCode();
                }

                @Override
                public boolean areContentsTheSame(
                        @NonNull Merchant oldUser, @NonNull Merchant newUser) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldUser.equals(newUser);
                }
            };

    public void clear() {
        //TODO
    }

    public void setPagedList(PagedList<Merchant> pagedList) {
        submitList(pagedList);
    }

}
