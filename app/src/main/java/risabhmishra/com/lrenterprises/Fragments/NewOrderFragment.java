package risabhmishra.com.lrenterprises.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import risabhmishra.com.lrenterprises.Model.Company;
import risabhmishra.com.lrenterprises.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewOrderFragment extends Fragment {

    ListView listView;
    ArrayList<String> arraylist = new ArrayList<>();
    DatabaseReference myRef;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_new_order, container, false);
        myRef = FirebaseDatabase.getInstance().getReference().child("ProductsData");

        listView = view.findViewById(R.id.listview);
        final ArrayAdapter<String> arrayAdapter  = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,arraylist);
        listView.setAdapter(arrayAdapter);
myRef.addChildEventListener(new ChildEventListener() {
    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        arraylist.add(dataSnapshot.getValue(String.class));
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});


    return view;
    }

}
