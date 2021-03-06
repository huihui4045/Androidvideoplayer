package com.android.tedcoder.androidvideoplayer;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.android.tedcoder.wkvideoplayer.model.Video;
import com.android.tedcoder.wkvideoplayer.model.VideoUrl;
import com.android.tedcoder.wkvideoplayer.util.DensityUtil;
import com.android.tedcoder.wkvideoplayer.view.MediaController;
import com.android.tedcoder.wkvideoplayer.view.SuperVideoPlayer;

import java.util.ArrayList;


public class MainActivity extends Activity implements View.OnClickListener {
    private String TEST_URL = "http://7xkbzx.com1.z0.glb.clouddn.com/SampleVideo_1080x720_10mb.mp4";
    private SuperVideoPlayer mSuperVideoPlayer;
    private View mPlayBtnView;

    private SuperVideoPlayer.VideoPlayCallbackImpl mVideoPlayCallback = new SuperVideoPlayer.VideoPlayCallbackImpl() {
        @Override
        public void onCloseVideo() {
            mSuperVideoPlayer.stopPlay();
            mPlayBtnView.setVisibility(View.VISIBLE);
            mSuperVideoPlayer.setVisibility(View.GONE);
            resetPageToPortrait();
        }

        @Override
        public void onSwitchPageType() {
            if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
            }else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                mSuperVideoPlayer.setPageType(MediaController.PageType.EXPAND);
            }
        }

        @Override
        public void onPlayFinish() {

        }
    };

    @Override
    public void onClick(View view) {
        mPlayBtnView.setVisibility(View.GONE);
        mSuperVideoPlayer.setVisibility(View.VISIBLE);
        Video video = new Video();
        VideoUrl videoUrl1 = new VideoUrl();
        videoUrl1.setFormatName("720P");
        videoUrl1.setFormatUrl("http://7xkbzx.com1.z0.glb.clouddn.com/SampleVideo_1080x720_20mb.mp4");
        VideoUrl videoUrl2 = new VideoUrl();
        videoUrl2.setFormatName("480P");
        videoUrl2.setFormatUrl("http://7xkbzx.com1.z0.glb.clouddn.com/SampleVideo_720x480_20mb.mp4");
        ArrayList<VideoUrl> arrayList1 = new ArrayList<VideoUrl>();
        arrayList1.add(videoUrl1);
        arrayList1.add(videoUrl2);
        video.setVideoName("房源视频");
        video.setVideoUrl(arrayList1);

        Video video2 = new Video();
        VideoUrl videoUrl3 = new VideoUrl();
        videoUrl3.setFormatName("720P");
        videoUrl3.setFormatUrl("http://7xkbzx.com1.z0.glb.clouddn.com/SampleVideo_1080x720_10mb.mp4");
        VideoUrl videoUrl4 = new VideoUrl();
        videoUrl4.setFormatName("480P");
        videoUrl4.setFormatUrl("http://7xkbzx.com1.z0.glb.clouddn.com/SampleVideo_720x480_10mb.mp4");
        ArrayList<VideoUrl> arrayList2 = new ArrayList<VideoUrl>();
        arrayList2.add(videoUrl3);
        arrayList2.add(videoUrl4);
        video2.setVideoName("小区视频");
        video2.setVideoUrl(arrayList2);

        ArrayList<Video> videoArrayList = new ArrayList<Video>();
        videoArrayList.add(video);
        videoArrayList.add(video2);

        mSuperVideoPlayer.loadMultipleVideo(videoArrayList);
    }

    /***
     * 旋转屏幕之后回调
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(null == mSuperVideoPlayer)return;
        /***
         * 根据屏幕方向重新设置播放器的大小
         */
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().getDecorView().invalidate();
            float height = DensityUtil.getWidthInPx(this);
            float width = DensityUtil.getHeightInPx(this);
            mSuperVideoPlayer.getLayoutParams().height = (int)width;
            mSuperVideoPlayer.getLayoutParams().width = (int)height;
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            final WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attrs);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            float width = DensityUtil.getWidthInPx(this);
            float height = DensityUtil.dip2px(this, 230.f);
            mSuperVideoPlayer.getLayoutParams().height = (int)height;
            mSuperVideoPlayer.getLayoutParams().width = (int)width;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSuperVideoPlayer = (SuperVideoPlayer)findViewById(R.id.video_player_item_1);
        mPlayBtnView = findViewById(R.id.play_btn);
        mPlayBtnView.setOnClickListener(this);
        mSuperVideoPlayer.setVideoPlayCallback(mVideoPlayCallback);
    }

    /***
     * 恢复屏幕至竖屏
     */
    private void resetPageToPortrait(){
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
        }
    }
}
