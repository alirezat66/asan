package ir.greencode.advicelawAndroid.vote;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.greencode.advicelawAndroid.R;
import ir.greencode.advicelawAndroid.utils.BaseActivity;

public class VoteActivity extends BaseActivity {
    @BindView(R.id.toggle_good_moshavere)
    ToggleButton toggleGoodMoshavere;
    @BindView(R.id.toggle_normal_moshavere)
    ToggleButton toggleNormalMoshavere;
    @BindView(R.id.toggle_bad_moshavere)
    ToggleButton toggleBadMoshavere;
    @BindView(R.id.toggle_good_problem)
    ToggleButton toggleGoodProblem;
    @BindView(R.id.toggle_normal_problem)
    ToggleButton toggleNormalProblem;
    @BindView(R.id.toggle_bad_problem)
    ToggleButton toggleBadProblem;
    @BindView(R.id.toggle_good_call)
    ToggleButton toggleGoodCall;
    @BindView(R.id.toggle_normal_call)
    ToggleButton toggleNormalCall;
    @BindView(R.id.toggle_bad_call)
    ToggleButton toggleBadCall;
    @BindView(R.id.edt_description)
    EditText edtDescription;
    @BindView(R.id.btn_insert)
    Button btnInsert;
    @BindView(R.id.btn_call)
    Button btnCall;
    @BindView(R.id.bottomLayout)
    LinearLayout bottomLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.toggle_good_moshavere, R.id.toggle_normal_moshavere, R.id.toggle_bad_moshavere, R.id.toggle_good_problem, R.id.toggle_normal_problem, R.id.toggle_bad_problem, R.id.toggle_good_call, R.id.toggle_normal_call, R.id.toggle_bad_call, R.id.btn_insert, R.id.btn_call})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toggle_good_moshavere:
                manageMoshavere(3);
                break;
            case R.id.toggle_normal_moshavere:
                manageMoshavere(2);
                break;
            case R.id.toggle_bad_moshavere:
                manageMoshavere(1);
                break;
            case R.id.toggle_good_problem:
                manageProblem(1);
                break;
            case R.id.toggle_normal_problem:
                manageProblem(2);
                break;
            case R.id.toggle_bad_problem:
                manageProblem(3);

                break;
            case R.id.toggle_good_call:
                manageCall(1);
                break;
            case R.id.toggle_normal_call:
                manageCall(2);

                break;
            case R.id.toggle_bad_call:
                manageCall(3);

                break;
            case R.id.btn_insert:
                Intent intent  = new Intent(this,VoteAcceptedActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_call:
                break;
        }
    }

    private void manageCall(int checked) {
        boolean bad = false;
        boolean normal = false;
        boolean good = false;
        if(checked==1) {
            bad = true;
        }else if(checked == 2){
            normal = true;
        }else {
            good = true;
        }
        toggleBadCall.setChecked(bad);
        toggleNormalCall.setChecked(normal);
        toggleGoodCall.setChecked(good);
    }

    private void manageProblem(int checked) {
        boolean bad = false;
        boolean normal = false;
        boolean good = false;
        if(checked==1) {
            bad = true;
        }else if(checked == 2){
            normal = true;
        }else {
            good = true;
        }
        toggleBadProblem.setChecked(bad);
        toggleNormalProblem.setChecked(normal);
        toggleGoodProblem.setChecked(good);


    }

    private void manageMoshavere(int checked) {
        boolean bad = false;
        boolean normal = false;
        boolean good = false;
        if(checked==1) {
            bad = true;
        }else if(checked == 2){
            normal = true;
        }else {
            good = true;
        }
        toggleBadMoshavere.setChecked(bad);
        toggleNormalMoshavere.setChecked(normal);
        toggleGoodMoshavere.setChecked(good);

    }
}
