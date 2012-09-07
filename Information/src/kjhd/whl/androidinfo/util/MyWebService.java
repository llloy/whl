package kjhd.whl.androidinfo.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import kjhd.whl.androidinfo.enity.MyAnswerEntity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.R.integer;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;


public class MyWebService {
	
	private static final String NAMESPACE = "http://192.168.1.116:8088/InfoResearch/services/WebService/";

	// WebService��ַ
	private static String URL = "http://192.168.1.116:8088/InfoResearch/services/WebService";




    
	public MyWebService(){
		netWebservice();
	}

	



	/**
	 * ��֤¼����Աid������id��ִ�б���
	 * @param id	¼����Աid
	 * @param dd	����id
	 * @param excuteId	ִ�б���
	 * @return �����ַ���
	 */

	
	public String ValidateExcuteId(String excuteid) {
		

			
			String METHOD_NAME = "ValidateExcuteId";
			String SOAP_ACTION = NAMESPACE+METHOD_NAME;
			SoapObject rpc = new SoapObject(NAMESPACE, METHOD_NAME);


			rpc.addProperty("excuteId", excuteid);

			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.bodyOut = rpc;
			envelope.dotNet = true;
			envelope.setOutputSoapObject(rpc);
			
			HttpTransportSE ht = new HttpTransportSE(URL);

			//AndroidHttpTransport ht = new AndroidHttpTransport(URL);
			ht.debug = true;

			try {
				ht.call(SOAP_ACTION, envelope);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//ht.call(null, envelope);

			//SoapObject result = (SoapObject)envelope.bodyIn;
			//detail = (SoapObject) result.getProperty("getWeatherbyCityNameResult");

			SoapObject sObject =(SoapObject)envelope.bodyIn;

			if(sObject!=null){
				return sObject.getProperty(0).toString();
			}else {
				 return null;
			}
			
	}
	
	/**
	 * ����ִ�б����ѯģ����Ϣ
	 * @param type	ģ������ 1������ģ��2��С��ģ��3������ģ��
	 * @param excuteId	ִ�б���
	 * @return	����json ��ʽ���ַ���
	 */

	
	public String findJcmk(int type,String excuteid) {

		String METHOD_NAME = "findJcmk";
		String SOAP_ACTION = NAMESPACE+METHOD_NAME;
		SoapObject rpc = new SoapObject(NAMESPACE, METHOD_NAME);

		rpc.addProperty("type", type);
		rpc.addProperty("excuteId", excuteid);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.bodyOut = rpc;
		envelope.dotNet = true;
		envelope.setOutputSoapObject(rpc);
		HttpTransportSE ht = new HttpTransportSE(URL);
		ht.debug = true;

		try {
			ht.call(SOAP_ACTION, envelope);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
        
		SoapObject sObject =(SoapObject)envelope.bodyIn;
		if(sObject!=null){
			return sObject.getProperty(0).toString();
		}
		

	    return null;
	}
	
	
	/**
	 * ����ִ�б����ѯ�ʾ�id
	 * @param excuteId
	 * @return
	 */
    public int getQuestionId(String excuteId){
    	
    	return 0;
    }
    /**
	 * ����ִ�б����ѯ��Ŀ
	 */
	public String findTm(String excuteid){

		String METHOD_NAME = "findTm";
		String SOAP_ACTION = NAMESPACE+METHOD_NAME;
		SoapObject rpc = new SoapObject(NAMESPACE, METHOD_NAME);



		rpc.addProperty("excuteId", excuteid);

		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.bodyOut = rpc;
		envelope.dotNet = true;
		envelope.setOutputSoapObject(rpc);
		
		HttpTransportSE ht = new HttpTransportSE(URL);

		//AndroidHttpTransport ht = new AndroidHttpTransport(URL);
		ht.debug = true;

		try {
			ht.call(SOAP_ACTION, envelope);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//ht.call(null, envelope);

		//SoapObject result = (SoapObject)envelope.bodyIn;
		//detail = (SoapObject) result.getProperty("getWeatherbyCityNameResult");

		SoapObject sObject =(SoapObject)envelope.bodyIn;
		String result=sObject.getProperty(0).toString();
		
		Log.e("mywebservice4", result);

	    return result;
	}

	/**
	 * ��������ģ����Ϣ
	 * @param type	ģ������
	 * @param json {excuteId:'', questionId:1, jcmks:{}, titleIds:{},type:1}
	 * return succ�ɹ� failʧ��
	 */
	public String saveQtmk(List<Map<String, String>> json,String extuteid) throws Exception{

		    JSONObject mSendObject=new JSONObject();
		    mSendObject.put("excuteId", extuteid);
			mSendObject.put("questionId", json.get(0).get("question_id"));
			mSendObject.put("type", json.get(0).get("type"));
			JSONArray aJsonArray=new JSONArray();
				for (int j = 0; j < json.size(); j++) {
					JSONObject mjObject=new JSONObject();
					//mjObject.put("moduleKeys", "module_key����");
					aJsonArray.put(json.get(j).get("module_key"));
				}

		    mSendObject.put("moduleKeys",aJsonArray);
			JSONArray bJsonArray=new JSONArray();
				for (int k = 0; k < json.size(); k++) {
					JSONObject njObject=new JSONObject();
					//njObject.put("title_id", String.valueOf(i));
					bJsonArray.put(json.get(k).get("title_id"));
				}
		
			mSendObject.put("titleIds", bJsonArray);
				
				
				Log.e("mywebservicesaveQtmk", mSendObject.toString());

			   
				String METHOD_NAME = "saveQtmk";
				String SOAP_ACTION =NAMESPACE+METHOD_NAME;
				SoapObject rpc = new SoapObject(NAMESPACE, METHOD_NAME);



				rpc.addProperty("json", mSendObject.toString());

				
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.bodyOut = rpc;
				envelope.dotNet = true;
				envelope.setOutputSoapObject(rpc);
				
				HttpTransportSE ht = new HttpTransportSE(URL);

				//AndroidHttpTransport ht = new AndroidHttpTransport(URL);
				ht.debug = true;

				try {
					ht.call(SOAP_ACTION, envelope);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//ht.call(null, envelope);

				//SoapObject result = (SoapObject)envelope.bodyIn;
				//detail = (SoapObject) result.getProperty("getWeatherbyCityNameResult");

				SoapObject sObject =(SoapObject)envelope.bodyIn;
				String result=sObject.getProperty(0).toString();
				
				Log.e("saveqtmkresult", result);
			
			return null;
			

	}
	/**
	 * ������Ŀ
	 * json {"excuteId":"","questionId":1,"indexId":1,"idPr":'',"idXh":1,"df":0.5,"kfyy":'',"imgUrl":''}
	 */
	public void saveTm(List<MyAnswerEntity> mlist){
		
		try {
			
			    for (MyAnswerEntity answerEntity: mlist) {
			    	JSONObject  mSendJsonObject=new JSONObject();
					mSendJsonObject.put("excuteId", answerEntity.getExcuteId());
					mSendJsonObject.put("questionId", answerEntity.getQuestionId());
					mSendJsonObject.put("indexId", answerEntity.getIndexId());
					mSendJsonObject.put("idPr", answerEntity.getIdPr());
					mSendJsonObject.put("idXh", answerEntity.getIdXh());
					mSendJsonObject.put("df", answerEntity.getValue());
					mSendJsonObject.put("kfyy", answerEntity.getReason());
					mSendJsonObject.put("imgUrl", answerEntity.getImgUrl());
					mSendJsonObject.put("selectKey", answerEntity.getSelectKey());
					mSendJsonObject.put("titleType", answerEntity.getTitle_type());
					String mjsonStr=mSendJsonObject.toString();
					DebugUtil.debug("savetm"+ "------"+mjsonStr);
					
					String METHOD_NAME = "saveTm";
					String SOAP_ACTION =NAMESPACE+METHOD_NAME;
					SoapObject rpc = new SoapObject(NAMESPACE, METHOD_NAME);



					rpc.addProperty("json", mjsonStr);

					
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.bodyOut = rpc;
					envelope.dotNet = true;
					envelope.setOutputSoapObject(rpc);
					
					HttpTransportSE ht = new HttpTransportSE(URL);

					//AndroidHttpTransport ht = new AndroidHttpTransport(URL);
					ht.debug = true;

					try {
						ht.call(SOAP_ACTION, envelope);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (XmlPullParserException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//ht.call(null, envelope);

					//SoapObject result = (SoapObject)envelope.bodyIn;
					//detail = (SoapObject) result.getProperty("getWeatherbyCityNameResult");

					SoapObject sObject =(SoapObject)envelope.bodyIn;
					String result=sObject.getProperty(0).toString();
					
					DebugUtil.debug("backresult"+"-----"+result);
				}
				
				
			
			
			
		} catch (Exception e) {
		}
		
		
		}
		
	
	/**
	 * ����ִ�б������Ϣ
	 * json  {"ExcuteId":'',"enterId":1,"ddId":1}
	 */
	public void saveQuestionKey(String excuteid,String address){
		try {
			JSONObject loginObject=new JSONObject();
			loginObject.put("ExcuteId", excuteid);
			loginObject.put("enterAddress", address);
			String loginInfoStr=loginObject.toString();
			String METHOD_NAME = "saveQuestionKey";
			String SOAP_ACTION =NAMESPACE+METHOD_NAME;
			SoapObject rpc = new SoapObject(NAMESPACE, METHOD_NAME);



			rpc.addProperty("json", loginInfoStr);

			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.bodyOut = rpc;
			envelope.dotNet = true;
			envelope.setOutputSoapObject(rpc);
			
			HttpTransportSE ht = new HttpTransportSE(URL);

			//AndroidHttpTransport ht = new AndroidHttpTransport(URL);
			ht.debug = true;

			try {
				ht.call(SOAP_ACTION, envelope);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//ht.call(null, envelope);

			//SoapObject result = (SoapObject)envelope.bodyIn;
			//detail = (SoapObject) result.getProperty("getWeatherbyCityNameResult");

			SoapObject sObject =(SoapObject)envelope.bodyIn;
			String result=sObject.getProperty(0).toString();
			
			Log.e("result2", result);
			
		} catch (Exception e) {

		}
		
		

	}
    //������������ڵ���webservice֮ǰ����O�ˡ�����
	public void netWebservice(){
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		.detectDiskReads()
		.detectDiskWrites()
		.detectNetwork()
		.penaltyLog() 
		.build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
		.detectLeakedSqlLiteObjects()
		.detectLeakedSqlLiteObjects()
		.penaltyLog()
		.penaltyDeath()
		.build());
	}
}
