package com.unicomg.robustatask.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.unicomg.robustatask.R;
import com.unicomg.robustatask.store.local.Image;

import java.util.List;


public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {

    private final List<Image> mValues;
    private final Context mContext;
    private OnClickImage onClickImage;

    public ImagesAdapter(List<Image> items, OnClickImage listener, Context context) {
        mValues = items;
        onClickImage = listener;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_item, parent, false);
        return new ImagesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ImagesAdapter.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Picasso.get().load(holder.mItem.getUrl()).into(holder.imageView);
        holder.textView.setText(holder.mItem.getPlace());
        holder.imageView.setOnClickListener(view -> {
            onClickImage.show(holder.mItem, position);
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public interface OnClickImage {
        void show(Image imageModel, int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public Image mItem;
        ImageView imageView;
        TextView textView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imageView = itemView.findViewById(R.id.request_img);
            textView = itemView.findViewById(R.id.place_name);
        }
    }
}

