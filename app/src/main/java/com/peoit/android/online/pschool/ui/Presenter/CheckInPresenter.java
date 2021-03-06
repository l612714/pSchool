package com.peoit.android.online.pschool.ui.Presenter;

import com.peoit.android.online.pschool.ActBase;
import com.peoit.android.online.pschool.R;
import com.peoit.android.online.pschool.config.NetConstants;
import com.peoit.android.online.pschool.entity.CheckInInfo;
import com.peoit.android.online.pschool.net.CallBack;
import com.peoit.android.online.pschool.ui.Base.BasePresenter;
import com.peoit.android.online.pschool.ui.activity.CheckInActivity;
import com.peoit.android.online.pschool.ui.adapter.CheckInAdapter;

import java.util.List;
import java.util.Map;

/**
 * author:libo
 * time:2015/8/12
 * E-mail:boli_android@163.com
 * last: ...
 */
public abstract class CheckInPresenter extends BasePresenter<CheckInInfo> {
    private CheckInActivity mAc;
    private CheckInAdapter adapter;
    private boolean isFirst = true;
    private String day;

    public CheckInPresenter(ActBase actBase) {
        super(actBase);
        mAc = (CheckInActivity) actBase;
    }

    public CheckInAdapter getAdapter(){
        this.adapter = new CheckInAdapter(mActBase.getActivity(), R.layout.act_check_in_item);
        return this.adapter;

    }

    public void doLoadCheckIn(String day){
        this.day = day;
//        if (isFirst) {
//            mActBase.showLoadingDialog("正在加载...");
//            isFirst = false;
//        }

        mActBase.getUIShowPresenter().doShowloading();

        request(NetConstants.NET_CHECK_IN_LIST, new CallBack<CheckInInfo>() {
            @Override
            public void onSimpleFailure(int error, String errorMsg) {
                mActBase.onResponseFailure(error, errorMsg);
                isFirst = true;
                mActBase.getUIShowPresenter().doShowNodata(R.drawable.nocheck_in);
            }

            @Override
            public void onSimpleSuccessList(List<CheckInInfo> result) {
                System.out.println(">>>>>>>>>考勤查询数据：" + result);
                if (result != null && result.size() != 0) {
                    mActBase.getUIShowPresenter().doShowData();
                    adapter.upDateList(result);
                } else {
                    mActBase.getUIShowPresenter().doShowNodata(R.drawable.nocheck_in);
                    isFirst = true;
                }

            }
        });
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = getSignParams();
        params.put("day", day);
        return getCheckInPresenter(params);
    }

    public abstract Map<String, String> getCheckInPresenter(Map<String, String> params);

    @Override
    public Class<CheckInInfo> getGsonClass() {
        return CheckInInfo.class;
    }

}
