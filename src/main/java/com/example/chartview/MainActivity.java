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
        Log.e("create","create");

        setContentView(R.layout.activity_main);
        chartview = (ChartView) findViewById(R.id.chartview);

        chartview.setValues(90,119,151);
        chartview.setColors(Color.YELLOW,Color.GREEN,Color.BLACK);

  /*      chartview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("haah");
                alertDialog.setMessage("message");
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();*/

                Intent intent = new Intent(MainActivity.this,TwoActivity.class);
                startActivity(intent);

        /*        PopupWindow popupWindow = new PopupWindow(500,500);
                TextView textView = new TextView(MainActivity.this);
                textView.setText("hahahahaha");
                textView.setTextSize(20);
                textView.setGravity(Gravity.CENTER);
                textView.setBackgroundColor(Color.YELLOW);
                popupWindow.setContentView(textView);
                popupWindow.showAtLocation(chartview,10,20,20);*/
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("onStrat","onstart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("resume","resume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("pause","pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("stop","stop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("restart","restart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("destroy","destroy");

    }
}
