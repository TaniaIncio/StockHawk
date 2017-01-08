package com.udacity.stockhawk.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.stockhawk.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tincio on 08/01/17.
 */

public class DetailHistoryAdapter extends RecyclerView.Adapter<DetailHistoryAdapter.HistoryViewHolder>{

    List<String> details;

    public DetailHistoryAdapter(List<String> details){
        this.details = details;
    }
    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        String[] data = details.get(position).split(",");
        holder.date.setText(getFormatDate(new Long(data[0].trim())));
        holder.value.setText(getFormatPrice(Float.parseFloat(data[1].trim())));
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    private String getFormatDate(long milisec){
        SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
        return format.format(new Date(milisec));
    }

    private String getFormatPrice(float price){
        DecimalFormat format = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        return format.format(price);
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.txt_date)
        TextView date;
        @BindView(R.id.txt_value)
        TextView value;
        public HistoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
