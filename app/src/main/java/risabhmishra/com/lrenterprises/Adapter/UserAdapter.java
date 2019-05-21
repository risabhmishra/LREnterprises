package risabhmishra.com.lrenterprises.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import risabhmishra.com.lrenterprises.Model.User;
import risabhmishra.com.lrenterprises.R;
import risabhmishra.com.lrenterprises.UI.BrandSelect;
import risabhmishra.com.lrenterprises.Utils.Preferences;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> implements Filterable {
    public UserAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
        sortedUserList = new ArrayList<>(userList);
    }

    List<User> userList,sortedUserList;
Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
      final User user = userList.get(i);
      myViewHolder.name.setText(user.getName());
      myViewHolder.address.setText(user.getAddress());
      myViewHolder.gst.setText(user.getGst());
      myViewHolder.phone.setText(user.getPhone());
      myViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              String user_gst = user.getGst();
              Preferences.setUserGst(context,user_gst);
              context.startActivity(new Intent(context, BrandSelect.class));

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
            List<User> fiteredList = new ArrayList<>();
            if(charSequence == null || charSequence.length()==0)
            {
                fiteredList.addAll(sortedUserList);
            }
            else
            {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(User ip:sortedUserList)
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
        TextView name,address,gst,phone;
        RelativeLayout relativeLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.user_name);
            address = itemView.findViewById(R.id.user_address);
            gst = itemView.findViewById(R.id.user_gst);
            phone = itemView.findViewById(R.id.user_phone);
            relativeLayout = itemView.findViewById(R.id.rlayout);

        }
    }

}
