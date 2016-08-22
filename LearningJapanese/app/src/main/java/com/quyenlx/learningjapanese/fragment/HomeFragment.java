package com.quyenlx.learningjapanese.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.quyenlx.learningjapanese.R;
import com.quyenlx.learningjapanese.Util.Utils;
import com.quyenlx.learningjapanese.activity.HomeActivity;
import com.quyenlx.learningjapanese.listerner.OnSelectedTabListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by LxQuyen on 21/08/2016.
 */
public class HomeFragment extends BaseFragment implements TabHost.OnTabChangeListener {
    private static final String TAG = HomeFragment.class.getSimpleName();
    @Bind(android.R.id.tabhost)
    TabHost mTabHost;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, TabInfo>();
    private HashMap<String, Fragment> mapFragment = new HashMap<String, Fragment>();
    private TabInfo mLastTab = null;
    private String TAB_1 = "tabs1";
    private String TAB_2 = "tabs2";
    private String TAB_3 = "tabs3";
    private String TAB_4 = "tabs4";
    private FragmentManager mFragmentManager;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Utils.setColorStatusBar(getActivity(), R.color.colorPrimaryDark);
        mFragmentManager = getChildFragmentManager();
        initialiseTabHost(savedInstanceState);

        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab")); //set the tab as per the saved state
        }
    }

    private void initialiseTabHost(Bundle args) {
        mTabHost.setup();
        TabInfo tabInfo = null;
        addTab(this.mTabHost, this.mTabHost.newTabSpec(TAB_1).setIndicator(createTabView(R.layout.tabs1_icon, R.drawable.tabbar_member_normal, "Tabs 1", R.color.black_1)), (tabInfo = new TabInfo(TAB_1, Tab1Fragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        addTab(this.mTabHost, this.mTabHost.newTabSpec(TAB_2).setIndicator(createTabView(R.layout.tabs_icon, R.drawable.tabbar_member_normal, "Tabs 2", R.color.black_1)), (tabInfo = new TabInfo(TAB_2, Tab2Fragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        addTab(this.mTabHost, this.mTabHost.newTabSpec(TAB_3).setIndicator(createTabView(R.layout.tabs1_icon, R.drawable.tabbar_member_normal, "Tabs 3", R.color.black_1)), (tabInfo = new TabInfo(TAB_3, Tab3Fragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        addTab(this.mTabHost, this.mTabHost.newTabSpec(TAB_4).setIndicator(createTabView(R.layout.tabs_icon, R.drawable.tabbar_member_normal, "Tabs 4", R.color.black_1)), (tabInfo = new TabInfo(TAB_4, Tab4Fragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        // Default to first tab
        this.onTabChanged(TAB_1);
        //
        mTabHost.setOnTabChangedListener(this);
    }

    private void addTab(TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {
        // Attach a Tab view factory to the spec
        tabSpec.setContent(new TabFactory(mContext));
        String tag = tabSpec.getTag();
        Log.i(TAG, "===========>tag: " + tag);

        // Check to see if we already have a fragment for this tab, probably
        // from a previously saved state.  If so, deactivate it, because our
        // initial state is that a tab isn't shown.
        //tabInfo.fragment = getChildFragmentManager().findFragmentByTag(tag);
        tabInfo.fragment = mFragmentManager.findFragmentByTag(tag);
        if (tabInfo.fragment != null && !tabInfo.fragment.isDetached()) {
            //FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.detach(tabInfo.fragment);
            ft.commit();
            //getChildFragmentManager().executePendingTransactions();
            mFragmentManager.executePendingTransactions();
        }

        tabHost.addTab(tabSpec);

    }

    private View createTabView(final int layoutid, final int imgid, final String name, final int color) {
        View view = LayoutInflater.from(mContext).inflate(layoutid, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
        imageView.setImageDrawable(getResources().getDrawable(imgid));
        TextView txtName = (TextView) view.findViewById(R.id.txtName);
        txtName.setText(name);
        txtName.setTextColor(view.getResources().getColor(color));
        return view;
    }

    @Override
    public void onTabChanged(String tag) {
        TabInfo newTab = this.mapTabInfo.get(tag);
        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {


            if (i == 0) {
                View view = mTabHost.getTabWidget().getChildAt(0);
                ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_tabbar_settings));
                TextView txtName = (TextView) view.findViewById(R.id.txtName);
                txtName.setText(R.string.txt_tab1);
                txtName.setTextColor(view.getResources().getColor(R.color.black_1));
            } else if (i == 1) {
                View view = mTabHost.getTabWidget().getChildAt(1);
                ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_tabbar_settings));
                TextView txtName = (TextView) view.findViewById(R.id.txtName);
                txtName.setText(R.string.txt_tab2);
                txtName.setTextColor(view.getResources().getColor(R.color.black_1));
            } else if (i == 2) {
                View view = mTabHost.getTabWidget().getChildAt(2);
                ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);

                imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_tabbar_settings));
                TextView txtName = (TextView) view.findViewById(R.id.txtName);
                txtName.setText(R.string.txt_tab3);
                txtName.setTextColor(view.getResources().getColor(R.color.black_1));

            } else if (i == 3) {
                View view = mTabHost.getTabWidget().getChildAt(3);
                ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_tabbar_settings));
                TextView txtName = (TextView) view.findViewById(R.id.txtName);
                txtName.setText(R.string.txt_tab4);

                txtName.setTextColor(view.getResources().getColor(R.color.black_1));
            }
            mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));

        }
        if (newTab != null) {
            if (mapFragment.get(newTab.tag) == null) {
                newTab.fragment = Fragment.instantiate(mContext,
                        newTab.clss.getName(), newTab.args);
                addFragment(newTab.fragment, newTab.tag);
                mapFragment.put(newTab.tag, newTab.fragment);
            } else {
                addFragment(mapFragment.get(newTab.tag), newTab.tag);
            }

        }

        if (TAB_1.equals(tag)) {
            View view = mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab());
            ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_tabbar_settings_yellow));
            TextView txtName = (TextView) view.findViewById(R.id.txtName);
            txtName.setText(R.string.txt_tab1);
            txtName.setTextColor(view.getResources().getColor(R.color.yellow));
//            ((HomeActivity) mContext).setController(getString(R.string.txt_tab1), false, false);
//            ((OnSelectedTabListener) mContext).onTabSelected(0, newTab.fragment);

        } else if (TAB_2.equals(tag)) {
            View view = mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab());
            ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_tabbar_settings_yellow));
            TextView txtName = (TextView) view.findViewById(R.id.txtName);
            txtName.setText(R.string.txt_tab2);
            txtName.setTextColor(view.getResources().getColor(R.color.yellow));
//            ((HomeActivity) mContext).setController(getString(R.string.txt_tab2), false, false);
//            ((OnSelectedTabListener) mContext).onTabSelected(1, newTab.fragment);
        } else if (TAB_3.equals(tag)) {
            View view = mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab());
            ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_tabbar_settings_yellow));
            TextView txtName = (TextView) view.findViewById(R.id.txtName);
            txtName.setText(R.string.txt_tab3);
            txtName.setTextColor(view.getResources().getColor(R.color.yellow));
//            ((HomeActivity) mContext).setController(getString(R.string.txt_tab3), false, false);
//            ((OnSelectedTabListener) mContext).onTabSelected(2, newTab.fragment);

        } else if (TAB_4.equals(tag)) {
            View view = mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab());
            ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_tabbar_settings_yellow));
            TextView txtName = (TextView) view.findViewById(R.id.txtName);
            txtName.setText(R.string.txt_tab4);
            txtName.setTextColor(view.getResources().getColor(R.color.yellow));
//            ((HomeActivity) mContext).setController(getString(R.string.txt_tab4), false, false);
//            ((OnSelectedTabListener) mContext).onTabSelected(3, newTab.fragment);
        }


    }

    private void addFragment(Fragment frag, String tag) {
        //FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if (frag.isAdded()) {
            ft.show(frag);
        } else {
            ft.add(R.id.realtabcontent, frag);
        }

        for (Map.Entry<String, Fragment> entry : mapFragment.entrySet()) {
            if (entry.getValue() != null && entry.getValue() != mapFragment.get(tag) && entry.getValue().isAdded()) {
                ft.hide(entry.getValue());
            }
        }

        ft.commit();

    }

    class TabFactory implements TabHost.TabContentFactory {

        private final Context mContext;

        /**
         * @param context
         */
        public TabFactory(Context context) {
            mContext = context;
        }

        /**
         * (non-Javadoc)
         *
         * @see TabHost.TabContentFactory#createTabContent(String)
         */
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }

    }

    public Fragment getFragment(String tag) {
        TabInfo tab = this.mapTabInfo.get(tag);
        if (tab == null)
            return null;
        return mapFragment.get(tab.tag);
    }

    private class TabInfo {
        private String tag;
        private Class<?> clss;
        private Bundle args;
        private Fragment fragment;

        TabInfo(String tag, Class<?> clazz, Bundle args) {
            this.tag = tag;
            this.clss = clazz;
            this.args = args;
        }

    }
}
