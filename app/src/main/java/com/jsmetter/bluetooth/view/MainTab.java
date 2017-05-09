package com.jsmetter.bluetooth.view;

import com.jsmetter.bluetooth.R;
import com.jsmetter.bluetooth.fragment.HeatMeterPagerFragment;
import com.jsmetter.bluetooth.fragment.MeterGprsFragment;
import com.jsmetter.bluetooth.fragment.MeterTopworxFragment;
import com.jsmetter.bluetooth.fragment.WaterMeterPagerFragment;


public enum MainTab {

	/*
	NEWS(0, R.string.main_tab_name_news, R.drawable.tab_icon_new,
			NewsViewPagerFragment.class),
			*/

    NEWS(0, R.string.main_tab_name_watermeter, R.drawable.tab_icon_new,
            WaterMeterPagerFragment.class),

    TWEET(1, R.string.main_tab_name_hotmeter, R.drawable.tab_icon_tweet,
            HeatMeterPagerFragment.class),

    QUICK(2, R.string.main_tab_name_bluetooth, R.drawable.tab_icon_new,
            null),
    ME(3, R.string.main_tab_name_topworx, R.drawable.tab_icon_me,
            MeterTopworxFragment.class),
    EXPLORE(4, R.string.main_tab_name_gprs, R.drawable.tab_icon_explore,
            MeterGprsFragment.class);



    private int idx;
    private int resName;
    private int resIcon;
    private Class<?> clz;

    private MainTab(int idx, int resName, int resIcon, Class<?> clz) {
        this.idx = idx;
        this.resName = resName;
        this.resIcon = resIcon;
        this.clz = clz;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getResName() {
        return resName;
    }

    public void setResName(int resName) {
        this.resName = resName;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }
}
