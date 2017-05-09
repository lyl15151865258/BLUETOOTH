package com.jsmetter.bluetooth.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jsmetter.bluetooth.AppContext;
import com.jsmetter.bluetooth.R;
import com.jsmetter.bluetooth.base.BaseFragment;
import com.jsmetter.bluetooth.util.Frequent;
import com.jsmetter.bluetooth.view.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

//import com.jsmetter.bluetooth.util.UIHelper;
//import com.jsmetter.bluetooth.util.UpdateManager;

public class MeterParamFragment extends BaseFragment implements View.OnClickListener {


    @Bind(R.id.Buttonreadparameter)
    Button Buttonreadparameter;
    @Bind(R.id.EditTextmeterid)
    EditText EditTextmeterid;
    @Bind(R.id.Buttonsetmeterid)
    Button Buttonsetmeterid;
    @Bind(R.id.Buttonopenvalve)
    Button Buttonopenvalve;
    @Bind(R.id.Buttonclosevalve)
    Button Buttonclosevalve;
    @Bind(R.id.Buttonamendmeterid)
    Button Buttonamendmeterid;
    @Bind(R.id.EditTextamendmeterid)
    EditText EditTextamendmeterid;
    @Bind(R.id.Buttoninsteadmeterid)
    Button Buttoninsteadmeterid;

    @Bind(R.id.Buttonadjusttime)
    Button Buttonadjusttime;
    @Bind(R.id.CheckBoxsyn)
    CheckBox CheckBoxsyn;
    @Bind(R.id.EditTextdatetime)
    EditText EditTextdatetime;
    @Bind(R.id.Buttonchoosedate)
    Button Buttonchoosedate;
    @Bind(R.id.Buttonchoosetime)
    Button Buttonchoosetime;

    @Bind(R.id.Buttontotalunit)
    Button Buttontotalunit;
    @Bind(R.id.RadioButtonunitm)
    RadioButton RadioButtonunitm;
    @Bind(R.id.RadioButtonunitgal)
    RadioButton RadioButtonunitgal;

    @Bind(R.id.Buttonamendx)
    Button Buttonamendx;
    @Bind(R.id.EditTextamendx)
    EditText EditTextamendx;
    @Bind(R.id.Buttonamendslope)
    Button Buttonamendslope;
    @Bind(R.id.EditTextslope)
    EditText EditTextslope;
    @Bind(R.id.Buttonstartf)
    Button Buttonstartf;
    @Bind(R.id.EditTextstartf)
    EditText EditTextstartf;

    @Bind(R.id.EditTextdiv1)
    EditText EditTextdiv1;
    @Bind(R.id.EditTextdiv2)
    EditText EditTextdiv2;
    @Bind(R.id.EditTextdiv3)
    EditText EditTextdiv3;
    @Bind(R.id.spinnerpointwhere)
    Spinner spinnerpointwhere;
    private String[] pointwhereitems={"1000","100","10"};
    private String pointwhere="1000";
    @Bind(R.id.spinnermetersize)
    Spinner spinnermetersize;
    @Bind(R.id.EditTextsleeptime)
    EditText EditTextsleeptime;
    @Bind(R.id.Buttonprograme)
    Button Buttonprograme;

    @Bind(R.id.Buttoncreditdsumheat)
    Button Buttoncreditdsumheat;
    @Bind(R.id.EditTextcreditdsumheat)
    EditText EditTextcreditdsumheat;
    @Bind(R.id.Buttonclearcredit)
    Button Buttonclearcredit;
    @Bind(R.id.Buttoninitdsumheat)
    Button Buttoninitdsumheat;
    @Bind(R.id.EditTextinitdsumheat)
    EditText EditTextinitdsumheat;

    @Bind(R.id.spinnerPulseDN)
    Spinner spinnerPulseDN;
    @Bind(R.id.spinnerPulseUnit)
    Spinner spinnerPulseUnit;
    @Bind(R.id.spinnerPulseWidth)
    Spinner spinnerPulseWidth;
    @Bind(R.id.spinnerPulseCycle)
    Spinner spinnerPulseCycle;

    @Bind(R.id.btnClearRst)
    Button btnClearRst;


    @Bind(R.id.btnSetPulse)
    Button btnSetPulse;
    public  String[] arrPulseDN = {"DN15~40","DN50~125","DN150以上"};
    public  String[] arrPulseDNval = {"1000","0100","0010"};
    ArrayAdapter<String> pulseDNAdapter ;
    public  String[] arrPulseUnit = {"0.01升","0.1升","1升","10升","100升","1000升"};
    public  String[] arrPulseUnitval = {"01","02","03","04","05","06"};
    ArrayAdapter<String> pulseUnitAdapter ;
    public  String[] arrPulseCycle = {"周期2ms","周期3ms","周期4ms","周期5ms","周期6ms","周期7ms","周期8ms","周期99ms","周期200ms","周期300ms","周期400ms"};
    public  String[] arrPulseCycleval = {"0002","0003","0004","0005","0006","0007","0008","0099","0200","0300","0400"};
    ArrayAdapter<String> pulseCycleAdapter ;
    public  String[] arrPulseWidth = {"脉宽1ms","脉宽2ms","脉宽3ms","脉宽4ms","脉宽5ms","脉宽6ms","脉宽7ms","脉宽90ms","脉宽100ms","脉宽150ms","脉宽200ms"};
    public  String[] arrPulseWidthval = {"0001","0002","0003","0004","0005","0006","0007","0090","0100","0150","0200"};
    ArrayAdapter<String> pulseWidthAdapter ;

    private String[] metersize15_40items={"15","20","25","32","40"};
    private String[] metersize50_125items={"50","65","80","100","125"};
    private String[] metersize150_500items={"150","200","250","300","350","400","450","500"};
    private String metersize="20";
    private int pointwhereint=0;

    private String strDeviceTypeCode = "10";

    @Bind(R.id.Buttoncs16)
    Button Buttoncs16;
    @Bind(R.id.Buttonsend)
    Button Buttonsend;
    @Bind(R.id.EditTextsend)
    EditText EditTextsend;


    @Bind(R.id.btnSetCH)
    Button btnSetCH;
    @Bind(R.id.spinnerlorach)
    Spinner spinnerlorach;
    public  String[] arrlorachIetms = {"频道_00","频道_01","频道_02","频道_03","频道_04","频道_05","频道_06","频道_07","频道_08","频道_09"};
    public  String[] arrlorach = {"00","01","02","03","04","05","06","07","08","09"};
    ArrayAdapter<String> lorachlist ;

    private int nYear;
    private int nMonth;
    private int nDay;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    private int mSecond;

    private Timer datetimetimer = new Timer();

    private static final int SHOW_DATAPICK = 0;
    private static final int SHOW_TIMEPICK = 1;
    private static final int SHOW_ENDDATAPICK = 2;
    private static final int TIME_DIALOG_ID = 3;
    private static final int SHOW_CLOSEDATAPICK = 4;
    private static final int CLOSEDATE_DIALOG_ID = 5;

    public Context context;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.meterparam_layout, container, false);
        ButterKnife.bind(this, view);
        context = getContext();
        //FrameLayout generalActionBar = (FrameLayout) view.findViewById(R.id.general_actionbar);
        //TextView tvTitle = (TextView) generalActionBar.findViewById(R.id.tv_explore_scan);
        //tvTitle.setText(R.string.main_tab_name_topworx);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void initView(View view) {

        //RadioButtonunitm.setChecked(true);
       // RadioButtonunitgal.setChecked(false);
        ArrayAdapter<String> pointwherelist = new ArrayAdapter<String>(AppContext.context(), R.layout.spinner_layout_head,pointwhereitems);
        final ArrayAdapter<String> metersize15_40list = new ArrayAdapter<String>(AppContext.context(), R.layout.spinner_layout_head,metersize15_40items);
        final ArrayAdapter<String> metersize50_125list = new ArrayAdapter<String>(AppContext.context(), R.layout.spinner_layout_head,metersize50_125items);
        final ArrayAdapter<String> metersize150_500list = new ArrayAdapter<String>(AppContext.context(), R.layout.spinner_layout_head,metersize150_500items);
        spinnerpointwhere.setAdapter(pointwherelist);
        spinnerpointwhere.setSelection(0, false);
        spinnermetersize.setAdapter(metersize15_40list);
        spinnermetersize.setSelection(0, false);

        spinnerpointwhere.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
                pointwhere=pointwhereitems[arg2];
                if (pointwhere.endsWith("1000")){
                    spinnermetersize.setAdapter(metersize15_40list);
                    spinnermetersize.setSelection(1, false);
                    metersize="20";
                    pointwhereint=0;
                }else if (pointwhere.endsWith("100")){
                    spinnermetersize.setAdapter(metersize50_125list);
                    spinnermetersize.setSelection(0, false);
                    metersize="50";
                    pointwhereint=1;
                }else if (pointwhere.endsWith("10")){
                    spinnermetersize.setAdapter(metersize150_500list);
                    spinnermetersize.setSelection(0, false);
                    metersize="150";
                    pointwhereint=2;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        spinnermetersize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (pointwhere.endsWith("1000")){
                    metersize=metersize15_40items[arg2];
                }else if (pointwhere.endsWith("100")){
                    metersize=metersize50_125items[arg2];
                }else if (pointwhere.endsWith("10")){
                    metersize=metersize150_500items[arg2];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        Buttonprograme.setOnClickListener(new View.OnClickListener() {
            // @Override
            public void onClick(View v) {
                String meterid="";
                meterid=EditTextmeterid.getText().toString().replace(" ", "");
                //EditTextmeterid.setText(meterid);
                if  (meterid.length()!=8) {
                    Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                } else{
                    if (!meterid.endsWith("FFFFFFFF")){
                        try
                        {
                            Integer.parseInt(meterid);
                        }catch (Exception e) {
                            Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    String x="";
                    int xi=0;
                    String y="";
                    int yi=0;
                    String z="";
                    int zi=0;
                    String o="";
                    int oi=0;
                    String p="";
                    int pi=0;
                    String q="";
                    x=EditTextdiv1.getText().toString().replace(" ", "");
                    EditTextdiv1.setText(x);
                    try
                    {
                        xi=Integer.parseInt(x);
                        if (xi>65536){
                            Toast.makeText(context,"请正确输入分界点1(<65536)",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }catch (Exception e) {
                        Toast.makeText(context,"请正确输入分界点1(<65536)",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    y=EditTextdiv2.getText().toString().replace(" ", "");
                    EditTextdiv2.setText(y);
                    try
                    {
                        yi=Integer.parseInt(y);
                        if (yi>65536){
                            Toast.makeText(context,"请正确输入分界点2(<65536)",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }catch (Exception e) {
                        Toast.makeText(context,"请正确输入分界点2(<65536)",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    z=EditTextdiv3.getText().toString().replace(" ", "");
                    EditTextdiv3.setText(z);
                    try
                    {
                        zi=Integer.parseInt(z);
                        if (zi>65536){
                            Toast.makeText(context,"请正确输入分界点3(<65536)",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }catch (Exception e) {
                        Toast.makeText(context,"请正确输入分界点3(<65536)",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    o=pointwhere;
                    try
                    {
                        oi=Integer.parseInt(o);
                        if (oi>65536){
                            Toast.makeText(context,"请正确选则显示单位(<65536)",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }catch (Exception e) {
                        Toast.makeText(context,"请正确输入显示单位(<65536)",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    p=metersize;
                    try
                    {
                        pi=Integer.parseInt(p);
                        if (pi>65536){
                            Toast.makeText(context,"请正确显示仪表口(<65536)",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }catch (Exception e) {
                        Toast.makeText(context,"请正确输入显示仪表口(<65536)",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    q=EditTextsleeptime.getText().toString().replace(" ", "");
                    EditTextsleeptime.setText(q);
                    if  ((q.length()>4)||(q.length()<=0)){
                        Toast.makeText(context,"请正确输入睡眠时间(<FFFF)",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String tx=programme(meterid,strDeviceTypeCode,"001111",xi,yi,zi,oi,pi,q);
                    EditTextsend.setText(tx);
                    MainActivity.smsg = "";
                    MainActivity.writeData(tx);
//                    SharedPreferences sharedPreferences = getSharedPreferences("setparameter", Context.MODE_PRIVATE);
//                    Editor editor = sharedPreferences.edit();
//                    editor.putString("meterid",meterid);
//                    editor.putString("div1",x);
//                    editor.putString("div2",y);
//                    editor.putString("div3",z);
//                    editor.putInt("pointwhereint",pointwhereint);
//                    editor.putString("sleeptime",q);
//                    editor.commit();//提交修改
                }
            }
        });
        Buttonreadparameter.setOnClickListener(new View.OnClickListener() {
            // @Override
            public void onClick(View v) {
                EditTextmeterid.setText("");
                MainActivity.smsg="";
                //String tx="6810FFFFFFFFFFFFFF0103901F002416";6849FFFFFFFFFFFFFF0103901F005D16
                String tx = "6820AAAAAAAAAAAAAA1A039A2F001416";
                EditTextsend.setText(tx);
                MainActivity.writeData(tx);
            }
        });
        Buttonopenvalve.setOnClickListener(new View.OnClickListener() {
            // @Override
            public void onClick(View v) {
                String meterid="";
                meterid=EditTextmeterid.getText().toString().replace(" ", "");
                //EditTextmeterid.setText(meterid);
                if  (meterid.length()!=8) {
                    Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                } else{
                    if (!meterid.endsWith("FFFFFFFF")){
                        try
                        {
                            Integer.parseInt(meterid);
                        }catch (Exception e) {
                            Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    String tx=openvalve(meterid,strDeviceTypeCode,"001111");
                    EditTextsend.setText(tx);
                    MainActivity.smsg = "";
                    MainActivity.writeData(tx);
//                    SharedPreferences sharedPreferences = getSharedPreferences("setparameter", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("meterid",meterid);
//                    editor.commit();//提交修改
                }
            }
        });
        Buttonclosevalve.setOnClickListener(new View.OnClickListener() {
            // @Override
            public void onClick(View v) {
                String meterid="";
                meterid=EditTextmeterid.getText().toString().replace(" ", "");
                //EditTextmeterid.setText(meterid);
                if  (meterid.length()!=8) {
                    Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                } else{
                    if (!meterid.endsWith("FFFFFFFF")){
                        try
                        {
                            Integer.parseInt(meterid);
                        }catch (Exception e) {
                            Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    String tx=closevalve(meterid,strDeviceTypeCode,"001111");
                    EditTextsend.setText(tx);
                    MainActivity.smsg = "";
                    MainActivity.writeData(tx);
//                    SharedPreferences sharedPreferences = getSharedPreferences("setparameter", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("meterid",meterid);
//                    editor.commit();//提交修改
                }
            }
        });
        Buttonsetmeterid.setOnClickListener(new View.OnClickListener() {
            // @Override
            public void onClick(View v) {
                EditTextmeterid.setText("11111111");
            }
        });

        btnClearRst.setOnClickListener(new View.OnClickListener(){

            // @Override
            public void onClick(View v) {
                String meterid="";
                meterid=EditTextmeterid.getText().toString().replace(" ", "");
                //EditTextmeterid.setText(meterid);
                if  (meterid.length()!=8) {
                    Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                } else{
                    if (!meterid.endsWith("FFFFFFFF")){
                        try
                        {
                            Integer.parseInt(meterid);
                        }catch (Exception e) {
                            Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    String tx=CearRstTimes(meterid,strDeviceTypeCode,"001111");
                    EditTextsend.setText(tx);
                    MainActivity.writeData(tx);
                }
            }
        });

        Buttonamendmeterid.setOnClickListener(new View.OnClickListener() {
            // @Override
            public void onClick(View v) {

                String meterid="";
                meterid=EditTextmeterid.getText().toString().replace(" ", "");
                //EditTextmeterid.setText(meterid);
                if  (meterid.length()!=8) {
                    Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                } else{
                    if (!meterid.endsWith("FFFFFFFF")){
                        try
                        {
                            Integer.parseInt(meterid);
                        }catch (Exception e) {
                            Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    String amendmeterid="";
                    amendmeterid=EditTextamendmeterid.getText().toString().replace(" ", "");
                    EditTextamendmeterid.setText(amendmeterid);
                    if  (amendmeterid.length()!=8) {
                        Toast.makeText(context,"请输入8位修改表号",Toast.LENGTH_SHORT).show();
                    } else{
                        try
                        {
                            Integer.parseInt(amendmeterid);
                        }catch (Exception e) {
                            Toast.makeText(context,"请输入8位修改表号",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String tx=amendmeterid(meterid,strDeviceTypeCode,"001111",amendmeterid);
                        EditTextsend.setText(tx);
                        MainActivity.smsg = "";
                        MainActivity.writeData(tx);
//                        SharedPreferences sharedPreferences = getSharedPreferences("setparameter", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString("meterid",meterid);
//                        editor.putString("amendmeterid",amendmeterid);
//                        editor.commit();//提交修改
                    }
                }
            }
        });
        Buttoninsteadmeterid.setOnClickListener(new View.OnClickListener() {
            // @Override
            public void onClick(View v) {

                String meterid="";
                meterid=EditTextamendmeterid.getText().toString().replace(" ", "");
                //EditTextmeterid.setText(meterid);
                if  (meterid.length()!=8) {
                    Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                } else{
                    if (!meterid.endsWith("FFFFFFFF")){
                        try
                        {
                            Integer.parseInt(meterid);
                        }catch (Exception e) {
                            Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    EditTextmeterid.setText(meterid);
//                    SharedPreferences sharedPreferences = getSharedPreferences("setparameter", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("meterid",meterid);
//                    editor.commit();
                }
            }
        });
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (CheckBoxsyn.isChecked()){
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    mHour = c.get(Calendar.HOUR_OF_DAY);
                    mMinute = c.get(Calendar.MINUTE);
                    mSecond = c.get(Calendar.SECOND);
                    EditTextdatetime.setText(new StringBuilder().append(mYear).append("-")
                            .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
                            .append("-")
                            .append((mDay < 10) ? "0" + mDay : mDay)
                            .append(" ")
                            .append((mHour < 10) ? "0" + mHour : mHour)
                            .append(":")
                            .append((mMinute < 10) ? "0" + mMinute : mMinute)
                            .append(":")
                            .append((mSecond < 10) ? "0" + mSecond : mSecond));
                }
            }
        };
        datetimetimer = new Timer();
        datetimetimer.schedule(new TimerTask() {
            public void run() {
                Message msg = new Message();
                handler.sendMessage(msg);
            }
        }, 0, 1000);

        Buttonchoosedate.setOnClickListener(new View.OnClickListener() {
            // @Override
            public void onClick(View v) {
                Message msg = new Message();
                if (Buttonchoosedate.equals((Button) v)) {
                    msg.what = SHOW_DATAPICK;
                }
                dateandtimeHandler.sendMessage(msg);
            }
        });
        Buttonchoosetime.setOnClickListener(new View.OnClickListener() {
            // @Override
            public void onClick(View v) {
                Message msg = new Message();
                if (Buttonchoosetime.equals((Button) v)) {
                    msg.what = SHOW_TIMEPICK;
                }
                dateandtimeHandler.sendMessage(msg);
            }
        });
        Buttonadjusttime.setOnClickListener(new View.OnClickListener() {
            // @Override
            @SuppressLint("SimpleDateFormat")
            public void onClick(View v) {
                String meterid="";
                meterid=EditTextmeterid.getText().toString().replace(" ", "");
                //EditTextmeterid.setText(meterid);
                if  (meterid.length()!=8) {
                    Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                } else{
                    if (!meterid.endsWith("FFFFFFFF")){
                        try
                        {
                            Integer.parseInt(meterid);
                        }catch (Exception e) {
                            Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    String x="";
                    String rx="";
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    SimpleDateFormat sdf1=new SimpleDateFormat("ssmmHHddMMyyyy");
                    x=EditTextdatetime.getText().toString();
                    try
                    {
                        Date xd=sdf.parse(x);
                        rx=sdf1.format(xd);
                    }catch (Exception e) {
                        Toast.makeText(context,"请正确输入当前时间",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String tx=adjusttime(meterid,strDeviceTypeCode,"001111",rx);
                    EditTextsend.setText(tx);
                    MainActivity.smsg = "";
                    MainActivity.writeData(tx);
                }
            }
        });
        Buttoncs16.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String tx=EditTextsend.getText().toString().replace(" ", "");
                if (tx.length()%2!=0) {
                    Toast.makeText(context,"发送的指令为--奇数",Toast.LENGTH_SHORT).show();
                    return;
                }
                tx=tx+ Frequent.getsum(tx,0)+"16";
                EditTextsend.setText(tx);
            }
        });
        Buttonsend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String tx="";
                tx=EditTextsend.getText().toString().replace(" ", "");
                if (tx.length()%2!=0) {
                    Toast.makeText(context,"发送的指令为--奇数",Toast.LENGTH_SHORT).show();
                    return;
                }
                EditTextsend.setText(tx);
                MainActivity.smsg = "";
                MainActivity.writeData(tx);
            }
        });

        Buttontotalunit.setOnClickListener(new View.OnClickListener() {
            // @Override
            public void onClick(View v) {

                String meterid="";
                meterid=EditTextmeterid.getText().toString().replace(" ", "");
                //EditTextmeterid.setText(meterid);
                if  (meterid.length()!=8) {
                    Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                } else{
                    if (!meterid.endsWith("FFFFFFFF")){
                        try
                        {
                            Integer.parseInt(meterid);
                        }catch (Exception e) {
                            Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    String units="";
                    boolean b=RadioButtonunitm.isChecked();
                    if (b) units="100E";
                    else
                        units="E803";
                    String tx=setheatunit(meterid,strDeviceTypeCode,"001111",units);
                    EditTextsend.setText(tx);
                    MainActivity.smsg = "";
                    MainActivity.writeData(tx);
//                    SharedPreferences sharedPreferences = getSharedPreferences("setparameter", Context.MODE_PRIVATE);
//                    Editor editor = sharedPreferences.edit();
//                    editor.putString("meterid",meterid);
//                    editor.putBoolean("unitm",b);
//                    editor.commit();//提交修改
                }
            }
        });

        Buttonamendx.setOnClickListener(new View.OnClickListener() {
            // @Override
            public void onClick(View v) {

                String meterid="";
                meterid=EditTextmeterid.getText().toString().replace(" ", "");
                //EditTextmeterid.setText(meterid);
                if  (meterid.length()!=8) {
                    Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                } else{
                    if (!meterid.endsWith("FFFFFFFF")){
                        try
                        {
                            Integer.parseInt(meterid);
                        }catch (Exception e) {
                            Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    String x="";
                    int xi=0;
                    x=EditTextamendx.getText().toString().replace(" ", "");
                    EditTextamendx.setText(x);
                    try
                    {
                        xi=Integer.parseInt(x);
                        if (xi>65536){
                            Toast.makeText(context,"请正确输入修正系数(<65536)",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }catch (Exception e) {
                        Toast.makeText(context,"请正确输入修正系数(<65536)",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String tx=amendx(meterid,strDeviceTypeCode,"001111",xi);
                    EditTextsend.setText(tx);
                    MainActivity.smsg = "";
                    MainActivity.writeData(tx);
//                    SharedPreferences sharedPreferences = getSharedPreferences("setparameter", Context.MODE_PRIVATE);
//                    Editor editor = sharedPreferences.edit();
//                    editor.putString("meterid",meterid);
//                    editor.putString("amendx",x);
//                    editor.commit();//提交修改
                }
            }
        });
        Buttonstartf.setOnClickListener(new View.OnClickListener() {
            // @Override
            public void onClick(View v) {

                String meterid="";
                meterid=EditTextmeterid.getText().toString().replace(" ", "");
                //EditTextmeterid.setText(meterid);
                if  (meterid.length()!=8) {
                    Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                } else{
                    if (!meterid.endsWith("FFFFFFFF")){
                        try
                        {
                            Integer.parseInt(meterid);
                        }catch (Exception e) {
                            Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    String x="";
                    int xi=0;
                    x=EditTextstartf.getText().toString().replace(" ", "");
                    EditTextstartf.setText(x);
                    try
                    {
                        xi=Integer.parseInt(x);
                        if (xi>65536){
                            Toast.makeText(context,"请正确输入启动流量(<65536)",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }catch (Exception e) {
                        Toast.makeText(context,"请正确输入启动流量(<65536)",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String tx=amendstartf(meterid,strDeviceTypeCode,"001111",xi);
                    EditTextsend.setText(tx);
                    MainActivity.smsg = "";
                    MainActivity.writeData(tx);
//                    SharedPreferences sharedPreferences = getSharedPreferences("setparameter", Context.MODE_PRIVATE);
//                    Editor editor = sharedPreferences.edit();
//                    editor.putString("meterid",meterid);
//                    editor.putString("startf",x);
//                    editor.commit();//提交修改
                }
            }
        });
        Buttonamendslope.setOnClickListener(new View.OnClickListener() {
            // @Override
            public void onClick(View v) {

                String meterid="";
                meterid=EditTextmeterid.getText().toString().replace(" ", "");
                //EditTextmeterid.setText(meterid);
                if  (meterid.length()!=8) {
                    Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                } else{
                    if (!meterid.endsWith("FFFFFFFF")){
                        try
                        {
                            Integer.parseInt(meterid);
                        }catch (Exception e) {
                            Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    String x="";
                    int xi=0;
                    x=EditTextslope.getText().toString().replace(" ", "");
                    EditTextslope.setText(x);
                    try
                    {
                        xi=Integer.parseInt(x);
                        if (xi>65536){
                            Toast.makeText(context,"请正确输入斜率修正(<65536)",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }catch (Exception e) {
                        Toast.makeText(context,"请正确输入斜率修正(<65536)",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String tx=amendslope(meterid,strDeviceTypeCode,"001111",xi);
                    EditTextsend.setText(tx);
                    MainActivity.smsg = "";
                    MainActivity.writeData(tx);
//                    SharedPreferences sharedPreferences = getSharedPreferences("setparameter", Context.MODE_PRIVATE);
//                    Editor editor = sharedPreferences.edit();
//                    editor.putString("meterid",meterid);
//                    editor.putString("slope",x);
//                    editor.commit();//提交修改
                }
            }
        });
        Buttonstartf.setOnClickListener(new View.OnClickListener() {
            // @Override
            public void onClick(View v) {

                String meterid="";
                meterid=EditTextmeterid.getText().toString().replace(" ", "");
                //EditTextmeterid.setText(meterid);
                if  (meterid.length()!=8) {
                    Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                } else{
                    if (!meterid.endsWith("FFFFFFFF")){
                        try
                        {
                            Integer.parseInt(meterid);
                        }catch (Exception e) {
                            Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    String x="";
                    int xi=0;
                    x=EditTextstartf.getText().toString().replace(" ", "");
                    EditTextstartf.setText(x);
                    try
                    {
                        xi=Integer.parseInt(x);
                        if (xi>65536){
                            Toast.makeText(context,"请正确输入启动流量(<65536)",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }catch (Exception e) {
                        Toast.makeText(context,"请正确输入启动流量(<65536)",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String tx=amendstartf(meterid,strDeviceTypeCode,"001111",xi);
                    EditTextsend.setText(tx);
                    MainActivity.smsg = "";
                    MainActivity.writeData(tx);
//                    SharedPreferences sharedPreferences = getSharedPreferences("setparameter", Context.MODE_PRIVATE);
//                    Editor editor = sharedPreferences.edit();
//                    editor.putString("meterid",meterid);
//                    editor.putString("startf",x);
//                    editor.commit();//提交修改
                }
            }
        });
        Buttoncreditdsumheat.setOnClickListener(new View.OnClickListener() {
            // @Override
            public void onClick(View v) {
                String meterid="";
                meterid=EditTextmeterid.getText().toString().replace(" ", "");
                //EditTextmeterid.setText(meterid);
                if  (meterid.length()!=8) {
                    Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                } else{
                    if (!meterid.endsWith("FFFFFFFF")){
                        try
                        {
                            Integer.parseInt(meterid);
                        }catch (Exception e) {
                            Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    String x="";
                    int xi=0;
                    x=EditTextcreditdsumheat.getText().toString().replace(" ", "");
                    EditTextcreditdsumheat.setText(x);
                    try
                    {
                        xi=Integer.parseInt(x);
                        if (xi>99999999){
                            Toast.makeText(context,"请正确输入充值热量(<99999999)",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }catch (Exception e) {
                        Toast.makeText(context,"请正确输入充值热量(<99999999)",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String tx=creditdsumheat(meterid,strDeviceTypeCode,"001111",xi);
                    EditTextsend.setText(tx);
                    MainActivity.smsg = "";
                    MainActivity.writeData(tx);
//                    SharedPreferences sharedPreferences = getSharedPreferences("setparameter", Context.MODE_PRIVATE);
//                    Editor editor = sharedPreferences.edit();
//                    editor.putString("meterid",meterid);
//                    editor.putString("creditdsumheat",x);
//                    editor.commit();//提交修改
                }
            }
        });

        Buttonclearcredit.setOnClickListener(new View.OnClickListener() {
            // @Override
            public void onClick(View v) {
                String meterid="";
                meterid=EditTextmeterid.getText().toString().replace(" ", "");
                //EditTextmeterid.setText(meterid);
                if  (meterid.length()!=8) {
                    Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                } else{
                    if (!meterid.endsWith("FFFFFFFF")){
                        try
                        {
                            Integer.parseInt(meterid);
                        }catch (Exception e) {
                            Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    String tx=cleardsumheat(meterid,strDeviceTypeCode,"001111");
                    EditTextsend.setText(tx);
                    MainActivity.writeData(tx);
                }
            }
        });
        Buttoninitdsumheat.setOnClickListener(new View.OnClickListener() {
            // @Override
            public void onClick(View v) {
                String meterid="";
                meterid=EditTextmeterid.getText().toString().replace(" ", "");
                //EditTextmeterid.setText(meterid);
                if  (meterid.length()!=8) {
                    Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                } else{
                    if (!meterid.endsWith("FFFFFFFF")){
                        try
                        {
                            Integer.parseInt(meterid);
                        }catch (Exception e) {
                            Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    String x="";
                    int xi=0;
                    x=EditTextinitdsumheat.getText().toString().replace(" ", "");
                    EditTextinitdsumheat.setText(x);
                    try
                    {
                        xi=Integer.parseInt(x);
                        if (xi>99999999){
                            Toast.makeText(context,"请正确输入初始热量(<99999999)",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }catch (Exception e) {
                        Toast.makeText(context,"请正确输入初始热量(<99999999)",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String tx=initdsumheat(meterid,strDeviceTypeCode,"001111",xi);
                    EditTextsend.setText(tx);
                    MainActivity.smsg = "";
                    MainActivity.writeData(tx);
//                    SharedPreferences sharedPreferences = getSharedPreferences("setparameter", Context.MODE_PRIVATE);
//                    Editor editor = sharedPreferences.edit();
//                    editor.putString("meterid",meterid);
//                    editor.putString("initdsumheat",x);
//                    editor.commit();//提交修改
                }
            }
        });
        pulseDNAdapter = new ArrayAdapter<String>(context,R.layout.spinner_layout_head,arrPulseDN);
        pulseUnitAdapter = new ArrayAdapter<String>(context,R.layout.spinner_layout_head,arrPulseUnit);
        pulseCycleAdapter = new ArrayAdapter<String>(context,R.layout.spinner_layout_head,arrPulseCycle);
        pulseWidthAdapter = new ArrayAdapter<String>(context,R.layout.spinner_layout_head,arrPulseWidth);
        spinnerPulseDN.setAdapter(pulseDNAdapter);
        spinnerPulseUnit.setAdapter(pulseUnitAdapter);
        spinnerPulseCycle.setAdapter(pulseCycleAdapter);
        spinnerPulseWidth.setAdapter(pulseWidthAdapter);
        btnSetPulse.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String meterid="";
                meterid=EditTextmeterid.getText().toString().replace(" ", "");
                //EditTextmeterid.setText(meterid);
                if  (meterid.length()!=8) {
                    Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                } else {
                    if (!meterid.endsWith("FFFFFFFF")) {
                        try {
                            Integer.parseInt(meterid);
                        } catch (Exception e) {
                            Toast.makeText(context, "请输入8位出厂编码", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    String strDN = arrPulseDNval[spinnerPulseDN.getSelectedItemPosition()];
                    String strUnit = arrPulseUnitval[spinnerPulseUnit.getSelectedItemPosition()];
                    String strCycle = arrPulseCycleval[spinnerPulseCycle.getSelectedItemPosition()];
                    String strWidth = arrPulseWidthval[spinnerPulseWidth.getSelectedItemPosition()];
                    if(Integer.parseInt(strCycle)<=Integer.parseInt(strWidth)) {
                        Toast.makeText(context, "脉冲宽度设置错误，请核查！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String tx=SetPulseParam(meterid,strDeviceTypeCode,"001111",strDN,strUnit,strCycle,strWidth);
                    EditTextsend.setText(tx);
                    MainActivity.smsg = "";
                    MainActivity.writeData(tx);
                    //btnSetPulse.requestFocus();
                }
            }
        });

        lorachlist = new ArrayAdapter<String>(context,R.layout.spinner_layout_head,arrlorachIetms);
        spinnerlorach.setAdapter(lorachlist);
        // spinnerlorach.setSelection(0, true);
        btnSetCH.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String meterid="";
                meterid=EditTextmeterid.getText().toString().replace(" ", "");
                //EditTextmeterid.setText(meterid);
                if  (meterid.length()!=8) {
                    Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                } else{
                    if (!meterid.endsWith("FFFFFFFF")){
                        try
                        {
                            Integer.parseInt(meterid);
                        }catch (Exception e) {
                            Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    String strch = arrlorach[spinnerlorach.getSelectedItemPosition()];
                    String tx=SetLoraCh(meterid,"54","001111",strch);
                    EditTextsend.setText(tx);
                    MainActivity.smsg = "";
                    MainActivity.writeData(tx);
//                    SharedPreferences sharedPreferences = getSharedPreferences("setparameter", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("meterid",meterid);
//                    editor.commit();//提交修改
                }

            }
        });
        //SharedPreferences sharedPreferences = getSharedPreferences("setparameter", Context.MODE_PRIVATE);
        //EditTextmeterid.setText(sharedPreferences.getString("meterid","11111111"));
        //EditTextamendmeterid.setText(sharedPreferences.getString("amendmeterid",""));
    }



    @Override
    public void initData() {
        //mTvVersionName.setText(TDevice.getVersionName());
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.Buttonreadparameter:

                break;
            default:
                break;
        }
    }

    Handler dateandtimeHandler = new Handler() {
        Calendar calendar;// 用来装日期的
        DatePickerDialog dialog;
        @SuppressWarnings("deprecation")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_DATAPICK:
                    //showDialog(DATE_DIALOG_ID);
                    Calendar calendar = Calendar.getInstance();
                    DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
                            mYear = year;
                            mMonth = monthOfYear;
                            mDay = dayOfMonth;
                            updateDateTimeDisplay();
                            //getTime.setText(year + "/" + monthOfYear + "/"+ dayOfMonth);
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    dialog.show();
                    break;
                case SHOW_TIMEPICK:
                    calendar = Calendar.getInstance();
                    TimePickerDialog tdialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            mHour = hourOfDay;
                            mMinute = minute;
                            updateDateTimeDisplay();
                        }
                    }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),true);
                    tdialog.show();
                    //showDialog(TIME_DIALOG_ID);
                    break;
                case  SHOW_ENDDATAPICK:
                    calendar = Calendar.getInstance();
                    DatePickerDialog enddialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
                            nYear = year;
                            nMonth = monthOfYear;
                            nDay = dayOfMonth;
                          //  updateEndDateTimeDisplay();
                            //getTime.setText(year + "/" + monthOfYear + "/"+ dayOfMonth);
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    enddialog.show();
                    break;
                case SHOW_CLOSEDATAPICK:
                    //showDialog(CLOSEDATE_DIALOG_ID);
                    break;

            }
        }

    };
    private void onClickUpdate() {

        //new UpdateManager(getActivity(), true).checkUpdate();
    }


    public String Endworktime(String meterid,String producttypetx,String factorycode,String x){
        String r="";
        String rx="";
        int i=1;

        try{
            rx=Integer.toHexString(Integer.valueOf(x.substring(0, 2)))+"00"+
                    (Integer.valueOf(x.substring(2, 4))<16?("0"+Integer.toHexString(Integer.valueOf(x.substring(2, 4)))):Integer.toHexString(Integer.valueOf(x.substring(2, 4))))+"00"+
                    (Integer.valueOf(x.substring(4, 6))<16?("0"+Integer.toHexString(Integer.valueOf(x.substring(4, 6)))):Integer.toHexString(Integer.valueOf(x.substring(4, 6))))+"00"+
                    (Integer.valueOf(x.substring(6, 8))<16?("0"+Integer.toHexString(Integer.valueOf(x.substring(6, 8)))):Integer.toHexString(Integer.valueOf(x.substring(6, 8))))+"00";
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"360CA0198800"+rx,0);
            r="68"+producttypetx+meterid+factorycode+"360CA0198800"+rx+cs+"16";
        }catch(Exception e){
        }
        return r;
    }


    private void updateDateTimeDisplay(){
        CheckBoxsyn.setChecked(false);
        EditTextdatetime.setText(new StringBuilder().append(mYear).append("-")
                .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
                .append((mDay < 10) ? "0" + mDay : mDay)
                .append(" ")
                .append((mHour < 10) ? "0" + mHour : mHour)
                .append(":")
                .append((mMinute < 10) ? "0" + mMinute : mMinute)
                .append(":")
                .append((mSecond < 10) ? "0" + mSecond : mSecond));
    }


    private String SetPulseParam(String meterid, String producttypetx, String factorycode, String strDN, String strUnit,String strCycle, String strWidth) {
        String tx="";
        try{
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"C307"+strDN+strUnit+strCycle+strWidth,0);
            tx=("68"+producttypetx+meterid+factorycode+"C307"+strDN+strUnit+strCycle+strWidth+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return  tx;
    }
    public String SetLoraCh(String meterid, String producttypetx, String factorycode, String strch) {
        String tx="";
        try{
            String cs=Frequent.getsum("68"+producttypetx+"11111111"+factorycode+"0705701C"+strch+"0000",0);
            tx=("68"+producttypetx+"11111111"+factorycode+"0705701C"+strch+"0000"+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return tx;
    }

    public String amendmeterid(String meterid,String producttypetx,String factorycode,String amendmeterid){
        String r="";
        try{
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"150AA018AA"+amendmeterid+factorycode,0);
            r=("68"+producttypetx+meterid+factorycode+"150AA018AA"+amendmeterid+factorycode+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return r;
    }
    public String clearmeter(String meterid,String producttypetx,String factorycode){
        String r="";
        try{
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"3A03901F00",0);
            r=("68"+producttypetx+meterid+factorycode+"3A03901F00"+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return r;
    }
    public String initreport(String meterid,String producttypetx,String factorycode){
        String r="";
        try{
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"0403A09A00",0);
            r=("68"+producttypetx+meterid+factorycode+"0403A09A00"+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return r;
    }
    public String clearbatter(String meterid,String producttypetx,String factorycode){
        String r="";
        try{
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"3A03907F00",0);
            r=("68"+producttypetx+meterid+factorycode+"3A03907F00"+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return r;
    }
    public String CearRstTimes(String meterid,String producttypetx,String factorycode){
        String r="";
        try{
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"4A0301F100",0);
            r=("68"+producttypetx+meterid+factorycode+"4A0301F100"+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return r;
    }

    public String adjusttime(String meterid,String producttypetx,String factorycode,String x){
        String r="";
        String rx="";
        try{
            rx=x.substring(0, 10)+x.substring(12, 14)+x.substring(10, 12);
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"040AA01500"+rx,0);
            r="68"+producttypetx+meterid+factorycode+"040AA01500"+rx+cs+"16";
        }catch(Exception e){
        }
        return r;
    }
    public String setheatunit(String meterid,String producttypetx,String factorycode,String units){
        String r="";
        try{
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"B204901F"+units,0);
            r=("68"+producttypetx+meterid+factorycode+"B204901F"+units+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return r;
    }
    public String amendx(String meterid,String producttypetx,String factorycode,int x){
        String r="";
        try{
            String rx="";
            rx=Integer.toHexString(x);
            switch (rx.length()) {
                case 0:rx="0000";break;
                case 1:rx="000"+rx;break;
                case 2:rx="00"+rx;break;
                case 3:rx="0"+rx;break;
                case 4:break;
                default:rx=rx.substring(rx.length()-4, rx.length());break;
            }
            rx=rx.substring(2, 4)+rx.substring(0,2);
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"B104901F"+rx,0);
            r=("68"+producttypetx+meterid+factorycode+"B104901F"+rx+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return r;
    }
    public String amendslope(String meterid,String producttypetx,String factorycode,int x){
        String r="";
        try{
            String rx="";
            rx=Integer.toHexString(x);
            switch (rx.length()) {
                case 0:rx="0000";break;
                case 1:rx="000"+rx;break;
                case 2:rx="00"+rx;break;
                case 3:rx="0"+rx;break;
                case 4:break;
                default:rx=rx.substring(rx.length()-4, rx.length());break;
            }
            rx=rx.substring(2, 4)+rx.substring(0,2);
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"B304901F"+rx,0);
            r=("68"+producttypetx+meterid+factorycode+"B304901F"+rx+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return r;
    }
    public String amendstartf(String meterid,String producttypetx,String factorycode,int x){
        String r="";
        try{
            String rx="";
            rx=Integer.toHexString(x);
            switch (rx.length()) {
                case 0:rx="0000";break;
                case 1:rx="000"+rx;break;
                case 2:rx="00"+rx;break;
                case 3:rx="0"+rx;break;
                case 4:break;
                default:rx=rx.substring(rx.length()-4, rx.length());break;
            }
            rx=rx.substring(2, 4)+rx.substring(0,2);
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"B404901F"+rx,0);
            r=("68"+producttypetx+meterid+factorycode+"B404901F"+rx+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return r;
    }
    public String programme(String meterid,String producttypetx,String factorycode,int x,int y,int z,int o,int p,String q){
        String r="";
        try{
            String rx="";
            String ry="";
            String rz="";
            String ro="";
            String rp="";
            String rq="0000";
            rx=Integer.toHexString(x);
            switch (rx.length()) {
                case 0:rx="0000";break;
                case 1:rx="000"+rx;break;
                case 2:rx="00"+rx;break;
                case 3:rx="0"+rx;break;
                case 4:break;
                default:rx=rx.substring(rx.length()-4, rx.length());break;
            }
            ry=Integer.toHexString(y);
            switch (ry.length()) {
                case 0:ry="0000";break;
                case 1:ry="000"+ry;break;
                case 2:ry="00"+ry;break;
                case 3:ry="0"+ry;break;
                case 4:break;
                default:ry=ry.substring(ry.length()-4, ry.length());break;
            }
            rz=Integer.toHexString(z);
            switch (rz.length()) {
                case 0:rz="0000";break;
                case 1:rz="000"+rz;break;
                case 2:rz="00"+rz;break;
                case 3:rz="0"+rz;break;
                case 4:break;
                default:rz=rz.substring(rz.length()-4, rz.length());break;
            }
            ro=Integer.toHexString(o);
            switch (ro.length()) {
                case 0:ro="0000";break;
                case 1:ro="000"+ro;break;
                case 2:ro="00"+ro;break;
                case 3:ro="0"+ro;break;
                case 4:break;
                default:ro=ro.substring(ro.length()-4, ro.length());break;
            }
            rp=Integer.toString(p);
            switch (rp.length()) {
                case 0:rp="0000";break;
                case 1:rp="000"+rp;break;
                case 2:rp="00"+rp;break;
                case 3:rp="0"+rp;break;
                case 4:break;
                default:rp=rp.substring(rp.length()-4, rp.length());break;
            }
            switch (q.length()) {
                case 0:rq="0000";break;
                case 1:rq="000"+q;break;
                case 2:rq="00"+q;break;
                case 3:rq="0"+q;break;
                case 4:rq=q;break;
                default:rq=q.substring(q.length()-4, q.length());break;
            }
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"3D0F903F00"+rx+ry+rz+ro+rq+rp,0);
            r=("68"+producttypetx+meterid+factorycode+"3D0F903F00"+rx+ry+rz+ro+rq+rp+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return r;
    }
    public String setport(String meterid,String producttypetx,String factorycode,String port){
        String r="";
        try{
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"C203901F"+port,0);
            r=("68"+producttypetx+meterid+factorycode+"C203901F"+port+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return r;
    }
    public String amendq(String meterid,String producttypetx,String factorycode,int x,int y,int z,int o){
        String r="";
        try{
            String rx="";
            String ry="";
            String rz="";
            String ro="";
            rx=Integer.toHexString(x);
            switch (rx.length()) {
                case 0:rx="0000";break;
                case 1:rx="000"+rx;break;
                case 2:rx="00"+rx;break;
                case 3:rx="0"+rx;break;
                case 4:break;
                default:rx=rx.substring(rx.length()-4, rx.length());break;
            }
            rx=rx.substring(2, 4)+rx.substring(0,2);
            ry=Integer.toHexString(y);
            switch (ry.length()) {
                case 0:ry="0000";break;
                case 1:ry="000"+ry;break;
                case 2:ry="00"+ry;break;
                case 3:ry="0"+ry;break;
                case 4:break;
                default:ry=ry.substring(ry.length()-4, ry.length());break;
            }
            ry=ry.substring(2, 4)+ry.substring(0,2);
            rz=Integer.toHexString(z);
            switch (rz.length()) {
                case 0:rz="0000";break;
                case 1:rz="000"+rz;break;
                case 2:rz="00"+rz;break;
                case 3:rz="0"+rz;break;
                case 4:break;
                default:rz=rz.substring(rz.length()-4, rz.length());break;
            }
            rz=rz.substring(2, 4)+rz.substring(0,2);
            ro=Integer.toHexString(o);
            switch (ro.length()) {
                case 0:ro="0000";break;
                case 1:ro="000"+ro;break;
                case 2:ro="00"+ro;break;
                case 3:ro="0"+ro;break;
                case 4:break;
                default:ro=ro.substring(ro.length()-4, ro.length());break;
            }
            ro=ro.substring(2, 4)+ro.substring(0,2);
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"360CA0198800"+rx+ry+rz+ro,0);
            r=("68"+producttypetx+meterid+factorycode+"360CA0198800"+rx+ry+rz+ro+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return r;
    }
    public String amendtcoef(String meterid,String producttypetx,String factorycode,int x,int y){
        String r="";
        try{
            String rx="";
            rx=Integer.toHexString(x);
            switch (rx.length()) {
                case 0:rx="0000";break;
                case 1:rx="000"+rx;break;
                case 2:rx="00"+rx;break;
                case 3:rx="0"+rx;break;
                case 4:break;
                default:rx=rx.substring(rx.length()-4, rx.length());break;
            }
            rx=rx.substring(2, 4)+rx.substring(0,2);

            String ry="";
            ry=Integer.toHexString(y);
            switch (ry.length()) {
                case 0:ry="0000";break;
                case 1:ry="000"+ry;break;
                case 2:ry="00"+ry;break;
                case 3:ry="0"+ry;break;
                case 4:break;
                default:ry=ry.substring(ry.length()-4, ry.length());break;
            }
            ry=ry.substring(2, 4)+ry.substring(0,2);
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"3808A0198800"+rx+ry,0);
            r=("68"+producttypetx+meterid+factorycode+"3808A0198800"+rx+ry+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return r;
    }
    public String amendt(String meterid,String producttypetx,String factorycode,int x,int y,int z,int o,int p){
        String r="";
        try{
            String rx="";
            String ry="";
            String rz="";
            String ro="";
            String rp="";
            rx=Integer.toHexString(x);
            switch (rx.length()) {
                case 0:rx="0000";break;
                case 1:rx="000"+rx;break;
                case 2:rx="00"+rx;break;
                case 3:rx="0"+rx;break;
                case 4:break;
                default:rx=rx.substring(rx.length()-4, rx.length());break;
            }
            rx=rx.substring(2, 4)+rx.substring(0,2);
            ry=Integer.toHexString(y);
            switch (ry.length()) {
                case 0:ry="0000";break;
                case 1:ry="000"+ry;break;
                case 2:ry="00"+ry;break;
                case 3:ry="0"+ry;break;
                case 4:break;
                default:ry=ry.substring(ry.length()-4, ry.length());break;
            }
            ry=ry.substring(2, 4)+ry.substring(0,2);
            rz=Integer.toHexString(z);
            switch (rz.length()) {
                case 0:rz="0000";break;
                case 1:rz="000"+rz;break;
                case 2:rz="00"+rz;break;
                case 3:rz="0"+rz;break;
                case 4:break;
                default:rz=rz.substring(rz.length()-4, rz.length());break;
            }
            rz=rz.substring(2, 4)+rz.substring(0,2);
            ro=Integer.toHexString(o);
            switch (ro.length()) {
                case 0:ro="0000";break;
                case 1:ro="000"+ro;break;
                case 2:ro="00"+ro;break;
                case 3:ro="0"+ro;break;
                case 4:break;
                default:ro=ro.substring(ro.length()-4, ro.length());break;
            }
            ro=ro.substring(2, 4)+ro.substring(0,2);
            rp=Integer.toString(p);
            switch (rp.length()) {
                case 0:rp="0000";break;
                case 1:rp="000"+rp;break;
                case 2:rp="00"+rp;break;
                case 3:rp="0"+rp;break;
                case 4:break;
                default:rp=rp.substring(rp.length()-4, rp.length());break;
            }
            rp=rp.substring(2, 4)+rp.substring(0,2);
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"370EA0198800"+rx+ry+rz+ro+rp,0);
            r=("68"+producttypetx+meterid+factorycode+"370EA0198800"+rx+ry+rz+ro+rp+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return r;
    }
    public String resetparameter(String meterid,String producttypetx,String factorycode){
        String r="";
        try{
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"B703901F00",0);
            r=("68"+producttypetx+meterid+factorycode+"B703901F00"+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return r;
    }
    public String amendclosedate(String meterid,String producttypetx,String factorycode,String x){
        String r="";
        try{
            String rx="";
            String ry="";
            String rz="";
            String ro="";
            rx=Integer.toHexString(Integer.parseInt(x.subSequence(0, 2).toString()));
            switch (rx.length()) {
                case 0:rx="0000";break;
                case 1:rx="000"+rx;break;
                case 2:rx="00"+rx;break;
                case 3:rx="0"+rx;break;
                case 4:break;
                default:rx=rx.substring(rx.length()-4, rx.length());break;
            }
            ry=Integer.toHexString(Integer.parseInt(x.subSequence(2, 4).toString()));
            switch (ry.length()) {
                case 0:ry="0000";break;
                case 1:ry="000"+ry;break;
                case 2:ry="00"+ry;break;
                case 3:ry="0"+ry;break;
                case 4:break;
                default:ry=ry.substring(ry.length()-4, ry.length());break;
            }
            rz=Integer.toHexString(Integer.parseInt(x.subSequence(4, 6).toString()));
            switch (rz.length()) {
                case 0:rz="0000";break;
                case 1:rz="000"+rz;break;
                case 2:rz="00"+rz;break;
                case 3:rz="0"+rz;break;
                case 4:break;
                default:rz=rz.substring(rz.length()-4, rz.length());break;
            }
            ro=Integer.toHexString(Integer.parseInt(x.subSequence(6, 8).toString()));
            switch (ro.length()) {
                case 0:ro="0000";break;
                case 1:ro="000"+ro;break;
                case 2:ro="00"+ro;break;
                case 3:ro="0"+ro;break;
                case 4:break;
                default:ro=ro.substring(ro.length()-4, ro.length());break;
            }
            ro=ro.substring(2, 4)+ro.substring(0,2);
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"360CA01A88"+rx+ry+rz+ro,0);
            r=("68"+producttypetx+meterid+factorycode+"360CA01A88"+rx+ry+rz+ro+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return r;
    }
    public String amendchecktime(String meterid,String producttypetx,String factorycode,int x){
        String r="";
        try{
            String rx="";
            rx=Integer.toHexString(x);
            switch (rx.length()) {
                case 0:rx="0000";break;
                case 1:rx="000"+rx;break;
                case 2:rx="00"+rx;break;
                case 3:rx="0"+rx;break;
                case 4:break;
                default:rx=rx.substring(rx.length()-4, rx.length());break;
            }
            rx=rx.substring(2, 4)+rx.substring(0,2);
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"C504901F00"+rx,0);
            r=("68"+producttypetx+meterid+factorycode+"C504901F00"+rx+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return r;
    }
    public String amendpermission(String meterid,String producttypetx,String factorycode,String x){
        String r="";
        try{
            String rx="";
            rx=x.substring(2, 4)+x.substring(0,2);
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"C204901F"+rx,0);
            r=("68"+producttypetx+meterid+factorycode+"C204901F"+rx+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return r;
    }
    public String amendpluse(String meterid,String producttypetx,String factorycode,int x){
        String r="";
        try{
            String rx="";
            rx=Integer.toHexString(x);
            switch (rx.length()) {
                case 0:rx="00";break;
                case 1:rx="0"+rx;break;
                case 2:break;
            }
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"3A03909A"+rx,0);
            r=("68"+producttypetx+meterid+factorycode+"3A03909A"+rx+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return r;
    }
    public String amendalarm(String meterid,String producttypetx,String factorycode,int x){
        String r="";
        try{
            String rx="";
            rx=Integer.toHexString(x);
            switch (rx.length()) {
                case 0:rx="00";break;
                case 1:rx="0"+rx;break;
                case 2:break;
            }
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"6603901F"+rx,0);
            r=("68"+producttypetx+meterid+factorycode+"6603901F"+rx+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return r;
    }
    public String creditdsumheat(String meterid,String producttypetx,String factorycode,int x){
        String r="";
        try{
            String rx="";
            rx=Integer.toHexString(x);
            switch (rx.length()) {
                case 0:rx="00000000";break;
                case 1:rx="0000000"+rx;break;
                case 2:rx="000000"+rx;break;
                case 3:rx="00000"+rx;break;
                case 4:rx="0000"+rx;break;
                case 5:rx="000"+rx;break;
                case 6:rx="00"+rx;break;
                case 7:rx="0"+rx;break;
                case 8:break;
                default:rx=rx.substring(rx.length()-8, rx.length());break;
            }
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"4D07901F00"+rx,0);
            r=("68"+producttypetx+meterid+factorycode+"4D07901F00"+rx+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return r;
    }
    public String cleardsumheat(String meterid,String producttypetx,String factorycode){
        String r="";
        try{
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"4D03905F00",0);
            r=("68"+producttypetx+meterid+factorycode+"4D03905F00"+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return r;
    }
    public String initdsumheat(String meterid,String producttypetx,String factorycode,int x){
        String r="";
        try{
            String rx="";
            rx=Integer.toHexString(x);
            switch (rx.length()) {
                case 0:rx="00000000";break;
                case 1:rx="0000000"+rx;break;
                case 2:rx="000000"+rx;break;
                case 3:rx="00000"+rx;break;
                case 4:rx="0000"+rx;break;
                case 5:rx="000"+rx;break;
                case 6:rx="00"+rx;break;
                case 7:rx="0"+rx;break;
                case 8:break;
                default:rx=rx.substring(rx.length()-8, rx.length());break;
            }
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"5E07901F00"+rx,0);
            r=("68"+producttypetx+meterid+factorycode+"5E07901F00"+rx+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return r;
    }
    public String openvalve(String meterid,String producttypetx,String factorycode){
        String r="";
        try{
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"0404A0170055",0);
            r=("68"+producttypetx+meterid+factorycode+"0404A0170055"+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return r;
    }
    @SuppressLint("DefaultLocale")
    public String closevalve(String meterid,String producttypetx,String factorycode){
        String r="";
        try{
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"0404A0170099",0);
            r=("68"+producttypetx+meterid+factorycode+"0404A0170099"+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return r;
    }
}
