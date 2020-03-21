package woodward.owen.fitnessapplication.weight_tracking_package;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BaseDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import woodward.owen.fitnessapplication.R;
import woodward.owen.fitnessapplication.exercise_package.Exercise;
import woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge.ExerciseViewModel;

public class GraphicalActivity extends AppCompatActivity {


    private LineChart lineChart;
    private ExerciseViewModel exerciseViewModel;
    private List<Exercise> currentExercises = new ArrayList<>();
    private List<Entry> entries = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphical);

        exerciseViewModel = new ViewModelProvider(GraphicalActivity.this).get(ExerciseViewModel.class);
        lineChart = findViewById(R.id.lineChartView);

        exerciseViewModel.setName("Hack Squat");

        Observe();
        ObserveNameChange();


        lineChart.setDragEnabled(true);

        //Enables/Disables scaling of the graph
        lineChart.setScaleEnabled(true);

        //Removes axis on the right side of the graph
        lineChart.getAxisRight().setEnabled(false);
        //Allows for gestures with the graph -> may not need this for the main system -> prototype its fine though
        //lineChart.setOnChartGestureListener(this);
        //Callback for highlighting values on touch request
        //lineChart.setOnChartValueSelectedListener(this);


        //Call query here for all information in relation to a particular exerciseName
        //If none exists through a prompt on screen that the user cannot view any graph data in relation to that exercise
        //because it does not exist


        /*entries.add(new Entry(0, 60f));
        entries.add(new Entry(2, 54f));
        entries.add(new Entry(6, 78f));
        entries.add(new Entry(8, 43f));
        entries.add(new Entry(4, 11f));*/
        //to add the entries from the data, use a for loop to gather add them all into the 'entries' list

        //for this prototype, manual implementation will be used
    }

    private void Observe() {
        exerciseViewModel.getListOfExercisesGraphical().observe(GraphicalActivity.this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                if(currentExercises.size() != 0){
                    currentExercises.clear();
                }
                currentExercises = exercises;
                setEntries(currentExercises);
            }
        });
    }

    private void ObserveNameChange () {
        exerciseViewModel.getCurrentName().observe(GraphicalActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(GraphicalActivity.this, "The Name has been changed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setEntries (List<Exercise> exercises) {
        //Enables dragging of the graph on the UI
        for(int i = 0; i < exercises.size(); i++){
            entries.add(new Entry(exercises.get(i).getReps(),
                    exercises.get(i).getRpe()));
        }



        //Call query here for all information in relation to a particular exerciseName
        //If none exists through a prompt on screen that the user cannot view any graph data in relation to that exercise
        //because it does not exist


        /*entries.add(new Entry(0, 60f));
        entries.add(new Entry(2, 54f));
        entries.add(new Entry(6, 78f));
        entries.add(new Entry(8, 43f));
        entries.add(new Entry(4, 11f));*/
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
}
