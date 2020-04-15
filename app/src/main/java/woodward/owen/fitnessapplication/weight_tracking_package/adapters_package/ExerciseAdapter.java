package woodward.owen.fitnessapplication.weight_tracking_package.adapters_package;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import woodward.owen.fitnessapplication.databinding.ExerciseItemBinding;
import woodward.owen.fitnessapplication.exercise_package.Exercise;
import woodward.owen.fitnessapplication.R;
import woodward.owen.fitnessapplication.weight_tracking_package.dao_package.ExerciseDao;
import woodward.owen.fitnessapplication.weight_tracking_package.database_package.ExerciseDatabase;

public class ExerciseAdapter extends ListAdapter<Exercise, ExerciseAdapter.ExerciseHolder> {
    private onItemClickListener listener;

    //Defines Comparison Logic between two lists
    public ExerciseAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Exercise> DIFF_CALLBACK = new DiffUtil.ItemCallback<Exercise>() {
        @Override
        public boolean areItemsTheSame(@NonNull Exercise oldItem, @NonNull Exercise newItem) {
            return oldItem.getId() == newItem.getId(); //return if id's are the same
        }

        @Override
        public boolean areContentsTheSame(@NonNull Exercise oldItem, @NonNull Exercise newItem) {
            return oldItem.getExerciseName().equals(newItem.getExerciseName()) &&
                    oldItem.getWeight() == newItem.getWeight() && oldItem.getReps() == newItem.getReps() &&
                    oldItem.getRpe() == newItem.getRpe();
        }
    };

    @NonNull
    @Override
    public ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
        return new ExerciseHolder(itemView);
    }

    //Takes care of data from the Data objects and placing them in the exercise holder
    @Override
    public void onBindViewHolder(@NonNull ExerciseHolder holder, int position) {
        Exercise currentExercise = getItem(position);

        String sourceStringRPE = "<b>" + "RPE: " + "</b>" + currentExercise.getRpe();
        String sourceStringReps = "<b>" + "Reps: " + "</b>" + currentExercise.getReps();
        String sourceStringDate = "<b>" + "Date: " + "</b>" + currentExercise.getDate();


        holder.textViewExerciseName.setText(currentExercise.getExerciseName());
        holder.textViewWeight.setText(String.format("%sKg", String.valueOf(currentExercise.getWeight())));
        holder.textViewRPE.setText(HtmlCompat.fromHtml(sourceStringRPE, HtmlCompat.FROM_HTML_MODE_COMPACT));
        holder.textViewReps.setText(HtmlCompat.fromHtml(sourceStringReps, HtmlCompat.FROM_HTML_MODE_COMPACT));
        holder.textViewDate.setText(HtmlCompat.fromHtml(sourceStringDate, HtmlCompat.FROM_HTML_MODE_COMPACT));

    }

    public Exercise getExercisePosition(int position) {
        return getItem(position);
    }

    class ExerciseHolder extends RecyclerView.ViewHolder {
        private final TextView textViewExerciseName;
        private final TextView textViewReps;
        private final TextView textViewRPE;
        private final TextView textViewWeight;
        private final TextView textViewDate;

        public ExerciseHolder(@NonNull View itemView) {
            super(itemView);
            textViewExerciseName = itemView.findViewById(R.id.textViewExerciseName);
            textViewReps = itemView.findViewById(R.id.textViewReps);
            textViewRPE = itemView.findViewById(R.id.textViewRPE);
            textViewWeight = itemView.findViewById(R.id.textViewWeight);
            textViewDate = itemView.findViewById(R.id.textViewDate);

            //setting listener on cardView
            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (listener != null && pos != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(pos));
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
