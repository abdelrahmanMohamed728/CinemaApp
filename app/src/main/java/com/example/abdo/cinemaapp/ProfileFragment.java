package com.example.abdo.cinemaapp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.abdo.cinemaapp.Adapters.SearchAdapter;
import com.example.abdo.cinemaapp.General.Search;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    public ProfileFragment()
    {

    }

    ArrayList<Search>list;
    ListView listView;
    SearchAdapter adapter;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_profile, container, false);
        listView = v.findViewById(R.id.profileListView);
        final String API_KEY=getString(R.string.API_KEY);
        list=new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final String uid=   mAuth.getCurrentUser().getUid();
        DatabaseReference reference= mDatabase.child("Users").child(uid).child("movies");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap:dataSnapshot.getChildren()
                     ) {
                  String id = snap.getKey();
                    LoadData("https://api.themoviedb.org/3/movie/"+id+"?api_key="+getString(R.string.API_KEY)+"&language=en-US");
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        adapter = new SearchAdapter(getContext(),list);
        listView.setAdapter(adapter);
        return v;
    }
    public void LoadData(String url)
    {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                        String id = response.getString("id");
                        String name = response.getString("original_title");
                        String img = "https://image.tmdb.org/t/p/original"+response.getString("poster_path");
                        list.add(new Search(id,name,img));
                        adapter.notifyDataSetChanged();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Log.e("tag",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }
}
