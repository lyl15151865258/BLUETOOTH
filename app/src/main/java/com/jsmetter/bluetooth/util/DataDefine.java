package com.jsmetter.bluetooth.util;

import android.os.Environment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class DataDefine {
	
	static final String HEXES = "0123456789ABCDEF";
	byte uchCRCHi = (byte) 0xFF;
	byte uchCRCLo = (byte) 0xFF;
	private static byte[] auchCRCHi = { 0x00, (byte) 0xC1, (byte) 0x81,
			(byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41,
			(byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00,
			(byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0,
			(byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81,
			(byte) 0x40, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40,
			(byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x01,
			(byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1,
			(byte) 0x81, (byte) 0x40, (byte) 0x00, (byte) 0xC1, (byte) 0x81,
			(byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41,
			(byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01,
			(byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0,
			(byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81,
			(byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41,
			(byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x00,
			(byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0,
			(byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81,
			(byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41,
			(byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00,
			(byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x00, (byte) 0xC1,
			(byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80,
			(byte) 0x41, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41,
			(byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01,
			(byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1,
			(byte) 0x81, (byte) 0x40, (byte) 0x00, (byte) 0xC1, (byte) 0x81,
			(byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41,
			(byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00,
			(byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x00, (byte) 0xC1,
			(byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80,
			(byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40,
			(byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x01,
			(byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1,
			(byte) 0x81, (byte) 0x40, (byte) 0x00, (byte) 0xC1, (byte) 0x81,
			(byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41,
			(byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00,
			(byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0,
			(byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81,
			(byte) 0x40, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40,
			(byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00,
			(byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0,
			(byte) 0x80, (byte) 0x41, (byte) 0x01, (byte) 0xC0, (byte) 0x80,
			(byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40,
			(byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00,
			(byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x00, (byte) 0xC1,
			(byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80,
			(byte) 0x41, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41,
			(byte) 0x00, (byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x00,
			(byte) 0xC1, (byte) 0x81, (byte) 0x40, (byte) 0x01, (byte) 0xC0,
			(byte) 0x80, (byte) 0x41, (byte) 0x00, (byte) 0xC1, (byte) 0x81,
			(byte) 0x40, (byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41,
			(byte) 0x01, (byte) 0xC0, (byte) 0x80, (byte) 0x41, (byte) 0x00,
			(byte) 0xC1, (byte) 0x81, (byte) 0x40 };

	private static byte[] auchCRCLo = { (byte) 0x00, (byte) 0xC0, (byte) 0xC1,
			(byte) 0x01, (byte) 0xC3, (byte) 0x03, (byte) 0x02, (byte) 0xC2,
			(byte) 0xC6, (byte) 0x06, (byte) 0x07, (byte) 0xC7, (byte) 0x05,
			(byte) 0xC5, (byte) 0xC4, (byte) 0x04, (byte) 0xCC, (byte) 0x0C,
			(byte) 0x0D, (byte) 0xCD, (byte) 0x0F, (byte) 0xCF, (byte) 0xCE,
			(byte) 0x0E, (byte) 0x0A, (byte) 0xCA, (byte) 0xCB, (byte) 0x0B,
			(byte) 0xC9, (byte) 0x09, (byte) 0x08, (byte) 0xC8, (byte) 0xD8,
			(byte) 0x18, (byte) 0x19, (byte) 0xD9, (byte) 0x1B, (byte) 0xDB,
			(byte) 0xDA, (byte) 0x1A, (byte) 0x1E, (byte) 0xDE, (byte) 0xDF,
			(byte) 0x1F, (byte) 0xDD, (byte) 0x1D, (byte) 0x1C, (byte) 0xDC,
			(byte) 0x14, (byte) 0xD4, (byte) 0xD5, (byte) 0x15, (byte) 0xD7,
			(byte) 0x17, (byte) 0x16, (byte) 0xD6, (byte) 0xD2, (byte) 0x12,
			(byte) 0x13, (byte) 0xD3, (byte) 0x11, (byte) 0xD1, (byte) 0xD0,
			(byte) 0x10, (byte) 0xF0, (byte) 0x30, (byte) 0x31, (byte) 0xF1,
			(byte) 0x33, (byte) 0xF3, (byte) 0xF2, (byte) 0x32, (byte) 0x36,
			(byte) 0xF6, (byte) 0xF7, (byte) 0x37, (byte) 0xF5, (byte) 0x35,
			(byte) 0x34, (byte) 0xF4, (byte) 0x3C, (byte) 0xFC, (byte) 0xFD,
			(byte) 0x3D, (byte) 0xFF, (byte) 0x3F, (byte) 0x3E, (byte) 0xFE,
			(byte) 0xFA, (byte) 0x3A, (byte) 0x3B, (byte) 0xFB, (byte) 0x39,
			(byte) 0xF9, (byte) 0xF8, (byte) 0x38, (byte) 0x28, (byte) 0xE8,
			(byte) 0xE9, (byte) 0x29, (byte) 0xEB, (byte) 0x2B, (byte) 0x2A,
			(byte) 0xEA, (byte) 0xEE, (byte) 0x2E, (byte) 0x2F, (byte) 0xEF,
			(byte) 0x2D, (byte) 0xED, (byte) 0xEC, (byte) 0x2C, (byte) 0xE4,
			(byte) 0x24, (byte) 0x25, (byte) 0xE5, (byte) 0x27, (byte) 0xE7,
			(byte) 0xE6, (byte) 0x26, (byte) 0x22, (byte) 0xE2, (byte) 0xE3,
			(byte) 0x23, (byte) 0xE1, (byte) 0x21, (byte) 0x20, (byte) 0xE0,
			(byte) 0xA0, (byte) 0x60, (byte) 0x61, (byte) 0xA1, (byte) 0x63,
			(byte) 0xA3, (byte) 0xA2, (byte) 0x62, (byte) 0x66, (byte) 0xA6,
			(byte) 0xA7, (byte) 0x67, (byte) 0xA5, (byte) 0x65, (byte) 0x64,
			(byte) 0xA4, (byte) 0x6C, (byte) 0xAC, (byte) 0xAD, (byte) 0x6D,
			(byte) 0xAF, (byte) 0x6F, (byte) 0x6E, (byte) 0xAE, (byte) 0xAA,
			(byte) 0x6A, (byte) 0x6B, (byte) 0xAB, (byte) 0x69, (byte) 0xA9,
			(byte) 0xA8, (byte) 0x68, (byte) 0x78, (byte) 0xB8, (byte) 0xB9,
			(byte) 0x79, (byte) 0xBB, (byte) 0x7B, (byte) 0x7A, (byte) 0xBA,
			(byte) 0xBE, (byte) 0x7E, (byte) 0x7F, (byte) 0xBF, (byte) 0x7D,
			(byte) 0xBD, (byte) 0xBC, (byte) 0x7C, (byte) 0xB4, (byte) 0x74,
			(byte) 0x75, (byte) 0xB5, (byte) 0x77, (byte) 0xB7, (byte) 0xB6,
			(byte) 0x76, (byte) 0x72, (byte) 0xB2, (byte) 0xB3, (byte) 0x73,
			(byte) 0xB1, (byte) 0x71, (byte) 0x70, (byte) 0xB0, (byte) 0x50,
			(byte) 0x90, (byte) 0x91, (byte) 0x51, (byte) 0x93, (byte) 0x53,
			(byte) 0x52, (byte) 0x92, (byte) 0x96, (byte) 0x56, (byte) 0x57,
			(byte) 0x97, (byte) 0x55, (byte) 0x95, (byte) 0x94, (byte) 0x54,
			(byte) 0x9C, (byte) 0x5C, (byte) 0x5D, (byte) 0x9D, (byte) 0x5F,
			(byte) 0x9F, (byte) 0x9E, (byte) 0x5E, (byte) 0x5A, (byte) 0x9A,
			(byte) 0x9B, (byte) 0x5B, (byte) 0x99, (byte) 0x59, (byte) 0x58,
			(byte) 0x98, (byte) 0x88, (byte) 0x48, (byte) 0x49, (byte) 0x89,
			(byte) 0x4B, (byte) 0x8B, (byte) 0x8A, (byte) 0x4A, (byte) 0x4E,
			(byte) 0x8E, (byte) 0x8F, (byte) 0x4F, (byte) 0x8D, (byte) 0x4D,
			(byte) 0x4C, (byte) 0x8C, (byte) 0x44, (byte) 0x84, (byte) 0x85,
			(byte) 0x45, (byte) 0x87, (byte) 0x47, (byte) 0x46, (byte) 0x86,
			(byte) 0x82, (byte) 0x42, (byte) 0x43, (byte) 0x83, (byte) 0x41,
			(byte) 0x81, (byte) 0x80, (byte) 0x40 };

	public int value;

	
	  // Message types sent from the BluetoothService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    public static final int MESSAGE_DATAS = 6;
    public static final int AutoScan = 7;
    public static final int ScanDevice =8;
    public static final int MESSAGE_LOCAL_IN_NET =9;
    
    public final static byte MESSAGE_READNODE_ANSWER = (byte) 0xE1;  
	 public final static byte MESSAGE_READROUTER_ANSWER = (byte) 0xE2;  
	 public final static byte MESSAGE_READNODELIST_ANSWER = (byte) 0xE3;  
	 public final static byte MESSAGE_READWORKSTATE_ANSWER = (byte) 0xE4;  	
	 public final static byte MESSAGE_WSNRODE_REGIST = (byte) 0xE4;  		 
	 public final static byte MESSAGE_DATADELEVER_ANSWER = (byte) 0xE8;  
	 public final static byte MESSAGE_NOTHING_ANSWER = (byte) 0xE7;

	 
	 public final static byte MESSAGE_READDEVICEPARAM_ANSWER = (byte) 0x06;
	 
	 
	 
    //WSN cmd
	 public final static byte READNODE = (byte) 0xF1;  
	 public final static byte READROUTER = (byte) 0xF2;  
	 public final static byte READNODELIST = (byte) 0xF3;  
	 public final static byte SETWORKSTATE = (byte) 0xF4;  
	 public final static byte RESET = (byte) 0xF5;  
	 public final static byte CHANGROUTER = (byte) 0xF6;  
	 public final static byte DATADELEVER = (byte) 0xF8;  
	 public final static byte NOTHING = (byte) 0xF7;  
	 public final static byte SETSERIALPORT = (byte) 0xF9;  

	 public final static byte READNODE_ANSWER = (byte) 0xE1;  
	 public final static byte READROUTER_ANSWER = (byte) 0xE2;  
	 public final static byte READNODELIST_ANSWER = (byte) 0xE3;  
	 public final static byte READWORKSTATE_ANSWER = (byte) 0xE4;  	
	 public final static byte WSNRODE_REGIST = (byte) 0xE4;  		 
	 public final static byte DATADELEVER_ANSWER = (byte) 0xE8;  
	 public final static byte NOTHING_ANSWER = (byte) 0xE7;  
	 
	 //device cmd
	 public final static byte READDEVICE_PARAM_ANSWER = (byte) 0x06;  
	 public final static byte READWYY_STATE_ANSWER = (byte) 0x08;  
	 public final static byte READDEVICE_SAMPLE_ANSWER = (byte) 0x04;  
	 public final static byte READDEVICE_TIME_ANSWER = (byte) 0x02;  
	 public final static byte READCJY_HITORYDATA_ANSWER = (byte) 0x05;  
	 public final static byte READSGY_CROUVELINE_ANSWER = (byte) 0x0C;  
	 public final static byte READSGY_PUMPPARAM_ANSWER = (byte) 0x0B;  

	
	 public final static byte WSNNETOPERATE = 1;  
	 public final static byte NODESETOPERATE = 2;  
	 public static byte operate_mode = WSNNETOPERATE;
	 
	 
	 public static boolean loadingsample = false;
	 public static boolean cjytestsample = false;
	 public static boolean dblink = false;
	 public static boolean dblinkChange = false;
	 
	 //��������
	 public final static  int REQUEST_CONNECT_DEVICE = 1;
	 public final static  int REQUEST_ENABLE_BT = 2;
	 
	 public final static byte[] FINDLOCAL = {(byte) 0XFF,(byte) 0XFF,(byte) 0XF1,(byte) 0XC1,(byte) 0X84};
	 
	 public  static String datatarget ;
	 public  static String maindatatarget ;
	 
	 public  static String targetName ;
	 public  static int targetid ;
	 
	 public  static	Boolean firstframe = false;
	 public  static Boolean secondframe = false;
	 public  static Boolean thirdframe = false;
	 public  static Boolean fourthframe = false;
	 public  static Boolean fifthframe = false;
	 
	 public static HashMap<Integer,ArrayList<Byte>> OneSgyMap = new HashMap<Integer,ArrayList<Byte>>();
	 public static HashMap<Integer,ArrayList<Byte>> OneJLCurve = new HashMap<Integer,ArrayList<Byte>>();
	 
	 

	 public  static String MAPROOT_PATH = Environment.getExternalStorageDirectory() + "/�ܽ�����/"; 
	 public  static String MAP_PATH = Environment.getExternalStorageDirectory() + "/�ܽ�����/ʾ��ͼ/"; 
	 public  static String CRUVE_PATH = Environment.getExternalStorageDirectory() + "/�ܽ�����/����ͼ/"; 
	 
	 
	 
	public DataDefine() {
		value = 0;

	}
	public  enum TargetEmun {
		  manager, wsnnet, node, sgy, wyy, dly, cjy, debug;
	}
	public void update(byte[] puchMsg, int usDataLen) {

		int uIndex;
		// int i = 0;
		for (int i = 0; i < usDataLen; i++) {
			uIndex = (uchCRCHi ^ puchMsg[i]) & 0xff;

			uchCRCHi = (byte) (uchCRCLo ^ auchCRCHi[uIndex]);
			uchCRCLo = auchCRCLo[uIndex];
		}
		value = ((((int) uchCRCHi) << 8 | (((int) uchCRCLo) & 0xff))) & 0xffff;

		return;
	}

	public void reset() {
		value = 0;
		uchCRCHi = (byte) 0xff;
		uchCRCLo = (byte) 0xff;
	}

	public int getValue() {
		return value;
	}

	private static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
				.byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
				.byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	private static byte[] HexString2Buf(String src) {
		int len = src.length();
		byte[] ret = new byte[len / 2+2];
		byte[] tmp = src.getBytes();
		for (int i = 0; i < len; i += 2) {
			ret[i / 2] = uniteBytes(tmp[i], tmp[i + 1]);
		}
		return ret;
	}

	public static String toStringHex(String s) 
	{ 
		byte[] baKeyword = new byte[s.length()/2]; 
		for(int i = 0; i < baKeyword.length; i++) 
		{ 
			try 
			{ 
				baKeyword[i] = (byte)(0xff & Integer.parseInt(s.substring(i*2, i*2+2),16)); 
			} 
			catch(Exception e) 
			{ 
				e.printStackTrace(); 
			} 
		} 
		try 
		{ 
			s = new String(baKeyword, "utf-8");//UTF-16le:Not 
		} 
		catch (Exception e1) 
		{ 
			e1.printStackTrace(); 
		} 
		return s; 
	} 
	public static String getHexString(byte[] b) throws Exception {
		  String result = "";
		  for (int i=0; i < b.length; i++) {
		    result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 )+" ";
		  }
		  return result;
		}
	public static byte[] getSendBuf(String toSend){
		byte[] bb = HexString2Buf(toSend);
		DataDefine crc16 = new DataDefine();
		crc16.update(bb, bb.length-2);
		int ri = crc16.getValue();
		bb[bb.length-1]=(byte) (0xff & ri);
		bb[bb.length-2]=(byte) ((0xff00 & ri) >> 8);
		return bb;
	}
	public static byte[] getSendBuf(byte[] toSend){
		byte[] bb = new byte[toSend.length+2];
		for(int i = 0;i<toSend.length;i++)
			bb[i] = toSend[i];
		DataDefine crc16 = new DataDefine();
		crc16.update(bb, bb.length-2);
		int ri = crc16.getValue();
		bb[bb.length-1]=(byte) (0xff & ri);
		bb[bb.length-2]=(byte) ((0xff00 & ri) >> 8);
		return bb;
	}
	
	public static boolean checkBuf(byte[] bb){
		DataDefine crc16 = new DataDefine();
		crc16.update(bb, bb.length-2);
		int ri = crc16.getValue();
		if(bb[bb.length-1]==(byte)(ri&0xff) 
				&& bb[bb.length-2]==(byte) ((0xff00 & ri) >> 8))
			return true;
		return false;
	}
	public static String getBufHexStr(byte[] raw){
	    if ( raw == null ) {
		      return null;
		    }
		    final StringBuilder hex = new StringBuilder( 2 * raw.length );
		    for ( final byte b : raw ) {
		      hex.append(HEXES.charAt((b & 0xF0) >> 4))
		         .append(HEXES.charAt((b & 0x0F)));
		    }
		    return hex.toString();
	}

	public static byte[] getNodeParamCmd(int nid){
		byte[] send = new byte[3] ;
		send[0] = (byte) nid ; 
		send[1] = (byte)(nid >> 8);
		send[2] = (byte) 0xF1;
		return getSendBuf(send);
	}
	public static byte[] getNodeListCmd(int nid) {
		byte[] send = new byte[3] ;
		send[0] = (byte) nid ; 
		send[1] = (byte)(nid >> 8);
		send[2] = (byte) 0xF3;
		return getSendBuf(send);
	}

	public static byte[] getNodeDeviceParamCmd(int nid) {
		byte[] send = new byte[4] ;
		send[0] = (byte) nid ; 
		send[1] = (byte)(nid >> 8);
		send[2] = (byte) 0x06;
		send[3] = (byte) 0x01;
		return getSendBuf(send);
	}

	public static byte[] getNodeResetCmd(int nid) {
		byte[] send = new byte[3] ;
		send[0] = (byte) nid ; 
		send[1] = (byte)(nid >> 8);
		send[2] = (byte) 0xF5;
		return getSendBuf(send);
	}

	public static byte[] setNodeTypeCmd(int nid, byte b) {
		byte[] send = new byte[7] ;
		send[0] = (byte) nid ; 
		send[1] = (byte)(nid >> 8);
		send[2] = (byte)0xF4;
		send[3] = (byte)0;
		send[4] = (byte)0;
		send[5] = (byte)0;
		send[6] = b;
		return getSendBuf(send);
	}

	public static byte[] setNodeMainCHCmd(int nid, byte b) {
		byte[] send = new byte[6] ;
		send[0] = (byte) nid ; 
		send[1] = (byte)(nid >> 8);
		send[2] = (byte)0xF4;
		send[3] = (byte)0;
		send[4] = (byte)0;
		send[5] = b;
		return getSendBuf(send);
	}
	public static byte[] setNodeSearchCHCmd(int nid, byte b) {
		byte[] send = new byte[8] ;
		send[0] = (byte) nid ; 
		send[1] = (byte)(nid >> 8);
		send[2] = (byte)0xF4;
		send[3] = (byte)0;
		send[4] = (byte)0;
		send[5] = (byte)0;
		send[6] = (byte)0;
		send[7] = b;
		return getSendBuf(send);
	}
	public static byte[] setNodeListenCHCmd(int nid, byte b) {
		byte[] send = new byte[9] ;
		send[0] = (byte) nid ; 
		send[1] = (byte)(nid >> 8);
		send[2] = (byte)0xF4;
		send[3] = (byte)0;
		send[4] = (byte)0;
		send[5] = (byte)0;
		send[6] = (byte)0;
		send[7] = (byte)0xFF;
		send[8] = b;
		return getSendBuf(send);
	}

	public static byte[] getSampleDataCmd(int nid) {
		byte[] send = new byte[4] ;
		send[0] = (byte) nid ; 
		send[1] = (byte)(nid >> 8);
		send[2] = (byte)0x04;
		send[3] = (byte)01;
		return getSendBuf(send);

	}

	public static byte[] getTestSampleCmd(int nid) {
		byte[] send = new byte[5] ;
		send[0] = (byte) nid ; 
		send[1] = (byte)(nid >> 8);
		send[2] = (byte)0x04;
		send[3] = (byte)0x02;
		send[4] = (byte)0x01;
		return getSendBuf(send);
	}

	public static byte[] getReadTimeCmd(int thisnodeid) {
		byte[] cmdBuffer = new byte[4];
		cmdBuffer[0] =(byte)(thisnodeid & 0x00FF);       		
		cmdBuffer[1] = (byte)((thisnodeid & 0xFF00)>>8);
		cmdBuffer[2] = (byte)0x02;
		cmdBuffer[3] = (byte)0x01;
		return getSendBuf(cmdBuffer);
	}

	public static byte[] getSetTimeCmd(int thisnodeid) {
		 final Calendar mCalendar=Calendar.getInstance();
		 int mYear = mCalendar.get(Calendar.YEAR);			 
         int mMonth = mCalendar.get(Calendar.MONTH)+1; 
         int mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
         int mHour= mCalendar.get(Calendar.HOUR_OF_DAY);	              
         int mMinuts= mCalendar.get(Calendar.MINUTE);
         int mSenconds= mCalendar.get(Calendar.SECOND);
         
         byte[] cmdBuffer = new byte[12];
		cmdBuffer[0] =(byte)(thisnodeid & 0x00FF);       		
		cmdBuffer[1] = (byte)((thisnodeid & 0xFF00)>>8);
		cmdBuffer[2] = (byte)0x02;
		cmdBuffer[3] = (byte)0x02;
		cmdBuffer[4] = (byte) 0x20;
		cmdBuffer[5] = (byte) ((mYear-2000)/10*16+mYear%10);
		cmdBuffer[6] = (byte) (mMonth/10*16 +mMonth%10);
		cmdBuffer[7] = (byte) (mDay/10*16 +mDay%10);
		cmdBuffer[8] = (byte) 0;
		cmdBuffer[9] = (byte) (mHour/10*16 +mHour%10);
		cmdBuffer[10] = (byte) (mMinuts/10*16 +mMinuts%10);
		cmdBuffer[11] = (byte) (mSenconds/10*16 +mSenconds%10);
		return getSendBuf(cmdBuffer);
	}

	public static byte[] getCjyReadHistoryDataCmd(int thisnodeid, int numbe) {
		byte[] cmdBuffer = new byte[6];
		cmdBuffer[0] =(byte)(thisnodeid & 0x00FF);       		
		cmdBuffer[1] = (byte)((thisnodeid & 0xFF00)>>8);
		cmdBuffer[2] = (byte)0x05;
		cmdBuffer[3] = (byte)0x01;
		cmdBuffer[4] =(byte)(numbe & 0x00FF);       		
		cmdBuffer[5] = (byte)((numbe & 0xFF00)>>8);
		return getSendBuf(cmdBuffer);
	}

	public static byte[] getDeleteCjyHisDataCmd(int thisnodeid) {
		byte[] cmdBuffer = new byte[6];
		cmdBuffer[0] =(byte)(thisnodeid & 0x00FF);       		
		cmdBuffer[1] = (byte)((thisnodeid & 0xFF00)>>8);
		cmdBuffer[2] = (byte)0x05;
		cmdBuffer[3] = (byte)0x02;
		cmdBuffer[4] = (byte)0x5a;
		cmdBuffer[5] = (byte)0x5a;
		return getSendBuf(cmdBuffer);
	}

	public static byte[] getCjyRenameSensorCmd(int thisnodeid,int sensorid,String strvalue) {
		byte[] byteName = null;
		try {
			byteName = strvalue.getBytes("GBK");
		} catch (UnsupportedEncodingException e) {

		}
		if(byteName.length==2){
			byte[] cmdBuffer = new byte[8];
			cmdBuffer[0] =(byte)(thisnodeid & 0x00FF);       		
			cmdBuffer[1] = (byte)((thisnodeid & 0xFF00)>>8);
			cmdBuffer[2] = (byte)0x06;
			cmdBuffer[3] = (byte)0x04;
			cmdBuffer[4] =(byte)(sensorid & 0x00FF);       		
			cmdBuffer[5] = (byte)((sensorid & 0xFF00)>>8);
			cmdBuffer[6] = byteName[0];
			cmdBuffer[7] = byteName[1];
			return getSendBuf(cmdBuffer);
		}else return null;
	}

	public static byte[] getCjyRenameDeviceCmd(int thisnodeid, int sensorid,String strvalue) {
		byte[] byteName = null;
		try {
			byteName = strvalue.getBytes("GBK");
		} catch (UnsupportedEncodingException e) {

		}
		if(byteName.length>0){
			byte[] cmdBuffer = new byte[26];
			cmdBuffer[0] =(byte)(thisnodeid & 0x00FF);       		
			cmdBuffer[1] = (byte)((thisnodeid & 0xFF00)>>8);
			cmdBuffer[2] = (byte)0x06;
			cmdBuffer[3] = (byte)0x02;
			cmdBuffer[4] = (byte)0x01;
			cmdBuffer[5] = (byte)0x00;
			for(int jname = 0;jname<20;jname++)
				cmdBuffer[jname+6] = 0x20;
			for(int iname = 0;iname<byteName.length;iname++)
				cmdBuffer[iname+6] = byteName[iname];
			
	
			return getSendBuf(cmdBuffer);
		}else return null;
	}
	public static byte[] getSyRenameDeviceCmd(int thisnodeid, String strvalue) {
		byte[] byteName = null;
		try {
			byteName = strvalue.getBytes("GBK");
		} catch (UnsupportedEncodingException e) {

		}
		if(byteName.length>0){
			byte[] cmdBuffer = new byte[25];
			cmdBuffer[0] =(byte)(thisnodeid & 0x00FF);       		
			cmdBuffer[1] = (byte)((thisnodeid & 0xFF00)>>8);
			cmdBuffer[2] = (byte)0x06;
			cmdBuffer[3] = (byte)0x02;
			cmdBuffer[4] = (byte)0x05;
			for(int jname = 0;jname<20;jname++)
				cmdBuffer[jname+5] = 0x20;
			for(int iname = 0;iname<byteName.length;iname++)
				cmdBuffer[iname+5] = byteName[iname];
			return getSendBuf(cmdBuffer);
		}else return null;
	}
	public static byte[] getWyyRenameDeviceCmd(int thisnodeid, String strvalue) {
		byte[] byteName = null;
		try {
			byteName = strvalue.getBytes("GBK");
		} catch (UnsupportedEncodingException e) {

		}
		if(byteName.length>0){
			byte[] cmdBuffer = new byte[25];
			cmdBuffer[0] =(byte)(thisnodeid & 0x00FF);       		
			cmdBuffer[1] = (byte)((thisnodeid & 0xFF00)>>8);
			cmdBuffer[2] = (byte)0x06;
			cmdBuffer[3] = (byte)0x02;
			cmdBuffer[4] = (byte)0x05;
			for(int jname = 0;jname<20;jname++)
				cmdBuffer[jname+5] = 0x20;
			for(int iname = 0;iname<byteName.length;iname++)
				cmdBuffer[iname+5] = byteName[iname];
			return getSendBuf(cmdBuffer);
		}else return null;
	}
	public static byte[] getSetInterfaceCmd(int thisnodeid, int inteface) {
		byte[] cmdBuffer = new byte[8];
		cmdBuffer[0] = (byte)(thisnodeid & 0x00FF);       		
		cmdBuffer[1] = (byte)((thisnodeid & 0xFF00)>>8);
		cmdBuffer[2] = (byte)0x06;
		cmdBuffer[3] = (byte)0x02;
		cmdBuffer[4] = (byte)0x02;
		cmdBuffer[5] = (byte)0x00;
		cmdBuffer[6] = (byte)(inteface & 0x00FF); 
		cmdBuffer[7] = (byte)((inteface & 0xFF00)>>8);
		return getSendBuf(cmdBuffer);
	}

	public static byte[] getSgyStateCmd(int thisnodeid) {
		byte[] cmdBuffer = new byte[4];
		cmdBuffer[0] = (byte)(thisnodeid & 0x00FF);       		
		cmdBuffer[1] = (byte)((thisnodeid & 0xFF00)>>8);
		cmdBuffer[2] = (byte)0x04;
		cmdBuffer[3] = (byte)0x05;
		return getSendBuf(cmdBuffer);
	}

	public static byte[] getOpenSampleCmd(int thisnodeid) {
		byte[] cmdBuffer = new byte[5];
		cmdBuffer[0] = (byte)(thisnodeid & 0x00FF);       		
		cmdBuffer[1] = (byte)((thisnodeid & 0xFF00)>>8);
		cmdBuffer[2] = (byte)0x04;
		cmdBuffer[3] = (byte)0x01;
		cmdBuffer[4] = (byte)0xD8;
		return getSendBuf(cmdBuffer);
	}

	public static byte[] getCloseSampleCmd(int thisnodeid) {
		byte[] cmdBuffer = new byte[4];
		cmdBuffer[0] = (byte)(thisnodeid & 0x00FF);       		
		cmdBuffer[1] = (byte)((thisnodeid & 0xFF00)>>8);
		cmdBuffer[2] = (byte)0x04;
		cmdBuffer[3] = (byte)0x02;
		return getSendBuf(cmdBuffer);
	}

	public static byte[] getTestMapCmd(int thisnodeid) {
		byte[] cmdBuffer = new byte[4];
		cmdBuffer[0] = (byte)(thisnodeid & 0x00FF);       		
		cmdBuffer[1] = (byte)((thisnodeid & 0xFF00)>>8);
		cmdBuffer[2] = (byte)0x04;
		cmdBuffer[3] = (byte)0x03;
		return getSendBuf(cmdBuffer);
	}

	public static byte[] getReadLoadMapCmd(int thisnodeid, int numbs) {
		byte[] cmdBuffer = new byte[10];
		//cmdBuffer = new byte[10];
		cmdBuffer[0] = (byte)(thisnodeid & 0x00FF);       		
		cmdBuffer[1] = (byte)((thisnodeid & 0xFF00)>>8);
		cmdBuffer[2] = (byte)0x04;
		cmdBuffer[3] = (byte)0x04;
		cmdBuffer[4] = (byte)(numbs & 0x00FF); 
		cmdBuffer[5] = (byte)((numbs & 0xFF00)>>8);
		cmdBuffer[6] = (byte)0xA5;
		cmdBuffer[7] = (byte)0x5A;
		cmdBuffer[8] = (byte)0xB8;
		cmdBuffer[9] = (byte)0x00;
		return getSendBuf(cmdBuffer);
	}

	public static byte[] getReadCruveLineCmd(int thisnodeid, int numbs) {
		byte[] cmdBuffer = new byte[6];
		//cmdBuffer = new byte[10];
		cmdBuffer[0] = (byte)(thisnodeid & 0x00FF);       		
		cmdBuffer[1] = (byte)((thisnodeid & 0xFF00)>>8);
		cmdBuffer[2] = (byte)0x0C;
		cmdBuffer[3] = (byte)0x02;
		cmdBuffer[4] = (byte)(numbs & 0x00FF); 
		cmdBuffer[5] = (byte)((numbs & 0xFF00)>>8);
		return getSendBuf(cmdBuffer);
	}

	public static byte[] getSgyPumpCmd(int thisnodeid) {
		byte[] cmdBuffer = new byte[4];
		cmdBuffer[0] = (byte)(thisnodeid & 0x00FF);       		
		cmdBuffer[1] = (byte)((thisnodeid & 0xFF00)>>8);
		cmdBuffer[2] = (byte)0x0B;
		cmdBuffer[3] = (byte)0x01;
		return getSendBuf(cmdBuffer);
	}

	public static byte[] getSetSgyPumpCmd(int thisnodeid, int numbs) {
		byte[] cmdBuffer = new byte[7];
		//cmdBuffer = new byte[10];
		cmdBuffer[0] = (byte)(thisnodeid & 0x00FF);       		
		cmdBuffer[1] = (byte)((thisnodeid & 0xFF00)>>8);
		cmdBuffer[2] = (byte)0x0B;
		cmdBuffer[3] = (byte)0x02;
		cmdBuffer[4] = (byte)0x00;
		cmdBuffer[5] = (byte)(numbs & 0x00FF); 
		cmdBuffer[6] = (byte)((numbs & 0xFF00)>>8);
		return getSendBuf(cmdBuffer);
	}

	public static byte[] getReadClearLoadmapCmd(int thisnodeid) {
		byte[] cmdBuffer = new byte[7];
		//cmdBuffer = new byte[10];
		cmdBuffer[0] = (byte)(thisnodeid & 0x00FF);       		
		cmdBuffer[1] = (byte)((thisnodeid & 0xFF00)>>8);
		cmdBuffer[2] = (byte)0x04;
		cmdBuffer[3] = (byte)0x06;
		cmdBuffer[4] = (byte)0xA5;
		cmdBuffer[5] = (byte)0x5A;
		cmdBuffer[6] = (byte)0xAA;
		return getSendBuf(cmdBuffer);
	}

	public static byte[] getWyyStateCmd(int thisnodeid) {
		byte[] cmdBuffer = new byte[4];
		//cmdBuffer = new byte[10];
		cmdBuffer[0] = (byte)(thisnodeid & 0x00FF);       		
		cmdBuffer[1] = (byte)((thisnodeid & 0xFF00)>>8);
		cmdBuffer[2] = (byte)0x08;
		cmdBuffer[3] = (byte)0x03;
		return getSendBuf(cmdBuffer);
	}

	public static byte[] getReadRealDataOnceCmd(int thisnodeid) {
		byte[] cmdBuffer = new byte[5];
		//cmdBuffer = new byte[10];
		cmdBuffer[0] = (byte)(thisnodeid & 0x00FF);       		
		cmdBuffer[1] = (byte)((thisnodeid & 0xFF00)>>8);
		cmdBuffer[2] = (byte)0x04;
		cmdBuffer[3] = (byte)0x01;
		cmdBuffer[4] = (byte)0x01;
		return getSendBuf(cmdBuffer);
	}

	public static byte[] getOpenWyySampleCmd(int thisnodeid) {
		byte[] cmdBuffer = new byte[5];
		//cmdBuffer = new byte[10];
		cmdBuffer[0] = (byte)(thisnodeid & 0x00FF);       		
		cmdBuffer[1] = (byte)((thisnodeid & 0xFF00)>>8);
		cmdBuffer[2] = (byte)0x04;
		cmdBuffer[3] = (byte)0x01;
		cmdBuffer[4] = (byte)0x02;
		return getSendBuf(cmdBuffer);
	}

	public static byte[] getSetWyyInterfaceCmd(int thisnodeid, int numbs) {
		byte[] cmdBuffer = new byte[7];
		//cmdBuffer = new byte[10];
		cmdBuffer[0] = (byte)(thisnodeid & 0x00FF);       		
		cmdBuffer[1] = (byte)((thisnodeid & 0xFF00)>>8);
		cmdBuffer[2] = (byte)0x06;
		cmdBuffer[3] = (byte)0x02;
		cmdBuffer[4] = (byte)0x06;
		cmdBuffer[5] = (byte)(numbs & 0x00FF); 
		cmdBuffer[6] = (byte)((numbs & 0xFF00)>>8);
		return getSendBuf(cmdBuffer);
	}

	
}
