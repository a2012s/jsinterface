package com.ganwenpeng.jsinterface.page.jsinterface;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.ganwenpeng.jsinterface.R;
import com.ganwenpeng.jsinterface.model.jsinterface.JsInterfaceLogic;
import com.ganwenpeng.jsinterface.page.base.BaseFragment;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.ContentValues.TAG;

/**
 * @author gangan
 */
public class JsInterfaceFragment extends BaseFragment<JsInterfaceContract.Presenter> implements JsInterfaceContract.View {
    @BindView(R.id.tv_toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.img_toolbar_back)
    ImageView mImgToolbarBack;
    @BindView(R.id.web)
    WebView mWebView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intercept_webview, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        setupUI();
        return view;
    }

    @SuppressWarnings("all")
    private void setupUI() {
        mTvToolbarTitle.setText("拦截跳转");
        mImgToolbarBack.setVisibility(View.VISIBLE);

        mWebView.getSettings().setJavaScriptEnabled(true);

        // mWebView.addJavascriptInterface(new JsInterfaceLogic(this), "app");

        mWebView.addJavascriptInterface(new JsInterfaceLogic(this), "androidApp");

    }

    @OnClick(R.id.img_toolbar_back)
    public void onBackClick() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.tv_btn1)
    public void onBtn1Click() {
        mPresenter.clickBtn1();
    }

    @OnClick(R.id.tv_btn2)
    public void onBtn2Click() {
        mPresenter.clickBtn2();
    }

    @Override
    public void renderUrl(@NonNull String url) {
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.loadUrl(url);
    }

    @Override
    public void execJavaScript(@NonNull String js) {
    }

    final class MyWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d("logcat", "onPageStarted");
            inputJS(view);
            super.onPageStarted(view, url, favicon);
        }


        public void onPageFinished(WebView view, String url) {
            Log.d("logcat", "onPageFinished ");


            String newJs = " console.log(\"ua=\"+ navigator.userAgent);";


            view.evaluateJavascript(newJs, null);

            super.onPageFinished(view, url);
        }
    }

    private void inputJS(WebView view) {

        String jsStr = "";

        long time1 = System.currentTimeMillis();
        try {
////            Resources res = getResources();
////            InputStream in = res.openRawResource(trust.web3provider.R.raw.trustmin15);

            AssetManager am = null;
            am = getActivity().getAssets();
            InputStream in = am.open("trustmin16.js");

            byte[] b = new byte[in.available()];
            int readLen = in.read(b);
            jsStr = new String(b);//; String.format("Len: %1$s\n%2$s", readLen, new String(b));

            // ((TextView) findViewById(R.id.out)).setText(jsStr);
            Log.e(TAG, jsStr);
        } catch (Exception e) {
            // ((TextView) findViewById(R.id.out)).setText(e.getMessage());
            Log.e(TAG, "加载错误 " + e.getMessage());
        }

        long time2 = System.currentTimeMillis();
        Log.e(TAG, "时间：" + (time2 - time1) / 1000);

        // view.loadUrl(jsStr);//创建方法
        view.evaluateJavascript(jsStr, new ValueCallback<String>() {//将读取的js字符串注入webview中
            @Override
            public void onReceiveValue(String value) {//js与native交互的回调函数
                Log.d(TAG, "value=" + value);
            }
        });


        String url2 = "";
        try {
//            Resources res = getResources();
//            InputStream in = getActivity().getClass().getResourceAsStream("assets/plus.js");//// res.openRawResource(R.raw.plus);
            AssetManager am = null;
            am = getActivity().getAssets();
            InputStream in = am.open("plus.js");

            byte[] b = new byte[in.available()];
            int readLen = in.read(b);
            url2 = new String(b);//; String.format("Len: %1$s\n%2$s", readLen, new String(b));

            // ((TextView) findViewById(R.id.out)).setText(jsStr);
            // Log.e(TAG, jsStr);
        } catch (Exception e) {
            // ((TextView) findViewById(R.id.out)).setText(e.getMessage());
            Log.e(TAG, "加载错误 " + e.getMessage());
        }

        // view.loadUrl(url2);
        view.evaluateJavascript(url2, new ValueCallback<String>() {//将读取的js字符串注入webview中
            @Override
            public void onReceiveValue(String value) {//js与native交互的回调函数
                Log.d(TAG, "value=" + value);
            }
        });


    }


}
