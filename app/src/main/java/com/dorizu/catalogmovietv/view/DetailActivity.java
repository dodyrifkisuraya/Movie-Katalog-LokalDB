package com.dorizu.catalogmovietv.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dorizu.catalogmovietv.R;
import com.dorizu.catalogmovietv.db.MovieHelper;
import com.dorizu.catalogmovietv.db.TvHelper;
import com.dorizu.catalogmovietv.model.FavoritItem;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{
    private boolean isFavorit = false;
    private FavoritItem modelFavorite;
    private int position;
    private MovieHelper movieHelper;
    private TvHelper tvHelper;

    public static String EXTRA_TITLE        = "extra_title";
    public static String EXTRA_OVERVIEW     = "extra_overview";
    public static String EXTRA_RELEASE_DATE = "extra_release_date";
    public static String EXTRA_POSTER_JPG   = "extra_poster_jpg";
    public static String EXTRA_BANNER       = "extra_banner";
    public static String EXTRA_RATE_COUNT   = "extra_rate_count";
    public static String EXTRA_RATE         = "extra_rate";
    public static String EXTRA_LANG         = "extra_lang";
    public static String EXTRA_ISMOVIE      = "extra_movie";
    public static String EXTRA_ISTVSHOW     = "extra_tvshow";

    public static final String EXTRA_FILM_FAVORITE = "extra_film_favorite";
    public static final String EXTRA_POSTION = "extra_position";

    public static final int REQUEST_ADD = 100;
    public static final int RESULT_ADD = 101;

    private TextView tvJudul,tvRating, tvRilis, tvOverview, tvOrilang;
    private ImageView imgThumnail, imgBanner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        getSupportActionBar().setTitle("");

        tvJudul = findViewById(R.id.tv_judul);
        tvRating = findViewById(R.id.tv_rating);
        tvRilis = findViewById(R.id.tv_rilis);
        tvOverview = findViewById(R.id.tv_overview);
        tvOrilang = findViewById(R.id.tv_lang);
        imgThumnail = findViewById(R.id.img_thumnail);
        imgBanner = findViewById(R.id.img_banner);
        tvOrilang = findViewById(R.id.tv_lang);

        FloatingActionButton fab = findViewById(R.id.fab);


        final String title = getIntent().getStringExtra(EXTRA_TITLE);
        final String overview = getIntent().getStringExtra(EXTRA_OVERVIEW);
        String rating = getIntent().getStringExtra(EXTRA_RATE);
        String rating_count = getIntent().getStringExtra(EXTRA_RATE_COUNT);
        final String poster_jpg = getIntent().getStringExtra(EXTRA_POSTER_JPG);
        String banner = getIntent().getStringExtra(EXTRA_BANNER);
        String release_date = getIntent().getStringExtra(EXTRA_RELEASE_DATE);
        String ori_language = getIntent().getStringExtra(EXTRA_LANG);
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = date_format.parse(release_date);

            SimpleDateFormat new_date_format = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            String date_of_release = new_date_format.format(date);
            tvRilis.setText(date_of_release);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvJudul.setText(title);
        tvRating.setText("( "+rating+"/10 ) "+rating_count+" Ratingers");
        tvOverview.setText(overview);
        tvOrilang.setText(ori_language);
        Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w342/"+poster_jpg).into(imgThumnail);
        Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w342/"+banner).into(imgBanner);

        movieHelper = MovieHelper.getInstance(getApplicationContext());
        movieHelper.open();
        modelFavorite = getIntent().getParcelableExtra(EXTRA_FILM_FAVORITE);

        tvHelper = TvHelper.getInstance(getApplicationContext());
        tvHelper.open();

        if (modelFavorite != null){
            position = getIntent().getIntExtra(EXTRA_POSTION, 0);
            isFavorit = true;
            fab.hide();
        }else{
            modelFavorite = new FavoritItem();
        }


        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab){

            modelFavorite.setJudul(tvJudul.getText().toString().trim());
            modelFavorite.setOverview(tvOverview.getText().toString().trim());
            modelFavorite.setThumnail(getIntent().getStringExtra(EXTRA_POSTER_JPG));

            Intent intent = new Intent();
            intent.putExtra(EXTRA_FILM_FAVORITE, modelFavorite);
            intent.putExtra(EXTRA_POSTION, position);

            if (!isFavorit){
                long result = 0;
                if (getIntent().getBooleanExtra(EXTRA_ISMOVIE, false)){
                    result = movieHelper.insertFilm(modelFavorite);
                }else if(getIntent().getBooleanExtra(EXTRA_ISTVSHOW, false)){
                    result = tvHelper.insertTv(modelFavorite);
                }

                if (result > 0){
                    setResult(RESULT_ADD, intent);
                    Toast.makeText(DetailActivity.this, getString(R.string.succes_add), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }else{
                Toast.makeText(DetailActivity.this, getString(R.string.fail_add), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
