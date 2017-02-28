package com.example.administrator.javascript;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private Button button;
    private Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);
        initview();

    }

    private void initview() {

        webView= (WebView) findViewById(R.id.webview);
        button= (Button) findViewById(R.id.button);
        button2= (Button) findViewById(R.id.button2);
        webView.getSettings().setJavaScriptEnabled(true);
        // 从assets目录下面的加载html
        webView.loadUrl("file:///android_asset/web.html");
        webView.addJavascriptInterface(MainActivity.this,"android");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:javacalljs()");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:javacalljswith(\" + \"'http://blog.csdn.net/Leejizhou'\" + \")");
            }
        });
    }
    @JavascriptInterface
    public void startFunction(){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
              Toast.makeText(MainActivity.this,"show",Toast.LENGTH_LONG).show();

            }
        });
    }

    @JavascriptInterface
    public void startFunction(final String text){
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                new AlertDialog.Builder(MainActivity.this).setMessage(text).show();

            }
        });


    }
    @JavascriptInterface
    public void call(final int mobile){
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Intent i=new Intent();
                i.setAction("android.intent.action.CALL");
                i.setData(Uri.parse("tel:"+mobile));
                startActivity(i);

            }
        });


    }
public void sendmassage(String content,int mobile){
    SmsManager smsManager = SmsManager.getDefault();
    ArrayList<String> texts = smsManager.divideMessage(content);//拆分短信,短信字数太多了的时候要分几次发
    for(String text : texts){
        smsManager.sendTextMessage(String.valueOf(mobile), null, text, null, null);//发送短信,mobile是对方手机号
    }
}

}
