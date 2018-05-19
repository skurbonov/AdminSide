package com.example.sardorbek.adminside.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sardorbek.adminside.Common.Common;
import com.example.sardorbek.adminside.Interface.ItemClickListener;
import com.example.sardorbek.adminside.R;

/**
 * Created by sardorbek on 4/20/18.
 */

public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{
    public TextView txtBookName;
    public ImageView imageBookView;


    private ItemClickListener itemClickListener;

    public BookViewHolder(View itemView){
        super(itemView);

        txtBookName = (TextView)itemView.findViewById(R.id.book_name);
        imageBookView = (ImageView)itemView.findViewById(R.id.book_image);

        itemView.setOnCreateContextMenuListener(this);
        itemView.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {

        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select the action");
        menu.add(0,0,getAdapterPosition(), Common.UPDATE);
        menu.add(0,1,getAdapterPosition(), Common.DELETE);


    }
}