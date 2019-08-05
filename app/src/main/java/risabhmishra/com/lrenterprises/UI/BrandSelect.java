package risabhmishra.com.lrenterprises.UI;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dpizarro.uipicker.library.picker.PickerUI;
import com.dpizarro.uipicker.library.picker.PickerUISettings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import risabhmishra.com.lrenterprises.Adapter.BrandAdapter;
import risabhmishra.com.lrenterprises.MainActivity;
import risabhmishra.com.lrenterprises.Model.Company;
import risabhmishra.com.lrenterprises.R;
import risabhmishra.com.lrenterprises.Utils.Controller;

public class BrandSelect extends AppCompatActivity {

    private DatabaseReference myRef;
    private ArrayList<String> options;
    RecyclerView recyclerView;
    BrandAdapter brandAdapter;
    private PickerUI mPickerUI;
    PickerUISettings pickerUISettings;
    List<Company> brandlist;
    public static TextView tv_cart_badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_select);

        myRef = FirebaseDatabase.getInstance().getReference().child("ProductsData");

        mPickerUI = findViewById(R.id.picker_ui_view);
        options = new ArrayList<>();
        brandlist = new ArrayList<>();

        pickerUISettings = new PickerUISettings.Builder()
                .withItems(options)
                .withItemsClickables(true)
                .withAutoDismiss(false)
                .withUseBlur(false)
                .build();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    String brand = ds.getKey();
                    options.add(brand);
                }
                mPickerUI.setSettings(pickerUISettings);
                mPickerUI.slide(options.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(BrandSelect.this,"Database Error.. Try Again!",Toast.LENGTH_LONG).show();
            }
        });

        mPickerUI.setOnClickItemPickerUIListener(new PickerUI.PickerUIItemClickListener() {
            @Override
            public void onItemClickPickerUI(int which, int position, String valueResult) {
                Toast.makeText(BrandSelect.this, valueResult, Toast.LENGTH_SHORT).show();
                 RequestBrand(valueResult);

            }
        });


        recyclerView = findViewById(R.id.rview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        MenuItem menuCartItem = menu.findItem(R.id.action_cart);
        tv_cart_badge = menuCartItem.getActionView().findViewById(R.id.badge_notification_1);
        Button notify_badge_button = menuCartItem.getActionView().findViewById(R.id.button1_badge);
        notify_badge_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BrandSelect.this,CartActivity.class));
            }
        });

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)menuItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                brandAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    public void RequestBrand(String name) {

        myRef.child(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                brandlist.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Company brand = ds.getValue(Company.class);
                    brandlist.add(brand);
                }
                brandAdapter = new BrandAdapter(brandlist, BrandSelect.this);
                recyclerView.setAdapter(brandAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(BrandSelect.this, "Database Error.. Try Again!", Toast.LENGTH_LONG).show();
            }
        });

    }
}
