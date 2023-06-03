package com.example.aplikasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class FoodDetail extends AppCompatActivity {

    private TextView Name,Desk;
    private ImageView Logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Name = findViewById(R.id.teamname);
        Desk = findViewById(R.id.deskclub);
        Logo = findViewById(R.id.teamlogo);

        Intent intent = getIntent();
        FoodModel foodModel = intent.getParcelableExtra("myTeam");

        Name.setText(foodModel.getFoodname());
        Desk.setText(foodModel.getFooddesk());

        Glide.with(this).load(foodModel.getFoodimage()).into(Logo);

    }
}