package ir.greencode.advicelawAndroid.dialogs;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import ir.greencode.advicelawAndroid.R;
import ir.greencode.advicelawAndroid.database.Profile;
import ir.greencode.advicelawAndroid.main.MenuAdapter;
import ir.greencode.advicelawAndroid.objects.MenuItem;
import ir.greencode.advicelawAndroid.utils.Utility;

public class MenuDialog extends BottomSheetDialog implements MenuAdapter.onItemClick {
    MenuDialogInterface myInterface;

    Context context;
    @BindView(R.id.img_profile)
    CircleImageView imgProfile;
    @BindView(R.id.txt_User_Name)
    TextView txtUserName;
    @BindView(R.id.menu_recycle)
    RecyclerView menuRecycle;
    @BindView(R.id.imgClose)
    AppCompatImageView imgClose;
    ArrayList<MenuItem> list = new ArrayList<>();
    @BindView(R.id.rl_user)
    RelativeLayout rlUser;
    Profile profile;
    @BindView(R.id.version)
    TextView version;

    public MenuDialog(Context context, Profile profile) {
        super(context);
        this.context = context;
        this.profile = profile;
    }

    public void setListener(MenuDialogInterface listener) {
        this.myInterface = listener;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        View view = View.inflate(context, R.layout.dialog_main_menu, null);
        setContentView(view);
        setCancelable(true);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = -1;
        getWindow().setAttributes(params);
        ButterKnife.bind(this, view);
        rlUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myInterface.onProfileClick();
            }
        });

        list.add(new MenuItem(1, "درخواست های من"));
        list.add(new MenuItem(2, "ارتباط با پشتیبانی"));
        list.add(new MenuItem(3, "حریم خصوصی"));
        list.add(new MenuItem(4, "درباره ما"));
        list.add(new MenuItem(5,"خروج"));
        MenuAdapter adapter = new MenuAdapter(list, this, context);
        menuRecycle.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        menuRecycle.setAdapter(adapter);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myInterface.onFinish();
            }
        });
        version.setText("ASAN App v"+Utility.getVersionName(context));
        version.setTypeface(Utility.getEnglishModel(context));

        if (profile != null) {
            if (!(profile.getFName() + profile.getLName()).equals("")) {
                txtUserName.setText(profile.getFName() + " " + profile.getLName());
            } else {
                txtUserName.setText("کاربر گرامی");
            }
            if (profile.getUserImage().equals("")) {
                imgProfile.setImageDrawable(context.getResources().getDrawable(R.drawable.profile_img_place_holder));

            } else {
                imgProfile.setImageBitmap(Utility.getBitmapFromBase(profile.getUserImage()));
            }
        } else {
            imgProfile.setImageDrawable(context.getResources().getDrawable(R.drawable.profile_img_place_holder));
            txtUserName.setText("کاربر گرامی");
        }
    }


    @Override
    public void onClick(int position) {
        myInterface.onChosenItem(list.get(position - 1));
    }
}
