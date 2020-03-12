package woodward.owen.fitnessapplication.graphical_analysis_package;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import woodward.owen.fitnessapplication.R;
import woodward.owen.fitnessapplication.exercise_package.CategoryType;
import woodward.owen.fitnessapplication.weight_tracking_package.AddExercise;

public class GraphicalActivity extends AppCompatActivity {


    private LineChart lineChart;
    private GraphViewModel graphicViewModel;
    private Spinner catSpinner;
    private Spinner exerciseSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphical);

        catSpinner = findViewById(R.id.categorySpinnerGraphical);
        exerciseSpinner = findViewById(R.id.exerciseSpinnerGraphical);
        lineChart = findViewById(R.id.lineChartView);
        graphicViewModel = new ViewModelProvider(GraphicalActivity.this).get(GraphViewModel.class);
        graphicViewModel.assignIOValues(getApplication());
        checkSelectedItem();



        //Allows for gestures with the graph -> may not need this for the main system -> prototype its fine though
        //lineChart.setOnChartGestureListener(this);
        //Callback for highlighting values on touch request
        //lineChart.setOnChartValueSelectedListener(this);

        //Enables dragging of the graph on the UI
        lineChart.setDragEnabled(true);

        //Enables/Disables scaling of the graph
        lineChart.setScaleEnabled(true);

        //Removes axis on the right side of the graph
        lineChart.getAxisRight().setEnabled(false);

        //Call query here for all information in relation to a particular exerciseName
        //If none exists through a prompt on screen that the user cannot view any graph data in relation to that exercise
        //because it does not exist
        List<Entry> entries = new ArrayList<>();

        entries.add(new Entry(0, 60f));
        entries.add(new Entry(2, 54f));
        entries.add(new Entry(6, 78f));
        entries.add(new Entry(8, 43f));
        entries.add(new Entry(4, 11f));
        //to add the entries from the data, use a for loop to gather add them all into the 'entries' list

        //for this prototype, manual implementation will be used

        LineDataSet lineDataSet = new LineDataSet(entries, "The Data Ting"); //This is where it would place the information to be display
        lineDataSet.setColor(Color.BLUE);
        lineDataSet.setFillColor(Color.WHITE);
        lineDataSet.setValueTextColor(Color.WHITE);
        lineDataSet.setCircleColor(Color.WHITE);
        lineDataSet.setCircleRadius(5f);
        lineDataSet.setValueTextSize(10);
        lineDataSet.setLineWidth(1f);

        lineDataSet.setFillAlpha(110);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setTextColor(Color.WHITE);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setTextColor(Color.WHITE);

        yAxis.setTextSize(12); xAxis.setTextSize(12);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        LineData lineData = new LineData(dataSets);
        lineChart.setData(lineData);



    }


    private Spinner setAdapterCatAdapter() {

        List<CategoryType> catList = new ArrayList<>(graphicViewModel.getPossibleNames().keySet());

        ArrayAdapter<CategoryType> catAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, catList);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catSpinner.setAdapter(catAdapter);
        return catSpinner;
    }

    private void checkSelectedItem() {
        catSpinner = setAdapterCatAdapter();

        catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (Map.Entry<CategoryType, List<String>> entry : graphicViewModel.getPossibleNames().entrySet()) {
                    if (entry.getKey().equals(catSpinner.getSelectedItem())) {
                        graphicViewModel.setExerciseList(entry.getValue());

                        ArrayAdapter<String> exerciseAdapter = new ArrayAdapter<>(GraphicalActivity.this,
                                android.R.layout.simple_spinner_item, graphicViewModel.getExerciseList());
                        exerciseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        exerciseSpinner.setAdapter(exerciseAdapter);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}
