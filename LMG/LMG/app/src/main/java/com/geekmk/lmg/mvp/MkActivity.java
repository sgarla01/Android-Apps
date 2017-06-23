package com.geekmk.lmg.mvp;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Mani on 09/06/17.
 */

public abstract class MkActivity extends AppCompatActivity {

    public abstract void initListener();

    public abstract void initComponents();

    public abstract void initializeViewBean();

    public abstract void initPresenter();

}
