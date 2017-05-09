package com.jsmetter.bluetooth.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.jsmetter.bluetooth.AppContext;
import com.jsmetter.bluetooth.R;
import com.jsmetter.bluetooth.base.BaseFragment;
import com.jsmetter.bluetooth.bean.Gridlist;
import com.jsmetter.bluetooth.bean.Protocol;
import com.jsmetter.bluetooth.util.Analysise;
import com.jsmetter.bluetooth.view.MainActivity;

import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

//import com.jsmetter.bluetooth.util.UIHelper;
//import com.jsmetter.bluetooth.util.UpdateManager;

public class MeterDataFragment extends BaseFragment implements View.OnClickListener {


    @Bind(R.id.EditTextmeterid)
    EditText EditTextmeterid;

    @Bind(R.id.Buttonreadparameter)
    Button Buttonreadparameter;

    @Bind(R.id.readdata_check)
    CheckBox readdata_check;

    @Bind(R.id.viewGroupreaddata)
    ViewGroup viewGroup;

    @Bind(R.id.listViewreaddata)
    ListView listView;

    static ArrayList<Gridlist> arr = new ArrayList<Gridlist>();

    public static Context showcontext;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.meterdata_layout, container, false);
        ButterKnife.bind(this, view);
        showcontext = AppContext.getInstance();
        initView(view);
        initData();
        return view;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {
        //mTvVersionName.setText(TDevice.getVersionName());
        Buttonreadparameter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.Buttonreadparameter:
                String tx="";
                if  ((Buttonreadparameter.getText().equals("获取表号"))) {
                    EditTextmeterid.setText("");
                    MainActivity.smsg = "";
                    tx="6820AAAAAAAAAAAAAA1A039A2F001416";
                    MainActivity.writeData(tx);
                };
                if  ((Buttonreadparameter.getText().equals("读表数据"))) {
                    EditTextmeterid.setText(EditTextmeterid.getText().toString().replace(" ", ""));
                    if  (EditTextmeterid.getText().toString().length()!=8) {
                        Buttonreadparameter.setText("获取表号");
                    } else{
                        tx=readmeter(EditTextmeterid.getText().toString(),"20","001111");
                        MainActivity.smsg = "";
                        MainActivity.writeData(tx);
                    }
                };
                break;
            default:
                break;
        }
    }
    public String readmeter(String meterid,String producttypetx,String factorycode){
        String r="";
        Analysise analysise=new Analysise();
        String cs=analysise.getsum("68"+producttypetx+meterid+factorycode+"0103901F00",0);
        r="68"+producttypetx+meterid+factorycode+"0103901F00"+cs+"16";
        return r;
    }

    @SuppressLint("SimpleDateFormat")
    public  void addlistview(Protocol protocol){
        String shoowreport="";
        if (!protocol.getMeterID().equals("")) {
            if (protocol.getProductTypeTX().equals("10")) {
                shoowreport=shoowreport+" "+"产品类型"+" "+protocol.getProductTypeTX();

                shoowreport=shoowreport+" "+"正向累计流量"+" "+protocol.getTotal()+" "+protocol.getTotalUnit();

                shoowreport=shoowreport+" "+"反向累计流量"+" "+protocol.getOppositeTotal()+" "+protocol.getOppositeTotalUnit();

                shoowreport=shoowreport+" "+"瞬时"+" "+protocol.getFlowRate()+" "+protocol.getFlowRateUnit();

                if (!protocol.getValveStatus().equals("-")) {
                    shoowreport=shoowreport+" "+"阀门"+" "+protocol.getValveStatus()+" "+"";
                };

                if (!protocol.getTimeInP().equals("-")) {
                    shoowreport=shoowreport+" "+"时间"+" "+protocol.getTimeInP()+" ";
                };

                if (!protocol.getStatus().equals("-")) {
                    shoowreport=shoowreport+" "+"状态"+" "+protocol.getStatus();
                };
            };
            if (protocol.getProductTypeTX().equals("20")) {
                shoowreport=shoowreport+" "+"产品类型"+" "+protocol.getProductTypeTX();
                if (!protocol.getMBUSAddress().equals("-")) {
                    shoowreport=shoowreport+" "+"MBUS"+" "+protocol.getMBUSAddress();
                };
                shoowreport=shoowreport+" "+"累计冷量"+" "+protocol.getSumCool()+" "+protocol.getSumCoolUnit();

                shoowreport=shoowreport+" "+"累计热量"+" "+protocol.getSumHeat()+" "+protocol.getSumHeatUnit();

                shoowreport=shoowreport+" "+"累计流量"+" "+protocol.getTotal()+" "+protocol.getTotalUnit();

                shoowreport=shoowreport+" "+"功率"+" "+protocol.getPower()+" "+protocol.getPowerUnit();

                shoowreport=shoowreport+" "+"瞬时"+" "+protocol.getFlowRate()+" "+protocol.getFlowRateUnit();
                if (protocol.getSumOpenValveM()!=0) {
                    shoowreport=shoowreport+" "+"累计开阀"+" "+protocol.getSumOpenValveM()+" "+"Min";
                };
                if (!protocol.getCloseTime().equals("-")) {
                    shoowreport=shoowreport+" "+"截止日期"+" "+protocol.getCloseTime()+" ";
                };
                if (!protocol.getLosePowerTime().equals("-")) {
                    shoowreport=shoowreport+" "+"断电时间"+" "+protocol.getLosePowerTime()+" "+"Min";
                };
                if (!protocol.getLoseConTime().equals("-")) {
                    shoowreport=shoowreport+" "+"无通讯时间"+" "+protocol.getLoseConTime()+" "+"h";
                };
                if (protocol.getInsideT()!=0) {
                    shoowreport=shoowreport+" "+"室温"+" "+protocol.getInsideT()+" "+"℃";
                };
                if (!protocol.getInsideTSet().equals("-")) {
                    shoowreport=shoowreport+" "+"设定室温"+" "+protocol.getInsideTSet()+" "+"℃";
                };
                if (!protocol.getValveStatus().equals("-")) {
                    shoowreport=shoowreport+" "+"阀门"+" "+protocol.getValveStatus()+" "+"";
                };
                if (protocol.getT1InP()!=0) {
                    shoowreport=shoowreport+" "+"T1"+" "+protocol.getT1InP()+" "+"℃";
                };
                if (protocol.getT2InP()!=0) {
                    shoowreport=shoowreport+" "+"T2"+" "+protocol.getT2InP()+" "+"℃";
                };
                if (protocol.getT1InP()!=0) {
                    shoowreport=shoowreport+" "+"工作时间"+" "+protocol.getWorkTimeInP()+" "+"h";
                };
                if (!protocol.getTimeInP().equals("-")) {
                    shoowreport=shoowreport+" "+"时间"+" "+protocol.getTimeInP()+" ";
                };
                if (!protocol.getVol().equals("-")) {
                    shoowreport=shoowreport+" "+"电压"+" "+protocol.getVol()+" "+"V";
                };
                if (!protocol.getStatus().equals("-")) {
                    shoowreport=shoowreport+" "+"状态"+" "+protocol.getStatus();
                };
            };
            if (protocol.getProductTypeTX().equals("49")) {
                shoowreport=shoowreport+" "+"产品类型"+" "+protocol.getProductTypeTX();

                shoowreport=shoowreport+" "+"累计开阀"+" "+protocol.getSumOpenValveM()+" "+"Min";

                shoowreport=shoowreport+" "+"截止日期"+" "+protocol.getCloseTime()+" ";

                shoowreport=shoowreport+" "+"断电时间"+" "+protocol.getLosePowerTime()+" "+"Min";

                shoowreport=shoowreport+" "+"无通讯时间"+" "+protocol.getLoseConTime()+" "+"h";

                shoowreport=shoowreport+" "+"室温"+" "+protocol.getInsideT()+" "+"℃";

                shoowreport=shoowreport+" "+"设定室温"+" "+protocol.getInsideTSet()+" "+"℃";

                shoowreport=shoowreport+" "+"阀门"+" "+protocol.getValveStatus()+" "+"";

                shoowreport=shoowreport+" "+"T1"+" "+protocol.getT1InP()+" "+"℃";

                shoowreport=shoowreport+" "+"T2"+" "+protocol.getT2InP()+" "+"℃";

                shoowreport=shoowreport+" "+"工作时间"+" "+protocol.getWorkTimeInP()+" "+"h";

                shoowreport=shoowreport+" "+"时间"+" "+protocol.getTimeInP()+" ";

                shoowreport=shoowreport+" "+"电压"+" "+protocol.getVol()+" "+"V";

            };
            arr.add(new Gridlist(protocol.getMeterID(),(new java.text.SimpleDateFormat("hh:mm:ss")).format(new Date()),shoowreport));
        };
        MeterDataAdapter adapter = new MeterDataAdapter(showcontext, arr);
        listView.setAdapter(adapter);
    }

    private void onClickUpdate() {

        //new UpdateManager(getActivity(), true).checkUpdate();
    }
}
