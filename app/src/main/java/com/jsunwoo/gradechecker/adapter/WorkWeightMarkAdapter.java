package com.jsunwoo.gradechecker.adapter;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jsunwoo.gradechecker.R;
import com.jsunwoo.gradechecker.activity.CourseWorksActivity;
import com.jsunwoo.gradechecker.entity.GradeScale;
import com.jsunwoo.gradechecker.entity.WorkWeightMark;

import java.util.ArrayList;
import java.util.List;

public class WorkWeightMarkAdapter extends RecyclerView.Adapter<WorkWeightMarkAdapter.ViewHolder> {
    Context context;
    public List<WorkWeightMark> wwms;
    private static final String TAG = WorkWeightMarkAdapter.class.getSimpleName();


    public WorkWeightMarkAdapter(Context context, List<WorkWeightMark> wwms) {
        this.context = context;
        this.wwms = wwms;
    }

    @NonNull
    @Override
    public WorkWeightMarkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_work_weight_mark, parent, false);
        return new ViewHolder(itemView, new WWMcustomEditTextListner());
    }


    @Override
    public void onBindViewHolder(@NonNull WorkWeightMarkAdapter.ViewHolder holder, final int position) {

        holder.gradeItem.setText(wwms.get(position).gradeItem);
        holder.gradeWeight.setText(wwms.get(position).weight+"");
        holder.gradeMark.setText(wwms.get(position).mark+"");
        holder.wWMcustomEditTextListner.updatePosition(position);

    }


    @Override
    public int getItemCount() {
        return wwms.size();
    }

    // 주소값 대체 하려고 (주소값을 넘겨주기 위해서)
    public void setWwms(List<WorkWeightMark> wwms){
        this.wwms=wwms;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        EditText gradeItem;
        EditText gradeWeight;
        EditText gradeMark;
        WWMcustomEditTextListner wWMcustomEditTextListner;
        public ViewHolder(@NonNull View itemView, WWMcustomEditTextListner wWMcustomEditTextListner) {
            super(itemView);
            gradeItem = itemView.findViewById(R.id.grade_item_editText);
            this.wWMcustomEditTextListner=wWMcustomEditTextListner;
            gradeItem.addTextChangedListener(wWMcustomEditTextListner);
            gradeWeight = itemView.findViewById(R.id.grade_weight_editText);
            gradeMark = itemView.findViewById(R.id.grade_mark_editText);
        }
    }

    private class WWMcustomEditTextListner implements TextWatcher {

        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            wwms.get(position).gradeItem = s.toString();
        }
    }

}

