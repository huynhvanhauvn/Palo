package com.hhub.palo.Fragments.ProfileFragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hhub.palo.Activities.ChangePassActivity.ChangePassActivity;
import com.hhub.palo.Activities.ChangeProfileActivity.ChangeProfileActivity;
import com.hhub.palo.Activities.FeedbackActivity.FeedbackActivity;
import com.hhub.palo.Activities.LoginActivity.LoginActivity;
import com.hhub.palo.Activities.MyReviewActivity.MyReviewActivity;
import com.hhub.palo.Activities.WelcomeActivity;
import com.hhub.palo.Models.Background;
import com.hhub.palo.Models.User;
import com.hhub.palo.R;
import com.hhub.palo.Utils.NetworkStateReceiver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileFragment extends Fragment implements ProfileView, NetworkStateReceiver.NetworkStateReceiverListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 436;
    private static final int CODE_PIC = 783;
    private Button btnLogout, btnChangeProfile, btnChangePass, btnViewReview, btnAbout;
    private ProfilePresenter presenter;
    private ConstraintLayout layout;
    private TextView txtName, txtPoint;
    private NetworkStateReceiver networkStateReceiver;
    private ImageView imgAvatar;
    private String oldAvatar = "";
    private Bitmap selectedImage;
    private String id;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnLogout = (Button) view.findViewById(R.id.profile_btn_logout);
        layout = (ConstraintLayout) view.findViewById(R.id.profile_layout);
        btnChangeProfile = (Button) view.findViewById(R.id.profile_btn_edit_profile);
        btnChangePass = (Button) view.findViewById(R.id.profile_btn_change_pass);
        btnViewReview = (Button) view.findViewById(R.id.profile_btn_list_review);
        btnAbout = (Button) view.findViewById(R.id.profile_btn_about);
        txtName = (TextView) view.findViewById(R.id.profile_txt_name);
        txtPoint = (TextView) view.findViewById(R.id.profile_txt_rating);
        imgAvatar = (ImageView) view.findViewById(R.id.profile_img_avatar);

        presenter = new ProfilePresenter(this);
        presenter.getBackground();
        SharedPreferences preferences = getContext().getSharedPreferences(WelcomeActivity.SHARED_DATA,
                Context.MODE_PRIVATE);
        id = preferences.getString(WelcomeActivity.USER_ID,"");
        if(!id.equals("")) {
            presenter.getProfile(id);
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
            } else {}

            imgAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, CODE_PIC);
                }
            });
        }

        btnChangeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChangeProfileActivity.class);
                startActivity(intent);
            }
        });
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChangePassActivity.class);
                startActivity(intent);
            }
        });
        btnViewReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyReviewActivity.class);
                startActivity(intent);
            }
        });
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FeedbackActivity.class);
                startActivity(intent);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getContext()
                        .getSharedPreferences(WelcomeActivity.SHARED_DATA, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(WelcomeActivity.IS_LOGGED,false);
                editor.commit();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    @Override
    public void showBackground(Background background) {
        Glide.with(getContext()).load(background.getImage()).into(new CustomTarget<Drawable>() {

            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                layout.setBackground(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
    }

    @Override
    public void showProfile(User user) {
        if(user.getAvatar() != null && !user.getAvatar().equals("")) {
            oldAvatar = user.getAvatar().replace("http://huynhvanhaua.000webhostapp.com/avatarimage/","");
            Glide.with(getContext()).load(user.getAvatar()).centerCrop().into(imgAvatar);
        }
        txtName.setText(user.getName());
        float point = Float.parseFloat(user.getPoint());
        DecimalFormat format = new DecimalFormat("0.0");
        txtPoint.setText(format.format(point));
    }

    @Override
    public void updatedAvatar(String result) {
//        Toast.makeText(getContext(),result,Toast.LENGTH_SHORT).show();
        imgAvatar.setImageBitmap(selectedImage);
    }

    @Override
    public void networkAvailable() {

    }

    @Override
    public void networkUnavailable() {
        Toast.makeText(getContext(),"No Connection",Toast.LENGTH_SHORT).show();
    }

    public void startNetworkBroadcastReceiver(Context currentContext) {
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener((NetworkStateReceiver.NetworkStateReceiverListener) currentContext);
        registerNetworkBroadcastReceiver(currentContext);
    }

    public void registerNetworkBroadcastReceiver(Context currentContext) {
        currentContext.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    public void unregisterNetworkBroadcastReceiver(Context currentContext) {
        currentContext.unregisterReceiver(networkStateReceiver);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_PIC && data != null && data.getData() != null) {
            Uri uri = data.getData();
            String storyPath = new File(getRealPathFromURI(uri)).getAbsolutePath();
            // Load image
            InputStream imageStream = null;
            try {
                imageStream = getContext().getContentResolver().openInputStream(uri);
                selectedImage = BitmapFactory.decodeStream(imageStream);
//                imgAvatar.setImageBitmap(selectedImage);
                File file = new File(storyPath);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestFile);
                presenter.updateAvatar(body,id,oldAvatar);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContext().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(selectedImage != null && !selectedImage.equals("")) {
            imgAvatar.setImageBitmap(selectedImage);
        }
    }
}
