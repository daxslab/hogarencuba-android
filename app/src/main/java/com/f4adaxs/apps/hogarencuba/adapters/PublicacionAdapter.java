package com.f4adaxs.apps.hogarencuba.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.f4adaxs.apps.hogarencuba.R;
import com.f4adaxs.apps.hogarencuba.adapters.filter.PublicacionFilter;
import com.f4adaxs.apps.hogarencuba.databinding.ItemPublicacionBinding;
import com.f4adaxs.apps.hogarencuba.db.entity.Publicacion;
import com.f4adaxs.apps.hogarencuba.models.PublicacionModel;
import com.f4adaxs.apps.hogarencuba.util.ConnectionUtils;
import com.f4adaxs.apps.hogarencuba.util.FontManager;
import com.f4adaxs.apps.hogarencuba.widget.GlideImageLoader;

import java.util.List;
import java.util.Objects;

/**
 * Created by rigo on 2/15/18.
 */

public class PublicacionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  implements Filterable {

    private List<Publicacion> mPublicacionList, mPublicacionListFiltered;

    //viewtypes
    private final int VIEW_TYPE_NORMAL = 0;
    private final int VIEW_TYPE_ITEM_PENDING = 2;
    private final int VIEW_TYPE_ITEM_OUT = 3;

    private Context context;
    private PublicacionFilter filter;
    private PublicacionAdapter.OnItemClickListener onItemClickListener;

    public PublicacionAdapter(Context context, PublicacionAdapter.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.mPublicacionListFiltered = mPublicacionList;
        this.onItemClickListener = onItemClickListener;
    }

    public void setPublicacionList(final List<Publicacion> publicacionList) {
        if (mPublicacionList == null) {
            mPublicacionList = publicacionList;
            mPublicacionListFiltered = publicacionList;
            notifyItemRangeInserted(0, publicacionList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mPublicacionList.size();
                }

                @Override
                public int getNewListSize() {
                    return publicacionList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mPublicacionList.get(oldItemPosition).getCode() == publicacionList.get(newItemPosition).getCode();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Publicacion newPublicacion = publicacionList.get(newItemPosition);
                    Publicacion oldPublicacion = mPublicacionList.get(oldItemPosition);
                    return (newPublicacion.getCode() == oldPublicacion.getCode()
                            && Objects.equals(newPublicacion.getDescription(), oldPublicacion.getDescription())
                            && Objects.equals(newPublicacion.getLocation(), oldPublicacion.getLocation())
                            && Objects.equals(newPublicacion.getMetadata(), oldPublicacion.getMetadata())
                            && Objects.equals(newPublicacion.getBathroomsCount(), oldPublicacion.getBathroomsCount())
                            && Objects.equals(newPublicacion.getBedroomsCount(), oldPublicacion.getBedroomsCount())
                            && Objects.equals(newPublicacion.getGarageCount(), oldPublicacion.getGarageCount())
                            && newPublicacion.getTitle() == oldPublicacion.getTitle()
                    );
                }
            });
            mPublicacionList = publicacionList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemPublicacionBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_publicacion, parent, false);
        binding.setClickCallback(onItemClickListener);
        RecyclerView.ViewHolder viewHolder = null;
        if(viewType == VIEW_TYPE_NORMAL) {
            viewHolder = new PublicacionViewHolder(binding);
        } else if(viewType == VIEW_TYPE_ITEM_PENDING) {
            viewHolder = new PublicacionCheckPendingViewHolder(binding);
        } else if(viewType == VIEW_TYPE_ITEM_OUT) {
            viewHolder = new PublicacionCheckOutViewHolder(binding);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderView, int position) {
        final Publicacion publicacionEntity = mPublicacionList.get(position);
        PublicacionModel model = new PublicacionModel(publicacionEntity.getCode(), publicacionEntity.getTitle(), publicacionEntity.getBedroomsCount(), publicacionEntity.getBathroomsCount(), publicacionEntity.getGarageCount(), publicacionEntity.getAreaCount(),publicacionEntity.getMetadata(),publicacionEntity.getDescription(),publicacionEntity.getLocation(),publicacionEntity.getPrice(),publicacionEntity.getRanking(),publicacionEntity.getCreated_at(),publicacionEntity.getFirstImageUrl(),publicacionEntity.getType());
        PublicacionViewHolder holder = (PublicacionViewHolder) holderView;
        holder.binding.setPublicacion(model);
        holder.binding.executePendingBindings();

        if(publicacionEntity.getFirstImageUrl() != null) {
            if(ConnectionUtils.isNetworkConnected(this.context)) {
                new GlideImageLoader(holder.binding.itemImage, holder.binding.imagenLoadProgress).load(publicacionEntity.getFirstImageUrl(), new RequestOptions()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .placeholder(R.mipmap.image_placeholder)
                        .error(R.mipmap.image_error)
                        .priority(Priority.HIGH));
            } else {
                new GlideImageLoader(holder.binding.itemImage, holder.binding.imagenLoadProgress).load(publicacionEntity.getFirstImageUrl(), new RequestOptions()
                        .centerCrop()
                        .onlyRetrieveFromCache(true)
                        .placeholder(R.mipmap.image_placeholder)
                        .error(R.mipmap.image_error)
                        .priority(Priority.HIGH));
            }

        } else {
            holder.binding.itemImage.setImageResource(R.drawable.house);
        }

        if (Double.parseDouble(publicacionEntity.getRanking()) <= 9999) {
            holder.binding.publicacionBox.setBackgroundColor(context.getResources().getColor(R.color.red_box));
            holder.binding.iconsLayout.setBackgroundColor(context.getResources().getColor(R.color.red_box));
        }
        if (Double.parseDouble(publicacionEntity.getRanking()) >= 10000 && Double.parseDouble(publicacionEntity.getRanking()) <= 49999) {
            holder.binding.publicacionBox.setBackgroundColor(context.getResources().getColor(R.color.gray_box));
            holder.binding.iconsLayout.setBackgroundColor(context.getResources().getColor(R.color.gray_box));
        }
        if (Double.parseDouble(publicacionEntity.getRanking()) >= 50000 && Double.parseDouble(publicacionEntity.getRanking()) <= 99999) {
            holder.binding.publicacionBox.setBackgroundColor(context.getResources().getColor(R.color.blue_box));
            holder.binding.iconsLayout.setBackgroundColor(context.getResources().getColor(R.color.blue_box));
        }
        if (Double.parseDouble(publicacionEntity.getRanking()) >= 100000) {
            holder.binding.publicacionBox.setBackgroundColor(context.getResources().getColor(R.color.green_box));
            holder.binding.iconsLayout.setBackgroundColor(context.getResources().getColor(R.color.green_box));
        }

    }

    @Override
    public int getItemCount() {
        return this.mPublicacionList != null ? this.mPublicacionList.size() : 0;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new PublicacionFilter(mPublicacionListFiltered, this);
        }

        return filter;
    }

    public class PublicacionViewHolder extends RecyclerView.ViewHolder {
        final ItemPublicacionBinding binding;

        public PublicacionViewHolder(ItemPublicacionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.faBed.setTypeface(FontManager.getTypeface(context,FontManager.FONTAWESOME));
            binding.faShower.setTypeface(FontManager.getTypeface(context,FontManager.FONTAWESOME));
            binding.faCar.setTypeface(FontManager.getTypeface(context,FontManager.FONTAWESOME));
            binding.map.setTypeface(FontManager.getTypeface(context,FontManager.FONTAWESOME));
        }
    }

    public class PublicacionCheckPendingViewHolder extends PublicacionViewHolder {
        final ItemPublicacionBinding binding;

        public PublicacionCheckPendingViewHolder(ItemPublicacionBinding binding) {
            super(binding);
            this.binding = binding;
            binding.itemImage.setBackgroundResource(R.color.inventory_check_penddend);
        }
    }

    public class PublicacionCheckOutViewHolder extends PublicacionViewHolder {
        final ItemPublicacionBinding binding;

        public PublicacionCheckOutViewHolder(ItemPublicacionBinding binding) {
            super(binding);
            this.binding = binding;
            binding.itemImage.setBackgroundResource(R.color.inventory_check_another);
        }
    }

    public interface OnItemClickListener {
        void onClick(PublicacionModel publicacion);
    }
}