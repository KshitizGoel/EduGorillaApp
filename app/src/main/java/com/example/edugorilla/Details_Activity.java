package com.example.edugorilla;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Details_Activity extends AppCompatActivity {

    public RecyclerView recyclerView;
    private RecyclerView.Adapter SecondAdapter;
    public static final String URL = "https://www.dropbox.com/s/1hh8vh7whv6cjme/list1.json?dl=1";
    TextView nameFromGmail ;
    private List<model> listItem ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_details_);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        nameFromGmail = findViewById(R.id.username);
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
            nameFromGmail.setText(signInAccount.getDisplayName() + " !");
        }

        recyclerView = findViewById(R.id.list_Items);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        listItem = new ArrayList<>();

        if (!isConnectedToWifi(this)) {
            showcustomDialog();
        } else {
            loadData();
        }
    }

    private boolean isConnectedToWifi(Details_Activity details_activity) {

        ConnectivityManager connectivityManager = (ConnectivityManager) details_activity.getSystemService(Context.CONNECTIVITY_SERVICE);


        NetworkInfo wifiCon = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileCon = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wifiCon!=null && wifiCon.isConnected()) ||(mobileCon!=null && mobileCon.isConnected()) ){
            return  true;
        }else{
            return  false;
        }
    }

    private void showcustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Details_Activity.this);
        builder.setMessage("Please connect to the internet to continue.")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Details_Activity.this, "Can't proceed further without internet connection!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

    private void loadData() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Retrieving the data...");
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pd.dismiss();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            JSONArray array = jsonObject.getJSONArray("data");

                            for(int i = 0 ; i < 10 ; i++){
                                JSONObject k =  array.getJSONObject(i);
                                model item  = new model(k.getString("id") , k.getString("name") , k.getString("email"));
                                listItem.add(item);
                            }

                            SecondAdapter = new MyAdapter(listItem , getApplicationContext());
                            recyclerView.setAdapter(SecondAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            pd.dismiss();
                Toast.makeText(Details_Activity.this , error.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(Details_Activity.this);
        requestQueue.add(stringRequest);
    }

}