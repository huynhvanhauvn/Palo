package com.sbro.palo.Fragments.ProfileFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sbro.palo.Activities.LoginActivity.LoginActivity;
import com.sbro.palo.Activities.WelcomeActivity;
import com.sbro.palo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private Button btnLogout;

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

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getContext()
                        .getSharedPreferences(WelcomeActivity.SHARED_DATA, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString(WelcomeActivity.USER_NAME,"");
//                editor.putString(WelcomeActivity.PASSWORD,"");
                editor.putBoolean(WelcomeActivity.IS_LOGGED,false);
                editor.commit();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}
