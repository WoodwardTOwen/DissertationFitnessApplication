package woodward.owen.fitnessapplication.weight_tracking_package;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import woodward.owen.fitnessapplication.R;
import woodward.owen.fitnessapplication.exercise_package.Exercise;
import woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge.ExerciseViewModel;
import woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge.GraphicalViewModel;

public class GraphicalActivity extends AppCompatActivity {


    private LineChart lineChart;
    private GraphicalViewModel graphicalViewModel;
    private List<Exercise> currentExercises;
    private String dateArray[];
    private String filter = "";
    public static final String LIST_OF_EXERCISES = "ListOfExercises";
    public static final String FILTER_OPTION = "Filter";
    //public static final String EXTRA_EXERCISE_NAME = "woodard.owen.fitnessapplication.EXTRA_EXERCISE_NAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphical);
        lineChart = findViewById(R.id.lineChartView);

        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#86b8ff")));
        graphicalViewModel = new ViewModelProvider(GraphicalActivity.this).get(GraphicalViewModel.class);

        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        filter = intent.getStringExtra(FILTER_OPTION);
        currentExercises = bundle.getParcelableArrayList(LIST_OF_EXERCISES);

        dateArray = new String[currentExercises.size()];

        for(int x = 0; x < currentExercises.size(); x++){
            dateArray[x] = currentExercises.get(x).getDate();
        }


        setEntries(currentExercises);
        //Enables Dragging/ Scaling of the UI
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setDrawGridBackground(false);

        //Removes axis on the right side of the graph
        lineChart.getAxisRight().setEnabled(false);

    }

    private void setEntries (List<Exercise> exercises) {
        //Enables dragging of the graph on the UI
        List<Entry> entries = new ArrayList<>();
        entries = createEntries(filter, currentExercises);
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
        xAxis.setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(dateArray));       //<-- requires values to be passed in as array
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelRotationAngle(-45);


        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setTextColor(Color.WHITE);

        yAxis.setTextSize(12); xAxis.setTextSize(12);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        LineData lineData = new LineData(dataSets);
        lineChart.setData(lineData);
        lineChart.animateX(2000);
        lineChart.invalidate();
        lineChart.getLegend().setEnabled(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.notifyDataSetChanged();
    }

    private List<Entry> createEntries (String filter, List<Exercise> exercises) {
        List<Entry> entries = new ArrayList<>();
        switch(filter) {
            case "MaxWeight" :
                for(int i = 0; i < exercises.size(); i++){
                    entries.add(new Entry(i,
                            graphicalViewModel.convertToFloat(exercises.get(i).getWeight())));
                }
                return entries;
            case "MaxVolume":
                for(int i = 0; i < exercises.size(); i++){
                    entries.add(new Entry(i,
                            graphicalViewModel.convertToFloat(exercises.get(i).getTotalVolume())));
                }
                return entries;
        }
        return entries;
    }

    private static class myAxisFormatter extends ValueFormatter {
        private long referenceTimeStamp;
        private DateFormat dateFormat;
        private Date date;

        public myAxisFormatter(long referenceTimeStamp) {
            this.referenceTimeStamp = referenceTimeStamp;
            this.dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            this.date = new Date();
        }

        @Override
        public String getFormattedValue(float value){

            long convertedTimeStamp = (long) value;
            //retrieve original timestamp
            long originalTime = referenceTimeStamp + convertedTimeStamp;
            //Convert timestamp
            return getDateString(originalTime);
        }

        private String getDateString(long timestamp) {
            try {
                date.setTime(timestamp);
                return dateFormat.format(date);
            }
            catch (Exception ex) {
                return "ERROR";
            }
        }
    }
}
