package kjhd.whl.androidinfo.json;

/**
 * 解析问卷
 * @author whl (81813780@qq.com)
 * @data 2012-07-10
 * @version 1.1
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import com.baidu.mapapi.Mj;

import android.R.integer;
import android.app.AliasActivity;
import android.database.CursorJoiner.Result;
import android.util.Log;

import kjhd.whl.androidinfo.enity.AnswerItemEntity;
import kjhd.whl.androidinfo.enity.MyModularEntity;

import kjhd.whl.androidinfo.enity.MyAnswerEntity;
import kjhd.whl.androidinfo.util.CommonSetting;
import kjhd.whl.androidinfo.util.CustomerHttpClient;
import kjhd.whl.androidinfo.util.MyWebService;

public class JsonParse {
	
	private MyWebService mWebsService;
	private String resultAnswerStr;
	private String modularStr;
	private List<MyAnswerEntity> mlistAnswer;
	private MyAnswerEntity mAnswerentity;
	private List<AnswerItemEntity> mListAnswerItem;
	private AnswerItemEntity mAnswerItemEntity;
	private String[] answerResult;
	private List<Map<String, String>> mModularList;
	Map<String, String> mlistMap;
	
	public JsonParse(){
		 
		 mWebsService=new MyWebService();
	 }
	
	//题目模块解析方法，尚未完善。
	
	public List<MyAnswerEntity> parseAnswerInfo(String excuteid) throws Exception{
		
		resultAnswerStr=mWebsService.findTm(excuteid);
		Log.e("android4", resultAnswerStr);
		JSONArray jArray=new JSONArray(resultAnswerStr);
		mlistAnswer=new ArrayList<MyAnswerEntity>();
		   for (int i = 0; i < jArray.length(); i++) {
			mAnswerentity=new MyAnswerEntity();
			JSONObject mJobject=jArray.getJSONObject(i);
			mAnswerentity.setImgUrl(mJobject.getString("img_url"));
			mAnswerentity.setTitle(mJobject.getString("title"));
			mAnswerentity.setRequest(mJobject.getString("request"));
			mAnswerentity.setPfgz(mJobject.getString("pfgz"));
			mAnswerentity.setSelect_type(mJobject.getInt("select_type"));
			mAnswerentity.setTm_value(mJobject.getDouble("tm_value"));
			mAnswerentity.setIndexId(mJobject.getInt("index_id"));
			mAnswerentity.setIdXh(mJobject.getInt("id_xh"));
			mAnswerentity.setQuestionId(mJobject.getInt("question_id"));
			mAnswerentity.setValue(mJobject.getDouble("value"));
			mAnswerentity.setReason(mJobject.getString("reason"));
			mAnswerentity.setIdPr(mJobject.getString("id_pr"));
			mAnswerentity.setZxbz(mJobject.getString("zxbz"));
			mAnswerentity.setJtsm(mJobject.getString("jtsm"));
			mAnswerentity.setBz(mJobject.getString("bz"));
			mAnswerentity.setTm_key(mJobject.getString("tm_key"));
			mAnswerentity.setJump(mJobject.getInt("jump"));
			mAnswerentity.setJump_key(mJobject.getString("jump_key"));
			mAnswerentity.setJump_testid(mJobject.getString("jump_testid"));
			mAnswerentity.setTitle_type(mJobject.getInt("title_type"));
			mAnswerentity.setTest_null(mJobject.getInt("test_null"));
			mAnswerentity.setSelectKey(mJobject.getString("select_key"));
			mAnswerentity.setExcuteId(mJobject.getString("excute_id"));
			
			answerResult=mJobject.getString("key_select").split("；");

			mListAnswerItem =new ArrayList<AnswerItemEntity>();
			for (int j = 0; j < answerResult.length; j++) {
				mAnswerItemEntity = new AnswerItemEntity();
				mAnswerItemEntity.setAnswercontent(answerResult[j]);
				mListAnswerItem.add(mAnswerItemEntity);
				mAnswerentity.setAnswersitem(mListAnswerItem);
				
			}
			mlistAnswer.add(mAnswerentity);
			
		}
		return mlistAnswer;
	}
	
	


	//基础模块信息封装
	
	public List<Map<String, String>> parseModular(int type,String excuteId) throws Exception{
		
		modularStr=mWebsService.findJcmk(type, excuteId);
        Log.e("modular", modularStr);
		JSONArray moJsonArray=new JSONArray(modularStr);
		mModularList=new ArrayList<Map<String,String>>();
		for (int i = 0; i < moJsonArray.length(); i++) {
			mlistMap=new HashMap<String, String>();
			JSONObject jObject=moJsonArray.getJSONObject(i);
			mlistMap.put("title_name", jObject.getString("title_name"));
			mlistMap.put("module_key", jObject.getString("module_key"));
			mlistMap.put("title_type", String.valueOf(jObject.getInt("title_type")));
			mlistMap.put("title_id", jObject.getString("title_id"));
			mlistMap.put("question_id", String.valueOf(jObject.getInt("question_id")));
		    mlistMap.put("type", String.valueOf(jObject.getInt("type")));
		    mlistMap.put("title_null", String.valueOf(jObject.getInt("title_null")));

			mlistMap.put("title_description", "edittext测试"+i);
			mModularList.add(mlistMap);
			
		}
		
		return mModularList;
	}	
	

}
