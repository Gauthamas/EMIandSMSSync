package com.example.gauthama.emiandsmssync.ui.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.gauthama.emiandsmssync.R;
import com.example.gauthama.emiandsmssync.service.SMSSyncIntentService;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainMVPView {

    @BindView(R.id.emi)
    TextView emiTextview;

    @BindView(R.id.interest_payable)
    TextView interestPayableTextView;

    @BindView(R.id.payment)
    TextView paymentTextView;

    @BindView(R.id.interestSeekBar)
    SeekBar interestBar;

    @BindView(R.id.interestTextView)
    TextView interestTextView;

    @BindView(R.id.loanSeekBar)
    SeekBar loanBar;

    @BindView(R.id.loanTextView)
    TextView loanTextView;

    @BindView(R.id.tenureSeekBar)
    SeekBar tenureBar;

    @BindView(R.id.tenureTextView)
    TextView tenureTextView;

    @BindView(R.id.piechart)
    PieChart pieChart;

    MainPresenter mMainPresenter;

    @BindString(R.string.year_short_form)
    String yearShortForm;

    @BindString(R.string.lakh_short_form)
    String lakhShortForm;

    @BindString(R.string.percentage_short_form)
    String percentShortForm;

    @BindString(R.string.total_interest)
    String totalInterest;

    @BindString(R.string.principal_amount)
    String principalAmount;

    @BindString(R.string.piechart_summary)
    String piechartSummary;

    @BindString(R.string.rupees)
    String rupees;

    ArrayList<String> xVals = new ArrayList<String>();

    double loan = 200;
    double rate = 20.0, tenure = 30;

    private final int MY_PERMISSIONS_REQUEST_READ_SMS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mMainPresenter = new MainPresenter();
        mMainPresenter.attachView(this);
        mMainPresenter.calculateResults(loan, rate, tenure);

        interestBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rate = 5+progress;
                mMainPresenter.interestChanged(rate);
                mMainPresenter.calculateResults(loan, rate, tenure);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        loanBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                loan = progress;
                mMainPresenter.loanChanged(loan);
                mMainPresenter.calculateResults(loan, rate, tenure);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        tenureBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tenure = 1+progress;
                mMainPresenter.tenureChanged(tenure);
                mMainPresenter.calculateResults(loan, rate, tenure);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        checkSMSPermissions(this);



    }

    private void checkSMSPermissions(Context context) {

        if (android.os.Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    Manifest.permission.READ_SMS)) {

                Log.d(MainActivity.class.getName(), "if rationale required");

            } else {

                Log.d(MainActivity.class.getName(), "requesting permissions");
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.READ_SMS},
                        MY_PERMISSIONS_REQUEST_READ_SMS);
            }
        } else {
            startService(new Intent(this, SMSSyncIntentService.class));
        }


    }

    @Override
    public void displayResults(long emi, long interestPayable, long payment) {


        emiTextview.setText(rupees+Long.toString(emi));
        interestPayableTextView.setText(rupees+Long.toString(interestPayable));
        paymentTextView.setText(rupees+Long.toString(payment));

        if(payment!=0L){
            double principalpercent = loan*100000/(double)payment;
            double interestpercent = (double)interestPayable/(double)payment;


            pieChart.setUsePercentValues(true);
            ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
            yvalues.add(new PieEntry((float)(principalpercent*100), principalAmount));
            yvalues.add(new PieEntry((float)(interestpercent*100), totalInterest));

            PieDataSet dataSet = new PieDataSet(yvalues, piechartSummary);

            PieData data = new PieData(dataSet);

            data.setValueFormatter(new PercentFormatter());
            pieChart.setData(data);
            pieChart.setContentDescription("");

            pieChart.setEntryLabelColor(android.R.color.black);

            Description description = new Description();
            description.setText(piechartSummary);
            pieChart.setDescription(description);

            dataSet.setColors(ColorTemplate.JOYFUL_COLORS);


            pieChart.animateXY(1400, 1400);
        }
        else {
            pieChart.clear();
        }



    }

    @Override
    public void updateLoan(double loans) {
        loanTextView.setText(Double.toString(loans)+lakhShortForm);
    }

    @Override
    public void updateInterest(double interest) {
        interestTextView.setText(Double.toString(interest)+percentShortForm);
    }

    @Override
    public void updateTenure(double tenure) {
        tenureTextView.setText(Double.toString(tenure)+yearShortForm);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.d(MainActivity.class.getName(), "permission granted");
                    startService(new Intent(this, SMSSyncIntentService.class));

                } else {
                    Log.w(MainActivity.class.getName(), "permission denied!!!");

                }
                return;
            }

        }
    }
}
