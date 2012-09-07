package kjhd.whl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.codec.binary.Base32;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.R.bool;
import android.util.Base64;
import android.util.Log;

public class TestUploadImg {
	
	
	public void uploadTest(){
		
		try {
			FileInputStream fis = new FileInputStream("/sdcard/myImage/20120811141932.jpg");
			
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			byte[] buffer=new byte[8192];
			int count=0;
			while((count=fis.read(buffer))>=0){
				baos.write(buffer,0,count);
			}
			String uploadBuffer=new String(org.apache.commons.codec.binary.Base64.encodeBase64(baos.toByteArray()));
			connectWebService(uploadBuffer);
			
		} catch (Exception e) {
			
		}
	}

	private boolean connectWebService(String uploadBuffer) {

		boolean ret=false;
		String Url="http://192.168.1.103:8080/axis2/services/Upload?wsdl";
		String namespace="http://ws.apache.org/axis2";
		String methodName="UpdateImage";
		SoapObject request=new SoapObject(namespace, methodName);
		request.addProperty("name","test");
		request.addProperty("image",uploadBuffer);
		SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		HttpTransportSE ht=new HttpTransportSE(Url);
		
		try {
			ht.call(null, envelope);
			SoapObject so=(SoapObject)envelope.bodyIn;
			ret=Boolean.valueOf(so.getProperty("return").toString());
			Log.e("sssssssssssssss", so.getProperty("return").toString());
			return ret;
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

}
