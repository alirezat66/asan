package ir.greencode.advicelawAndroid.calling;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sinch.android.rtc.MissingPermissionException;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallEndCause;
import com.sinch.android.rtc.calling.CallListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import ir.greencode.advicelawAndroid.R;
import ir.greencode.advicelawAndroid.services.SinchService;
import ir.greencode.advicelawAndroid.utils.BaseActivity;
import ir.greencode.advicelawAndroid.utils.BaseActivityWithOutFont;
import ir.greencode.advicelawAndroid.utils.SwipeButton;
import ir.greencode.advicelawAndroid.utils.Utility;

public class IncomingCallScreenActivity extends BaseActivityWithOutFont {

    static final String TAG = IncomingCallScreenActivity.class.getSimpleName();
    @BindView(R.id.callState)
    TextView callState;
    @BindView(R.id.img_remote)
    CircleImageView imgRemote;
    @BindView(R.id.txt_user_name)
    TextView txtUserName;
    @BindView(R.id.btn_cancel)
    ImageButton btnCancel;
    @BindView(R.id.swipe_btn)
    SwipeButton swipeBtn;
    @BindView(R.id.txt_timer)
    TextView txtTimer;
    private String mCallId;
    private AudioPlayer mAudioPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incoming);
        ButterKnife.bind(this);



        /*Button answer = (Button) findViewById(R.id.answerButton);
        answer.setOnClickListener(mClickListener);
        Button decline = (Button) findViewById(R.id.declineButton);
        decline.setOnClickListener(mClickListener);
*/
        mAudioPlayer = new AudioPlayer(this);
        mAudioPlayer.playRingtone();
        mCallId = getIntent().getStringExtra(SinchService.CALL_ID);

        txtTimer.setTypeface(Utility.getRegularTypeFace(this));
        callState.setTypeface(Utility.getRegularTypeFace(this));
        txtUserName.setTypeface(Utility.getRegularTypeFace(this));

        swipeBtn.onChangeListener(new SwipeButton.ChangeState() {
            @Override
            public void onCallActive() {
                answerClicked();

                startTimer();

            }

            @Override
            public void onCallCancel() {
                declineClicked();
                finish();
            }
        });
    }

    private void startTimer() {

        final Handler handler = new Handler();
        final int delay = 1000; //milliseconds
        final int[] second = {0};

        handler.postDelayed(new Runnable(){
            public void run(){
                //do something
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String hours;
                        String min;
                        String seconds;
                        String divider=" : ";
                        second[0] +=1;
                        int sec = second[0];
                        int inthour = sec /3600;
                        sec = sec%3600;
                        int intmin = sec /60;
                        sec = sec%60;
                        int intSec = second[0];
                        sec = sec%60;

                        hours = (inthour>9 ? inthour+"" : "0"+inthour);
                        min = (intmin>9 ? intmin+"" : "0"+intmin);
                        seconds = (sec>9 ? sec+"" : "0"+sec);
                        txtTimer.setText(hours+divider+min+divider+seconds);

                    }
                });
                handler.postDelayed(this, delay);
            }
        }, delay);

    }
  /*  @Override
    protected void attachBaseContext(Context newBase) {
   //     Toast.makeText(newBase, "this is override", Toast.LENGTH_SHORT).show();
    }*/

    @Override
    protected void onServiceConnected() {
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            call.addCallListener(new SinchCallListener());
            txtUserName.setText(call.getRemoteUserId());
        } else {
            Log.e(TAG, "Started with invalid callId, aborting");
            finish();
        }
    }

    private void answerClicked() {
        mAudioPlayer.stopRingtone();
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            try {
                call.answer();
                Intent intent = new Intent(this, CallScreenActivity.class);
                intent.putExtra(SinchService.CALL_ID, mCallId);
                startActivity(intent);
            } catch (MissingPermissionException e) {
                ActivityCompat.requestPermissions(this, new String[]{e.getRequiredPermission()}, 0);
            }
        } else {
            finish();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You may now answer the call", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "This application needs permission to use your microphone to function properly.", Toast
                    .LENGTH_LONG).show();
        }
    }

    private void declineClicked() {
        mAudioPlayer.stopRingtone();
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            call.hangup();
        }
        finish();
    }

    private class SinchCallListener implements CallListener {

        @Override
        public void onCallEnded(Call call) {
            CallEndCause cause = call.getDetails().getEndCause();
            Log.d(TAG, "Call ended, cause: " + cause.toString());
            mAudioPlayer.stopRingtone();
            finish();
        }

        @Override
        public void onCallEstablished(Call call) {
            Log.d(TAG, "Call established");
        }

        @Override
        public void onCallProgressing(Call call) {
            Log.d(TAG, "Call progressing");
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> pushPairs) {
            // Send a push through your push provider here, e.g. GCM
        }

    }
}
