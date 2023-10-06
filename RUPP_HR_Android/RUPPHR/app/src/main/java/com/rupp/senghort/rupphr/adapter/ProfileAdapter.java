package com.rupp.senghort.rupphr.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rupp.senghort.rupphr.R;
import com.rupp.senghort.rupphr.model.User;

import java.util.List;

/**
 * Created by KHEANG SENGHORT on 4/5/2018.
 */

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {
    private List<User> profiles;

    public class ProfileViewHolder extends RecyclerView.ViewHolder {
        public TextView labelTitle;
        public TextView labelHeader;

        public ProfileViewHolder(View view) {
            super(view);
            labelHeader = (TextView) view.findViewById(R.id.labelHeader);
            labelTitle = (TextView) view.findViewById(R.id.labelTitle);
        }
    }

    public ProfileAdapter(List<User> profiles) {
        this.profiles = profiles;
    }

    @Override
    public ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_list, parent, false);

        return new ProfileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProfileViewHolder holder, int position) {
        User user = profiles.get(position);
        holder.labelHeader.setText(user.getHeader());
        holder.labelTitle.setText(user.getText());
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

}
