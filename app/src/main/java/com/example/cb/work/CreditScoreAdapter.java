package com.example.cb.work;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cb.R;

import java.util.List;

public class CreditScoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private List<String> list;

    public CreditScoreAdapter(List<String> list)
    {
        this.list=list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_credit_rating_staff_row_item,parent,false);
        return new CreditScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        final CreditScoreViewHolder viewHolder=(CreditScoreViewHolder)holder;
        viewHolder.getNumber_Text().setText("출석번호:"+String.valueOf(position+1));
        viewHolder.getName_Text().setText("이름:"+list.get(position));
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener listener=null;

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener=listener;
    }

    private class CreditScoreViewHolder extends RecyclerView.ViewHolder
    {
        private TextView number_Text;
        private TextView name_Text;



        public CreditScoreViewHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.getLayoutParams().height=200;
            itemView.requestLayout();

            number_Text=(TextView) itemView.findViewById(R.id.WorkActivity_CreditRatingStaff_Row_Item_Number);
            name_Text=(TextView)itemView.findViewById(R.id.WorkActivity_CreditRatingStaff_Row_Item_Name);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    int position=getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION)
                        if (listener != null)
                            listener.onItemClick(view, position);

                }
            });


        }

        public TextView getNumber_Text() { return number_Text; }
        public TextView getName_Text() { return name_Text; }
    }
}
