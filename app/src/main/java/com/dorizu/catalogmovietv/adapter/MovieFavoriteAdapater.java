package com.dorizu.catalogmovietv.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dorizu.catalogmovietv.R;
import com.dorizu.catalogmovietv.model.FavoritItem;

import java.util.ArrayList;

public class MovieFavoriteAdapater extends RecyclerView.Adapter<MovieFavoriteAdapater.MovieFavoriteViewHolder> {

    private ArrayList<FavoritItem> listMovieFavorite = new ArrayList<>();
    private Activity activity;

    private ArrayList<FavoritItem> mData;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public MovieFavoriteAdapater(Activity activity) {
        this.activity = activity;
    }

    public void removeItem(int position) {
        this.listMovieFavorite.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,listMovieFavorite.size());
    }

    public ArrayList<FavoritItem> getListMovieFavorite() {
        return listMovieFavorite;
    }

    public void setListMovieFavorite(ArrayList<FavoritItem> listNotes) {

        if (listMovieFavorite.size() > 0) {
            this.listMovieFavorite.clear();
        }
        this.listMovieFavorite.addAll(listNotes);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieFavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_favorite, parent, false);
        return new MovieFavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieFavoriteViewHolder holder, final int position) {
        holder.tvTitle.setText(listMovieFavorite.get(position).getJudul());
        holder.tvDescription.setText(listMovieFavorite.get(position).getOverview());
        Glide.with(holder.imagePoster)
                .load("http://image.tmdb.org/t/p/w154/"+listMovieFavorite.get(position).getThumnail())
                .apply(new RequestOptions().override(350, 550))
                .into(holder.imagePoster);
        holder.btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null){
                    mListener.OnItemClick(listMovieFavorite.get(position).getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovieFavorite.size();
    }

    public class MovieFavoriteViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle, tvDescription;
        final ImageView imagePoster;
        Button btnHapus;
        public MovieFavoriteViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvDescription = itemView.findViewById(R.id.tv_item_description);
            imagePoster = itemView.findViewById(R.id.img_item_poster);
            btnHapus = itemView.findViewById(R.id.btn_delete_favorite);
        }
    }

    public ArrayList<FavoritItem> getData(){
        return mData;
    }
}
