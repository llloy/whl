package kjhd.whl.androidinfo.ui;

import java.util.ArrayList;
import java.util.List;

import kjhd.whl.R;
import kjhd.whl.androidinfo.enity.MyAnswerEntity;
import kjhd.whl.androidinfo.json.JsonParse;
import kjhd.whl.androidinfo.util.GridViewAdapter;
import kjhd.whl.androidinfo.util.SysApplication;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class Answerbackup extends Activity{
	
	

	//Button mtestBut=null;
	//TextView mtestTev=null;
	String mExcuteId=null;
    JsonParse mJsonParse=null;
	List<MyAnswerEntity> mAnswerListAll;
	MyAnswerEntity mAnswerEntity;
	List<MyAnswerEntity>  mOkList=null;
	List<MyAnswerEntity>  mNotList=null;
	int notTm=0;
	int okTm=0;
	GridView notGridView;
	GridView okGridView;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SysApplication.getInstance().addActivity(this);
		setContentView(R.layout.check);
		Intent intent=getIntent();
		mExcuteId=intent.getExtras().getString("excuteid");
		notGridView=(GridView) findViewById(R.id.check_grid_not);
		okGridView=(GridView) findViewById(R.id.check_grid_ok);
		mOkList =new ArrayList<MyAnswerEntity>();
		mNotList =new ArrayList<MyAnswerEntity>();
		try {
			refreshTm();
		} catch (Exception e) {
			e.printStackTrace();
		}
		notGridView.setAdapter(new GridViewAdapter(this, mNotList));
		okGridView.setAdapter(new GridViewAdapter(this, mOkList));
		
		notGridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				TextView mTextView=(TextView)arg1.findViewById(R.id.check_title);
				Log.e("textview", mTextView.getText().toString());
				Intent intent=new Intent();
				intent.putExtra("number", mTextView.getText().toString());
				setResult(2,intent);
				finish();
			}
			
		});
		
		okGridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				TextView mTextView=(TextView)arg1.findViewById(R.id.check_title);
				Log.e("textview", mTextView.getText().toString());
				Intent intent=new Intent();
				intent.putExtra("number", mTextView.getText().toString());
				setResult(2,intent);
				finish();
				
			}
			
		});
		//mAnswerList=new ArrayList<MyAnswerEntity>();
		/*Object[] mObjects=(Object[]) intent.getSerializableExtra("tm");
		String teString=intent.getExtras().getString("ceshi");
		for (int i = 0; i < mObjects.length; i++) {
			MyAnswerEntity myAnswerEntity=(MyAnswerEntity) mObjects[i];
			Log.e("objects", myAnswerEntity.getReason());
			mAnswerList.add(myAnswerEntity);
		}*/
		
/*		mtestBut=(Button) findViewById(R.id.but_mytest);
		mtestTev=(TextView) findViewById(R.id.edit_mytest);
		

		mtestBut.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {

				try {
					refreshTm();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Log.e("nottm2222", String.valueOf(notTm));
				//mtestTev.setText(notTm);
				Intent intent=new Intent();
				intent.putExtra("number", "²âÊÔ·µ»Ø");
				setResult(2,intent);
				finish();
				for (int i = 0; i < mAnswerList.size(); i++) {
					Log.e("list", mAnswerList.get(i).getReason());
					MyAnswerEntity myAnswerEntity2=mAnswerList.get(i);
					mtestTev.setText(myAnswerEntity2.getReason());
				}
			}
		});*/
	}
	
	public void refreshTm() throws Exception{
		mJsonParse=new JsonParse();
		mAnswerListAll=mJsonParse.parseAnswerInfo(mExcuteId);
		
		for (int i = 0; i < mAnswerListAll.size(); i++) {
			
			mAnswerEntity=mAnswerListAll.get(i);
			if (mAnswerEntity.getTm_key()==null||mAnswerEntity.getTm_key().equals("")
					&&mAnswerEntity.getReason()==null||mAnswerEntity.getReason().equals("")
					  &&mAnswerEntity.getValue()==0) 
			   {
				notTm++;
				MyAnswerEntity mNotAnswerEntity=new MyAnswerEntity();
				Log.e("nottm1111", String.valueOf(notTm));
				mNotAnswerEntity.setIdPr(mAnswerEntity.getIdPr());
				mNotAnswerEntity.setIdXh(mAnswerEntity.getIdXh());
				mNotList.add(mNotAnswerEntity);
				
			   }else {
				   MyAnswerEntity mOkAnswerEntity=new MyAnswerEntity();
				   mOkAnswerEntity.setIdPr(mAnswerEntity.getIdPr());
				   mOkAnswerEntity.setIdXh(mAnswerEntity.getIdXh());
				   mOkList.add(mOkAnswerEntity);
			}
		}

	}

}
