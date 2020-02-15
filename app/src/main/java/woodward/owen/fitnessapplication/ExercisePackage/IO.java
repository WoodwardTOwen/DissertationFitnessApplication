package woodward.owen.fitnessapplication.ExercisePackage;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import woodward.owen.fitnessapplication.R;


public class IO {

    //private Context context;

   // public IO(Context context) {
      //  this.context = context;
    //}

    private static IO single_Instance = null;
    private static Application context;

    private IO(Application application){
        IO.context = application;
    }

    public static void setInstance(Application app){
        if(single_Instance == null){
            single_Instance = new IO(app);
        }
    }

    public static Map<CategoryType, String[]> ReadData() {

        Map<CategoryType, String[]> dict = new Hashtable<>();
        InputStream IS = context.getResources().openRawResource(R.raw.list_of_exercises);
        BufferedReader reader = new BufferedReader(new InputStreamReader(IS, Charset.forName("UTF-8")));

        String line = "";
        try {

            while ((line = reader.readLine()) != null) {

                String[] columns = line.split(",");
                CategoryType type = Enum.valueOf(CategoryType.class, columns[0].toUpperCase());
                List<String> list = new ArrayList<>();

                for (int x = 1; x < columns.length; x++) {
                    if (isNullOrWhitespace(columns[x])) {
                        continue;
                    }
                    list.add(columns[x]);
                }

                String[] array = new String[list.size()];

                for(int i = 0; i< list.size(); i++){
                    array[i] = list.get(i);
                }

                if (dict.containsKey(type)) {
                    dict.replace(type, array);

                } else {
                    dict.put(type, array);
                }

            }

        } catch (IOException e) {
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
