package com.example.chartview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by jrazy on 2016/8/25.
 */
public class TwoActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("1create...","cretae");
        setContentView(R.layout.two);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("onStrat11111111","onstart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("resume111111","resume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("pause11111","pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("stop1111","stop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("restart1111","restart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("destroy111","destroy");

    }
}
