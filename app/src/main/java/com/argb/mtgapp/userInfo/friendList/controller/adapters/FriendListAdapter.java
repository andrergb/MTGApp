package com.argb.mtgapp.userInfo.friendList.controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.argb.mtgapp.R;
import com.argb.mtgapp.main.Constants;
import com.argb.mtgapp.main.model.Player;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {

    private Context mContext;
    private FriendListInterface mFriendListInterface;
    private List<Player> mDataSet;

    public FriendListAdapter(Context mContext, FriendListInterface friendListInterface) {
        this.mContext = mContext;
        this.mFriendListInterface = friendListInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_friend_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Player currentPlayer = mDataSet.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFriendListInterface.OnFriendClickListener(currentPlayer);
            }
        });

        Glide.with(mContext)
                .load(currentPlayer.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                //.placeholder(R.drawable.ic_placeholder) /*TODO*/
                .into(holder.avatar);
        holder.name.setText(currentPlayer.getName());

        Constants.FriendshipStatus friendshipStatus = currentPlayer.getFriendshipStatus();
        if (friendshipStatus != null && friendshipStatus != Constants.FriendshipStatus.FRIEND) {
            holder.requestButtons.setVisibility(View.VISIBLE);

            if (friendshipStatus == Constants.FriendshipStatus.DENY_ACCEPT) {
                holder.denyButton.setVisibility(View.VISIBLE);
                holder.denyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFriendListInterface.OnDenyClickListener(currentPlayer);
                    }
                });
                holder.acceptButton.setVisibility(View.VISIBLE);
                holder.acceptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFriendListInterface.OnAcceptClickListener(currentPlayer);
                    }
                });
                holder.pendingButton.setVisibility(View.GONE);
            } else if (friendshipStatus == Constants.FriendshipStatus.PENDING) {
                holder.denyButton.setVisibility(View.GONE);
                holder.denyButton.setOnClickListener(null);
                holder.acceptButton.setVisibility(View.GONE);
                holder.acceptButton.setOnClickListener(null);
                holder.pendingButton.setVisibility(View.VISIBLE);
            }
        } else {
            holder.requestButtons.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void setDataSet(List<Player> playerList) {
        this.mDataSet = playerList;
        sortDataSet();
        notifyDataSetChanged();
    }

    private void sortDataSet() {
        Collections.sort(mDataSet, new Player.NameComparator());
        Collections.sort(mDataSet, new Player.FriendShipStatusComparator());
    }

    public interface FriendListInterface {
        void OnAcceptClickListener(Player player);

        void OnDenyClickListener(Player player);

        void OnFriendClickListener(Player player);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView name;
        ConstraintLayout requestButtons;
        RelativeLayout denyButton;
        RelativeLayout acceptButton;
        RelativeLayout pendingButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.avatar = itemView.findViewById(R.id.friend_row_avatar);
            this.name = itemView.findViewById(R.id.friend_row_name);
            this.requestButtons = itemView.findViewById(R.id.friend_row_request_buttons);
            this.denyButton = requestButtons.findViewById(R.id.friend_request_deny_button);
            this.acceptButton = requestButtons.findViewById(R.id.friend_request_accept_button);
            this.pendingButton = requestButtons.findViewById(R.id.friend_request_pending_button);
        }
    }
}
