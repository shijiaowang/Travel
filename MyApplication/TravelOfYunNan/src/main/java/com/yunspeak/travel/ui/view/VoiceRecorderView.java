package com.yunspeak.travel.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.PowerManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yunspeak.travel.R;
import com.hyphenate.EMError;


/**
 * 录音动画view
 *
 */
public class VoiceRecorderView extends RelativeLayout {
    protected Context context;
    protected LayoutInflater inflater;
    protected Drawable[] micImages;
    protected VoiceRecorder voiceRecorder;

    protected PowerManager.WakeLock wakeLock;
    protected ImageView micImage;
    protected TextView recordingHint;

    protected Handler micImageHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            // change image
            micImage.setImageDrawable(micImages[msg.what]);
        }
    };

    public VoiceRecorderView(Context context) {
        super(context);
        init(context);
    }

    public VoiceRecorderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VoiceRecorderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.widget_voice_recorder, this);

        micImage = (ImageView) findViewById(R.id.mic_image);
        recordingHint = (TextView) findViewById(R.id.recording_hint);

        voiceRecorder = new VoiceRecorder(micImageHandler);

        // animation resources, used for recording
        micImages = new Drawable[] {
                getResources().getDrawable(R.drawable.chat_voice1),
                getResources().getDrawable(R.drawable.chat_voice2),
                getResources().getDrawable(R.drawable.chat_voice3),
                getResources().getDrawable(R.drawable.chat_voice4),
                getResources().getDrawable(R.drawable.chat_voice5),
                getResources().getDrawable(R.drawable.chat_voice6),
                getResources().getDrawable(R.drawable.chat_voice7),
                getResources().getDrawable(R.drawable.chat_voice8),

                getResources().getDrawable(R.drawable.chat_voice9),
                getResources().getDrawable(R.drawable.chat_voice10),
                getResources().getDrawable(R.drawable.chat_voice11),
                getResources().getDrawable(R.drawable.chat_voice12),
                getResources().getDrawable(R.drawable.chat_voice13),
                getResources().getDrawable(R.drawable.chat_voice14),
                getResources().getDrawable(R.drawable.chat_voice15),
                getResources().getDrawable(R.drawable.chat_voice16), };

        wakeLock = ((PowerManager) context.getSystemService(Context.POWER_SERVICE)).newWakeLock(
                PowerManager.SCREEN_DIM_WAKE_LOCK, "yunspeak");
    }

    /**
     * on speak button touched
     * 
     * @param v
     * @param event
     */
    public boolean onPressToSpeakBtnTouch(View v, MotionEvent event, EaseVoiceRecorderCallback recorderCallback) {
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            try {
                //停止正在播放的语音
                //if (EaseChatRowVoicePlayClickListener.isPlaying)
                   // EaseChatRowVoicePlayClickListener.currentPlayListener.stopPlayVoice();
                v.setPressed(true);
                startRecording();
            } catch (Exception e) {
                v.setPressed(false);
            }
            return true;
        case MotionEvent.ACTION_MOVE:
            if (event.getY() < 0) {
                showReleaseToCancelHint();
            } else {
                showMoveUpToCancelHint();
            }
            return true;
        case MotionEvent.ACTION_UP:
            v.setPressed(false);
            if (event.getY() < 0) {
                // discard the recorded audio.
                discardRecording();
            } else {
                // stop recording and send voice file
                try {
                    int length = stopRecoding();
                    if (length > 0) {
                        if (recorderCallback != null) {
                            recorderCallback.onVoiceRecordComplete(getVoiceFilePath(), length);
                        }
                    } else if (length == EMError.FILE_INVALID) {
                        Toast.makeText(context, R.string.Recording_without_permission, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, R.string.The_recording_time_is_too_short, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, R.string.send_failure_please, Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        default:
            discardRecording();
            return false;
        }
    }

    public interface EaseVoiceRecorderCallback {
        /**
         * on voice record complete
         * 
         * @param voiceFilePath
         *            录音完毕后的文件路径
         * @param voiceTimeLength
         *            录音时长
         */
        void onVoiceRecordComplete(String voiceFilePath, int voiceTimeLength);
    }

    public void startRecording() {
        if (!android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            Toast.makeText(context, R.string.Send_voice_need_sdcard_support, Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            wakeLock.acquire();
            this.setVisibility(View.VISIBLE);
            recordingHint.setText(context.getString(R.string.move_up_to_cancel));
            recordingHint.setBackgroundColor(Color.TRANSPARENT);
            voiceRecorder.startRecording(context);
        } catch (Exception e) {
            e.printStackTrace();
            if (wakeLock.isHeld())
                wakeLock.release();
            if (voiceRecorder != null)
                voiceRecorder.discardRecording();
            this.setVisibility(View.INVISIBLE);
            Toast.makeText(context, R.string.recoding_fail, Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void showReleaseToCancelHint() {
        recordingHint.setText(context.getString(R.string.release_to_cancel));
        recordingHint.setBackgroundResource(R.drawable.weiget_recording_hint_bg);
    }

    public void showMoveUpToCancelHint() {
        recordingHint.setText(context.getString(R.string.move_up_to_cancel));
        recordingHint.setBackgroundColor(Color.TRANSPARENT);
    }

    public void discardRecording() {
        if (wakeLock.isHeld())
            wakeLock.release();
        try {
            // stop recording
            if (voiceRecorder.isRecording()) {
                voiceRecorder.discardRecording();
                this.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
        }
    }

    public int stopRecoding() {
        this.setVisibility(View.INVISIBLE);
        if (wakeLock.isHeld())
            wakeLock.release();
        return voiceRecorder.stopRecoding();
    }

    public String getVoiceFilePath() {
        return voiceRecorder.getVoiceFilePath();
    }

    public String getVoiceFileName() {
        return voiceRecorder.getVoiceFileName();
    }

    public boolean isRecording() {
        return voiceRecorder.isRecording();
    }

}
