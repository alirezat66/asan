package ir.greencode.advicelawAndroid.choselawyer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import ir.greencode.advicelawAndroid.R;
import ir.greencode.advicelawAndroid.cats.LawyerPresenter;
import ir.greencode.advicelawAndroid.objects.ChunkTime;
import ir.greencode.advicelawAndroid.objects.Course;
import ir.greencode.advicelawAndroid.objects.FreeTime;
import ir.greencode.advicelawAndroid.objects.Lawyer;
import ir.greencode.advicelawAndroid.retrofit.reqobject.AcceptReq;
import ir.greencode.advicelawAndroid.retrofit.reqobject.CancelReq;
import ir.greencode.advicelawAndroid.utils.BaseActivity;
import ir.greencode.advicelawAndroid.utils.PersianCalculater;
import ir.greencode.advicelawAndroid.utils.PreferencesData;
import ir.greencode.advicelawAndroid.utils.Utility;
import saman.zamani.persiandate.PersianDate;

public class LawyerActivity extends BaseActivity implements LawyerInterface, CourseAdapter.onItemClick {
    @BindView(R.id.imgPhoto)
    CircleImageView imgPhoto;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_education)
    TextView txtEducation;
    @BindView(R.id.txt_counsel_count)
    TextView txtCounselCount;
    @BindView(R.id.txt_happy_percent)
    TextView txtHappyPercent;
    @BindView(R.id.txt_rate_scpre)
    TextView txtRateScpre;
    @BindView(R.id.btn_add)
    ImageView btnAdd;
    @BindView(R.id.hour_time)
    TextView txtHourTime;
    @BindView(R.id.time_layout)
    LinearLayout timeLayout;
    @BindView(R.id.txt_location)
    TextView txtLocation;
    @BindView(R.id.txt_price_per_hours)
    TextView txtPricePerHours;
    @BindView(R.id.txt_experience)
    TextView txtExperience;
    @BindView(R.id.recycler_courses)
    RecyclerView recyclerCourses;
    @BindView(R.id.btn_load_doc)
    Button btnLoadDoc;
    @BindView(R.id.btn_send)
    Button btnSend;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.footer)
    LinearLayout footer;

    LawyerPresenter presenter;
    Lawyer lawyer;
    @BindView(R.id.spinner_services)
    Spinner spinnerServices;
    @BindView(R.id.btnMin)
    ImageView btnMin;
    List<FreeTime> times;
    double hour = 0.5;
    int responseHourId;
    int selectedId = 0;
    KProgressHUD progressHUD;
    int reqId = 0;
    int offerId = 0;
    String catName;
    String subCatName;
    List<String> spinnerList;
    String jsonStr;
    ArrayAdapter<String> spinnerAdapter;

    ArrayList<ChunkTime> validChunks = new ArrayList<>();
    @BindView(R.id.yourcardid)
    CardView yourcardid;
    @BindView(R.id.title_docs)
    TextView titleDocs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_detail);
        ButterKnife.bind(this);
        presenter = new LawyerPresenter(this);
        Bundle bundle = getIntent().getExtras();
        progressHUD = Utility.makeWaiting(this);
        if (bundle != null) {
            jsonStr = PreferencesData.getString("lawyer", this);

            reqId = bundle.getInt("reqId");
            catName = bundle.getString("catName");
            subCatName = bundle.getString("subCatName");
            offerId = bundle.getInt("offerId");
            Gson gson = new Gson();
            lawyer = gson.fromJson(jsonStr, Lawyer.class);

        }
        if (!lawyer.getUserImg().equals("")) {
            imgPhoto.setImageBitmap(Utility.getBitmapFromBase(lawyer.getUserImg()));
        }
        txtName.setText(lawyer.getfName() + " " + lawyer.getlName());
        txtEducation.setText(lawyer.getJobTitle().get(0));
        txtCounselCount.setText(" "+lawyer.getTotalAdvice() + " ");
        txtExperience.setText(lawyer.getYearOfWork() + " سال سابقه" + "\n" + "فعالیت تخصصی");
        txtLocation.setText(lawyer.getLocations().get(0));
        txtHappyPercent.setText("%"+(lawyer.getAverageScore() / lawyer.getAllPosibleAverageScore() * 100) );
        txtPricePerHours.setText("هر ساعت مشاوره" + "\n" + (lawyer.getPayEachHour() / 1000) + " هزار تومان");
        txtRateScpre.setText(" "+lawyer.getScore() + " ");
        recyclerCourses.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerCourses.setAdapter(new CourseAdapter(lawyer.getDocuments(), this, this));
        if(lawyer.getDocuments().size()==0){
            titleDocs.setVisibility(View.GONE);
        }

        times = lawyer.getFreeTimes();

        updateSpinner(hour);

        spinnerServices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void updateSpinner(double hour) {

        progressHUD = Utility.makeWaiting(this);
        progressHUD.show();
        //تعداد چانکها در میاد مثلا اگه ۱.۵ باشه معلوم می شه ما ۳ تا چانک داریم
        double chunck = hour / 0.5;
        int chunkNumbers = (int) chunck;
        // چانک تایم خودش آرایه ای هست از فری تایم ها
        ArrayList<ChunkTime> chunkTimes = new ArrayList<>();
        for (int i = 0; i < times.size() - chunkNumbers + 1; i++) {
            ArrayList<FreeTime> localChunk = new ArrayList<>();
            for (int j = i; j < i + chunkNumbers; j++) {
                localChunk.add(times.get(j));
            }
            ChunkTime chunkTime = new ChunkTime(localChunk);
            chunkTimes.add(chunkTime);
        }
        validChunks = new ArrayList<>();
        for (ChunkTime chunk : chunkTimes) {
            if (chunk.isValid()) {
                validChunks.add(chunk);
            }
        }

        spinnerList = new ArrayList<>();


        spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerList);
        for (ChunkTime chunkTime : validChunks) {
            PersianDate persianDate = new PersianDate(System.currentTimeMillis());
            FreeTime firstFree = chunkTime.getFreeTimes().get(0);
            String dayName = Utility.getPersianDayName(firstFree.getDayOfWeek());

            while (!persianDate.dayName().equals(dayName)) {
                persianDate.addDay(1);

            }

            spinnerAdapter.add(persianDate.dayName() + " " + persianDate.getShDay() + " " + persianDate.monthName() + " ساعت " + firstFree.getStartTime());

        }
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServices.setAdapter(spinnerAdapter);
        progressHUD.dismiss();
    }

    @Override
    public void onReadyList(List<Course> courseList) {
        recyclerCourses.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerCourses.setAdapter(new CourseAdapter(courseList, this, this));
    }

    @Override
    public void onError(String str) {
        progressHUD.dismiss();
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessAccept(String paymentUrl) {
        progressHUD.dismiss();
       /* Intent intent = new Intent(this,PrePaymentActivity.class);
        intent.putExtra("catName",catName);
        intent.putExtra("subCatName",subCatName);
        intent.putExtra("lawyerName",lawyer.getfName()+" "+lawyer.getlName());
        intent.putExtra("edu",lawyer.getJobTitle().get(0));
        intent.putExtra("time",spinnerList.get(spinnerServices.getSelectedItemPosition()));
        intent.putExtra("hour",hour);
        intent.putExtra("eachhour",lawyer.getPayEachHour());
        startActivity(intent);*/

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(paymentUrl));
        startActivity(i);
        finish();

    }

    @Override
    public void onSuccessCancel() {
        progressHUD.dismiss();

        finish();
    }

    @Override
    public void onClick(Course item) {

    }

    @OnClick({R.id.btnMin, R.id.btn_load_doc, R.id.btn_send, R.id.btn_cancel, R.id.btn_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnMin:
                if (hour > 0.5) {
                    hour = hour - 0.5;
                    txtHourTime.setText(hour + "");
                    updateSpinner(hour);
                }
                break;
            case R.id.btn_add:
                hour = hour + 0.5;
                txtHourTime.setText(hour + "");
                updateSpinner(hour);
                break;
            case R.id.btn_load_doc:
                finish();
                break;
            case R.id.btn_send:
                getAccept();
                break;
            case R.id.btn_cancel:
                progressHUD.show();
                presenter.cancelOffer(new CancelReq(Utility.getToken(), offerId, Utility.getId()));
                break;
        }
    }

    private void getAccept() {
        if (spinnerServices.getAdapter().getCount() > 0) {
            progressHUD = Utility.makeWaiting(this);
            ChunkTime chunkTime = validChunks.get(spinnerServices.getSelectedItemPosition());
            String ids = "";
            for (FreeTime freeTime : chunkTime.getFreeTimes()) {
                ids += freeTime.getTimeId();
                ids += ",";
            }
            if (ids.length() > 0) {
                ids = Utility.cutLastChar(ids);
            }


            PersianDate persianDate = new PersianDate(System.currentTimeMillis());
            FreeTime firstFree = chunkTime.getFreeTimes().get(0);
            String dayName = Utility.getPersianDayName(firstFree.getDayOfWeek());

            while (!persianDate.dayName().equals(dayName)) {
                persianDate.addDay(1);

            }
            String[] parts = firstFree.getStartTime().split(":");
            persianDate.setHour(Integer.parseInt(parts[0]));
            persianDate.setMinute(Integer.parseInt(parts[1]));
            PreferencesData.saveString("call_time", parts[0] + ":" + parts[1], this);
            Intent intent = new Intent(this, PrePaymentActivity.class);
            intent.putExtra("catName", catName);
            intent.putExtra("subCatName", subCatName);
            intent.putExtra("lawyerName", lawyer.getfName() + " " + lawyer.getlName());
            intent.putExtra("edu", lawyer.getJobTitle().get(0));

            intent.putExtra("time", persianDate.dayName() + " " + persianDate.getShDay() + " " + persianDate.monthName() + " " + persianDate.getShYear() + "، ساعت " + PersianCalculater.getHourseAndMin(persianDate.getTime()));
            intent.putExtra("hour", hour);
            intent.putExtra("eachhour", lawyer.getPayEachHour());
            intent.putExtra("send_time", persianDate.getTime() / 1000);
            intent.putExtra("send_adviser_id", lawyer.getId());
            intent.putExtra("send_offer_id", offerId);
            intent.putExtra("send_ids", ids);
            PreferencesData.saveString("lawyer", jsonStr, this);
            intent.putExtra("reqId", reqId);
            startActivity(intent);
            //       presenter.acceptLawyer(new AcceptReq(Utility.getToken(),offerId,lawyer.getId(),ids,persianDate.getTime()/1000,hour));

        } else {
            Toast.makeText(this, "زمان شروع باید انتخاب شود.", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendRequest() {
        if (spinnerServices.getAdapter().getCount() > 0) {
            progressHUD = Utility.makeWaiting(this);
            ChunkTime chunkTime = validChunks.get(spinnerServices.getSelectedItemPosition());
            String ids = "";
            for (FreeTime freeTime : chunkTime.getFreeTimes()) {
                ids += freeTime.getTimeId();
                ids += ",";
            }
            if (ids.length() > 0) {
                ids = Utility.cutLastChar(ids);
            }


            PersianDate persianDate = new PersianDate(System.currentTimeMillis());
            FreeTime firstFree = chunkTime.getFreeTimes().get(0);
            String dayName = Utility.getPersianDayName(firstFree.getDayOfWeek());

            while (!persianDate.dayName().equals(dayName)) {
                persianDate.addDay(1);

            }
            String[] parts = firstFree.getStartTime().split(":");
            persianDate.setHour(Integer.parseInt(parts[0]));
            persianDate.setMinute(Integer.parseInt(parts[1]));
            PreferencesData.saveString("call_time", parts[0] + ":" + parts[1], this);
            presenter.acceptLawyer(new AcceptReq(Utility.getToken(), offerId, lawyer.getId(), ids, persianDate.getTime() / 1000, hour));

        } else {
            Toast.makeText(this, "زمان شروع باید انتخاب شود.", Toast.LENGTH_SHORT).show();
        }

    }
}
