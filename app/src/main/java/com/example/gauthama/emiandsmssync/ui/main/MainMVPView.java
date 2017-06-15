package com.example.gauthama.emiandsmssync.ui.main;

import com.example.gauthama.emiandsmssync.ui.base.MVPView;


public interface MainMVPView extends MVPView {

    public void displayResults(long emi, long interestPayable, long payment);

    public void updateLoan(double loans);

    public void updateInterest(double interest);

    public void updateTenure(double tenure);

}
