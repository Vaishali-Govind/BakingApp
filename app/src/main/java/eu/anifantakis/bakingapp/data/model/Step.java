package eu.anifantakis.bakingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Step implements Parcelable {
    private int id;
    private String Descriptionshort;
    private String description;
    private String URLvideo;
    private String URLthumbnail;

    protected Step(Parcel in) {
        id = in.readInt();
        Descriptionshort = in.readString();
        description = in.readString();
        URLvideo = in.readString();
        URLthumbnail = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public void swapVideoWithThumb(){
        String oldThumb = getThumbnailURL();
        String oldVideo = getVideoURL();

        setThumbnailURL(oldVideo);
        setVideoURL(oldThumb);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return Descriptionshort;
    }

    public void setShortDescription(String Descriptionshort) {
        this.Descriptionshort = Descriptionshort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return URLvideo;
    }

    public void setVideoURL(String URLvideo) {
        this.URLvideo = URLvideo;
    }

    public String getThumbnailURL() {
        return URLthumbnail;
    }

    public void setThumbnailURL(String URLthumbnail) {
        this.URLthumbnail = URLthumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(Descriptionshort);
        dest.writeString(description);
        dest.writeString(URLvideo);
        dest.writeString(URLthumbnail);
    }
}
