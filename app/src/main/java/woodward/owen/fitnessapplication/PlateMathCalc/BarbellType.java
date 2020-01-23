package woodward.owen.fitnessapplication.PlateMathCalc;

import android.os.Parcel;
import android.os.Parcelable;

public class BarbellType implements Parcelable {
    private String barbellName;
    private Float barbellWeight;

    public BarbellType (String pBarbellName, Float pBarbellWeight) {
        this.barbellName = pBarbellName;
        this.barbellWeight = pBarbellWeight;
    }

    public String getBarbellName () { return barbellName; }
    public Float getBarbellWeight() { return barbellWeight; }

    public void setBarbellName(String pBarbellName) { this.barbellName = pBarbellName; }
    public void setBarbellWeight(Float pBarbellWeight) { this.barbellWeight = pBarbellWeight; }

    @Override
    public String toString() {
        return barbellName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel destination, int argument1) {
        destination.writeString(barbellName);
        destination.writeFloat(barbellWeight);
    }

    public BarbellType (Parcel in) {
        barbellName = in.readString();
        barbellWeight = in.readFloat();
    }

    public static final Parcelable.Creator<BarbellType> CREATOR = new Parcelable.Creator<BarbellType>() {
        public BarbellType createFromParcel(Parcel in){
            return new BarbellType(in);
        }
        public BarbellType[] newArray(int size) {
            return new BarbellType[size];
        }
    };

}
