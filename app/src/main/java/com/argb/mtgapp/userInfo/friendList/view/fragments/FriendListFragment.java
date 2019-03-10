package com.argb.mtgapp.userInfo.friendList.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.argb.mtgapp.R;
import com.argb.mtgapp.main.Constants;
import com.argb.mtgapp.main.model.Player;
import com.argb.mtgapp.userInfo.friendList.controller.adapters.FriendListAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FriendListFragment extends Fragment implements FriendListAdapter.FriendListInterface {

    FriendListAdapter mFriendListAdapter;

    public FriendListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_list, container, false);

        Player p1 = new Player();
        p1.setName("Player1");
        p1.setId("1");
        p1.setAvatarUrl("https://api.adorable.io/avatars/285/" + p1.getName());
        p1.setFriendshipStatus(Constants.FriendshipStatus.FRIEND);

        Player p2 = new Player();
        p2.setName("Player2");
        p2.setId("2");
        p2.setAvatarUrl("https://api.adorable.io/avatars/285/" + p2.getName());
        p2.setFriendshipStatus(Constants.FriendshipStatus.PENDING);

        Player p3 = new Player();
        p3.setName("Player3");
        p3.setId("3");
        p3.setAvatarUrl("https://api.adorable.io/avatars/285/" + p3.getName());
        p3.setFriendshipStatus(Constants.FriendshipStatus.DENY_ACCEPT);

        Player p4 = new Player();
        p4.setName("Player4");
        p4.setId("4");
        p4.setAvatarUrl("https://api.adorable.io/avatars/285/" + p4.getName());
        p4.setFriendshipStatus(Constants.FriendshipStatus.FRIEND);

        Player p5 = new Player();
        p5.setName("Player5");
        p5.setId("5");
        p5.setAvatarUrl("https://api.adorable.io/avatars/285/" + p5.getName());
        p5.setFriendshipStatus(Constants.FriendshipStatus.FRIEND);

        Player p6 = new Player();
        p6.setName("Player6");
        p6.setId("6");
        p6.setAvatarUrl("https://api.adorable.io/avatars/285/" + p6.getName());
        p6.setFriendshipStatus(Constants.FriendshipStatus.FRIEND);

        Player p7 = new Player();
        p7.setName("Player7");
        p7.setId("7");
        p7.setAvatarUrl("https://api.adorable.io/avatars/285/" + p7.getName());
        p7.setFriendshipStatus(Constants.FriendshipStatus.DENY_ACCEPT);

        Player p8 = new Player();
        p8.setName("Player8");
        p8.setId("8");
        p8.setAvatarUrl("https://api.adorable.io/avatars/285/" + p8.getName());
        p8.setFriendshipStatus(Constants.FriendshipStatus.FRIEND);

        Player p9 = new Player();
        p9.setName("Player9");
        p9.setId("9");
        p9.setAvatarUrl("https://api.adorable.io/avatars/285/" + p9.getName());
        p9.setFriendshipStatus(Constants.FriendshipStatus.FRIEND);

        Player p10 = new Player();
        p10.setName("Player10");
        p10.setId("10");
        p10.setAvatarUrl("https://api.adorable.io/avatars/285/" + p10.getName());
        p10.setFriendshipStatus(Constants.FriendshipStatus.PENDING);

        Player p11 = new Player();
        p11.setName("Player11");
        p11.setId("11");
        p11.setAvatarUrl("https://api.adorable.io/avatars/285/" + p11.getName());
        p11.setFriendshipStatus(Constants.FriendshipStatus.FRIEND);

        Player p12 = new Player();
        p12.setName("Player12");
        p12.setId("12");
        p12.setAvatarUrl("https://api.adorable.io/avatars/285/" + p12.getName());
        p12.setFriendshipStatus(Constants.FriendshipStatus.DENY_ACCEPT);

        Player p13 = new Player();
        p13.setName("Player13");
        p13.setId("13");
        p13.setAvatarUrl("https://api.adorable.io/avatars/285/" + p13.getName());
        p13.setFriendshipStatus(Constants.FriendshipStatus.FRIEND);

        List<Player> playerList = new ArrayList<>();
        playerList.add(p1);
        playerList.add(p2);
        playerList.add(p3);
        playerList.add(p4);
        playerList.add(p5);
        playerList.add(p6);
        playerList.add(p7);
        playerList.add(p8);
        playerList.add(p9);
        playerList.add(p10);
        playerList.add(p11);
        playerList.add(p12);
        playerList.add(p13);

        mFriendListAdapter = new FriendListAdapter(getContext(), this);
        mFriendListAdapter.setDataSet(playerList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        RecyclerView friendList = view.findViewById(R.id.friend_list);
        friendList.setLayoutManager(layoutManager);
        friendList.setAdapter(mFriendListAdapter);

        return view;
    }

    @Override
    public void OnAcceptClickListener(Player player) {
        Toast.makeText(getContext(), "Accept: " + player.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnDenyClickListener(Player player) {
        Toast.makeText(getContext(), "Deny: " + player.getName(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void OnFriendClickListener(Player player) {
        Toast.makeText(getContext(), "Friend: " + player.getName(), Toast.LENGTH_SHORT).show();
    }
}
