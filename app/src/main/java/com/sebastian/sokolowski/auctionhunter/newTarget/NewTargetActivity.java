package com.sebastian.sokolowski.auctionhunter.newTarget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sebastian.sokolowski.auctionhunter.R;
import com.sebastian.sokolowski.auctionhunter.database.models.Target;

import java.util.List;

public class NewTargetActivity extends AppCompatActivity implements NewTargetContract.View {

    private NewTargetContract.Presenter mNewTargetPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_target);

        mNewTargetPresenter = new NewTargetPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void showAddTarget() {

    }

    @Override
    public void showViewType(Type type) {

    }

    @Override
    public void showLoadingProgress() {

    }

    @Override
    public void showNoTarget() {

    }

    @Override
    public void showTargets(List<Target> targets) {

    }

    @Override
    public void showErrorDialog() {

    }

    public void save(View view) {
    }

    public void add_category(View view) {
    }
}
