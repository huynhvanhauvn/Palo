package com.hhub.palo.Activities.FeedbackActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hhub.palo.Activities.WelcomeActivity;
import com.hhub.palo.Models.Background;
import com.hhub.palo.R;

import java.util.ArrayList;

public class FeedbackActivity extends AppCompatActivity implements FeedbackView {

    private EditText edtFeedback;
    private CardView cardSend;
    private ConstraintLayout layout;
    private FeedbackPresenter presenter;
    private ArrayList<String> feedbackTypes;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        getSupportActionBar().hide();

        layout = (ConstraintLayout) findViewById(R.id.feedback_layout);
        edtFeedback = (EditText) findViewById(R.id.feedback_edt);
        cardSend = (CardView) findViewById(R.id.feedback_card_send);
        spinner = (Spinner) findViewById(R.id.feedback_spin);

        feedbackTypes = new ArrayList<>();
        feedbackTypes.add(getResources().getString(R.string.feedback_type_ux));
        feedbackTypes.add(getResources().getString(R.string.feedback_type_content));

        ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(),
                R.layout.simple_spinner_item,feedbackTypes);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        presenter = new FeedbackPresenter(this);
        presenter.getBackground();

        cardSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtFeedback.getText().toString() != null && !edtFeedback.getText().toString().equals("")) {
                    SharedPreferences preferences = getApplicationContext()
                            .getSharedPreferences(WelcomeActivity.SHARED_DATA,
                            Context.MODE_PRIVATE);
                    String id = preferences.getString(WelcomeActivity.USER_ID,"");
                    if(!id.equals("")) {
                        presenter.sendFeedBack(spinner.getSelectedItemPosition(),
                                edtFeedback.getText().toString(), id);
                    }
                } else {
                    edtFeedback.setError(getResources().getString(R.string.feedback_err));
                }
            }
        });
    }

    @Override
    public void feedbackSuccess() {
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
