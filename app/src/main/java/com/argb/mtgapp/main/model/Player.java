package com.argb.mtgapp.main.model;

import com.argb.mtgapp.main.Constants;

import java.util.Comparator;

public class Player {
    private String id;
    private String name;
    private String avatarUrl;
    private Constants.FriendshipStatus friendshipStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Constants.FriendshipStatus getFriendshipStatus() {
        return friendshipStatus;
    }

    public void setFriendshipStatus(Constants.FriendshipStatus friendshipStatus) {
        this.friendshipStatus = friendshipStatus;
    }

    public static class NameComparator implements Comparator<Player> {
        @Override
        public int compare(Player p1, Player p2) {
            return p1.getName().compareToIgnoreCase(p2.getName());
        }
    }

    public static class FriendShipStatusComparator implements Comparator<Player> {
        @Override
        public int compare(Player p1, Player p2) {
            Constants.FriendshipStatus p1Status = p1.getFriendshipStatus();
            Constants.FriendshipStatus p2Status = p2.getFriendshipStatus();
            if (p1Status == null || p2Status == null) {
                return 0;
            }
            return p2Status.ordinal() - p1Status.ordinal();
        }
    }
}
