package ir.greencode.advicelawAndroid.cats;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.greencode.advicelawAndroid.R;
import ir.greencode.advicelawAndroid.controler.AppDatabase;
import ir.greencode.advicelawAndroid.insertrequest.InsertRequestActivity;
import ir.greencode.advicelawAndroid.objects.SubCat;
import ir.greencode.advicelawAndroid.retrofit.reqobject.SubCatReq;
import ir.greencode.advicelawAndroid.utils.BaseActivity;
import ir.greencode.advicelawAndroid.utils.Constants;
import ir.greencode.advicelawAndroid.utils.DividerDecorator;
import ir.greencode.advicelawAndroid.utils.PreferencesData;
import ir.greencode.advicelawAndroid.utils.Utility;

public class SubCatActivity extends BaseActivity implements SubCatInterface , SubCatAdapter.onItemClick {
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.txtCatName)
    TextView txtCatName;
    @BindView(R.id.btnChange)
    Button btnChange;
    @BindView(R.id.recycler_sub_cat)
    RecyclerView recyclerSubCat;
    String catName;
    int catId=0;
    SubCatPresenter presenter;
    KProgressHUD progress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        ButterKnife.bind(this);
        presenter = new SubCatPresenter(this);


        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            catName = bundle.getString("catName");
            catId = bundle.getInt("catId");
            txtCatName.setText(catName);
            String token = PreferencesData.getString(Constants.PREF_TOKEN,this);
            progress = Utility.makeWaiting(Constants.WaitingTitle,Constants.WaitingDescNormal,this);
            progress.show();
            presenter.getSubCats(new SubCatReq(token,0,catId));
        }
    }

    @OnClick({R.id.imgBack, R.id.btnChange})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                openCat();
                break;
            case R.id.btnChange:
                openCat();
                break;
        }
    }

    private void openCat() {
        Intent intent = new Intent(this,CategoryActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(SubCat item) {
        Intent intent = new Intent(this, InsertRequestActivity.class);
        intent.putExtra("catName",catName);
        intent.putExtra("subCatName",item.getSubCatName());
        intent.putExtra("catId",item.getId());
        startActivity(intent);
        finish();
    }

    @Override
    public void onReadySubCat(List<SubCat> subCats) {
        progress.dismiss();
        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerSubCat.addItemDecoration(divider);
        recyclerSubCat.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerSubCat.setAdapter(new SubCatAdapter(subCats,this,this));
    }

    @Override
    public void onError(String message) {
        progress.dismiss();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
