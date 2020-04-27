package com.sbro.palo.Activities.ReviewActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
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
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sbro.palo.Activities.WelcomeActivity;
import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.Movie;
import com.sbro.palo.R;
import com.sbro.palo.Services.APIService;
import com.sbro.palo.Services.Service;
import com.sbro.palo.Utils.ImageUtil;
import com.sbro.palo.Utils.ReadPathUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ReviewActivity extends AppCompatActivity implements ReviewView {

    private ConstraintLayout layout, layoutIntro, layoutPic, layoutSound, layoutFeel;
    private ReviewPresenter presenter;
    private Service service = APIService.getService();
    private TextView txtTitle, txtIntro, txtStory, txtAct, txtPic, txtSound, txtFeel, txtMsg, txtEnd;
    private ImageButton btnIntro, btnPic, btnSound, btnFeel,
            btnImgStory, btnImgAct, btnImgPic, btnImgSound, btnImgFeel,btnImgMsg;
    private EditText edtIntro, edtStory, edtAct, edtPic, edtSound, edtFeel, edtMsg, edtEnd;
    private static final int CODE_STORY = 1, CODE_ACT = 2, CODE_PIC = 3, CODE_SOUND = 4, CODE_FEEL = 5, CODE_MSG = 6;
    private String storyPath="", actPath="", picPath="", soundPath="", feelPath="", msgPath="";
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 900;
    private CardView btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        getSupportActionBar().hide();

        init();

        presenter = new ReviewPresenter(this);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        if(id != null) {
            Observable<Movie> movieObservable = service.movie(id);
            movieObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Movie>() {
                        @Override
                        public void call(Movie movie) {
                            if(movie != null) {
                                presenter.showMovieInfo(movie);
                            }
                        }
                    });
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
                Toast.makeText(getApplicationContext(),"click1",Toast.LENGTH_SHORT).show();
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
                    Log.d("hvhau", idUser + "," + movie.getId() + "," +
                            edtIntro != null ? edtIntro.getText().toString() : "" + "," +
                            edtStory.getText().toString() + "," + "" + "," +
                            edtAct.getText().toString() + "," + "" + "," +
                            edtPic != null ? edtPic.getText().toString() : "" + "," + "" + "," +
                            edtSound != null ? edtSound.getText().toString() : "" + "," + "" + "," +
                            edtFeel != null ? edtFeel.getText().toString() : "" + "," + "" + "," +
                            edtMsg.getText().toString() + "," + "" + "," + edtEnd.getText().toString());
                    Observable<String> sendObservable = service.review(idUser, movie.getId(),
                            edtIntro != null ? edtIntro.getText().toString() : "",
                            edtStory.getText().toString(), "",
                            edtAct.getText().toString(), "",
                            edtPic != null ? edtPic.getText().toString() : "", "",
                            edtSound != null ? edtSound.getText().toString() : "", "",
                            edtFeel != null ? edtFeel.getText().toString() : "", "",
                            edtMsg.getText().toString(), "", edtEnd.getText().toString());
                    sendObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<String>() {
                                @Override
                                public void call(String s) {
                                    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                                    if(s != null && !s.equals("")) {
                                        if(!storyPath.equals("")) {
                                            pushImage(storyPath, s, "storyImage");
                                        }
                                        if(!actPath.equals("")) {
                                            pushImage(actPath, s, "actingImage");
                                        }
                                        if(!picPath.equals("")) {
                                            pushImage(picPath, s, "pictureImage");
                                        }
                                        if(!soundPath.equals("")) {
                                            pushImage(soundPath, s, "soundImage");
                                        }
                                        if(!feelPath.equals("")) {
                                            pushImage(feelPath, s, "feelImage");
                                        }
                                        if(!msgPath.equals("")) {
                                            pushImage(msgPath, s, "messageImage");
                                        }
                                    }
                                }
                            });
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_STORY && data.getData() != null) {
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
        if(requestCode == CODE_ACT && data.getData() != null) {
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
        if(requestCode == CODE_PIC && data.getData() != null) {
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
        if(requestCode == CODE_SOUND && data.getData() != null) {
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
        if(requestCode == CODE_FEEL && data.getData() != null) {
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
        if(requestCode == CODE_MSG && data.getData() != null) {
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

    public void pushImage(String path, String id, String type) {
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestFile);
        Observable<String> observable = service.uploadImage(body, id, type);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {

                    }
                });
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
    }
}
