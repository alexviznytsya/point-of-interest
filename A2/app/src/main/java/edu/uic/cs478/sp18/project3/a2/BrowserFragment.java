/**
 * BrowserFragment.java
 *
 * Project #3, A2
 *
 * Author: Alex Viznytsya
 *
 * CS 478 Software Development for Mobile Platforms
 * Spring 2018, UIC
 *
 * Date: 04/02/2018
 */

package edu.uic.cs478.sp18.project3.a2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.webkit.WebViewFragment;

public class BrowserFragment extends WebViewFragment {

    private String url = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getWebView().setWebViewClient(new WebViewClient());
        getWebView().setWebChromeClient(new WebChromeClient());
        getWebView().getSettings().setJavaScriptEnabled(true);
        if(url != null) {
            getWebView().loadUrl(url);
        }
    }

    public void loadUrl(String url) {
        this.url = url;
        getWebView().loadUrl(url);
    }

}
