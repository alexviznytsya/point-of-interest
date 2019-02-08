/**
 * MainActivity.java
 *
 * Project #3, A1
 *
 * Author: Alex Viznytsya
 *
 * CS 478 Software Development for Mobile Platforms
 * Spring 2018, UIC
 *
 * Date: 04/02/2018
 */

package edu.uic.cs478.sp18.project3.a1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Toolbar actionBar;
    private Button showAttractions, showRestaurants;
    private final int PROJECT3_PERMISSION = 1;
    private final String ATTRACTIONS_ACTION = "edu.uic.cs478.sp18.project3.attractions";
    private final String RESTAURANTS_ACTION = "edu.uic.cs478.sp18.project3.restaurants";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add custom action bar:
        this.actionBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(actionBar);

        this.showAttractions = (Button) findViewById(R.id.showAttractionsBtn);
        this.showRestaurants = (Button) findViewById(R.id.showRestaurantsBtn);

        // Set button event listeners:
        this.showAttractions.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "Broadcasting 'attractions' intent to A2",
                        Toast.LENGTH_SHORT).show();
                Intent intent =  new Intent();
                intent.setAction(ATTRACTIONS_ACTION);
                intent.putExtra("activity", "attractions");
                sendBroadcast(intent);
            }
        });

        this.showRestaurants.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                         "Broadcasting 'restaurants' intent to A2",
                               Toast.LENGTH_SHORT).show();
                Intent intent =  new Intent();
                intent.setAction(RESTAURANTS_ACTION);
                intent.putExtra("activity", "restaurants");
                sendBroadcast(intent);
            }
        });

        // Check if required permissions are granted:
        int permissionCheck = ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.project3);
        if(permissionCheck == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.project3},
                    PROJECT3_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PROJECT3_PERMISSION:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(),
                            "Project3 permission has been successfuly granted.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "This application require project3 permission to work correctly.",
                            Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                openAboutDialog();
                return true;
            case R.id.exit:
                finishAndRemoveTask();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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


}
