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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import woodward.owen.fitnessapplication.R;
import woodward.owen.fitnessapplication.exercise_package.Exercise;
import woodward.owen.fitnessapplication.weight_tracking_package.viewmodels_packge.ExerciseViewModel;

public class GraphicalActivity extends AppCompatActivity {


    private LineChart lineChart;
    private ExerciseViewModel exerciseViewModel;
    private List<Exercise> currentExercises;
    private List<Exercise> refinedList;
    public static final String LIST_OF_EXERCISES = "ListOfExercises";
    //public static final String EXTRA_EXERCISE_NAME = "woodard.owen.fitnessapplication.EXTRA_EXERCISE_NAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphical);
        lineChart = findViewById(R.id.lineChartView);

        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#86b8ff")));
        exerciseViewModel = new ViewModelProvider(GraphicalActivity.this).get(ExerciseViewModel.class);


        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        currentExercises = bundle.getParcelableArrayList(LIST_OF_EXERCISES);
        assert currentExercises != null;

        convertDates(currentExercises);
        sortDatesInOrder(currentExercises);
        refinedList = sortDataForWeightEntries(currentExercises);

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
        //xAxis.setValueFormatter(new myAxisForamtter());       <-- requires values to be passed in as array

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

    private void convertDates (List<Exercise> exercises) {
        String str;
        for(int x= 0; x< exercises.size(); x++){
            str = exercises.get(x).getDate();
            str = str.replace("-", "/");
            exercises.get(x).setDate(str);
        }
    }

    private void sortDatesInOrder(List<Exercise> exercises) {
        Collections.sort(exercises, new Comparator<Exercise>() {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            @Override
            public int compare(Exercise exerciseDate1, Exercise exerciseDate2) {
                try{
                    return format.parse(exerciseDate1.getDate()).compareTo(format.parse(exerciseDate2.getDate()));
                }
                catch (ParseException ex) {
                    throw new IllegalArgumentException(ex);
                }
            }
        });
    }

    private List<Exercise> sortDataForWeightEntries(List<Exercise> exercises) {
        String currentDate = "";
        List<Exercise> tempStorageList = new ArrayList<>();
        List<Exercise> newExerciseData = new ArrayList<>();

        for(int x = 0; x < exercises.size(); x++){

            if(exercises.get(x).getDate().equals(currentDate)){
                tempStorageList.add(exercises.get(x));
                if(x == exercises.size() - 1) {
                    newExerciseData.add(findHighestWeightForDate(tempStorageList));
                }
            }
            else if(tempStorageList.size() != 0 && !exercises.get(x).getDate().equals(currentDate)) {
                newExerciseData.add(findHighestWeightForDate(tempStorageList));
                currentDate = exercises.get(x).getDate();
                tempStorageList.clear();
                tempStorageList.add(exercises.get(x));
            }
            else {
                currentDate = exercises.get(x).getDate();
                tempStorageList.clear();
                tempStorageList.add(exercises.get(x));
                if(x == exercises.size() - 1) {
                    newExerciseData.add(findHighestWeightForDate(tempStorageList));
                }
            }

        }
        return newExerciseData;

    }

    private Exercise findHighestWeightForDate (List<Exercise> exercises) {
        Exercise bestExercise = new Exercise();
        double currentHighest = 0;
        for(int x = 0; x < exercises.size(); x++) {
            if(exercises.get(x).getWeight() > currentHighest){
                currentHighest = exercises.get(x).getWeight();
                bestExercise = exercises.get(x);
            }
        }
        return bestExercise;
    }

    private double findHighestVolumeForDate (List<Exercise> exercises) {
        double currentHightest = 0;
        for(int x = 0; x < exercises.size(); x++){
            double TotalVolume = (exercises.get(x).getWeight() * exercises.get(x).getReps());
            if(TotalVolume > currentHightest){
                currentHightest = TotalVolume;
            }
        }
        return currentHightest;
    }

    private static class myAxisForamtter extends ValueFormatter {
        private long referenceTimeStamp;
        private DateFormat dateFormat;
        private Date date;

        public myAxisForamtter(long referenceTimeStamp) {
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
