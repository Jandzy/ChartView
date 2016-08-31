package com.example.chartview;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.example.chartview.view.ChartView;

public class MainActivity extends Activity {

    ChartView chartview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        chartview = (ChartView) findViewById(R.id.chartview);

        chartview.setValues(90 ,120, 150);
        chartview.setColors(Color.YELLOW, Color.GREEN,Color.DKGRAY);

    }

}
