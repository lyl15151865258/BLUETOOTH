package com.jsmetter.bluetooth.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.jsmetter.bluetooth.R;
import com.jsmetter.bluetooth.adapter.ViewPageFragmentAdapter;
import com.jsmetter.bluetooth.base.BaseViewPagerFragment;
import com.jsmetter.bluetooth.interf.OnTabReselectListener;

//import com.jsmetter.bluetooth.base.BaseListFragment;
//import com.jsmetter.bluetooth.bean.BlogList;
//import com.jsmetter.bluetooth.bean.NewsList;
//import com.jsmetter.bluetooth.bean.SimpleBackPage;
//import com.jsmetter.bluetooth.improve.base.fragments.BaseGeneralListFragment;
//import com.jsmetter.bluetooth.improve.general.fragments.BlogFragment;
//import com.jsmetter.bluetooth.improve.general.fragments.EventFragment;
//import com.jsmetter.bluetooth.improve.general.fragments.NewsFragment;
//import com.jsmetter.bluetooth.improve.general.fragments.QuestionFragment;
//import com.jsmetter.bluetooth.util.UIHelper;

/**
 * 综合Tab界面
 */
public class WaterMeterPagerFragment extends BaseViewPagerFragment implements
        OnTabReselectListener {

    @Override
    protected void onSetupTabAdapter(ViewPageFragmentAdapter adapter) {

        //FrameLayout generalActionBar = (FrameLayout) mRoot.findViewById(R.id.general_actionbar);
        //TextView tvTitle = (TextView) generalActionBar.findViewById(R.id.tv_explore_scan);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) generalActionBar.getLayoutParams();
           // layoutParams.topMargin = UIUtil.getStatusHeight(getActivity());
        }
        //ImageView ivDiscover = (ImageView) generalActionBar.findViewById(R.id.iv_explore_discover);

       // tvTitle.setText(R.string.main_tab_name_watermeter);
        //ivDiscover.setVisibility(View.VISIBLE);
        //ivDiscover.setOnClickListener(new View.OnClickListener() {
       //     @Override
        //    public void onClick(View v) {
              //  UIHelper.showSimpleBack(getActivity(), SimpleBackPage.SEARCH);
        //    }
        //});


        String[] title = getResources().getStringArray(
                R.array.watermater_viewpage_arrays);

        adapter.addTab(title[0], "watermeterdata", MeterDataFragment.class,
                getBundle(0));
        adapter.addTab(title[1], "watermeterlcd", MeterLcdFragment.class,
                getBundle(1));
        adapter.addTab(title[2], "watermeterparam", MeterParamFragment.class,
                getBundle(2));
        adapter.addTab(title[3], "watermeteradjust", MeterAdjustFragment.class,
                getBundle(3));

    }

    private Bundle getBundle(int newType) {
        Bundle bundle = new Bundle();
        bundle.putInt("BUNDLE_KEY_CATALOG", newType);
        return bundle;
    }

    @Override
    protected void setScreenPageLimit() {
        mViewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 基类会根据不同的catalog展示相应的数据
     *
     * @param catalog 要显示的数据类别
     * @return
     */
    private Bundle getBundle(String catalog) {
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_BLOG_TYPE", catalog);
        return bundle;
    }

    public  Fragment getCurrentFragment(){
        int currentIndex = mViewPager.getCurrentItem();
        Fragment currentFragment = getChildFragmentManager().getFragments()
                .get(currentIndex);
        return currentFragment;
    }
    @Override
    public void onTabReselect() {
        //Fragment fragment = mTabsAdapter.getItem(mViewPager.getCurrentItem());
//        if (fragment != null && fragment instanceof BaseGeneralListFragment) {
//            ((BaseGeneralListFragment) fragment).onTabReselect();
//        }

        try {
            int currentIndex = mViewPager.getCurrentItem();
            Fragment currentFragment = getChildFragmentManager().getFragments()
                    .get(currentIndex);
            if (currentFragment != null
                    && currentFragment instanceof OnTabReselectListener) {
                OnTabReselectListener listener = (OnTabReselectListener) currentFragment;
                listener.onTabReselect();
            }
        } catch (NullPointerException e) {
        }


    }
}