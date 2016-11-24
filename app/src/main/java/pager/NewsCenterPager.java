package pager;
import android.app.Activity;
import android.graphics.Color;

import android.text.TextUtils;
import android.view.Gravity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import base.BaseMenuDetailPager;
import base.BasePager;
import bean.NewsMenu;
import global.GlobalConstants;
import utils.CacheUtils;


/**
 * 新闻中心
 * 
 * @author Kevin
 * @date 2015-10-18
 */
public class NewsCenterPager extends BasePager {


    private NewsMenu mNewsData;

    public NewsCenterPager(Activity activity) {
		super(activity);
	}


	@Override
	public void initData() {
		System.out.println("新闻中心.");

//		// 要给帧布局填充布局对象
//		TextView view = new TextView(mActivity);
//		view.setText("新闻中心");
//		view.setTextColor(Color.RED);
//		view.setTextSize(22);
//		view.setGravity(Gravity.CENTER);
//
//		flContent.addView(view);

		// 修改页面标题
		tvTitle.setText("新闻中心");

		// 先判断有没有缓存,如果有的话,就加载缓存
		String cache = CacheUtils.getCache(GlobalConstants.CATEGORY_URL,
				mActivity);
		if (!TextUtils.isEmpty(cache)) {
			System.out.println("发现缓存啦...");
			processData(cache);
		}

		// 请求服务器,获取数据
		// 开源框架: XUtils
		getDataFromServer();



	}
    /**
     * 从服务器获取数据 需要权限:<uses-permission android:name="android.permission.INTERNET"
     * />
     */
	private void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, GlobalConstants.CATEGORY_URL,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        // 请求成功
                        String result = responseInfo.result;// 获取服务器返回结果
                        System.out.println("服务器返回结果:" + result);

                        // JsonObject, Gson
                        processData(result);

                        // 写缓存
                        CacheUtils.setCache(GlobalConstants.CATEGORY_URL,
                                result, mActivity);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        // 请求失败
                        error.printStackTrace();
                        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT)
                                .show();
                    }
                });

	}

	private void processData(String json) {
        // Gson: Google Json
        Gson gson = new Gson();
        mNewsData = gson.fromJson(json, NewsMenu.class);
        System.out.println("解析结果:" + mNewsData);

        NewsMenuDetailPager pager=new NewsMenuDetailPager(mActivity,mNewsData.data.get(0).children);
        View view=pager.mRootView;

        flContent.removeAllViews();

        flContent.addView(view);

        pager.initData();

        tvTitle.setText(mNewsData.data.get(0).title);

    }

}
