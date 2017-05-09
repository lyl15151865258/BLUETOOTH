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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

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

public class MeterTopworxFragment extends BaseFragment implements View.OnClickListener {


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
    @Bind(R.id.Buttoncs16)
    Button Buttoncs16;
    @Bind(R.id.Buttonsend)
    Button Buttonsend;
    @Bind(R.id.EditTextsend)
    EditText EditTextsend;


    @Bind(R.id.btnTopworxEndTime)
    Button btnTopworxEndTime;
    @Bind(R.id.edTopworxEndDate)
    EditText edTopworxEndDate;
    @Bind(R.id.btnTopworxChoosedate)
    Button btnTopworxChoosedate;

    @Bind(R.id.btnSetCH)
    Button btnSetCH;
    @Bind(R.id.spinnerlorach)
    Spinner spinnerlorach;

    private static String strDeviceTypeCode = "49";

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
    public  String[] arrlorachIetms = {"频道_10","频道_11","频道_12","频道_13","频道_14","频道_15","频道_16","频道_17","频道_18","频道_19"};
    public  String[] arrlorach = {"0A","0B","0C","0D","0E","0F","10","11","12","13"};
    ArrayAdapter<String> lorachlist ;

    public Context context;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.metertopworx_layout, container, false);
        ButterKnife.bind(this, view);
        context = getContext();

        FrameLayout generalActionBar = (FrameLayout) view.findViewById(R.id.general_actionbar);
        //TextView tvTitle = (TextView) generalActionBar.findViewById(R.id.tv_explore_scan);
        //tvTitle.setText(R.string.main_tab_name_topworx);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void initView(View view) {
        edTopworxEndDate.setText("2099-12-31");



        Buttonreadparameter.setOnClickListener(new View.OnClickListener() {
            // @Override
            public void onClick(View v) {
                EditTextmeterid.setText("");
                edTopworxEndDate.setText("");
                MainActivity.smsg="";
                //String tx="6820AAAAAAAAAAAAAA1A039A2F001416";6849FFFFFFFFFFFFFF0103901F005D16
                String tx = "6849FFFFFFFFFFFFFF0103901F005D16";
                EditTextsend.setText(tx);
                MainActivity.writeData(tx);
            }
        });
        Buttonopenvalve.setOnClickListener(new View.OnClickListener() {
            // @Override
            public void onClick(View v) {
                String meterid="";
                meterid=EditTextmeterid.getText().toString().replace(" ", "");
                EditTextmeterid.setText(meterid);
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
                EditTextmeterid.setText(meterid);
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
        Buttonamendmeterid.setOnClickListener(new View.OnClickListener() {
            // @Override
            public void onClick(View v) {

                String meterid="";
                meterid=EditTextmeterid.getText().toString().replace(" ", "");
                EditTextmeterid.setText(meterid);
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
                EditTextmeterid.setText(meterid);
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
        btnTopworxEndTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String meterid="";
                meterid=EditTextmeterid.getText().toString().replace(" ", "");
                EditTextmeterid.setText(meterid);
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
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd");
                    x=edTopworxEndDate.getText().toString();
                    try
                    {
                        Date xd=sdf.parse(x);
                        rx=sdf1.format(xd);
                    }catch (Exception e) {
                        Toast.makeText(context,"请正确输入当前时间",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String tx=Endworktime(meterid,strDeviceTypeCode,"001111",rx);
                    EditTextsend.setText(tx);
                    MainActivity.smsg = "";
                    MainActivity.writeData(tx);
                }
            }
        });
        btnTopworxChoosedate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                if (btnTopworxChoosedate.equals((Button) v)) {
                    msg.what = SHOW_ENDDATAPICK;
                }
                dateandtimeHandler.sendMessage(msg);
            }
        });
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
                EditTextmeterid.setText(meterid);
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
        lorachlist = new ArrayAdapter<String>(context,R.layout.spinner_layout_head,arrlorachIetms);
        spinnerlorach.setAdapter(lorachlist);
       // spinnerlorach.setSelection(0, true);
        btnSetCH.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String meterid="";
                meterid=EditTextmeterid.getText().toString().replace(" ", "");
                EditTextmeterid.setText(meterid);
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
                            updateEndDateTimeDisplay();
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
    public String amendmeterid(String meterid,String producttypetx,String factorycode,String amendmeterid){
        String r="";
        try{
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"150AA018AA"+amendmeterid+factorycode,0);
            r=("68"+producttypetx+meterid+factorycode+"150AA018AA"+amendmeterid+factorycode+cs+"16").toUpperCase();
            //684911111111001111150AA018AA222222220011114216
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
    private  void updateEndDateTimeDisplay(){
        edTopworxEndDate.setText(new StringBuilder().append(nYear).append("-")
                .append((nMonth + 1) < 10 ? "0" + (nMonth + 1) : (nMonth + 1)).append("-")
                .append((nDay < 10) ? "0" + nDay : nDay));

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

//    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
//        public void onDateSet(DatePicker view, int year, int monthOfYear,
//                              int dayOfMonth) {
//            mYear = year;
//            mMonth = monthOfYear;
//            mDay = dayOfMonth;
//            updateDateTimeDisplay();
//        }
//    };
//    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
//        @Override
//        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//            mHour = hourOfDay;
//            mMinute = minute;
//            updateDateTimeDisplay();
//        }
//    };

    public String SetLoraCh(String meterid, String producttypetx, String factorycode, String strch) {
        String tx="";
        try{
            String cs=Frequent.getsum("68"+producttypetx+"11111111"+factorycode+"0705701C"+strch+"0000",0);
           // String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"0705701C"+strch+"0000",0);
            tx=("68"+producttypetx+"11111111"+factorycode+"0705701C"+strch+"0000"+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return tx;
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
