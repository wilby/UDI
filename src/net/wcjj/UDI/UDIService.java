package net.wcjj.UDI;

import java.io.FileNotFoundException;
import java.util.zip.Inflater;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;




public class UDIService extends InputMethodService {

	LinearLayout mInputView;
	PreferencesToTextViewAdapter mAdapter;
	
	public final static String UDI_PREFERENCE = "USER_DEFINED_INPUT_PREFERENCES";
	
	@Override
	public void onCreate() 
	{
		super.onCreate();
	}
	
	
	@Override 
	public View onCreateInputView() {
		return mInputView;
    }
	
	@Override
	public View onCreateCandidatesView() {	
		return null;
	}
	
	@Override
	public void onInitializeInterface() {
		try {
			mAdapter =  new PreferencesToTextViewAdapter(this, R.layout.input, UDI_PREFERENCE);			
		} catch (FileNotFoundException e) {
			Log.e("UDIService", "Getting the preferences file returned exception", e);
		}
	
		LayoutInflater inflater = (LayoutInflater)this.getSystemService
	      (Context.LAYOUT_INFLATER_SERVICE);
		
		mInputView = (LinearLayout)inflater.inflate(R.layout.input_method_service_view, null);
		ListView lv = (ListView)mInputView.findViewWithTag("lv");
		
		final Context innerContext = this;
		
		lv.setAdapter(mAdapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {					
			      Toast.makeText(innerContext, ((TextView) view).getText(),
			          Toast.LENGTH_SHORT).show();
			}
				
		});
	}
	
}
