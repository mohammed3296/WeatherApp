package com.unicomg.robustatask.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.unicomg.robustatask.R;
import com.unicomg.robustatask.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;


public class ImagesListFragment extends BaseFragment {


    @BindView(R.id.images_list)
    RecyclerView recyclerView;
    @BindView(R.id.full_image)
    RelativeLayout relativeLayout;
    @BindView(R.id.min)
    ImageView min;
    @BindView(R.id.image)
    ImageView image;

    public ImagesListFragment() {
    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_images_list;
    }

    @Override
    protected void initFragment() {
        mainViewModel.getAllImages();
        mainViewModel.listMutableLiveData.observe(getViewLifecycleOwner(), images -> {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            recyclerView.setAdapter(new ImagesAdapter(images, (model, index) -> {
                relativeLayout.setVisibility(View.VISIBLE);
                Picasso.get().load(model.getUrl()).into(image);

            }, getContext()));
        });
    }


    @OnClick(R.id.min)
    void min() {
        relativeLayout.setVisibility(View.GONE);
    }
}
