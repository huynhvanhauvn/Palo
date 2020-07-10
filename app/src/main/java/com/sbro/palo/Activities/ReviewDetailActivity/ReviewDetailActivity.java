package com.sbro.palo.Activities.ReviewDetailActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.internal.ShareFeedContent;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMedia;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareStoryContent;
import com.facebook.share.widget.ShareDialog;
import com.sbro.palo.Activities.WelcomeActivity;
import com.sbro.palo.Models.Review;
import com.sbro.palo.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class ReviewDetailActivity extends AppCompatActivity implements ReviewDetailView {

    public static final String ID = "idReview";
    public static final String TITLE = "title";
    public static final String POSTER = "poster";
    public static final String AUTHOR = "author";
    private String id, title, poster, author;
    private TextView txtTitle, txtIntro, txtStory, txtAct, txtPic, txtSound, txtFeel, txtMsg, txtEnd, txtAuthor;
    private ImageView imgStory, imgAct, imgPic, imgSound, imgFeel, imgMsg;
    private ReviewDetailPresenter presenter;
    private ConstraintLayout layout, layoutVote;
    private CardView cardSend;
    private RatingBar ratingBar;
    private ImageButton btnShare;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);
        getSupportActionBar().hide();

        printKeyHash();

        Intent intent = getIntent();
        id = intent.getStringExtra(ReviewDetailActivity.ID);
        title = intent.getStringExtra(ReviewDetailActivity.TITLE);
        poster = intent.getStringExtra(ReviewDetailActivity.POSTER);
        author = intent.getStringExtra(ReviewDetailActivity.AUTHOR);
        presenter = new ReviewDetailPresenter(this);

        FacebookSdk.sdkInitialize(getApplicationContext());

        init();
        Glide.with(getApplicationContext()).load(poster).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                layout.setBackground(resource);
            }
        });
        txtTitle.setText(title);
        txtAuthor.setText(author);
        txtAuthor.setVisibility(View.VISIBLE);
        if(id != null && !id.equals("")) {
            presenter.showReview(id);
        }
    }

    private void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.sbro.palo", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest digest = MessageDigest.getInstance("SHA");
                digest.update(signature.toByteArray());
                Log.d("hvhau", Base64.encodeToString(digest.digest(),Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
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
        txtAuthor = (TextView) findViewById(R.id.review_detail_txt_author);
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
        btnShare = (ImageButton) findViewById(R.id.review_detail_btn_share);
    }

    @Override
    public void showReview(final Review review) {
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

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postFacebook(review);
            }
        });
    }

    private void postFacebook(Review review) {
        Log.d("hvhau","click");
        ArrayList<SharePhoto> sharePhotos = new ArrayList<>();
        if(review.getIntroduction() != null && !review.getIntroduction().equals("")) {
            SharePhoto photo = new SharePhoto.Builder().setCaption(review.getIntroduction()).build();
            sharePhotos.add(photo);
        }
        if(review.getStory() != null && !review.getStory().equals("")) {
            SharePhoto photo;
            if(review.getStoryImage() != null && !review.getStoryImage().equals("")) {
                photo = new SharePhoto.Builder().setCaption(review.getStory())
                        .setImageUrl(Uri.parse(review.getStoryImage())).build();
            } else {
                photo = new SharePhoto.Builder().setCaption(review.getStory()).build();
            }
            sharePhotos.add(photo);
        }
        SharePhotoContent photoContent = new SharePhotoContent.Builder()
                .addPhotos(sharePhotos).build();

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        //shareDialog.show(this,photoContent);
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Log.d("hvhau",result.getPostId());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.d("hvhau",error.getLocalizedMessage());
            }
        });
        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setQuote("Hello").setContentUrl(Uri.parse(review.getStoryImage())).build();
        shareDialog.show(linkContent);
//        Glide.with(getApplicationContext())
//                .asBitmap()
//                .load(review.getStoryImage())
//                .into(new CustomTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                        SharePhoto photo = new SharePhoto.Builder()
//                                .setBitmap(resource).build();
//                        SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo)
//                                .build();
//                        shareDialog.canShow(content);
//                    }
//
//                    @Override
//                    public void onLoadCleared(@Nullable Drawable placeholder) {
//                    }
//                });
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
