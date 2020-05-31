package woodward.owen.fitnessapplication.weight_tracking_package.adapters_package;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import woodward.owen.fitnessapplication.databinding.ExercisenameItemBinding;
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
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ExercisenameItemBinding itemBinding = ExercisenameItemBinding.inflate(layoutInflater, parent, false);
        return new ExerciseNameHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseNameHolder holder, int position) {
        ExerciseName exerciseName = getItem(position);
        holder.bind(exerciseName);
    }

    class ExerciseNameHolder extends RecyclerView.ViewHolder {
        private ExercisenameItemBinding binding;

        ExerciseNameHolder(ExercisenameItemBinding exercisenameItemBinding) {
            super(exercisenameItemBinding.getRoot());
            this.binding = exercisenameItemBinding; // Setting the Binding

            //setting listener on cardView
            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (listener != null && pos != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(pos));
                }
            });
            //OnLongPress for Removal Of Items
            itemView.setOnLongClickListener(v -> {
               int pos = getAdapterPosition();
               if(longClickListener != null && pos != RecyclerView.NO_POSITION) {
                   longClickListener.onItemLongClickListener(getItem(pos));
                   return true;
               }
               return false;
            });
        }
        void bind(ExerciseName exerciseName) {
            binding.setExerciseName(exerciseName);
            binding.executePendingBindings();
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
