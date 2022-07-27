package com.example.latlong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RouteActivity extends AppCompatActivity {
    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        Button btnmaps = findViewById(R.id.btnmaps);

        TextView edtlink = findViewById(R.id.edt_link);

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://192.168.100.151:5000/getLink").build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RouteActivity.this, "ERROR LINK EWEH", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            edtlink.setText(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        btnmaps.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String link = edtlink.getText().toString();
                                Intent i2=new Intent(Intent.ACTION_VIEW);
                                i2.setData(Uri.parse(link));
                                startActivity(i2);
                            }
                        });
                    }
                });
            }
        });

    }
}