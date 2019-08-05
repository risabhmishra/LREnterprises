package risabhmishra.com.lrenterprises.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import risabhmishra.com.lrenterprises.Model.CartItem;
import risabhmishra.com.lrenterprises.Model.Company;
import risabhmishra.com.lrenterprises.R;
import risabhmishra.com.lrenterprises.Utils.Controller;


public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.MyViewHolder> implements Filterable {
    public BrandAdapter(List<Company> userList, Context context) {
        this.userList = userList;
        this.context = context;
        sortedUserList = new ArrayList<>(userList);
    }

    List<Company> userList,sortedUserList;
    Context context;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.brand_item,viewGroup,false);
        return new BrandAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
final Company company = userList.get(i);
        myViewHolder.name.setText(company.getPRODUCT());
        myViewHolder.price.setText(company.getRATE());
        myViewHolder.type.setText(company.getTYPE());

        if(company.getStatus())
        {
            myViewHolder.cart.setBackgroundColor(Color.DKGRAY);
            myViewHolder.cart.setClickable(false);
        }

        myViewHolder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String qty = myViewHolder.qty.getText().toString();
                Controller.addItemToCart(new CartItem(company.getPRODUCT(),company.getTYPE(),company.getRATE(),qty));
                company.setStatus(true);
                notifyItemChanged(myViewHolder.getAdapterPosition());

            }
        });

        myViewHolder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Update Price");
        alertDialog.setMessage("Enter New Price");
        final EditText input = new EditText(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String price = input.getText().toString();
                        company.setRATE(price);
                        notifyDataSetChanged();
                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
        return true;
    }
});
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    @Override
    public Filter getFilter() {
        return sortedFilter;
    }

    private Filter sortedFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Company> fiteredList = new ArrayList<>();
            if(charSequence == null || charSequence.length()==0)
            {
                fiteredList.addAll(sortedUserList);
            }
            else
            {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(Company ip:sortedUserList)
                {
                    if(ip.getPRODUCT().toLowerCase().contains(filterPattern))
                    {
                        fiteredList.add(ip);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values=fiteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            userList.clear();
            userList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,type,price;
        EditText qty;
        ImageButton cart;
        RelativeLayout relativeLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            type = itemView.findViewById(R.id.product_type);
            price =itemView.findViewById(R.id.product_price);
            qty = itemView.findViewById(R.id.product_qty);
            cart = itemView.findViewById(R.id.product_cartbu);
            relativeLayout = itemView.findViewById(R.id.rlayout);
        }
    }
}
