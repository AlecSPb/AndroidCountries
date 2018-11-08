package com.example.oksana.country;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    String INDEX_KEY = "Index";
    volatile List<Country> countriesList = new ArrayList<>();
    volatile GridView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Request request = new Request.Builder()
                .url("https://restcountries.eu/rest/v2")
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONArray json = new JSONArray(myResponse);
                            for (int i = 0; i < json.length(); i++) {
                                Country country = new Country(
                                        json.getJSONObject(i).getString("name"),
                                        json.getJSONObject(i).getString("capital"),
                                        json.getJSONObject(i).getString("subregion"),
                                        json.getJSONObject(i).getString("population"),
                                        json.getJSONObject(i).getString("area"),
                                        json.getJSONObject(i).getString("alpha2Code"),
                                        json.getJSONObject(i).getString("flag")
                                );
                                countriesList.add(country);
                            }
                            gv = findViewById(R.id.gv);
                            final CountryListAdapter gridViewArrayAdapter = new CountryListAdapter
                                    (MainActivity.this, countriesList);

                            gv.setAdapter(gridViewArrayAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        gv = findViewById(R.id.gv);
        gv.setSelection(savedInstanceState.getInt(INDEX_KEY));
    }

    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int firstVisiblePosition = ((GridView) findViewById(R.id.gv)).getFirstVisiblePosition();
        outState.putInt(INDEX_KEY, firstVisiblePosition);
    }
}