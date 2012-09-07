package kjhd.whl.androidinfo.test;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import junit.framework.TestCase;
import kjhd.whl.TestUploadImg;
import kjhd.whl.androidinfo.enity.MyAnswerEntity;
import kjhd.whl.androidinfo.json.JsonParse;
import kjhd.whl.androidinfo.ui.AnswerActivity;
import kjhd.whl.androidinfo.util.MyWebService;

 public class TestSample extends TestCase{
 
        private int a;
 
        private String b;
 
        private String result;
        
        MyWebService webService;
        TestUploadImg mytestImg;
        
        AnswerActivity answerActivity;
        JsonParse jsonParse;
        List<MyAnswerEntity> mList;
        
        
        
        public void setUp() /*��ʼ���Ե�ǰ�����C��ʼ�����Ի���*/{
 
        	 mytestImg=new TestUploadImg();
             webService =new MyWebService();
             jsonParse=new JsonParse();
             mList=new ArrayList<MyAnswerEntity>();
             a=1;
             b="120828042042412162";
 
          
          }



        public void upLoad(){
        	mytestImg.uploadTest();

        }
        public void testFind(){
        	
        	result=webService.findJcmk(3, b);
        	Log.e("jsonresult", result);
        	
        }
        
        public void testfindTm(){
        	
        	result=webService.findTm(b);
        	Log.e("jsonresult", result);
        }

 
        public void testJsonAnswer(){
        	
        	try {
        		
				mList=jsonParse.parseAnswerInfo(b);
				for(MyAnswerEntity answerEntity:mList){
					
					Log.e("sssssssssssssssssssss", answerEntity.getTitle());
					
					
				}
				
			} catch (Exception e) {
	
				e.printStackTrace();
				Log.e("ssssssssssssssssssssssss", "�����˰�");
			}
        }

       public void tearDown()/*�����������Խ���*/
 
{}
 
       public void testsave(){
    	   
    	   //webService.saveQtmk("1");
    	  
       }


 


 
}
 
