package woodward.owen.fitnessapplication.plate_math_calculator_package;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PlateMathCustomAdapter extends ArrayAdapter<Map.Entry<Float, Integer>> {

    public PlateMathCustomAdapter(Context context, List<Map.Entry<Float, Integer>> map) {
        super(context, android.R.layout.simple_list_item_2, map);
    }

    @SuppressLint("DefaultLocale")
    @NonNull @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView != null ? convertView :
                LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2,parent,false);

        Map.Entry<Float, Integer> currentEntry = this.getItem(position);

        TextView textViewKey = currentItemView.findViewById(android.R.id.text1);
        TextView textViewValue = currentItemView.findViewById(android.R.id.text2);

        textViewKey.setText(String.format("%skg", Objects.requireNonNull(currentEntry).getKey()));
        textViewValue.setText(String.format("Number of plates: %d (%d Plates Each side)", currentEntry.getValue(), currentEntry.getValue() / 2));

        textViewKey.setTextColor(Color.parseColor("#FFFFFF"));
        textViewValue.setTextColor(Color.parseColor("#FFFFFF"));


        return currentItemView;
    }
}
