package com.company.photogallery.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.company.photogallery.fragment.PhotoGalleryFragment;

public class PhotoGalleryActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, PhotoGalleryActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return PhotoGalleryFragment.newInstance();
    }
}
