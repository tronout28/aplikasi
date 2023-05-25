package com.example.aplikasi;

import android.os.Bundle;
import android.util.Log;

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

public class MainActivity extends AppCompatActivity {
    RecyclerView rvKontakName;
    ArrayList<EPLTeamModel> listDataEPLTeams;
    private Adapter Adapter;

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
                                myTeam.setStrTeamBadge(jsonTeam.getString("strTeamBadge"));
                                listDataEPLTeams.add(myTeam);
                            }
                            rvKontakName = findViewById(R.id.recyclerView);
                            Adapter = new Adapter(getApplicationContext(), listDataEPLTeams,MainActivity.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            rvKontakName.setHasFixedSize(true);
                            rvKontakName.setLayoutManager(mLayoutManager);
                            rvKontakName.setAdapter(Adapter);

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listDataEPLTeams = new ArrayList<>();

        getEPLOnline();

    }
}