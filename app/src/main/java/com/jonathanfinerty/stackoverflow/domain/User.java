package com.jonathanfinerty.stackoverflow.domain;

public class User {

    public final String username;
    public final String avatarUrl;
    public final int reputation;

    public User(String username, String avatarUrl, int reputation) {
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.reputation = reputation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (reputation != user.reputation) return false;
        if (username != null ? !username.equals(user.username) : user.username != null)
            return false;
        return avatarUrl != null ? avatarUrl.equals(user.avatarUrl) : user.avatarUrl == null;

    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (avatarUrl != null ? avatarUrl.hashCode() : 0);
        result = 31 * result + reputation;
        return result;
    }
}
