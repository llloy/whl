package kjhd.whl.androidinfo.util;

import kjhd.whl.androidinfo.ui.LoginActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.provider.Settings;
import android.widget.Toast;

public class CheckNetwork {
	
	//�������״̬
    public static boolean CheckNetworkState(Context context)
    {
        boolean flag = false;
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        State mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        //���3G��wifi��2G������״̬�����ӵģ����˳���������ʾ��ʾ��Ϣ�����������ý���
        if(mobile == State.CONNECTED||mobile==State.CONNECTING){
        	
        	flag=true;
        	return flag;
        	
        }else if(wifi == State.CONNECTED||wifi==State.CONNECTING){
            flag=true;
        	return flag;
        }
          return flag;
       
    }
    
   
}
