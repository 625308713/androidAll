package com.dai.daicommon.ui.activity.frament;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dai.daicommon.R;
import com.dai.daicommon.ui.frament.BaseMainFragment;
import com.dai.daicommon.ui.frament.DiscoverFragment;
import com.dai.daicommon.ui.frament.HomeFragment;
import com.dai.daicommon.ui.frament.LoginFragment;
import com.dai.daicommon.ui.frament.MySupportFragment;
import com.dai.daicommon.ui.frament.ShopFragment;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;

public class FramentOneActivity extends MySupportActivity
        implements NavigationView.OnNavigationItemSelectedListener, BaseMainFragment.OnFragmentOpenDrawerListener
        , LoginFragment.OnLoginSuccessListener{
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    private TextView mTvName;   // NavigationView上的名字
    private ImageView mImgNav;  // NavigationView上的头像
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frament_one);
        //先查找栈内是否已存在该Frament
        MySupportFragment fragment = findFragment(HomeFragment.class);
        if (fragment == null) {
            //导入Frament
            loadRootFragment(R.id.fl_container, HomeFragment.newInstance());
        }
        initView();
    }
    private void initView() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setCheckedItem(R.id.nav_home);

        LinearLayout llNavHeader = (LinearLayout) mNavigationView.getHeaderView(0);
        mTvName = (TextView) llNavHeader.findViewById(R.id.tv_name);
        mImgNav = (ImageView) llNavHeader.findViewById(R.id.img_nav);
        llNavHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(GravityCompat.START);
                mDrawer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //跳转到登录Frament
                        start(LoginFragment.newInstance());
                    }
                }, 250);
            }
        });
    }

    @Override
    public void onBackPressedSupport() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            //如果抽屉打开则按返回键关闭抽屉
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            ISupportFragment topFragment = getTopFragment();

            // 主页的Fragment
            if (topFragment instanceof BaseMainFragment) {
                mNavigationView.setCheckedItem(R.id.nav_home);
            }

            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                //依次去掉栈中的Frament
                pop();
            } else {
                //提示再次点击退出
                if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                    finish();
                } else {
                    TOUCH_TIME = System.currentTimeMillis();
                    Toast.makeText(this, R.string.press_again_exit, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    @Override
    public boolean onNavigationItemSelected(final @NonNull MenuItem item) {
        mDrawer.closeDrawer(GravityCompat.START);
        mDrawer.postDelayed(new Runnable() {
            @Override
            public void run() {
                int id = item.getItemId();

                final ISupportFragment topFragment = getTopFragment();
                MySupportFragment myHome = (MySupportFragment) topFragment;
                if (id == R.id.nav_home) {
                    HomeFragment fragment = findFragment(HomeFragment.class);
                    Bundle newBundle = new Bundle();
                    newBundle.putString("from", "From:" + topFragment.getClass().getSimpleName());
                    fragment.putNewBundle(newBundle);
                    myHome.start(fragment, SupportFragment.SINGLETASK);
                } else if (id == R.id.nav_discover) {
                    DiscoverFragment fragment = findFragment(DiscoverFragment.class);
                    if (fragment == null) {
                        myHome.startWithPopTo(DiscoverFragment.newInstance(), HomeFragment.class, false);
                    } else {
                        // 如果已经在栈内,则以SingleTask模式start
                        myHome.start(fragment, SupportFragment.SINGLETASK);
                    }
                } else if (id == R.id.nav_shop) {
                    ShopFragment fragment = findFragment(ShopFragment.class);
                    if (fragment == null) {
                        myHome.startWithPopTo(ShopFragment.newInstance(), HomeFragment.class, false);
                    } else {
                        // 如果已经在栈内,则以SingleTask模式start,也可以用popTo
//                        start(fragment, SupportFragment.SINGLETASK);
                        myHome.popTo(ShopFragment.class, false);
                    }
                } else if (id == R.id.nav_login) {
                    start(LoginFragment.newInstance());
                } else if (id == R.id.nav_swipe_back) {
//                    startActivity(new Intent(FramentOneActivity.this, SwipeBackSampleActivity.class));
                }
            }
        }, 300);

        return true;
    }

    @Override
    public void onOpenDrawer() {
        if (!mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onLoginSuccess(String account) {

    }
}
