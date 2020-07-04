package com.example.edugorilla;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

    private static final String URL_DATA = "https://www.dropbox.com/s/n4i57r22rdx89cw/list2.json?dl=1";
    private List<model_third> detailsForGraph;

    Button nextActivityBtn;

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;

    GraphView graphView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_graph);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        nextActivityBtn = findViewById(R.id.button);

        nextActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GraphActivity.this , LastActivity.class);
                startActivity(intent);
            }
        });

        graphView = findViewById(R.id.graph);
        recyclerView = findViewById(R.id.account_details);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        retrievingTheDataFromJsonFile();

        detailsForGraph = new ArrayList<>();

    }

    private void retrievingTheDataFromJsonFile() {
        final ProgressDialog pd = new ProgressDialog(this);

        pd.setMessage("Retrieving the data ...");
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

                for (int i = 0; i < jArray.length(); i++) {
                    try {
                        JSONObject oneObject = jArray.getJSONObject(i);
                        String date = oneObject.getString("date_created");
                        String amount = oneObject.getString("amount");


                        String datesForJuly = date.substring(5, 8);
                        String dayOfJulyMonth = date.substring(8);


                        if (datesForJuly.contains("07")) {
                            model_third modelThird = new model_third(date, amount);
                            detailsForGraph.add(modelThird);

//-----------------------------------------Unable to plot the graph from the desired data=========================================================

                            int a = Integer.parseInt(dayOfJulyMonth);
                            int b = Integer.parseInt(amount);

                            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                                    new DataPoint(a, b),
                            });
                            graphView.addSeries(series);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter = new MySecondAdapter(detailsForGraph, getApplicationContext());
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GraphActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(GraphActivity.this);
        requestQueue.add(stringRequest);
    }


}