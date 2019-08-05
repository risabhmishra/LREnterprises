package risabhmishra.com.lrenterprises.Fragments;

import android.accounts.Account;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import risabhmishra.com.lrenterprises.Adapter.AccountAdapter;
import risabhmishra.com.lrenterprises.Adapter.OrderAdapter;
import risabhmishra.com.lrenterprises.Model.Order;
import risabhmishra.com.lrenterprises.R;


public class AccountsFragment extends Fragment {
    ArrayList<Order> arraylist;
    DatabaseReference myRef;
    RecyclerView recyclerView;
    AccountAdapter userAdapter;
    private ProgressDialog dialog;
    TextView total;
    Double tot = 0.0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        FirebaseApp.initializeApp(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_accounts, container, false);
        arraylist = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        userAdapter = new AccountAdapter(arraylist,getContext());

        total = view.findViewById(R.id.acc_tot);

        dialog = new ProgressDialog(getContext());
        myRef = FirebaseDatabase.getInstance().getReference().child("MyAccount");
        dialog.setMessage("Loading.....");
        dialog.show();
        new RequestData().execute();

        return view;
    }

    public class RequestData extends AsyncTask<Void,Void,Boolean>
    {

        @Override
        protected Boolean doInBackground(Void... voids) {

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds:dataSnapshot.getChildren())
                    {
                        Order order = ds.getValue(Order.class);
                        tot+=Double.parseDouble(order.getAmount().replace("Rs.",""));
                        arraylist.add(order);
                    }
                    total.setText("Rs. "+Double.toString(tot*0.1));
                    recyclerView.setAdapter(userAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getContext(),"Database Error.. Try Again!",Toast.LENGTH_LONG).show();
                }
            });

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            dialog.dismiss();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu,menu);
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
                userAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }



}
