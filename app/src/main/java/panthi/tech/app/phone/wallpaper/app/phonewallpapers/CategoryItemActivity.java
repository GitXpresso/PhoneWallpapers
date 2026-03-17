package panthi.tech.app.phone.wallpaper.app.phonewallpapers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CategoryItemActivity extends AppCompatActivity {
    TextView names;
    int i;
    String title;

    RecyclerView recycler_view;

    Context context;
    JSONObject userDetail;
    JSONObject obj;
    public static ArrayList<Wallpaper> wallpapers;
    ArrayList<String> name, author, dimensions, url, thumbnail, collections;
    private FavoriteDao mFavoriteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item);

        i = getIntent().getIntExtra("position", 0);
        title = getIntent().getStringExtra("name");
        ImageView back_detail = findViewById(R.id.back_detail);
        back_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        names = findViewById(R.id.name);
        names.setText(title);

        FavoriteDatabase db = FavoriteDatabase.getDatabase(context);
        mFavoriteDao = db.getDao();

        recycler_view = findViewById(R.id.recycler_views);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CategoryItemActivity.this, 2);
        recycler_view.setLayoutManager(gridLayoutManager);

        name = new ArrayList<>();
        author = new ArrayList<>();
        dimensions = new ArrayList<>();
        url = new ArrayList<>();
        thumbnail = new ArrayList<>();
        collections = new ArrayList<>();

        try {
            obj = new JSONObject(loadJSONFromAsset());
            JSONArray userArray = obj.getJSONArray(title);
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

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open(title + ".json");
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
    public void onBackPressed() {
        super.onBackPressed();
    }
}