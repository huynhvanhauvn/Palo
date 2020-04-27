package com.sbro.palo.Activities.LoginActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sbro.palo.Activities.MainActivity;
import com.sbro.palo.Activities.SignUp.SignupActivity;
import com.sbro.palo.Activities.WelcomeActivity;
import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.User;
import com.sbro.palo.R;
import com.sbro.palo.Services.APIService;
import com.sbro.palo.Services.Service;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity implements LoginView{

    private LoginPresenter presenter;
    private EditText edtUser, edtPass;
    private CardView btnLogin;
    private TextView txtSignup;
    private ImageView imgBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        btnLogin = (CardView) findViewById(R.id.login_btn_login);
        edtUser = (EditText) findViewById(R.id.login_edt_username);
        edtPass = (EditText) findViewById(R.id.login_edt_password);
        txtSignup = (TextView) findViewById(R.id.login_txt_signup);
        imgBackground = (ImageView) findViewById(R.id.login_background);
        presenter = new LoginPresenter(this);

        final Service service = APIService.getService();
        presenter.updateBackground();

        SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences(WelcomeActivity.SHARED_DATA,Context.MODE_PRIVATE);
        String user = sharedPreferences.getString(WelcomeActivity.USER_NAME,"");
        String pass = sharedPreferences.getString(WelcomeActivity.PASSWORD,"");
        if(user != null && !user.equals("")) {
            edtUser.setText(user);
        }
        if(pass != null && !user.equals("")) {
            edtPass.setText(pass);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtUser.getText() != null && !edtUser.getText().toString().equals("")
                        && edtPass.getText() != null && !edtPass.getText().toString().equals("")) {
                    presenter.infoUser(edtUser.getText().toString(),
                            edtPass.getText().toString(),getApplicationContext(),getResources());
                }
            }
        });

        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void infoUser(User user) {
        SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences(WelcomeActivity.SHARED_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(WelcomeActivity.USER_ID,user.getId());
        editor.putString(WelcomeActivity.USER_NAME,user.getName());
        editor.putString(WelcomeActivity.PASSWORD,user.getPassword());
        editor.putBoolean(WelcomeActivity.IS_LOGGED,true);
        editor.commit();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void updateBackground(Background background) {
        Glide.with(getApplicationContext()).load(background.getImage()).into(imgBackground);
    }
}
