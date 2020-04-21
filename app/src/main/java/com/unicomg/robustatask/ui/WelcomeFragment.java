package com.unicomg.robustatask.ui;

import com.unicomg.robustatask.R;
import com.unicomg.robustatask.base.BaseFragment;

import butterknife.OnClick;

import static androidx.navigation.Navigation.findNavController;


public class WelcomeFragment extends BaseFragment {

    public WelcomeFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_welcome;
    }

    @Override
    protected void initFragment() {

    }

    @OnClick(R.id.start_btn)
    void start() {
        findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.action_splashFragment_to_shareFragment);

    }
}
