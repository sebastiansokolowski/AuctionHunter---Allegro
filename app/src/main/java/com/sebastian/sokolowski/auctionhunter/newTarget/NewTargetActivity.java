package com.sebastian.sokolowski.auctionhunter.newTarget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.sebastian.sokolowski.auctionhunter.R;
import com.sebastian.sokolowski.auctionhunter.database.entity.Target;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
}
