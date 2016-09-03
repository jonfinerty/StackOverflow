package com.jonathanfinerty.stackoverflow;

import com.jonathanfinerty.stackoverflow.api.ApiClient;
import com.jonathanfinerty.stackoverflow.api.model.UserResponse;
import com.jonathanfinerty.stackoverflow.api.model.UsersResponse;
import com.jonathanfinerty.stackoverflow.domain.User;
import com.jonathanfinerty.stackoverflow.ui.UserListPresenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserPresenterTests {

    private UserListPresenter.View mockView;
    private ApiClient mockApiClient;
    private UserListPresenter presenter;

    @Before
    public void setUp() {

        mockView = mock(UserListPresenter.View.class);
        mockApiClient = mock(ApiClient.class);

        presenter = new UserListPresenter(mockApiClient);

        RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook() {
            @Override
            public Scheduler getIOScheduler() {
                return Schedulers.immediate();
            }
        });

        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }

    @After
    public void tearDown() {
        RxAndroidPlugins.getInstance().reset();
        RxJavaPlugins.getInstance().reset();
    }

    @Test
    public void shouldShowLoading() {
        presenter.bind(mockView);
        verify(mockView).showLoadingState();
    }

    @Test
    public void shouldShowCorrectUsers() {
        UsersResponse usersResponse = new UsersResponse();
        UserResponse userResponse1 = new UserResponse();
        userResponse1.display_name = "user 1";
        userResponse1.profile_image = "url 1";
        userResponse1.reputation = 123;

        UserResponse userResponse2 = new UserResponse();
        userResponse2.display_name = "user 2";
        userResponse2.profile_image = "url 2";
        userResponse2.reputation = 256;

        usersResponse.items = new ArrayList<>();
        usersResponse.items.add(userResponse1);
        usersResponse.items.add(userResponse2);

        when(mockApiClient.getUsers()).thenReturn(Observable.just(usersResponse));

        presenter.bind(mockView);
        presenter.getUsers();

        User expectedUser1 = new User("user 1", "url 1", 123);
        User expectedUser2 = new User("user 2", "url 2", 256);
        List<User> expectedUserList = new ArrayList<>();
        expectedUserList.add(expectedUser1);
        expectedUserList.add(expectedUser2);
        verify(mockView).setUsers(expectedUserList);
    }

    @Test
    public void shouldDisplayEmptyStateOnError() {
        when(mockApiClient.getUsers()).thenReturn(Observable.error(new Exception("It's broken")));

        presenter.bind(mockView);
        presenter.getUsers();

        verify(mockView).showEmptyState();
    }
}