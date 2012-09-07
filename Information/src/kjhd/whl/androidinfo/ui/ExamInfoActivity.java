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
	  // 定义地图引擎管理类 
	  private BMapManager mapManager; 
	  // 定义搜索服务类 
	  private MKSearch mMKSearch;
	  private int longitude=0;
	  private int latitude=0;
	  private Location location;
	  private LocationManager locationManager;
	  //通过network获取location  
	  private String networkProvider = LocationManager.NETWORK_PROVIDER;  
	  //通过gps获取location  
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
	        // 初始化MapActivity 
	        mapManager = new BMapManager(getApplication()); 
	        // init方法的第一个参数需填入申请的API Key 
	        mapManager.init("B71CB93622C19897E8C753D5A3A1096FECA16292", null); 
	        super.initMapActivity(mapManager); 
	        // 初始化MKSearch 
	        mMKSearch = new MKSearch();
	        mMKSearch.init(mapManager, new MySearchListener());
	        
	        mListView=(ListView)findViewById(R.id.list_examinfo);
	        saveButton = (Button)findViewById(R.id.intoexam);
	        intoButton = (Button)findViewById(R.id.exam_intobut);
	        exitButton=(Button) findViewById(R.id.exam_top_back);
	        textView=(TextView) findViewById(R.id.top_title);
	        if (type==2) {
				textView.setText("小结信息");
				intoButton.setText("退出");
				exitButton.setText("返回");
			}
	        //此按钮被隐藏，无用
	        saveButton.setOnClickListener(this);
	        //进入问卷并保存修改信息
	        intoButton.setOnClickListener(this);
	        //退出和返回按钮
	        exitButton.setOnClickListener(this);
           new MyGetExamTask().execute(mExcuteId);
    }
	

		//创建网点界面
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
		
		
		//添加说明对话框方法
		public void editContent(final AdapterView<?> arg0,final int arg2){
			final EditText addText=new EditText(this);
			AlertDialog.Builder editBuilder=new AlertDialog.Builder(this);
			editBuilder.setTitle("请输入修改内容:");
			editBuilder.setView(addText);
			editBuilder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					//添加的扣分原因内容

			    if(!addText.getText().toString().equals("")){
		            contentString=addText.getText().toString();
		            ListView mList=(ListView) arg0;
					ListAdapter listAdapter=(ListAdapter) arg0.getAdapter();
					changeItemMap=(Map<String, String>) arg0.getItemAtPosition(arg2);
					changeItemMap.put("module_key", contentString);
					((SimpleAdapter)listAdapter).notifyDataSetChanged();
					Toast.makeText(getApplicationContext(), "修改成功!", Toast.LENGTH_SHORT).show();
	

			    }else {
			    	Toast.makeText(getApplicationContext(), "你没有做任何修改!!", Toast.LENGTH_SHORT).show();
				}
			    

			    
	 
			    dialog.dismiss();
					
				}
			});
			editBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
		
					dialog.dismiss();
					Toast.makeText(getApplicationContext(), "你没有做任何修改!", Toast.LENGTH_SHORT).show();
				}
			});
			
			editBuilder.create().show();

		}
		
		//退出程序提示
		public boolean onKeyDown(int keyCode, KeyEvent event) {

			
			if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
				dialog();
				
				return true;
			}
			return true;
		}
		
	
	    //退出事件监听对话框
		private void dialog() {
		
			AlertDialog.Builder builder=new Builder(this);
			builder.setMessage("确定要退出吗？");
			builder.setTitle("提示");
			builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					SysApplication.getInstance().exit();				}
			});
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					
					dialog.dismiss();
					
				}
			});
			builder.create().show();
		}
		
		//校验是否有必填内容
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
		  * 创建日期及时间选择对话框    
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
									Toast.makeText(getApplicationContext(), "修改成功!", Toast.LENGTH_SHORT).show();
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
    									Toast.makeText(getApplicationContext(), "修改成功!", Toast.LENGTH_SHORT).show();
								
							}
						}, 
						c.get(Calendar.HOUR_OF_DAY), 
						c.get(Calendar.MINUTE),
						false);
	
				break;
			}
			return mDialog;
			
		}
		
		//填充listview任务
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
					Toast.makeText(getApplicationContext(), "读取失败", Toast.LENGTH_SHORT).show();
				}
			}
		}
	
		public void getAddress(){
	    	
            //获取经纬度
            locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
            //首先检测 通过network 能否获得location对象  
            //如果获得了location对象 则更新tv  
            if (startLocation(networkProvider)) {  
                updateLocation(location);  
            }else   
                //通过gps 能否获得location对象  
                //如果获得了location对象 则更新tv  
                if(startLocation(GpsProvider)){  
                updateLocation(location);  
            }else{  
                //如果上面两种方法都不能获得location对象 则显示下列信息  
                Toast.makeText(this, "没有打开GPS设备", 5000).show();  
            }  

	    
}


/** 
* 通过参数 获取Location对象 
* 如果Location对象为空 则返回 true 并且赋值给全局变量 location 
*   如果为空 返回false 不赋值给全局变量location 
*  
* @param provider 
* @param mContext 
* @return 
*/  
       private boolean startLocation(String provider){  
           Location location = locationManager.getLastKnownLocation(provider);  
  
       // 位置监听器  
           locationManager.requestLocationUpdates(provider, 500, 0, new MylocationListener());
       //如果Location对象为空 则返回 true 并且赋值给全局变量 location  
       //如果为空 返回false 不赋值给全局变量location  
      if (location!= null) {  
           this.location=location;  
           return true;  
          }  
      return false; 

       }    


// 更新位置信息 展示到tv中  
public void updateLocation(Location location) {  
if (location != null) {  
	longitude=(int) (1000000 * location.getLongitude());
     latitude=(int) (1000000 * location.getLatitude());
     Log.e("out5", String.valueOf(longitude));
     Log.e("out6", String.valueOf(latitude));
     
     //查询该经纬度值所对应的地址位置信息 
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
		            // 程序退出前需调用此方法 
		            mapManager.destroy(); 
		            mapManager = null; 
		        } 
		        super.onDestroy(); 
		    } 
		  
		    @Override
		    protected void onPause() { 
		        if (mapManager != null) { 
		            // 终止百度地图 API 
		            mapManager.stop(); 
		        } 
		        super.onPause(); 
		    } 
		  
		    @Override
		    protected void onResume() { 
		        if (mapManager != null) { 
		            // 开启百度地图 API 
		            mapManager.start(); 
		        } 
		        super.onResume(); 
		    } 
		  
		    /** 
		     * 内部类实现MKSearchListener接口,用于实现异步搜索服务 
		     *  
		     * @author liufeng 
		     */
		    public class MySearchListener implements MKSearchListener { 
		        /** 
		         * 根据经纬度搜索地址信息结果 
		         *  
		         * @param result 
		         *            搜索结果 
		         * @param iError 
		         *            错误号（0表示正确返回） 
		         */
		        public void onGetAddrResult(MKAddrInfo result, int iError) { 
		            if (result == null) { 
		                return; 
		            } 
		            StringBuffer sb = new StringBuffer(); 
		            // 经纬度所对应的位置 
		            sb.append(result.strAddr); 
		  
		            // 判断该地址附近是否有POI（Point of Interest,即兴趣点） 
		        
		            // 将地址信息、兴趣点信息显示在TextView上 
		            addressStr=sb.toString();
		            Log.e("address", addressStr);
		        } 
		  
		        /** 
		         * 驾车路线搜索结果 
		         *  
		         * @param result 
		         *            搜索结果 
		         * @param iError 
		         *            错误号（0表示正确返回） 
		         */
		        public void onGetDrivingRouteResult(MKDrivingRouteResult result, 
		                int iError) { 
		        } 
		  
		        /** 
		         * POI搜索结果（范围检索、城市POI检索、周边检索） 
		         *  
		         * @param result 
		         *            搜索结果 
		         * @param type 
		         *            返回结果类型（11,12,21:poi列表 7:城市列表） 
		         * @param iError 
		         *            错误号（0表示正确返回） 
		         */
		        public void onGetPoiResult(MKPoiResult result, int type, int iError) { 
		        } 
		  
		        /** 
		         * 公交换乘路线搜索结果 
		         *  
		         * @param result 
		         *            搜索结果 
		         * @param iError 
		         *            错误号（0表示正确返回） 
		         */
		        public void onGetTransitRouteResult(MKTransitRouteResult result, 
		                int iError) { 
		        } 
		  
		        /** 
		         * 步行路线搜索结果 
		         *  
		         * @param result 
		         *            搜索结果 
		         * @param iError 
		         *            错误号（0表示正确返回） 
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
		    
		    //location事件监听类
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
            //按钮事件监听方法
			public void onClick(View v) {

				switch (v.getId()) {
				case R.id.intoexam:
					boolean flag=CheckNetwork.CheckNetworkState(ExamInfoActivity.this);
					if(flag==false){
						Toast.makeText(getApplicationContext(), "当前无可用网络", Toast.LENGTH_SHORT).show();
						
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