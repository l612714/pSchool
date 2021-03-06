package com.peoit.android.online.pschool.config;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.peoit.android.online.pschool.entity.HomeBannerInfo;
import com.peoit.android.online.pschool.entity.UserInfo;
import com.peoit.android.online.pschool.utils.ShareUserHelper;

import java.util.List;


/**
 * author:libo
 * time:2015/7/9
 * E-mail:boli_android@163.com
 * last: ...
 */
public class CommonUtil {
    private static DisplayMetrics metrics;

    public static Context mContext;
    public static int w_screeen;
    public static int h_screeen;

    public static LinearLayout.LayoutParams PARAM_MP_WC = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);

    public static LinearLayout.LayoutParams PARAM_WC_WC = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);

    public static LinearLayout.LayoutParams PARAM_WC_MP = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT);

    public static LinearLayout.LayoutParams PARAM_MP_MP = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT);

    public static int VISIBLE = View.VISIBLE;

    public static int INVISIBLE = View.INVISIBLE;

    public static int GONE = View.GONE;

    private static boolean isLogin = false;
    @Nullable
    private static UserInfo currentUser = null;

    public static List<HomeBannerInfo> mHomeBanners;

    public static String groupid;

    public static String getGroupid() {
        return groupid;
    }

    /**
     * 配置全局Context
     *
     * @param context
     */
    public static void initContext(@NonNull Context context) {
        mContext = context;
        metrics = getApplicationContext().getResources().getDisplayMetrics();

        currentUser = ShareUserHelper.getInstance().getCurrentUser();
        groupid = ShareUserHelper.getInstance().getString(Constants.LOGIN_CHAT_GROUP_ID);
        isLogin = ShareUserHelper.getInstance().isLogin();

        w_screeen = metrics.widthPixels;
        h_screeen = metrics.heightPixels;
    }

    public static String getCurentuserNike(){
        String nikeName = ShareUserHelper.getInstance().getString(Constants.LOGIN_USER_NIKE);
        return nikeName;
    }

    /**
     * 判断是否登录
     *
     * @return
     */
    public synchronized static boolean isIsLogin() {
        isLogin = ShareUserHelper.getInstance().isLogin();
        return isLogin;
    }

    @Nullable
    public synchronized static UserInfo getCurrentUser() {
        currentUser = ShareUserHelper.getInstance().getCurrentUser();
        return currentUser;
    }

    public synchronized static void logout() {
        ShareUserHelper.getInstance().clear();
    }

    /**
     * 获取用户类型...
     *
     * @return
     */
    public synchronized static int getIdEntityType() {
        if (isIsLogin())
            return Integer.valueOf(getCurrentUser().getIdentityType());
        return Constants.TPYE_NOT_LOGIN;
    }

    /**
     * 获取登录后的用户Sign
     *
     * @return
     */
    public static String getUser_sign() {
        String sign = ShareUserHelper.getInstance().getString(Constants.LOGIN_USER_SIGN);
        if (TextUtils.isEmpty(sign))
            throw new NullPointerException(" @libo sign is null ");
        return sign;
    }

    /**
     * 获取登录后保存的用户名
     *
     * @return
     */
    public static String getUser_name() {
        String name = ShareUserHelper.getInstance().getString(Constants.LOGIN_USER_NAME);
        if (TextUtils.isEmpty(name))
            throw new NullPointerException(" @libo username is null ");
        return name;
    }

    public static boolean isZhuanJia(){
        int type = getIdEntityType();
        return type == Constants.TYPE_ZHUAN_JIA || type == Constants.TYPE_ZHUAN_JIA1;
    }

    /**
     * 获取系统ApplicationContext
     *
     * @return
     */
    public static Context getApplicationContext() {
        return mContext.getApplicationContext();
    }

    /**
     * 显示Toast
     *
     * @param msg
     */
    public static void showToast(@NonNull CharSequence msg) {
        if (!TextUtils.isEmpty(msg))
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(@Nullable Activity mAc, @NonNull CharSequence msg) {
        if (!TextUtils.isEmpty(msg))
            Toast.makeText(mAc == null ? mContext : mAc, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * dp转换为px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        //MyLogger.i(scale+"");
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px装换成dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转换为px
     */
    public static int sp2px(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }

    /**
     * px转换为sp
     */
    public static int px2sp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scale + 0.5f);
    }
}
