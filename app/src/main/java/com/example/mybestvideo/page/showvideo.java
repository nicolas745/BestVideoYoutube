package com.example.mybestvideo.page;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.mybestvideo.LoadFragment;
import com.example.mybestvideo.R;

public class showvideo extends Fragment {
    private static String video_url = "https://www.youtube.com";

    public showvideo(String url){
        video_url = url;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_video, container, false);

        /*Button del = view.findViewById(R.id.delvideo);
        del.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                new LoadFragment(getParentFragmentManager()).load(new ListVideo());
            }
        });
        */
        WebView webView = view.findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        Log.d("url",video_url);
        webView.loadUrl(video_url);

        return view;
    }
}
