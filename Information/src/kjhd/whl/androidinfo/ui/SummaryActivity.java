package kjhd.whl.androidinfo.ui;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import kjhd.whl.R;
import kjhd.whl.androidinfo.json.JsonParse;
import kjhd.whl.androidinfo.util.CheckNetwork;
import kjhd.whl.androidinfo.util.MyWebService;
import kjhd.whl.androidinfo.util.SysApplication;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SummaryActivity extends Activity{
	
	
	  private Button saveButton=null;
	  private Button exitButton=null;
	  private Button exitButton2=null;
	  private ListView mListView=null;
	  private JsonParse mJsonParse;
	  private MyWebService myWebService;
	  private List<Map<String, String>> mlisMaps=null;
	  private Map<String, String> changeItemMap;
	  private String contentString="";
	  private String mExcuteId="";
	  private final static int DATE_DIALOG = 0;    
	  private final static int TIME_DIALOG = 1;    
	  private Calendar c = null;
	  private String dateStr;
	  private String timeStr;
	  private String mModule_key;
	  
	  public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        SysApplication.getInstance().addActivity(this);
	        setContentView(R.layout.summary);
	        
	        Intent intent =getIntent();
	        Bundle bundle=intent.getExtras();
	        mExcuteId=bundle.getString("excuteid");
	        Log.e("mexcutid", mExcuteId);
	        myWebService=new MyWebService();
	        mListView=(ListView)findViewById(R.id.list_summaryinfo);
	        //�����ʾ���˳������¼�
	        saveButton = (Button)findViewById(R.id.tosummary);
	        exitButton = (Button)findViewById(R.id.but_exit);
	        exitButton2=(Button) findViewById(R.id.summary_back);
	        
/*	        Thread geThread=new Thread(new getInfoThread());
	        geThread.start();
	        */
	        saveButton.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					boolean flag=CheckNetwork.CheckNetworkState(SummaryActivity.this);
					if(flag==false){
						Toast.makeText(getApplicationContext(), "��ǰ�޿�������", Toast.LENGTH_SHORT).show();
						
					}else {

						Toast.makeText(getApplicationContext(), "������Ϣ�ɹ���", Toast.LENGTH_SHORT).show();
					}
					

					
				}
			});
	        
	       
	        exitButton.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					
					/*saveModular();
					Intent intent = new Intent();
					intent.setClass(SummaryActivity.this, AnswerActivity.class);
					intent.putExtra("excuteid", mExcuteId);
					startActivity(intent);*/
					SysApplication.getInstance().exit();
					
				}
			});
	        exitButton2.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {

					Intent intent=new Intent();
					setResult(4,intent);
					finish();

				}
			});
	        
	        TextView mTextView=new TextView(this);
	        mTextView.setText("С�Ჹ����Ϣ����");
	        //����ģ����Ϣ����
	       /* try {
				createLayout();
			} catch (Exception e) {

				e.printStackTrace();
			}*/


  }
	

		//�����������
		private void createLayout() throws Exception{
			

			mJsonParse=new JsonParse();
			Log.e("mexcutid", mExcuteId);
			mlisMaps=mJsonParse.parseModular(2,mExcuteId);
		    if(mlisMaps!=null){
			//SimpleAdapter simpleAdapter=new SimpleAdapter(this, mlisMaps, R.layout.mymodular_item, new String[]{"title_name","module_key"}, new int[]{R.id.tev_mymodular_titlename,R.id.tev_mymodular_modulekey});
			//mListView.setAdapter(simpleAdapter);
			mListView.setDivider(getResources().getDrawable(R.drawable.pdialog_img_divider));
			mListView.setDividerHeight(3);
			mListView.setCacheColorHint(Color.TRANSPARENT);
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
			}else{
				Toast.makeText(getApplicationContext(), "С��ģ��û����Ϣ!", Toast.LENGTH_SHORT).show();
			}

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
					SummaryActivity.this.finish();
				}
			});
			builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					
					dialog.dismiss();
					
				}
			});
			builder.create().show();
		}
		
		//����ģ����Ϣ
		public void saveModular() {
			
			for (int i = 0; i < mlisMaps.size(); i++) {
				changeItemMap=mlisMaps.get(i);
				mModule_key=changeItemMap.get("module_key");
				Log.e("testmodule", mModule_key);
			}
			try {
				myWebService.saveQtmk(mlisMaps,mExcuteId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

}
