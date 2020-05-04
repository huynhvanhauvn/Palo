package com.sbro.palo.Activities.ChangePassActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sbro.palo.Activities.LoginActivity.LoginActivity;
import com.sbro.palo.Activities.WelcomeActivity;
import com.sbro.palo.R;

public class ChangePassActivity extends AppCompatActivity implements ChangePassView {

    private EditText edtOld, edtNew, edtConfirm;
    private String oldPass, newPass, confirmPass;
    private String mPass, mId;
    private CardView btnChange;
    private ChangePassPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        getSupportActionBar().hide();

        edtOld = (EditText) findViewById(R.id.pass_edt_pass);
        edtNew = (EditText) findViewById(R.id.pass_edt_newpass);
        edtConfirm = (EditText) findViewById(R.id.pass_edt_confirmpass);
        btnChange = (CardView) findViewById(R.id.pass_btn_change);

        presenter = new ChangePassPresenter(this);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(WelcomeActivity.SHARED_DATA,
                Context.MODE_PRIVATE);
        mPass = preferences.getString(WelcomeActivity.PASSWORD,"");
        mId = preferences.getString(WelcomeActivity.USER_ID,"");

        edtOld.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                oldPass = editable.toString();
                if(!oldPass.equals(mPass)) {
                    edtOld.setError(getResources().getString(R.string.pass_err_wrong));
                }
            }
        });

        edtNew.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                newPass = editable.toString();
                if(newPass.equals(mPass)) {
                    edtNew.setError(getResources().getString(R.string.pass_err_different));
                } else {
                    if(newPass.equals("")) {
                        edtNew.setError(getResources().getString(R.string.all_err_empty));
                    }
                }
            }
        });

        edtConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                confirmPass = editable.toString();
                if(!confirmPass.equals(newPass)) {
                    edtConfirm.setError(getResources().getString(R.string.pass_err_not_match));
                }
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((edtOld.getError() == null || edtOld.getError().length()<=0) &&
                        (edtNew.getError() == null || edtNew.getError().length()<=0) &&
                        (edtConfirm.getError() == null || edtConfirm.getError().length()<=0)) {
                    presenter.changePassword(mId, newPass);
                }
            }
        });
    }

    @Override
    public void ChangeSuccess() {
        SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences(WelcomeActivity.SHARED_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(WelcomeActivity.IS_LOGGED,false);
        editor.putString(WelcomeActivity.PASSWORD,"");
        editor.commit();
        Intent intent = new Intent(ChangePassActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
