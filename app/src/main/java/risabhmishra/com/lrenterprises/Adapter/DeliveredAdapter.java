package risabhmishra.com.lrenterprises.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import risabhmishra.com.lrenterprises.Model.Order;
import risabhmishra.com.lrenterprises.R;


public class DeliveredAdapter extends RecyclerView.Adapter<DeliveredAdapter.MyViewHolder> implements Filterable {
    public DeliveredAdapter(List<Order> userList, Context context) {
        this.userList = userList;
        this.context = context;
        sortedUserList = new ArrayList<>(userList);
    }

    List<Order> userList,sortedUserList;
    Context context;
    String url;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.delivered_order_item,viewGroup,false);
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


        myViewHolder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM / yyyy");
                String strDate = mdformat.format(calendar.getTime());

                url = user.getPDFUrl();

                new UpdateDelivered().execute(user.getKey(),user.getOrderNo(),user.getName(),user.getGST(),user.getAmount(),strDate,Integer.toString(i));

                Task<Void> uploadref = FirebaseDatabase.getInstance().getReference().child("DeliveredOrders").child(user.getKey()).removeValue();
                userList.remove(i);
                notifyItemRemoved(i);


            }
        });
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
        Button phone;
        RelativeLayout relativeLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.user_name);
            amount = itemView.findViewById(R.id.user_amount);
            gst = itemView.findViewById(R.id.user_gst);
            phone = itemView.findViewById(R.id.user_phone);
            orderno = itemView.findViewById(R.id.orderno);
            relativeLayout = itemView.findViewById(R.id.rlayout);
            date = itemView.findViewById(R.id.date);

        }
    }

    public class UpdateDelivered extends AsyncTask<String,Void,Boolean>
    {

        DatabaseReference deliveredref;
        @Override
        protected Boolean doInBackground(String... strings) {


            deliveredref = FirebaseDatabase.getInstance().getReference().child("MyAccount").push();
            HashMap<String,String> hs = new HashMap<>();
            hs.put("OrderNo",strings[1]);
            hs.put("Name",strings[2]);
            hs.put("GST",strings[3]);
            hs.put("Amount",strings[4]);
            hs.put("Date",strings[5]);
            hs.put("PDFUrl",url);
            deliveredref.setValue(hs);

            return true;
        }
    }


}

