package panthi.tech.app.phone.wallpaper.app.phonewallpapers;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CategoriesFragment extends Fragment {
    RecyclerView recycler_view;

    Context context;
    JSONObject userDetail;
    JSONObject obj;
    public static ArrayList<Category> categories;
    ArrayList<String> name, count, thumbnail;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        context = getActivity();
        recycler_view = view.findViewById(R.id.recycler_view1);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        recycler_view.setLayoutManager(gridLayoutManager);

        name = new ArrayList<>();
        count = new ArrayList<>();
        thumbnail = new ArrayList<>();

        try {
            obj = new JSONObject(loadJSONFromAsset());
            JSONArray userArray = obj.getJSONArray("Category");

            for (int i = 0; i < userArray.length(); i++) {
                userDetail = userArray.getJSONObject(i);

                count.add(userDetail.getString("count"));
                thumbnail.add(userDetail.getString("thumbnail"));
                name.add(userDetail.getString("collections"));

                Log.d("name", name.toString());

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CategoryAdapter customAdapter = new CategoryAdapter(context, name, count, thumbnail);
        recycler_view.setAdapter(customAdapter); // set the Adapter to RecyclerView

        return view;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("Category.json");
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