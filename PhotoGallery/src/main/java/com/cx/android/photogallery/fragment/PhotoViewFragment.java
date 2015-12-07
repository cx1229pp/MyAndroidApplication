package com.cx.android.photogallery.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cx.android.photogallery.R;

/**
 * Created by 陈雪 on 2015/10/22.
 */
public class PhotoViewFragment extends Fragment {
    private String mUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Uri uri = getActivity().getIntent().getData();
        if(uri != null){
            mUrl = uri.toString();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview,container,false);
        WebView mWebView = (WebView) view.findViewById(R.id.wv_photoView);

        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.pb_loadImage);
        progressBar.setMax(100);

        //final TextView textView = (TextView) view.findViewById(R.id.tv_title);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.INVISIBLE);
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
            }

            /*@Override
            public void onReceivedTitle(WebView view, String title) {
                textView.setText(title);
            }*/
        });

        mWebView.loadUrl(mUrl);

        return view;
    }
}
