package com.geekmk.lmg.catalog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.geekmk.lmg.R;
import com.geekmk.lmg.utils.ActivityUtils;

import butterknife.ButterKnife;

public class CatalogActivity extends AppCompatActivity {



    private CatalogPresenter presenter;

    public static final String SHOULD_LOAD_DATA = "SHOULD_LOAD_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        ButterKnife.bind(this);

        CatalogFragment catalogFragment = (CatalogFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if(catalogFragment == null){
            catalogFragment = CatalogFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),catalogFragment,R.id.contentFrame);
        }

        boolean shouldReloadData = true;

        // Prevent the presenter from loading data from the repository if this is a config change.
        if (savedInstanceState != null) {
            // Data might not have loaded when the config change happen, so we saved the state.
            shouldReloadData = savedInstanceState.getBoolean(SHOULD_LOAD_DATA);
        }

        presenter = new CatalogPresenter(catalogFragment,shouldReloadData);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save the state so that next time we know if we need to refresh data.
        outState.putBoolean(SHOULD_LOAD_DATA, presenter.isDataMissing());
        super.onSaveInstanceState(outState);
    }
}
