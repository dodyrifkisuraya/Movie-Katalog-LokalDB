package com.dorizu.catalogmovietv.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dorizu.catalogmovietv.adapter.MovieFavoriteAdapater;
import com.dorizu.catalogmovietv.R;
import com.dorizu.catalogmovietv.db.TvHelper;
import com.dorizu.catalogmovietv.helper.MappingTvHelper;
import com.dorizu.catalogmovietv.model.FavoritItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class TvFavoriteFragment extends Fragment implements LoadTvCallback, MovieFavoriteAdapater.OnItemClickListener {
    private ProgressBar progressBar;
    private RecyclerView rvMovie;
    private MovieFavoriteAdapater adapater;
    private TvHelper tvHelper;
    private int posisi;
    private FavoritItem favoritItem;
    private static final String EXTRA_STATE = "extra_state";

    private final int ALERT_DIALOG_CLOSE = 10;
    public static final int RESULT_DELETE = 301;
    private final int ALERT_DIALOG_DELETE = 20;
    public static final String EXTRA_POSITION = "extra_position";

    public TvFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_favorite, container, false);

        progressBar = rootView.findViewById(R.id.progressBar);
        rvMovie = rootView.findViewById(R.id.rv_movie);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovie.setHasFixedSize(true);
        adapater = new MovieFavoriteAdapater(getActivity());
        rvMovie.setAdapter(adapater);
        adapater.setOnItemClickListener(this);

        tvHelper = TvHelper.getInstance(getContext());
        tvHelper.open();

        new LoadTvAsync(tvHelper, this).execute();

        if (savedInstanceState == null){
            new LoadTvAsync(tvHelper, this).execute();
        }else{
            ArrayList<FavoritItem> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null){
                adapater.setListMovieFavorite(list);
            }
        }

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == MovieFavoriteFragment.RESULT_DELETE){
            int position = data.getIntExtra(TvFavoriteFragment.EXTRA_POSITION, 0);
            adapater.removeItem(position);
            showSnackbarMessage("Satu Item Berhasil Dihapus");
        }

        Log.d("ResultCode", String.valueOf(resultCode));
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapater.getListMovieFavorite());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tvHelper.close();
    }

    private void showSnackbarMessage(String message){
        Snackbar.make(rvMovie, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<FavoritItem> modelFavorites) {
        progressBar.setVisibility(View.INVISIBLE);
        if (modelFavorites.size() > 0){
            adapater.setListMovieFavorite(modelFavorites);
        }else{
            adapater.setListMovieFavorite(new ArrayList<FavoritItem>());
            showSnackbarMessage("Tidak Ada Data Saat Ini");
        }
    }

    @Override
    public void OnItemClick(int id) {
        showAlertDialog(ALERT_DIALOG_DELETE, id);
    }

    private static class LoadTvAsync extends AsyncTask<Void, Void, ArrayList<FavoritItem>> {

        private final WeakReference<TvHelper> weakTvHelper;
        private final WeakReference<LoadTvCallback> weakCallback;

        private LoadTvAsync(TvHelper tvHelper, LoadTvCallback callback){
            weakTvHelper = new WeakReference<>(tvHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<FavoritItem> doInBackground(Void... voids) {
            Cursor dataCursor = weakTvHelper.get().queryAll();
            return MappingTvHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<FavoritItem> modelFavorites) {
            super.onPostExecute(modelFavorites);

            weakCallback.get().postExecute(modelFavorites);
        }
    }

    private void showAlertDialog(int type, final int idmovie) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;

        if (isDialogClose) {
            dialogTitle = "Batal";
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada form?";
        } else {
            dialogMessage = "Apakah anda yakin ingin menghapus item ini?";
            dialogTitle = "Hapus Note";
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (isDialogClose) {
                            getActivity().finish();
                        } else {
                            long result = tvHelper.deleteFilm(idmovie);
                            if (result > 0) {
                                adapater.removeItem(posisi);
                                showSnackbarMessage("Satu Item Berhasil Dihapus");
                            } else {
                                Toast.makeText(getActivity(), "Gagal menghapus data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}

interface LoadTvCallback{
    void preExecute();
    void postExecute(ArrayList<FavoritItem> modelFavorites);
}