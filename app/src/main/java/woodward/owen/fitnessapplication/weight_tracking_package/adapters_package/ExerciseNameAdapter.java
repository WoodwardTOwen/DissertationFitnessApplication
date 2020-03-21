package woodward.owen.fitnessapplication.weight_tracking_package.adapters_package;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import woodward.owen.fitnessapplication.R;
import woodward.owen.fitnessapplication.exercise_package.ExerciseName;

public class ExerciseNameAdapter extends ListAdapter<ExerciseName, ExerciseNameAdapter.ExerciseNameHolder> {
    private onItemClickListener listener;
    private onItemLongClickListenerInterface longClickListener;

    public ExerciseNameAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<ExerciseName> DIFF_CALLBACK = new DiffUtil.ItemCallback<ExerciseName>() {
        @Override
        public boolean areItemsTheSame(@NonNull ExerciseName oldItem, @NonNull ExerciseName newItem) {
            return oldItem.getId() == newItem.getId(); //return if id's are the same
        }

        @Override
        public boolean areContentsTheSame(@NonNull ExerciseName oldItem, @NonNull ExerciseName newItem) {
            return oldItem.getExerciseName().equals(newItem.getExerciseName());
        }
    };


    @NonNull
    @Override
    public ExerciseNameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercisename_item, parent, false);
        return new ExerciseNameHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseNameHolder holder, int position) {
        ExerciseName currentExercise = getItem(position);
        holder.exerciseName.setText(currentExercise.getExerciseName());
    }

    class ExerciseNameHolder extends RecyclerView.ViewHolder {
        private final TextView exerciseName;

        public ExerciseNameHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.recyclerViewExerciseTextView);

            //setting listener on cardView
            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (listener != null && pos != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(pos));
                }
            });

            itemView.setOnLongClickListener(v -> {
               int pos = getAdapterPosition();
               if(longClickListener != null && pos != RecyclerView.NO_POSITION) {
                   longClickListener.onItemLongClickListener(getItem(pos));
                   return true;
               }
               return false;
            });

        }
    }

    public interface onItemClickListener {
        void onItemClick(ExerciseName exercise);
    }

    public interface onItemLongClickListenerInterface {
        void onItemLongClickListener(ExerciseName exerciseName);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemLongClickListener(onItemLongClickListenerInterface listener) {this.longClickListener = listener; }
}
