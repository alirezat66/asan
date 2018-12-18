package ir.greencode.advicelawAndroid.profile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alirezaafkar.sundatepicker.DatePicker;
import com.alirezaafkar.sundatepicker.interfaces.DateSetListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import ir.greencode.advicelawAndroid.R;
import ir.greencode.advicelawAndroid.controler.AppDatabase;
import ir.greencode.advicelawAndroid.database.Profile;
import ir.greencode.advicelawAndroid.insertrequest.ChosePhotoTakerDialog;
import ir.greencode.advicelawAndroid.insertrequest.InsertRequestActivity;
import ir.greencode.advicelawAndroid.insertrequest.PhotoChoserInterface;
import ir.greencode.advicelawAndroid.retrofit.reqobject.ProfileUpdateReq;
import ir.greencode.advicelawAndroid.utils.BaseActivity;
import ir.greencode.advicelawAndroid.utils.Constants;
import ir.greencode.advicelawAndroid.utils.GenderPickerDialog;
import ir.greencode.advicelawAndroid.utils.PersianCalculater;
import ir.greencode.advicelawAndroid.utils.PreferencesData;
import ir.greencode.advicelawAndroid.utils.Utility;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import saman.zamani.persiandate.PersianDate;

public class ProfileActivity extends BaseActivity  implements DateSetListener,ProfileInterface {
    @BindView(R.id.img_profile)
    CircleImageView imgProfile;
    @BindView(R.id.edt_fname)
    EditText edtFname;
    @BindView(R.id.edt_lname)
    EditText edtLname;
    @BindView(R.id.header)
    RelativeLayout header;
    @BindView(R.id.edt_ncode)
    EditText edtNcode;
    @BindView(R.id.edt_birth_date)
    EditText edtBirthDate;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_sex)
    EditText edtSex;
    @BindView(R.id.edt_mail)
    EditText edtMail;
    @BindView(R.id.second_layout)
    RelativeLayout secondLayout;
    @BindView(R.id.btn_send)
    Button btnSend;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.footer)
    LinearLayout footer;
    Profile profile;
    String sex="";
    long birthDate = 0;
    KProgressHUD kProgressHUD;
    String b64Image = "";
    String[] mPermission = {
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
    };
    ChosePhotoTakerDialog choseDialog;
    ProfilePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        presenter = new ProfilePresenter(this);

                profile = AppDatabase.getInMemoryDatabase(this).profileDao().getProfile();
                if(profile!=null) {
                    b64Image = profile.getUserImage();
                    edtFname.setText(profile.getFName());
                    edtLname.setText(profile.getLName());
                    edtNcode.setText(profile.getNCode());
                    edtPhone.setText(profile.getMob());
                    edtMail.setText(profile.getMail());
                    if (profile.getGender().equals("male")) {
                        sex = profile.getGender();
                        edtSex.setText("مرد");
                    } else if (profile.getGender().equals("female")) {
                        sex = profile.getGender();
                        edtSex.setText("زن");
                    } else {
                        edtSex.setText("جنسیت");
                    }
                    if (profile.getBDate() != 0) {
                        birthDate = profile.getBDate();
                        edtBirthDate.setText(PersianCalculater.getYearMonthAndDay(profile.getBDate()));
                    }
                    if (!profile.getUserImage().equals("")) {
                        imgProfile.setImageBitmap(Utility.getBitmapFromBase(profile.getUserImage()));
                    }
                }



    }

    @Override
    protected void onResume() {

        super.onResume();
        AppDatabase database = AppDatabase.getInMemoryDatabase(this);
        if(database.profileDao().getProfile()!=null){
            profile = database.profileDao().getProfile();
        }
    }

    public void showWaiting(){
        kProgressHUD=  KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("لطفا منتظر بمانید")
                .setDetailsLabel("در حال انجام عملیات")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.8f)
                .setBackgroundColor(getResources().getColor(R.color.blueBack))
                .show();
    }
    public void disMissWaiting(){
        if(kProgressHUD!=null){
            kProgressHUD.dismiss();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 5817) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[2] == PackageManager.PERMISSION_GRANTED
                    ) {
                showDialogForImageSelector();

            } else {
                Toast.makeText(this, "بدون دادن اجازه نمی توانید مستندی اضافه کنید.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick({R.id.img_profile, R.id.edt_sex, R.id.btn_send, R.id.btn_cancel,R.id.edt_birth_date})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_profile:
                checkPermissionExtera();
                break;
            case R.id.edt_sex:
                openSexDialog();
                break;

            case R.id.btn_send:
                SendData();
                break;
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.edt_birth_date:
                showBirthDialog();
                break;
        }
    }

    private void SendData() {
        showWaiting();
        presenter.updateProfile(new ProfileUpdateReq(Utility.getToken(), Utility.getId(),edtFname.getText().toString(),
                edtLname.getText().toString(),edtNcode.getText().toString(),birthDate,sex,edtMail.getText().toString(),b64Image));
    }

    private void showBirthDialog() {
        long justNow = System.currentTimeMillis();
        final Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(justNow);
        PersianDate date = new PersianDate(System.currentTimeMillis());
        PersianDate defultYear = new PersianDate(System.currentTimeMillis());
        defultYear.setShYear(1368);
        calendar.setTimeInMillis(defultYear.getTime());
        new DatePicker.Builder()
                .id(1)

                .minYear(1300)
                .maxYear(1380)
                .maxMonth(date.getShMonth())
                .showYearFirst(true)
                .closeYearAutomatically(true)
                .theme(R.style.DialogTheme)
                .date(calendar)
                .build(ProfileActivity.this)
                .show(getSupportFragmentManager(), "انتخاب تاریخ تولد");
    }

    private void openSexDialog() {
        final GenderPickerDialog dialog=new GenderPickerDialog(ProfileActivity.this);
        dialog.setOnSelectingGender(new GenderPickerDialog.OnGenderSelectListener() {
            @Override
            public void onSelectingGender(String value) {
                edtSex.setText(value);
                if(value.equals("زن")){
                    sex="female";
                }else {
                    sex="male";
                }
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePicked(final File imageFile, EasyImage.ImageSource source, int type) {
                showWaiting();
                if(imageFile!=null) {
                    Picasso.with(ProfileActivity.this).load(imageFile)
                            .fit()
                            .centerCrop()
                            .into(imgProfile);
                    new ConvertToB64().execute(imageFile);
                }

            }
        });
    }
    private void checkPermissionExtera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                if (ActivityCompat.checkSelfPermission(this, mPermission[0])
                        != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(this, mPermission[1])
                                != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, mPermission[2])
                        != PackageManager.PERMISSION_GRANTED
                        ) {
                    ActivityCompat.requestPermissions(this,
                            mPermission, 5817);
                    // If any permission aboe not allowed by user, this condition will execute every tim, else your else part will work
                } else {
                    showDialogForImageSelector();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showDialogForImageSelector();
        }
    }


    public void showDialogForImageSelector() {
        choseDialog = new ChosePhotoTakerDialog(this);
        choseDialog.setListener(new PhotoChoserInterface() {
            @Override
            public void onSuccess(int type) {

                if (type == 1) {
                    EasyImage.openCamera(ProfileActivity.this, 500);
                    //camera chosemn
                } else {
                    EasyImage.openGallery(ProfileActivity.this, 600);
                    //gallery chosen
                }
                choseDialog.dismiss();
            }

            @Override
            public void onRejected() {
                choseDialog.dismiss();
            }
        });
        choseDialog.show();

    }

    @Override
    public void onDateSet(int id, @Nullable Calendar calendar, int day, int month, int year) {
            PersianDate pdate = new PersianDate(System.currentTimeMillis());
            pdate.setShYear(year);
            pdate.setMinute(0);
            pdate.setHour(12);
            pdate.setShMonth(month);
            pdate.setShDay(day);
            birthDate = pdate.getTime();
            edtBirthDate.setText(PersianCalculater.getYearMonthAndDay(year, month, day));
    }

    @Override
    public void onSuccess(Profile profile) {
        AppDatabase database = AppDatabase.getInMemoryDatabase(this);
        database.profileDao().update(profile);
        disMissWaiting();
        finish();
    }

    @Override
    public void onError(String message) {
        disMissWaiting();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    class ConvertToB64 extends AsyncTask<File, String, String> {
        @Override
        protected String doInBackground(File... files) {
            File imageFile = files[0];
            Bitmap bm = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            bm = Utility.resizeBitmap(bm, 800);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//added lines
            bm.recycle();
            bm = null;
//added lines
            byte[] b = baos.toByteArray();
            String b64 = Base64.encodeToString(b, Base64.DEFAULT);
            return b64;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            b64Image = result;
            disMissWaiting();

        }

    }

}
