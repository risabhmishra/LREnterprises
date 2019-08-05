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

public class AddUser extends AppCompatActivity {
EditText name,gst,addr,phone;
Button submit;
DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        FirebaseApp.initializeApp(AddUser.this);
        db = FirebaseDatabase.getInstance().getReference().child("UserData");

        name = findViewById(R.id.et_name);
        gst = findViewById(R.id.et_gst);
        addr = findViewById(R.id.et_addr);
        phone = findViewById(R.id.et_phone);
        submit = findViewById(R.id.bu_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                HashMap<String,String> hs = new HashMap<>();
                hs.put("Name",name.getText().toString());
                hs.put("GST",gst.getText().toString());
                hs.put("Address",addr.getText().toString());
                hs.put("Phone",phone.getText().toString());
                db.push().setValue(hs);

                startActivity(new Intent(AddUser.this, MainActivity.class));
                finish();
            }
        });

    }
}
