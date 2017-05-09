package com.jsmetter.bluetooth.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jsmetter.bluetooth.R;
import com.jsmetter.bluetooth.base.BaseFragment;
import com.jsmetter.bluetooth.bean.TcpUdpParam;
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

public class MeterGprsFragment extends BaseFragment implements View.OnClickListener {


    @Bind(R.id.tvIMEI)
    TextView tvIMEI;
    @Bind(R.id.btnSetIMEI)
    Button btnSetIMEI;
    @Bind(R.id.editTextIMEI)
    EditText editTextIMEI;
    @Bind(R.id.btnReadIMEI)
    Button btnReadIMEI;
    @Bind(R.id.tvGprsParam)
    TextView tvGprsParam;
    @Bind(R.id.radioTcpServer)
    RadioButton radioTcpServer;
    @Bind(R.id.radioUdpServer)
    RadioButton radioUdpServer;
    @Bind(R.id.editTextIP1)
    EditText editTextIP1;
    @Bind(R.id.editTextIP2)
    EditText editTextIP2;
    @Bind(R.id.editTextIP3)
    EditText editTextIP3;
    @Bind(R.id.editTextIP4)
    EditText editTextIP4;
    @Bind(R.id.editTextPort)
    EditText editTextPort;
    @Bind(R.id.btnSetComm)
    Button btnSetComm;
    @Bind(R.id.btnReadComm)
    Button btnReadComm;
    @Bind(R.id.btnGprsDefault)
    Button btnGprsDefault;





    @Bind(R.id.spinnerupdatecycle)
    Spinner spinnerupdatecycle;
    @Bind(R.id.spinnerupdatehour)
    Spinner spinnerupdatehour;
    @Bind(R.id.spinnerpwrbattery)
    Spinner spinnerpwrbattery;
    @Bind(R.id.spinnerpwrelectric)
    Spinner spinnerpwrelectric;
    @Bind(R.id.btnUserReading)
    Button btnUserReading;
    @Bind(R.id.btnUserUpdate)
    Button btnUserUpdate;
    @Bind(R.id.btnReadReadingParam)
    Button btnReadReadingParam;
    @Bind(R.id.btnSetReadingParam)
    Button btnSetReadingParam;


    @Bind(R.id.btnReadDatetime)
    Button btnReadDatetime;

    @Bind(R.id.textViewDateTime)
    TextView textViewDateTime;

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


    private String[] updatecycleitems = {"2小时","3小时","4小时","6小时","8小时","12小时"};
    public static int[] arrupdatecycle = {2,3,4,6,8,12};
    private String[] updatehouritems  = {"0点","1点","2点","3点","4点","5点","6点","7点","8点","9点","10点",
            "11点","12点","13点","14点","15点","16点","17点","18点","19点",
            "20点","21点","22点","23点"};
    public static int[] arrupdatehour = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};
    private String[] pwrbatteryitems =  {"10分钟","15分钟","20分钟","30分钟","60分钟"};
    public static int[] arrpwrbattery = {10,15,20,30,60};
    private String[] pwrelectricitems   =  {"2分钟","3分钟","4分钟","5分钟","6分钟","7分钟","8分钟","9分钟","10分钟"};
    public static int[] arrpwrelectric = {2,3,4,5,6,7,8,9,10};
    ArrayAdapter<String> updatecyclelist ;
    ArrayAdapter<String> updatehourlist  ;
    ArrayAdapter<String> pwrbatterylist  ;
    ArrayAdapter<String> pwrelectriclist ;

    //private static String strDeviceTypeCode = "20";
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    private int mSecond;
    private static final int SHOW_DATAPICK = 0;
    private static final int DATE_DIALOG_ID = 1;
    private static final int SHOW_TIMEPICK = 2;
    private static final int TIME_DIALOG_ID = 3;
    private static final int SHOW_CLOSEDATAPICK = 4;
    private static final int CLOSEDATE_DIALOG_ID = 5;
    private Timer datetimetimer = new Timer();
    static ColorStateList colorgray ;
    static ColorStateList colordarkgreen ;
    static boolean autoRefreshDateTime ;

    Resources resource;

    boolean isSetParamLegal;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.metergprs_layout, container, false);
        ButterKnife.bind(this, view);
        context = getContext();
        autoRefreshDateTime = false;
        isSetParamLegal = true;
        resource = (Resources) context.getResources();
        colorgray = (ColorStateList) resource.getColorStateList(R.color.gray);
        colordarkgreen= (ColorStateList) resource.getColorStateList(R.color.darkgreen);
        updatecyclelist = new ArrayAdapter<String>(context,R.layout.spinner_layout_head,updatecycleitems);
        updatehourlist = new ArrayAdapter<String>(context,R.layout.spinner_layout_head,updatehouritems);
        pwrbatterylist = new ArrayAdapter<String>(context,R.layout.spinner_layout_head,pwrbatteryitems);
        pwrelectriclist = new ArrayAdapter<String>(context,R.layout.spinner_layout_head,pwrelectricitems);

        //FrameLayout generalActionBar = (FrameLayout) view.findViewById(R.id.general_actionbar);
        //TextView tvTitle = (TextView) generalActionBar.findViewById(R.id.tv_explore_scan);
        //tvTitle.setText(R.string.main_tab_name_gprs);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void initView(View view) {
        btnSetIMEI.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                tvIMEI.setTextColor(colorgray);
                //7B89002B3030303030303030303030684811111111001111070EC1410031323334353637383930316B167B
                String newimei = editTextIMEI.getText().toString().replace(" ", "");
                if(newimei.length()==11){
                    int checksum = 557;
                    StringBuilder sb = new StringBuilder();
                    sb.append("7B89002B3030303030303030303030684811111111001111070EC14100");
                    for(int i=0;i<newimei.length();i++){
                        sb.append('3');
                        sb.append(newimei.charAt(i));
                        checksum+= 3*16+Integer.parseInt(""+(newimei.charAt(i)));
                    }
                    String cs = Integer.toHexString(checksum);
                    sb.append(cs.substring(cs.length()-2));
                    sb.append("167B");
                    //Toast.makeText(getApplicationContext(), tx, Toast.LENGTH_SHORT).show();
                    System.out.println(sb.toString());
                    MainActivity.smsg = "";
                    MainActivity.writeData(sb.toString());
                }else{
                    Toast.makeText(context, "请输入11位IMEI号！", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnReadIMEI.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.gray);
                tvIMEI.setTextColor(colorgray);
                //EditTextmeterid.setText("");
                String tx="7B89002030303030303030303030306848111111110011110703C1420023167B";
                MainActivity.smsg = "";
                // Toast.makeText(getApplicationContext(), tx, Toast.LENGTH_SHORT).show();
                MainActivity.writeData(tx);
            }
        });

        radioTcpServer.setChecked(true);
        radioTcpServer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                radioTcpServer.setChecked(true);
                radioUdpServer.setChecked(false);

            }
        });
        radioUdpServer.setChecked(false);
        radioUdpServer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                radioTcpServer.setChecked(false);
                radioUdpServer.setChecked(true);

            }
        });
        btnGprsDefault.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                editTextIP1.setText("58");
                editTextIP2.setText("240");
                editTextIP3.setText("47");
                editTextIP4.setText("50");


            }
        });
        btnSetComm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                tvGprsParam.setTextColor(colorgray);
                //7B89003D30303030303030303030306848111111110011110703C11E0122544350222C2235382E3234302E34372E3530222C2235303034220D2361167B
                StringBuilder sb = new StringBuilder();
                sb.append("7B89003D30303030303030303030306848111111110011110703C11E01");
                TcpUdpParam param = new TcpUdpParam();
                if(radioTcpServer.isChecked()){
                    param.setMode("TCP");
                }else{
                    param.setMode("UDP");
                }
                param.setIp1( editTextIP1.getText().toString().replace(" ", ""));
                param.setIp2( editTextIP2.getText().toString().replace(" ", ""));
                param.setIp3( editTextIP3.getText().toString().replace(" ", ""));
                param.setIp4( editTextIP4.getText().toString().replace(" ", ""));
                param.setPort( editTextPort.getText().toString().replace(" ", ""));
                // MainActivity.writeData(tx);
                if(param.isIsvalid()){
                    sb.append(getHexStr(param));
                    sb.append("0D23");

                    String checkStr = sb.substring(sb.indexOf("68"));
                    int len = checkStr.length()-20;
                    //StringBuffer sbf = new StringBuffer(checkStr);
                   // sb.replace(50, 52, len<17?("0"+Integer.toHexString(len)):(Integer.toHexString(len)));
                    checkStr = sb.substring(sb.indexOf("68"));
                    int checksum = getCheckSum(checkStr);
                    String cs = Integer.toHexString(checksum);
                    sb.append(cs.substring(cs.length()-2));
                    sb.append("167B");
                    sb.replace(6, 8, sb.length()/2<17?("0"+Integer.toHexString(sb.length()/2)):(Integer.toHexString(sb.length()/2)));
                    MainActivity.smsg = "";
                    System.out.println("settcp:"+sb.toString());
                    MainActivity.writeData(sb.toString());
                }else{
                    Toast.makeText(context, "IP或端口输入错误！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnReadComm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //EditTextmeterid.setText("");
                //MainActivity.smsg = "";
                tvGprsParam.setTextColor(colorgray);
                MainActivity.smsg = "";
                String tx="7B89002130303030303030303030306848111111110011110703C12F000111167B";
                MainActivity.writeData(tx);
            }
        });
        btnReadDatetime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                textViewDateTime.setTextColor(colorgray);
                MainActivity.smsg = "";
                String tx="7B89002030303030303030303030306848111111110011110403A11700D5167B";
                MainActivity.writeData(tx);
            }
        });
       // btnSynchDatetime.setOnClickListener(new View.OnClickListener() {

      //      @Override
      //      public void onClick(View arg0) {
                //7B8900273030303030303030303030684811111111001111040AA0150016 150803081620 4D167B
                /*textViewDateTime.setTextColor(colorgray);
                if(checkBoxDateRefresh.isChecked()){
                    Calendar mCalendar= Calendar.getInstance();
                    int mYear = mCalendar.get(Calendar.YEAR);
                    int mMonth = mCalendar.get(Calendar.MONTH)+1;
                    int mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
                    int mHour= mCalendar.get(Calendar.HOUR_OF_DAY);
                    int mMinuts= mCalendar.get(Calendar.MINUTE);
                    int mSenconds= mCalendar.get(Calendar.SECOND);
                    int checksum = 473;
                    StringBuilder sb = new StringBuilder();
                    sb.append("7B8900273030303030303030303030684811111111001111040AA01500");
                    sb.append(mSenconds/10+""+mSenconds%10);
                    checksum += (mSenconds/10*16+mSenconds%10);
                    sb.append(mMinuts/10+""+mMinuts%10);
                    checksum += (mMinuts/10*16+mMinuts%10);
                    sb.append(mHour/10+""+mHour%10);
                    checksum += (mHour/10*16+mHour%10);
                    sb.append(mDay/10+""+mDay%10);
                    checksum += (mDay/10*16+mDay%10);
                    sb.append(mMonth/10+""+mMonth%10);
                    checksum += (mMonth/10*16+mMonth%10);
                    System.out.println("mYear:"+mYear);
                    System.out.println((mYear%100)/10+""+(mYear%100)%10);
                    sb.append((mYear%100)/10+""+(mYear%100)%10);
                    checksum += ((mYear%100)/10*16+(mYear%100)%10);
                    sb.append((mYear/100)/10+""+(mYear/100)%10);
                    checksum += ((mYear/100)/10*16+(mYear/100)%10);
                    String cs = Integer.toHexString(checksum);
                    sb.append(cs.substring(cs.length()-2));
                    sb.append("167B");
                    //Toast.makeText(getApplicationContext(), tx, Toast.LENGTH_SHORT).show();
                    System.out.println(sb.toString());
                    MainActivity.smsg = "";
                    MainActivity.writeData(sb.toString());
                }else{

                    String newYear = editTextYear.getText().toString().replace(" ", "");
                    String newMonth = editTextMonth.getText().toString().replace(" ", "");
                    String newDay = editTextDay.getText().toString().replace(" ", "");
                    String newHour = editTextHour.getText().toString().replace(" ", "");
                    String newMinute = editTextMinute.getText().toString().replace(" ", "");
                    String newSecond = editTextSecond.getText().toString().replace(" ", "");
                    int iYear = Integer.parseInt(newYear);
                    int iMonth = Integer.parseInt(newMonth);
                    int iDay = Integer.parseInt(newDay);
                    int iHour = Integer.parseInt(newHour);
                    int iMinute = Integer.parseInt(newMinute);
                    int iSecond = Integer.parseInt(newSecond);

                    if(iMonth<13 && iDay<32 && iHour<24 && iMinute<60 && iSecond<60){
                        int checksum = 473;
                        StringBuilder sb = new StringBuilder();
                        sb.append("7B8900273030303030303030303030684811111111001111040AA01500");
                        sb.append(newSecond);
                        checksum += (iSecond/10*16+iSecond%10);
                        sb.append(newMinute);
                        checksum += (iMinute/10*16+iMinute%10);
                        sb.append(newHour);
                        checksum += (iHour/10*16+iHour%10);
                        sb.append(newDay);
                        checksum += (iDay/10*16+iDay%10);
                        sb.append(newMonth);
                        checksum += (iMonth/10*16+iMonth%10);
                        sb.append(newYear.substring(2));
                        sb.append(newYear.substring(0,2));
                        checksum += (iYear%100/10*16+iYear%100%10);
                        checksum += (iYear/100/10*16+iYear/100%10);
                        String cs = Integer.toHexString(checksum);
                        sb.append(cs.substring(cs.length()-2));
                        sb.append("167B");
                        System.out.println(sb.toString());
                        MainActivity.smsg = "";
                        MainActivity.writeData(sb.toString());

                    }else{
                        Toast.makeText(context, "日期或时间输入错误！", Toast.LENGTH_SHORT).show();

                    }
                }*/
         //   }
        //});


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
                String strimei="";
                strimei=tvIMEI.getText().toString().replace(" ", "");
                tvIMEI.setText(strimei);
                if  (strimei.length() != 11) {
                    Toast.makeText(context,"请输入11位出厂编码",Toast.LENGTH_SHORT).show();
                } else{

                    String x="";
                    String rx="";
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    SimpleDateFormat sdf1=new SimpleDateFormat("ssmmHHddMMyyyy");
                    x=EditTextdatetime.getText().toString();
                    try
                    {
                        Date xd=sdf.parse(x);
                        rx=sdf1.format(xd);
                        rx=rx.substring(0, 10)+rx.substring(12, 14)+rx.substring(10, 12);
                    }catch (Exception e) {
                        Toast.makeText(context,"请正确输入当前时间",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int checksum = 473;
                    StringBuilder sb = new StringBuilder();
                    sb.append("7B8900273030303030303030303030684811111111001111040AA01500");
                    sb.append(rx);
                    //int temp=
                    checksum += Integer.parseInt(rx.substring(0,1))*16+Integer.parseInt(rx.substring(1,2));
                    checksum += Integer.parseInt(rx.substring(2,3))*16+Integer.parseInt(rx.substring(3,4));
                    checksum += Integer.parseInt(rx.substring(4,5))*16+Integer.parseInt(rx.substring(5,6));
                    checksum += Integer.parseInt(rx.substring(6,7))*16+Integer.parseInt(rx.substring(7,8));
                    checksum += Integer.parseInt(rx.substring(8,9))*16+Integer.parseInt(rx.substring(9,10));
                    checksum += Integer.parseInt(rx.substring(10,11))*16+Integer.parseInt(rx.substring(11,12));
                    checksum += Integer.parseInt(rx.substring(12,13))*16+Integer.parseInt(rx.substring(13,14));
                    String cs = Integer.toHexString(checksum);
                    sb.append(cs.substring(cs.length()-2));
                    sb.append("167B");

                    //EditTextsend.setText(tx);
                    MainActivity.smsg = "";
                    MainActivity.writeData(sb.toString());
                }
            }
        });

        spinnerupdatecycle.setAdapter(updatecyclelist);
        spinnerupdatecycle.setSelection(5, false);
        spinnerupdatehour.setAdapter(updatehourlist);
        spinnerupdatehour.setSelection(6, false);
        spinnerpwrbattery.setAdapter(pwrbatterylist);
        spinnerpwrbattery.setSelection(2, false);
        spinnerpwrelectric.setAdapter(pwrelectriclist);
        spinnerpwrelectric.setSelection(0, false);
        spinnerupdatecycle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                 @Override
                 public void onItemSelected(AdapterView<?> arg0, View arg1,
                                            int arg2, long arg3) {
                     switch (arg2) {
                         case 4:
                             if (spinnerpwrbattery.getSelectedItemPosition() == 0) {
                                 isSetParamLegal = false;
                                 Toast.makeText(context, "您选择的抄表周期与上传周期冲突！", Toast.LENGTH_SHORT).show();
                             } else {
                                 isSetParamLegal = true;
                             }
                             break;
                         case 5:
                             if (spinnerpwrbattery.getSelectedItemPosition() == 0
                                     || spinnerpwrbattery.getSelectedItemPosition() == 1) {
                                 isSetParamLegal = false;
                                 Toast.makeText(context, "您选择的抄表周期与上传周期冲突！", Toast.LENGTH_SHORT).show();
                             } else {
                                 isSetParamLegal = true;
                             }
                             break;
                         default:
                             isSetParamLegal = true;
                             break;
                     }
                 }

                 @Override
                 public void onNothingSelected(AdapterView<?> parent) {

                 }
             }
        );
        spinnerpwrbattery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                switch(arg2){
                    case 0:
                        if(spinnerupdatecycle.getSelectedItemPosition() >= 4){
                            isSetParamLegal = false;
                            Toast.makeText(context, "您选择的抄表周期与上传周期冲突！", Toast.LENGTH_SHORT).show();
                        }else{
                            isSetParamLegal = true;
                        }
                        break;
                    case 1:
                        if(spinnerupdatecycle.getSelectedItemPosition() >= 5){
                            isSetParamLegal = false;
                            Toast.makeText(context, "您选择的抄表周期与上传周期冲突！", Toast.LENGTH_SHORT).show();
                        }else{
                            isSetParamLegal = true;
                        }
                        break;
                    default:
                        isSetParamLegal = true;
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        btnUserReading.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                MainActivity.smsg = "";
                String tx="7B89002030303030303030303030306848111111110011112B0379450002167B";
                MainActivity.writeData(tx);

            }
        });
        btnUserUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                MainActivity.smsg = "";
                String tx="7B89002030303030303030303030306848111111110011112B0379460003167B";
                MainActivity.writeData(tx);

            }
        });
        btnReadReadingParam.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                MainActivity.smsg = "";
                String tx="7B890021303030303030303030303068481111111100111115048677004470167B";
                MainActivity.writeData(tx);

            }
        });
        btnSetReadingParam.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(isSetParamLegal){
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("7B89002C3030303030303030303030");

                    StringBuilder sb = new StringBuilder();
                    sb.append("684811111111001111150F857600");
                    int updatecycle = spinnerupdatecycle.getSelectedItemPosition();
                    if(arrupdatecycle[updatecycle]<16)
                        sb.append("0");
                    sb.append(Integer.toHexString(arrupdatecycle[updatecycle]));
                    int updatehour = spinnerupdatehour.getSelectedItemPosition();
                    if(arrupdatehour[updatehour]<16)
                        sb.append("0");
                    sb.append(Integer.toHexString(arrupdatehour[updatehour]));
                    int pwrbattery = spinnerpwrbattery.getSelectedItemPosition();
                    if(arrpwrbattery[pwrbattery]<16)
                        sb.append("0");
                    sb.append(Integer.toHexString(arrpwrbattery[pwrbattery]));
                    int pwrelectric = spinnerpwrelectric.getSelectedItemPosition();
                    if(arrpwrelectric[pwrelectric]<16)
                        sb.append("0");
                    sb.append(Integer.toHexString(arrpwrelectric[pwrelectric]));
                    sb.append("0000000000000000");
                    int checksum =getCheckSum(sb.toString());
                    String cs = Integer.toHexString(checksum);
                    sb.append(cs.substring(cs.length()-2));
                    sb.append("167B");
                    stringBuilder.append(sb.toString());
                    //Toast.makeText(getApplicationContext(), stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                    MainActivity.smsg = "";
                    String tx = stringBuilder.toString().toUpperCase().replace(" ", "");
                    MainActivity.writeData(tx);
                }else{
                    Toast.makeText(context, "您选择的抄表周期与上传周期冲突！", Toast.LENGTH_SHORT).show();

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
                msg.what = 1;
                handler.sendMessage(msg);
            }
        }, 0, 1000);
    }

    @Override
    public void initData() {
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

    private void onClickUpdate() {

        //new UpdateManager(getActivity(), true).checkUpdate();
    }
    public String getHexStr(TcpUdpParam param){
        TcpUdpParam tcpUdpParam = param;
        String strtcp = tcpUdpParam.toString();
        byte[] tcpbytes = strtcp.getBytes();
        StringBuilder sb = new StringBuilder();
        for(int i=0;i< tcpbytes.length;i++){
            sb.append(Integer.toHexString(tcpbytes[i]));
        }
        System.out.println(sb.toString().toUpperCase());
        return sb.toString().toUpperCase();
    }

    public int getCheckSum(String param){
        StringBuilder sb = new StringBuilder();
        int res = 0;
        for(int i = 0;i<param.length()/2;i++){
            res+= Frequent.HexS2ToInt(param.substring(i*2,i*2+2));
        }
        return res;
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
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
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
                case SHOW_CLOSEDATAPICK:
                    //showDialog(CLOSEDATE_DIALOG_ID);
                    break;
            }
        }

    };

}
