package com.filip.androidgames.framework;

/**
 * Created by RE1010 on 2017-10-04.
 */
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;

import java.io.File;


public interface DrawText {

//   public void onCreate(Bundle savedInstanceState);
    public void createFromAsset(AssetManager mgr, String path);
  //  public void paintTypeface();
  //  public void canvasDrawText();

    public void dispose();
}
