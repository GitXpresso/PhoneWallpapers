package panthi.tech.app.phone.wallpaper.app.phonewallpapers;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class WallpapersFragment extends Fragment {
    RecyclerView recycler_view;

    Context context;
    JSONObject userDetail;
    JSONObject obj;
    public static ArrayList<Wallpaper> wallpapers;
    ArrayList<String> name, author, dimensions, url, thumbnail, collections;
    private FavoriteDao mFavoriteDao;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallpapers, container, false);
        context = getActivity();

        FavoriteDatabase db = FavoriteDatabase.getDatabase(context);
        mFavoriteDao = db.getDao();

        recycler_view = view.findViewById(R.id.recycler_view);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        recycler_view.setLayoutManager(gridLayoutManager);

        name = new ArrayList<>();
        author = new ArrayList<>();
        dimensions = new ArrayList<>();
        url = new ArrayList<>();
        thumbnail = new ArrayList<>();
        collections = new ArrayList<>();


        try {
            obj = new JSONObject(loadJSONFromAsset());
            JSONArray userArray = obj.getJSONArray("wallpaper");
            for (int i = 0; i < userArray.length(); i++) {
                userDetail = userArray.getJSONObject(i);

                name.add(userDetail.getString("name"));
                author.add(userDetail.getString("author"));
                dimensions.add(userDetail.getString("dimensions"));
                url.add(userDetail.getString("url"));
                thumbnail.add(userDetail.getString("thumbnail"));
                collections.add(userDetail.getString("collections"));


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        WallpaperAdapter customAdapter = new WallpaperAdapter(context,mFavoriteDao, name, author, dimensions, url, thumbnail, collections);
        recycler_view.setAdapter(customAdapter); // set the Adapter to RecyclerView

        return view;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("Wallpaper.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}