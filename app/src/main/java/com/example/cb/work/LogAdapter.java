package com.example.cb.work;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cb.R;
import com.example.cb.account.AccountLog;
import com.example.cb.info.Notice;

import java.util.ArrayList;
import java.util.List;

public class LogAdapter extends RecyclerView.Adapter
{
    private List<AccountLog> list;

    public LogAdapter(ArrayList<AccountLog> list)
    {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;
        view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_account_log_row_item,parent,false);
        return new LogAdapter.LogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        final LogAdapter.LogViewHolder viewHolder = (LogAdapter.LogViewHolder) holder;

        viewHolder.itemView.getLayoutParams().height=100;
        viewHolder.itemView.requestLayout();

        viewHolder.getDate_Text().setText(list.get(position).getDateOfTransaction());
        viewHolder.getTime_Text().setText(list.get(position).getTimeOfTransaction());
        if (list.get(position).isDepositOrNot())
            viewHolder.getDepositOrNot_Text().setText("입금");
        else
            viewHolder.getDepositOrNot_Text().setText("출금");

        viewHolder.getAmount_Text().setText(String.valueOf(list.get(position).getAmount()));
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }



    private class LogViewHolder extends RecyclerView.ViewHolder
    {
        private TextView date_Text;
        private TextView time_Text;
        private TextView depositOrNot_Text;
        private TextView amount_Text;


        private LogViewHolder(@NonNull View itemView)
        {
            super(itemView);

            date_Text=itemView.findViewById(R.id.AccountLogActivity_Row_Item_Date);
            time_Text=itemView.findViewById(R.id.AccountLogActivity_Row_Item_Time);
            depositOrNot_Text=itemView.findViewById(R.id.AccountLogActivity_Row_Item_DepositOrNot);
            amount_Text=itemView.findViewById(R.id.AccountLogActivity_Row_Item_Amount);

        }

        public TextView getDate_Text() { return date_Text; }
        public TextView getTime_Text() { return time_Text; }
        public TextView getDepositOrNot_Text() { return depositOrNot_Text; }
        public TextView getAmount_Text() { return amount_Text; }
    }
}
