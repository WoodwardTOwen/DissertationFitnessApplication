package woodward.owen.fitnessapplication.TrackingPackage;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp (Long value) {
        return value == null ? null : new Date(value);
    }
}
