package com.argb.mtgapp.userInfo;

import android.os.Bundle;
import android.widget.ImageView;

import com.argb.mtgapp.R;
import com.argb.mtgapp.main.Constants;
import com.argb.mtgapp.main.model.Player;
import com.argb.mtgapp.userInfo.adapters.ViewPagerAdapter;
import com.argb.mtgapp.userInfo.fragments.OneFragment;
import com.argb.mtgapp.userInfo.fragments.ThreeFragment;
import com.argb.mtgapp.userInfo.fragments.TwoFragment;
import com.argb.mtgapp.userInfo.friendList.view.fragments.FriendListFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class UserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        boolean isSelfProfile = getIntent().getBooleanExtra(Constants.INTENT_EXTRA_IS_SELF_PROFILE, false);

        Player currentPlayer = new Player();
        if (isSelfProfile) {
            FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
            if (mUser != null) {
                currentPlayer.setName(mUser.getDisplayName());
                currentPlayer.setAvatarUrl(Objects.requireNonNull(mUser.getPhotoUrl()).toString());
            }
        } else {
            //TODO load user
            currentPlayer.setName("Test");
            currentPlayer.setAvatarUrl("https://api.adorable.io/avatars/285/" + currentPlayer.getName());
        }

        final ImageView avatar = findViewById(R.id.user_avatar);
        avatar.setTransitionName(getString(R.string.shared_element_avatar));

        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(currentPlayer.getName());

        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) { // Collapsed
                    avatar.setTransitionName(null);
                } else if (verticalOffset == 0) { // Expanded
                    avatar.setTransitionName(getString(R.string.shared_element_avatar));
                } else { // Moving
                    if (collapsingToolbarLayout.getContentScrim() != null && collapsingToolbarLayout.getContentScrim().getAlpha() == 0) {
                        avatar.setTransitionName(getString(R.string.shared_element_avatar));
                    } else {
                        avatar.setTransitionName(null);
                    }
                }
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager, isSelfProfile);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Glide.with(this)
                .load(currentPlayer.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
//                .placeholder(R.drawable.ic_placeholder) /*TODO*/
                .into(avatar);
    }

    private void setupViewPager(ViewPager viewPager, boolean isSelfProfile) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if (isSelfProfile) {
            adapter.addFragment(new OneFragment(), getString(R.string.user_info_title_personal));
            adapter.addFragment(new TwoFragment(), getString(R.string.user_info_title_games));
            adapter.addFragment(new ThreeFragment(), getString(R.string.user_info_title_decks));
            adapter.addFragment(new FriendListFragment(), getString(R.string.user_info_title_friends));
        } else {
            adapter.addFragment(new TwoFragment(), getString(R.string.user_info_title_games));
            adapter.addFragment(new ThreeFragment(), getString(R.string.user_info_title_decks));
        }
        viewPager.setAdapter(adapter);
    }
}
