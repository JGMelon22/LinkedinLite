package com.example.linkedinlite.javascriptInjection;

import android.webkit.WebView;

import androidx.annotation.NonNull;

public class JavaScriptInjectionService {
    public static void removeElement(@NonNull WebView webView) {
        // Remove banner linkedin mobile
        webView.loadUrl("javascript:(function() {document.querySelector('.omnibanner').remove()})()");
    }
}
