package risabhmishra.com.lrenterprises.Adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.List;

import risabhmishra.com.lrenterprises.Model.Order;
import risabhmishra.com.lrenterprises.R;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.MyViewHolder> implements Filterable {
public AccountAdapter(List<Order> userList, Context context) {
        this.userList = userList;
        this.context = context;
        sortedUserList = new ArrayList<>(userList);
        }

        List<Order> userList,sortedUserList;
        Context context;

@NonNull
@Override
public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.account_item,viewGroup,false);
        return new MyViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
    final Order user = userList.get(i);
    myViewHolder.name.setText(user.getName());
    myViewHolder.amount.setText(user.getAmount());
    myViewHolder.gst.setText(user.getGST());
    myViewHolder.orderno.setText(user.getOrderNo());
    myViewHolder.date.setText(user.getDate());
    myViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(user.getPDFUrl()));
            context.startActivity(intent);
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
        List<Order> fiteredList = new ArrayList<>();
        if(charSequence == null || charSequence.length()==0)
        {
        fiteredList.addAll(sortedUserList);
        }
        else
        {
        String filterPattern = charSequence.toString().toLowerCase().trim();
        for(Order ip:sortedUserList)
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
    TextView name,amount,gst,orderno,date;
    RelativeLayout relativeLayout;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.user_name);
        amount = itemView.findViewById(R.id.user_amount);
        gst = itemView.findViewById(R.id.user_gst);
        orderno = itemView.findViewById(R.id.orderno);
        date = itemView.findViewById(R.id.date);
        relativeLayout = itemView.findViewById(R.id.rlayout);

    }
}

}

