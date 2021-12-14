package com.example.cb.work;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cb.R;
import com.example.cb.info.Notice;


import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter
{
    private ArrayList<Notice> list;

    public NoticeAdapter(ArrayList<Notice> list)
    {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;
        view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_screen_row_item,parent,false);
        return new NoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        final NoticeAdapter.NoticeViewHolder viewHolder = (NoticeAdapter.NoticeViewHolder) holder;

        viewHolder.itemView.getLayoutParams().height=100;
        viewHolder.itemView.requestLayout();

        viewHolder.getTitle_Text().setText(list.get(position).getTitle());
        viewHolder.getDate_Text().setText(list.get(position).getDate());


    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }



    private class NoticeViewHolder extends RecyclerView.ViewHolder
    {
        private TextView title_Text;
        private TextView date_Text;


        private NoticeViewHolder(@NonNull View itemView)
        {
            super(itemView);

            title_Text=(TextView) itemView.findViewById(R.id.MainScreenActivity_Row_Item_Title);
            date_Text=(TextView) itemView.findViewById(R.id.MainScreenActivity_Row_Item_Date);

        }

        public TextView getTitle_Text() {
            return title_Text;
        }

        public TextView getDate_Text() {
            return date_Text;
        }
    }
}
