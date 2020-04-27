package com.sbro.palo.Activities.SignUp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sbro.palo.Activities.LoginActivity.LoginActivity;
import com.sbro.palo.Activities.WelcomeActivity;
import com.sbro.palo.Models.User;
import com.sbro.palo.R;
import com.sbro.palo.Services.APIService;
import com.sbro.palo.Services.Service;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SignupActivity extends AppCompatActivity implements SignupView {

    private EditText edtUsername;
    private EditText edtPassword;
    private SignupPresenter presenter;
    private CardView btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().hide();
        presenter = new SignupPresenter(this);

        edtUsername = (EditText) findViewById(R.id.signup_edt_username);
        edtPassword = (EditText) findViewById(R.id.signup_edt_password);
        btnSignup = (CardView) findViewById(R.id.signup_btn_signup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtUsername != null && !edtUsername.getText().toString().equals("")
                        && edtPassword != null && !edtPassword.getText().toString().equals("")) {
                    Service service = APIService.getService();
                    Observable<String> observable = service.signup(edtUsername.getText().toString(),
                            edtPassword.getText().toString());
                    observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<String>() {
                                @Override
                                public void call(String s) {
                                    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                                    if(s.equals("success")) {
                                        presenter.signup(edtUsername.getText().toString(), edtPassword.getText().toString());
                                    }
                                }
                            });
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
}
