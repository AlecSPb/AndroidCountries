package com.example.oksana.country;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailsActivity extends AppCompatActivity {
    private final DecimalFormat formatter = new DecimalFormat("#,###,###.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent myIntent = getIntent();
        String countryName = myIntent.getStringExtra("country");
        String alpha2 = myIntent.getStringExtra("alpha2");
        setTitle(countryName);
        setContentView(R.layout.activity_details);

        Request request = new Request.Builder()
                .url("https://restcountries.eu/rest/v2/alpha/" + alpha2)
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

                DetailsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject json = new JSONObject(myResponse);
                            ((TextView) findViewById(R.id.common)).setText(DetailsActivity.this.getString(R.string.common_name) + json.getString("name"));
                            ((TextView) findViewById(R.id.official)).setText(DetailsActivity.this.getString(R.string.official_name) + json.getJSONArray("altSpellings").getString(0));
                            ((TextView) findViewById(R.id.common_native)).setText("\u200E" + DetailsActivity.this.getString(R.string.common_name_native) + json.getString("nativeName"));
                            ((TextView) findViewById(R.id.language)).setText(DetailsActivity.this.getString(R.string.language) + json.getJSONArray("languages").getJSONObject(0).getString("name"));
                            ((TextView) findViewById(R.id.region)).setText(DetailsActivity.this.getString(R.string.region) + json.getString("region"));
                            ((TextView) findViewById(R.id.subregion)).setText(DetailsActivity.this.getString(R.string.subregion) + json.getString("subregion"));
                            ((TextView) findViewById(R.id.capital)).setText(DetailsActivity.this.getString(R.string.capital) + json.getString("capital"));
                            ((TextView) findViewById(R.id.demonym)).setText(DetailsActivity.this.getString(R.string.demonym) + json.getString("demonym"));

                            String area = json.getString("area");
                            if (!area.isEmpty() && !area.equals("null")) {
                                area = formatter.format(Double.parseDouble(area));
                            }
                            ((TextView) findViewById(R.id.area)).setText(DetailsActivity.this.getString(R.string.area) + area + DetailsActivity.this.getString(R.string.km2));
                            ((TextView) findViewById(R.id.time_zone)).setText(DetailsActivity.this.getString(R.string.time_zone) + json.getJSONArray("timezones").getString(0));
                            ((TextView) findViewById(R.id.alpha3)).setText(DetailsActivity.this.getString(R.string.alpha3) + json.getString("alpha3Code"));
                            ((TextView) findViewById(R.id.alpha2)).setText(DetailsActivity.this.getString(R.string.alpha2) + json.getString("alpha2Code"));
                            ((TextView) findViewById(R.id.numeric)).setText(DetailsActivity.this.getString(R.string.numeric_code) + json.getString("numericCode"));
                            ((TextView) findViewById(R.id.calling_code)).setText(DetailsActivity.this.getString(R.string.calling_code) + json.getJSONArray("callingCodes").getString(0));
                            ((TextView) findViewById(R.id.currency_code)).setText(DetailsActivity.this.getString(R.string.currency_code) + json.getJSONArray("currencies").getJSONObject(0).getString("code"));

                            ImageView imageView = findViewById(R.id.imageView_flag);
                            String flagUrl = json.getString("flag");
                            ImageUtils.fetchSvg(DetailsActivity.this, flagUrl, imageView);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }

        });
    }
}
