package woodward.owen.fitnessapplication.TrackingPackage;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import woodward.owen.fitnessapplication.ExercisePackage.Category;
import woodward.owen.fitnessapplication.ExercisePackage.CategoryType;
import woodward.owen.fitnessapplication.ExercisePackage.IO;
import woodward.owen.fitnessapplication.R;

public class AddExercise extends Activity {

    private static final Map<CategoryType, List<String>> PossibleNames = new HashMap<>();
    private static final Map<CategoryType, Category> Categories = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_exercise_layout_file);

        readIO();

    }

    private Map<CategoryType, String[]> readIO(){
        Map<CategoryType, String[]> dict = new HashMap<>();
        InputStream IS = getResources().openRawResource(R.raw.list_of_exercises);
        BufferedReader reader = new BufferedReader(new InputStreamReader(IS, Charset.forName("UTF-8")));

        String line = "";
        try {

            while ((line = reader.readLine()) != null) {

                String[] columns = line.split(",");
                CategoryType type = Enum.valueOf(CategoryType.class, columns[0].toUpperCase());
                List<String> list = new ArrayList<>();

                for(int x = 1; x < columns.length; x++){
                    if(isNullOrWhitespace(columns[x])){
                        continue;
                    }
                    list.add(columns[x]);
                }

                String[] array = list.toArray(new String[list.size()]);
                if(dict.containsKey(type)) {
                    for (String item : dict.get(type)) {
                        list.add(item);
                    }
                    //dict[type] = list.toArray();

                }
                else {
                    dict.put(type, array);
                }

            }

        }
        catch (IOException e) {
            Log.wtf("MyActivity", "Error Reading Data File on Line " + line + e);
            e.printStackTrace();
        }

        return dict;

    }

    public static boolean isNullOrWhitespace(String s) {
        return s == null || isWhitespace(s);
    }

    private static boolean isWhitespace(String s) {
        int length = s.length();
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                if (!Character.isWhitespace(s.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
