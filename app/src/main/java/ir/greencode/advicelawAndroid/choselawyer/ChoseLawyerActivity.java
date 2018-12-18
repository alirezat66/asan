package ir.greencode.advicelawAndroid.choselawyer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.greencode.advicelawAndroid.R;
import ir.greencode.advicelawAndroid.callpage.ActivityPreCallPage;
import ir.greencode.advicelawAndroid.dialogs.QuestionDialog;
import ir.greencode.advicelawAndroid.dialogs.QuestionListener;
import ir.greencode.advicelawAndroid.main.PhotoActivity;
import ir.greencode.advicelawAndroid.objects.DocumentImg;
import ir.greencode.advicelawAndroid.objects.Lawyer;
import ir.greencode.advicelawAndroid.retrofit.reqobject.CancelReq;
import ir.greencode.advicelawAndroid.retrofit.respObject.WaitedAdviceDetial;
import ir.greencode.advicelawAndroid.retrofit.reqobject.GetWaitedDetailsReq;
import ir.greencode.advicelawAndroid.utils.BaseActivity;
import ir.greencode.advicelawAndroid.utils.Constants;
import ir.greencode.advicelawAndroid.utils.PreferencesData;
import ir.greencode.advicelawAndroid.utils.Utility;

public class ChoseLawyerActivity extends BaseActivity implements ChoseLawyerInterface, LawyerAdapter.onItemClick, DocumentsAdapter.onItemClick {
    @BindView(R.id.imgLogo)
    ImageView imgLogo;
    @BindView(R.id.txt_cat_name)
    TextView txtCatName;
    @BindView(R.id.txt_sub_cat_name)
    TextView txtSubCatName;

    @BindView(R.id.txt_lawyer_count)
    TextView txtLawyerCount;
    @BindView(R.id.lawyer_recycler)
    RecyclerView lawyerRecycler;
    @BindView(R.id.txt_description)
    TextView txtDescription;
    @BindView(R.id.docs_recycler)
    RecyclerView docsRecycler;

    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.footer)
    LinearLayout footer;

    String catName = "";
    String subCatName = "";

    ChoseLawerPresenter presenter;
    @BindView(R.id.layer_state)
    LinearLayout layerState;
    int state;
    int numberOfLawer;
    String b64Img;
    int offerId;
    int reqId;
    KProgressHUD progress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_lawer);
        ButterKnife.bind(this);






    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle bundle = getIntent().getExtras();
        progress = Utility.makeWaiting(this);
        progress.show();
        if (bundle != null) {
            offerId = bundle.getInt("offerId");
            reqId = bundle.getInt("reqId");
            state = bundle.getInt("state");
            catName = bundle.getString("catName");
            subCatName = bundle.getString("subCatName");
            numberOfLawer = bundle.getInt("numberOfLower");
            b64Img = PreferencesData.getString("b64",this);
        }
        if(state==0){
            layerState.setBackgroundColor(getResources().getColor(R.color.greenBlue));
            txtLawyerCount.setText("هنوز هیچ مشاوری اعلام آمادگی نکرده");
            presenter = new ChoseLawerPresenter(this);
            presenter.getDetails(new GetWaitedDetailsReq(reqId,Utility.getToken()));
        }else if(state==1){
            layerState.setBackgroundColor(getResources().getColor(R.color.pinkLight));
            txtLawyerCount.setText(numberOfLawer + " متخصص آماده ارائه مشاوره به شما");
            presenter = new ChoseLawerPresenter(this);
            presenter.getActiveDetail(new GetWaitedDetailsReq(reqId,Utility.getToken()));
        }
        txtCatName.setText(catName);
        txtSubCatName.setText(subCatName);

        if(b64Img.equals("")){
            imgLogo.setImageDrawable(getResources().getDrawable(R.drawable.sample_icon_1));
        }else {
            imgLogo.setImageBitmap(Utility.getBitmapFromBase(b64Img));

        }
    }

    @Override
    public void onReadyLawyer(WaitedAdviceDetial detial) {
        if (progress.isShowing())
            progress.dismiss();
        txtDescription.setText(detial.getContext());
        lawyerRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        docsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        if(detial.getAdvisers()!=null){
             if(detial.getAdvisers().size()>0) {
            lawyerRecycler.setAdapter(new LawyerAdapter(detial.getAdvisers(), this, this));
            }
        }
        if(detial.getDocuments().size()>0) {
            DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
            docsRecycler.addItemDecoration(divider);
            docsRecycler.setAdapter(new DocumentsAdapter(detial.getDocuments(), this, this, false));
        }
    }

    @Override
    public void onError(String str) {
        if(progress.isShowing())
            progress.dismiss();
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessCancel() {
        if(progress.isShowing())
            progress.dismiss();
        finish();
    }

    @Override
    public void onClick(Lawyer item) {


        Intent intent = new Intent(this, LawyerActivity.class);
        PreferencesData.saveString("lawyer", item.toJson().toString(),this);
        intent.putExtra("reqId", reqId);
        intent.putExtra("offerId",offerId);
        intent.putExtra("catName",txtCatName.getText().toString());
        intent.putExtra("subCatName",txtSubCatName.getText().toString());
        startActivity(intent);
    }

    @OnClick({R.id.btn_back, R.id.btn_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_cancel:
                showCancelDialog();

                break;
        }
    }

    private void showCancelDialog() {
        final QuestionDialog dialog = new QuestionDialog(this,"","آیا از لغو مشاوره اطمینان دارید؟");
        dialog.setListener(new QuestionListener() {
            @Override
            public void onReject() {
                dialog.dismiss();
            }

            @Override
            public void onSuccess() {
                progress.show();
                if(state==0) {
                    presenter.cancelReq(new CancelReq(Utility.getToken(), reqId, Utility.getId()));
                }else {
                    presenter.cancelOffer(new CancelReq(Utility.getToken(), offerId, Utility.getId()));

                }
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    @Override
    public void onClick(DocumentImg item) {
        Intent intent = new Intent(this, PhotoActivity.class);
        PreferencesData.saveString("myb64",item.getDocSource(),this);
     //   intent.putExtra("b64",item.getDocSource());
        startActivity(intent);
    }

    @Override
    public void onDelete(DocumentImg item) {

    }
}
