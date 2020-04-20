package com.unicomg.robustatask.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.unicomg.robustatask.R;
import com.unicomg.robustatask.base.BaseFragment;

import static androidx.navigation.Navigation.findNavController;


public class SplashFragment extends BaseFragment {

    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_splash;
    }

    @Override
    protected void initFragment() {

    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(() -> {
            findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.action_splashFragment_to_shareFragment);
        }, 1000);

    }
}
