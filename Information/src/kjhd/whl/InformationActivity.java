package kjhd.whl;

import java.io.IOException;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;


import kjhd.whl.androidinfo.json.JsonParse;
import kjhd.whl.androidinfo.util.MyWebService;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class InformationActivity extends Activity {

    TextView showTest;
    Button button;
    
    

    private static final String NAMESPACE = "http://192.168.1.104:8080/InfoResearch/services/WebService";

	// WebServiceµÿ÷∑
	//private static String URL = "http://192.168.1.104:8080/InfoResearch/services/WebService";
	private static String URL = "http://192.168.1.104:8080/InfoResearch/services/WebService";

	private static final String METHOD_NAME = "findTm";


	private static String SOAP_ACTION = "http://192.168.1.104:8080/InfoResearch/services/WebService/findTm";


	private int id=25;
	private int dd=7;
	private String excuteId="120803041745593";
	int type=1;
	

	String result;
    
    String text="";
    private SoapObject sObject;
    
    MyWebService webService;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
     
        webService=new MyWebService();
       // showTest=(TextView)findViewById(R.id.testview);
        
       // button=(Button)findViewById(R.id.testbut);
        button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
			
				String text1;
				try {
					text1 = webService.ValidateExcuteId("120803041745593");
					showTest.setText(text1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
        

        
        
    }
    
private String ValidateExcuteId() {
		
		
			
			SoapObject rpc = new SoapObject(NAMESPACE, METHOD_NAME);

			//rpc.addProperty("type", type);
			//System.out.println(id);
			//rpc.addProperty("dd", dd);
			//System.out.println(dd);
			rpc.addProperty("excuteId", excuteId);
			System.out.println(excuteId);
	

			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
			envelope.bodyOut = rpc;
			envelope.dotNet = true;
			envelope.setOutputSoapObject(rpc);
			
			HttpTransportSE ht = new HttpTransportSE(URL);

			//AndroidHttpTransport ht = new AndroidHttpTransport(URL);
			ht.debug = true;

			try {
				ht.call(SOAP_ACTION, envelope);
			} catch (Exception e) {
	
				e.printStackTrace();
			}

			sObject =(SoapObject)envelope.bodyIn;
			result=sObject.getProperty(0).toString();
			
			Log.e("out", result);
			

		
		    return result;
        

		
	}

	class MyViewPager extends ViewPager{

		public MyViewPager(Context context) {
			super(context);
		}
		@Override
		public void setCurrentItem(int item, boolean smoothScroll) {
			// TODO Auto-generated method stub
			super.setCurrentItem(item, smoothScroll);
		}
	}
	
}