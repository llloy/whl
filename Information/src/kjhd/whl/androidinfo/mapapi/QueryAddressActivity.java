package kjhd.whl.androidinfo.mapapi;

import kjhd.whl.R;
import android.R.integer;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle; 
import android.text.StaticLayout;
import android.util.Log;
import android.view.View; 
import android.view.View.OnClickListener; 
import android.widget.Button; 
import android.widget.EditText; 
import android.widget.TextView; 
  
import com.baidu.mapapi.BMapManager; 
import com.baidu.mapapi.GeoPoint; 
import com.baidu.mapapi.MKAddrInfo; 
import com.baidu.mapapi.MKBusLineResult;
import com.baidu.mapapi.MKDrivingRouteResult; 
import com.baidu.mapapi.MKPoiInfo; 
import com.baidu.mapapi.MKPoiResult; 
import com.baidu.mapapi.MKSearch; 
import com.baidu.mapapi.MKSearchListener; 
import com.baidu.mapapi.MKSuggestionResult;
import com.baidu.mapapi.MKTransitRouteResult; 
import com.baidu.mapapi.MKWalkingRouteResult; 
import com.baidu.mapapi.MapActivity; 
  
/** 
 * 根据经纬度查询地址信息 
 *  
 * @author liufeng 
 * @date 2011-05-03 
 */
public class QueryAddressActivity extends MapActivity { 
    // 定义地图引擎管理类 
    private BMapManager mapManager; 
    // 定义搜索服务类 
    private MKSearch mMKSearch;
    
    private EditText longitudeEditText; 
    private EditText latitudeEditText; 
    private TextView addressTextView; 
    private Button queryButton; 
  
    @Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.main); 
  
        // 初始化MapActivity 
        mapManager = new BMapManager(getApplication()); 
        // init方法的第一个参数需填入申请的API Key 
        mapManager.init("B71CB93622C19897E8C753D5A3A1096FECA16292", null); 
        super.initMapActivity(mapManager); 
  
        // 初始化MKSearch 
        mMKSearch = new MKSearch(); 
        mMKSearch.init(mapManager, new MySearchListener()); 
        
		
		
		
        // 通过id查询在布局文件中定义的控件 
       /* longitudeEditText = (EditText) findViewById(R.id.longitude_input); 
        latitudeEditText = (EditText) findViewById(R.id.latitude_input); 
        addressTextView = (TextView) findViewById(R.id.address_text); 
        queryButton = (Button) findViewById(R.id.query_button); */
  

        
        // 给地址查询按钮设置单击事件监听器 
        queryButton.setOnClickListener(new OnClickListener() { 
            public void onClick(View v) { 
            	 // 用户输入的经度值 
            	String longitudeStr = longitudeEditText.getText().toString(); 
                // 用户输入的纬度值 
                String latitudeStr = latitudeEditText.getText().toString(); 
             
                try { 
                    // 将用户输入的经纬度值转换成int类型 
          

                	//int longitude = 
                			//(int) (1000000 * Double.parseDouble(longitudeStr)); 
                    //int latitude =
                    		//(int) (1000000 * Double .parseDouble(latitudeStr)); 
                  //  Log.e("out1", String.valueOf(longitude));
                   // Log.e("out2", String.valueOf(latitude));
                    
                    //获取经纬度
                    LocationManager locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
                    Location location =locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
                    Log.e("out3", String.valueOf(location.getLongitude()));
                    Log.e("out4", String.valueOf(location.getLatitude()));
                    int longitude=(int) (1000000 * location.getLongitude());
                    int latitude=(int) (1000000 * location.getLatitude());
                    
                    Log.e("out5", String.valueOf(longitude));
                    Log.e("out6", String.valueOf(latitude));
                    
                    //查询该经纬度值所对应的地址位置信息 
                    mMKSearch.reverseGeocode(new GeoPoint(latitude, longitude)); 
                } catch (Exception e) { 
                    addressTextView.setText("查询出错，请检查您输入的经纬度值！"); 
                    Log.e("out", e.getMessage());
                } 
            } 
        }); 
    } 


	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	  
  
    @Override
    protected boolean isRouteDisplayed() { 
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
            addressTextView.setText(sb.toString()); 
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
}
