package ir.greencode.advicelawAndroid.cats;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.greencode.advicelawAndroid.R;
import ir.greencode.advicelawAndroid.objects.Category;
import ir.greencode.advicelawAndroid.retrofit.reqobject.CatReq;
import ir.greencode.advicelawAndroid.utils.BaseActivity;
import ir.greencode.advicelawAndroid.utils.Constants;
import ir.greencode.advicelawAndroid.utils.PreferencesData;
import ir.greencode.advicelawAndroid.utils.Utility;

public class CategoryActivity extends BaseActivity implements CatAdapter.onItemClick, CatInterface {
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.recycler_cat)
    RecyclerView recyclerCat;

    CatPresenter presenter;
    KProgressHUD progress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        progress = Utility.makeWaiting(Constants.WaitingTitle,Constants.WaitingDescNormal,this);
        progress.show();
        presenter = new CatPresenter(this);
        String token = PreferencesData.getString(Constants.PREF_TOKEN,this);
        String id ="";
        long timeStamp = 0;
        presenter.getCats(new CatReq(token,timeStamp,""));


    }

    @Override
    public void onClick(Category item) {
        Intent intent = new Intent(CategoryActivity.this, SubCatActivity.class);
        intent.putExtra("catId", item.getCatId());
        intent.putExtra("catName", item.getCatName());
        PreferencesData.saveString("catImg",item.getCatImg(),this);

        startActivity(intent);
        finish();
    }

    @Override
    public void onReadyCats(List<Category> categories) {
        progress.dismiss();
        recyclerCat.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerCat.setAdapter(new CatAdapter(categories, this, this));
    }

    @Override
    public void onError(String str) {
        progress.dismiss();
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.imgBack)
    public void onClick() {
        finish();
    }
}
