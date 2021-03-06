package com.peoit.android.online.pschool.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.peoit.android.online.pschool.R;
import com.peoit.android.online.pschool.config.CommonUtil;
import com.peoit.android.online.pschool.entity.GradeStatInfo;
import com.peoit.android.online.pschool.ui.Base.BaseActivity;
import com.peoit.android.online.pschool.ui.Presenter.GradeInfoPersenter;
import com.peoit.android.online.pschool.ui.adapter.GradeStatAdapter;
import com.peoit.android.online.pschool.ui.view.PullToRefreshLayout;
import com.peoit.android.online.pschool.ui.view.PullableListView;

import java.util.Calendar;
import java.util.Map;

/**
 * 首页
 * <p/>
 * author:libo
 * time:2015/7/14
 * E-mail:boli_android@163.com
 * last: ...
 */
public class GradeInfoActivity extends BaseActivity{

    public static final int GRADE_MONTH = 2;
    public static final int GRADE_MID = 3;
    public static final int GRADE_END = 4;

    private TextView etStart;
    private TextView etEnd;
    private TextView tvSearch;
    private GradeInfoPersenter mPersenter;
    private String start_time;
    private String end_time;

    private PullToRefreshLayout pullLayout;
    private PullableListView pullList;
    private GradeStatAdapter adapter;
    private int type;
    private String title;
    private int mWidth;

    public static void startThisActivity(Activity mAc, int type) {
        Intent intent = new Intent(mAc, GradeInfoActivity.class);
        intent.putExtra("type", type);
        mAc.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_grade_info);
    }

    @Override
    public void initData() {

        type = getIntent().getIntExtra("type", -1);

        switch (type) {
            case GRADE_MONTH:
                title = "月考考试";
                break;
            case GRADE_MID:
                title = "期中考试";
                break;
            case GRADE_END:
                title = "期末考试";
                break;
            default: {
                showToast("传输错误");
                finish();
            }
            break;
        }

        mWidth = (CommonUtil.w_screeen - CommonUtil.dip2px(mContext, 92)) / 2;

        mPersenter = new GradeInfoPersenter(this) {
            @Override
            public Map<String, String> getGradeInfoParam(Map<String, String> params) {
                params.put("stime", start_time);
                params.put("endtime", end_time);
                return params;
            }
        };

        this.adapter = mPersenter.getAdapter(title);
    }

    @Override
    public void initView() {
        getPsActionBar().settitle(title);

        etStart = (TextView) findViewById(R.id.et_start);
        etEnd = (TextView) findViewById(R.id.et_end);
        tvSearch = (TextView) findViewById(R.id.tv_search);
//        setEtWidth(etStart);
//        setEtWidth(etEnd);

        pullLayout = (PullToRefreshLayout) findViewById(R.id.pull_layout);
        pullList = (PullableListView) findViewById(R.id.pull_list);
        pullList.setAdapter(adapter);
    }

    private void setEtWidth(EditText et) {
        ViewGroup.LayoutParams mEtLayoutParams = et.getLayoutParams();
        mEtLayoutParams.width = mWidth;
        et.setLayoutParams(mEtLayoutParams);
    }

    @Override
    public void initListener() {
        etStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 选择日期
                AlertDialog.Builder builder = new AlertDialog.Builder(GradeInfoActivity.this);
                View view = View.inflate(GradeInfoActivity.this, R.layout.dialog_date_time, null);
                final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
                builder.setView(view);

                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);
                etStart.setInputType(InputType.TYPE_NULL);

                builder.setTitle("选取开始查询日期");
                builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuffer sb = new StringBuffer();
                        sb.append(String.format("%d-%02d-%02d",
                                datePicker.getYear(),
                                datePicker.getMonth() + 1,
                                datePicker.getDayOfMonth()));
                        etStart.setText(sb);
                        dialog.cancel();
                    }
                });
                Dialog dialog = builder.create();
                dialog.show();
            }
        });
        etEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 选择日期
                AlertDialog.Builder builder = new AlertDialog.Builder(GradeInfoActivity.this);
                View view = View.inflate(GradeInfoActivity.this, R.layout.dialog_date_time, null);
                final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
                builder.setView(view);

                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);
                etEnd.setInputType(InputType.TYPE_NULL);

                builder.setTitle("选取结束查询日期");
                builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuffer sb = new StringBuffer();
                        sb.append(String.format("%d-%02d-%02d",
                                datePicker.getYear(),
                                datePicker.getMonth() + 1,
                                datePicker.getDayOfMonth()));
                        etEnd.setText(sb);
                        dialog.cancel();
                    }
                });
                Dialog dialog = builder.create();
                dialog.show();
            }
        });

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (match()) {
                    mPersenter.load();
                }
            }
        });
        pullLayout.setOnRefreshListener(mPersenter);
        pullList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GradeStatInfo info = adapter.getItem(position);
                GradeByIdActivity.startThisActivity(mContext, info.getId());
            }
        });
    }

    public boolean match() {
        start_time = etStart.getText().toString();
        if (TextUtils.isEmpty(start_time)) {
            showToast("请输入开始时间");
            return false;
        }
        end_time = etEnd.getText().toString();
        if (TextUtils.isEmpty(end_time)) {
            showToast("请输入结束时间");
            return false;
        }
        return true;
    }
}
