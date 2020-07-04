package com.example.edugorilla;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LastActivity extends AppCompatActivity {

    private WebView webView;
    TextView Title_From_Json;
    Button next , previous;

    public static final String URL_DATA= "https://www.dropbox.com/s/ep7v5yex3fjs3s1/webview.json?dl=1";

    //========================Unable to complete this activity because lot of time was spent on previous activity==========================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_last);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Title_From_Json = findViewById(R.id.title);

        next = findViewById(R.id.nxt_btn);
        previous = findViewById(R.id.prev_btn);

        webView = findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.dropbox.com/s/ep7v5yex3fjs3s1/webview.json?dl=1");

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(webView.canGoBack()){
                    webView.goBack();
                }else{
                    Toast.makeText(LastActivity.this, "Can't go back further", Toast.LENGTH_SHORT).show();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(webView.canGoForward()){
                    webView.goForward();
                }else{
                    Toast.makeText(LastActivity.this, "Can't go forward anymore", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loadingTheDataFromJson();



    }

    private void loadingTheDataFromJson() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Retrieving your data..");
        pd.show();


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            pd.dismiss();

                JSONArray jArray = null;
                try {
                    jArray = new JSONArray(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for(int i = 0 ; i < jArray.length() ; i++){
                    JSONObject oneObject = null;
                    try {
                        oneObject = jArray.getJSONObject(i);

                    String title1 = oneObject.getString("Title");

                        Title_From_Json.append(title1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(LastActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(LastActivity.this);
        requestQueue.add(stringRequest);
    }


}