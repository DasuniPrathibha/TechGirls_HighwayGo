package com.example.highwaygo.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.highwaygo.R;
import com.example.highwaygo.model.Bus;

import java.util.ArrayList;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.BusViewHolder> {

    Context context;
    static ArrayList<Bus> list;
    private OnBusListener mOnTicketListener;

    public BusAdapter(Context context, ArrayList<Bus> busList) {
        this.context = context;
        this.list = busList;

        Log.i("BADA", " Created AD = " + list.get(0).getDate());
    }

    @NonNull
    @Override
    public BusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bus_item, parent, false);

        return new BusViewHolder(view, mOnTicketListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BusAdapter.BusViewHolder holder, int position) {
        Bus bus = this.list.get(position);
        Log.i("BADA", "  busName = " + this.list.get(position).getFrom());
        holder.txtNo.setText(bus.getNo());
        holder.txtName.setText(bus.getName());
        holder.txtDate.setText(bus.getDate());
        holder.txtFrom.setText(bus.getFrom());
        holder.txtTo.setText(bus.getTo());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BusViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBus;
        TextView txtNo, txtName, txtFrom, txtTo, txtDate;

        public BusViewHolder(@NonNull View itemView, OnBusListener mOnTicketListener) {
            super(itemView);
            imgBus = itemView.findViewById(R.id.imgThumb);
            txtNo = itemView.findViewById(R.id.txtNo);
            txtName = itemView.findViewById(R.id.txtTitle);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtFrom = itemView.findViewById(R.id.txtFrom);
            txtTo = itemView.findViewById(R.id.txtTo);
        }
    }
}
