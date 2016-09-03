package com.jonathanfinerty.stackoverflow.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jonathanfinerty.stackoverflow.R;
import com.jonathanfinerty.stackoverflow.domain.User;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private List<User> users = Collections.emptyList();

    public UserListAdapter(Context context) {
        this.context = context;
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View userView = LayoutInflater.from(parent.getContext()).inflate(R.layout.li_user, parent, false);
        return new UserViewHolder(userView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        User user = users.get(position);
        UserViewHolder userViewHolder = (UserViewHolder) holder;

        userViewHolder.avatar.setImageURI(user.avatarUrl);
        userViewHolder.name.setText(user.username);
        userViewHolder.reputation.setText(context.getString(R.string.reputation, user.reputation));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sdv_avatar) SimpleDraweeView avatar;
        @BindView(R.id.tv_username) TextView name;
        @BindView(R.id.tv_reputation) TextView reputation;

        public UserViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
