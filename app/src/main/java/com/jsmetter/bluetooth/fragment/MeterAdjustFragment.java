package com.jsmetter.bluetooth.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.jsmetter.bluetooth.AppContext;
import com.jsmetter.bluetooth.R;
import com.jsmetter.bluetooth.base.BaseFragment;
import com.jsmetter.bluetooth.util.Frequent;
import com.jsmetter.bluetooth.view.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


//import com.jsmetter.bluetooth.util.UIHelper;
//import com.jsmetter.bluetooth.util.UpdateManager;

public class MeterAdjustFragment extends BaseFragment implements View.OnClickListener {


    @Bind(R.id.EditTextmeterid)
    EditText EditTextmeterid;

    @Bind(R.id.Buttonreadparameter)
    Button Buttonreadparameter;

    @Bind(R.id.btnReset)
    Button btnReset;

    @Bind(R.id.EditTextqn_1)
    EditText EditTextqn_1;

    @Bind(R.id.EditTextqn_2)
    EditText EditTextqn_2;

    @Bind(R.id.EditTextqn_3)
    EditText EditTextqn_3;

    @Bind(R.id.EditTextqn2_1)
    EditText EditTextqn2_1;

    @Bind(R.id.EditTextqn2_2)
    EditText EditTextqn2_2;

    @Bind(R.id.EditTextqn2_3)
    EditText EditTextqn2_3;

    @Bind(R.id.EditTextqn1_1)
    EditText EditTextqn1_1;

    @Bind(R.id.EditTextqn1_2)
    EditText EditTextqn1_2;

    @Bind(R.id.EditTextqn1_3)
    EditText EditTextqn1_3;

    @Bind(R.id.EditTextqmin_1)
    EditText EditTextqmin_1;

    @Bind(R.id.EditTextqmin_2)
    EditText EditTextqmin_2;

    @Bind(R.id.EditTextqmin_3)
    EditText EditTextqmin_3;

    @Bind(R.id.edtTotalUsed)
    EditText edtTotalUsed;

    //@Bind(R.id.EditTexttotalpoint)
    //EditText EditTexttotalpoint;

    @Bind(R.id.Buttonsetq)
    Button Buttonsetq;

    @Bind(R.id.btnAmendUsed)
    Button btnAmendUsed;

    @Bind(R.id.spinnerTotalpoint)
    Spinner spinnerTotalpoint;

    @Bind(R.id.ButtonsetAdjust)
    Button ButtonsetAdjust;

    @Bind(R.id.EditTextadjust)
    EditText EditTextadjust;

    String meterId = "";
    private String[] totalpointItems1 = {"1000","100","10"};
    private String[] totalpointItems = {"DN15~40","DN50~125","DN150以上"};
    ArrayAdapter<String> totalpointAdapter;
    private static String strDeviceTypeCode = "10";

    public static Context context;
    AppContext appContext ;
    public  boolean userJurisdiction ;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.meteradjust_layout, container, false);
        ButterKnife.bind(this, view);
        userJurisdiction = true;
        context = getContext();
        appContext = AppContext.getInstance();
        initView(view);
        initData();
        return view;
    }

    @Override
    public void initView(View view) {
        btnAmendUsed.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        Buttonreadparameter.setOnClickListener(this);
        Buttonsetq.setOnClickListener(this);
        ButtonsetAdjust.setOnClickListener(this);

        EditTextqn_1.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double x1,x2,xx,xn=0;
                try
                {
                    x1=-Double.valueOf(s.toString().replace(" ", ""));
                }catch (Exception e) {
                    return;
                }
                try
                {
                    xx=Double.valueOf(EditTextqn_2.getText().toString().replace(" ", ""));
                    xn=xx*(1+x1*0.01);
                }catch (Exception e) {
                    return;
                }
                EditTextqn_3.setText(String.format(String.valueOf((int)xn)));
            }
        } );
        EditTextqn2_1.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double x1,xx,xn=0;
                try
                {
                    x1=-Double.valueOf(s.toString().replace(" ", ""));
                }catch (Exception e) {

                    return;
                }
                try
                {
                    xx=Double.valueOf(EditTextqn2_2.getText().toString().replace(" ", ""));
                    xn=xx*(1+x1*0.01);
                }catch (Exception e) {

                    return;
                }
                EditTextqn2_3.setText(String.format(String.valueOf((int)xn)));
            }
        });
        EditTextqn1_1.addTextChangedListener(new TextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {
                //
                double x1,x2,xx,xn=0;
                try
                {
                    x1=-Double.valueOf(s.toString().replace(" ", ""));
                }catch (Exception e) {
                    //Toast.makeText(showcontext, "error1", Toast.LENGTH_SHORT).show();
                    return;
                }
                try
                {
                    xx=Double.valueOf(EditTextqn1_2.getText().toString().replace(" ", ""));
                    xn=xx*(1+x1*0.01);
                }catch (Exception e) {
                    // Toast.makeText(showcontext, "error2", Toast.LENGTH_SHORT).show();
                    return;
                }
                EditTextqn1_3.setText(String.format(String.valueOf((int)xn)));
                //  try
                // {
                //	   x2=Double.valueOf(s.toString().replace(" ", ""));
                //  }catch (Exception e) {
                //	   return;
                //   }
                //DecimalFormat df = new DecimalFormat("#.##");
                //EditTextqn2_1.setText(df.format((x1+x2)/2));
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO 自动生成的方法存根
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO 自动生成的方法存根
            }
        });
        EditTextqmin_1.addTextChangedListener(new TextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {
                //
                double x1,xx,xn=0;
                try
                {
                    x1=-Double.valueOf(s.toString().replace(" ", ""));
                }catch (Exception e) {
                    return;
                }
                try
                {
                    xx=Double.valueOf(EditTextqmin_2.getText().toString().replace(" ", ""));
                    xn=xx*(1+x1*0.01);
                }catch (Exception e) {
                    return;
                }
                EditTextqmin_3.setText(String.format(String.valueOf((int)xn)));
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO 自动生成的方法存根

            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO 自动生成的方法存根
            }
        });
        totalpointAdapter = new ArrayAdapter<String>(AppContext.context(), R.layout.spinner_layout_head,totalpointItems);
        spinnerTotalpoint.setAdapter(totalpointAdapter);
        spinnerTotalpoint.setSelection(0, false);
    }

    @Override
    public void initData() {
        //mTvVersionName.setText(TDevice.getVersionName());
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.ButtonsetAdjust:
                meterId="";
                meterId=EditTextmeterid.getText().toString().replace(" ", "");
                EditTextmeterid.setText(meterId);
                if  (meterId.length()!=8) {
                    Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                } else{
                    if (!meterId.endsWith("FFFFFFFF")){
                        try
                        {
                            Integer.parseInt(meterId);
                        }catch (Exception e) {
                            Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    SharedPreferences sharedPreferences = context.getSharedPreferences("setCodeParam", Context.MODE_PRIVATE);
                    @SuppressWarnings("unchecked")
                    Map<String,String> map = (Map<String, String>) sharedPreferences.getAll();
                    if(userJurisdiction)
                        SetAdjust();
                    else {
                        if (map.get(meterId.toString() + "code") != null) {
                            if (CheckPassword(map.get(meterId.toString() + "code")))
                                SetAdjust();
                            else
                                ShowDialog(1);
                        } else
                            ShowDialog(1);
                    }
                }

                break;
            case R.id.Buttonsetq:
                meterId="";
                meterId=EditTextmeterid.getText().toString().replace(" ", "");
                EditTextmeterid.setText(meterId);
                if  (meterId.length()!=8) {
                    Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                } else{
                    if (!meterId.endsWith("FFFFFFFF")){
                        try
                        {
                            Integer.parseInt(meterId);
                        }catch (Exception e) {
                            Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    SharedPreferences sharedPreferences = context.getSharedPreferences("setCodeParam", Context.MODE_PRIVATE);
                    @SuppressWarnings("unchecked")
                    Map<String,String> map = (Map<String, String>) sharedPreferences.getAll();
                    if(userJurisdiction)
                        SetTQ();
                    else {
                        if (map.get(meterId.toString() + "code") != null) {
                            if (CheckPassword(map.get(meterId.toString() + "code")))
                                SetTQ();
                            else
                                ShowDialog(1);
                        } else
                            ShowDialog(1);
                    }
                }
                break;
            case R.id.btnAmendUsed:
                meterId="";
                meterId=EditTextmeterid.getText().toString().replace(" ", "");
                EditTextmeterid.setText(meterId);
                if  (meterId.length()!=8) {
                    Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                } else{
                    if (!meterId.endsWith("FFFFFFFF")){
                        try
                        {
                            Integer.parseInt(meterId);
                        }catch (Exception e) {
                            Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    SharedPreferences sharedPreferences = context.getSharedPreferences("setCodeParam", Context.MODE_PRIVATE);
                    @SuppressWarnings("unchecked")
                    Map<String,String> map = (Map<String, String>) sharedPreferences.getAll();
                    if(userJurisdiction)
                        SetTotalUsed();
                    else {
                        if (map.get(meterId.toString() + "code") != null) {
                            if (CheckPassword(map.get(meterId.toString() + "code")))
                                SetTotalUsed();
                            else
                                ShowDialog(3);
                        } else
                            ShowDialog(3);
                    }
                }
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
                }
                break;
            case R.id.Buttonreadparameter:
                EditTextmeterid.setText("");
                EditTextqn_1.setText("");
                EditTextqn_2.setText("");
                EditTextqn_3.setText("");

                EditTextqn2_1.setText("");
                EditTextqn2_2.setText("");
                EditTextqn2_3.setText("");

                EditTextqn1_1.setText("");
                EditTextqn1_2.setText("");
                EditTextqn1_3.setText("");

                EditTextqmin_1.setText("");
                EditTextqmin_2.setText("");
                EditTextqmin_3.setText("");
                MainActivity.smsg = "";
                String tx="6810AAAAAAAAAAAAAA1A039A2F000416";
                MainActivity.writeData(tx);
                break;
            case R.id.btnReset:
                meterId="";
                meterId=EditTextmeterid.getText().toString().replace(" ", "");
                EditTextmeterid.setText(meterId);
                if  (meterId.length()!=8) {
                    Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                } else{
                    if (!meterId.endsWith("FFFFFFFF")){
                        try
                        {
                            Integer.parseInt(meterId);
                        }catch (Exception e) {
                            Toast.makeText(context,"请输入8位出厂编码",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    AlertDialog dialog1= new AlertDialog.Builder(context)
                            .setTitle("警告!")
                            .setMessage("确定要恢复出厂值吗？")
                            .setPositiveButton("确定",
                                    new DialogInterface.OnClickListener(){
                                        public void onClick(DialogInterface dialog,int whichButton){
                                            int xi = 0,yi = 0,zi = 0,oi=0;
                                            SharedPreferences sharedPreferences = context.getSharedPreferences("setQnParam", Context.MODE_PRIVATE);
                                            Map<String,String> map = (Map<String, String>) sharedPreferences.getAll();
                                            for (Map.Entry<String,String> entry : map.entrySet()) {
                                                if(entry.getKey().endsWith(meterId+"qn_2"))
                                                    xi = Integer.parseInt(entry.getValue());
                                                if(entry.getKey().endsWith(meterId+"qn2_2"))
                                                    yi = Integer.parseInt(entry.getValue());
                                                if(entry.getKey().endsWith(meterId+"qn1_2"))
                                                    zi = Integer.parseInt(entry.getValue());
                                                if(entry.getKey().endsWith(meterId+"qmin_2"))
                                                    oi = Integer.parseInt(entry.getValue());
                                                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                                            }

                                            if(xi!=0&&yi!=0&&zi!=0&&oi!=0){
                                                String tx=amendq(meterId,strDeviceTypeCode,"001111",xi,yi,zi,oi);
                                                EditTextqn_1.setText("");
                                                EditTextqn_2.setText("");
                                                EditTextqn_3.setText("");

                                                EditTextqn2_1.setText("");
                                                EditTextqn2_2.setText("");
                                                EditTextqn2_3.setText("");

                                                EditTextqn1_1.setText("");
                                                EditTextqn1_2.setText("");
                                                EditTextqn1_3.setText("");

                                                EditTextqmin_1.setText("");
                                                EditTextqmin_2.setText("");
                                                EditTextqmin_3.setText("");

                                                EditTextqn_3.setText(String.valueOf(xi));
                                                EditTextqn2_3.setText(String.valueOf(yi));
                                                EditTextqn1_3.setText(String.valueOf(zi));
                                                EditTextqmin_3.setText(String.valueOf(oi));
                                                EditTextqn_2.setText(String.valueOf(xi));
                                                EditTextqn2_2.setText(String.valueOf(yi));
                                                EditTextqn1_2.setText(String.valueOf(zi));
                                                EditTextqmin_2.setText(String.valueOf(oi));
                                                //MainActivity.smsg = "";
                                                //MainActivity.writeData(tx);
                                            }else{
                                                Toast.makeText(context,"没有保存出厂值！",Toast.LENGTH_SHORT).show();
                                            }

                                        } //第二个按钮设置
                                    })
                            .setNegativeButton("取消",new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog,int whichButton){


                                } //第二个按钮设置
                            })
                            .create();  //创建对话框
                    dialog1.show(); //显示对话框
                }
                break;
            default:
                break;
        }
    }
    void SetAdjust(){
        String x="";
        int xi=0;
        x=EditTextadjust.getText().toString().replace(" ", "");
        EditTextadjust.setText(x);
        try {
            xi=Integer.parseInt(x);
            if (xi>1500){
                Toast.makeText(context,"请正确输入调整值(<1500)",Toast.LENGTH_SHORT).show();
                return;
            }
            if (xi<500){
                Toast.makeText(context,"请正确输入调整值(>=500)",Toast.LENGTH_SHORT).show();
                return;
            }
        }catch (Exception e) {
            Toast.makeText(context,"请正确输入调整值(<255)",Toast.LENGTH_SHORT).show();
            return;
        }
        String tx=adjust(meterId,strDeviceTypeCode,"001111",xi);
        MainActivity.smsg = "";
        MainActivity.writeData(tx);
    }
    void SetTQ(){
        String x="";
        int xi=0;
        x=EditTextqn_3.getText().toString().replace(" ", "");
        EditTextqn_3.setText(x);
        try{
            xi=Integer.parseInt(x);
            if (xi>65535){
                Toast.makeText(context,"请正确输入常用流量系数(<65535)",Toast.LENGTH_SHORT).show();
                return;
            }
        }catch (Exception e) {
            Toast.makeText(context,"请正确输入常用流量系数(<65535)",Toast.LENGTH_SHORT).show();
            return;
        }
        String y="";
        int yi=0;
        y=EditTextqn2_3.getText().toString().replace(" ", "");
        EditTextqn2_3.setText(y);
        try{
            yi=Integer.parseInt(y);
            if (yi>65535){
                Toast.makeText(context,"请正确输入0.2常用流量系数(<65535)",Toast.LENGTH_SHORT).show();
                return;
            }
        }catch (Exception e) {
            Toast.makeText(context,"请正确输入0.2常用流量系数(<65535)",Toast.LENGTH_SHORT).show();
            return;
        }
        String z="";
        int zi=0;
        z=EditTextqn1_3.getText().toString().replace(" ", "");
        EditTextqn1_3.setText(z);
        try{
            zi=Integer.parseInt(z);
            if (zi>65535){
                Toast.makeText(context,"请正确输入0.1常用流量系数(<65535)",Toast.LENGTH_SHORT).show();
                return;
            }
        }catch (Exception e) {
            Toast.makeText(context,"请正确输入0.1常用流量系数(<65535)",Toast.LENGTH_SHORT).show();
            return;
        }
        String o="";
        int oi=0;
        o=EditTextqmin_3.getText().toString().replace(" ", "");
        EditTextqmin_3.setText(o);
        try{
            oi=Integer.parseInt(o);
            if (oi>65535){
                Toast.makeText(context,"请正确输入最小流量系数(<65535)",Toast.LENGTH_SHORT).show();
                return;
            }
        }catch (Exception e) {
            Toast.makeText(context,"请正确输入最小流量系数(<65535)",Toast.LENGTH_SHORT).show();
            return;
        }
        String tx=amendq(meterId,strDeviceTypeCode,"001111",xi,yi,zi,oi);
        EditTextqn_1.setText("");
        EditTextqn_2.setText("");
        EditTextqn_3.setText("");

        EditTextqn2_1.setText("");
        EditTextqn2_2.setText("");
        EditTextqn2_3.setText("");

        EditTextqn1_1.setText("");
        EditTextqn1_2.setText("");
        EditTextqn1_3.setText("");

        EditTextqmin_1.setText("");
        EditTextqmin_2.setText("");
        EditTextqmin_3.setText("");
        MainActivity.smsg = "";
        MainActivity.writeData(tx);
    }

    void SetTotalUsed(){
        String z="";
        double zd=0;
        int zi=0;
        z=edtTotalUsed.getText().toString().replace(" ", "");
        edtTotalUsed.setText(z);
        try
        {
            zd=Double.valueOf(z);
            if (zd>99999999){
                Toast.makeText(context,"请正确流量值(<99999999)",Toast.LENGTH_SHORT).show();
                return;
            }
        }catch (Exception e) {
            Toast.makeText(context,"请正确输入流量值(<99999999)",Toast.LENGTH_SHORT).show();
            return;
        }
        if("DN15~40".equals(totalpointItems[spinnerTotalpoint.getSelectedItemPosition()]))
            zd *= 1000;
        if("DN50~125".equals(totalpointItems[spinnerTotalpoint.getSelectedItemPosition()]))
            zd *= 100;
        if("DN150以上".equals(totalpointItems[spinnerTotalpoint.getSelectedItemPosition()]))
            zd *= 10;
        zi=(int)zd;
        String tx=amendused(meterId,strDeviceTypeCode,"001111",0,0,zi);
        MainActivity.smsg = "";
        MainActivity.writeData(tx);
    }

    void ShowDialog(final int type){
        final EditText inputServer = new EditText(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.amenderr_notice).setIcon(android.R.drawable.ic_dialog_info).setView(inputServer)
                .setNegativeButton(R.string.amenderr_setCancle, null);
        builder.setPositiveButton(R.string.amenderr_setOk, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                if(CheckPassword(inputServer.getText().toString().replace(" ", ""))){
                   // appContext.pu

                    SharedPreferences sharedPreferences = context.getSharedPreferences("setCodeParam", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(meterId.toString()+"code",inputServer.getText().toString().replace(" ", ""));
                    editor.commit();//提交修改
                    if(type==1)
                        SetTQ();
                        // else if(type==2)
                        //	SetAdjust();
                    else if(type==3)
                        SetTotalUsed();
                }else{
                    Toast.makeText(context, "密码校验错误！请核实！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.show();

    }

    boolean CheckPassword(String pws){
        if(userJurisdiction)
            return  true;
        if(pws.length()!=8)
            return false;
        try{
            Integer.parseInt(pws);
        }catch (Exception e) {
            return false;
        }
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = sDateFormat.format(new java.util.Date());
        String res= "";
        for(int i=0;i<date.length();i++){
            if((date.charAt(7)-0x30)%2==1){
                res+=String.valueOf(((date.charAt(i)-0x30)+(meterId.charAt(7-i)-0x30))>=10?((date.charAt(i)-0x30)+(meterId.charAt(7-i)-0x30))-10:((date.charAt(i)-0x30)+(meterId.charAt(7-i)-0x30))).charAt(0);
            }else{
                res+=String.valueOf(Math.abs((date.charAt(i)-0x30)-(meterId.charAt(7-i)-0x30)));
            }
        }
        return res.equals(pws);
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

    public String adjust(String meterid,String producttypetx,String factorycode,int x){
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
            String cs= Frequent.getsum("68"+producttypetx+meterid+factorycode+"CD04901F"+rx,0);
            r=("68"+producttypetx+meterid+factorycode+"CD04901F"+rx+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return r;
    }

    public String amendused(String meterid,String producttypetx,String factorycode,int sumheat,int sumcool,int total){
        String r="";
        try{
            String rx,sx,qx="";
            rx=Integer.toHexString(sumheat);
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
            rx=rx.substring(0, 8);
            sx=Integer.toHexString(sumcool);
            switch (sx.length()) {
                case 0:sx="00000000";break;
                case 1:sx="0000000"+sx;break;
                case 2:sx="000000"+sx;break;
                case 3:sx="00000"+sx;break;
                case 4:sx="0000"+sx;break;
                case 5:sx="000"+sx;break;
                case 6:sx="00"+sx;break;
                case 7:sx="0"+sx;break;
                case 8:break;
                default:sx=sx.substring(sx.length()-8, sx.length());break;
            }
            sx=sx.substring(0, 8);
            qx=Integer.toHexString(total);
            switch (qx.length()) {
                case 0:qx="00000000";break;
                case 1:qx="0000000"+qx;break;
                case 2:qx="000000"+qx;break;
                case 3:qx="00000"+qx;break;
                case 4:qx="0000"+qx;break;
                case 5:qx="000"+qx;break;
                case 6:qx="00"+qx;break;
                case 7:qx="0"+qx;break;
                case 8:break;
                default:qx=qx.substring(qx.length()-8, qx.length());break;
            }
            qx=qx.substring(0, 8);
            String cs=Frequent.getsum("68"+producttypetx+meterid+factorycode+"3C0F901F00"+rx+sx+qx,0);
            r=("68"+producttypetx+meterid+factorycode+"3C0F901F00"+rx+sx+qx+cs+"16").toUpperCase();
        }catch(Exception e){
        }
        return r;
    }
    private void onClickUpdate() {

        //new UpdateManager(getActivity(), true).checkUpdate();
    }
}
