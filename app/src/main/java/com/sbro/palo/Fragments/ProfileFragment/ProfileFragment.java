package com.sbro.palo.Fragments.ProfileFragment;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sbro.palo.Activities.ChangePassActivity.ChangePassActivity;
import com.sbro.palo.Activities.ChangeProfileActivity.ChangeProfileActivity;
import com.sbro.palo.Activities.LoginActivity.LoginActivity;
import com.sbro.palo.Activities.MyReviewActivity.MyReviewActivity;
import com.sbro.palo.Activities.WelcomeActivity;
import com.sbro.palo.Models.Background;
import com.sbro.palo.Models.User;
import com.sbro.palo.R;
import com.sbro.palo.Utils.NetworkStateReceiver;

import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements ProfileView, NetworkStateReceiver.NetworkStateReceiverListener {

    private Button btnLogout, btnChangeProfile, btnChangePass, btnViewReview, btnAbout;
    private ProfilePresenter presenter;
    private ConstraintLayout layout;
    private TextView txtName, txtPoint;
    private NetworkStateReceiver networkStateReceiver;

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

        presenter = new ProfilePresenter(this);
        presenter.getBackground();
        SharedPreferences preferences = getContext().getSharedPreferences(WelcomeActivity.SHARED_DATA,
                Context.MODE_PRIVATE);
        String id = preferences.getString(WelcomeActivity.USER_ID,"");
        if(!id.equals("")) {
            presenter.getProfile(id);
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
    public void showBackdround(Background background) {
        Glide.with(getContext()).load(background.getImage()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                layout.setBackground(resource);
            }
        });
    }

    @Override
    public void showProfile(User user) {
        txtName.setText(user.getName());
        float point = Float.parseFloat(user.getPoint());
        DecimalFormat format = new DecimalFormat("0.0");
        txtPoint.setText(format.format(point));
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
}
