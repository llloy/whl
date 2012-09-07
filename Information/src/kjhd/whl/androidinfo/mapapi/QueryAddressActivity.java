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
 * ���ݾ�γ�Ȳ�ѯ��ַ��Ϣ 
 *  
 * @author liufeng 
 * @date 2011-05-03 
 */
public class QueryAddressActivity extends MapActivity { 
    // �����ͼ��������� 
    private BMapManager mapManager; 
    // �������������� 
    private MKSearch mMKSearch;
    
    private EditText longitudeEditText; 
    private EditText latitudeEditText; 
    private TextView addressTextView; 
    private Button queryButton; 
  
    @Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.main); 
  
        // ��ʼ��MapActivity 
        mapManager = new BMapManager(getApplication()); 
        // init�����ĵ�һ�����������������API Key 
        mapManager.init("B71CB93622C19897E8C753D5A3A1096FECA16292", null); 
        super.initMapActivity(mapManager); 
  
        // ��ʼ��MKSearch 
        mMKSearch = new MKSearch(); 
        mMKSearch.init(mapManager, new MySearchListener()); 
        
		
		
		
        // ͨ��id��ѯ�ڲ����ļ��ж���Ŀؼ� 
       /* longitudeEditText = (EditText) findViewById(R.id.longitude_input); 
        latitudeEditText = (EditText) findViewById(R.id.latitude_input); 
        addressTextView = (TextView) findViewById(R.id.address_text); 
        queryButton = (Button) findViewById(R.id.query_button); */
  

        
        // ����ַ��ѯ��ť���õ����¼������� 
        queryButton.setOnClickListener(new OnClickListener() { 
            public void onClick(View v) { 
            	 // �û�����ľ���ֵ 
            	String longitudeStr = longitudeEditText.getText().toString(); 
                // �û������γ��ֵ 
                String latitudeStr = latitudeEditText.getText().toString(); 
             
                try { 
                    // ���û�����ľ�γ��ֵת����int���� 
          

                	//int longitude = 
                			//(int) (1000000 * Double.parseDouble(longitudeStr)); 
                    //int latitude =
                    		//(int) (1000000 * Double .parseDouble(latitudeStr)); 
                  //  Log.e("out1", String.valueOf(longitude));
                   // Log.e("out2", String.valueOf(latitude));
                    
                    //��ȡ��γ��
                    LocationManager locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
                    Location location =locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
                    Log.e("out3", String.valueOf(location.getLongitude()));
                    Log.e("out4", String.valueOf(location.getLatitude()));
                    int longitude=(int) (1000000 * location.getLongitude());
                    int latitude=(int) (1000000 * location.getLatitude());
                    
                    Log.e("out5", String.valueOf(longitude));
                    Log.e("out6", String.valueOf(latitude));
                    
                    //��ѯ�þ�γ��ֵ����Ӧ�ĵ�ַλ����Ϣ 
                    mMKSearch.reverseGeocode(new GeoPoint(latitude, longitude)); 
                } catch (Exception e) { 
                    addressTextView.setText("��ѯ��������������ľ�γ��ֵ��"); 
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
            addressTextView.setText(sb.toString()); 
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
}
