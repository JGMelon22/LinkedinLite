package com.example.linkedinlite.javascriptInjection;

import android.webkit.WebView;

import androidx.annotation.NonNull;

public class JavaScriptInjectionService {
    public static void removeElement(@NonNull WebView webView) {
        // Remove banner linkedin mobile
        webView.loadUrl("javascript:(function() {document.querySelector('[class=\"omnibanner fixed bg-cool-gray-90 z-1 flex items-center w-full bottom-0 !bottom-6\"]').remove()})()");
    }
}
