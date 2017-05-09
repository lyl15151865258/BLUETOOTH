package com.jsmetter.bluetooth.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsmetter.bluetooth.R;
import com.jsmetter.bluetooth.base.BaseFragment;
import com.jsmetter.bluetooth.util.TDevice;

import butterknife.Bind;
import butterknife.ButterKnife;

//import com.jsmetter.bluetooth.util.UIHelper;
//import com.jsmetter.bluetooth.util.UpdateManager;

public class AboutJSMETTERFragment extends BaseFragment {

    @Bind(R.id.tv_version)
    TextView mTvVersionStatus;

    @Bind(R.id.tv_version_name)
    TextView mTvVersionName;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void initView(View view) {
//        view.findViewById(R.id.rl_check_update).setOnClickListener(this);
//        view.findViewById(R.id.rl_grade).setOnClickListener(this);
//        view.findViewById(R.id.rl_gitapp).setOnClickListener(this);
//        view.findViewById(R.id.tv_oscsite).setOnClickListener(this);
//        view.findViewById(R.id.tv_knowmore).setOnClickListener(this);
    }

    @Override
    public void initData() {
        mTvVersionName.setText(TDevice.getVersionName());

    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.rl_check_update:
                onClickUpdate();
                break;
            case R.id.rl_grade:
                TDevice.openAppInMarket(getActivity());
                break;
            case R.id.rl_gitapp:
                boolean res = TDevice.openAppActivity(getActivity(),
                        "net.oschina.gitapp", "net.oschina.gitapp.WelcomePage");

                if (!res) {
                    if (!TDevice.isHaveMarket(getActivity())) {
                       // UIHelper.openExternalBrowser(getActivity(), "http://git.oschina.net/appclient");
                    } else {
                        TDevice.gotoMarket(getActivity(), "net.oschina.gitapp");
                    }
                }
                break;
            case R.id.tv_oscsite:
               // UIHelper.openInternalBrowser(getActivity(), "https://www.oschina.net");
                break;
            case R.id.tv_knowmore:
               //UIHelper.openInternalBrowser(getActivity(), "https://www.oschina.net/home/aboutosc");
                break;
            default:
                break;
        }
    }

    private void onClickUpdate() {

        //new UpdateManager(getActivity(), true).checkUpdate();
    }
}
