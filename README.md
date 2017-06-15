# EMIandSMSSync

## Features of EMIandSMSSync

### EMI calculation

-Given loan amount, interest rate and tenure, calculate emi, total interst payable, total paymen
-Also produces a pie chart of principal amount vs total interest
-The loan amount, interest rate and tenure are input in terms of a seek bar which has a
1 Lakh Rs unit for amount,
1% unit for interest rate,
and 1yr unit for tenure. This can be minimized even further as per the requirement.
-Seekbar has a predefined range as well as per emicalculator.net website.

### SMS syncing

-For syncing up smses with fake json server https://boiling-eyrie-75655.herokuapp.com .
-The server goes down after 30 minutes of inactivity and data will be lost.
-SMS syncing is easier in pre 23 android api because there is no runtime permissions required.
-For post 23 apis,we have to explicitly start the activity and give runtime permissions so that it will work.
-SMS will sync 
   -Whenever we explicitly launch application
   -Also when we receive an sms, all the previous pending sms(excluding the current one) will be synced.
   -Last synced message is stored locally so that it will be remembered.
-This model of syncing was used because it is easier to test,
        -Other options would be
        -Running the sync service periodically
        -Running the sync service whenever a certain number of messages are pending to be synced(say 10 msgs).(considered more efficient)
               
        
        

