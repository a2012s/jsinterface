package com.ganwenpeng.jsinterface.page.jsinterface;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ganwenpeng.jsinterface.R;
import com.ganwenpeng.jsinterface.page.base.BasePresenter;

import java.io.InputStream;

public class JsInterfacePresenter extends BasePresenter implements JsInterfaceContract.Presenter {
    private final JsInterfaceContract.View mView;

    JsInterfacePresenter(JsInterfaceContract.View mView) {
        this.mView = mView;
    }

    @Override
    protected void start(boolean isFirstStart) {
        if (isFirstStart) {
            mView.renderUrl("file:///android_asset/jsinterface.html");
           // mView.renderUrl("https://app.compound.finance");

           // mView.execJavaScript();

        }
    }

    @Override
    public void clickBtn1() {
        mView.execJavaScript("showResponse('点击了按钮111111111111')");
    }

    @Override
    public void clickBtn2() {
        mView.execJavaScript("showResponse('点击了按钮22222222222')");
    }



}
