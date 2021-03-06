package com.sh3rawi.RNAudioPlayer;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.Callback;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.AudioManager;

public class RNAudioPlayerModule extends ReactContextBaseJavaModule {
  ReactApplicationContext reactContext;
  MediaPlayer mp;

  public RNAudioPlayerModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNAudioPlayer";
  }

  @ReactMethod
  public void play(String audio) {
      AudioManager am = (AudioManager) this.reactContext.getSystemService(Context.AUDIO_SERVICE);
      if (am.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {

          String fname = audio.toLowerCase();
          int resID = this.reactContext.getResources().getIdentifier(fname, "raw", this.reactContext.getPackageName());
          if (resID == 0) { return; }
          mp = MediaPlayer.create(this.reactContext, resID);
          mp.start();
          mp.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
              mp.reset();
              mp.release();
              mp = null;
            }
          });
      }
  }
}
