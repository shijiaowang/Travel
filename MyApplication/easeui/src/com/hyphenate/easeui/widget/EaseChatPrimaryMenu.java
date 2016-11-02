package com.hyphenate.easeui.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hyphenate.easeui.R;

/**
 * primary menu
 *
 */
public class EaseChatPrimaryMenu extends EaseChatPrimaryMenuBase implements OnClickListener {
    private EditText editText;
    private View buttonSetModeKeyboard;
    private View buttonSetModeVoice;
    private View buttonSend;
    private View buttonPressToSpeak;
    private ImageView faceChecked;
    private Button buttonMore;
    private boolean isDontShowOther;
    private InputMethodManager imm;
    private boolean isFace;

    public EaseChatPrimaryMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public EaseChatPrimaryMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EaseChatPrimaryMenu(Context context) {
        super(context);
        init(context, null);
    }

    private void init(final Context context, AttributeSet attrs) {

        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.ease_widget_chat_primary_menu, this);
        editText = (EditText) findViewById(R.id.et_sendmessage);
        buttonSetModeKeyboard = findViewById(R.id.btn_set_mode_keyboard);
        buttonSetModeVoice = findViewById(R.id.btn_set_mode_voice);
        buttonSend = findViewById(R.id.btn_send);
        buttonPressToSpeak = findViewById(R.id.btn_press_to_speak);
        faceChecked = (ImageView) findViewById(R.id.iv_face_checked);
        buttonMore = (Button) findViewById(R.id.btn_more);
        faceChecked.setOnClickListener(this);
        buttonSend.setOnClickListener(this);
        buttonSetModeKeyboard.setOnClickListener(this);
        buttonSetModeVoice.setOnClickListener(this);
        buttonMore.setOnClickListener(this);
        editText.setOnClickListener(this);
        editText.requestFocus();
        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (listener!=null){
                        faceChecked.setImageResource(R.drawable.chat_emoj);
                        isFace=!isFace;
                        listener.onEditTextClicked();
                    }
                }
            }
        });
        // listen the text change
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    buttonMore.setVisibility(View.GONE);
                    buttonSend.setVisibility(View.VISIBLE);
                } else if (!isDontShowOther){
                    buttonMore.setVisibility(View.VISIBLE);
                    buttonSend.setVisibility(View.GONE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        
        
        buttonPressToSpeak.setOnTouchListener(new OnTouchListener() {
            
            @Override 
            public boolean onTouch(View v, MotionEvent event) {
                if(listener != null){
                    return listener.onPressToSpeakBtnTouch(v, event);
                }
                return false;
            }
        });
    }
    //发现页使用，去掉语音和更多
    @Override
    public void setFindUse(){
        isDontShowOther = true;
        buttonMore.setVisibility(GONE);
        buttonSend.setVisibility(VISIBLE);
        buttonPressToSpeak.setVisibility(GONE);
        buttonSetModeKeyboard.setVisibility(GONE);
        buttonSetModeVoice.setVisibility(GONE);
    }
    /**
     * set recorder view when speak icon is touched
     * @param voiceRecorderView
     */
    public void setPressToSpeakRecorderView(EaseVoiceRecorderView voiceRecorderView){
        EaseVoiceRecorderView voiceRecorderView1 = voiceRecorderView;
    }
    @Override
   protected void showSoftWore(){
       imm.showSoftInput(editText,InputMethodManager.SHOW_FORCED);
   }

    @Override
    public void hideKeyboard() {
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0); //强制隐藏键盘
    }

    /**
     * append emoji icon to editText
     * @param emojiContent
     */
    public void onEmojiconInputEvent(CharSequence emojiContent){
        editText.append(emojiContent);
    }
    @Override
    public void setHint(String hint){
        editText.setText("");
        editText.setHint(hint);
        setModeKeyboard();
        showSoftWore();
    }
    /**
     * delete emojicon
     */
    public void onEmojiconDeleteEvent(){
        if (!TextUtils.isEmpty(editText.getText())) {
            KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
            editText.dispatchKeyEvent(event);
        }
    }
    
    /**
     * on clicked event
     * @param view
     */
    @Override
    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.btn_send) {
            if(listener != null){
                String s = editText.getText().toString();
                editText.setText("");
                listener.onSendBtnClicked(s);
            }
        } else if (id == R.id.btn_set_mode_voice) {
            setModeVoice();
            showNormalFaceImage();
            if(listener != null)
                listener.onToggleVoiceBtnClicked();
        } else if (id == R.id.btn_set_mode_keyboard) {
            setModeKeyboard();
            showNormalFaceImage();
            if(listener != null)
                listener.onToggleVoiceBtnClicked();
        } else if (id == R.id.btn_more) {
            buttonSetModeVoice.setVisibility(View.VISIBLE);
            buttonSetModeKeyboard.setVisibility(View.GONE);
            editText.setVisibility(View.VISIBLE);
            buttonPressToSpeak.setVisibility(View.GONE);
            showNormalFaceImage();
            if(listener != null)
                listener.onToggleExtendClicked();
        } else if (id == R.id.et_sendmessage) {
            if(listener != null) {
                listener.hideEmoji();
                listener.onEditTextClicked();
            }
        } else if (id == R.id.iv_face_checked) {
            faceChecked.setImageResource(isFace?R.drawable.chat_emoj:R.drawable.chat_soft_wore);
            isFace = !isFace;
            if(listener != null){
                listener.onToggleEmojiconClicked();
            }
        } else {
        }
    }
    
    
    /**
     * show voice icon when speak bar is touched
     * 
     */
    protected void setModeVoice() {
        hideKeyboard();
        editText.setVisibility(View.GONE);
        buttonSetModeVoice.setVisibility(View.GONE);
        buttonSetModeKeyboard.setVisibility(View.VISIBLE);
        buttonSend.setVisibility(View.GONE);
        buttonMore.setVisibility(View.VISIBLE);
        faceChecked.setVisibility(GONE);
        buttonPressToSpeak.setVisibility(View.VISIBLE);
    }

    /**
     * show keyboard
     */
    protected void setModeKeyboard() {
        editText.setVisibility(View.VISIBLE);
        buttonSetModeKeyboard.setVisibility(View.GONE);
        buttonSetModeVoice.setVisibility(View.VISIBLE);
        // mEditTextContent.setVisibility(View.VISIBLE);
        faceChecked.setVisibility(VISIBLE);
        editText.requestFocus();
        // buttonSend.setVisibility(View.VISIBLE);
        buttonPressToSpeak.setVisibility(View.GONE);
        if (TextUtils.isEmpty(editText.getText())) {
            buttonMore.setVisibility(View.VISIBLE);
            buttonSend.setVisibility(View.GONE);
        } else {
            buttonMore.setVisibility(View.GONE);
            buttonSend.setVisibility(View.VISIBLE);
        }

    }
    
    
    protected void toggleFaceImage(){

    }
    
    private void showNormalFaceImage(){

    }
    
    private void showSelectedFaceImage(){

    }
    

    @Override
    public void onExtendMenuContainerHide() {
        showNormalFaceImage();
    }

    @Override
    public void onTextInsert(CharSequence text) {
       int start = editText.getSelectionStart();
       Editable editable = editText.getEditableText();
       editable.insert(start, text);
       setModeKeyboard();
    }

    @Override
    public EditText getEditText() {
        return editText;
    }

}
