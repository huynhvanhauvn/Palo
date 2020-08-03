package com.hhub.palo.Activities.ChangeProfileActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hhub.palo.Activities.WelcomeActivity;
import com.hhub.palo.Models.Background;
import com.hhub.palo.Models.User;
import com.hhub.palo.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ChangeProfileActivity extends AppCompatActivity implements ChangeProfileView {

    private EditText edtName;
    private TextView txtBirthday;
    private RadioGroup radioGroup;
    private RadioButton radMale, radFemale, radSecret;
    private ImageButton btnCalendar;
    private ChangeProfilePresenter presenter;
    private CardView btnConfirm;
    private String gender = "2";
    private String id = "";
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);
        getSupportActionBar().hide();

        radioGroup = (RadioGroup) findViewById(R.id.cprofile_rag_gender);
        edtName = (EditText) findViewById(R.id.cprofile_edt_name);
        txtBirthday = (TextView) findViewById(R.id.cprofile_txt_birthday);
        radMale = (RadioButton) findViewById(R.id.cprofile_rad_male);
        radFemale = (RadioButton) findViewById(R.id.cprofile_rad_female);
        radSecret = (RadioButton) findViewById(R.id.cprofile_rad_secret);
        btnCalendar = (ImageButton) findViewById(R.id.cprofile_btn_birthday);
        btnConfirm = (CardView) findViewById(R.id.cprofile_btn_change);
        layout = (ConstraintLayout) findViewById(R.id.cprofile_layout);

        presenter = new ChangeProfilePresenter(this);
        presenter.getBackground();
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(WelcomeActivity.SHARED_DATA,
                Context.MODE_PRIVATE);
        id = preferences.getString(WelcomeActivity.USER_ID,"");
        if(!id.equals("")) {
            presenter.getProfile(id);
        }

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(ChangeProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(year,month,day);
                        txtBirthday.setText(format.format(calendar1.getTime()));
                    }
                }, year, month, day);
                dialog.show();
            }
        });

        //validate
        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable == null || editable.toString().equals("")) {
                    edtName.setError(getResources().getString(R.string.all_err_empty));
                }
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtName.getError() == null && txtBirthday.getText() != null && !txtBirthday.getText().equals("")) {
                    if(radMale.isChecked()) {
                        gender = "1";
                    } else if(radFemale.isChecked()) {
                        gender = "0";
                    } else {
                        gender = "2";
                    }
                    Log.d("hvhau","go");
                    presenter.updateProfie(id,edtName.getText().toString(),gender,txtBirthday.getText().toString());
                }
            }
        });
    }

    @Override
    public void showProfile(User user) {
        if(user.getFullName() != null) {
            edtName.setText(user.getFullName());
        }
        if(user.getGender() != null && !user.getGender().equals("")) {
            switch (user.getGender()) {
                case "1":
                    radMale.setChecked(true);
                    break;
                case "0":
                    radFemale.setChecked(true);
                    break;
                default:
                    radSecret.setChecked(true);
                    break;
            }
        }
        if(user.getBirthday() != null && !user.getBirthday().equals("")) {
            txtBirthday.setText(user.getBirthday());
        }
    }

    @Override
    public void updateSuccess() {
        finish();
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
