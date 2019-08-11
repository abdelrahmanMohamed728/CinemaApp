package com.example.abdo.cinemaapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.abdo.cinemaapp.Adapters.SearchAdapter;
import com.example.abdo.cinemaapp.General.Search;
import com.example.abdo.cinemaapp.General.Trend;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
     RadioButton radioMovie;
     RadioButton radioShow;
     RadioButton radioPerson;
     ListView listView;
     RadioGroup radioGroup;
     ArrayList<Search>list;
     SearchAdapter adapter;
     EditText searchEditText;
     Button searchBtn;
    public SearchFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_search, container, false);
        searchEditText = v.findViewById(R.id.searchEditText);

        listView = v.findViewById(R.id.listViewSearch);
        final String API_KEY=getString(R.string.API_KEY);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                    list = new ArrayList<>();
                    LoadData("https://api.themoviedb.org/3/search/movie?api_key="+API_KEY+"&language=en-US&query="+searchEditText.getText().toString()+"&page=1");

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getActivity(),MovieActivity.class);
                            intent.putExtra("id",list.get(position).getId());
                            startActivity(intent);
                        }
                    });


                    LoadData1("https://api.themoviedb.org/3/search/tv?api_key="+API_KEY+"&language=en-US&query="+searchEditText.getText().toString()+"&page=1");

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getActivity(),TvShowActivity.class);
                            intent.putExtra("id",list.get(position).getId());
                            startActivity(intent);
                        }
                    });

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return v;
    }
    public void LoadData(String url)
    {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    JSONArray array = response.getJSONArray("results");
                    for (int i =0;i<5;i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        String id = object.getString("id");
                        String name = object.getString("title");
                        String img = "https://image.tmdb.org/t/p/w200"+object.getString("poster_path");
                        list.add(new Search(id,name,img));

                    }

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
    public void LoadData1(String url)
    {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    JSONArray array = response.getJSONArray("results");
                    for (int i =0;i<5;i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        String id = object.getString("id");
                        String name = object.getString("name");
                        String img = "https://image.tmdb.org/t/p/w200"+object.getString("poster_path");
                        list.add(new Search(id,name,img));
                    }
                    adapter = new SearchAdapter(getContext(),list);
                    listView.setAdapter(adapter);
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
