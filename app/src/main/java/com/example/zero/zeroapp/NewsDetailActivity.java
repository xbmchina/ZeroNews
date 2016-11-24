package com.example.zero.zeroapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.Random;

/**
 * Created by Administrator on 2016/11/24
 * 新闻详情页.
 */
public class NewsDetailActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.ll_control)
    private LinearLayout llControl;
    @ViewInject(R.id.btn_back)
    private ImageButton btnBack;
    @ViewInject(R.id.btn_textsize)
    private ImageButton btnTextSize;
    @ViewInject(R.id.btn_menu)
    private ImageButton btnMenu;
    @ViewInject(R.id.btn_share)
    private ImageButton btnShare;
    @ViewInject(R.id.wv_news_detail)
    private WebView mWebView;
    @ViewInject(R.id.pb_loading)
    private ProgressBar pbLoading;
    private String mUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news_detail);
        ViewUtils.inject(this);

        llControl.setVisibility(View.VISIBLE);
        btnBack.setVisibility(View.VISIBLE);
        btnMenu.setVisibility(View.GONE);

        btnBack.setOnClickListener(this);
        btnTextSize.setOnClickListener(this);
        btnShare.setOnClickListener(this);

        mUrl = getIntent().getStringExtra("url");
        String[] allUrl=new String[]{
                "http://mp.weixin.qq.com/s?__biz=MjM5MjI2MTU1MQ==&idx=3&mid=2652011500&sn=bdb1d173b28ca8b2b951d3d867beee77",
                "http://mp.weixin.qq.com/s?__biz=MzA5NzIwMjQzMA==&idx=3&mid=2649774376&sn=cb4c12128f97c0f3f09db3e3cf62d244",
                "http://mp.weixin.qq.com/s?__biz=MzIzMTA5MzkxNw==&idx=1&mid=2649557306&sn=68d8ad01bc7c7026ff462aa825626358",
                "http://mp.weixin.qq.com/s?__biz=MjM5ODIzNTc2MA==&idx=4&mid=2660758183&sn=4276595e3f582dfc20a8afb138397920"
        };
        int pointUrl= (int) (Math.random()*(allUrl.length-1));
        mWebView.loadUrl(allUrl[pointUrl]);
//        mWebView.loadUrl("http://172.24.108.1:8080/");
//        mWebView.loadUrl("http://mp.weixin.qq.com/s?__biz=MjM5MjI2MTU1MQ==&idx=3&mid=2652011500&sn=bdb1d173b28ca8b2b951d3d867beee77");
//        http://mp.weixin.qq.com/s?__biz=MzA5NzIwMjQzMA==&idx=3&mid=2649774376&sn=cb4c12128f97c0f3f09db3e3cf62d244
//        mWebView.loadUrl("http://sports.163.com/16/1124/14/C6L3M36J0005877U.html?f=bjrec_pr&h=o#loc=1");
//        mWebView.loadUrl(mUrl);

        WebSettings settings = mWebView.getSettings();
        settings.setBuiltInZoomControls(true);// 显示缩放按钮(wap网页不支持)
        settings.setUseWideViewPort(true);// 支持双击缩放(wap网页不支持)
        settings.setJavaScriptEnabled(true);// 支持js功能


        mWebView.setWebViewClient(new WebViewClient() {
            // 开始加载网页
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                System.out.println("开始加载网页了");
                pbLoading.setVisibility(View.VISIBLE);
            }

            // 网页加载结束
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                System.out.println("网页加载结束");
                pbLoading.setVisibility(View.INVISIBLE);
            }

            // 所有链接跳转会走此方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                System.out.println("跳转链接:" + url);
                view.loadUrl(url);// 在跳转链接时强制在当前webview中加载
                return true;
            }
        });

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_textsize:
                // 修改网页字体大小
                showChooseDialog();
                break;

            case R.id.btn_share:
                showShare();
                break;

            default:
                break;
        }
    }

    private int mTempWhich;// 记录临时选择的字体大小(点击确定之前)

    private int mCurrenWhich = 2;// 记录当前选中的字体大小(点击确定之后), 默认正常字体

    /**
     * 展示选择字体大小的弹窗
     */
    private void showChooseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("字体设置");

        String[] items = new String[] { "超大号字体", "大号字体", "正常字体", "小号字体",
                "超小号字体" };

        builder.setSingleChoiceItems(items, mCurrenWhich,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTempWhich = which;
                    }
                });

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 根据选择的字体来修改网页字体大小

                WebSettings settings = mWebView.getSettings();

                switch (mTempWhich) {
                    case 0:
                        // 超大字体
                        settings.setTextSize(WebSettings.TextSize.LARGEST);
                        // settings.setTextZoom(22);
                        break;
                    case 1:
                        // 大字体
                        settings.setTextSize(WebSettings.TextSize.LARGER);
                        break;
                    case 2:
                        // 正常字体
                        settings.setTextSize(WebSettings.TextSize.NORMAL);
                        break;
                    case 3:
                        // 小字体
                        settings.setTextSize(WebSettings.TextSize.SMALLER);
                        break;
                    case 4:
                        // 超小字体
                        settings.setTextSize(WebSettings.TextSize.SMALLEST);
                        break;

                    default:
                        break;
                }

                mCurrenWhich = mTempWhich;
            }
        });

        builder.setNegativeButton("取消", null);

        builder.show();
    }



//分享新闻用的
    private void showShare() {
    }
}
