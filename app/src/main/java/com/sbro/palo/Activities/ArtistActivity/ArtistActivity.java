package com.sbro.palo.Activities.ArtistActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sbro.palo.Activities.MovieDetail.MovieDetailActivity;
import com.sbro.palo.Adapter.RecentAdapter;
import com.sbro.palo.Models.Artist;
import com.sbro.palo.Models.Movie;
import com.sbro.palo.R;

import java.util.ArrayList;

public class ArtistActivity extends AppCompatActivity implements ArtistView {

    public static final String ID = "id";
    private String id="";
    private ArtistPresenter presenter;
    private RoundedImageView imgAvatar;
    private TextView txtName, txtOld, txtBirthday, txtRole, txtNation, txtLabelDirect, txtLabelWrite, txtLabelAct;
    private RecyclerView recyclerDirect, recyclerWrite, recyclerAct;
    private ConstraintLayout layout;
    private Artist mArtist = new Artist();
    private ConstraintLayout layoutBirthday, layoutRole, layoutNation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        getSupportActionBar().hide();

        imgAvatar = (RoundedImageView) findViewById(R.id.artist_img_avatar);
        txtName = (TextView) findViewById(R.id.artist_txt_fullname);
        txtOld = (TextView) findViewById(R.id.artist_txt_yearold);
        txtBirthday = (TextView) findViewById(R.id.artist_txt_birthday);
        txtRole = (TextView) findViewById(R.id.artist_txt_role);
        txtNation = (TextView) findViewById(R.id.artist_txt_nation);
        txtLabelDirect = (TextView) findViewById(R.id.artist_label_direct);
        txtLabelWrite = (TextView) findViewById(R.id.artist_label_write);
        txtLabelAct = (TextView) findViewById(R.id.artist_label_act);
        recyclerDirect = (RecyclerView) findViewById(R.id.artist_recycler_director);
        recyclerWrite = (RecyclerView) findViewById(R.id.artist_recycler_write);
        recyclerAct = (RecyclerView) findViewById(R.id.artist_recycler_act);
        layout = (ConstraintLayout) findViewById(R.id.artist_layout);
        layoutBirthday = (ConstraintLayout) findViewById(R.id.artist_layout_birthday);
        layoutRole = (ConstraintLayout) findViewById(R.id.artist_layout_role);
        layoutNation = (ConstraintLayout) findViewById(R.id.artist_layout_nation);

        presenter = new ArtistPresenter(this);
        Intent intent = getIntent();
        id = intent.getStringExtra(ID);
        if(!id.equals("")) {
            presenter.getArtist(id);
        }
    }

    @Override
    public void showArtist(Artist artist) {
        mArtist = artist;
        Glide.with(getApplicationContext()).load(artist.getImage()).into(imgAvatar);
        txtName.setText(artist.getName());
        txtOld.setText("");
        txtBirthday.setText(artist.getBirthday());
        txtRole.setText(artist.getRole());
        txtNation.setText(artist.getNation());
        presenter.getDirectList(artist.getId());
        presenter.getWriteList(artist.getId());
        presenter.getActList(artist.getId());
        layoutBirthday.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.anim_load_list));
        layoutBirthday.setVisibility(View.VISIBLE);
        layoutRole.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.anim_load_list));
        layoutRole.setVisibility(View.VISIBLE);
        layoutNation.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.anim_load_list));
        layoutNation.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDirectList(final ArrayList<Movie> movies) {
        if(mArtist.getRole().equals("Director")) {
            Glide.with(getApplicationContext()).load(movies.get(0).getPoster()).centerCrop().into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    layout.setBackground(resource);
                }
            });
        }
        txtLabelDirect.setVisibility(View.VISIBLE);
        recyclerDirect.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerDirect.setLayoutManager(layoutManager);
        RecentAdapter adapter = new RecentAdapter(getApplicationContext(),movies);
        recyclerDirect.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecentAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
                intent.putExtra("id",movies.get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void showWriteList(final ArrayList<Movie> movies) {
        if(mArtist.getRole().equals("Write")) {
            Glide.with(getApplicationContext()).load(movies.get(0).getPoster()).centerCrop().into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    layout.setBackground(resource);
                }
            });
        }
        txtLabelWrite.setVisibility(View.VISIBLE);
        recyclerWrite.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerWrite.setLayoutManager(layoutManager);
        RecentAdapter adapter = new RecentAdapter(getApplicationContext(),movies);
        recyclerWrite.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecentAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
                intent.putExtra("id",movies.get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void showActList(final ArrayList<Movie> movies) {
        if(mArtist.getRole().equals("Cast")) {
            Glide.with(getApplicationContext()).load(movies.get(0).getPoster()).centerCrop().into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    layout.setBackground(resource);
                }
            });
        }
        txtLabelAct.setVisibility(View.VISIBLE);
        recyclerAct.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerAct.setLayoutManager(layoutManager);
        RecentAdapter adapter = new RecentAdapter(getApplicationContext(),movies);
        recyclerAct.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecentAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
                intent.putExtra("id",movies.get(position).getId());
                startActivity(intent);
            }
        });
    }
}