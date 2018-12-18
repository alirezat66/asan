package ir.greencode.advicelawAndroid.choselawyer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.greencode.advicelawAndroid.R;
import ir.greencode.advicelawAndroid.cats.LawyerPresenter;
import ir.greencode.advicelawAndroid.objects.ChunkTime;
import ir.greencode.advicelawAndroid.objects.Course;
import ir.greencode.advicelawAndroid.objects.FreeTime;
import ir.greencode.advicelawAndroid.objects.Lawyer;
import ir.greencode.advicelawAndroid.objects.Subject;
import ir.greencode.advicelawAndroid.retrofit.reqobject.AcceptReq;
import ir.greencode.advicelawAndroid.utils.BaseActivity;
import ir.greencode.advicelawAndroid.utils.PreferencesData;
import ir.greencode.advicelawAndroid.utils.Utility;
import saman.zamani.persiandate.PersianDate;

public class PrePaymentActivity extends BaseActivity implements LawyerInterface {

    @BindView(R.id.btn_back)
    Button btnBack;

    String catName, subCatName, lawyerName, edu, time;
    double hour ;
    int  eachhour;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.btn_pay)
    Button btnPay;
    LawyerPresenter presenter;
    KProgressHUD progressHUD;

    long send_time;
    int send_adviser_id;
    int send_offer_id;
    String send_ids;
    int reqId;
    String lawyer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_payed);
        ButterKnife.bind(this);
        presenter = new LawyerPresenter(this);
        progressHUD = Utility.makeWaiting(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            catName = bundle.getString("catName");
            subCatName = bundle.getString("subCatName");
            lawyerName = bundle.getString("lawyerName");
            edu = bundle.getString("edu");
            time = bundle.getString("time");
            hour = bundle.getDouble("hour");
            eachhour = bundle.getInt("eachhour");
            send_time = bundle.getLong("send_time");
            send_adviser_id = bundle.getInt("send_adviser_id");
            send_offer_id = bundle.getInt("send_offer_id");
            send_ids = bundle.getString("send_ids");
            lawyer = PreferencesData.getString("lawyer",this);
            reqId = bundle.getInt("reqId");

        }
        List<Subject> subjects = new ArrayList<>();
        Subject subject = new Subject("موضوع", "درخواست مشاوره برای " + catName + "\n" + subCatName);
        Subject subject1 = new Subject("مشاور", lawyerName + "\n" + edu);
        Subject subject2 = new Subject("زمان", time + "\n" + "به مدت " + hour + " ساعت");
        Subject subject3 = new Subject("نوع", "مشاوره تلفنی آنلاین" + "\n" + "تماس رایگان درون برنامه ای");
        Subject subject4 = new Subject("مبلغ", "هر ساعت مشاوره " + eachhour + " تومان" + "\n" + "مبلغ پرداختی شما " +(int)(eachhour * hour) + " تومان");
        Subject subject5 = new Subject("پرداخت", "پرداخت آنلاین" + "\n" + "از طریق تمامی کارت های بانکی");
        subjects.add(subject);
        subjects.add(subject1);
        subjects.add(subject2);
        subjects.add(subject3);
        subjects.add(subject4);
        subjects.add(subject5);
        SubjectAdapter adapter = new SubjectAdapter(subjects, this);
        list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        list.setAdapter(adapter);
    }

    @OnClick({R.id.btn_back, R.id.btn_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                Intent intent = new Intent(this, LawyerActivity.class);
                intent.putExtra("lawyer",lawyer);
                intent.putExtra("reqId",reqId);
                intent.putExtra("catName",catName);
                intent.putExtra("subCatName",subCatName);
                intent.putExtra("offerId",send_offer_id);
                startActivity(intent);
                finish();

                break;
            case R.id.btn_pay:
                sendRequest();

                break;
        }
    }


    private void sendRequest() {
            progressHUD = Utility.makeWaiting(this);
            progressHUD.show();
            presenter.acceptLawyer(new AcceptReq(Utility.getToken(),send_offer_id,send_adviser_id,send_ids,send_time,hour));


    }

    @Override
    public void onReadyList(List<Course> courseList) {

    }

    @Override
    public void onError(String str) {
        progressHUD.dismiss();
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessAccept(String paymentUrl) {
        progressHUD.dismiss();
              Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(paymentUrl));
        startActivity(i);
        finish();
    }

    @Override
    public void onSuccessCancel() {

    }
}
