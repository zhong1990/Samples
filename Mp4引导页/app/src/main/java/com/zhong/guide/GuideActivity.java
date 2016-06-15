package com.zhong.guide;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Create by zhongchongfengi@gmail.com
 * <p>
 * 原文链接:http://blog.csdn.net/qq_31530015/article/details/51302784
 */
public class GuideActivity extends Activity implements SurfaceHolder.Callback,
        MediaPlayer.OnPreparedListener {
    public ViewPager       guide_viewpager;
    /**
     * view容器
     */
    public ArrayList<View> viewcontaniers;

    public View view1;
    public View view2;
    public View view3;

    private SurfaceHolder holder1;
    private SurfaceHolder holder2;
    private SurfaceHolder holder3;

    private MediaPlayer player;

    // 3个SurfaceView实例
    private SurfaceView sf1;
    private SurfaceView sf2;
    private SurfaceView sf3;

    // 目录
    private String Path = "1.mp4";

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        // 初始化控件
        initView();

        // 获取SurfaceHolder实例
        holder1 = sf1.getHolder();
        holder2 = sf2.getHolder();
        holder3 = sf3.getHolder();

        // 实现接口
        holder1.addCallback(this);
        holder2.addCallback(this);
        holder3.addCallback(this);

        guide_viewpager.setAdapter(new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return viewcontaniers.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewcontaniers.get(position));

                return viewcontaniers.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {

                container.removeView((View) object);
            }
        });

    }

    private void initView() {
        guide_viewpager = (ViewPager) findViewById(R.id.guide_viewpager);
        viewcontaniers = new ArrayList<View>();
        view1 = View.inflate(this, R.layout.guide_view1, null);
        view2 = View.inflate(this, R.layout.guide_view2, null);
        view3 = View.inflate(this, R.layout.guide_view3, null);

        sf1 = (SurfaceView) view1.findViewById(R.id.sf1);
        sf2 = (SurfaceView) view2.findViewById(R.id.sf2);
        sf3 = (SurfaceView) view3.findViewById(R.id.sf3);

        viewcontaniers.add(view1);
        viewcontaniers.add(view2);
        viewcontaniers.add(view3);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        // 下面开始实例化MediaPlayer对象
        player = new MediaPlayer();
        // 设置播放在surfaceview上
        player.setDisplay(holder);
        player.setOnPreparedListener(this);
        // 设置循环播放
        player.setLooping(true);

        if (holder1.equals(holder)) {
            Path = "1.mp4";
        }

        if (holder2.equals(holder)) {
            Path = "2.mp4";
        }
        if (holder3.equals(holder)) {
            Path = "3.mp4";
        }


        try {
            AssetFileDescriptor afd = getAssets().openFd(Path);
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.prepareAsync(); // 准备播放
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        // 开始播放
        mp.start();
    }

}

