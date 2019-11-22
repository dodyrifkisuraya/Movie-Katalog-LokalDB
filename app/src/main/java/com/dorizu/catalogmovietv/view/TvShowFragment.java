package com.dorizu.catalogmovietv.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dorizu.catalogmovietv.BuildConfig;
import com.dorizu.catalogmovietv.adapter.MovieAdapter;
import com.dorizu.catalogmovietv.R;
import com.dorizu.catalogmovietv.model.MovieItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TvShowFragment extends Fragment {
    private RecyclerView.Adapter adapter;
    private ArrayList<MovieItem> arrayList;
    private RecyclerView recyclerView;


    private static final String TV_URL = "https://api.themoviedb.org/3/tv/top_rated?api_key="+ BuildConfig.API_KEY+"&language=en-US&page=1";

    public TvShowFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view_fragment_movie = inflater.inflate(R.layout.movie_fragment, container, false);

        recyclerView = view_fragment_movie.findViewById(R.id.rv_movie);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        arrayList = new ArrayList<>();

        loadUrlData();

        return view_fragment_movie;
    }

    private void loadUrlData() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                TV_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        MovieItem movies = new MovieItem();

                        JSONObject data = jsonArray.getJSONObject(i);
                        movies.setJudul(data.getString("original_name"));
                        movies.setOverview(data.getString("overview"));
                        movies.setTanggalRilis(data.getString("first_air_date"));
                        movies.setThunail(data.getString("poster_path"));
                        movies.setRateCount(data.getString("vote_count"));
                        movies.setRate(data.getString("vote_average"));
                        movies.setBahasa(data.getString("original_language"));
                        movies.setBanner(data.getString("backdrop_path"));
                        movies.setTv(true);
                        movies.setMovie(false);
                        arrayList.add(movies);
                    }
                    adapter = new MovieAdapter(arrayList, getActivity());
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                loadUrlData();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
