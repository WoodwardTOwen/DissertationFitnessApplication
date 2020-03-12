package woodward.owen.fitnessapplication.weight_tracking_package.database_package;

import androidx.room.TypeConverter;

import java.util.Date;

public class DatabaseTypeConverters {
    @TypeConverter
    public static Date fromTimestamp (Long value) {
        return value == null ? null : new Date(value);
    }
}
