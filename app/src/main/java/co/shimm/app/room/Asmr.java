package co.shimm.app.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Asmr {
    @PrimaryKey
    @SerializedName("sleep_id")
    private int id;
    @SerializedName("sleep_name")
    private String title;
    @SerializedName("sleep_msec")
    private int duration;
    @SerializedName("sleep_picture")
    private String thumbnail;
    @SerializedName("sleep_music")
    private String url;
    private int order;

    public Asmr(int id, String title, int duration, String thumbnail, String url, int order) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.thumbnail = thumbnail;
        this.url = url;
        this.order = order;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
