package com.saku.plantz.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public abstract class FragmentBase extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected abstract int getLayoutResource();

    protected abstract void setUI(View view, Bundle savedInstanceState);

    protected abstract void setActionListners(Bundle savedInstanceState);

    public boolean onBackPressed() {
        return false;
    }
}
