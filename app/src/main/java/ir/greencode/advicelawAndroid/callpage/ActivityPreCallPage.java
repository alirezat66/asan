package ir.greencode.advicelawAndroid.callpage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.greencode.advicelawAndroid.R;
import ir.greencode.advicelawAndroid.calling.CallScreenActivity;
import ir.greencode.advicelawAndroid.dialogs.QuestionDialog;
import ir.greencode.advicelawAndroid.dialogs.QuestionListener;
import ir.greencode.advicelawAndroid.objects.DoneDetailItem;
import ir.greencode.advicelawAndroid.retrofit.reqobject.CancelReq;
import ir.greencode.advicelawAndroid.retrofit.reqobject.DoneAdviceDetailReq;
import ir.greencode.advicelawAndroid.retrofit.respObject.CallStateResponse;
import ir.greencode.advicelawAndroid.retrofit.respObject.DoneAdviceDetailRes;
import ir.greencode.advicelawAndroid.services.SinchService;
import ir.greencode.advicelawAndroid.utils.BaseActivity;
import ir.greencode.advicelawAndroid.utils.Constants;
import ir.greencode.advicelawAndroid.utils.PersianCalculater;
import ir.greencode.advicelawAndroid.utils.PreferencesData;
import ir.greencode.advicelawAndroid.utils.Utility;
import saman.zamani.persiandate.PersianDate;

public class ActivityPreCallPage extends BaseActivity implements PreCallInterface , SinchService.StartFailedListener{
    @BindView(R.id.imgLogo)
    ImageView imgLogo;
    @BindView(R.id.txt_cat_name)
    TextView txtCatName;
    @BindView(R.id.txt_sub_cat_name)
    TextView txtSubCatName;
    @BindView(R.id.docs_recycler)
    RecyclerView list;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.btn_call)
    Button btnCall;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.footer)
    LinearLayout footer;

    KProgressHUD progressHUD ;
    KProgressHUD callProgress ;
    PreCallPresenter presentr;
    DoneDetailAdapter adapter;
    int offerId ;
    String catName = "";
    String subCatName = "";
    String b64Img;
    int reqId;
    String userName="";
    DoneAdviceDetailRes detailRes ;
    int ourcall;


    String[] mPermission = {
            Manifest.permission.RECORD_AUDIO
    };
    private static final int REQUEST_CODE_PERMISSION = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_call_request);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            offerId = bundle.getInt("offerId");
            catName = bundle.getString("catName");
            subCatName = bundle.getString("subCatName");
            b64Img = PreferencesData.getString("b64",this);
            reqId = bundle.getInt("reqId");
            userName ="user"+ bundle.getInt("userId");
        }
        txtCatName.setText(catName);
        txtSubCatName.setText(subCatName);
        this.presentr = new PreCallPresenter(this);
        progressHUD = Utility.makeWaiting(this);
        progressHUD.show();
        presentr.getDetail(new DoneAdviceDetailReq(offerId));


    }

    @OnClick({R.id.btn_back, R.id.btn_call, R.id.btn_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_call:
                if (ActivityCompat.checkSelfPermission(this, mPermission[0])
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            mPermission, REQUEST_CODE_PERMISSION);
                    // If any permission aboe not allowed by user, this condition will execute every tim, else your else part will work
                }else {
                    presentr.getCallState(Utility.getToken(),offerId+"");
                }
                break;
            case R.id.btn_cancel:
                showCancelDialog();

                break;
        }
    }

    private void callingProcess() {

        if (!userName.equals(getSinchServiceInterface().getUserName())) {
            getSinchServiceInterface().stopClient();
            getSinchServiceInterface().startClient(userName);
            callProgress = Utility.makeWaiting("در حال برقراری تماس","لطفا منتظر بمانید...",this);
            callProgress.show();
        }

       else if (!getSinchServiceInterface().isStarted()) {
            getSinchServiceInterface().startClient(userName);
            callProgress = Utility.makeWaiting("در حال برقراری تماس","لطفا منتظر بمانید...",this);
            callProgress.show();
        } else {
            openPlaceCallActivity();
        }


    }
    @Override
    protected void onServiceConnected() {
        getSinchServiceInterface().setStartListener(this);
    }

    private void showCancelDialog() {
        String dialogCancelTitle = Utility.makeCancelTitle(detailRes.getTimeOfadvice()*1000,detailRes.getPrice());
        final QuestionDialog dialog = new QuestionDialog(this,"",dialogCancelTitle);
        dialog.setListener(new QuestionListener() {
            @Override
            public void onReject() {
                dialog.dismiss();
            }

            @Override
            public void onSuccess() {
                progressHUD = Utility.makeWaiting(ActivityPreCallPage.this);
                progressHUD.show();
                presentr.cancelRequest(new CancelReq(Utility.getToken(),reqId, PreferencesData.getInt(Constants.Pref_USER_ID,ActivityPreCallPage.this)));
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    @Override
    public void onError(String str) {
        progressHUD.dismiss();
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessRes(DoneAdviceDetailRes resp) {
        progressHUD.dismiss();
        ourcall = resp.getCallId();
        ArrayList<DoneDetailItem> items = new ArrayList<>();
        String job = resp.getAdvicerInfo().getType().size()>0?resp.getAdvicerInfo().getType().get(0):"ضمینه شغلی نامشخص";
        items.add(new DoneDetailItem("مشاور",resp.getAdvicerInfo().getfName()+" "+resp.getAdvicerInfo().getlName(),job));
        PersianDate date = new PersianDate(resp.getTimeOfadvice()*1000);
        items.add(new DoneDetailItem("زمان", date.dayName()+" "+date.getShDay()
                +" "+date.monthName()+" "+date.getShYear()+
                "، "+"ساعت "+PersianCalculater.getHourseAndMin(date.getTime()),"به مدت "+" "+resp.getAdviceHourCount() +" ساعت"));
        items.add(new DoneDetailItem("نوع","مشاوره تلفنی آنلاین","تماس درون برنامه ای"));
        items.add(new DoneDetailItem("مبلغ","هر ساعت مشاوره "+resp.getAdvicerInfo().getPayEachHour()+" هزار تومان","مبلغ پرداختی شما "+resp.getPrice()+" هزار تومان"));
        items.add(new DoneDetailItem("پرداخت","پرداخت آنلاین","زمان و شماره پرداخت " + resp.getTrackingCode() +", "+ PersianCalculater.getYearMonthAndDay(resp.getTimeOfPayment()*1000)+" ساعت "+PersianCalculater.getHourseAndMin(resp.getTimeOfPayment())));
        detailRes = resp;
        adapter = new DoneDetailAdapter(items,this);
        list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        list.setAdapter(adapter);

        if((System.currentTimeMillis()/1000)>detailRes.getTimeOfadvice()){
            btnCall.setEnabled(true);
            btnCall.setBackgroundColor(getResources().getColor(R.color.done));
        }


    }

    @Override
    public void onSuccessCancel() {
        progressHUD.dismiss();
        Toast.makeText(this, "با موقیت کنسل شد.", Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    public void onSuccessCallState(CallStateResponse res) {
        if(res.getState()==1) {
            callingProcess();
        }else {
            Toast.makeText(this, "تماس در این ساعت بر اساس سفارش شما مقدور نیست . لطفا در زمان صحیح تماس بگیرید.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStartFailed(SinchError error) {
        callProgress.dismiss();
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStarted() {
        callProgress.dismiss();
        if (ActivityCompat.checkSelfPermission(this, mPermission[0])
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    mPermission, REQUEST_CODE_PERMISSION);
            // If any permission aboe not allowed by user, this condition will execute every tim, else your else part will work
        }else {
            openPlaceCallActivity();
        }
    }

    private void openPlaceCallActivity() {
        Call call = getSinchServiceInterface().callUser("lawyer"+detailRes.getAdvicerInfo().getId());
        if (call == null) {
            // Service failed for some reason, show a Toast and abort
            Toast.makeText(this, "Service is not started. Try stopping the service and starting it again before "
                    + "placing a call.", Toast.LENGTH_LONG).show();
            progressHUD.dismiss();
            return;
        }
        String callId = call.getCallId();
        Intent callScreen = new Intent(this, CallScreenActivity.class);
        callScreen.putExtra("ourcallId",ourcall);
        callScreen.putExtra(SinchService.CALL_ID, callId);
        startActivity(callScreen);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                callingProcess();

         } else {
                Toast.makeText(this, "بدون دادن اجازه نمی توانید تماس برقرار کنید", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
