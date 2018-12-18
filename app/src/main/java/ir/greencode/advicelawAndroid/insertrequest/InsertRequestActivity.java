package ir.greencode.advicelawAndroid.insertrequest;

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
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.greencode.advicelawAndroid.R;
import ir.greencode.advicelawAndroid.choselawyer.DocumentsAdapter;
import ir.greencode.advicelawAndroid.dialogs.QuestionDialog;
import ir.greencode.advicelawAndroid.dialogs.QuestionListener;
import ir.greencode.advicelawAndroid.main.PhotoActivity;
import ir.greencode.advicelawAndroid.objects.DocumentImg;
import ir.greencode.advicelawAndroid.retrofit.reqobject.InsertReq;
import ir.greencode.advicelawAndroid.utils.BaseActivity;
import ir.greencode.advicelawAndroid.utils.Constants;
import ir.greencode.advicelawAndroid.utils.DividerDecorator;
import ir.greencode.advicelawAndroid.utils.PreferencesData;
import ir.greencode.advicelawAndroid.utils.Utility;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class InsertRequestActivity extends BaseActivity implements DocumentsAdapter.onItemClick , InsertRequestInterface {
    KProgressHUD kProgressHUD;

    int indexOfLastLetter = 0;
    String[] mPermission = {
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
    };
    private static final int REQUEST_CODE_PERMISSION = 2;
    ChosePhotoTakerDialog dialog;
    List<String> myDocs = new ArrayList<>();

    @BindView(R.id.imgLogo)
    ImageView imgLogo;
    @BindView(R.id.txt_cat_name)
    TextView txtCatName;
    @BindView(R.id.txt_sub_cat_name)
    TextView txtSubCatName;
    @BindView(R.id.txt_description)
    EditText txtDescription;
    @BindView(R.id.docs_recycler)
    RecyclerView docsRecycler;
    @BindView(R.id.btn_load_doc)
    Button btnLoadDoc;
    @BindView(R.id.btn_send)
    Button btnSend;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    String b64Image;
    @BindView(R.id.txtCounter)
    TextView txtCounter;
    int size;
    DocumentsAdapter adapter ;
    List<DocumentImg> docs = new ArrayList<>();
    InsertRequestPresenter presenter;
    int userId ;
    int catId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_request);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        presenter = new InsertRequestPresenter(this);
        userId = PreferencesData.getInt(Constants.Pref_USER_ID,this);
        if (bundle != null) {
            txtCatName.setText(bundle.getString("catName"));
            txtSubCatName.setText(bundle.getString("subCatName"));
            catId = bundle.getInt("catId");

        }
        String catImg = PreferencesData.getString("catImg",this);
       if(!catImg.equals("")) {
            Bitmap bitmap = Utility.getBitmapFromBase(catImg);
            imgLogo.setImageBitmap(bitmap);
        }
        myDocs  = new ArrayList<>();
        docsRecycler.setLayoutManager(new LinearLayoutManager(InsertRequestActivity.this,
                LinearLayoutManager.VERTICAL,false));
        adapter = new DocumentsAdapter(docs,this,this,true);
        DividerItemDecoration decoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        docsRecycler.addItemDecoration(decoration);

        docsRecycler.setAdapter(adapter);
        txtDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                txtCounter.setText(countWords(txtDescription.getText().toString())+" از ۷۰");
                if (count == 0 && countWords(txtDescription.getText().toString()) >= 70) {
                    setCharLimit(txtDescription, txtDescription.getText().length());
                } else {
                    removeFilter(txtDescription);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private int countWords(String s) {
        String trim = s.trim();
        if (trim.isEmpty())
            return 0;
        return trim.split("\\s+").length; // separate string around spaces
    }

    private InputFilter filter;

    private void setCharLimit(EditText et, int max) {
        filter = new InputFilter.LengthFilter(max);
        et.setFilters(new InputFilter[] { filter });
    }

    private void removeFilter(EditText et) {
        if (filter != null) {
            et.setFilters(new InputFilter[0]);
            filter = null;
        }
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                if (ActivityCompat.checkSelfPermission(this, mPermission[0])
                        != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(this, mPermission[1])
                                != PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.checkSelfPermission(this, mPermission[2])
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            mPermission, REQUEST_CODE_PERMISSION);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePicked(final File imageFile, EasyImage.ImageSource source, int type) {
                showWaiting();

                new ConvertToB64().execute(imageFile);

                imgLogo.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(DocumentImg item) {
            Intent intent = new Intent(this, PhotoActivity.class);
            PreferencesData.saveString("myb64",item.getDocSource(),this);
            startActivity(intent);
    }

    @Override
    public void onDelete(final DocumentImg item) {

        final QuestionDialog dialog = new QuestionDialog(this,"","آیا قصد حذف فایل ضمیمه شده را دارید؟");
        dialog.setListener(new QuestionListener() {
            @Override
            public void onReject() {
                dialog.dismiss();
            }

            @Override
            public void onSuccess() {
                adapter.deleteDoc(item);
                btnLoadDoc.setEnabled(true);
                btnLoadDoc.setBackgroundColor(getResources().getColor(R.color.greenBlue));
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    @Override
    public void onSuccessInsert() {
        kProgressHUD.dismiss();
        finish();
    }

    @Override
    public void onFaildInsert(String error) {
        kProgressHUD.dismiss();
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
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
            size = b.length/1024;
            String b64 = Base64.encodeToString(b, Base64.DEFAULT);
            return b64;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            b64Image = result;
            myDocs.add(b64Image);
            disMissWaiting();
            showDialogName();

        }

    }

    private void showDialogName() {
        DocumentImg img = new DocumentImg("",size+"",b64Image);
        adapter.addDoc(img);
        if(adapter.getItemCount()==5){
            btnLoadDoc.setEnabled(false);
            btnLoadDoc.setBackgroundColor(getResources().getColor(R.color.disableGray));
        }
    }

    public void showDialogForImageSelector() {
        dialog = new ChosePhotoTakerDialog(this);
        dialog.setListener(new PhotoChoserInterface() {
            @Override
            public void onSuccess(int type) {

                if (type == 1) {
                    EasyImage.openCamera(InsertRequestActivity.this, 500);
                    //camera chosemn
                } else {
                    EasyImage.openGallery(InsertRequestActivity.this, 600);
                    //gallery chosen
                }
                dialog.dismiss();
            }

            @Override
            public void onRejected() {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
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

    @OnClick({R.id.btn_load_doc, R.id.btn_send, R.id.btn_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_load_doc:
                checkPermission();
                break;
            case R.id.btn_send:
                if(txtDescription.getText().toString().trim().length()>0) {
                   asqQuestion();
                                  }else {
                    txtDescription.setError("لطفا توضیحات را پر کنید");
                }
                break;
            case R.id.btn_cancel:
                cancelQuestion();
                break;
        }
    }
    private void cancelQuestion() {
        final QuestionDialog dialog = new QuestionDialog(this,"","آیا قصد حذف این درخواست را دارید؟");
        dialog.setListener(new QuestionListener() {
            @Override
            public void onReject() {
                dialog.dismiss();
            }

            @Override
            public void onSuccess() {
                finish();
            }
        });
        dialog.show();
    }
    private void asqQuestion() {
        final QuestionDialog dialog = new QuestionDialog(this,"","از ارسال درخواست اطمینان دارید؟");
        dialog.setListener(new QuestionListener() {
            @Override
            public void onReject() {
                dialog.dismiss();
            }

            @Override
            public void onSuccess() {
                kProgressHUD = Utility.makeWaiting(InsertRequestActivity.this);
                kProgressHUD.show();
                presenter.insert(new InsertReq(Utility.getToken(), userId, catId, myDocs, txtDescription.getText().toString()));

                dialog.dismiss();
            }
        });
        dialog.show();
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
}
