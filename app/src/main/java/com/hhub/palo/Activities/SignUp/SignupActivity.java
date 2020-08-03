package com.hhub.palo.Activities.SignUp;

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
import android.view.View;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hhub.palo.Activities.LoginActivity.LoginActivity;
import com.hhub.palo.Activities.WelcomeActivity;
import com.hhub.palo.Models.Background;
import com.hhub.palo.R;

public class SignupActivity extends AppCompatActivity implements SignupView {

    private EditText edtUsername;
    private EditText edtPassword;
    private SignupPresenter presenter;
    private CardView btnSignup;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().hide();
        presenter = new SignupPresenter(this);

        edtUsername = (EditText) findViewById(R.id.signup_edt_username);
        edtPassword = (EditText) findViewById(R.id.signup_edt_password);
        btnSignup = (CardView) findViewById(R.id.signup_btn_signup);
        layout = (ConstraintLayout) findViewById(R.id.signup_layout);

        presenter.getBackground();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtUsername != null && !edtUsername.getText().toString().equals("")
                        && edtPassword != null && !edtPassword.getText().toString().equals("")) {
                    presenter.signup(edtUsername.getText().toString(), edtPassword.getText().toString());
                }
            }
        });
    }

    @Override
    public void signup(String username, String password) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(WelcomeActivity.SHARED_DATA,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(WelcomeActivity.USER_NAME,username);
        editor.putString(WelcomeActivity.PASSWORD,password);
        editor.commit();
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    @Override
    public void showBackground(Background background) {
        Glide.with(getApplicationContext()).load(background.getImage()).into(new CustomTarget<Drawable>() {

            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                layout.setBackground(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
    }
}
