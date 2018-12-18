package ir.greencode.advicelawAndroid.myrequest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.greencode.advicelawAndroid.R;
import ir.greencode.advicelawAndroid.retrofit.respObject.MyRequest;
import ir.greencode.advicelawAndroid.utils.BaseActivity;
import ir.greencode.advicelawAndroid.utils.Utility;

public class MyRequestActivity extends BaseActivity implements MyRequestInterface,MyRequestAdapter.onItemClick {
    @BindView(R.id.btn_insert_req)
    Button btnInsertReq;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.empty_layout)
    LinearLayout emptyLayout;

    MyRequestPresenter presenter;

    KProgressHUD progressHUD;
    @BindView(R.id.btn_back_second)
    Button btnBackSecond;
    @BindView(R.id.my_request_list)
    RecyclerView myRequestList;
    @BindView(R.id.ly_list)
    RelativeLayout lyList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_request);
        ButterKnife.bind(this);
        presenter = new MyRequestPresenter(this);
        getMyRequest();


    }

    @OnClick({R.id.btn_insert_req, R.id.btn_back,R.id.btn_back_second})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_insert_req:
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_back_second:
                finish();
                break;
        }
    }

    public void getMyRequest() {
        progressHUD = Utility.makeWaiting(this);
        progressHUD.show();
        presenter.getMyRequest(Utility.getToken());
    }

    @Override
    public void onError(String message) {
        progressHUD.dismiss();
        emptyShow();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void emptyShow() {
        lyList.setVisibility(View.GONE);
        emptyLayout.setVisibility(View.VISIBLE);
    }
    private void emptyHide(){
        lyList.setVisibility(View.VISIBLE);
        emptyLayout.setVisibility(View.GONE);
    }

    @Override
    public void onListReady(List<MyRequest> posts) {
        progressHUD.dismiss();
        if(posts.size()==0){
            emptyShow();
        }else {
            MyRequestAdapter adapter = new MyRequestAdapter(posts,this,this);
            myRequestList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
            myRequestList.setAdapter(adapter);
            emptyHide();
        }
    }

    @Override
    public void onClick(MyRequest item) {

    }
}
