package net.wcjj.UDI;

import java.io.FileNotFoundException;

import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;




public class UDIService extends InputMethodService {

	private LinearLayout mInputView;
	private PreferencesToTextViewAdapter mAdapter;	 
	
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
		
		/*Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();		
		int height = display.getHeight();*/
		
		mInputView = (LinearLayout)inflater.inflate(R.layout.input_method_service_view, null);
		/*LinearLayout.LayoutParams params = (LayoutParams) mInputView.getLayoutParams();
		if(params == null) 
			params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
		
		params.height = (height / 2);
		mInputView.setLayoutParams(params);*/
		
		ListView lv = (ListView)mInputView.findViewById(R.id.lvInputMethod);	
		lv.setAdapter(mAdapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {		
				  TextView tv = (TextView)view;
				  InputConnection ic = getCurrentInputConnection();
				  ic.commitText(((TextView) view).getText(), 1);			      
			}	
		});
		
		final UDIService currentService = this;
		Button btnSettings = (Button)mInputView.findViewById(R.id.btnSettings);
		btnSettings.setOnClickListener(new OnClickListener() {	    
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(),UDIManagementActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				currentService.hideWindow();
				startActivity(i);
			}
		});
	}
	
}
