package com.sebastian.sokolowski.auctionhunter.newTarget;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sebastian.sokolowski.auctionhunter.R;
import com.sebastian.sokolowski.auctionhunter.database.entity.Target;
import com.sebastian.sokolowski.auctionhunter.main.MainContract;
import com.sebastian.sokolowski.auctionhunter.main.MainPresenter;
import com.sebastian.sokolowski.auctionhunter.main.adapter.MainAdapter;

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
        getMenuInflater().inflate(R.menu.main, menu);
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
