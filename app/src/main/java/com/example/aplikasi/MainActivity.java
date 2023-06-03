package com.example.aplikasi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Adapter.ContactsAdapterListener {
    RecyclerView rvfoodset;
    ArrayList<FoodModel> listDataFood;
    private Adapter adapter;

    public void getEPLOnline() {
        String url = "https://www.themealdb.com/api/json/v1/1/search.php?f=a";
        AndroidNetworking.get(url)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            if (jsonObject.has("meals")) {
                                JSONArray jsonArrayFood = jsonObject.getJSONArray("meals");
                                for (int i = 0; i < jsonArrayFood.length(); i++) {
                                    FoodModel myFood = new FoodModel();
                                    JSONObject jsonfood = jsonArrayFood.getJSONObject(i);
                                    myFood.setfoodname(jsonfood.getString("strMeal"));
                                    myFood.setfoodfrom(jsonfood.getString("strArea"));
                                    myFood.setfooddescription(jsonfood.getString("strInstructions"));
                                    myFood.setfoodimage(jsonfood.getString("strMealThumb"));
                                    listDataFood.add(myFood);
                                }

                                rvfoodset = findViewById(R.id.recyclerView);
                                adapter = new Adapter(getApplicationContext(), listDataFood, MainActivity.this);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                rvfoodset.setHasFixedSize(true);
                                rvfoodset.setLayoutManager(mLayoutManager);
                                rvfoodset.setAdapter(adapter);
                            } else {
                                Log.e("MainActivity", "No 'meals' key found in JSON response");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("MainActivity", "Error: " + anError.getErrorDetail());
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listDataFood = new ArrayList<>();

        getEPLOnline();
    }

    @Override
    public void onContactSelected(FoodModel myfood) {
        // move to another page
        Intent intent = new Intent(this, FoodDetail.class);
        intent.putExtra("myTeam", myfood);
        startActivity(intent);
    }
}

