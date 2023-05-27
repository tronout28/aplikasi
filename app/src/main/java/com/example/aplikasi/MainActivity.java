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
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Adapter.ContactsAdapterListener{
    RecyclerView rvClubName;
    ArrayList<EPLTeamModel> listDataEPLTeams;
    private Adapter adapter;

    public void getEPLOnline() {
        String url = "https://www.thesportsdb.com/api/v1/json/3/search_all_teams.php?l=English%20Premier%20League";
        AndroidNetworking.get(url)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
//                            Log.d("sukses", "onResponse: "+jsonObject.toString());

                        try {
                            JSONArray jsonArrayEPLTeam = jsonObject.getJSONArray("teams");
                            for (int i = 0; i < jsonArrayEPLTeam.length() ; i++) {
                                EPLTeamModel myTeam = new EPLTeamModel();
                                JSONObject jsonTeam = jsonArrayEPLTeam.getJSONObject(i);
                                myTeam.setTeamname(jsonTeam.getString("strTeam"));
                                myTeam.setStadiun(jsonTeam.getString("strStadium"));
                                myTeam.setTeamdescription(jsonTeam.getString("strDescriptionEN"));
                                myTeam.setStrTeamBadge(jsonTeam.getString("strTeamBadge"));
                                listDataEPLTeams.add(myTeam);
                            }
                            rvClubName  = findViewById(R.id.recyclerView);
                            adapter = new Adapter(getApplicationContext(), listDataEPLTeams,MainActivity.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            rvClubName.setHasFixedSize(true);
                            rvClubName.setLayoutManager(mLayoutManager);
                            rvClubName.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("gagal", "onError: "+anError.toString());
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
        switch (item.getItemId()){
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
        listDataEPLTeams = new ArrayList<>();

        getEPLOnline();
    }
    @Override
    public void onContactSelected(EPLTeamModel myteam) {
        // move to another page
        Intent intent = new Intent(this, DetailTeam.class);
        intent.putExtra("myTeam", myteam);
        startActivity(intent);
    }

}
