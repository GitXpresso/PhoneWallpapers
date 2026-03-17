package panthi.tech.app.phone.wallpaper.app.phonewallpapers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.ArrayList;
import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    Context context;
    String imageUrl;
    ArrayList<String> name, count, thumbnail;
    private boolean[] favorites;

    public CategoryAdapter(Context context, ArrayList<String> name, ArrayList<String> count, ArrayList<String> thumbnail) {
        this.context = context;
        this.name = name;
        this.count = count;
        this.thumbnail = thumbnail;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adaper_category, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.wallpaper_name.setText(name.get(position));
        holder.wallpaper_author.setText(count.get(position));
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
                Intent intent = new Intent(context, CategoryItemActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("name", name.get(position));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


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
        ImageView id1;
        TextView wallpaper_name, wallpaper_author;

        public MyViewHolder(View itemView) {
            super(itemView);
            id1 = itemView.findViewById(R.id.wallpaper_image);
            wallpaper_name = itemView.findViewById(R.id.collection_title);
            wallpaper_author = itemView.findViewById(R.id.collection_count);

        }
    }
}

