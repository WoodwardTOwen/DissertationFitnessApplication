package woodward.owen.fitnessapplication.weight_tracking_package.adapters_package;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import woodward.owen.fitnessapplication.databinding.CategoryItemBinding;
import woodward.owen.fitnessapplication.exercise_package.Category;

public class CategoryAdapter extends ListAdapter<Category, CategoryAdapter.CategoryHolder> {
    private onItemClickListener listener;
    private onItemLongClickListener longListener;

    public CategoryAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Category> DIFF_CALLBACK = new DiffUtil.ItemCallback<Category>() {
        @Override
        public boolean areItemsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
            return oldItem.getId() == newItem.getId(); //return if id's are the same
        }

        @Override
        public boolean areContentsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
            return oldItem.getCategoryName().equals(newItem.getCategoryName());
        }
    };


    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CategoryItemBinding itemBinding = CategoryItemBinding.inflate(layoutInflater, parent, false);
        return new CategoryHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Category currentCategory = getItem(position);
        holder.bind(currentCategory);
    }

    class CategoryHolder extends RecyclerView.ViewHolder {
        private CategoryItemBinding binding;

        CategoryHolder(CategoryItemBinding categoryItemBinding) {
            super(categoryItemBinding.getRoot());
            this.binding = categoryItemBinding;
            //setting listener on cardView
            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (listener != null && pos != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(pos));
                }
            });

            //setting listener for long clicks to prompt the user to delete a category
            itemView.setOnLongClickListener(v -> {
                int pos = getAdapterPosition();
                    if (longListener != null && pos != RecyclerView.NO_POSITION){
                    longListener.onItemLongClicked(getItem(pos));
                    return true;
                }
                    return false;
            });

        }

        void bind(Category category) {
            binding.setCategory(category);
            binding.executePendingBindings();
        }
    }

    public interface onItemClickListener {
        void onItemClick(Category category);
    }

    public interface onItemLongClickListener {
        boolean onItemLongClicked(Category category);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemLongClickListener(onItemLongClickListener listener) {this.longListener = listener;}

}
