package woodward.owen.fitnessapplication.plate_math_calculator_package;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class BarbellType implements Parcelable {
    private String barbellName;
    private int barbellWeight;

    BarbellType(String pBarbellName, int pBarbellWeight) {
        this.barbellName = pBarbellName;
        this.barbellWeight = pBarbellWeight;
    }

    String getBarbellName() { return barbellName; }
    int getBarbellWeight() { return barbellWeight; }

    @NonNull
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
        destination.writeInt(barbellWeight);
    }

    private BarbellType(Parcel in) {
        barbellName = in.readString();
        barbellWeight = in.readInt();
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
