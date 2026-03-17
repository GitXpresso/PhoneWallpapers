package panthi.tech.app.phone.wallpaper.app.phonewallpapers;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    public final Context context;
    private final List<FavoriteModel> list;
    private final DeleteItemClickListner deleteItemClickListner;
    private final FavoriteDao mFavoriteDao;

    public FavoriteAdapter(Context context, FavoriteDao favoriteDao, List<FavoriteModel> list, DeleteItemClickListner deleteItemClickListner) {
        this.context = context;
        this.mFavoriteDao = favoriteDao;
        this.list = list;
        this.deleteItemClickListner = deleteItemClickListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adaper_wallpaper, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        FavoriteModel model = list.get(position);
        holder.wallpaper_name.setText(model.getName());
        holder.wallpaper_author.setText(model.getAuthor());
        Glide.with(context)
                .load(Uri.parse(model.getThumbnail()))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        holder.id1.setImageDrawable(resource);
                    }
                });


        boolean isFavorite = mFavoriteDao.isFavorite(model.id);
        if (isFavorite) {
            holder.favorite.setImageResource(R.drawable.ic_favorite);
        } else {
            holder.favorite.setImageResource(R.drawable.ic_favorite_outline);
        }
        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItemClickListner.onItemDelete(position, list.get(position).id);
            }
        });

        holder.id1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WallpaperDetailActivity.class);
                intent.putExtra("name", model.getName());
                intent.putExtra("author", model.getAuthor());
                intent.putExtra("dimensions", model.getDimensions());
                intent.putExtra("url", model.getUrl());
                intent.putExtra("thumbnail", model.getThumbnail());
                intent.putExtra("collections", model.getCollections());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
//        holder.favorite.setOnClickListener(view -> deleteItemClickListner.onItemDelete(position, list.get(position).id));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface DeleteItemClickListner {
        void onItemDelete(int position, int id);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView id1, favorite;
        TextView wallpaper_name, wallpaper_author;

        public ViewHolder(View view) {
            super(view);

            id1 = itemView.findViewById(R.id.wallpaper_image);
            wallpaper_name = itemView.findViewById(R.id.wallpaper_name);
            wallpaper_author = itemView.findViewById(R.id.wallpaper_author);
            favorite = itemView.findViewById(R.id.fav_button);

        }
    }
}
