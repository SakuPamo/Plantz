package com.saku.plantz.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.saku.plantz.LoginActivity;
import com.saku.plantz.R;


public class ProfileFragment extends FragmentBase {

    private Button signOutBtn;
    private Context context;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResource(), container, false);
        this.context = getContext();
        setUI(view, savedInstanceState);
        return view;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void setUI(View view, Bundle savedInstanceState) {

        signOutBtn = view.findViewById(R.id.signOut_button);

    }

    @Override
    protected void setActionListners(Bundle savedInstanceState) {

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FirebaseAuth.getInstance();
                FirebaseAuth.getInstance().signOut();
                ((Activity) context).finish();
                Intent intent = new Intent(context, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });

    }

}