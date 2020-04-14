package woodward.owen.fitnessapplication.weight_tracking_package;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.MPPointF;

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
    private String[] dateArray;
    private String filter = "";
    public static final String LIST_OF_EXERCISES = "ListOfExercises";
    public static final String FILTER_OPTION = "Filter";
    public static final String EXTRA_EXERCISE_NAME = "woodard.owen.fitnessApplication.EXTRA_EXERCISE_NAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphical);
        lineChart = findViewById(R.id.lineChartView);
        TextView titleTextView = findViewById(R.id.Graphical_Analysis_Title);

        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#86b8ff")));
        graphicalViewModel = new ViewModelProvider(GraphicalActivity.this).get(GraphicalViewModel.class);

        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        filter = intent.getStringExtra(FILTER_OPTION);
        String exerciseName = intent.getStringExtra(EXTRA_EXERCISE_NAME);
        currentExercises = bundle.getParcelableArrayList(LIST_OF_EXERCISES);

        titleTextView.setText(String.format("%s for %s", filter, exerciseName));

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
        entries = createEntries(filter, exercises);
        LineDataSet lineDataSet = new LineDataSet(entries, "The Data Ting"); //This is where it would place the information to be display

        int color = ContextCompat.getColor(getApplicationContext(), R.color.colorDatePicker);

        lineDataSet.setColor(Color.BLACK);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(color);
        lineDataSet.setValueTextColor(Color.WHITE);
        lineDataSet.setCircleColor(color);
        lineDataSet.setCircleRadius(5f);
        lineDataSet.setValueTextSize(12);
        lineDataSet.setLineWidth(2f);

        lineDataSet.setFillAlpha(80);

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

        IMarker marker = new CustomMarketView(getApplicationContext(), R.layout.marker_layout);
        lineChart.setMarker(marker);
        lineChart.getLegend().setEnabled(false);
        lineChart.setExtraRightOffset(30);
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


    public class CustomMarketView extends MarkerView {

        private TextView textview;
        /**
         * Constructor. Sets up the MarkerView with a custom layout resource.
         *
         * @param context
         * @param layoutResource the layout resource to use for the MarkerView
         */
        public CustomMarketView(Context context, int layoutResource) {
            super(context, layoutResource);

            //NEED TO ESTABLISH TEXTVIEW HERE ONCE LAYOUT HAS BEEN CREATED
            textview = findViewById(R.id.marketContentTV);
        }

        @Override
        public void refreshContent (Entry e, Highlight highlight) {
            int xValue = (int) e.getX();

            String value = GraphicalAnalysisMethods.FindXAxisValue(currentExercises, xValue);

            textview.setText(value);
            super.refreshContent(e, highlight);
        }

        private MPPointF offset;

        @Override
        public MPPointF getOffset() {
            if(offset == null) {
                offset = new MPPointF(-(getWidth()), -getHeight());
            }

            return offset;
        }
    }
}
