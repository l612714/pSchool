package com.peoit.android.online.pschool.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peoit.android.online.pschool.R;
import com.peoit.android.online.pschool.ui.Base.BaseActivity;

public class QueryGradeActivity extends BaseActivity implements View.OnClickListener {
    EditText etStuname;
    TextView tvSearch;
    LinearLayout llExamMonth;
    LinearLayout llExamMid;
    LinearLayout llExamEnd;

    public static void startThisActivity(Activity mAc) {
        Intent intent = new Intent(mAc, QueryGradeActivity.class);
        mAc.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_query_grade);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        getPsActionBar().settitle(getString(R.string.query_grade_title));

        etStuname = (EditText) findViewById(R.id.et_stuname);
        tvSearch = (TextView) findViewById(R.id.tv_search);

        llExamMonth = (LinearLayout) findViewById(R.id.ll_exam_month);
        llExamMid = (LinearLayout) findViewById(R.id.ll_exam_mid);
        llExamEnd = (LinearLayout) findViewById(R.id.ll_exam_end);
    }

    @Override
    public void initListener() {
        llExamMonth.setOnClickListener(this);
        llExamMid.setOnClickListener(this);
        llExamEnd.setOnClickListener(this);

        tvSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == llExamMonth) {
            GradeInfoActivity.startThisActivity(getActivity(), GradeInfoActivity.GRADE_MONTH);
        } else if (v == llExamMid) {
            GradeInfoActivity.startThisActivity(getActivity(), GradeInfoActivity.GRADE_MID);
        } else if (v == llExamEnd) {
            GradeInfoActivity.startThisActivity(getActivity(), GradeInfoActivity.GRADE_END);
        } else if (v == tvSearch) {
            showToast(getString(R.string.query_txt));
        }
    }
}
