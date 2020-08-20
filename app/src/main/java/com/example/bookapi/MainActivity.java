package com.example.bookapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.bookapi.adapter.ProductRecyclerAdapter;
import com.example.bookapi.model.Products;
import com.example.bookapi.retrofit.ApiInterface;
import com.example.bookapi.retrofit.RetrofitClient;
import com.google.gson.Gson;

import java.util.List;
import java.util.Properties;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ApiInterface apiInterface;
    RecyclerView recyclerView;
    ProductRecyclerAdapter productRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppThemeLight);
        }
        setContentView(R.layout.activity_main);


        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<List<Products>> call = apiInterface.getAllProduct();

        call.enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {

                List<Products> products = response.body();
                Gson g = new Gson();
                String m = g.toJson(products);
                Log.d("this is the data ", m);
                //  Toast.makeText(getApplicationContext(), m,Toast.LENGTH_LONG).show();
                setRecyclerData(products);
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {

            }
        });
        // for Dark mode
        findViewById(R.id.flb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
               //  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });

    }

    private void setRecyclerData(List<Products> productsList) {

        recyclerView = findViewById(R.id.re);
        productRecyclerAdapter = new ProductRecyclerAdapter(productsList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(productRecyclerAdapter);
        productRecyclerAdapter.notifyDataSetChanged();

    }
}