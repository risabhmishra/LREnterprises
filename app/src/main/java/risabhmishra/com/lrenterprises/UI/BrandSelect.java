package risabhmishra.com.lrenterprises.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import risabhmishra.com.lrenterprises.R;

public class BrandSelect extends AppCompatActivity {

    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_select);

        myRef = FirebaseDatabase.getInstance().getReference().child("ProductsData");

    }
}
