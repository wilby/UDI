package net.wcjj.UDI;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputConnection;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PreferencesToTextViewAdapter extends BaseAdapter {

	private Context mContext;
	private SharedPreferences mPreferences;
	private String mPreferenceName;
	private int mResource;	
	
	public PreferencesToTextViewAdapter(Context context, int resource, String preferenceName) throws FileNotFoundException {	
		mContext = context;		
		mPreferenceName = preferenceName;
		final int mode = context.MODE_PRIVATE;
		mResource = resource;
		mPreferences = context.getSharedPreferences(preferenceName, mode);
		if (mPreferences == null) {			
			throw new FileNotFoundException("The indicated preference file does not exist");
		}
	}
	
	@Override
	public int getCount() {		
		return mPreferences.getAll().size();
	}

	@Override
	public Object getItem(int arg0) {		
		@SuppressWarnings("unchecked")
		Map<String, String> prefs = (Map<String,String>)mPreferences.getAll();
		TreeMap<String, String> sortedPrefs = new TreeMap<String, String>();
		sortedPrefs.putAll(prefs);
		String key = sortedPrefs.keySet().toArray()[arg0].toString();
		
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put(key, sortedPrefs.get(key).toString());
		return hm;
	}

	@Override
	public long getItemId(int arg0) {		
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final TextView tv;
		
		HashMap<String, String> preference = (HashMap<String, String>)getItem(position);
		
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater)mContext.getSystemService
		      (Context.LAYOUT_INFLATER_SERVICE);			
			tv = (TextView) inflater.inflate(mResource, null);			
		} else {
			tv = (TextView) convertView;
		}		
		String key = preference.keySet().toArray()[0].toString();
		tv.setTag(key);
		tv.setText(key + ": " + preference.get(key));		
		return tv;
	}

}
