package risabhmishra.com.lrenterprises.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import risabhmishra.com.lrenterprises.Model.CartItem;
import risabhmishra.com.lrenterprises.R;
import risabhmishra.com.lrenterprises.Utils.Controller;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> implements Filterable {
    public CartAdapter(List<CartItem> userList, Context context) {
        this.userList = userList;
        this.context = context;
        sortedUserList = new ArrayList<>(userList);
    }

    List<CartItem> userList,sortedUserList;
    Context context;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item,viewGroup,false);
        return new CartAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final CartItem cartItem = userList.get(i);
        myViewHolder.name.setText(cartItem.getName());
        myViewHolder.type.setText(cartItem.getType());
        myViewHolder.qty.setText(cartItem.getQty());
        myViewHolder.amount.setText(cartItem.getAmount());

        myViewHolder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Controller.removeItemFromCart(cartItem);
                notifyDataSetChanged();
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
            List<CartItem> fiteredList = new ArrayList<>();
            if(charSequence == null || charSequence.length()==0)
            {
                fiteredList.addAll(sortedUserList);
            }
            else
            {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(CartItem ip:sortedUserList)
                {
                    if(ip.getName().toLowerCase().contains(filterPattern))
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
        TextView name,type,amount;
        TextView qty;
        ImageButton cart;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            type = itemView.findViewById(R.id.product_type);
            amount =itemView.findViewById(R.id.product_amount);
            qty = itemView.findViewById(R.id.product_qty);
            cart = itemView.findViewById(R.id.product_cartremove);
        }
    }
}
