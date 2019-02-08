/**
 * Attractions.java
 *
 * Project #3, A2
 *
 * Author: Alex Viznytsya
 *
 * CS 478 Software Development for Mobile Platforms
 * Spring 2018, UIC
 *
 * Date: 04/02/2018
 */

package edu.uic.cs478.sp18.project3.a2;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;


public class Attractions extends AppCompatActivity implements TitleFragment.ListSelectionListener {
    private Toolbar actionBar;
    private String[] titleArray;
    private String[] urlArray;
    private FragmentManager fragmentManager;
    private FrameLayout titleFrameLayout, browserFrameLayout;
    private BrowserFragment browserFragment;
    private TitleFragment titleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions);

        // Set action bar:
        this.actionBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(actionBar);
        getSupportActionBar().setTitle("A2, Attractions");

        // Set resources:
        titleArray = getResources().getStringArray(R.array.attraction_titles);
        urlArray = getResources().getStringArray(R.array.attraction_urls);

        titleFrameLayout = (FrameLayout) findViewById(R.id.title_fragment_container);
        browserFrameLayout = (FrameLayout) findViewById(R.id.browser_fragment_container);

        fragmentManager = getFragmentManager();

        // Get existing fragments:
        titleFragment = (TitleFragment) fragmentManager.findFragmentByTag("TitleFragment");
        browserFragment = (BrowserFragment) fragmentManager.findFragmentByTag("BrowserFragment");

        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {

            @Override
            public void onBackStackChanged() {
                setLayout();
            }
        });

        // Re-attach fragments in case of configuration changes:
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(titleFragment == null) {
            titleFragment = new TitleFragment();
        }
        if(browserFragment == null){
            browserFragment = new BrowserFragment();
        } else {
            if(!browserFragment.isAdded()) {
                fragmentTransaction.replace(R.id.browser_fragment_container, browserFragment, "BrowserFragment");
                fragmentTransaction.addToBackStack(null);
            } else {
                setLayout();
            }
        }
        fragmentTransaction.replace(R.id.title_fragment_container, titleFragment, "TitleFragment");
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.attractions_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_restaurants:
                startActivity(new Intent(getApplicationContext(), Restaurants.class));
                return true;
            case R.id.menu_about:
                openAboutDialog();
                return true;
            case R.id.menu_exit:
                finishAndRemoveTask();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListSelection(int index) {
        if (!browserFragment.isAdded()) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.browser_fragment_container, browserFragment, "BrowserFragment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            fragmentManager.executePendingTransactions();

        }
        browserFragment.loadUrl(urlArray[index]);
    }

    @Override
    public String[] getTitleArray() {
        return this.titleArray;
    }

    private void openAboutDialog() {
        final View dialogLayout = getLayoutInflater().inflate(R.layout.dialog_about, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogLayout);
        final AlertDialog dialogAbout = dialogBuilder.create();

        // Add "Cancel" button listener
        ((Button)dialogLayout.findViewById(R.id.dialogClose)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAbout.dismiss();
            }
        });

        dialogAbout.show();
    }


    private void setLayout() {
        Configuration config = getResources().getConfiguration();
        if( config.orientation == config.ORIENTATION_PORTRAIT) {
            if(!browserFragment.isAdded()) {
                titleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
                browserFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT));
            } else {
                titleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT));
                browserFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            }
        } else {
            if(!browserFragment.isAdded()) {
                titleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
                browserFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT));
            } else {
                titleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 1f));
                browserFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 2f));
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
