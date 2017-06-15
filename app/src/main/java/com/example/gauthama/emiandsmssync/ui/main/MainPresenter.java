package com.example.gauthama.emiandsmssync.ui.main;

import com.example.gauthama.emiandsmssync.ui.base.MVPView;
import com.example.gauthama.emiandsmssync.ui.base.Presenter;
import com.example.gauthama.emiandsmssync.Utils;


public class MainPresenter implements Presenter<MVPView> {

    private MainMVPView mainMVPView;

    private double emi;
    private double interstable;
    private double payment;

    @Override
    public void attachView(MVPView mvpView) {
       mainMVPView = (MainMVPView)mvpView;
    }

    @Override
    public void detachView() {


    }



    public void calculateResults(double loan, double rate, double tenure){

       double months;
       months = tenure * 12;
       double interestMonth = rate/1200;
       double loanRupees = loan*100000;
       emi = Utils.getEmi(loanRupees, interestMonth, months);
       interstable = Utils.getTotalInterest(loanRupees, emi, months);
        payment = Utils.getAmount(months, emi);

        mainMVPView.displayResults((long)Math.round(emi),
                (long)Math.round(interstable), (long)Math.round(payment));
    }

    public void loanChanged(double loans){
        mainMVPView.updateLoan(loans);
    }

    public void tenureChanged(double tenure){
        mainMVPView.updateTenure(tenure);
    }

    public void interestChanged(double interest){
        mainMVPView.updateInterest(interest);
    }
}
