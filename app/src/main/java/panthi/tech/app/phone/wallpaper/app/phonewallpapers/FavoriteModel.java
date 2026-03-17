package panthi.tech.app.phone.wallpaper.app.phonewallpapers;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "favorite_table", indices = {
        @Index(value = {"name"}, unique = true),
        @Index(value = {"author"}, unique = true),
        @Index(value = {"dimensions"}, unique = true),
        @Index(value = {"url"}, unique = true),
        @Index(value = {"thumbnail"}, unique = true),
        @Index(value = {"collections"}, unique = true)})
public class FavoriteModel {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("dimensions")
    @Expose
    private String dimensions;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("collections")
    @Expose
    private String collections;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCollections() {
        return collections;
    }

    public void setCollections(String collections) {
        this.collections = collections;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    public FavoriteModel(String name, String author, String dimensions, String url, String thumbnail, String collections) {
        this.name = name;
        this.author = author;
        this.dimensions = dimensions;
        this.url = url;
        this.thumbnail = thumbnail;
        this.collections = collections;
    }
}
