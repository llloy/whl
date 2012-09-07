package kjhd.whl.androidinfo.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.MKAddrInfo;
import com.baidu.mapapi.MKBusLineResult;
import com.baidu.mapapi.MKDrivingRouteResult;
import com.baidu.mapapi.MKPoiResult;
import com.baidu.mapapi.MKSearch;
import com.baidu.mapapi.MKSearchListener;
import com.baidu.mapapi.MKSuggestionResult;
import com.baidu.mapapi.MKTransitRouteResult;
import com.baidu.mapapi.MKWalkingRouteResult;
import com.baidu.mapapi.MapActivity;
import kjhd.whl.InformationActivity;
import kjhd.whl.R;
import kjhd.whl.androidinfo.util.CheckNetwork;
import kjhd.whl.androidinfo.util.CommonSetting;
import kjhd.whl.androidinfo.util.CustomerHttpClient;
import kjhd.whl.androidinfo.util.MyWebService;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity{
	

	private EditText excuteId;
	private Button loginBut;
	private ProgressDialog mDialog;
    private MyWebService webService;
	private String excuteString="";
	private String resultStr;
    
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
           
      
        excuteId=(EditText)findViewById(R.id.excuteid);
        loginBut=(Button)findViewById(R.id.loginbut);
        if(CheckNetwork.CheckNetworkState(this)==false)
        showTips();
        webService = new MyWebService();
        //��¼
        loginBut.setOnClickListener(new Button.OnClickListener()
        {

            public void onClick(View arg0) {
            	if("".equals(excuteId.getText().toString())){
                
            		Toast.makeText(getApplicationContext(), "ִ�б��벻��Ϊ�գ�", Toast.LENGTH_SHORT).show();
                }else{
                	
                	
                    excuteString=excuteId.getText().toString();
                    new MyLoginTask().execute(excuteString);
                }

            }
            
        });

        
	
	}
	
	
    
    
    
    private void showTips()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("û�п�������");
        builder.setMessage("��ǰ���粻���ã��Ƿ��������磿");
        builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
            
            public void onClick(DialogInterface dialog, int which) {
                // ���û���������ӣ�������������ý���
                startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            }
        });
        builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
            
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                LoginActivity.this.finish();
            }
        });
        builder.create();
        builder.show();
    }


 
	   //��¼��������
	   private class MyLoginTask extends AsyncTask<String, Integer, String>{

			@Override
			protected void onPreExecute() {
				mDialog = new ProgressDialog(LoginActivity.this);
	            mDialog.setTitle("��½");
	            mDialog.setMessage("���ڵ�½�����������Ժ�...");
	           
	            mDialog.show();
				
			};
			protected String doInBackground(String... params) {
                resultStr=webService.ValidateExcuteId(params[0]);
                if(resultStr!=null&&!resultStr.equals("")){             
                       return resultStr;
                    } 
                   
				return "error";
			}
	    	@Override
	    	protected void onPostExecute(String result) {
                if (result.equals("succ")) {
                	 mDialog.cancel();
           		     Toast.makeText(getApplicationContext(), "��¼�ɹ���", Toast.LENGTH_SHORT).show();
                     Intent intent = new Intent();
                     intent.putExtra("excuteid", excuteString);
                     intent.putExtra("type", 1);
                     intent.setClass(LoginActivity.this, ExamInfoActivity.class);
                     startActivity(intent);
                     finish();   
				}else if(result.equals("error")) {
					  mDialog.cancel();
	                  Toast.makeText(getApplicationContext(), "��¼������ʧ�ܣ��������磡", Toast.LENGTH_SHORT).show();
				}else {
					  mDialog.cancel();
	                  Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
				}
	    	}
	    }
}
