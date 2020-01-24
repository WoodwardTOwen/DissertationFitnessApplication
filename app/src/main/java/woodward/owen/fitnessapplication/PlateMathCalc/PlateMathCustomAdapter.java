package woodward.owen.fitnessapplication.PlateMathCalc;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import woodward.owen.fitnessapplication.R;

public class PlateMathCustomAdapter extends ArrayAdapter<Map.Entry<Float, Integer>> {

    public PlateMathCustomAdapter(Context context, List<Map.Entry<Float, Integer>> map) {
        super(context, android.R.layout.simple_list_item_2, map);
    }

    @NonNull @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View currentItemView = convertView != null ? convertView :
                LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2,parent,false);

        Map.Entry<Float, Integer> currentEntry = this.getItem(position);

        TextView textViewKey = currentItemView.findViewById(android.R.id.text1);
        TextView textViewValue = currentItemView.findViewById(android.R.id.text2);

        textViewKey.setText(String.valueOf(currentEntry.getKey()) + "kg");
        textViewValue.setText("Number of plates: " + String.valueOf(currentEntry.getValue()));

        return currentItemView;
    }
}
