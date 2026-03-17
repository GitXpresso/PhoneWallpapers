package panthi.tech.app.phone.wallpaper.app.phonewallpapers;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;

import android.app.WallpaperManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.ortiz.touchview.TouchImageView;
import com.squareup.picasso.Picasso;
import com.varunjohn1990.iosdialogs4android.IOSDialog;
import com.varunjohn1990.iosdialogs4android.IOSDialogButton;
import com.varunjohn1990.iosdialogs4android.IOSDialogMultiOptionsListeners;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WallpaperDetailActivity extends AppCompatActivity {
    TouchImageView wallpaper;
    TextView toolbar_title, toolbar_subtitle;
    ProgressBar progressBar;
    ImageView back;
    BottomNavigationView bottom_navigation;
    String name1, author1, dimensions1, url1, thumbnail1, collections1;

    WallpaperManager myWallpaperManager;
    private static final int CHOOSE_IMAGE = 22;
    private Button btnSetWallpaper;
    String[] options = new String[]{
            "Home Screen",
            "Lock Screen",
            "Both"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_detail);

        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.details:
                        showBottomSheetDialog();
                        break;
                    case R.id.download:

                        new IOSDialog.Builder(WallpaperDetailActivity.this)
                                .title("Save Wallpaper..?")              // String or String Resource ID
                                .message("Save Wallpaper in Your Internal Storage.")  // String or String Resource ID
                                .positiveButtonText("Yeah, sure")  // String or String Resource ID
                                .negativeButtonText("No Thanks")   // String or String Resource ID
                                .positiveClickListener(new IOSDialog.Listener() {
                                    @Override
                                    public void onClick(IOSDialog iosDialog) {
                                        Picasso.get().load(url1).into(new com.squareup.picasso.Target() {
                                            @Override
                                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                                                File dataFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Wallpaper");
                                                Log.d("parent", dataFolder.getParent());

                                                if (!dataFolder.exists()) {
                                                    Log.d("mkdir_success", "Succesfully created directory: " + dataFolder.mkdirs());
                                                } else {
                                                    Log.d("fileexists", "true");
                                                }
                                                String filename = new SimpleDateFormat("yyMMddHHmmss").format(Calendar.getInstance().getTime()) + ".png";
                                                File testMediaFile = new File(dataFolder, filename);
                                                try {
                                                    Log.d("media_file", "successfully created: " + testMediaFile.createNewFile());
                                                    FileOutputStream out = new FileOutputStream(testMediaFile);
                                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                                                    Toast.makeText(WallpaperDetailActivity.this, "Saved SucessFully..!!", Toast.LENGTH_SHORT).show();
                                                    out.flush();
                                                    out.close();

                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }

                                                MediaScannerConnection.scanFile(WallpaperDetailActivity.this, new String[]{testMediaFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                                                        public void onScanCompleted(String path, Uri uri) {
                                                             Log.i("ExternalStorage", "Scanned " + path + ":");
                                                            Log.i("ExternalStorage", "-> uri=" + uri);
                                                        }
                                                    });
                                            }

                                            @Override
                                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                                                Toast.makeText(WallpaperDetailActivity.this, "Failed To Download.", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                                            }
                                        });
                                        iosDialog.dismiss();
                                    }
                                }).negativeClickListener(new IOSDialog.Listener() {
                                    @Override
                                    public void onClick(IOSDialog iosDialog) {
                                        iosDialog.dismiss();
                                    }
                                })
                                .build()
                                .show();


                        break;
                    case R.id.apply:
//

                        List<IOSDialogButton> iosDialogButtons = new ArrayList<>();
                        iosDialogButtons.add(new IOSDialogButton(1, "Home Screen", true, IOSDialogButton.TYPE_POSITIVE));
                        iosDialogButtons.add(new IOSDialogButton(2, "Lock Screen", true, IOSDialogButton.TYPE_POSITIVE));
                        iosDialogButtons.add(new IOSDialogButton(3, "Set Both", true, IOSDialogButton.TYPE_NEGATIVE));

                        new IOSDialog.Builder(WallpaperDetailActivity.this)
                                .title("Apply Wallpaper..??")              // String or String Resource ID
                                .message("How to like you set wallpaper by Multiple Choice.")  // String or String Resource ID
                                .multiOptions(true)                // Set this true other it will not work
                                .multiOptionsListeners(new IOSDialogMultiOptionsListeners() {
                                    @Override
                                    public void onClick(IOSDialog iosDialog, IOSDialogButton iosDialogButton) {
                                        iosDialog.dismiss();
                                        InputStream ins = null;
                                        switch (iosDialogButton.getId()) {
                                            case 1:
                                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                                StrictMode.setThreadPolicy(policy);
                                                myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                                                try {
                                                    ins = new URL(url1).openStream();
                                                    myWallpaperManager.setStream(ins, null, false, WallpaperManager.FLAG_SYSTEM);
                                                    Toast.makeText(WallpaperDetailActivity.this, "Wallpaper Applied SucessFully...!!", Toast.LENGTH_SHORT).show();
                                                    iosDialog.dismiss();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                break;
                                            case 2:
                                                StrictMode.ThreadPolicy policy1 = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                                StrictMode.setThreadPolicy(policy1);
                                                myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                                                try {
                                                    ins = new URL(url1).openStream();
                                                    myWallpaperManager.setStream(ins, null, false, WallpaperManager.FLAG_LOCK);
                                                    Toast.makeText(WallpaperDetailActivity.this, "Wallpaper Applied SucessFully...!!", Toast.LENGTH_SHORT).show();
                                                    iosDialog.dismiss();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                break;
                                            case 3:
                                                StrictMode.ThreadPolicy policy2 = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                                StrictMode.setThreadPolicy(policy2);
                                                myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                                                try {
                                                    ins = new URL(url1).openStream();
                                                    myWallpaperManager.setStream(ins, null, false, WallpaperManager.FLAG_LOCK | WallpaperManager.FLAG_SYSTEM);
                                                    Toast.makeText(WallpaperDetailActivity.this, "Wallpaper Applied SucessFully...!!", Toast.LENGTH_SHORT).show();
                                                    iosDialog.dismiss();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                break;
                                        }
                                    }
                                })
                                .iosDialogButtonList(iosDialogButtons)
                                .build()
                                .show();
//                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//                        StrictMode.setThreadPolicy(policy);
//
//                        WallpaperManager wpm = WallpaperManager.getInstance(WallpaperDetailActivity.this);
//                        InputStream ins = null;
//                        try {
//                            ins = new URL(url1).openStream();
//                            wpm.setStream(ins);
//                            Toast.makeText(WallpaperDetailActivity.this, "Wallpaper Applied SucessFully...!!", Toast.LENGTH_SHORT).show();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }

//                        Toast.makeText(WallpaperDetailActivity.this, "Apply", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.favorites:
                        Toast.makeText(WallpaperDetailActivity.this, "Favorite", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }


        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        wallpaper = findViewById(R.id.wallpaper);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_subtitle = findViewById(R.id.toolbar_subtitle);

        progressBar = findViewById(R.id.progress);

        name1 = getIntent().getExtras().getString("name");
        author1 = getIntent().getExtras().getString("author");
        dimensions1 = getIntent().getExtras().getString("dimensions");
        url1 = getIntent().getExtras().getString("url");
        thumbnail1 = getIntent().getExtras().getString("thumbnail");
        collections1 = getIntent().getExtras().getString("collections");

        toolbar_title.setText(name1);
        toolbar_subtitle.setText(author1);

        Glide.with(this)
                .load(Uri.parse(url1))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        wallpaper.setImageDrawable(resource);
                    }
                });
    }

    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(R.layout.info_dialog);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        TextView detail_authors = bottomSheetDialog.findViewById(R.id.detail_authors);
        TextView detail_resolutions = bottomSheetDialog.findViewById(R.id.detail_resolutions);
        TextView detail_names = bottomSheetDialog.findViewById(R.id.detail_names);

        TextView vibrantView = (TextView) bottomSheetDialog.findViewById(R.id.vibrantView);
        TextView vibrantLightView = (TextView) bottomSheetDialog.findViewById(R.id.vibrantLightView);
        TextView vibrantDarkView = (TextView) bottomSheetDialog.findViewById(R.id.vibrantDarkView);
        TextView mutedView = (TextView) bottomSheetDialog.findViewById(R.id.mutedView);
        TextView mutedLightView = (TextView) bottomSheetDialog.findViewById(R.id.mutedLightView);
        TextView mutedDarkView = (TextView) bottomSheetDialog.findViewById(R.id.mutedDarkView);

        detail_authors.setText(author1);
        detail_resolutions.setText(dimensions1);
        detail_names.setText(name1);

//
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wallpaper);

        Palette.from(getBitmapFromURL(url1)).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
//                palette.getDominantColor(ContextCompat.getColor(this, R.color.defaultColor));
                //work with the palette here
                int defaultValue = palette.getDominantColor(ContextCompat.getColor(WallpaperDetailActivity.this, R.color.black));
                int vibrant = palette.getVibrantColor(defaultValue);
                int vibrantLight = palette.getLightVibrantColor(defaultValue);
                int vibrantDark = palette.getDarkVibrantColor(defaultValue);
                int muted = palette.getMutedColor(defaultValue);
                int mutedLight = palette.getLightMutedColor(defaultValue);
                int mutedDark = palette.getDarkMutedColor(defaultValue);

                vibrantView.setBackgroundColor(vibrant);
                vibrantLightView.setBackgroundColor(vibrantLight);
                vibrantDarkView.setBackgroundColor(vibrantDark);
                mutedView.setBackgroundColor(muted);
                mutedLightView.setBackgroundColor(mutedLight);
                mutedDarkView.setBackgroundColor(mutedDark);

                String hexColor = String.format("#%06X", (0xFFFFFF & vibrant));
                vibrantView.setText(hexColor);
                vibrantView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(WallpaperDetailActivity.this, "Color copied to Clipboard", Toast.LENGTH_LONG).show();
                        ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("label", hexColor));

                    }
                });
                String hexColor1 = String.format("#%06X", (0xFFFFFF & vibrantLight));
                vibrantLightView.setText(hexColor1);
                vibrantLightView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(WallpaperDetailActivity.this, "Color copied to Clipboard", Toast.LENGTH_LONG).show();
                        ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("label", hexColor1));

                    }
                });
                String hexColor2 = String.format("#%06X", (0xFFFFFF & vibrantDark));
                vibrantDarkView.setText(hexColor2);
                vibrantDarkView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(WallpaperDetailActivity.this, "Color copied to Clipboard", Toast.LENGTH_LONG).show();
                        ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("label", hexColor2));

                    }
                });
                String hexColor3 = String.format("#%06X", (0xFFFFFF & muted));
                mutedView.setText(hexColor3);
                mutedView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(WallpaperDetailActivity.this, "Color copied to Clipboard", Toast.LENGTH_LONG).show();
                        ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("label", hexColor3));

                    }
                });
                String hexColor4 = String.format("#%06X", (0xFFFFFF & mutedLight));
                mutedLightView.setText(hexColor4);
                mutedLightView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(WallpaperDetailActivity.this, "Color copied to Clipboard", Toast.LENGTH_LONG).show();
                        ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("label", hexColor4));

                    }
                });
                String hexColor5 = String.format("#%06X", (0xFFFFFF & mutedDark));
                mutedDarkView.setText(hexColor5);
                mutedDarkView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(WallpaperDetailActivity.this, "Color copied to Clipboard", Toast.LENGTH_LONG).show();
                        ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("label", hexColor5));

                    }
                });
            }
        });


        bottomSheetDialog.show();
    }


    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE && data != null) {
            Uri mCropImageUri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mCropImageUri);
                Log.e("name", "onActivityResult: BIT " + bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}