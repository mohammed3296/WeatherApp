package com.unicomg.robustatask.base;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.unicomg.robustatask.R;
import com.unicomg.robustatask.di.activity.ActivityComponent;

import javax.inject.Inject;

import butterknife.ButterKnife;

public abstract class BaseActivity<V extends BaseViewModel> extends AppCompatActivity {

    public V viewModel;
    public ActivityComponent component;
    @Inject
    ViewModelFactory viewModelFactory;
    Dialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getLayout() != 0) {
            setContentView(getLayout());
            ButterKnife.bind(this);
        }
        initActivityComponent();
        if (component != null) {
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass());
        }
        progressDialog = new Dialog(this);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setContentView(R.layout.custom_dialog_upload);
        initActivity();
        initObservers();

    }

    protected abstract void initActivityComponent();

    protected abstract int getLayout();

    protected abstract Class<V> getViewModelClass();

    protected abstract void initActivity();

    public void initObservers() {

        if (viewModel != null) {
            viewModel.errorMsgLiveData.observe(this, msg -> {
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            });

            viewModel.successMsgLiveData.observe(this, msg -> {
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            });

            viewModel.warningMsgLiveData.observe(this, msg -> {
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            });

            viewModel.loadingLiveData.observe(this, loading -> {
                if (loading) {
                    progressDialog.show();
                } else {
                    progressDialog.dismiss();
                }
            });

        }
    }

}
