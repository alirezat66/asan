package ir.greencode.advicelawAndroid.insertrequest;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.greencode.advicelawAndroid.R;


/**
 * Created by alireza on 5/18/18.
 */

public class DocNameDialog extends Dialog {

    DocNameInterface myInterface;
    Context context;
    @BindView(R.id.edt_doc_name)
    EditText edtDocName;
    @BindView(R.id.btn_acc)
    Button btnAcc;
    @BindView(R.id.btn_cancel)
    Button btnCancel;


    public DocNameDialog(Context context) {
        super(context);
        this.context = context;
    }

    public void setListener(DocNameInterface listener) {
        this.myInterface = listener;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        View view = View.inflate(context, R.layout.dialog_name_of_doc, null);
        ButterKnife.bind(this, view);

        setContentView(view);
        setCancelable(true);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = -1;
        getWindow().setAttributes(params);



    }

    @Override
    public void onBackPressed() {
        myInterface.onRejected();
        super.onBackPressed();
    }

    @OnClick({R.id.btn_acc, R.id.btn_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_acc:
                myInterface.onSuccess(edtDocName.getText().toString());
                break;
            case R.id.btn_cancel:
                myInterface.onRejected();
                break;
        }
    }
}
