package eu.anifantakis.bakingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import eu.anifantakis.bakingapp.utils.AppUtils;



public class Ingredient implements Parcelable {
    private float Quantity;
    private String Measures;
    private String Ingredients;

    protected Ingredient(Parcel in) {
        Quantity = in.readFloat();
        Measures = in.readString();
        Ingredients = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public String getDoseStr(){
        return AppUtils.fmt(Quantity)  + " " +Measures;
    }

    public float getQuantity() {
        return Quantity;
    }

    public void setQuantity(float Quantity) {
        this.Quantity = Quantity;
    }

    public String getMeasure() {
        return Measures;
    }

    public void setMeasure(String Measures) {
        this.Measures = Measures;
    }

    public String getIngredient() {
        return Ingredients;
    }

    public void setIngredient(String Ingredients) {
        this.Ingredients = Ingredients;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(Quantity);
        dest.writeString(Measures);
        dest.writeString(Ingredients);
    }
}
