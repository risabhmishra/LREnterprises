package risabhmishra.com.lrenterprises.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import risabhmishra.com.lrenterprises.Adapter.BrandAdapter;
import risabhmishra.com.lrenterprises.Adapter.CartAdapter;
import risabhmishra.com.lrenterprises.MainActivity;
import risabhmishra.com.lrenterprises.Model.CartItem;
import risabhmishra.com.lrenterprises.R;
import risabhmishra.com.lrenterprises.Utils.Controller;

public class CartActivity extends AppCompatActivity {
    private ArrayList<CartItem> cartItemList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CartAdapter vegetableAdapter;
    public static TextView tvTotalCart;
    private RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        tvTotalCart = findViewById(R.id.tv_total_amount);
        relativeLayout = findViewById(R.id.rl_fragment_order_total);

        cartItemList = new ArrayList<>();
        cartItemList = Controller.getCartItems();

        recyclerView = (RecyclerView) findViewById(R.id.rview);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this,OrderInvoice.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
            }
        });

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        vegetableAdapter = new CartAdapter(cartItemList, this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(vegetableAdapter);

        String cartTotal = String.valueOf(Controller.getCartTotal());
        tvTotalCart.setText(cartTotal);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.empty_cart,menu);
        MenuItem empty_cart = menu.findItem(R.id.empty_cart);
        empty_cart.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {


                final AlertDialog.Builder builder  =new AlertDialog.Builder(CartActivity.this);
                builder.setTitle("Empty Cart");
                builder.setMessage("Are you sure ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Controller.emptyCart();
                    startActivity(new Intent(CartActivity.this, BrandSelect.class));
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//        finish();
                    }
                });

                builder.create().show();

                return false;
            }
        });

        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



}
