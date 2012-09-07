package kjhd.whl.androidinfo.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import kjhd.whl.R;
import kjhd.whl.R.layout;
import kjhd.whl.R.string;

import kjhd.whl.androidinfo.enity.AnswerItemEntity;

import kjhd.whl.androidinfo.enity.MyAnswerEntity;
import kjhd.whl.androidinfo.json.JsonParse;
import kjhd.whl.androidinfo.util.DebugUtil;
import kjhd.whl.androidinfo.util.MyWebService;
import kjhd.whl.androidinfo.util.SysApplication;
import android.R.anim;
import android.R.bool;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class AnswerActivity extends Activity implements OnClickListener{

	
	private ViewPager mViewPager=null;
	private List<MyAnswerEntity> mAnswerList=null;
	private List<AnswerItemEntity> scorelist=null;
	private MyAnswerEntity mAnEntity=null;
	private JsonParse mJsonParse=null;
	private Button backBut=null;
	private Button photoBut=null;
	private Button uploadbButton=null;
	private Button leftButton=null;
	private Button rightbButton=null;
	private Button checkButton=null;
	private List<View>  mListView=null;
	private List<View>  mGetListView=null;
	private TextView mtextView1=null;
	private TextView mtextView2=null;
	private TextView mtextView3=null;
	private TextView mtextView4=null;
	private TextView mtextView5=null;
	private TextView mtextView6=null;
	private TextView mtextView7=null;
	private TextView mtextView8=null;
	private TextView mtextView9=null;
	private TextView mTxt_lable=null;
	private LayoutInflater mInflater=null;
	private EditText mediText1=null;
	private EditText mediText2=null;
	private Button mCancelBut=null;
	private LinearLayout answerLayout=null;
	private LinearLayout goneLayout=null;//���ص÷ֽ���
	private int mViewNumbers=0;//��תҳ����
	private int mTotalViewNumbers=0;
	private String mExcuteId="";//ִ�б���
	//private int[] mQuestionId;
	//private int mIndexId=0;
	//private String[] mIdPr;
	//private int[] mIdXh;
	//private double mValue=1.0;
	//private String mReason="";
	private String mImgUrl="";
	//private String mKey_select;
	private String[] jumpNumbers;//��Ҫ���������
	//private int mTitle_type[];
    private boolean flag=false;//�Ƿ�ʼ������
    private Map<Integer, String> mJumpMap=null;//������Ŀ��ź�ǰ׺���Լ�id
    private List<Map<Integer, String>> mNeedJumpList;//��Ҫ������Ŀ����
    private ProgressDialog mDialog;
    
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SysApplication.getInstance().addActivity(this);
        //Debug.waitForDebugger(); 
        setContentView(R.layout.answers);
        
        Intent intent =getIntent();
        Bundle bundle=intent.getExtras();
        mExcuteId=bundle.getString("excuteid");
        
        //ʵ�������
        backBut=(Button)findViewById(R.id.top_back);
        photoBut=(Button)findViewById(R.id.top_btn);
        checkButton=(Button)findViewById(R.id.btn_mode);
        leftButton=(Button)findViewById(R.id.btn_previous);
        uploadbButton=(Button)findViewById(R.id.btn_upload);
        rightbButton=(Button)findViewById(R.id.btn_next);
        mTxt_lable=(TextView) findViewById(R.id.txt_label);
        mViewPager=(ViewPager)findViewById(R.id.myviewpager);
        mInflater=getLayoutInflater();

        mJumpMap=new TreeMap<Integer, String>();
        mNeedJumpList=new ArrayList<Map<Integer,String>>();
        backBut.setOnClickListener(this);
        photoBut.setOnClickListener(this);//�����¼�
        checkButton.setOnClickListener(this);//����ʾ�  
	    uploadbButton.setOnClickListener(this);//�ύ�ʾ�ť
	    leftButton.setOnClickListener(this);
	    rightbButton.setOnClickListener(this);
	    new MyFillInfoTask().execute("ddd");
	     
   }
	
	   
	
	/**
	 * �����Ŀ
	 *  ��Ŀ��̬���ɣ���ʾ��ʽ��R.layout.answer_items�����ã���MyViewPager��ʾ��
	 * </p>
	 */
	private String fillAnswers() {
		
		mJsonParse=new JsonParse();
		mListView=new ArrayList<View>();
		try {
			mAnswerList=mJsonParse.parseAnswerInfo(mExcuteId);
		} catch (Exception e) {
		}
		
		if(mAnswerList!=null)
	      {
		      for(int i=0;i<mAnswerList.size();i++){
		    	  
		    	   mAnEntity=mAnswerList.get(i);
			       View mitemView=mInflater.inflate(R.layout.answer_items, null);
			       mtextView1=(TextView)mitemView.findViewById(R.id.tev_title);
			       mtextView2=(TextView)mitemView.findViewById(R.id.tev_request);
			       mtextView3=(TextView)mitemView.findViewById(R.id.tev_tm_value);
			       mtextView4=(TextView)mitemView.findViewById(R.id.tev_select_type);
			       mtextView5=(TextView)mitemView.findViewById(R.id.tev_pfgz);
			       mtextView6=(TextView)mitemView.findViewById(R.id.tev_zxbz);
			       mtextView7=(TextView)mitemView.findViewById(R.id.tev_jtsm);
			       mtextView8=(TextView)mitemView.findViewById(R.id.tev_bz);
			       mtextView9=(TextView)mitemView.findViewById(R.id.tev_titletype);
			       mediText1=(EditText)mitemView.findViewById(R.id.getscoreedit);
			       mediText2=(EditText)mitemView.findViewById(R.id.losescoreedit);
			       mCancelBut=(Button) mitemView.findViewById(R.id.but_cancelimg);
			       goneLayout=(LinearLayout) mitemView.findViewById(R.id.linearout_needgone);
			       answerLayout=(LinearLayout)mitemView.findViewById(R.id.layout_answeritem);
			       createAnswerView(answerLayout,mAnEntity.getSelect_type(),mAnEntity.getAnswersitem(),mAnEntity,mediText1);
			       fillChildData(mAnEntity,goneLayout);
			       mediText1.setTag("page"+i+"_getscoreedit");
			       mediText2.setTag("page"+i+"_losescoreedit");
			       mCancelBut.setTag("page"+i+"_cancelbut");
			       mJumpMap.put(i, mAnEntity.getIdPr()+mAnEntity.getIdXh());
			       mitemView.setTag(mAnEntity.getIdPr()+mAnEntity.getIdXh());
			       mitemView.setId(i);
			       mCancelBut.setOnClickListener(new MyCancelOnClickListener());
        
			       mListView.add(mitemView);
			   
		          }
		      return "succ";
	     }
		return "false";
        
	}
	/**
	 * �����ʾ����
	 *  ���ݶ�̬����
	 * </p>
	 */
	private void fillChildData(MyAnswerEntity myAnswerEntity,LinearLayout gLinearLayout){
		
		   mtextView1.setText("��"+myAnswerEntity.getIdPr()+myAnswerEntity.getIdXh()+"��:"+myAnswerEntity.getTitle());
		   if(myAnswerEntity.getRequest()!=null)
	       mtextView2.setText(getResources().getString(R.string.request)+myAnswerEntity.getRequest());
	       mtextView3.setText(getResources().getString(R.string.tm_value)+myAnswerEntity.getTm_value());
	       if(myAnswerEntity.getPfgz()!=null)
	       mtextView5.setText(getResources().getString(R.string.tev_pfgz)+myAnswerEntity.getPfgz());
	       if(myAnswerEntity.getZxbz()!=null)
	       mtextView6.setText(getResources().getString(R.string.zxbz)+myAnswerEntity.getZxbz());
	       if(myAnswerEntity.getJtsm()!=null);
	       mtextView7.setText(getResources().getString(R.string.jtsm)+myAnswerEntity.getJtsm());
	       if(myAnswerEntity.getBz()!=null);
	       mtextView8.setText(getResources().getString(R.string.bz)+myAnswerEntity.getBz());
	       if(String.valueOf(myAnswerEntity.getValue())!=null)
		   mediText1.setText(String.valueOf(myAnswerEntity.getValue()));
		   if(myAnswerEntity.getReason()!=null)
		   mediText2.setText(myAnswerEntity.getReason());
	         //������
	         switch (myAnswerEntity.getSelect_type()) {
		       case 1:
		    	     mtextView4.setText(getResources().getString(R.string.select_type1));
			       break;

		       case 2:
			    	 mtextView4.setText(getResources().getString(R.string.select_type2));
				   break;
		       case 3:
			    	 mtextView4.setText(getResources().getString(R.string.select_type3));
			    	 gLinearLayout.setVisibility(View.VISIBLE);
				   break;
		       }
	         //��Ŀ����
	         switch (myAnswerEntity.getTitle_type()) {
		       case 1:
		    	     mtextView9.setText(getResources().getString(R.string.title_type1));
			       break;

		       case 2:
			    	 mtextView9.setText(getResources().getString(R.string.title_type2));
				   break;
		       case 3:
			    	 mtextView9.setText(getResources().getString(R.string.title_type3));
				   break;
		       }
	         
			     
	}
	
	/**
	 * ������Ŀ�����ͣ�������ͬ�Ĵ𰸡�
	 * @param ll �������LinearLayout
	 * @param type ������
	 */
	private void createAnswerView(LinearLayout answerLayout, int type,
			List<AnswerItemEntity> listitem,MyAnswerEntity myAnswerEntity,EditText scoreEditText) {
		
		switch (type) {
		case MyAnswerEntity.SINGLE:
			createRadioGroup(answerLayout, listitem,myAnswerEntity,scoreEditText);
			break;
		case MyAnswerEntity.MULTLCHECK:
			createMutilChecked(answerLayout,listitem);
			break;
		case MyAnswerEntity.DESCRIPTION:
			createDescription(answerLayout,listitem);
			break;


		}
	
	}


	
	/**
	 * ������ѡ����
	 * @param ll
	 */
	private void createRadioGroup(LinearLayout answerLayout,List<AnswerItemEntity> listitem,final MyAnswerEntity myAnswerEntity,final EditText scoreEditText) {
		
		RadioGroup rGroup=new RadioGroup(AnswerActivity.this);
		final Map<Integer, String> mNeedJumpMap=new TreeMap<Integer, String>();
		for(int i=0;i<listitem.size();i++){
            
			final RadioButton radioButItem=new RadioButton(AnswerActivity.this);
			radioButItem.setText(listitem.get(i).getAnswercontent().toString());
			radioButItem.setTextColor(android.graphics.Color.BLACK);
			radioButItem.setTag(i);
            radioButItem.setGravity(Gravity.CENTER);
            radioButItem.setId(i);
            if(myAnswerEntity.getSelectKey()!=null&&!myAnswerEntity.getSelectKey().equals("")){
            	String mKey=myAnswerEntity.getSelectKey().replace("\n", "");
            	
            	//Log.e("getselect", myAnswerEntity.getSelectKey());
            	//���ˣ����ִ�и�����jumpNumbers
            	if(i==Integer.parseInt(mKey)-1){
            		if(myAnswerEntity.getJump()==1&&i==Integer.parseInt(myAnswerEntity.getJump_key())-1){
						jumpNumbers=myAnswerEntity.getJump_testid().split("��");
					    flag=true;
					    /*for (int j = 0; j < jumpNumbers.length; j++) {
							mNeedJumpMap.put(j, jumpNumbers[j]);
						    Log.e("jumekey0000000", jumpNumbers[j]);
						}*/
					    if(myAnswerEntity.getJump_testid()!=null&&!myAnswerEntity.getJump_testid().equals("")){
					    	
			                String[] testarray=mAnEntity.getJump_testid().split("��");
			                for (int j = 0; j < testarray.length; j++) {
					        mNeedJumpMap.put(j, testarray[j]);
				            }
			                mNeedJumpMap.put(testarray.length, "true");
			                mNeedJumpList.add(mNeedJumpMap);
			          }
					}else if(jumpNumbers!=null){
						flag=false;
						jumpNumbers=null;
					}
            		radioButItem.setChecked(true);
            		mAnswerList.get(mViewNumbers).setSelectKey(myAnswerEntity.getSelectKey());
            	}
            	
            }
			
            radioButItem.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					
					radioButItem.setChecked(true);
					String[] mAnswerNumber=radioButItem.getText().toString().split("��");
					mAnswerNumber[0].replace("\n", "");
					mAnswerList.get(mViewNumbers).setSelectKey(mAnswerNumber[0]);
                    String[] mScore=mAnswerNumber[1].split("--");
					scoreEditText.setText(mScore[1]);
					scoreEditText.setFocusable(false);
					DebugUtil.debug(mAnswerList.get(mViewNumbers).getSelectKey());
					DebugUtil.debug("test",mAnswerList.get(mViewNumbers).getSelectKey());
					if(myAnswerEntity.getJump()==1&&radioButItem.getId()==Integer.parseInt(myAnswerEntity.getJump_key())-1){
						jumpNumbers=myAnswerEntity.getJump_testid().split("��");
					    flag=true;
					}else if(jumpNumbers!=null||mNeedJumpMap!=null){
						mNeedJumpList.remove(mNeedJumpMap);
						flag=false;
						jumpNumbers=null;
					}
				
				}
			});
			rGroup.addView(radioButItem);
		}
		
		answerLayout.addView(rGroup);


		
	}
	
	
	
	
	
	/**
	 * ������������
	 * @param ll
	 */
	
	private void createDescription(LinearLayout answerLayout,
			List<AnswerItemEntity> listitem) {

		for (int i = 0; i < listitem.size(); i++) {
			TextView mAnswerText=new TextView(AnswerActivity.this);
			String mText=listitem.get(i).getAnswercontent().toString().replace("\n", "");
			mAnswerText.setText(listitem.get(i).getAnswercontent().toString());
			mAnswerText.setTextColor(android.graphics.Color.BLACK);
			answerLayout.addView(mAnswerText);
		}
		    
	}
	/**
	 * ������ѡ����
	 * @param ll
	 */
	private void createMutilChecked(LinearLayout answerLayout,
			List<AnswerItemEntity> listitem) {

		   answerLayout.setOrientation(LinearLayout.VERTICAL);
		   for (int i = 0; i < listitem.size(); i++) {
			   final CheckBox mCheckBox = new CheckBox(AnswerActivity.this);
			   Log.e("checkbox", listitem.get(i).getAnswercontent());
			   mCheckBox.setText(listitem.get(i).getAnswercontent().toString());
			   Log.e("checkbox", mCheckBox.getText().toString());
			   mCheckBox.setTextColor(android.graphics.Color.BLACK);
			   mCheckBox.setTag(i);
			   mCheckBox.setOnClickListener(new OnClickListener() {
			   
				public void onClick(View v) {

					mCheckBox.setChecked(mCheckBox.isChecked());
				}
			});
			   answerLayout.addView(mCheckBox);
		}
		   
	}
	
	
	/**
	 * �洢��Ƭ������SD��
	 * @param 1
	 */

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==Activity.RESULT_OK){
			String sdStatus=Environment.getExternalStorageState();
			if(!sdStatus.equals(Environment.MEDIA_MOUNTED)){// ���sd�Ƿ����
				Log.v("TestFile",
                        "SD card is not avaiable/writeable right now.");
                return;
			}
			Bundle bundle=data.getExtras();
			Bitmap bitmap=(Bitmap)bundle.get("data");
			FileOutputStream b = null;
			File file = new File("/sdcard/myImage/");
            file.mkdirs();// �����ļ���
            String str=null;
            Date date=null;
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");//��ȡ��ǰʱ�䣬��һ��ת��Ϊ�ַ���
            date =new Date();
            str=format.format(date);
            String fileName = "/sdcard/myImage/"+str+".jpg";
            try {
                b = new FileOutputStream(fileName);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// ������д���ļ�
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    b.flush();
                    b.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

				try {
					FileInputStream fis = new FileInputStream(fileName);
					
					ByteArrayOutputStream baos=new ByteArrayOutputStream();
					byte[] buffer=new byte[8192];
					int count=0;
					while((count=fis.read(buffer))>=0){
						baos.write(buffer,0,count);
					}
					mImgUrl=new String(org.apache.commons.codec.binary.Base64.encodeBase64(baos.toByteArray()));
					mAnswerList.get(mViewNumbers).setImgUrl(new String(org.apache.commons.codec.binary.Base64.encodeBase64(baos.toByteArray())));
				} catch (Exception e) {
					
				}
				//ͼƬ�ѱ��棬���ȷ���ϴ�
				Toast.makeText(getApplicationContext(), "ͼƬ�ѱ��棬���ȷ���ϴ�", Toast.LENGTH_SHORT).show();

			
		}
		else if(resultCode==2){
			String itemString=data.getExtras().getString("number");
			Toast.makeText(getApplicationContext(), itemString, Toast.LENGTH_SHORT).show();
			for (int i = 0; i < mJumpMap.size(); i++) {
				if (mJumpMap.get(i).equals(itemString)) {
					mViewNumbers=i;
					Log.e("i", String.valueOf(i));
				}
			}
			mViewPager.setCurrentItem(mViewNumbers);
			mTxt_lable.setText((mViewNumbers+1)+"/"+mListView.size());
		}
		
	}
	


    //��ť�����¼�����
	public void onClick(View v) {

		switch (v.getId()) {
		//���ذ�ť
		case R.id.top_back:
			Intent intent=new Intent();
			setResult(5,intent);
			finish();
			break;
        //���հ�ť
        case R.id.top_btn:
        	Intent intent2=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent2, 1);
			break;
		//����ʾ�
        case R.id.btn_mode:
        	/*for (int i = 0; i < mSendList.size(); i++) {
    		Log.e("size", String.valueOf(mSendList.size()));
    		Log.e("msendlist", mSendList.get(i).getReason());
    		
		}*/
    	     Intent intent3 = new Intent();
    	     //intent3.putExtra("tm", mSendList.toArray());
    	     intent3.putExtra("excuteid", mExcuteId);
		     intent3.setClass(AnswerActivity.this, Answerbackup.class);
             startActivityForResult(intent3, 3);
			break;
		//��һ��
        case R.id.btn_previous:
        	if(mViewNumbers!=0){
                 if (flag==true) {

					for (int i = jumpNumbers.length-1; i >=0; i--) {
						if(mJumpMap.get(mViewNumbers-1).equals(jumpNumbers[i])){
							
							mViewNumbers=mViewNumbers-1;
							Log.e("55555555", String.valueOf(mViewNumbers));
						}
					}
					//Log.e("6666666", String.valueOf(mViewNumbers));
				}/*else if(mNeedJumpList!=null){
					for (int i = mNeedJumpList.size()-1; i >= 0; i--) {
		        		for (int j = mNeedJumpList.get(i).size()-1; j >=0 ; j++) {
		        			if(mJumpMap.get(mViewNumbers-1).equals(mNeedJumpList.get(i).get(j))){
			        			//�Ӹ������ж���������Ŀ�Ƿ�Ϊ��
			        			mViewNumbers=mViewNumbers-1;
			        		    Log.e("88888", String.valueOf(mViewNumbers));
			        		}
						}
		        		
					}
				}*/
  				mViewNumbers--;
  				mTxt_lable.setText((mViewNumbers+1)+"/"+mListView.size());
  				if (mViewNumbers>=0) {
	  				mViewPager.setCurrentItem(mViewNumbers, true);
				}else{
  					Toast.makeText(getApplicationContext(), "���Ѿ��ǵ�һҳ��", Toast.LENGTH_SHORT).show();
  				}		
  				}
  				else{
  					Toast.makeText(getApplicationContext(), "���Ѿ��ǵ�һҳ��", Toast.LENGTH_SHORT).show();
  				}
			break;
	    //��һ��
        case R.id.btn_next:
                if(saveQuestion(mViewNumbers)){
				if(mViewNumbers<mListView.size()-1){
			        
					if(flag){
			        	for (int i = 0; i < jumpNumbers.length; i++) {
			        		if(mJumpMap.get(mViewNumbers+1).equals(jumpNumbers[i])){
			        			//�Ӹ������ж���������Ŀ�Ƿ�Ϊ��
			        			mViewNumbers=mViewNumbers+1;
			        		    Log.e("88888", String.valueOf(mViewNumbers));
			        		}
						}
					}else if(mNeedJumpList!=null){
			        	for (int i = 0; i < mNeedJumpList.size(); i++) {
			        		for (int j = 0; j < mNeedJumpList.get(i).size(); j++) {
			        			if(mJumpMap.get(mViewNumbers+1).equals(mNeedJumpList.get(i).get(j))){
				        			//�Ӹ������ж���������Ŀ�Ƿ�Ϊ��
				        			mViewNumbers=mViewNumbers+1;
				        		    Log.e("88888", String.valueOf(mViewNumbers));
				        		}
							}
			        		
						}
			        }
					
			        
				    mViewNumbers++;
				    mTxt_lable.setText((mViewNumbers+1)+"/"+mListView.size());
				    if (mViewNumbers<=mListView.size()-1) {
	  					Log.e("33333333", String.valueOf(mViewNumbers));
		  				mViewPager.setCurrentItem(mViewNumbers, true);
					}else{
						mViewNumbers=(mViewNumbers-1)-1;
	  					Toast.makeText(getApplicationContext(), "���Ѿ������һҳ��", Toast.LENGTH_SHORT).show();
	  				}

				
				
				}
				else{
					Toast.makeText(getApplicationContext(), "���Ѿ������һҳ��", Toast.LENGTH_SHORT).show();
				    } 
                }else {
                	Toast.makeText(getApplicationContext(), "����Ϊ�����⣡", Toast.LENGTH_SHORT).show();
				}
			break;
		//�ύ�����ʾ�
        case R.id.btn_upload:
        	
           /* for (int i = 0; i < mAnswerList.size(); i++) {
				if(judgeTm(i)){
					
				}else {
					MyWebService allMyWebService=new MyWebService();
					allMyWebService.saveTm(mAnswerList.get(i));
				}
			}*/
        	for (int i = 0; i < mNeedJumpList.size(); i++) {
        		Log.e("fucki", String.valueOf(i));
				 for (int j = 0; j < mNeedJumpList.get(i).size(); j++) {
					Log.e("fuck", mNeedJumpList.get(i).get(j));
					Log.e("fuckj", String.valueOf(i));
				}
			}
        	sumitDialog();
			break;
		}
		
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
				SysApplication.getInstance().exit();
			}
		});
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				
				dialog.dismiss();
				
			}
		});
		builder.create().show();
	}
	
	//������Ŀ��Ϣ
	private boolean saveQuestion(int page){
		EditText mGetEditText1=(EditText)mViewPager.findViewWithTag("page"+page+"_getscoreedit"); 
		EditText mGetEditText2=(EditText)mViewPager.findViewWithTag("page"+page+"_losescoreedit"); 
	    if(mGetEditText1.getText().toString()!=null&&!mGetEditText1.equals("")){
	    	if (mAnswerList.get(page).getTm_value()<Double.parseDouble(mGetEditText1.getText().toString())) {
	    		Toast.makeText(getApplicationContext(), "����ֳ����˹涨�ķ�ֵ��", Toast.LENGTH_SHORT).show();
	    		return false;
			}else {
				mAnswerList.get(page).setValue(Double.parseDouble(mGetEditText1.getText().toString()));
			}
		     
	    }
        mAnswerList.get(page).setReason(mGetEditText2.getText().toString());
		   if (mAnswerList.get(page).getTest_null()==1) {
			
	           if(judgeTm(page)){
	    		   return true;
	           }else {
				   return false;
			   }
		   }else {
			  return true;
		   }  
	   
	}
   //�ж��Ƿ��ϴ���Ŀ��Ϣ
	private boolean judgeTm(int tmNumber){
		Log.e("selectkey", mAnswerList.get(tmNumber).getSelectKey());
		/*if (mAnswerList.get(tmNumber).getSelectKey()==null||mAnswerList.get(tmNumber).getSelectKey().length()==0
				&&mAnswerList.get(tmNumber).getReason()==null||mAnswerList.get(tmNumber).getReason().equals("")
				  &&mAnswerList.get(tmNumber).getValue()==0)
		{
		     return false;	
		}*/
		if (mAnswerList.get(tmNumber).getSelectKey()==null||mAnswerList.get(tmNumber).getSelectKey().length()==0) {
			  if (mAnswerList.get(tmNumber).getReason()==null||mAnswerList.get(tmNumber).getReason().equals("")) {
				if (mAnswerList.get(tmNumber).getValue()==0) {
					return false;
				}
			}
		}
		
		return true;
	}
   
    //ViewPager�������ڲ���
    private class MyPagerAdapter extends PagerAdapter{

    	@Override
		public boolean isViewFromObject(View arg0, Object arg1) {

			return arg0==arg1;
		}
		
		@Override
		public int getCount() {
		
		    return mListView.size();
			
		}
		public int getConut(int size){
			return size;
		}
		@Override
		public void destroyItem(View container, int position,
				Object object) {

			if(position<mListView.size()){
			((ViewPager) container).removeView(mListView.get(position));
			}else {
				Toast.makeText(getApplicationContext(), "���Ѿ������һҳ�ˣ�", Toast.LENGTH_SHORT).show();
			}

		}
		
		@Override
		public Object instantiateItem(View container, int position) {

			((ViewPager) container).addView(mListView.get(position),0);
			return mListView.get(position);
		}
		public int getItemPosition(Object object) {
			
			return POSITION_NONE;
		};
		@Override
		public void notifyDataSetChanged() {
			// TODO Auto-generated method stub
			super.notifyDataSetChanged();
		}
    	
    }
    //viewpager�������ڲ���
    private class MyOnPageChangeListener implements OnPageChangeListener{

		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		public void onPageSelected(int arg0) {
			  mViewNumbers=arg0;
			 
		}
    	
    }
    //ȡ����ͼ�ϴ���ť
    private class MyCancelOnClickListener implements OnClickListener{

		public void onClick(View v) {

			if(mAnswerList.get(mViewNumbers).getImgUrl()!=null&&!mAnswerList.get(mViewNumbers).getImgUrl().equals("")){
				mAnswerList.get(mViewNumbers).setImgUrl("");
				Toast.makeText(getApplicationContext(), "��ͼ�ϴ�ȡ���ɹ���", Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(getApplicationContext(), "δ��ͼ���߽�ͼδ�ɹ���", Toast.LENGTH_SHORT).show();
			}
		}
    	
    	
    }
   //��ȡ���������ݣ������viewpager����
   private class MyFillInfoTask extends AsyncTask<String, Integer, String>{

	   protected void onPreExecute() {
		   mDialog = new ProgressDialog(AnswerActivity.this);
           mDialog.setTitle("������");
           mDialog.setMessage("���ڻ�ȡ��Ŀ��Ϣ�����Ժ�...");
          
           mDialog.show();
			
		};
		protected String doInBackground(String... params) {
	
			if(!params[0].equals("")){
				return fillAnswers();
			}
			return  "false";
		}
		protected void onPostExecute(String result) {
            if (result.equals("succ")) {
                mDialog.cancel();
            	mViewPager.setAdapter(new MyPagerAdapter());
            	mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
            	mTxt_lable.setText((mViewNumbers+1)+"/"+mListView.size());
			}else {
				mDialog.cancel();
				Toast.makeText(getApplicationContext(), "û�ж�ȡ�ɹ�,�������磡", Toast.LENGTH_SHORT).show();
			}
		}
    }
   //�ύ�����ʾ�
   private void sumitDialog(){
	   AlertDialog.Builder builder=new Builder(this);
		builder.setMessage("ȷ���ύ�ʾ���");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				MyWebService myWebService=new MyWebService();
				myWebService.saveTm(mAnswerList);
				Intent intent4=new Intent();
	        	intent4.putExtra("excuteid", mExcuteId);
	        	intent4.putExtra("type", 2);
	        	intent4.setClass(AnswerActivity.this, ExamInfoActivity.class);
	        	startActivityForResult(intent4, 4);
			}
		});
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				
				dialog.dismiss();
				
			}
		});
		builder.create().show();
   }
  
}