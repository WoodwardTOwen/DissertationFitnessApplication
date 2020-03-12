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
import woodward.owen.fitnessapplication.exercise_package.Category;

public class CategoryAdapter extends ListAdapter<Category, CategoryAdapter.CategoryHolder> {
    private onItemClickListener listener;

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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Category currentCategory = getItem(position);
        holder.categoryName.setText(currentCategory.getCategoryName());
    }

    class CategoryHolder extends RecyclerView.ViewHolder {
        private TextView categoryName;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.recyclerViewCategoryTextView);

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
        void onItemClick(Category category);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }


}
