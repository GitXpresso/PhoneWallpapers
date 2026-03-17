package panthi.tech.app.phone.wallpaper.app.phonewallpapers;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class FavoriteFragment extends Fragment {
    private FavoriteDao mFavoriteDao;
    private RecyclerView recyclerview;
    Context context;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        context = getActivity();
        recyclerview = view.findViewById(R.id.recyclerview);


        FavoriteDatabase db = FavoriteDatabase.getDatabase(context);
        mFavoriteDao = db.getDao();


        List<FavoriteModel> list = FavoriteDatabase.getDatabase(context).getDao().getAllData();
        recyclerview.setLayoutManager(new GridLayoutManager(context,2));
        recyclerview.setAdapter(new FavoriteAdapter(context, mFavoriteDao, list, (position, id) -> {
            FavoriteDatabase.getDatabase(context).getDao().deleteData(id);
            getData();
        }));

        ImageView emptyImage = view.findViewById(R.id.empty_image);
        if (list.isEmpty()) {
            emptyImage.setVisibility(View.VISIBLE);
        } else {
            emptyImage.setVisibility(View.GONE);
        }

        return view;

    }

    private void getData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}