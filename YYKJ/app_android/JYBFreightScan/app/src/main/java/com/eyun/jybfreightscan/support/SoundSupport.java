package com.eyun.jybfreightscan.support;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Vibrator;

import com.eyun.framework.angular.core.Scope;
import com.eyun.jybfreightscan.R;

import java.io.IOException;

/**
 * Created by Administrator on 2017/3/7.
 */

public class SoundSupport {
    /****************提示声音 震动  发送成功之后显示**********/
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    public SoundSupport(){
        initBeepSound();
    }
    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
        // The volume on STREAM_SYSTEM is not adjustable, and users found it
        // too loud,
        // so we now play on the music stream.
            Scope.activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);


            AssetFileDescriptor file = Scope.activity.getResources().openRawResourceFd(R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    private static final long VIBRATE_DURATION = 200L;


    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) Scope.activity.getSystemService(Scope.activity.VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }



    private void initVoice() {
// TODO Auto-generated method stub
        playBeep = true;
        AudioManager audioService = (AudioManager) Scope.activity.getSystemService(Scope.activity.AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

/*************************************************/

    /***************播放声音的另外一种方法*****************/
    private SoundPool soundPool;
    private int music;
    /**哪里要调用就执行这行代码**/
    public void play_voice() {
        soundPool.play(music, 1, 1, 0, 0, 1);
    }
    /**播放声音初始化*/
    public void initVoice2(){
        soundPool= new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);//第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        music = soundPool.load(Scope.activity, R.raw.beep, 1); //把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级
    }

    public static int miles=1000;//延迟
    public static void play(int raw){
        SoundPool soundPool= new SoundPool(10,AudioManager.STREAM_SYSTEM,5);
        final int music= soundPool.load(Scope.activity,raw,1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(music,1, 1, 0, 0, 1);
            }
        });

    }
}
