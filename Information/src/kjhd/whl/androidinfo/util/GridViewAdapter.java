package kjhd.whl.androidinfo.util;

import java.util.List;



import kjhd.whl.R;
import kjhd.whl.androidinfo.enity.MyAnswerEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter{
	
	Context mContext;
	List<MyAnswerEntity> mAnswerList=null;

	public GridViewAdapter(Context mContext,List<MyAnswerEntity> mAnswerList){
		this.mContext=mContext;
		this.mAnswerList=mAnswerList;
	}
	
	
	public int getCount() {

		return mAnswerList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.check_item, null);
		}
		TextView tv = (TextView) convertView.findViewById(R.id.check_title);
		tv.setText(mAnswerList.get(position).getIdPr()+mAnswerList.get(position).getIdXh());
		return convertView;
	}

}
