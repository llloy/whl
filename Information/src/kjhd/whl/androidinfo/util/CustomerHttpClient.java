package kjhd.whl.androidinfo.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

public class CustomerHttpClient {
	
	private static final String CHARSET = HTTP.UTF_8;
	private static HttpClient customerHttpClient;
	private static final String TAG = "CustomerHttpClient";

	
	private CustomerHttpClient(){
		
	}
	//������߳�����ĵ���HttpClient
	public static synchronized HttpClient getHttpClient() {
        if (null == customerHttpClient) {
            HttpParams params = new BasicHttpParams();
            // ����һЩ��������
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params,
                    CHARSET);
            HttpProtocolParams.setUseExpectContinue(params, true);
            HttpProtocolParams
                    .setUserAgent(
                            params,
                            "Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
                                    + "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
            // ��ʱ����
            /* �����ӳ���ȡ���ӵĳ�ʱʱ�� */
            ConnManagerParams.setTimeout(params, 1000);
            /* ���ӳ�ʱ */
            HttpConnectionParams.setConnectionTimeout(params, 2000);
            /* ����ʱ */
            HttpConnectionParams.setSoTimeout(params, 4000);
          
            // �������ǵ�HttpClient֧��HTTP��HTTPS����ģʽ
            SchemeRegistry schReg = new SchemeRegistry();
            schReg.register(new Scheme("http", PlainSocketFactory
                    .getSocketFactory(), 80));
            schReg.register(new Scheme("https", SSLSocketFactory
                    .getSocketFactory(), 443));

            // ʹ���̰߳�ȫ�����ӹ���������HttpClient
            ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
                    params, schReg);
            customerHttpClient = new DefaultHttpClient(conMgr, params);
        }
        return customerHttpClient;
    }
	
	//���ӷ�������ȡ���ݷ���
	public static StringBuilder getJsonData(String url)throws Exception{
		StringBuilder sBuilder=new StringBuilder();
		HttpClient client=getHttpClient();
		HttpResponse response=client.execute(new HttpGet(url));
		if (response.getStatusLine().getStatusCode() == 200){
			HttpEntity entity=response.getEntity();
			if(entity!=null){
				BufferedReader reader=new BufferedReader(new InputStreamReader(entity.getContent()));
				String data=null;
				while((data=reader.readLine())!=null){
					sBuilder.append(data);
				}
				reader.close();
			}
	}
		return sBuilder;
}

}
