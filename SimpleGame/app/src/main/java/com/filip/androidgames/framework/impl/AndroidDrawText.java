package com.filip.androidgames.framework.impl;

/**
 * Created by RE1010 on 2017-10-03.
 */

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.icu.text.DisplayContext;
import android.os.Bundle;
import android.view.TextureView;
import android.widget.TextView;

import com.filip.androidgames.framework.DrawText;

public class AndroidDrawText implements DrawText {

    int fontId;
    TextView _textView;

    public void AndroidDrawText(int fontId) {
    }

  //  @Override
    public void create(AssetManager mgr, String _path) {
        Typeface fromAsset = Typeface.createFromAsset(mgr, _path);
        _textView.setTypeface(fromAsset);
    }

    @Override
    public void createFromAsset(AssetManager mgr, String path) {
    }



    @Override
    public void dispose() {
    }
}
