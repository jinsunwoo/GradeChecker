package com.jsunwoo.gradechecker.adapter;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jsunwoo.gradechecker.R;
import com.jsunwoo.gradechecker.entity.GradeScale;
import java.util.ArrayList;
import java.util.List;

public class WorkWeightMarkAdapter extends RecyclerView.Adapter<WorkWeightMarkAdapter.ViewHolder> {
    Context context;
    public WorkWeightMarkAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public WorkWeightMarkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_work_weight_mark, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkWeightMarkAdapter.ViewHolder holder, final int position) {
        holder.gradeItem.setText("");
        holder.gradeWeight.setText("");
        holder.gradeMark.setText("");

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        EditText gradeItem;
        EditText gradeWeight;
        EditText gradeMark;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gradeItem = itemView.findViewById(R.id.grade_item_editText);
            gradeWeight = itemView.findViewById(R.id.grade_weight_editText);
            gradeMark = itemView.findViewById(R.id.grade_mark_editText);
        }
    }
}

