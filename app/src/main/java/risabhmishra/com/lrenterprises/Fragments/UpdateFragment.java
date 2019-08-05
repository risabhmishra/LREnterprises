package risabhmishra.com.lrenterprises.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import risabhmishra.com.lrenterprises.R;
import risabhmishra.com.lrenterprises.UI.AddProduct;
import risabhmishra.com.lrenterprises.UI.AddUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateFragment extends Fragment {


    public UpdateFragment() {
        // Required empty public constructor
    }

Button user,product;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_update, container, false);

        user = v.findViewById(R.id.buuser);
        product = v.findViewById(R.id.buproduct);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddUser.class));
            }
        });
product.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getContext(), AddProduct.class));
    }
});
        return v;
    }

}
