package com.peoit.android.libview.list;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/*
 * �Զ���� �����ؼ�
 * ������ onScrollChanged���������仯��,����ÿ�εı仯֪ͨ�� �۲�(�˱仯��)�۲���
 * ��ʹ�� AddOnScrollChangedListener �����ı��ؼ��� �������仯
 * */
public class MyHScrollView extends HorizontalScrollView {
	ScrollViewObserver mScrollViewObserver = new ScrollViewObserver();

	public MyHScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MyHScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyHScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	PointF downPointF = new PointF();
	PointF movePointf = new PointF();

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.i("pdwy","MyHScrollView onTouchEvent");
		return super.onTouchEvent(event);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		/*
		 * ���������ƶ����� �����¼���֪ͨ��۲��ߣ��۲��߻ᴫ�������ġ�
		 */
		if (mScrollViewObserver != null /*&& (l != oldl || t != oldt)*/) {
			mScrollViewObserver.NotifyOnScrollChanged(l, t, oldl, oldt);
		}
		super.onScrollChanged(l, t, oldl, oldt);
	}

	/*
	 * ���� ���ؼ� �� �������仯�¼�
	 * */
	public void AddOnScrollChangedListener(OnScrollChangedListener listener) {
		mScrollViewObserver.AddOnScrollChangedListener(listener);
	}

	/*
	 * ȡ�� ���� ���ؼ� �� �������仯�¼�
	 * */
	public void RemoveOnScrollChangedListener(OnScrollChangedListener listener) {
		mScrollViewObserver.RemoveOnScrollChangedListener(listener);
	}

	/*
	 * �������˹����¼�ʱ
	 */
	public static interface OnScrollChangedListener {
		public void onScrollChanged(int l, int t, int oldl, int oldt);
	}

	/*
	 * �۲���
	 */
	public static class ScrollViewObserver {
		List<OnScrollChangedListener> mList;

		public ScrollViewObserver() {
			super();
			mList = new ArrayList<OnScrollChangedListener>();
		}

		public void AddOnScrollChangedListener(OnScrollChangedListener listener) {
			mList.add(listener);
		}

		public void RemoveOnScrollChangedListener(
				OnScrollChangedListener listener) {
			mList.remove(listener);
		}

		public void NotifyOnScrollChanged(int l, int t, int oldl, int oldt) {
			if (mList == null || mList.size() == 0) {
				return;
			}
			for (int i = 0; i < mList.size(); i++) {
				if (mList.get(i) != null) {
					mList.get(i).onScrollChanged(l, t, oldl, oldt);
				}
			}
		}
	}

	/*
         *
         * һ����ͼ�����ؼ�
         * ��ֹ ���� ontouch�¼����ݸ����ӿؼ�
         * */
    public static class InterceptScrollContainer extends LinearLayout {

        public InterceptScrollContainer(Context context, AttributeSet attrs) {
            super(context, attrs);
            // TODO Auto-generated constructor stub
        }

        public InterceptScrollContainer(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
        }
    //
    //	@Override
    //	public boolean dispatchTouchEvent(MotionEvent ev) {
    //		// TODO Auto-generated method stub
    //		//return super.dispatchTouchEvent(ev);
    //		Log.i("pdwy","ScrollContainer dispatchTouchEvent");
    //		return true;
    //	}

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            // TODO Auto-generated method stub
            //return super.onInterceptTouchEvent(ev);
            Log.i("pdwy","ScrollContainer onInterceptTouchEvent");
            return true;

            //return super.onInterceptTouchEvent(ev);
        }

    //	@Override
    //	public boolean onTouchEvent(MotionEvent event) {
    //		// TODO Auto-generated method stub
    //		Log.i("pdwy","ScrollContainer onTouchEvent");
    //		return true;
    //	}
    }

	public static class MyHScrollListView {

    }
}
