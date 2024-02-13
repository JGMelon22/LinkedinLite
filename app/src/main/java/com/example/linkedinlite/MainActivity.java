package com.example.linkedinlite;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.linkedinlite.javascriptInjection.JavaScriptInjectionService;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ProgressBar progressBar = findViewById(R.id.progressBar9);

        webView = findViewById(R.id.webView);
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.loadUrl("https://www.linkedin.com/");
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                view.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                JavaScriptInjectionService.removeElement(view);

                // view.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);

                // Verifica se está conectado a internet
                isUserConnected(view);
            }

            // Caso o usuário esteja offline, indica que é preciso ter uma conexão de internet ativa
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

                if (error != null && (error.getErrorCode() == WebViewClient.ERROR_HOST_LOOKUP ||
                        error.getErrorCode() == WebViewClient.ERROR_CONNECT ||
                        error.getErrorCode() == WebViewClient.ERROR_TIMEOUT)) {
                    Intent errorIntent = new Intent(getApplicationContext(), ErrorActivity.class);
                    startActivity(errorIntent);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            webView.goBack();
            return true;
        }
        return MainActivity.super.onKeyDown(keyCode, event);
    }

    public void isUserConnected(View view) {
        // Instancia o network manager e julga se está offline ou não (evitando a web view offline)
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected)
            view.setVisibility(View.VISIBLE);
    }
}
