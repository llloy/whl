package kjhd.whl;

import kjhd.whl.androidinfo.util.MyWebService;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class TestActivity extends Activity{
	
	
	EditText mEditText;
	Button mButton;
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		mEditText=(EditText) findViewById(R.id.edit_mytest);
		Log.e("edit_mytest", mEditText.getText().toString());
		
		mButton=(Button)findViewById(R.id.but_mytest);
	
		mButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {

				Log.e("but_mytest", mEditText.getText().toString());
			}
		});

	}

}
