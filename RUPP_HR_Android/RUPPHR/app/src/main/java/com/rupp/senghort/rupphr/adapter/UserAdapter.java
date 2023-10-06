package com.rupp.senghort.rupphr.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.rupp.senghort.rupphr.R;
import com.rupp.senghort.rupphr.model.User;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

/**
 * Created by KHEANG SENGHORT on 3/30/2018.
 */

public class UserAdapter extends  StatelessSection {
    private static final String TAG = UserAdapter.class.getSimpleName();
    private String title;
    private List<User> list;
    public UserAdapter(String title, List<User> list) {
        super(R.layout.header_layout, R.layout.item_layout);
        this.title = title;
        this.list = list;
    }
    @Override
    public int getContentItemsTotal() {
        return list.size();
    }
    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new UserAdapter.ItemViewHolder(view);
    }
    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserAdapter.ItemViewHolder iHolder = (UserAdapter.ItemViewHolder)holder;
        iHolder.labelHeader.setText(list.get(position).getHeader());
        iHolder.labelTitle.setText(list.get(position).getText());
    }
    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new UserAdapter.HeaderViewHolder(view);
    }
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        UserAdapter.HeaderViewHolder hHolder = (UserAdapter.HeaderViewHolder)holder;
        hHolder.labelHeader.setText(title);
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView labelHeader;
        public TextView labelTitle;
        public ItemViewHolder(View itemView) {
            super(itemView);
            labelHeader = (TextView)itemView.findViewById(R.id.labelHeader);
            labelTitle = (TextView)itemView.findViewById(R.id.labelTitle);
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder{
        public TextView labelHeader;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            labelHeader = (TextView)itemView.findViewById(R.id.labelHeader);
        }
    }
}
