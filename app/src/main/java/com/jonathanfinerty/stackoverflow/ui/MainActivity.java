package com.jonathanfinerty.stackoverflow.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.jonathanfinerty.stackoverflow.R;
import com.jonathanfinerty.stackoverflow.api.ApiClient;
import com.jonathanfinerty.stackoverflow.domain.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements UserListPresenter.View {

    @BindView(R.id.tb_activity_main) Toolbar toolbar;
    @BindView(R.id.rv_users) RecyclerView recyclerView;
    @BindView(R.id.pb_loading_users) android.view.View loadingView;
    @BindView(R.id.tv_no_users) android.view.View emptyView;

    private UserListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserListAdapter(this);
        recyclerView.setAdapter(adapter);

        ApiClient apiClient = new ApiClient();
        UserListPresenter presenter = new UserListPresenter(apiClient);
        presenter.bind(this);

        presenter.getUsers();
    }

    @Override
    public void showLoadingState() {
        recyclerView.setVisibility(android.view.View.GONE);
        emptyView.setVisibility(android.view.View.GONE);
        loadingView.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void showEmptyState() {
        recyclerView.setVisibility(android.view.View.GONE);
        emptyView.setVisibility(android.view.View.VISIBLE);
        loadingView.setVisibility(android.view.View.GONE);
    }

    @Override
    public void setUsers(List<User> users) {
        adapter.setUsers(users);

        recyclerView.setVisibility(android.view.View.VISIBLE);
        emptyView.setVisibility(android.view.View.GONE);
        loadingView.setVisibility(android.view.View.GONE);
    }
}
