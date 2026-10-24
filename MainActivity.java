package com.fnosmartmoney.pro;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.os.Handler;
import android.os.Looper;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends Activity {
    private WebView webView;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.addJavascriptInterface(new GrowwBridge(), "AndroidGroww");
        webView.loadUrl("file:///android_asset/index.html");
        setContentView(webView);
    }

    private class GrowwBridge {
        @JavascriptInterface
        public void fetchQuote(String token, String symbol) {
            new Thread(() -> {
                HttpURLConnection connection = null;
                String result;
                try {
                    String encodedSymbol = URLEncoder.encode(symbol.trim(), "UTF-8");
                    URL url = new URL("https://api.groww.in/v1/live-data/quote?exchange=NSE&segment=FNO&trading_symbol=" + encodedSymbol);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(12000);
                    connection.setReadTimeout(12000);
                    connection.setRequestProperty("Accept", "application/json");
                    connection.setRequestProperty("Authorization", "Bearer " + token.trim());
                    connection.setRequestProperty("X-API-VERSION", "1.0");
                    int code = connection.getResponseCode();
                    InputStream stream = (code >= 200 && code < 300) ? connection.getInputStream() : connection.getErrorStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuilder body = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) body.append(line);
                    reader.close();
                    if (code < 200 || code >= 300) {
                        JSONObject err = new JSONObject();
                        err.put("error", "HTTP " + code + ": " + body);
                        result = err.toString();
                    } else {
                        result = body.toString();
                    }
                } catch (Exception e) {
                    JSONObject err = new JSONObject();
                    try { err.put("error", e.getMessage() == null ? "Network/API request failed" : e.getMessage()); }
                    catch (Exception ignored) {}
                    result = err.toString();
                } finally {
                    if (connection != null) connection.disconnect();
                }
                final String response = result;
                mainHandler.post(() -> webView.evaluateJavascript(
                    "window.onGrowwQuote(" + JSONObject.quote(response) + ")", null));
            }).start();
        }
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }
}
