package com.jonathanfinerty.stackoverflow.ui;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.jonathanfinerty.stackoverflow.api.ApiClient;
import com.jonathanfinerty.stackoverflow.domain.User;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserListPresenter {

    private View view;
    private final ApiClient apiClient;

    public interface View {
        void showLoadingState();
        void showEmptyState();
        void setUsers(List<User> users);
    }

    public UserListPresenter(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public void bind(View view) {
        this.view = view;
        view.showLoadingState();
    }

    public void getUsers() {
        apiClient.getUsers()
                .map(usersResponse ->
                        Stream.of(usersResponse.items)
                        .map(userResponse -> new User(userResponse.display_name, userResponse.profile_image, userResponse.reputation))
                        .collect(Collectors.toList())
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::setUsers,
                        throwable -> view.showEmptyState());
    }
}
