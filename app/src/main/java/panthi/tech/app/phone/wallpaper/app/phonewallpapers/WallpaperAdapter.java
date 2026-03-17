package panthi.tech.app.phone.wallpaper.app.phonewallpapers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.MyViewHolder> {
    ArrayList<String> name, author, dimensions, url, thumbnail, collections;
    List<Wallpaper> wallpaper;
    Context context;
    String imageUrl;
    private boolean[] favorites;
     FavoriteDao favoriteDao;

    public WallpaperAdapter(Context context, FavoriteDao favoriteDao,ArrayList<String> name, ArrayList<String> author, ArrayList<String> dimensions, ArrayList<String> url, ArrayList<String> thumbnail, ArrayList<String> collections) {
        this.context = context;
        this.favoriteDao = favoriteDao;
        this.name = name;
        this.author = author;
        this.dimensions = dimensions;
        this.url = url;
        this.thumbnail = thumbnail;
        this.collections = collections;

    }

    public WallpaperAdapter(Context context, List<Wallpaper> wallpaper) {
        this.context = context;
        this.wallpaper = wallpaper;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adaper_wallpaper, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.wallpaper_name.setText(name.get(position));
        holder.wallpaper_author.setText(author.get(position));
        Glide.with(context)
                .load(Uri.parse(thumbnail.get(position)))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        holder.id1.setImageDrawable(resource);
                    }
                });

        holder.id1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WallpaperDetailActivity.class);
                intent.putExtra("name", name.get(position).toString());
                intent.putExtra("author", author.get(position).toString());
                intent.putExtra("dimensions", dimensions.get(position).toString());
                intent.putExtra("url", url.get(position).toString());
                intent.putExtra("thumbnail", thumbnail.get(position).toString());
                intent.putExtra("collections", collections.get(position).toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        boolean isFavorite = favoriteDao.isFavorite(position);
        if (isFavorite) {
            holder.favorite.setImageResource(R.drawable.ic_favorite);
        } else {
            holder.favorite.setImageResource(R.drawable.ic_favorite_outline);
        }

        holder.favorite.setOnClickListener(view -> {
            boolean isCurrentlyFavorite = favoriteDao.isFavorite(position);
            if (isCurrentlyFavorite) {
                favoriteDao.deleteData(position);
                holder.favorite.setImageResource(R.drawable.ic_favorite_outline);
            } else {
                favoriteDao.insertAllData(new FavoriteModel( name.get(position),author.get(position),dimensions.get(position), url.get(position),thumbnail.get(position),collections.get(position)));
                holder.favorite.setImageResource(R.drawable.ic_favorite);
            }
        });

//        holder.favorite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_outline));
//        holder.favorite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                boolean isFavourite = readState();
//
//                if (isFavourite) {
//                    holder.favorite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_outline));
//                    isFavourite = false;
//                    saveState(isFavourite);
//
//                } else {
//                    holder.favorite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite));
//                    isFavourite = true;
//                    saveState(isFavourite);
//
//                }
//            }
//        });

    }

    private void saveState(boolean isFavourite) {
        SharedPreferences aSharedPreferences = this.context.getSharedPreferences("Favourite", Context.MODE_PRIVATE);
        SharedPreferences.Editor aSharedPreferencesEdit = aSharedPreferences.edit();
        aSharedPreferencesEdit.putBoolean("State", isFavourite);
        aSharedPreferencesEdit.commit();
    }

    private boolean readState() {
        SharedPreferences aSharedPreferences = this.context.getSharedPreferences("Favourite", Context.MODE_PRIVATE);
        return aSharedPreferences.getBoolean("State", true);
    }


    @Override
    public int getItemCount() {
        return name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView id1, favorite;
        TextView wallpaper_name, wallpaper_author;

        public MyViewHolder(View itemView) {
            super(itemView);
            id1 = itemView.findViewById(R.id.wallpaper_image);
            wallpaper_name = itemView.findViewById(R.id.wallpaper_name);
            wallpaper_author = itemView.findViewById(R.id.wallpaper_author);
            favorite = itemView.findViewById(R.id.fav_button);

        }
    }
}

