package com.sbro.palo.Activities.ReviewDetailActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sbro.palo.Activities.WelcomeActivity;
import com.sbro.palo.Models.Review;
import com.sbro.palo.R;

public class ReviewDetailActivity extends AppCompatActivity implements ReviewDetailView {

    public static final String ID = "idReview";
    public static final String TITLE = "title";
    public static final String POSTER = "poster";
    private String id, title, poster;
    private TextView txtTitle, txtIntro, txtStory, txtAct, txtPic, txtSound, txtFeel, txtMsg, txtEnd;
    private ImageView imgStory, imgAct, imgPic, imgSound, imgFeel, imgMsg;
    private ReviewDetailPresenter presenter;
    private ConstraintLayout layout, layoutVote;
    private CardView cardSend;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        id = intent.getStringExtra(ReviewDetailActivity.ID);
        title = intent.getStringExtra(ReviewDetailActivity.TITLE);
        poster = intent.getStringExtra(ReviewDetailActivity.POSTER);
        presenter = new ReviewDetailPresenter(this);

        init();
        Glide.with(getApplicationContext()).load(poster).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                layout.setBackground(resource);
            }
        });
        txtTitle.setText(title);
        if(id != null && !id.equals("")) {
            presenter.showReview(id);
        }
    }

    private void init() {
        txtTitle = (TextView) findViewById(R.id.review_detail_txt_title);
        txtIntro = (TextView) findViewById(R.id.review_detail_txt_intro);
        txtStory = (TextView) findViewById(R.id.review_detail_txt_story);
        txtAct = (TextView) findViewById(R.id.review_detail_txt_act);
        txtPic = (TextView) findViewById(R.id.review_detail_txt_pic);
        txtSound = (TextView) findViewById(R.id.review_detail_txt_sound);
        txtFeel = (TextView) findViewById(R.id.review_detail_txt_feel);
        txtMsg = (TextView) findViewById(R.id.review_detail_txt_msg);
        txtEnd = (TextView) findViewById(R.id.review_detail_txt_end);
        imgStory = (ImageView) findViewById(R.id.review_detail_img_story);
        imgAct = (ImageView) findViewById(R.id.review_detail_img_act);
        imgPic = (ImageView) findViewById(R.id.review_detail_img_pic);
        imgSound = (ImageView) findViewById(R.id.review_detail_img_sound);
        imgFeel = (ImageView) findViewById(R.id.review_detail_img_feel);
        imgMsg = (ImageView) findViewById(R.id.review_detail_img_msg);
        layout = (ConstraintLayout) findViewById(R.id.review_detail_layout);
        layoutVote = (ConstraintLayout) findViewById(R.id.review_detail_layout_vote);
        ratingBar = (RatingBar) findViewById(R.id.review_detail_rating_star);
        cardSend = (CardView) findViewById(R.id.review_detail_card_send);
    }

    @Override
    public void showReview(Review review) {
        if(!review.getIntroduction().equals("")) {
            txtIntro.setVisibility(View.VISIBLE);
            txtIntro.setText(review.getIntroduction());
        }
        if(!review.getStory().equals("")) {
            txtStory.setVisibility(View.VISIBLE);
            txtStory.setText(review.getStory());
        }
        if(!review.getStoryImage().equals("")) {
            imgStory.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(review.getStoryImage()).into(imgStory);
        }
        if(!review.getActing().equals("")) {
            txtAct.setVisibility(View.VISIBLE);
            txtAct.setText(review.getActing());
        }
        if(!review.getActingImage().equals("")) {
            imgAct.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(review.getActingImage()).into(imgAct);
        }
        if(!review.getPicture().equals("")) {
            txtPic.setVisibility(View.VISIBLE);
            txtPic.setText(review.getPicture());
        }
        if(!review.getPictureImage().equals("")) {
            imgPic.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(review.getPictureImage()).into(imgPic);
        }
        if(!review.getSound().equals("")) {
            txtSound.setVisibility(View.VISIBLE);
            txtSound.setText(review.getSound());
        }
        if(!review.getSoundImage().equals("")) {
            imgSound.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(review.getSoundImage()).into(imgSound);
        }
        if(!review.getFeel().equals("")) {
            txtFeel.setVisibility(View.VISIBLE);
            txtFeel.setText(review.getFeel());
        }
        if(!review.getFeelImage().equals("")) {
            imgFeel.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(review.getFeelImage()).into(imgFeel);
        }
        if(!review.getMessage().equals("")) {
            txtMsg.setVisibility(View.VISIBLE);
            txtMsg.setText(review.getMessage());
        }
        if(!review.getMessageImage().equals("")) {
            imgMsg.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(review.getMessageImage()).into(imgMsg);
        }
        if(!review.getEnd().equals("")) {
            txtEnd.setVisibility(View.VISIBLE);
            txtEnd.setText(review.getEnd());
        }

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(WelcomeActivity.SHARED_DATA,
                Context.MODE_PRIVATE);
        String idUser = preferences.getString(WelcomeActivity.USER_ID,"");
        if(!idUser.equals("")) {
            presenter.enableVote(idUser,id);
        }
    }

    @Override
    public void enableVote(final String idUser, final String idReview) {
        layoutVote.setVisibility(View.VISIBLE);
        cardSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ratingBar.getRating() > 0) {
                    presenter.rating(ratingBar.getRating(),idUser,idReview);
                } else {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.detail_not_rating),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void voteSuccess() {
        layoutVote.setVisibility(View.GONE);
    }
}
