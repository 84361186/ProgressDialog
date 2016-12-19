package com.xq.progressdialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity {
    private PopupWindow loadingWindow;
    private Button dialog;
    private ImageView mPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = (Button) findViewById(R.id.dialog);
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });
    }


    private void showPopupWindow() {
        View contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item, null);
        loadingWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        loadingWindow.setContentView(contentView);
        View rootview = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main, null);
        loadingWindow.showAtLocation(rootview, Gravity.CENTER,0,0);
        loadingWindow.setOutsideTouchable(false);

        mPoint = (ImageView) contentView.findViewById(R.id.point);
        RotateAnimation ra = new RotateAnimation(0,360, Animation.RELATIVE_TO_PARENT,0.37f,Animation.RELATIVE_TO_PARENT,0.37f);
        ra.setDuration(2000);
        ra.setRepeatCount(Animation.INFINITE);
        ra.setRepeatMode(Animation.RESTART);
        mPoint.startAnimation(ra);
        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mPoint.clearAnimation();
                        loadingWindow.dismiss();
                    }
                });
            }
        }.start();
    }
}
