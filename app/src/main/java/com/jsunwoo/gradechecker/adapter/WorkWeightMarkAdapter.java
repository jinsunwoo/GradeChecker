package com.jsunwoo.gradechecker.adapter;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
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

import com.jsunwoo.gradechecker.GradeCheckerResources;
import com.jsunwoo.gradechecker.R;
import com.jsunwoo.gradechecker.activity.CourseWorksActivity;
import com.jsunwoo.gradechecker.entity.GradeScale;
import com.jsunwoo.gradechecker.entity.WorkWeightMark;
import com.jsunwoo.gradechecker.popup.PopUpWWM;

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
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull WorkWeightMarkAdapter.ViewHolder holder, final int position) {

        holder.gradeItem.setText(wwms.get(position).gradeItem);
        holder.gradeWeight.setText(wwms.get(position).weight+"");
        holder.gradeMark.setText(wwms.get(position).mark+"");
        holder.gradeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUpWWM popup = new PopUpWWM(context);
                popup.setWwm(wwms.get(position));
                // 팝업이 나오게!
                popup.show();
                popup.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    // 팝업이 꺼질 때 뭘할지
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        final GradeCheckerResources APP = (GradeCheckerResources) context.getApplicationContext();
                        // 업데이트 된 내용으로 바꿔 줌 wwms 를
                        new AsyncTask<Object, Object, Object>() {
                            @Override
                            protected Object doInBackground(Object... objects) {
                                wwms=APP.db.wwmdao().getCourseIDforBridge(wwms.get(0).cid);
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Object o) {
                                super.onPostExecute(o);
                                // 데이터가 바뀌었으니까 새로고침
                                notifyDataSetChanged();
                            }
                        }.execute();


                    }
                });
            }
        });
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
        TextView gradeItem;
        TextView gradeWeight;
        TextView gradeMark;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gradeItem = itemView.findViewById(R.id.grade_item_editText);
            //gradeItem.addTextChangedListener(wWMcustomEditTextListner);
            gradeWeight = itemView.findViewById(R.id.grade_weight_editText);
            gradeMark = itemView.findViewById(R.id.grade_mark_editText);
        }
    }



}

