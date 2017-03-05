package com.sebastian.sokolowski.auctionhunter.main;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.sebastian.sokolowski.auctionhunter.R;
import com.sebastian.sokolowski.auctionhunter.database.models.Target;
import com.sebastian.sokolowski.auctionhunter.database.models.TargetItem;
import com.sebastian.sokolowski.auctionhunter.main.adapter.DrawerAdapter;
import com.sebastian.sokolowski.auctionhunter.main.adapter.MainAdapter;
import com.sebastian.sokolowski.auctionhunter.main.views.SliderRecyclerView;
import com.sebastian.sokolowski.auctionhunter.newTarget.NewTargetActivity;
import com.sebastian.sokolowski.auctionhunter.settings.SettingsActivity;
import com.sebastian.sokolowski.auctionhunter.soap.request.SortOrderEnum;
import com.sebastian.sokolowski.auctionhunter.soap.request.SortTypeEnum;
import com.sebastian.sokolowski.auctionhunter.utils.DialogHelper;

import java.util.ArrayList;
import java.util.List;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private MainPresenter mMainPresenter;
    private ListView mDrawerList;
    private SliderRecyclerView mTargetList;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainPresenter.addNewTarget();
            }
        });

        mTargetList = (SliderRecyclerView) findViewById(R.id.rv_target_list);
        mTargetList.setAdapter(new MainAdapter(this, new ArrayList<TargetItem>()));
        mTargetList.setLayoutManager(new LinearLayoutManager(this));

        mDrawerList = (ListView) findViewById(R.id.left_drawer_lv);
        mDrawerList.setAdapter(new DrawerAdapter(this, mMainPresenter));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mMainPresenter = new MainPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mMainPresenter.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.target_sort_type:
                DialogHelper.changeSortTypeDialog(this, new DialogHelper.OnChangeSortType() {
                    @Override
                    public void onChangeSortType(SortTypeEnum sortTypeEnum, SortOrderEnum sortOrderEnum) {
                        mMainPresenter.changeSortType(sortTypeEnum);
                        mMainPresenter.changeSortOrder(sortOrderEnum);
                    }
                });
                break;
            case R.id.target_view_type:
                break;
            case R.id.add_target:
                mMainPresenter.addNewTarget();
                break;
            case R.id.edit_target:
                mMainPresenter.editTarget();
                break;
            case R.id.delete_target:
                DialogHelper.deleteTargetDialog(this, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case BUTTON_POSITIVE:
                                        mMainPresenter.deleteTarget();
                                        break;
                                    case BUTTON_NEGATIVE:
                                        break;
                                }
                            }
                        }
                );
                break;
        }

        return true;
    }

    @Override
    public void showAddTarget() {
        Intent intent = new Intent(this, NewTargetActivity.class);
        startActivity(intent);
    }

    @Override
    public void showViewType(Type type) {

    }

    @Override
    public void showLoadingProgressBar() {

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

    @Override
    public void showProgressDialog(final int max) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog = DialogHelper.progressDialog(MainActivity.this, max, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
            }
        });

    }


    @Override
    public void showErrorProgressDialog(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgressDialog != null) {
                    mProgressDialog.setMessage(message);
                    mProgressDialog = null;
                }
            }
        });
    }

    @Override
    public void incrementProgressDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgressDialog != null) {
                    mProgressDialog.incrementProgressBy(1);
                    if (mProgressDialog.getProgress() == mProgressDialog.getMax()) {
                        mProgressDialog.cancel();
                    }
                }
            }
        });
    }

    public void showSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
