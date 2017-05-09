package com.jsmetter.bluetooth.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.jsmetter.bluetooth.AppConfig;
import com.jsmetter.bluetooth.AppContext;
import com.jsmetter.bluetooth.AppManager;
import com.jsmetter.bluetooth.R;
import com.jsmetter.bluetooth.base.BaseApplication;
import com.jsmetter.bluetooth.bean.Constants;
import com.jsmetter.bluetooth.bean.MBUS;
import com.jsmetter.bluetooth.bean.ParameterProtocol;
import com.jsmetter.bluetooth.bean.Protocol;
import com.jsmetter.bluetooth.bean.SSumHeat;
import com.jsmetter.bluetooth.bean.TcpUdpParam;
import com.jsmetter.bluetooth.cache.DataCleanManager;
import com.jsmetter.bluetooth.fragment.MeterAdjustFragment;
import com.jsmetter.bluetooth.fragment.MeterDataFragment;
import com.jsmetter.bluetooth.fragment.MeterGprsFragment;
import com.jsmetter.bluetooth.fragment.MeterLcdFragment;
import com.jsmetter.bluetooth.fragment.MeterParamFragment;
import com.jsmetter.bluetooth.fragment.MeterTopworxFragment;
import com.jsmetter.bluetooth.fragment.WaterMeterPagerFragment;
import com.jsmetter.bluetooth.interf.BaseViewInterface;
import com.jsmetter.bluetooth.util.Analysise;
import com.jsmetter.bluetooth.util.Frequent;
import com.jsmetter.bluetooth.widget.MyFragmentTabHost;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TabHost.OnTabChangeListener, BaseViewInterface, View.OnClickListener,
        View.OnTouchListener {

    //------------------------------------------对蓝牙操作接口

    private final static int REQUEST_CONNECT_DEVICE = 1;    //宏定义查询设备句柄
    private final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB";   //SPP服务UUID号
    private InputStream is;    //输入流，用来接收蓝牙数据
    public static String smsg = "";    //显示用数据缓存
    @SuppressWarnings("unused")
    private static String fmsg = "";    //保存用数据缓存
    public String filename = ""; //用来保存存储的文件名
    public int time = 0;
    BluetoothDevice _device = null;     //蓝牙设备
    static BluetoothSocket _socket = null;      //蓝牙通信socket
    boolean _discoveryFinished = false;
    boolean bRun = true;
    boolean bThread = false;
    static boolean flag = false;
    boolean flag_time_cancel = false;
    boolean flag_set_time = false;
    private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();    //获取本地蓝牙适配器，即蓝牙设备
    //end------------------------------------------对蓝牙操作接口
    private int indexPageFragment;

    private Fragment currentFragment;

    private Context mContext4Umeng;
    private Context context;
    private final String packageName4Umeng = "MainActivity";

    private long mBackPressedTime;

    @Bind(android.R.id.tabhost)
    MyFragmentTabHost mTabHost;

    @Bind(R.id.tvFrameTitle)
    TextView tvFrameTitle;

    @Bind(R.id.tvBTStatus)
    TextView tvBTStatus;

    private CharSequence mTitle;
    private String[] mTitles;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction().equals(Constants.INTENT_ACTION_NOTICE)) {
//            }

        }
    };

    /**
     * Used to store the last screen title. For use in
     * {@link #restoreActionBar()}.
     */

    @Bind(R.id.quick_option_iv)
    View mAddBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.mContext4Umeng = this;
        context = this;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();


        AppManager.getAppManager().addActivity(this);

        handleIntent(getIntent());
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //如果打开本地蓝牙设备不成功，提示信息，结束程序
        if (_bluetooth == null) {
            Toast.makeText(this, "无法打开手机蓝牙，请确认手机是否有蓝牙功能！", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // 设置设备可以被搜索
        new Thread() {
            public void run() {
                if (_bluetooth.isEnabled() == false) {
                    _bluetooth.enable();
                }
            }
        }.start();
    }

    @Override
    public void onTabChanged(String tabId) {
        currentFragment = getCurrentFragment();
        final int size = mTabHost.getTabWidget().getTabCount();
        for (int i = 0; i < size; i++) {
            View v = mTabHost.getTabWidget().getChildAt(i);
            if (i == mTabHost.getCurrentTab()) {
                v.setSelected(true);
                mTitle = mTitles[i == 4 || i == 3 ? i - 1 : i];
                this.tvFrameTitle.setText(mTitle);
                // WaterMeterPagerFragment wfragment = (WaterMeterPagerFragment)getCurrentFragment();
            } else {
                v.setSelected(false);
            }
        }
        if (tabId.equals(getString(MainTab.ME.getResName()))) {
            // mBvNotice.setText("");
            // mBvNotice.hide();
        }
        supportInvalidateOptionsMenu();
    }

    @Override
    public void initData() {

    }

    @Override
    public void onBackPressed() {

        boolean isDoubleClick = BaseApplication.get(AppConfig.KEY_DOUBLE_CLICK_EXIT, true);

        if (isDoubleClick) {
            long curTime = SystemClock.uptimeMillis();
            if ((curTime - mBackPressedTime) < (3 * 1000)) {
                finish();
            } else {
                mBackPressedTime = curTime;
                Toast.makeText(this, R.string.tip_double_click_exit, Toast.LENGTH_LONG).show();
            }
        } else {
            finish();
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            // 点击了快速操作按钮
            case R.id.quick_option_iv:
                try {
                    Connect();
                } catch (Exception e) {
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        //if (mbluetoothService != null)
        //   mbluetoothService.stop();

        if (_socket != null)  //关闭连接socket
            try {
                _socket.close();
            } catch (IOException e) {
            }
        try {
            _bluetooth.disable();  //关闭蓝牙服务
        } catch (Exception ex) {
        }
        //NoticeUtils.unbindFromService(this);
        unregisterReceiver(mReceiver);
        //mReceiver = null;
        // NoticeUtils.tryToShutDown(this);
        // AppManager.getAppManager().removeActivity(this);
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    @Override
    public void initView() {
        mTitle = getResources().getString(R.string.main_tab_name_watermeter);
        mTitles = getResources().getStringArray(R.array.main_titles_arrays);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        if (android.os.Build.VERSION.SDK_INT > 10) {
            mTabHost.getTabWidget().setShowDividers(0);
        }
        this.tvFrameTitle.setText(mTitle);
        this.tvBTStatus.setText(R.string.app_bluetoothunconnect);
        initTabs();

        // 中间按键图片触发
        mAddBt.setOnClickListener(this);

        mTabHost.setCurrentTab(0);
        mTabHost.setOnTabChangedListener(this);

        IntentFilter filter = new IntentFilter(Constants.INTENT_ACTION_NOTICE);
        filter.addAction(Constants.INTENT_ACTION_LOGOUT);
        registerReceiver(mReceiver, filter);
        // NoticeUtils.bindToService(this);

        if (AppContext.isFristStart()) {
            DataCleanManager.cleanInternalCache(AppContext.getInstance());
            AppContext.setFristStart(false);
        }

        checkUpdate();
    }

    private void checkUpdate() {
        if (!AppContext.get(AppConfig.KEY_CHECK_UPDATE, true)) {
            return;
        }
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                new UpdateManager(MainActivity.this, false).checkUpdate();
//            }
//        }, 2000);
    }

    @SuppressWarnings("deprecation")
    private void initTabs() {
        MainTab[] tabs = MainTab.values();
        int size = tabs.length;
        for (int i = 0; i < size; i++) {
            MainTab mainTab = tabs[i];
            TabHost.TabSpec tab = mTabHost.newTabSpec(getString(mainTab.getResName()) + this.toString());
            View indicator = View.inflate(this, R.layout.tab_indicator, null);
            TextView title = (TextView) indicator.findViewById(R.id.tab_title);
            ImageView icon = (ImageView) indicator.findViewById(R.id.iv_user_flow_icon);

            Drawable drawable = this.getResources().getDrawable(mainTab.getResIcon());
            icon.setImageDrawable(drawable);
            //title.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            if (i == 2) {
                indicator.setVisibility(View.INVISIBLE);
                mTabHost.setNoTabChangedTag(getString(mainTab.getResName()));
            }
            title.setText(getString(mainTab.getResName()));
            tab.setIndicator(indicator);
            mTabHost.addTab(tab, mainTab.getClz(), null);

            if (mainTab.equals(MainTab.ME)) {
//                    View cn = indicator.findViewById(R.id.tab_mes);
//                    mBvNotice = new BadgeView(MainActivity.this, cn);
//                    mBvNotice.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
//                    mBvNotice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
//                    mBvNotice.setBackgroundResource(R.mipmap.notification_bg);
//                    mBvNotice.setGravity(Gravity.CENTER);
            }
            mTabHost.getTabWidget().getChildAt(i).setOnTouchListener(this);
        }
    }


    @SuppressWarnings("deprecation")
    private void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(mTitle);
        }
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentByTag(
                mTabHost.getCurrentTabTag());
    }

    /**
     * 处理传进来的intent
     */
    private void handleIntent(Intent intent) {
        if (intent == null)
            return;
        String action = intent.getAction();
        if (action != null && action.equals(Intent.ACTION_VIEW)) {
            // UIHelper.showUrlRedirect(this, intent.getDataString());
        } else if (intent.getBooleanExtra("NOTICE", false)) {
            // notificationBarClick(intent);
        }
    }

    //连接功能函数
    private void Connect() {
        if (!_bluetooth.isEnabled()) {  //如果蓝牙服务不可用则提示
            Toast.makeText(this, " 打开蓝牙中...", Toast.LENGTH_LONG).show();
            return;
        }

        //如未连接设备则打开DeviceListActivity进行设备搜索
        //Button Buttonconnect = (Button) findViewById(R.id.Buttonconnect);
        if (_socket == null) {
            Intent serverIntent = new Intent(this, DeviceListActivity.class); //跳转程序设置
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);  //设置返回宏定义
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle(R.string.app_notice).setIcon(android.R.drawable.ic_dialog_info)
                    .setNegativeButton(R.string.amenderr_setCancle, null);
            builder.setPositiveButton(R.string.amenderr_setOk, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //关闭连接socket
                    try {
                        if (flag) {
                            if (is != null) {
                                try {
                                    is.close();
                                    is = null;
                                } catch (Exception ex) {
                                }
                            }
                            if (_socket != null) {
                                try {
                                    _socket.close();
                                    _socket = null;
                                } catch (Exception e) {
                                }
                            }
                            bRun = false;
                            //Buttonconnect.setText("连接");

                            flag = false;
                        }//连接标志位
                        tvBTStatus.setText(R.string.app_bluetoothunconnect);
                        // TextViewconnect.setText("蓝牙未连接");
                    } catch (Exception e) {
                    }
                }
            });
            builder.show();
        }
        return;
    }

    //接收活动结果，响应startActivityForResult()
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:     //连接结果，由DeviceListActivity设置返回
                // 响应返回结果
                if (resultCode == Activity.RESULT_OK) {   //连接成功，由DeviceListActivity设置返回
                    // MAC地址，由DeviceListActivity设置返回
                    String address = data.getExtras()
                            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    // 得到蓝牙设备句柄
                    _device = _bluetooth.getRemoteDevice(address);

                    // 用服务号得到socket
                    try {
                        _socket = _device.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
                    } catch (IOException e) {
                        Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
                    }
                    //连接socket
                    //Button Buttonconnect = (Button) findViewById(R.id.Buttonconnect);
                    try {
                        _socket.connect();
                        Toast.makeText(this, "连接" + _device.getName() + "成功！", Toast.LENGTH_SHORT).show();
                        // Buttonconnect.setText("断开");
                        flag = true;                            //连接标志位
                        //TextViewconnect.setText("已连接到："+_device.getName());
                        this.tvBTStatus.setText("已连接到：" + _device.getName());
                    } catch (IOException e) {
                        try {
                            Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
                            _socket.close();
                            _socket = null;
                        } catch (IOException ee) {
                            Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
                        }

                        return;
                    }

                    //打开接收线程
                    try {
                        is = _socket.getInputStream();   //得到蓝牙数据输入流
                    } catch (IOException e) {
                        Toast.makeText(this, "接收数据失败！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (bThread == false) {
                        ReadThread.start();
                        bThread = true;
                    } else {
                        bRun = true;
                    }
                }
                break;
            default:
                break;
        }
    }

    //接收数据线程
    Thread ReadThread = new Thread() {

        public void run() {
            int num = 0;
            byte[] buffer = new byte[1024];
            int i = 0;
            bRun = true;
            //接收线程
            while (true) {
                try {
                    while (is.available() == 0) {
                        while (bRun == false) {
                        }
                    }
                    while (true) {
                        num = is.read(buffer);         //读入数据
                        String s0 = "";
                        for (i = 0; i < num; i++) {
                            int b = (int) buffer[i];
                            if (b < 0) b = 256 + b;
                            s0 = s0 + Integer.toHexString(b / 16) + Integer.toHexString(b % 16);
                        }
                        fmsg += s0;    //保存收到数据
                        smsg += s0;   //写入接收缓存
                        if (is.available() == 0) break;  //短时间没有数据才跳出进行显示
                    }
                } catch (IOException e) {
                    handler.sendMessage(handler.obtainMessage(11));
                    break;
                }
                //发送显示消息，进行显示刷新
                handler.sendMessage(handler.obtainMessage());
            }
        }
    };


    //消息处理队列
    Handler handler = new Handler() {
        @SuppressLint("DefaultLocale")
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 11:
                    Toast.makeText(context, "蓝牙连接断开！", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    currentFragment = getCurrentFragment();
                    Protocol protocol = new Protocol();
                    MBUS mbus = new MBUS();
                    SSumHeat ssumheat = new SSumHeat();
                    ParameterProtocol parameterprotocol = new ParameterProtocol();
                    String data = "";
                    if (currentFragment instanceof WaterMeterPagerFragment) {
                        WaterMeterPagerFragment fragment = (WaterMeterPagerFragment) currentFragment;
                        Fragment subfragment = fragment.getCurrentFragment();
                        if (subfragment instanceof MeterParamFragment) {
                            MeterParamFragment meterParamFragment = (MeterParamFragment) subfragment;
                            data = smsg.toUpperCase().replace(" ", "");
                            switch (Analysise.analysiseData(data, protocol, ssumheat, mbus, parameterprotocol)) {
                                case 11:
                                    ((EditText) meterParamFragment.getView().findViewById(R.id.EditTextmeterid)).setText(parameterprotocol.getMeterid());
                                    ((EditText) meterParamFragment.getView().findViewById(R.id.EditTextslope)).setText(parameterprotocol.getSlope());
                                    ((EditText) meterParamFragment.getView().findViewById(R.id.EditTextstartf)).setText(parameterprotocol.getStartx());
                                    ((EditText) meterParamFragment.getView().findViewById(R.id.EditTextamendx)).setText(parameterprotocol.getAmendx());
                                    ((EditText) meterParamFragment.getView().findViewById(R.id.EditTextdiv1)).setText(parameterprotocol.getDivid1());
                                    ((EditText) meterParamFragment.getView().findViewById(R.id.EditTextdiv2)).setText(parameterprotocol.getDivid2());
                                    ((EditText) meterParamFragment.getView().findViewById(R.id.EditTextdiv3)).setText(parameterprotocol.getDivid3());
                                    ((EditText) meterParamFragment.getView().findViewById(R.id.EditTextsleeptime)).setText(parameterprotocol.getSleeptime());//003600
                                    if (parameterprotocol.getUnitStr().equals("003600")) {
                                        ((RadioButton) meterParamFragment.getView().findViewById(R.id.RadioButtonunitm)).setChecked(true);
                                        ((RadioButton) meterParamFragment.getView().findViewById(R.id.RadioButtonunitgal)).setChecked(false);
                                    } else if (parameterprotocol.getUnitStr().equals("001000")) {
                                        ((RadioButton) meterParamFragment.getView().findViewById(R.id.RadioButtonunitm)).setChecked(false);
                                        ((RadioButton) meterParamFragment.getView().findViewById(R.id.RadioButtonunitgal)).setChecked(true);
                                    }


                                    break;
                                default:
                                    break;
                            }
                        } else if (subfragment instanceof MeterDataFragment) {
                            MeterDataFragment meterDataFragment = (MeterDataFragment) subfragment;
                            data = smsg.toUpperCase().replace(" ", "");
                            switch (Analysise.analysiseData(data, protocol, ssumheat, mbus, parameterprotocol)) {
                                case 1: {
                                    if (((CheckBox) meterDataFragment.getView().findViewById(R.id.readdata_check)).isChecked())
                                        Analysise.dataunittostandard(protocol);
                                    //if (ReadDataActivity.readdata_check.isChecked()) Analysise.dataunittostandard(protocol);
                                    ((EditText) meterDataFragment.getView().findViewById(R.id.EditTextmeterid)).setText("");
                                    ((Button) meterDataFragment.getView().findViewById(R.id.Buttonreadparameter)).setText("获取表号");
                                    meterDataFragment.addlistview(protocol);
                                    //ReadDataActivity.EditTextmeterid.setText("");
                                    // ReadDataActivity.Buttonreadparameter.setText("获取表号");
                                    // ReadDataActivity.addlistview(protocol);
                                    String shoowreport = "";
                                    if (!protocol.getMeterID().equals("")) {
                                        if (protocol.getProductTypeTX().equals("10")) {
                                            shoowreport = shoowreport + " " + "产品类型" + " " + protocol.getProductTypeTX();

                                            shoowreport = shoowreport + " " + "正向累计流量" + " " + protocol.getTotal() + " " + protocol.getTotalUnit();

                                            shoowreport = shoowreport + " " + "反向累计流量" + " " + protocol.getOppositeTotal() + " " + protocol.getOppositeTotalUnit();

                                            shoowreport = shoowreport + " " + "瞬时" + " " + protocol.getFlowRate() + " " + protocol.getFlowRateUnit();

                                            if (!protocol.getValveStatus().equals("-")) {
                                                shoowreport = shoowreport + " " + "阀门" + " " + protocol.getValveStatus() + " " + "";
                                            }
                                            ;

                                            if (!protocol.getTimeInP().equals("-")) {
                                                shoowreport = shoowreport + " " + "时间" + " " + protocol.getTimeInP() + " ";
                                            }
                                            ;

                                            if (!protocol.getStatus().equals("-")) {
                                                shoowreport = shoowreport + " " + "状态" + " " + protocol.getStatus();
                                            }
                                            ;
                                        }
                                        ;
                                        if (protocol.getProductTypeTX().equals("20")) {
                                            shoowreport = shoowreport + " " + "产品类型" + " " + protocol.getProductTypeTX();
                                            if (!protocol.getMBUSAddress().equals("-")) {
                                                shoowreport = shoowreport + " " + "MBUS" + " " + protocol.getMBUSAddress();
                                            }
                                            ;
                                            shoowreport = shoowreport + " " + "累计冷量" + " " + protocol.getSumCool() + " " + protocol.getSumCoolUnit();

                                            shoowreport = shoowreport + " " + "累计热量" + " " + protocol.getSumHeat() + " " + protocol.getSumHeatUnit();

                                            shoowreport = shoowreport + " " + "累计流量" + " " + protocol.getTotal() + " " + protocol.getTotalUnit();

                                            shoowreport = shoowreport + " " + "功率" + " " + protocol.getPower() + " " + protocol.getPowerUnit();

                                            shoowreport = shoowreport + " " + "瞬时" + " " + protocol.getFlowRate() + " " + protocol.getFlowRateUnit();
                                            if (protocol.getSumOpenValveM() != 0) {
                                                shoowreport = shoowreport + " " + "累计开阀" + " " + protocol.getSumOpenValveM() + " " + "Min";
                                            }
                                            ;
                                            if (!protocol.getCloseTime().equals("-")) {
                                                shoowreport = shoowreport + " " + "截止日期" + " " + protocol.getCloseTime() + " ";
                                            }
                                            ;
                                            if (!protocol.getLosePowerTime().equals("-")) {
                                                shoowreport = shoowreport + " " + "断电时间" + " " + protocol.getLosePowerTime() + " " + "Min";
                                            }
                                            ;
                                            if (!protocol.getLoseConTime().equals("-")) {
                                                shoowreport = shoowreport + " " + "无通讯时间" + " " + protocol.getLoseConTime() + " " + "h";
                                            }
                                            ;
                                            if (protocol.getInsideT() != 0) {
                                                shoowreport = shoowreport + " " + "室温" + " " + protocol.getInsideT() + " " + "℃";
                                            }
                                            ;
                                            if (!protocol.getInsideTSet().equals("-")) {
                                                shoowreport = shoowreport + " " + "设定室温" + " " + protocol.getInsideTSet() + " " + "℃";
                                            }
                                            ;
                                            if (!protocol.getValveStatus().equals("-")) {
                                                shoowreport = shoowreport + " " + "阀门" + " " + protocol.getValveStatus() + " " + "";
                                            }
                                            ;
                                            if (protocol.getT1InP() != 0) {
                                                shoowreport = shoowreport + " " + "T1" + " " + protocol.getT1InP() + " " + "℃";
                                            }
                                            ;
                                            if (protocol.getT2InP() != 0) {
                                                shoowreport = shoowreport + " " + "T2" + " " + protocol.getT2InP() + " " + "℃";
                                            }
                                            ;
                                            if (protocol.getT1InP() != 0) {
                                                shoowreport = shoowreport + " " + "工作时间" + " " + protocol.getWorkTimeInP() + " " + "h";
                                            }
                                            ;
                                            if (!protocol.getTimeInP().equals("-")) {
                                                shoowreport = shoowreport + " " + "时间" + " " + protocol.getTimeInP() + " ";
                                            }
                                            ;
                                            if (!protocol.getVol().equals("-")) {
                                                shoowreport = shoowreport + " " + "电压" + " " + protocol.getVol() + " " + "V";
                                            }
                                            ;
                                            if (!protocol.getStatus().equals("-")) {
                                                shoowreport = shoowreport + " " + "状态" + " " + protocol.getStatus();
                                            }
                                            ;
                                        }
                                        ;
                                        if (protocol.getProductTypeTX().equals("49")) {
                                            shoowreport = shoowreport + " " + "产品类型" + " " + protocol.getProductTypeTX();

                                            shoowreport = shoowreport + " " + "累计开阀" + " " + protocol.getSumOpenValveM() + " " + "Min";

                                            shoowreport = shoowreport + " " + "截止日期" + " " + protocol.getCloseTime() + " ";

                                            shoowreport = shoowreport + " " + "断电时间" + " " + protocol.getLosePowerTime() + " " + "Min";

                                            shoowreport = shoowreport + " " + "无通讯时间" + " " + protocol.getLoseConTime() + " " + "h";

                                            shoowreport = shoowreport + " " + "室温" + " " + protocol.getInsideT() + " " + "℃";

                                            shoowreport = shoowreport + " " + "设定室温" + " " + protocol.getInsideTSet() + " " + "℃";

                                            shoowreport = shoowreport + " " + "阀门" + " " + protocol.getValveStatus() + " " + "";

                                            shoowreport = shoowreport + " " + "T1" + " " + protocol.getT1InP() + " " + "℃";

                                            shoowreport = shoowreport + " " + "T2" + " " + protocol.getT2InP() + " " + "℃";

                                            shoowreport = shoowreport + " " + "工作时间" + " " + protocol.getWorkTimeInP() + " " + "h";

                                            shoowreport = shoowreport + " " + "时间" + " " + protocol.getTimeInP() + " ";

                                            shoowreport = shoowreport + " " + "电压" + " " + protocol.getVol() + " " + "V";

                                        }
                                        ;
                                    }
                                    ;
                                    smsg = "";
                                    break;
                                }
                                case 11: {
                                    ((EditText) meterDataFragment.getView().findViewById(R.id.EditTextmeterid)).setText(parameterprotocol.getMeterid());
                                    ((Button) meterDataFragment.getView().findViewById(R.id.Buttonreadparameter)).setText("读表数据");

                                    smsg = "";
                                    break;
                                }
                            }
                        } else if (subfragment instanceof MeterLcdFragment) {
                            smsg = "";
                        } else if (subfragment instanceof MeterAdjustFragment) {
                            data = smsg.toUpperCase().replace(" ", "");
                            MeterAdjustFragment meterAdjustFragment = (MeterAdjustFragment) subfragment;
                            switch (Analysise.analysiseData(data, protocol, ssumheat, mbus, parameterprotocol)) {
                                case 1: {
                                    smsg = "";
                                    break;
                                }
                                case 11: {
                                    // AmendErrActivity.EditTextmeterid.setText(parameterprotocol.getMeterid());
                                    EditText EditTextmeterid = (EditText) meterAdjustFragment.getView().findViewById(R.id.EditTextmeterid);
                                    EditText EditTextqn_1 = (EditText) meterAdjustFragment.getView().findViewById(R.id.EditTextqn_1);
                                    EditText EditTextqn2_1 = (EditText) meterAdjustFragment.getView().findViewById(R.id.EditTextqn2_1);
                                    EditText EditTextqn1_1 = (EditText) meterAdjustFragment.getView().findViewById(R.id.EditTextqn1_1);
                                    EditText EditTextqmin_1 = (EditText) meterAdjustFragment.getView().findViewById(R.id.EditTextqmin_1);
                                    EditText EditTextqn_2 = (EditText) meterAdjustFragment.getView().findViewById(R.id.EditTextqn_2);
                                    EditText EditTextqn2_2 = (EditText) meterAdjustFragment.getView().findViewById(R.id.EditTextqn2_2);
                                    EditText EditTextqn1_2 = (EditText) meterAdjustFragment.getView().findViewById(R.id.EditTextqn1_2);
                                    EditText EditTextqmin_2 = (EditText) meterAdjustFragment.getView().findViewById(R.id.EditTextqmin_2);
                                    EditText EditTextqn_3 = (EditText) meterAdjustFragment.getView().findViewById(R.id.EditTextqn_3);
                                    EditText EditTextqn2_3 = (EditText) meterAdjustFragment.getView().findViewById(R.id.EditTextqn2_3);
                                    EditText EditTextqn1_3 = (EditText) meterAdjustFragment.getView().findViewById(R.id.EditTextqn1_3);
                                    EditText EditTextqmin_3 = (EditText) meterAdjustFragment.getView().findViewById(R.id.EditTextqmin_3);

                                    EditTextmeterid.setText(parameterprotocol.getMeterid());
                                    double qnerr, qn2err, qn1err, qminerr, qnold, qn2old, qn1old, qminold, qnnew, qn2new, qn1new, qminnew = 0;
                                    try {
                                        qnerr = Double.valueOf(EditTextqn_1.getText().toString().replace(" ", ""));
                                    } catch (Exception e) {
                                        qnerr = 0;
                                    }
                                    try {
                                        qn2err = Double.valueOf(EditTextqn2_1.getText().toString().replace(" ", ""));
                                    } catch (Exception e) {
                                        qn2err = 0;
                                    }
                                    try {
                                        qn1err = Double.valueOf(EditTextqn1_1.getText().toString().replace(" ", ""));
                                    } catch (Exception e) {
                                        qn1err = 0;
                                    }
                                    try {
                                        qminerr = Double.valueOf(EditTextqmin_1.getText().toString().replace(" ", ""));
                                    } catch (Exception e) {
                                        qminerr = 0;
                                    }
                                    EditTextqn_2.setText(parameterprotocol.getQnx());
                                    EditTextqn2_2.setText(parameterprotocol.getQn2x());
                                    EditTextqn1_2.setText(parameterprotocol.getQn1x());
                                    EditTextqmin_2.setText(parameterprotocol.getQminx());
                                    SharedPreferences sharedPreferences = context.getSharedPreferences("setQnParam", Context.MODE_PRIVATE);
                                    String nMeterid = parameterprotocol.getMeterid();

                                    Map<String, String> map = (Map<String, String>) sharedPreferences.getAll();
                                    if (!map.keySet().contains(nMeterid + "qn_2") &&
                                            !map.keySet().contains(nMeterid + "qn1_2") &&
                                            !map.keySet().contains(nMeterid + "qn2_2") &&
                                            !map.keySet().contains(nMeterid + "qmin_2")) {
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(nMeterid + "qn_2", parameterprotocol.getQnx());
                                        editor.putString(nMeterid + "qn1_2", parameterprotocol.getQn1x());
                                        editor.putString(nMeterid + "qn2_2", parameterprotocol.getQn2x());
                                        editor.putString(nMeterid + "qmin_2", parameterprotocol.getQminx());
                                        editor.commit();//提交修改
                                    }

                                    try {
                                        qnold = Double.valueOf(EditTextqn_2.getText().toString().replace(" ", ""));
                                    } catch (Exception e) {
                                        qnold = 0;
                                    }
                                    try {
                                        qn2old = Double.valueOf(EditTextqn2_2.getText().toString().replace(" ", ""));
                                    } catch (Exception e) {
                                        qn2old = 0;
                                    }
                                    try {
                                        qn1old = Double.valueOf(EditTextqn1_2.getText().toString().replace(" ", ""));
                                    } catch (Exception e) {
                                        qn1old = 0;
                                    }
                                    try {
                                        qminold = Double.valueOf(EditTextqmin_2.getText().toString().replace(" ", ""));
                                    } catch (Exception e) {
                                        qminold = 0;
                                    }
                                    qnnew = qnold * (1 + qnerr * 0.01);
                                    qn2new = qn2old * (1 + qn2err * 0.01);
                                    qn1new = qn1old * (1 + qn1err * 0.01);
                                    qminnew = qminold * (1 + qminerr * 0.01);
                                    EditTextqn_3.setText(String.valueOf((int) qnnew));
                                    EditTextqn2_3.setText(String.valueOf((int) qn2new));
                                    EditTextqn1_3.setText(String.valueOf((int) qn1new));
                                    EditTextqmin_3.setText(String.valueOf((int) qminnew));
                                    smsg = "";
                                    break;
                                }
                            }
                        }
                    } else if (currentFragment instanceof MeterTopworxFragment) {
                        MeterTopworxFragment meterTopworxFragment = (MeterTopworxFragment) currentFragment;
                        data = smsg.toUpperCase().replace(" ", "");
                        if (data == null || data == "")
                            return;
                        if (Analysise.analysiseData(data, protocol, ssumheat, mbus, parameterprotocol) == 1) {
                            ((EditText) meterTopworxFragment.getView().findViewById(R.id.EditTextmeterid)).setText(protocol.getMeterID());
                            if (protocol.getCloseTime() != null && protocol.getCloseTime().length() == 10)
                                ((EditText) meterTopworxFragment.getView().findViewById(R.id.edTopworxEndDate)).setText(protocol.getCloseTime());
                            Toast.makeText(context, "阀门状态：" + protocol.getValveStatus(), Toast.LENGTH_SHORT).show();
                            smsg = "";
                        }
                        //smsg="";
                    } else if (currentFragment instanceof MeterGprsFragment) {
                        MeterGprsFragment meterGprsFragment = (MeterGprsFragment) currentFragment;
                        data = smsg.toUpperCase().replace(" ", "");
                        if (data == null || data == "")
                            return;
                        if (data.indexOf("03794500") != -1) {//SET DISTANCE  OK
                            //if(data.indexOf("SET DISTANCE  OK")!=-1){
                            smsg = "";
                            //SetGprsParamterActivity.btnReadReadingParam.performClick();
                            Toast.makeText(context, "触发抄表成功！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (data.indexOf("03794600") != -1) {//SET DISTANCE  OK
                            //if(data.indexOf("SET DISTANCE  OK")!=-1){
                            smsg = "";
                            //SetGprsParamterActivity.btnReadReadingParam.performClick();
                            Toast.makeText(context, "触发上传成功！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (data.indexOf("5345542044495354414E434520204F4B") != -1) {//SET DISTANCE  OK
                            //if(data.indexOf("SET DISTANCE  OK")!=-1){
                            smsg = "";
                            //SetGprsParamterActivity.btnReadReadingParam.performClick();
                            ((Button) meterGprsFragment.getView().findViewById(R.id.btnReadReadingParam)).performClick();
                            Toast.makeText(context, "修改抄表间隔参数成功！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (data.indexOf("44495354414E4345204552524F52") != -1) {//DISTANCE ERROR
                            smsg = "";
                            Toast.makeText(context, "修改抄表间隔参数失败！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //Toast.makeText(mcontext, smsg, Toast.LENGTH_SHORT).show();
                        if (data.indexOf("4D4F4449465920495031202020204F4B") != -1) {
                            smsg = "";
                            //SetGprsParamterActivity.btnReadComm.performClick();
                            ((Button) meterGprsFragment.getView().findViewById(R.id.btnReadComm)).performClick();
                            Toast.makeText(context, "修改TCPUDP参数成功！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (data.indexOf("534554204D20434C4F434B204F4B") != -1) {
                            smsg = "";
                            //SetGprsParamterActivity.btnReadDatetime.performClick();
                            ((Button) meterGprsFragment.getView().findViewById(R.id.btnReadDatetime)).performClick();
                            Toast.makeText(context, "校正时钟成功！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (data.indexOf("494D4549204F4B20202020494D454920534554204F4B") != -1) {
                            smsg = "";
                            //SetGprsParamterActivity.btnReadIMEI.performClick();
                            ((Button) meterGprsFragment.getView().findViewById(R.id.btnReadIMEI)).performClick();
                            Toast.makeText(context, "修改IMEI成功！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        System.out.println("data:" + data);
                        int start7B = data.indexOf("7B");
                        if (start7B != -1) {
                            String newdata = data.substring(start7B + 2);
                            System.out.println("newdata:" + newdata);
                            int end7B = newdata.indexOf("7B");
                            if (end7B != -1) {

                                String aframe = newdata.substring(0, end7B);
                                System.out.println("aframe:" + aframe);
                                String framType = aframe.substring(4, 6);
                                StringBuilder builder = new StringBuilder();
                                builder.append(aframe);


                                if (builder.charAt(46) == '8'
                                        && builder.charAt(47) == '7'
                                        && builder.charAt(50) == 'C'
                                        && builder.charAt(51) == '1'
                                        && builder.charAt(52) == '4'
                                        && builder.charAt(53) == '2') {
                                    String imei = aframe.substring(4 + 2, 4 + 2 + 22);
                                    System.out.println(imei);
                                    StringBuilder sb = new StringBuilder();
                                    for (int i = 0; i < imei.length() / 2; i++) {
                                        sb.append((Integer.parseInt(imei.substring(i * 2, (i + 1) * 2))) - 30);
                                    }
                                    System.out.println(sb.toString());
                                    TextView tvIMEI = (TextView) meterGprsFragment.getView().findViewById(R.id.tvIMEI);
                                    tvIMEI.setText(sb.toString());
                                    tvIMEI.setTextColor((ColorStateList) context.getResources().getColorStateList(R.color.darkgreen));

                                } else if (builder.charAt(46) == '8'
                                        && builder.charAt(47) == '4'
                                        && builder.charAt(50) == 'A'
                                        && builder.charAt(51) == '1'
                                        && builder.charAt(52) == '1'
                                        && builder.charAt(53) == '7') {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(aframe.substring(34 * 2, 34 * 2 + 2));
                                    sb.append(aframe.substring(33 * 2, 33 * 2 + 2));
                                    sb.append('-');
                                    sb.append(aframe.substring(32 * 2, 32 * 2 + 2));
                                    sb.append('-');
                                    sb.append(aframe.substring(31 * 2, 31 * 2 + 2));
                                    sb.append(' ');
                                    sb.append(aframe.substring(30 * 2, 30 * 2 + 2));
                                    sb.append(':');
                                    sb.append(aframe.substring(29 * 2, 29 * 2 + 2));
                                    sb.append(':');
                                    sb.append(aframe.substring(28 * 2, 28 * 2 + 2));
                                    //SetGprsParamterActivity.textViewDateTime.setText(sb.toString());
                                    //SetGprsParamterActivity.textViewDateTime.setTextColor(SetGprsParamterActivity.colordarkgreen);
                                    TextView textViewDateTime = (TextView) meterGprsFragment.getView().findViewById(R.id.textViewDateTime);
                                    textViewDateTime.setText(sb.toString());
                                    textViewDateTime.setTextColor((ColorStateList) context.getResources().getColorStateList(R.color.darkgreen));

                                } else if (builder.charAt(46) == '8'
                                        && builder.charAt(47) == '7'
                                        && builder.charAt(50) == 'C'
                                        && builder.charAt(51) == '1'
                                        && builder.charAt(52) == '1'
                                        && builder.charAt(53) == 'F') {
                                    int tcpindex = aframe.indexOf("2254435022");
                                    System.out.println(aframe);
                                    if (tcpindex != -1) {
                                        ((RadioButton) meterGprsFragment.getView().findViewById(R.id.radioTcpServer)).setChecked(true);
                                        ((RadioButton) meterGprsFragment.getView().findViewById(R.id.radioUdpServer)).setChecked(false);
                                        //SetGprsParamterActivity.radioTcpServer.setChecked(true);
                                        //SetGprsParamterActivity.radioUdpServer.setChecked(false);
                                        TcpUdpParam param = new TcpUdpParam();
                                        param.setMode("TCP");
                                        String strtcpip = aframe.substring(tcpindex);
                                        String[] arrtcpip = strtcpip.split("222C22");
                                        System.out.println(arrtcpip[1]);
                                        String[] arrip = arrtcpip[1].split("2E");
                                        StringBuilder sb = new StringBuilder();
                                        for (int i = 0; i < arrip[0].length() / 2; i++) {
                                            sb.append((Integer.parseInt(arrip[0].substring(i * 2, (i + 1) * 2))) - 30);
                                        }
                                        param.setIp1(sb.toString());
                                        //SetGprsParamterActivity.editTextIP1.setText(sb.toString());
                                        sb = new StringBuilder();
                                        for (int i = 0; i < arrip[1].length() / 2; i++) {
                                            sb.append((Integer.parseInt(arrip[1].substring(i * 2, (i + 1) * 2))) - 30);
                                        }
                                        param.setIp2(sb.toString());
                                        //SetGprsParamterActivity.editTextIP2.setText(sb.toString());
                                        sb = new StringBuilder();
                                        for (int i = 0; i < arrip[2].length() / 2; i++) {
                                            sb.append((Integer.parseInt(arrip[2].substring(i * 2, (i + 1) * 2))) - 30);
                                        }
                                        param.setIp3(sb.toString());
                                        //SetGprsParamterActivity.editTextIP3.setText(sb.toString());
                                        sb = new StringBuilder();
                                        for (int i = 0; i < arrip[3].length() / 2; i++) {
                                            sb.append((Integer.parseInt(arrip[3].substring(i * 2, (i + 1) * 2))) - 30);
                                        }
                                        param.setIp4(sb.toString());
                                        //SetGprsParamterActivity.editTextIP4.setText(sb.toString());
                                        String strport = arrtcpip[2];
                                        System.out.println(strport);
                                        int index = strport.indexOf("22");
                                        if (index % 2 == 1)
                                            index++;
                                        strport = strport.substring(0, index);
                                        System.out.println(strport);
                                        sb = new StringBuilder();
                                        for (int i = 0; i < strport.length() / 2; i++) {
                                            sb.append((Integer.parseInt(strport.substring(i * 2, (i + 1) * 2))) - 30);
                                        }
                                        //System.out.println(sb.toString());
                                        param.setPort(sb.toString());
                                        //SetGprsParamterActivity.editTextPort.setText(sb.toString());
                                        //SetGprsParamterActivity.tvGprsParam.setText(param.toString());
                                        //SetGprsParamterActivity.tvGprsParam.setTextColor(SetGprsParamterActivity.colordarkgreen);
                                        TextView tvGprsParam = (TextView) meterGprsFragment.getView().findViewById(R.id.tvGprsParam);
                                        tvGprsParam.setText(param.toString());
                                        tvGprsParam.setTextColor((ColorStateList) context.getResources().getColorStateList(R.color.darkgreen));

                                    }

                                } else if (builder.indexOf("950F867700") == 46) {
                                    Spinner spinnerupdatecycle = (Spinner) meterGprsFragment.getView().findViewById(R.id.spinnerupdatecycle);
                                    Spinner spinnerupdatehour = (Spinner) meterGprsFragment.getView().findViewById(R.id.spinnerupdatehour);
                                    Spinner spinnerpwrbattery = (Spinner) meterGprsFragment.getView().findViewById(R.id.spinnerpwrbattery);
                                    Spinner spinnerpwrelectric = (Spinner) meterGprsFragment.getView().findViewById(R.id.spinnerpwrelectric);
                                    int position = Frequent.HexS2ToInt(builder.substring(56, 58));//Integer.parseInt(builder.charAt(28))*16+Integer.parseInt(builder.charAt(29));
                                    for (int i = 0; i < meterGprsFragment.arrupdatecycle.length; i++) {
                                        if (meterGprsFragment.arrupdatecycle[i] == position) {
                                            spinnerupdatecycle.setSelection(i, false);
                                            break;
                                        }
                                    }
                                    position = Frequent.HexS2ToInt(builder.substring(58, 60));//Integer.parseInt(builder.charAt(28))*16+Integer.parseInt(builder.charAt(29));
                                    for (int i = 0; i < meterGprsFragment.arrupdatehour.length; i++) {
                                        if (meterGprsFragment.arrupdatehour[i] == position) {
                                            spinnerupdatehour.setSelection(i, false);
                                            break;
                                        }
                                    }
                                    position = Frequent.HexS2ToInt(builder.substring(60, 62));//Integer.parseInt(builder.charAt(28))*16+Integer.parseInt(builder.charAt(29));
                                    for (int i = 0; i < meterGprsFragment.arrpwrbattery.length; i++) {
                                        if (meterGprsFragment.arrpwrbattery[i] == position) {
                                            spinnerpwrbattery.setSelection(i, false);
                                            break;
                                        }
                                    }
                                    position = Frequent.HexS2ToInt(builder.substring(62, 64));//Integer.parseInt(builder.charAt(28))*16+Integer.parseInt(builder.charAt(29));
                                    for (int i = 0; i < meterGprsFragment.arrpwrelectric.length; i++) {
                                        if (meterGprsFragment.arrpwrelectric[i] == position) {
                                            spinnerpwrelectric.setSelection(i, false);
                                            break;
                                        }
                                    }

                                    Toast.makeText(context, "收到抄表间隔参数！", Toast.LENGTH_SHORT).show();

                                }
                                start7B = -1;
                                end7B = -1;
                                smsg = "";
                            }
                        }
                    } else {
                    }
                    break;

            }
        }
    };


    //发送数据功能函数
    static int HexS1ToInt(char ch) {
        if ('a' <= ch && ch <= 'f') {
            return ch - 'a' + 10;
        }
        if ('A' <= ch && ch <= 'F') {
            return ch - 'A' + 10;
        }
        if ('0' <= ch && ch <= '9') {
            return ch - '0';
        }
        throw new IllegalArgumentException(String.valueOf(ch));
    }

    static int HexS2ToInt(String S) {
        int r = 0;
        char a[] = S.toCharArray();
        r = HexS1ToInt(a[0]) * 16 + HexS1ToInt(a[1]);
        return r;
    }

    public static void writeData(String tx) {
        if (flag == true) {
            int i = 0;
            try {
                OutputStream os = _socket.getOutputStream();   //蓝牙连接输出流
                byte[] bos = new byte[tx.length() / 2];
                for (i = 0; i < (tx.length() / 2); i++) { //手机中换行为0a,将其改为0d 0a后再发送
                    bos[i] = (byte) HexS2ToInt(tx.substring(2 * i, 2 * i + 2));
                }
                os.write(bos);
                //发送显示消息，进行显示刷新
                //handler.sendMessage(handler.obtainMessage());
            } catch (IOException e) {
            }
        }
    }
}
