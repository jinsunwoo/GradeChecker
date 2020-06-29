package com.jsunwoo.gradechecker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jsunwoo.gradechecker.R;

public class LetterGradeAdapter extends RecyclerView.Adapter<LetterGradeAdapter.ViewHolder> {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    // viewHolder = item_view. hold => 그 안에 넣을 텍스트 뷰를 여기에 hold 하는 느낌.
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView gradeText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gradeText = itemView.findViewById(R.id.textView3);

        }
    }
}
