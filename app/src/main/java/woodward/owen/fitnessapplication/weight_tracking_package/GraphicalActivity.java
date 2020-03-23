package woodward.owen.fitnessapplication.weight_tracking_package;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BaseDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import woodward.owen.fitnessapplication.R;
import woodward.owen.fitnessapplication.exercise_package.Exercise;
import woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge.ExerciseViewModel;

public class GraphicalActivity extends AppCompatActivity {


    private LineChart lineChart;
    private ExerciseViewModel exerciseViewModel;
    private List<Exercise> currentExercises = new ArrayList<>();
    public static final String EXTRA_EXERCISE_NAME = "woodard.owen.fitnessapplication.EXTRA_EXERCISE_NAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphical);

        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#86b8ff")));
        exerciseViewModel = new ViewModelProvider(GraphicalActivity.this).get(ExerciseViewModel.class);
        lineChart = findViewById(R.id.lineChartView);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_EXERCISE_NAME)) {
            exerciseViewModel.setName(intent.getStringExtra(EXTRA_EXERCISE_NAME));
        }

        Observe();
        ObserveNameChange();

        //Enables Dragging/ Scaling of the UI
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setDrawGridBackground(false);

        //Removes axis on the right side of the graph
        lineChart.getAxisRight().setEnabled(false);

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
        List<Entry> entries = new ArrayList<>();
        for(int i = 0; i < exercises.size(); i++){
            entries.add(new Entry(exercises.get(i).getReps(),
                    exerciseViewModel.convertToFloat(exercises.get(i).getWeight())));
        }
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
        lineChart.notifyDataSetChanged();
    }

    private class myAxisForamtter extends ValueFormatter implements IAxisValueFormatter {

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            return sdf.format(new Date(Long.parseLong(String.valueOf(value))));
        }
    }
}
