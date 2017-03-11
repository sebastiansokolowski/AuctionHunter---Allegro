package com.sebastian.sokolowski.auctionhunter.main;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.List;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private MainPresenter mMainPresenter;
    private ListView mDrawerList;
    private ProgressDialog mProgressDialog;
    private DrawerAdapter mDrawerAdapter;
    private MainAdapter mMainAdapter;

    private TextView mTextInfo;
    private SliderRecyclerView mTargetList;
    private Button mButtonSelectTarget;
    private SwipeRefreshLayout mSwipeContainer;
    private DrawerLayout mDrawer;
    private ImageView mLoadingImage;

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

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        mSwipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMainPresenter.refreshTargetItems();
            }
        });

        mMainAdapter = new MainAdapter(this);
        mMainAdapter.setOnClickListener(new MainAdapter.OnClickDrawerItemListener() {
            @Override
            public void onClick(TargetItem targetItem) {
                mMainPresenter.clickTargetItem(targetItem);
            }
        });

        mTargetList = (SliderRecyclerView) findViewById(R.id.rv_target_list);
        mTargetList.setAdapter(mMainAdapter);
        mTargetList.setLayoutManager(new LinearLayoutManager(this));

        mDrawerList = (ListView) findViewById(R.id.left_drawer_lv);
        mDrawerAdapter = new DrawerAdapter(this);
        mDrawerAdapter.setOnClickListener(new DrawerAdapter.OnClickDrawerItemListener() {
            @Override
            public void onClick(Target target, int position) {
                mDrawerList.setItemChecked(position, true);
                mMainPresenter.changeTarget(target);
                closeDrawer();
            }
        });


        mDrawerList.setAdapter(mDrawerAdapter);

        mTextInfo = (TextView) findViewById(R.id.tv_info);
        mLoadingImage = (ImageView) findViewById(R.id.iv_loading);
        mButtonSelectTarget = (Button) findViewById(R.id.btn_select_target);
        mButtonSelectTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer();
            }
        });

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
            closeDrawer();
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
                                        mDrawerList.setItemChecked(-1, true);
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
    public void showNoDataInfo() {
        showLoadingProgress(false);

        mButtonSelectTarget.setVisibility(View.INVISIBLE);
        mTargetList.setVisibility(View.INVISIBLE);
        mTextInfo.setVisibility(View.VISIBLE);
        mSwipeContainer.setVisibility(View.VISIBLE);
        mTextInfo.setText(getString(R.string.main_activity_no_data));
    }

    @Override
    public void showNoTargetInfo() {
        showLoadingProgress(false);

        mButtonSelectTarget.setVisibility(View.INVISIBLE);
        mTargetList.setVisibility(View.INVISIBLE);
        mTextInfo.setVisibility(View.VISIBLE);
        mSwipeContainer.setVisibility(View.INVISIBLE);
        mTextInfo.setText(getString(R.string.main_activity_add_new_target));
    }

    @Override
    public void showSelectTargetButton() {
        showLoadingProgress(false);

        mButtonSelectTarget.setVisibility(View.VISIBLE);
        mTargetList.setVisibility(View.INVISIBLE);
        mTextInfo.setVisibility(View.INVISIBLE);
        mSwipeContainer.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showErrorToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoadingProgress(boolean loading) {
        if (loading) {
            mLoadingImage.setVisibility(View.VISIBLE);
            mButtonSelectTarget.setVisibility(View.INVISIBLE);
            mTargetList.setVisibility(View.INVISIBLE);
            mTextInfo.setVisibility(View.INVISIBLE);
            mSwipeContainer.setVisibility(View.INVISIBLE);

            mLoadingImage.startAnimation(com.sebastian.sokolowski.auctionhunter.utils.AnimationUtils.createRotateAnimation(this));
        } else {
            mLoadingImage.setVisibility(View.INVISIBLE);
            mLoadingImage.clearAnimation();
        }
    }

    @Override
    public void showTargets(List<Target> targets) {

    }


    @Override
    public void showErrorDialog() {

    }

    @Override
    public void showTargetItem(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
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

    @Override
    public void setDrawerAdapterList(List<Target> list) {
        mDrawerAdapter.setItems(list);
    }

    @Override
    public void setMainAdapterList(List<TargetItem> list) {
        mButtonSelectTarget.setVisibility(View.INVISIBLE);
        mTextInfo.setVisibility(View.INVISIBLE);
        mTargetList.setVisibility(View.VISIBLE);
        mSwipeContainer.setVisibility(View.VISIBLE);
        mSwipeContainer.setRefreshing(false);
        mMainAdapter.setItems(list);
    }

    public void showSettings(View view) {
        closeDrawer();

        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void openDrawer() {
        mDrawer.openDrawer(Gravity.START);
    }

    private void closeDrawer() {
        mDrawer.closeDrawers();
    }
}
