package risabhmishra.com.lrenterprises.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import risabhmishra.com.lrenterprises.MainActivity;
import risabhmishra.com.lrenterprises.R;

public class AddProduct extends AppCompatActivity {
    EditText name,product,type,rate;
    Button submit;
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        FirebaseApp.initializeApp(AddProduct.this);
        db = FirebaseDatabase.getInstance().getReference().child("ProductsData");

        name = findViewById(R.id.et_brand);
        product = findViewById(R.id.et_product);
        type = findViewById(R.id.et_type);
        rate = findViewById(R.id.et_rate);
        submit = findViewById(R.id.bu_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                HashMap<String,String> hs = new HashMap<>();
                hs.put("PRODUCT",product.getText().toString());
                hs.put("RATE",rate.getText().toString());
                hs.put("TYPE",type.getText().toString());
                db.child(name.getText().toString().toUpperCase()).push().setValue(hs);

                startActivity(new Intent(AddProduct.this, MainActivity.class));
                finish();
            }
        });

    }
}

