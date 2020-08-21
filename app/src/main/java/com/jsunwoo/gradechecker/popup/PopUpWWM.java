package com.jsunwoo.gradechecker.popup;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jsunwoo.gradechecker.GradeCheckerResources;
import com.jsunwoo.gradechecker.R;
import com.jsunwoo.gradechecker.entity.WorkWeightMark;

public class PopUpWWM extends Dialog {
    private WorkWeightMark wwm;

    private EditText gradeItem, percentage, mark;
    public void setWwm(WorkWeightMark wwm) {
        this.wwm = wwm;
    }

    public PopUpWWM(@NonNull Context context) {
        super(context);
    }

    public PopUpWWM(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected PopUpWWM(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_wwm);
        gradeItem = findViewById(R.id.editTextGradeItem);
        percentage = findViewById(R.id.editTextPercentage);
        mark = findViewById(R.id.editTextMark);
        gradeItem.setText(wwm.gradeItem);
        // 숫자라서 텍스트로 바꿔줘야 함
        percentage.setText(wwm.weight+"");
        mark.setText(wwm.mark+"");
        Button back = findViewById(R.id.back);
        Button save = findViewById(R.id.save);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 화면에서 팝업만 사라짐 (activity 에서 finish 기능)
                dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // activity 가 아니라서 바로 getapplication 안되고 이 방식으로 접근
                final GradeCheckerResources APP = (GradeCheckerResources) getContext().getApplicationContext();
                wwm.gradeItem=gradeItem.getText().toString();
                wwm.weight=Integer.parseInt(percentage.getText().toString());
                wwm.mark=Integer.parseInt(mark.getText().toString());
                new AsyncTask<Object,Object,Object>() {

                    @Override
                    protected Object doInBackground(Object... objects) {
                        APP.db.wwmdao().insertAll(wwm);
                        return null;
                    }
                    // db 작업이 완료 된 후 해줘야 하니까 여기서 dismiss (팝업창 사라지는 거)
                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        dismiss();
                    }
                }.execute();


            }
        });


    }
}
