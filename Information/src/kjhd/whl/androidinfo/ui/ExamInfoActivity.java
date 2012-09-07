package kjhd.whl.androidinfo.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import kjhd.whl.R;

import kjhd.whl.androidinfo.json.JsonParse;
import kjhd.whl.androidinfo.util.CheckNetwork;
import kjhd.whl.androidinfo.util.MyWebService;
import kjhd.whl.androidinfo.util.SysApplication;
import android.R.color;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class ExamInfoActivity extends MapActivity implements OnClickListener{

	  private Button saveButton=null;
	  private Button intoButton=null;
	  private Button exitButton=null;
	  private TextView textView=null;
	  private ListView mListView=null;
	  private JsonParse mJsonParse;
	  private MyWebService myWebService;
	  private List<Map<String, String>> mlisMaps;
	  private Map<String, String> changeItemMap;
	  private String contentString="";
	  private String mExcuteId="";
	  private int type;
	  private final static int DATE_DIALOG = 0;    
	  private final static int TIME_DIALOG = 1;    
	  private Calendar c = null;
	  private String dateStr;
	  private String timeStr;
	  private String mModule_key;
	  private String addressStr="";
	  // �����ͼ��������� 
	  private BMapManager mapManager; 
	  // �������������� 
	  private MKSearch mMKSearch;
	  private int longitude=0;
	  private int latitude=0;
	  private Location location;
	  private LocationManager locationManager;
	  //ͨ��network��ȡlocation  
	  private String networkProvider = LocationManager.NETWORK_PROVIDER;  
	  //ͨ��gps��ȡlocation  
	  private String GpsProvider = LocationManager.GPS_PROVIDER;
	  
	  public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        SysApplication.getInstance().addActivity(this);
	        setContentView(R.layout.examinfo);
	        
	        Intent intent =getIntent();
	        Bundle bundle=intent.getExtras();
	        mExcuteId=bundle.getString("excuteid");
	        type=bundle.getInt("type");
	        myWebService=new MyWebService();
	        // ��ʼ��MapActivity 
	        mapManager = new BMapManager(getApplication()); 
	        // init�����ĵ�һ�����������������API Key 
	        mapManager.init("B71CB93622C19897E8C753D5A3A1096FECA16292", null); 
	        super.initMapActivity(mapManager); 
	        // ��ʼ��MKSearch 
	        mMKSearch = new MKSearch();
	        mMKSearch.init(mapManager, new MySearchListener());
	        
	        mListView=(ListView)findViewById(R.id.list_examinfo);
	        saveButton = (Button)findViewById(R.id.intoexam);
	        intoButton = (Button)findViewById(R.id.exam_intobut);
	        exitButton=(Button) findViewById(R.id.exam_top_back);
	        textView=(TextView) findViewById(R.id.top_title);
	        if (type==2) {
				textView.setText("С����Ϣ");
				intoButton.setText("�˳�");
				exitButton.setText("����");
			}
	        //�˰�ť�����أ�����
	        saveButton.setOnClickListener(this);
	        //�����ʾ������޸���Ϣ
	        intoButton.setOnClickListener(this);
	        //�˳��ͷ��ذ�ť
	        exitButton.setOnClickListener(this);
           new MyGetExamTask().execute(mExcuteId);
    }
	

		//�����������
		private void createLayout() {
			
            if(mlisMaps!=null){
			
			SimpleAdapter simpleAdapter=new SimpleAdapter(this, mlisMaps, R.layout.mymodular_item, new String[]{"title_name","module_key"}, new int[]{R.id.tev_mymodular_titlename,R.id.tev_mymodular_modulekey});
			mListView.setAdapter(simpleAdapter);
            }
			//mListView.setDivider(getResources().getDrawable(R.drawable.pdialog_img_divider));
			//mListView.setDividerHeight(3);
			//mListView.setCacheColorHint(Color.TRANSPARENT);
			Log.e("mexcutid3333333", mExcuteId);
			mListView.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {

					Map<String, String> mapType =(Map<String, String>) arg0.getItemAtPosition(arg2);
					
					if(mapType.get("title_type").equals("2")){
						
						getDialog(DATE_DIALOG,arg0,arg2).show();
					}else if (mapType.get("title_type").equals("3")) {
						getDialog(TIME_DIALOG, arg0, arg2).show();
					}
					
					else  {
						editContent(arg0,arg2);
					}
					
					
				}
			});

		} 
		
		
		//���˵���Ի��򷽷�
		public void editContent(final AdapterView<?> arg0,final int arg2){
			final EditText addText=new EditText(this);
			AlertDialog.Builder editBuilder=new AlertDialog.Builder(this);
			editBuilder.setTitle("�������޸�����:");
			editBuilder.setView(addText);
			editBuilder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					//��ӵĿ۷�ԭ������

			    if(!addText.getText().toString().equals("")){
		            contentString=addText.getText().toString();
		            ListView mList=(ListView) arg0;
					ListAdapter listAdapter=(ListAdapter) arg0.getAdapter();
					changeItemMap=(Map<String, String>) arg0.getItemAtPosition(arg2);
					changeItemMap.put("module_key", contentString);
					((SimpleAdapter)listAdapter).notifyDataSetChanged();
					Toast.makeText(getApplicationContext(), "�޸ĳɹ�!", Toast.LENGTH_SHORT).show();
	

			    }else {
			    	Toast.makeText(getApplicationContext(), "��û�����κ��޸�!!", Toast.LENGTH_SHORT).show();
				}
			    

			    
	 
			    dialog.dismiss();
					
				}
			});
			editBuilder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
		
					dialog.dismiss();
					Toast.makeText(getApplicationContext(), "��û�����κ��޸�!", Toast.LENGTH_SHORT).show();
				}
			});
			
			editBuilder.create().show();

		}
		
		//�˳�������ʾ
		public boolean onKeyDown(int keyCode, KeyEvent event) {

			
			if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
				dialog();
				
				return true;
			}
			return true;
		}
		
	
	    //�˳��¼������Ի���
		private void dialog() {
		
			AlertDialog.Builder builder=new Builder(this);
			builder.setMessage("ȷ��Ҫ�˳���");
			builder.setTitle("��ʾ");
			builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					SysApplication.getInstance().exit();				}
			});
			builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					
					dialog.dismiss();
					
				}
			});
			builder.create().show();
		}
		
		//У���Ƿ��б�������
		public boolean saveModular(){
			
			for (int i = 0; i < mlisMaps.size(); i++) {
				
				if (mlisMaps.get(i).get("title_null").equals("1")) {
					if (mlisMaps.get(i).get("module_key")==null||mlisMaps.get(i).get("module_key").equals("")) {
						return false;
		
					}
				}
			}
			return true;
		}
		
		 /**    
		  * �������ڼ�ʱ��ѡ��Ի���    
		  * */
		protected Dialog getDialog(int id,final AdapterView<?> arg0,final int arg2){
			Dialog mDialog=null;

			switch (id) {
			case DATE_DIALOG:
				c=Calendar.getInstance();
				mDialog=new DatePickerDialog(this,
						new DatePickerDialog.OnDateSetListener() {
							
							public void onDateSet(DatePicker view, int year, int monthOfYear,
									int dayOfMonth) {

								    dateStr=year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
								    ListView mList=(ListView) arg0;
									ListAdapter listAdapter=(ListAdapter) arg0.getAdapter();
									changeItemMap =(Map<String, String>) arg0.getItemAtPosition(arg2);
									changeItemMap.put("module_key", dateStr);
								    ((SimpleAdapter)listAdapter).notifyDataSetChanged();
									Toast.makeText(getApplicationContext(), "�޸ĳɹ�!", Toast.LENGTH_SHORT).show();
							}
						},
						c.get(Calendar.YEAR),
						c.get(Calendar.MONTH),
						c.get(Calendar.DAY_OF_MONTH));
				break;

			case TIME_DIALOG:
				c=Calendar.getInstance();
				mDialog=new TimePickerDialog(this,
						new TimePickerDialog.OnTimeSetListener() {
							
							public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        timeStr=String.valueOf(hourOfDay)+":"+String.valueOf(minute);
                                        ListView mList=(ListView) arg0;
    									ListAdapter listAdapter=(ListAdapter) arg0.getAdapter();
    									changeItemMap=(Map<String, String>) arg0.getItemAtPosition(arg2);
    									changeItemMap.put("module_key", timeStr);
    								    ((SimpleAdapter)listAdapter).notifyDataSetChanged();
    									Toast.makeText(getApplicationContext(), "�޸ĳɹ�!", Toast.LENGTH_SHORT).show();
								
							}
						}, 
						c.get(Calendar.HOUR_OF_DAY), 
						c.get(Calendar.MINUTE),
						false);
	
				break;
			}
			return mDialog;
			
		}
		
		//���listview����
		private class MyGetExamTask extends AsyncTask<String, Integer, String>{

			@Override
			protected String doInBackground(String... params) {
                if (!params[0].equals("")) {
                	mJsonParse=new JsonParse();
        			Log.e("mexcutid", mExcuteId);
        			try {
						mlisMaps=mJsonParse.parseModular(type,mExcuteId);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        			Log.e("mexcutid222222", mExcuteId);
					return "succ";
				}
				return "false";
			}
			@Override
			protected void onPostExecute(String result) {
                if (result.equals("succ")) {
                	
						createLayout();
				
				}else {
					Toast.makeText(getApplicationContext(), "��ȡʧ��", Toast.LENGTH_SHORT).show();
				}
			}
		}
	
		public void getAddress(){
	    	
            //��ȡ��γ��
            locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
            //���ȼ�� ͨ��network �ܷ���location����  
            //��������location���� �����tv  
            if (startLocation(networkProvider)) {  
                updateLocation(location);  
            }else   
                //ͨ��gps �ܷ���location����  
                //��������location���� �����tv  
                if(startLocation(GpsProvider)){  
                updateLocation(location);  
            }else{  
                //����������ַ��������ܻ��location���� ����ʾ������Ϣ  
                Toast.makeText(this, "û�д�GPS�豸", 5000).show();  
            }  

	    
}


/** 
* ͨ������ ��ȡLocation���� 
* ���Location����Ϊ�� �򷵻� true ���Ҹ�ֵ��ȫ�ֱ��� location 
*   ���Ϊ�� ����false ����ֵ��ȫ�ֱ���location 
*  
* @param provider 
* @param mContext 
* @return 
*/  
       private boolean startLocation(String provider){  
           Location location = locationManager.getLastKnownLocation(provider);  
  
       // λ�ü�����  
           locationManager.requestLocationUpdates(provider, 500, 0, new MylocationListener());
       //���Location����Ϊ�� �򷵻� true ���Ҹ�ֵ��ȫ�ֱ��� location  
       //���Ϊ�� ����false ����ֵ��ȫ�ֱ���location  
      if (location!= null) {  
           this.location=location;  
           return true;  
          }  
      return false; 

       }    


// ����λ����Ϣ չʾ��tv��  
public void updateLocation(Location location) {  
if (location != null) {  
	longitude=(int) (1000000 * location.getLongitude());
     latitude=(int) (1000000 * location.getLatitude());
     Log.e("out5", String.valueOf(longitude));
     Log.e("out6", String.valueOf(latitude));
     
     //��ѯ�þ�γ��ֵ����Ӧ�ĵ�ַλ����Ϣ 
     mMKSearch.reverseGeocode(new GeoPoint(latitude, longitude));
}
}    
		
		
		protected boolean isRouteDisplayed() {
			// TODO Auto-generated method stub
			return false;
		}

		 @Override
		    protected void onDestroy() { 
		        if (mapManager != null) { 
		            // �����˳�ǰ����ô˷��� 
		            mapManager.destroy(); 
		            mapManager = null; 
		        } 
		        super.onDestroy(); 
		    } 
		  
		    @Override
		    protected void onPause() { 
		        if (mapManager != null) { 
		            // ��ֹ�ٶȵ�ͼ API 
		            mapManager.stop(); 
		        } 
		        super.onPause(); 
		    } 
		  
		    @Override
		    protected void onResume() { 
		        if (mapManager != null) { 
		            // �����ٶȵ�ͼ API 
		            mapManager.start(); 
		        } 
		        super.onResume(); 
		    } 
		  
		    /** 
		     * �ڲ���ʵ��MKSearchListener�ӿ�,����ʵ���첽�������� 
		     *  
		     * @author liufeng 
		     */
		    public class MySearchListener implements MKSearchListener { 
		        /** 
		         * ���ݾ�γ��������ַ��Ϣ��� 
		         *  
		         * @param result 
		         *            ������� 
		         * @param iError 
		         *            ����ţ�0��ʾ��ȷ���أ� 
		         */
		        public void onGetAddrResult(MKAddrInfo result, int iError) { 
		            if (result == null) { 
		                return; 
		            } 
		            StringBuffer sb = new StringBuffer(); 
		            // ��γ������Ӧ��λ�� 
		            sb.append(result.strAddr); 
		  
		            // �жϸõ�ַ�����Ƿ���POI��Point of Interest,����Ȥ�㣩 
		        
		            // ����ַ��Ϣ����Ȥ����Ϣ��ʾ��TextView�� 
		            addressStr=sb.toString();
		            Log.e("address", addressStr);
		        } 
		  
		        /** 
		         * �ݳ�·��������� 
		         *  
		         * @param result 
		         *            ������� 
		         * @param iError 
		         *            ����ţ�0��ʾ��ȷ���أ� 
		         */
		        public void onGetDrivingRouteResult(MKDrivingRouteResult result, 
		                int iError) { 
		        } 
		  
		        /** 
		         * POI�����������Χ����������POI�������ܱ߼����� 
		         *  
		         * @param result 
		         *            ������� 
		         * @param type 
		         *            ���ؽ�����ͣ�11,12,21:poi�б� 7:�����б� 
		         * @param iError 
		         *            ����ţ�0��ʾ��ȷ���أ� 
		         */
		        public void onGetPoiResult(MKPoiResult result, int type, int iError) { 
		        } 
		  
		        /** 
		         * ��������·��������� 
		         *  
		         * @param result 
		         *            ������� 
		         * @param iError 
		         *            ����ţ�0��ʾ��ȷ���أ� 
		         */
		        public void onGetTransitRouteResult(MKTransitRouteResult result, 
		                int iError) { 
		        } 
		  
		        /** 
		         * ����·��������� 
		         *  
		         * @param result 
		         *            ������� 
		         * @param iError 
		         *            ����ţ�0��ʾ��ȷ���أ� 
		         */
		        public void onGetWalkingRouteResult(MKWalkingRouteResult result, 
		                int iError) { 
		        }

				public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}

				public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
					// TODO Auto-generated method stub
					
				} 
		    } 
		    
		    //location�¼�������
		    class MylocationListener implements LocationListener{

				public void onLocationChanged(Location location) {
					updateLocation(location); 
					
				}

				public void onProviderDisabled(String provider) {
					// TODO Auto-generated method stub
					
				}

				public void onProviderEnabled(String provider) {
					// TODO Auto-generated method stub
					
				}

				public void onStatusChanged(String provider, int status,
						Bundle extras) {
					// TODO Auto-generated method stub
					
				}
		    	
		    }
            //��ť�¼���������
			public void onClick(View v) {

				switch (v.getId()) {
				case R.id.intoexam:
					boolean flag=CheckNetwork.CheckNetworkState(ExamInfoActivity.this);
					if(flag==false){
						Toast.makeText(getApplicationContext(), "��ǰ�޿�������", Toast.LENGTH_SHORT).show();
						
					}
					break;

                case R.id.exam_intobut:
                	if(type==1){
                		
 					      if(saveModular()){
 						     Log.e("flag", mExcuteId);
 						     try {
 							       myWebService.saveQtmk(mlisMaps,mExcuteId);
 						      } catch (Exception e) {
 							       e.printStackTrace();
 						      }
 					               Intent intent = new Intent();
 					               intent.setClass(ExamInfoActivity.this, AnswerActivity.class);
 					               intent.putExtra("excuteid", mExcuteId);
 					               startActivityForResult(intent, 1);
 					         }
 					}else {
 					SysApplication.getInstance().exit();
 					    }
					break;
					
                case R.id.exam_top_back:
                	if (type==1) {
						dialog();
					}else {
						Intent intent=new Intent();
						setResult(4,intent);
						finish();
					}
					break;
				}
				
			}
		    
	
}