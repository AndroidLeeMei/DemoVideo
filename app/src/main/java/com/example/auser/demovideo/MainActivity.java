package com.example.auser.demovideo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    VideoView videoView;
    MediaController mc;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView=(VideoView)findViewById(R.id.videoView);
        if (mc==null){
            mc=new MediaController(this);
            videoView.setMediaController(mc);
        }
        requesPermission();
        videoView.setOnPreparedListener(onPreparedListener);
//        下面程式移到下面
//        Uri uri=Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Movies/sawing_a_baby_in_half.mp4");//取得手機根路徑
//        videoView.setVideoURI(uri);
//        videoView.setOnPreparedListener(onPreparedListener);
    }

    MediaPlayer.OnPreparedListener onPreparedListener= new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            //先檢查撥放位置,若是進度是0時,則立刻自動撥放影片,用vid
            if (position==0) videoView.start();
            mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                @Override
                public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                    mc.setAnchorView(videoView);
                }
            });
        }
    };

    //如果手機是6.0以上的話.要加入權限放在一個自的方法裏
    void requesPermission(){
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)){

            }else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);  //自己隨便給一個權限10
            }
            }else{
                Uri uri=Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Movies/sawing_a_baby_in_half.mp4");//取得手機根路徑
                videoView.setVideoURI(uri);

            }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 10://配合之前寫的權限等級10
                if (grantResults.length>0
                        && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                }else {

                }
                return;
        }
    }
}
