package com.example.gauthama.emiandsmssync.ui.base;

import com.example.gauthama.emiandsmssync.ui.base.MVPView;

public interface Presenter<V extends MVPView> {
    void attachView(V mvpView);

    void detachView();

}
