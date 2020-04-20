package com.unicomg.robustatask.uitils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Slide;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.unicomg.robustatask.R;


public class BottomSheetBuilder {


    private final View view;

    private BottomSheetDialog dialog;


    public BottomSheetBuilder(Context context, @LayoutRes int layoutRes) {

        view = LayoutInflater.from(context).inflate(layoutRes, null);

        dialog = new BottomSheetDialog(context);

        dialog.setCancelable(false);

        Window window = dialog.getWindow();

        if (window != null) {

            WindowManager.LayoutParams attributes = window.getAttributes();

            attributes.gravity = Gravity.CENTER;

            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;

            attributes.height = WindowManager.LayoutParams.MATCH_PARENT;

            window.setAttributes(attributes);


            window.getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);


        }

        dialog.setContentView(view);

    }


    public BottomSheetBuilder clickListener(@IdRes int viewId, @NonNull OnClickListener onClickListener) {

        view.findViewById(viewId).setOnClickListener(v -> onClickListener.onClick(dialog, v));

        return this;

    }


    public BottomSheetBuilder transparentBackground(boolean transparent) {

        if (transparent) {

            dialog.setOnShowListener(dialog -> {

                BottomSheetDialog d = (BottomSheetDialog) dialog;

                FrameLayout bottomSheet = d.findViewById(R.id.design_bottom_sheet);

                if (bottomSheet == null) {

                    return;

                }

                bottomSheet.setBackground(null);

            });

        }

        return this;

    }


    public BottomSheetBuilder background(@DrawableRes int drawableRes) {

        Window window = dialog.getWindow();

        if (window != null) {

            window.getAttributes();

            window.setBackgroundDrawableResource(drawableRes);

        }

        return this;

    }


    public BottomSheetBuilder text(@IdRes int viewId, String text) {

        View view = this.view.findViewById(viewId);

        view.setVisibility(View.VISIBLE);

        ((TextView) view).setText(text);

        return this;

    }


    public BottomSheetBuilder recyclerViewLayoutManger(@IdRes int viewId, RecyclerView.LayoutManager layoutManager) {

        View view = this.view.findViewById(viewId);

        ((RecyclerView) view).setLayoutManager(layoutManager);

        return this;

    }

    public BottomSheetBuilder recyclerViewAdapter(@IdRes int viewId, RecyclerView.Adapter adapter) {

        View view = this.view.findViewById(viewId);

        ((RecyclerView) view).setAdapter(adapter);

        return this;

    }

    public BottomSheetBuilder setExpanded(boolean isExpanded) {

        if (isExpanded) {

            dialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);

        }

        return this;

    }


    public BottomSheetBuilder setHideable(boolean isHideable) {

        if (!isHideable) {

            dialog.getBehavior().setHideable(false);

            dialog.getBehavior().setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {


                @Override

                public void onStateChanged(@NonNull View bottomSheet, int newState) {

                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {

                        dialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);

                    }


                }


                @Override

                public void onSlide(@NonNull View bottomSheet, float slideOffset) {


                }

            });

        }

        return this;

    }


    public BottomSheetBuilder drawableEnd(@IdRes int viewId, Drawable drawable) {

        View view = this.view.findViewById(viewId);

        view.setVisibility(View.VISIBLE);

        ((TextView) view).setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null);

        return this;

    }


    public BottomSheetBuilder image(@IdRes int viewId, String imageUrl) {

        View view = this.view.findViewById(viewId);

        view.setVisibility(View.VISIBLE);

        return this;

    }


    public BottomSheetBuilder textColor(@IdRes int viewId, int colorRes) {

        View view = this.view.findViewById(viewId);

        ((TextView) view).setTextColor(colorRes);

        return this;

    }


    public BottomSheetBuilder setActivated(@IdRes int viewId, Boolean isFavorite) {

        View view = this.view.findViewById(viewId);

        view.setVisibility(View.VISIBLE);

        view.setActivated(isFavorite);

        return this;

    }


    public BottomSheetBuilder setVisbility(@IdRes int viewId, Boolean isVisible) {

        View view = this.view.findViewById(viewId);

        if (isVisible)

            view.setVisibility(View.GONE);

        else

            view.setVisibility(View.VISIBLE);

        return this;

    }


    public BottomSheetBuilder cancelable(boolean cancelable) {

        dialog.setCancelable(cancelable);

        return this;

    }


    public Dialog build() {

        return dialog;

    }


    public BottomSheetBuilder visibility(@IdRes int viewId, @IdRes int viewIdTwo, boolean isDamaged) {

        if (isDamaged) {

            View view = this.view.findViewById(viewId);

            view.setVisibility(View.VISIBLE);

            View view2 = this.view.findViewById(viewIdTwo);

            view2.setVisibility(View.INVISIBLE);

        }

        return this;

    }


    public BottomSheetBuilder gravity(@Slide.GravityFlag int gravity) {

        Window window = dialog.getWindow();

        if (window != null) {

            WindowManager.LayoutParams attributes = window.getAttributes();

            attributes.gravity = gravity;

            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;

            attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;

            window.setAttributes(attributes);

        }

        return this;

    }


    public interface OnClickListener {


        void onClick(BottomSheetDialog dialog, View view);

    }


}