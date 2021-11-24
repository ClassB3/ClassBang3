package com.example.cb.work;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cb.R;
import com.example.cb.account.Saving;

import java.util.ArrayList;

public class SavingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private ArrayList<Saving> list;
    private Context context;

    public SavingAdapter(Context context, ArrayList<Saving> list)
    {
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;
        /*
        if (context instanceof SavingStateActivity)
        {
            view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_saving_state_row_item,parent,false);
        return new SavingViewHolder(view,context);
        }
        */
        view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_saving_state_row_item,parent,false);
        return new SavingViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        final SavingViewHolder viewHolder = (SavingViewHolder) holder;

        viewHolder.itemView.getLayoutParams().height=100;
        viewHolder.itemView.requestLayout();

        viewHolder.getNumber_Text().setText(list.get(position).getNumber());
        viewHolder.getName_Text().setText(list.get(position).getName());
        viewHolder.getSaving_Text().setText(list.get(position).getType());
        viewHolder.getdDay_Text().setText(list.get(position).getdDay());
        viewHolder.getDueDate_Text().setText(list.get(position).getDueDate());
        /*
        if (context instanceof SavingStateActivity)
        {
            viewHolder.getNumber_Text().setText(list.get(position).getNumber());
        viewHolder.getName_Text().setText(list.get(position).getName());
        viewHolder.getSaving_Text().setText(list.get(position).getType());
        viewHolder.getdDay_Text().setText(list.get(position).getdDay());
        viewHolder.getDueDate_Text().setText(list.get(position).getDueDate());
        }
        */
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    private class SavingViewHolder extends RecyclerView.ViewHolder
    {
        private TextView number_Text;
        private TextView name_Text;
        private TextView saving_Text;
        private TextView dDay_Text;
        private TextView dueDate_Text;
        private Button closing_Btn;

        private SavingViewHolder(@NonNull View itemView,Context context)
        {
            super(itemView);
            /*
            if (context instanceof SavingStateActivity)
            {
                number_Text=(TextView) itemView.findViewById(R.id.SavingStateActivity_Row_Item_Number);
                name_Text=(TextView) itemView.findViewById(R.id.SavingStateActivity_Row_Item_Name);
                saving_Text=(TextView) itemView.findViewById(R.id.SavingStateActivity_Row_Item_SavingType);
                dDay_Text=(TextView) itemView.findViewById(R.id.SavingStateActivity_Row_Item_Dday);
                dueDate_Text=(TextView) itemView.findViewById(R.id.SavingStateActivity_Row_Item_DueDate);
            }*/

            number_Text=(TextView) itemView.findViewById(R.id.SavingStateActivity_Row_Item_Number);
            name_Text=(TextView) itemView.findViewById(R.id.SavingStateActivity_Row_Item_Name);
            saving_Text=(TextView) itemView.findViewById(R.id.SavingStateActivity_Row_Item_SavingType);
            dDay_Text=(TextView) itemView.findViewById(R.id.SavingStateActivity_Row_Item_Dday);
            dueDate_Text=(TextView) itemView.findViewById(R.id.SavingStateActivity_Row_Item_DueDate);
        }

        public TextView getNumber_Text() {
            return number_Text;
        }

        public TextView getName_Text() {
            return name_Text;
        }

        public TextView getSaving_Text() {
            return saving_Text;
        }

        public TextView getdDay_Text() {
            return dDay_Text;
        }

        public TextView getDueDate_Text() {
            return dueDate_Text;
        }
    }
}
