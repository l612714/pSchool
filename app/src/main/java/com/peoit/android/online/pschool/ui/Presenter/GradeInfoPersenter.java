package com.peoit.android.online.pschool.ui.Presenter;

import com.peoit.android.online.pschool.ActBase;
import com.peoit.android.online.pschool.R;
import com.peoit.android.online.pschool.config.CommonUtil;
import com.peoit.android.online.pschool.config.NetConstants;
import com.peoit.android.online.pschool.entity.GradeStatInfo;
import com.peoit.android.online.pschool.net.CallBack;
import com.peoit.android.online.pschool.ui.Base.BasePresenter;
import com.peoit.android.online.pschool.ui.activity.GradeInfoActivity;
import com.peoit.android.online.pschool.ui.adapter.GradeStatAdapter;
import com.peoit.android.online.pschool.ui.view.PullToRefreshLayout;

import java.util.List;
import java.util.Map;

/**
 * 考试列表查询
 * author:libo
 * time:2015/8/9
 * E-mail:boli_android@163.com
 * last: ...
 */
public abstract class GradeInfoPersenter extends BasePresenter<GradeStatInfo> implements PullToRefreshLayout.OnRefreshListener {

    private final GradeInfoActivity mAc;
    private int skip = 0;
    private int pagesize = 10;
    private boolean isFirst = true;
    private GradeStatAdapter adapter;
    private PullToRefreshLayout loadLayout;

    public GradeInfoPersenter(ActBase actBase) {
        super(actBase);
        mAc = (GradeInfoActivity) actBase;
    }

    public void load() {
        if (!mAc.match()) {
            if (loadLayout != null) {
                loadLayout.refreshFinish(PullToRefreshLayout.FAIL, "请输入关键信息");
            }
            return;
        }
        if (isFirst) {
            mActBase.showLoadingDialog("正在加载...");
            isFirst = false;
        }
        skip = 0;
        request(NetConstants.NET_QUERYCOURSE, new CallBack<GradeStatInfo>() {

            @Override
            public void onFinish() {
                mActBase.hideLoadingDialog();
            }

            @Override
            public void onSimpleFailure(int error, String errorMsg) {
                mActBase.onResponseFailure(error, errorMsg);
                if (loadLayout != null) {
                    loadLayout.refreshFinish(PullToRefreshLayout.FAIL);
                }
                isFirst = true;
                CommonUtil.showToast("查询失败");
            }

            @Override
            public void onSimpleSuccessList(List<GradeStatInfo> result) {
                System.out.println(">>>>>>>>>>成绩详情数据："+result);
                skip += pagesize;
                if (result != null && result.size() != 0) {
                    adapter.upDateList(result);
                } else {
                    CommonUtil.showToast("暂无数据");
                }
                if (loadLayout != null) {
                    loadLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
                isFirst = true;
            }
        });
    }

    public void loadMore() {
        request(NetConstants.NET_QUERYCOURSE, new CallBack<GradeStatInfo>() {

            @Override
            public void onSimpleFailure(int error, String errorMsg) {
                mActBase.onResponseFailure(error, errorMsg);
                if (loadLayout != null) {
                    loadLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
                }
            }

            @Override
            public void onSimpleSuccessList(List<GradeStatInfo> result) {
                skip += pagesize;
                if (result.size() != 0) {
                    adapter.upDateList(result);
                } else {
                    CommonUtil.showToast("暂无数据");
                }
                if (loadLayout != null) {
                    loadLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }
        });
    }

    public GradeStatAdapter getAdapter(String title) {
        this.adapter = new GradeStatAdapter(mActBase.getActivity(), R.layout.act_school_info_item, null, title);
        return adapter;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = getSignParams();
        params.put("pagesize", pagesize + "");
        params.put("skip", skip + "");
        return getGradeInfoParam(params);
    }

    public abstract Map<String, String> getGradeInfoParam(Map<String, String> params);

    @Override
    public Class<GradeStatInfo> getGsonClass() {
        return GradeStatInfo.class;
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        this.loadLayout = pullToRefreshLayout;
        load();
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        this.loadLayout = pullToRefreshLayout;
        loadMore();
    }
}
