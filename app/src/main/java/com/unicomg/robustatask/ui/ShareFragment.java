package com.unicomg.robustatask.ui;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.unicomg.robustatask.R;
import com.unicomg.robustatask.base.BaseFragment;
import com.unicomg.robustatask.store.local.Image;
import com.unicomg.robustatask.uitils.BottomSheetBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static androidx.navigation.Navigation.findNavController;
import static com.unicomg.robustatask.uitils.Utilities.formatTemperature;
import static com.unicomg.robustatask.uitils.Utilities.getArtResourceForWeatherCondition;
import static com.unicomg.robustatask.uitils.Utilities.getDayName;


public class ShareFragment extends BaseFragment {

    private static final int PERMISSION_REQUEST_CODE = 200;

    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.temptxt)
    TextView temp;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.forecast)
    TextView forecast;
    @BindView(R.id.image_value)
    ImageView imageValue;
    @BindView(R.id.img_icon)
    ImageView imageIcon;
    @BindView(R.id.chose_file)
    TextView choseFile;
    @BindView(R.id.place_name)
    TextInputEditText placeName;
    @BindView(R.id.list_item_icon)
    ImageView weatherImage;

    private Bitmap imageForSharing;
    private int REQUEST_CAMERA = 0;
    private Uri imageCameraUri;
    private String imageUriToSave;

    public ShareFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel.getCurrentWeather("cairo", "439d4b804bc8187953eb36d2a8c26a02");
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_share;
    }

    @Override
    protected void initFragment() {

        mainViewModel.weatherResponseMutableLiveData.observe(getViewLifecycleOwner(), weatherData -> {
            city.setText(weatherData.getName());
            date.setText(getDayName(weatherData.getDt()));
            forecast.setText(weatherData.getWeather().get(0).getMain());
            temp.setText(formatTemperature(getContext(), weatherData.getMain().getTemp()));
            weatherImage.setImageResource(getArtResourceForWeatherCondition(weatherData.getWeather().get(0).getId()));
        });
        if (imageForSharing != null) {
            imageValue.setImageBitmap(imageForSharing);
            choseFile.setVisibility(View.GONE);
            imageIcon.setVisibility(View.GONE);
        }
    }


    public void init_attach_images() {
        new BottomSheetBuilder(getContext(), R.layout.gallary_camera_intent)
                .clickListener(R.id.camera_btn, (dialog, view) -> {
                    cameraIntent();
                    dialog.dismiss();
                })
                .clickListener(R.id.gallery_intent, (dialog, view) -> {
                    Intent intent = new Intent(getContext(), FilePickerActivity.class);
                    intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                            .setCheckPermission(true)
                            .setShowImages(true)
                            .setShowFiles(true)
                            .setShowVideos(false)
                            .setMaxSelection(1)
                            .build());
                    startActivityForResult(intent, 200);
                    dialog.dismiss();
                })
                .cancelable(true)
                .transparentBackground(true)
                .background(R.color.ColorBlackTransparent)
                .build()
                .show();

    }

    public void cameraIntent() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageCameraUri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageCameraUri);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton(getResources().getString(R.string.done), okListener)
                .setNegativeButton(getResources().getString(R.string.cancel), null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean readStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && readStorageAccepted && writeStorageAccepted)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel(getResources().getString(R.string.permissions),
                                        (dialog, which) ->
                                                requestPermissions(new String[]{CAMERA, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE},
                                                        PERMISSION_REQUEST_CODE));
                                return;
                            }
                        }

                }

                break;
        }
    }

    private boolean checkPermission() {
        int result1 = ContextCompat.checkSelfPermission(getContext().getApplicationContext(), CAMERA);
        int result2 = ContextCompat.checkSelfPermission(getContext().getApplicationContext(), READ_EXTERNAL_STORAGE);
        int result3 = ContextCompat.checkSelfPermission(getContext().getApplicationContext(), WRITE_EXTERNAL_STORAGE);

        return result1 == PackageManager.PERMISSION_GRANTED &&
                result2 == PackageManager.PERMISSION_GRANTED &&
                result3 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new
                String[]{CAMERA, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @OnClick(R.id.add_image)
    void add_image() {
        if (!checkPermission()) {
            requestPermission();
        } else {
            init_attach_images();
        }
    }

    @OnClick(R.id.share_btn)
    void share() {

        if (imageForSharing != null) {
            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(imageForSharing)
                    .build();

            SharePhotoContent content = new SharePhotoContent.Builder()
                    .addPhoto(photo)
                    .build();

            ShareDialog shareDialog = new ShareDialog(getActivity());
            shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
        } else {
            mainViewModel.errorMsgLiveData.setValue("Please chose an image ");
        }


    }


    @OnClick(R.id.save_btn)
    void save() {
        if (imageUriToSave != null && !placeName.getText().toString().isEmpty())
            mainViewModel.insertImage(new Image(imageUriToSave, placeName.getText().toString()));
        else {
            mainViewModel.errorMsgLiveData.setValue("Please chose an image and type place ");
        }
    }

    @OnClick(R.id.history_btn)
    void history() {
        findNavController(requireActivity(), R.id.nav_host_fragment)
                .navigate(R.id.action_shareFragment_to_imagesListFragment);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            choseFile.setVisibility(View.GONE);
            imageIcon.setVisibility(View.GONE);
            Uri picUri = null;
            if (requestCode == REQUEST_CAMERA) {
                imageUriToSave = imageCameraUri.toString();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageCameraUri);
                    imageForSharing = bitmap;
                    imageValue.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageValue.setImageBitmap(bitmap);

            } else if (requestCode == 200) {
                ArrayList<MediaFile> files = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);
                if (files != null)
                    if (files.size() != 0) {
                        picUri = Uri.fromFile(new File(files.get(0).getPath()));
                        if (files.get(0).getMediaType() == MediaFile.TYPE_IMAGE) {
                            Bitmap bitmap = null;
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), picUri);
                                imageValue.setImageBitmap(bitmap);
                                imageForSharing = bitmap;
                                imageUriToSave = picUri.toString();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            imageValue.setImageBitmap(bitmap);
                        }
                    }

            }
        }

    }


}


