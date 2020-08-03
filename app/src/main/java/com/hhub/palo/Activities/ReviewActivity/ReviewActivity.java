package com.hhub.palo.Activities.ReviewActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hhub.palo.Activities.ReviewDetailActivity.ReviewDetailActivity;
import com.hhub.palo.Activities.WelcomeActivity;
import com.hhub.palo.Models.Movie;
import com.hhub.palo.Models.Review;
import com.hhub.palo.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ReviewActivity extends AppCompatActivity implements ReviewView {

    private ConstraintLayout layout, layoutIntro, layoutPic, layoutSound, layoutFeel;
    private ReviewPresenter presenter;
    private TextView txtTitle, txtIntro, txtStory, txtAct, txtPic, txtSound, txtFeel, txtMsg, txtEnd;
    private ImageButton btnIntro, btnPic, btnSound, btnFeel,
            btnImgStory, btnImgAct, btnImgPic, btnImgSound, btnImgFeel,btnImgMsg;
    private EditText edtIntro, edtStory, edtAct, edtPic, edtSound, edtFeel, edtMsg, edtEnd;
    private static final int CODE_STORY = 1, CODE_ACT = 2, CODE_PIC = 3, CODE_SOUND = 4, CODE_FEEL = 5, CODE_MSG = 6;
    private String storyPath="", actPath="", picPath="", soundPath="", feelPath="", msgPath="";
    private String storyFile="",actFile="",picFile="",soundFile="",feelFile="",msgFile="";
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 900;
    private CardView btnSend, btnUpdate;
    private int numUploadImg = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        getSupportActionBar().hide();

        init();

        presenter = new ReviewPresenter(this);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        final String idReview = intent.getStringExtra(ReviewDetailActivity.ID);
        String title = intent.getStringExtra(ReviewDetailActivity.TITLE);
        String poster = intent.getStringExtra(ReviewDetailActivity.POSTER);
        if(idReview != null) {
            txtTitle.setText(title);
            Glide.with(getApplicationContext()).load(poster).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    layout.setBackground(resource);
                }
            });
            presenter.getReview(idReview);
            btnUpdate.setVisibility(View.VISIBLE);
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!idReview.equals("") &&
                            edtStory != null && !edtStory.getText().toString().equals("") &&
                            edtAct != null && !edtAct.getText().toString().equals("") &&
                            edtMsg!= null && !edtMsg.getText().toString().equals("") &&
                            edtEnd != null && !edtEnd.getText().toString().equals("")) {
                        presenter.updateReview(idReview,
                                edtIntro != null ? edtIntro.getText().toString() : "",
                                edtStory.getText().toString(),
                                edtAct.getText().toString(),
                                edtPic != null ? edtPic.getText().toString() : "",
                                edtSound != null ? edtSound.getText().toString() : "",
                                edtFeel != null ? edtFeel.getText().toString() : "",
                                edtMsg.getText().toString(), edtEnd.getText().toString());
                    }
                }
            });
        }
        if(id != null) {
            presenter.showMovieInfo(id);
        }

        toggleLayout();

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        } else {}

        pickImage();
    }

    private void toggleLayout() {
        btnIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnIntro.setVisibility(View.GONE);
                layoutIntro.setVisibility(View.GONE);
            }
        });
        txtIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnIntro.setVisibility(View.VISIBLE);
                layoutIntro.setVisibility(View.VISIBLE);
            }
        });

        btnPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPic.setVisibility(View.GONE);
                btnImgPic.setVisibility(View.GONE);
                layoutPic.setVisibility(View.GONE);
            }
        });
        txtPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPic.setVisibility(View.VISIBLE);
                btnImgPic.setVisibility(View.VISIBLE);
                layoutPic.setVisibility(View.VISIBLE);
            }
        });

        btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSound.setVisibility(View.GONE);
                btnImgSound.setVisibility(View.GONE);
                layoutSound.setVisibility(View.GONE);
            }
        });
        txtSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSound.setVisibility(View.VISIBLE);
                btnImgSound.setVisibility(View.VISIBLE);
                layoutSound.setVisibility(View.VISIBLE);
            }
        });

        btnFeel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnFeel.setVisibility(View.GONE);
                btnImgFeel.setVisibility(View.GONE);
                layoutFeel.setVisibility(View.GONE);
            }
        });
        txtFeel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnFeel.setVisibility(View.VISIBLE);
                btnImgFeel.setVisibility(View.VISIBLE);
                layoutFeel.setVisibility(View.VISIBLE);
            }
        });
    }

    private void pickImage() {
        btnImgStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, CODE_STORY);
            }
        });
        btnImgAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, CODE_ACT);
            }
        });
        btnImgPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, CODE_PIC);
            }
        });
        btnImgSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, CODE_SOUND);
            }
        });
        btnImgFeel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, CODE_FEEL);
            }
        });
        btnImgMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, CODE_MSG);
            }
        });
    }

    @Override
    public void showMovieInfo(final Movie movie) {
        Glide.with(getApplicationContext()).load(movie.getPoster()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                layout.setBackground(resource);
            }
        });
        txtTitle.setText(movie.getTitle());
        btnSend.setVisibility(View.VISIBLE);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getApplicationContext().getSharedPreferences(WelcomeActivity.SHARED_DATA,
                        Context.MODE_PRIVATE);
                final String idUser = preferences.getString(WelcomeActivity.USER_ID,"");
                if(!idUser.equals("") && movie.getId() != null && !movie.getId().equals("") &&
                        edtStory != null && !edtStory.getText().toString().equals("") &&
                        edtAct != null && !edtAct.getText().toString().equals("") &&
                        edtMsg!= null && !edtMsg.getText().toString().equals("") &&
                        edtEnd != null && !edtEnd.getText().toString().equals("")) {
                    presenter.sendReview(idUser, movie.getId(),
                            edtIntro != null ? edtIntro.getText().toString() : "",
                            edtStory.getText().toString(),
                            edtAct.getText().toString(),
                            edtPic != null ? edtPic.getText().toString() : "",
                            edtSound != null ? edtSound.getText().toString() : "",
                            edtFeel != null ? edtFeel.getText().toString() : "",
                            edtMsg.getText().toString(), edtEnd.getText().toString());
                }
            }
        });
    }

    @Override
    public void showReview(Review review) {
        edtIntro.setText(review.getIntroduction());
        edtIntro.setSelection(edtIntro.getText().length());
        edtStory.setText(review.getStory());
        edtStory.setSelection(edtStory.getText().length());
        if(review.getStoryImage()!=null && !review.getStoryImage().equals("")){
            Glide.with(getApplicationContext()).load(review.getStoryImage()).centerCrop().into(btnImgStory);
            storyFile = review.getStoryImage().replace("http://huynhvanhaua.000webhostapp.com/movieimage/","");
        }
        edtAct.setText(review.getActing());
        edtAct.setSelection(edtAct.getText().length());
        if(review.getActingImage()!=null && !review.getActingImage().equals("")){
            Glide.with(getApplicationContext()).load(review.getActingImage()).centerCrop().into(btnImgAct);
            actFile = review.getActingImage().replace("http://huynhvanhaua.000webhostapp.com/movieimage/","");
        }
        edtPic.setText(review.getPicture());
        edtPic.setSelection(edtPic.getText().length());
        if(review.getPictureImage()!=null && !review.getPictureImage().equals("")){
            Glide.with(getApplicationContext()).load(review.getPictureImage()).centerCrop().into(btnImgPic);
            picFile = review.getPictureImage().replace("http://huynhvanhaua.000webhostapp.com/movieimage/","");
        }
        edtSound.setText(review.getSound());
        edtSound.setSelection(edtSound.getText().length());
        if(review.getSoundImage()!=null && !review.getSoundImage().equals("")){
            Glide.with(getApplicationContext()).load(review.getSoundImage()).centerCrop().into(btnImgSound);
            soundFile = review.getSoundImage().replace("http://huynhvanhaua.000webhostapp.com/movieimage/","");
        }
        edtFeel.setText(review.getFeel());
        edtFeel.setSelection(edtFeel.getText().length());
        if(review.getFeelImage()!=null && !review.getFeelImage().equals("")){
            Glide.with(getApplicationContext()).load(review.getFeelImage()).centerCrop().into(btnImgFeel);
            feelFile = review.getFeelImage().replace("http://huynhvanhaua.000webhostapp.com/movieimage/","");
        }
        edtMsg.setText(review.getMessage());
        edtMsg.setSelection(edtMsg.getText().length());
        if(review.getMessageImage()!=null && !review.getMessageImage().equals("")){
            Glide.with(getApplicationContext()).load(review.getMessageImage()).centerCrop().into(btnImgMsg);
            msgFile = review.getMessageImage().replace("http://huynhvanhaua.000webhostapp.com/movieimage/","");
        }
        edtEnd.setText(review.getEnd());
        edtEnd.setSelection(edtEnd.getText().length());
    }

    @Override
    public void updateReviewTextSuccess(String idReview) {
        numUploadImg = 0;
        if(!storyPath.equals("")) {
            numUploadImg++;
            if(storyPath.equals("")) {
                pushImage(storyPath, idReview, "storyImage", false,"");
            } else {
                pushImage(storyPath, idReview, "storyImage", true, storyFile);
            }
        }
        if(!actPath.equals("")) {
            numUploadImg++;
            if(actFile.equals("")){
                pushImage(actPath, idReview, "actingImage", false,"");
            } else {
                pushImage(actPath, idReview, "storyImage", true, actFile);
            }
        }
        if(!picPath.equals("")) {
            numUploadImg++;
            if(picFile.equals("")) {
                pushImage(picPath, idReview, "pictureImage", false,"");
            } else {
                pushImage(picPath, idReview, "pictureImage", true, picFile);
            }
        }
        if(!soundPath.equals("")) {
            numUploadImg++;
            if(soundFile.equals("")) {
                pushImage(soundPath, idReview, "soundImage", false,"");
            } else {
                pushImage(soundPath, idReview, "soundImage", true, soundFile);
            }
        }
        if(!feelPath.equals("")) {
            numUploadImg++;
            if(feelFile.equals("")) {
                pushImage(feelPath, idReview, "feelImage", false,"");
            } else {
                pushImage(feelPath, idReview, "feelImage", true, feelFile);
            }
        }
        if(!msgPath.equals("")) {
            numUploadImg++;
            if(msgFile.equals("")) {
                pushImage(msgPath, idReview, "messageImage", false,"");
            } else {
                pushImage(msgPath, idReview, "messageImage", true, msgFile);
            }
        }
        if(numUploadImg<=0) {
            finish();
        }
    }

    @Override
    public void sendReviewSuccess(String idReview) {
        numUploadImg = 0;
        if(!storyPath.equals("")) {
            numUploadImg++;
            pushImage(storyPath, idReview, "storyImage", false,"");
        }
        if(!actPath.equals("")) {
            numUploadImg++;
            pushImage(actPath, idReview, "actingImage", false,"");
        }
        if(!picPath.equals("")) {
            numUploadImg++;
            pushImage(picPath, idReview, "pictureImage", false,"");
        }
        if(!soundPath.equals("")) {
            numUploadImg++;
            pushImage(soundPath, idReview, "soundImage", false,"");
        }
        if(!feelPath.equals("")) {
            numUploadImg++;
            pushImage(feelPath, idReview, "feelImage", false,"");
        }
        if(!msgPath.equals("")) {
            numUploadImg++;
            pushImage(msgPath, idReview, "messageImage", false,"");
        }
        if(numUploadImg<=0) {
            finish();
        }
    }

    @Override
    public void uploadImageSuccess() {
        numUploadImg--;
        if(numUploadImg<=0) {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_STORY && data != null && data.getData() != null) {
            Uri uri = data.getData();
            storyPath = new File(getRealPathFromURI(uri)).getAbsolutePath();
            // Load image
            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(uri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                btnImgStory.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(requestCode == CODE_ACT && data != null && data.getData() != null) {
            Uri uri = data.getData();
            actPath = new File(getRealPathFromURI(uri)).getAbsolutePath();
            // Load image
            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(uri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                btnImgAct.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(requestCode == CODE_PIC && data != null && data.getData() != null) {
            Uri uri = data.getData();
            picPath = new File(getRealPathFromURI(uri)).getAbsolutePath();
            // Load image
            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(uri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                btnImgPic.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(requestCode == CODE_SOUND && data != null && data.getData() != null) {
            Uri uri = data.getData();
            soundPath = new File(getRealPathFromURI(uri)).getAbsolutePath();
            // Load image
            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(uri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                btnImgSound.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(requestCode == CODE_FEEL && data != null && data.getData() != null) {
            Uri uri = data.getData();
            feelPath = new File(getRealPathFromURI(uri)).getAbsolutePath();
            // Load image
            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(uri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                btnImgFeel.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(requestCode == CODE_MSG && data != null && data.getData() != null) {
            Uri uri = data.getData();
            msgPath = new File(getRealPathFromURI(uri)).getAbsolutePath();
            // Load image
            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(uri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                btnImgMsg.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void pushImage(String path, String id, String type, boolean isUpdate, String oldFile) {
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestFile);
        if(isUpdate) {
            presenter.updateImage(body,id,type,oldFile);
        } else {
            presenter.uploadImage(body,id,type);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {

                }
                return;
            }
        }
    }

    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    private void init() {
        layout = (ConstraintLayout) findViewById(R.id.review_layout);
        layoutIntro = (ConstraintLayout) findViewById(R.id.review_layout_intro);
        layoutPic = (ConstraintLayout) findViewById(R.id.review_layout_pic);
        layoutSound = (ConstraintLayout) findViewById(R.id.review_layout_sound);
        layoutFeel = (ConstraintLayout) findViewById(R.id.review_layout_feel);
        txtTitle = (TextView) findViewById(R.id.review_txt_title);
        txtIntro = (TextView) findViewById(R.id.review_txt_intro);
        txtStory = (TextView) findViewById(R.id.review_txt_story);
        txtAct = (TextView) findViewById(R.id.review_txt_act);
        txtPic = (TextView) findViewById(R.id.review_txt_pic);
        txtSound = (TextView) findViewById(R.id.review_txt_sound);
        txtFeel = (TextView) findViewById(R.id.review_txt_feel);
        txtMsg = (TextView) findViewById(R.id.review_txt_msg);
        txtEnd = (TextView) findViewById(R.id.review_txt_end);
        btnIntro = (ImageButton) findViewById(R.id.review_btn_close_intro);
        btnPic = (ImageButton) findViewById(R.id.review_btn_close_pic);
        btnSound = (ImageButton) findViewById(R.id.review_btn_close_sound);
        btnFeel = (ImageButton) findViewById(R.id.review_btn_close_feel);
        btnImgStory = (ImageButton) findViewById(R.id.review_btn_image_story);
        btnImgAct = (ImageButton) findViewById(R.id.review_btn_image_act);
        btnImgPic = (ImageButton) findViewById(R.id.review_btn_image_pic);
        btnImgSound = (ImageButton) findViewById(R.id.review_btn_image_sound);
        btnImgFeel = (ImageButton) findViewById(R.id.review_btn_image_feel);
        btnImgMsg = (ImageButton) findViewById(R.id.review_btn_image_msg);
        edtIntro = (EditText) findViewById(R.id.review_edt_intro);
        edtStory = (EditText) findViewById(R.id.review_edt_story);
        edtAct = (EditText) findViewById(R.id.review_edt_act);
        edtPic = (EditText) findViewById(R.id.review_edt_pic);
        edtSound = (EditText) findViewById(R.id.review_edt_sound);
        edtFeel = (EditText) findViewById(R.id.review_edt_feel);
        edtMsg = (EditText) findViewById(R.id.review_edt_msg);
        edtEnd = (EditText) findViewById(R.id.review_edt_end);
        btnSend = (CardView) findViewById(R.id.review_card_send);
        btnUpdate = (CardView) findViewById(R.id.review_card_update);
    }
}
