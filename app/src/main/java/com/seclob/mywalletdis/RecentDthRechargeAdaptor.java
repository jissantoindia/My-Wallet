package com.seclob.mywalletdis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecentDthRechargeAdaptor extends RecyclerView.Adapter<RecentDthRechargeAdaptor.MyViewHolder> {

    private LayoutInflater inflater;
    Context ctx;
    private List<RecentDthRechargeModel> mRecentDthRechargeModels = new ArrayList<>();
    RecentDthRechargeModel RecentDthRechargeModel;
    String vidID;
    SharedPreferences sharedPreferences;
    Context context;
    Fragment LocationFrag;
    FragmentTransaction ft;

    public RecentDthRechargeAdaptor(Context ctx){
        this.ctx = ctx;

    }

    public void renewItems(List<RecentDthRechargeModel> list) {
        this.mRecentDthRechargeModels = list;
        notifyDataSetChanged();
    }

    @Override
    public RecentDthRechargeAdaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_mobiles_layout, parent,false);
        return new RecentDthRechargeAdaptor.MyViewHolder(inflate);


    }

    @Override
    public void onBindViewHolder(RecentDthRechargeAdaptor.MyViewHolder holder, final int position) {
        RecentDthRechargeModel = mRecentDthRechargeModels.get(position);
        holder.Mobile.setText(RecentDthRechargeModel.getMobile());
        holder.Details.setText(RecentDthRechargeModel.getDis());
        holder.Provider.setText(RecentDthRechargeModel.getProvider());
        if(RecentDthRechargeModel.getStatus().equalsIgnoreCase("Completed"))
            holder.Status.setBackground(ctx.getDrawable(R.drawable.success));
        else if(RecentDthRechargeModel.getStatus().equalsIgnoreCase("Success"))
            holder.Status.setBackground(ctx.getDrawable(R.drawable.pending));
        else
            holder.Status.setBackground(ctx.getDrawable(R.drawable.danger));

            holder.Status.setText(RecentDthRechargeModel.getStatus());
        holder.cardvidparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(v.getContext(),DthRefreshActivity.class);
                i.putExtra("RechargeID",mRecentDthRechargeModels.get(position).getIdRecharge());
                v.getContext().startActivity(i);

            }

        });
    }

    @Override
    public int getItemCount() {
        return mRecentDthRechargeModels.size();
    }

    public void updateList(List<RecentDthRechargeModel> list){
        mRecentDthRechargeModels = list;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Mobile,Provider,Details,Status;

        LinearLayout cardvidparent;
        public MyViewHolder(View itemView) {
            super(itemView);

            Mobile = (TextView) itemView.findViewById(R.id.recent_mobile);
            Provider = (TextView) itemView.findViewById(R.id.recent_provider);
            Details = (TextView) itemView.findViewById(R.id.recent_details);
            Status = (TextView) itemView.findViewById(R.id.recent_status);

            cardvidparent = itemView.findViewById(R.id.recent_layout);
        }

    }


}