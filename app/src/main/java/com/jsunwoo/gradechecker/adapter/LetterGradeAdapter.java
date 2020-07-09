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
import com.jsunwoo.gradechecker.entity.GradeScale;

import java.util.ArrayList;
import java.util.List;

public class LetterGradeAdapter extends RecyclerView.Adapter<LetterGradeAdapter.ViewHolder> {
    Context context;
    private String[] letterGrade;
    private int[] gradeCriteria;
    private GradeScale[] gradescales;
    // context 상태 (안드로이드 디바이스에 있는 모든 상태 정보를 가지고 있음 ((파일상태, 카메라 상태 etc)).)
    public LetterGradeAdapter(Context context, List<GradeScale> gradescales) {
        this.context = context;
        this.letterGrade = context.getResources().getStringArray(R.array.letter_grade);
        this.gradeCriteria = context.getResources().getIntArray(R.array.percentage_value);
        this.gradescales = new GradeScale[letterGrade.length];
        if(gradescales!=null) {
            for (int i = 0; i < gradescales.size(); i++) {
                this.gradescales[i] = gradescales.get(i);
            }
        }else {
            for (int i = 0; i < letterGrade.length; i++) {
                GradeScale gs = new GradeScale();
                gs.alphabet = letterGrade[i];
                gs.lowerCut = gradeCriteria[i];
                gs.gid = i + 1;
                if (i <= 0) {
                    gs.upperCut = 100;
                } else {
                    if (gradeCriteria[i - 1] <= 0) {
                        gs.upperCut = 0;
                    } else {
                        gs.upperCut = gradeCriteria[i - 1] - 1;
                    }
                }
                this.gradescales[i] = gs;
            }
        }

    }

    public GradeScale[] getGradescales() {
        for (GradeScale gs : gradescales) {
            Log.i(LetterGradeAdapter.class.getSimpleName(), "getGradescales: "+gs.gid+" : "+gs.lowerCut+", "+gs.upperCut);
        }
        return gradescales;
    }

    @NonNull
    @Override
    // 보이는건 6개, 위아래로 1개씩 더 (8개 뷰 만드는거 여기서 함)
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // layoutinflater : 부풀게 해주는 (xml 이 recipe 같은 느낌). parent 는 여기서 recycler view. attachToRoot 에서 root 는 원래 parent.
        // layout 을 root 인 전체화면이 아니라 바로 parent 인 리사이클러 뷰 안에 넣겠다는 말.
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_letter_grade, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    // 데이터 랑 뷰 를 묶어줌. 재사용되는거는 다 여기서 동작함.
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.gradeText.setText(gradescales[position].alphabet);
        // setText 라서 string 으로 받아줘야되서
        holder.gradeCut1.setText(""+ gradescales[position].lowerCut);
        holder.gradeCut2.setText(""+gradescales[position].upperCut);
        holder.gradeCut1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 빈칸이 아닐 경우에만 해당
                if (!s.toString().isEmpty()) {
                    int cut = Integer.parseInt(s.toString());
                    gradescales[position].lowerCut = cut;
                    if (cut > 100) {
                        Toast.makeText(context, "Grade should be no greater than 100", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        holder.gradeCut2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 빈칸이 아닐 경우에만 해당
                if (!s.toString().isEmpty()) {
                    int cut = Integer.parseInt(s.toString());
                    gradescales[position].upperCut = cut;
                    if (cut > 100) {
                        Toast.makeText(context, "Grade should be no greater than 100", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return letterGrade.length;
    }

    // viewHolder = item_view. hold => 그 안에 넣을 텍스트 뷰를 여기에 hold 하는 느낌.
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView gradeText;
        EditText gradeCut1;
        EditText gradeCut2;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gradeText = itemView.findViewById(R.id.gradeAlphabet_textView);
            gradeCut1 = itemView.findViewById(R.id.gradeCut1_editText);
            gradeCut2 = itemView.findViewById(R.id.gradeCut2_editText);
        }
    }
}
