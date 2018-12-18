package ir.greencode.advicelawAndroid.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.sinch.android.rtc.SinchError;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.greencode.advicelawAndroid.MainActivity;
import ir.greencode.advicelawAndroid.PreSplashActivity;
import ir.greencode.advicelawAndroid.R;
import ir.greencode.advicelawAndroid.calling.IncomingCallScreenActivity;
import ir.greencode.advicelawAndroid.callpage.ActivityPreCallPage;
import ir.greencode.advicelawAndroid.cats.CategoryActivity;
import ir.greencode.advicelawAndroid.choselawyer.ChoseLawyerActivity;
import ir.greencode.advicelawAndroid.controler.AppDatabase;
import ir.greencode.advicelawAndroid.database.Profile;
import ir.greencode.advicelawAndroid.dialogs.MenuDialog;
import ir.greencode.advicelawAndroid.dialogs.MenuDialogInterface;
import ir.greencode.advicelawAndroid.dialogs.QuestionDialog;
import ir.greencode.advicelawAndroid.dialogs.QuestionListener;
import ir.greencode.advicelawAndroid.myrequest.MyRequestActivity;
import ir.greencode.advicelawAndroid.objects.Advice;
import ir.greencode.advicelawAndroid.objects.MenuItem;
import ir.greencode.advicelawAndroid.profile.ProfileActivity;
import ir.greencode.advicelawAndroid.retrofit.reqobject.ProfileInfoReq;
import ir.greencode.advicelawAndroid.services.SinchService;
import ir.greencode.advicelawAndroid.signin.SignInActivity;
import ir.greencode.advicelawAndroid.signin.VerificationActivity;
import ir.greencode.advicelawAndroid.utils.BaseActivity;
import ir.greencode.advicelawAndroid.utils.Constants;
import ir.greencode.advicelawAndroid.utils.PreferencesData;
import ir.greencode.advicelawAndroid.utils.Utility;

public class ActivityMain extends BaseActivity implements MainInterfacde, AdviceAdapter.onItemClick, SinchService.StartFailedListener {


    MainPresenter presenter;

    KProgressHUD progress;
    Profile profile;
    @BindView(R.id.imglogotwo)
    ImageView imglogotwo;
    @BindView(R.id.emptyList)
    LinearLayout emptyList;
    @BindView(R.id.imgLogo)
    ImageView imgLogo;
    @BindView(R.id.txtsizeCount)
    TextView txtsizeCount;
    @BindView(R.id.layoutCount)
    LinearLayout layoutCount;
    @BindView(R.id.advice_recycler)
    RecyclerView adviceRecycler;
    @BindView(R.id.rlList)
    RelativeLayout rlList;
    @BindView(R.id.btn_insert_req)
    Button btnInsertReq;
    @BindView(R.id.btnLayout)
    LinearLayout btnLayout;
    @BindView(R.id.img_menu)
    LinearLayout imgMenu;
    @BindView(R.id.img_comment)
    LinearLayout imgComment;
    @BindView(R.id.RestofScreen)
    CardView RestofScreen;

    boolean makeListLock = false;
    String[] mPermission = {
            Manifest.permission.RECORD_AUDIO
    };
    QuestionDialog permissionDialog ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_asli);
        ButterKnife.bind(this);



    }

    private void makeList() {
        if(!makeListLock) {
            makeListLock =true;
            if (progress != null) {
                if (!progress.isShowing()) {
                    progress = Utility.makeWaiting(Constants.WaitingTitle, Constants.WaitingDescNormal, this);
                    progress.show();
                    String token = PreferencesData.getString(Constants.PREF_TOKEN, this);
                    presenter.getAdviceList(token);
                }
            } else {
                progress = Utility.makeWaiting(Constants.WaitingTitle, Constants.WaitingDescNormal, this);
                progress.show();
                String token = PreferencesData.getString(Constants.PREF_TOKEN, this);
                presenter.getAdviceList(token);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.presenter = new MainPresenter(this, this);



        if (profile != null) {
            if(getSinchServiceInterface()!=null) {
                checkPermissionExtera();
            }
        }else {
            presenter.getUserInfo(new ProfileInfoReq(Utility.getToken(),Utility.getId()));
        }
        makeList();

    }

    private void checkPermissionExtera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                if (ActivityCompat.checkSelfPermission(this, mPermission[0])
                        != PackageManager.PERMISSION_GRANTED
                        ) {
                    if(permissionDialog!=null) {
                        if (!permissionDialog.isShowing()) {
                            // If any permission aboe not allowed by user, this condition will execute every tim, else your else part will work
                            showDialogForAudio();
                        }else {
                            if(getSinchServiceInterface()!=null){
                                loginTosinch();
                            }
                        }
                    }else {
                        showDialogForAudio();
                    }

                }else {
                    if(getSinchServiceInterface()!=null){
                        loginTosinch();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            loginTosinch();
        }
    }

    private void showDialogForAudio() {
        permissionDialog= new QuestionDialog(this,"اجازه برقراری تماس","برای برقراری تماس با مشاوره دهنده نیاز است که اجازه استفاده از میکروفن برای برقراری تماس های درون برنامه ای گرفته شود. آیا دسترسی می دهید؟");

        permissionDialog.setListener(new QuestionListener() {
            @Override
            public void onReject() {
                showDialogLastChance();
                permissionDialog.dismiss();
            }

            @Override
            public void onSuccess() {
                ActivityCompat.requestPermissions(ActivityMain.this,
                        mPermission, 5817);
                permissionDialog.dismiss();
                loginTosinch();
            }
        });
        permissionDialog.show();
    }

    private void showDialogLastChance() {
        final QuestionDialog dialog =new QuestionDialog(this,"","در صورت اجازه ندادن ، امکان برقراری تماس از سمت مشاوره دهنده به شما و از سمت شما به مشاوره دهنده وجود نخواهد داشت. آیا از عدم اجازه اطمینان دارید؟");
        dialog.setListener(new QuestionListener() {
            @Override
            public void onReject() {
                dialog.dismiss();
                checkPermissionExtera();
            }

            @Override
            public void onSuccess() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onResume() {
        makeList();
        super.onResume();

    }

    private void openMenu() {
        final MenuDialog dialog = new MenuDialog(this, profile);
        dialog.setListener(new MenuDialogInterface() {
            @Override
            public void onProfileClick() {
                Intent intent = new Intent(ActivityMain.this, ProfileActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }

            @Override
            public void onChosenItem(MenuItem item) {
                if (item.getPosition() == 1) {
                    //my request activity
                    Intent intent = new Intent(ActivityMain.this, MyRequestActivity.class);
                    startActivity(intent);
                }else if(item.getPosition()==5){
                    FirebaseInstanceId.getInstance().getInstanceId()
                            .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                @Override
                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                    if (!task.isSuccessful()) {
                                        Log.w("hilevel", "getInstanceId failed", task.getException());
                                        return;
                                    }
                                    // Get new Instance ID token
                                    String token = task.getResult().getToken();


                                    progress = Utility.makeWaiting("در حال ","لطفا منتظر بمانید...",ActivityMain.this);
                                    progress.show();
                                    presenter.logout(Utility.getToken());

                                    // Log and toast

                                }
                            });
                }
                dialog.dismiss();
            }

            @Override
            public void onFinish() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onReadyAdviceList(List<Advice> adviceList) {
        makeListLock = false;
        if (progress.isShowing())
            progress.dismiss();
        if (adviceList.size() > 0) {
            emptyList.setVisibility(View.GONE);
            rlList.setVisibility(View.VISIBLE);
            txtsizeCount.setText("شما " + adviceList.size() + " درخواست فعال دارید");
            txtsizeCount.setVisibility(View.VISIBLE);
            adviceRecycler.setVisibility(View.VISIBLE);
            adviceRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            adviceRecycler.setAdapter(new AdviceAdapter(adviceList, this, this));
        } else {
            emptyList.setVisibility(View.VISIBLE);
            rlList.setVisibility(View.GONE);
            layoutCount.setVisibility(View.GONE);
            adviceRecycler.setVisibility(View.GONE);
        }
    }

    @Override
    public void onError(String str) {
        makeListLock = false;
        if(!str.equals("401")) {
            progress.dismiss();
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        }else {
            PreferencesData.saveBool(Constants.PREF_LOGIN,false,this);
            Intent intent = new Intent(this, PreSplashActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onSuccessProfile(Profile profile) {
        this.profile = profile;
        AppDatabase database = AppDatabase.getInMemoryDatabase(this);
        database.profileDao().insert(profile);
        if(getSinchServiceInterface()!=null) {
            checkPermissionExtera();
        }


    }

    @Override
    public void onSuccessLogout() {
        PreferencesData.saveBool(Constants.PREF_LOGIN,false,this);
        PreferencesData.saveString(Constants.PREF_TOKEN,"",this);
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(Advice item) {

        if (item.getState() == 0 || item.getState() == 1) {
            Intent intent = new Intent(this, ChoseLawyerActivity.class);
            intent.putExtra("catName", item.getTitle());
            intent.putExtra("subCatName", item.getSubTitle());
            intent.putExtra("state", item.getState());
            PreferencesData.saveString("b64", item.getImgCat(),this);
            intent.putExtra("numberOfLower", item.getLawersCont());

            intent.putExtra("offerId", item.getOfferId());
            intent.putExtra("reqId", item.getAdviceId());

            startActivity(intent);
        } else if (item.getState() == 2) {
            Intent intent = new Intent(this, ActivityPreCallPage.class);
            intent.putExtra("catName", item.getTitle());
            intent.putExtra("subCatName", item.getSubTitle());
            PreferencesData.saveString("b64", item.getImgCat(),this);
            intent.putExtra("offerId", item.getOfferId());
            intent.putExtra("reqId", item.getAdviceId());
            intent.putExtra("userId",profile.getId());
            startActivity(intent);

        }

    }

    @OnClick({R.id.btn_insert_req, R.id.img_menu, R.id.img_comment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_insert_req:
                goToCatActivity();
                break;
            case R.id.img_menu:
                openMenu();
                break;
            case R.id.img_comment:
                break;
        }
    }
    private void loginTosinch(){
        if(profile!=null) {
            String userName = "user" + profile.getId();
            if (!userName.equals(getSinchServiceInterface().getUserName())) {
                getSinchServiceInterface().stopClient();
                getSinchServiceInterface().startClient(userName);

            }

            if (!getSinchServiceInterface().isStarted()) {
                getSinchServiceInterface().startClient(userName);

            }
        }
    }

    @Override
    protected void onServiceConnected() {
        if(getSinchServiceInterface()!=null) {
            getSinchServiceInterface().setStartListener(this);
        }
    }
    private void goToCatActivity() {
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivity(intent);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 5817) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                showDialogLastChance();
                //   Toast.makeText(this, "بدون دادن اجازه نمی توانید مستندی اضافه کنید.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onStartFailed(SinchError error) {
        loginTosinch();
    }

    @Override
    public void onStarted() {

    }
}
