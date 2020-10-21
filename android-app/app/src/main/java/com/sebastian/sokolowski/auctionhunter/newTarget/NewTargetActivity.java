package com.sebastian.sokolowski.auctionhunter.newTarget;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;

import com.sebastian.sokolowski.auctionhunter.R;

public class NewTargetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);

        if (savedInstanceState == null) {
            NewTargetFragment newTargetFragment = NewTargetFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, newTargetFragment)
                    .addToBackStack(NewTargetFragment.class.getSimpleName())
                    .commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        backOrNavigateUpClicked();
        return true;
    }

    @Override
    public void onBackPressed() {
        backOrNavigateUpClicked();
    }

    private void backOrNavigateUpClicked() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int backCount = fragmentManager.getBackStackEntryCount();

        if (backCount > 1) {
            fragmentManager.popBackStack();
        } else {
            finish();
        }
    }
}
