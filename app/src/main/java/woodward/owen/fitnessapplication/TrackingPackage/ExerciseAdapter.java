package woodward.owen.fitnessapplication.TrackingPackage;

import android.app.Application;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.opencensus.resource.Resource;
import woodward.owen.fitnessapplication.ExercisePackage.Exercise;
import woodward.owen.fitnessapplication.R;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseHolder> {

    private List<Exercise> exerciseList = new ArrayList<>();
    private onItemClickListener listener;

    @NonNull
    @Override
    public ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
        return new ExerciseHolder(itemView);
    }

    //Takes care of data from the Data objects and placing them in the exercise holder
    @Override
    public void onBindViewHolder(@NonNull ExerciseHolder holder, int position) {
        Exercise currentExercise = exerciseList.get(position);

        String sourceStringRPE = "<b>" + "RPE: " + "</b>" + currentExercise.getRpe();
        String sourceStringReps = "<b>" + "Reps: " + "</b>" + currentExercise.getReps();
        String sourceStringDate = "<b>" + "Date: " + "</b>" + currentExercise.getDate();


        holder.textViewExerciseName.setText(currentExercise.getExerciseName());
        holder.textViewWeight.setText(String.valueOf(currentExercise.getWeight()) + "Kg");
        holder.textViewRPE.setText(Html.fromHtml(sourceStringRPE));
        holder.textViewReps.setText(Html.fromHtml(sourceStringReps));
        holder.textViewDate.setText(Html.fromHtml(sourceStringDate));
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public void setExercise(List<Exercise> exercise) {
        this.exerciseList = exercise;
        notifyDataSetChanged();
    }

    public Exercise getExercisePosition(int position) {
        return exerciseList.get(position);
    }

    class ExerciseHolder extends RecyclerView.ViewHolder {
        private TextView textViewExerciseName;
        private TextView textViewReps;
        private TextView textViewRPE;
        private TextView textViewWeight;
        private TextView textViewDate;

        public ExerciseHolder(@NonNull View itemView) {
            super(itemView);
            textViewExerciseName = itemView.findViewById(R.id.textViewExerciseName);
            textViewReps = itemView.findViewById(R.id.textViewReps);
            textViewRPE = itemView.findViewById(R.id.textViewRPE);
            textViewWeight = itemView.findViewById(R.id.textViewWeight);
            textViewDate = itemView.findViewById(R.id.textViewDate);

            //setting listener on cardView
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (listener != null && pos != RecyclerView.NO_POSITION) {
                        listener.onItemClick(exerciseList.get(pos));
                    }
                }
            });

        }
    }

    public interface onItemClickListener {
        void onItemClick(Exercise exercise);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

}