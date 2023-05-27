package com.example.aplikasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailTeam extends AppCompatActivity {

    Intent i;
    EPLTeamModel eplTeamModel;
    private TextView Name,Desk;
    private ImageView Logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_team);

        Name = findViewById(R.id.teamname);
        Desk = findViewById(R.id.deskclub);
        Logo = findViewById(R.id.teamlogo);

        Intent intent = getIntent();
        EPLTeamModel eplTeamModel = intent.getParcelableExtra("myTeam");

        Name.setText(eplTeamModel.getTeamname());
        Desk.setText(eplTeamModel.getTeamdescription());

        Glide.with(this).load(eplTeamModel.getStrTeamBadge()).into(Logo);

    }
}