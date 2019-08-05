package risabhmishra.com.lrenterprises;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.MenuItem;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import risabhmishra.com.lrenterprises.Fragments.AccountsFragment;
import risabhmishra.com.lrenterprises.Fragments.DeliveredFragment;
import risabhmishra.com.lrenterprises.Fragments.NewOrderFragment;
import risabhmishra.com.lrenterprises.Fragments.UpcomingFragment;
import risabhmishra.com.lrenterprises.Fragments.UpdateFragment;

public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;
BottomNavigationView bottomNavigationView;
    private int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
checkAndRequestPermissions();
FirebaseApp.initializeApp(MainActivity.this);
        toolbar = getSupportActionBar();

        // load the store fragment by default
        toolbar.setTitle("New Order");
        loadFragment(new NewOrderFragment());

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.navigation_upcoming:
                        toolbar.setTitle("Upcoming Orders");
                        fragment = new UpcomingFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_new:
                        toolbar.setTitle("New Order");
                        fragment = new NewOrderFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_delivered:
                        toolbar.setTitle("Delivered Orders");
                        fragment = new DeliveredFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_account:
                        toolbar.setTitle("My Account");
                        fragment = new AccountsFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_update:
                        toolbar.setTitle("Update");
                        fragment = new UpdateFragment();
                        loadFragment(fragment);
                        return true;
                }

                return false;
            }
        });
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void checkAndRequestPermissions() {

        int writeExternalStorage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        int readExternalStorage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (writeExternalStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (readExternalStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
        }
    }

}
