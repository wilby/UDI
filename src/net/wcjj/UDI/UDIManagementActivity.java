package net.wcjj.UDI;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class UDIManagementActivity extends Activity {

	private Boolean mIsVerified = false;
	private PreferencesToTextViewAdapter mAdapter;
	
	@Override	
	protected void onCreate(Bundle icicle) {		
		super.onCreate(icicle);
		
		setContentView(R.layout.manage_input);		
		SetupAddDefinitionButton();
		try {
			SetupListView();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void SetupListView() throws FileNotFoundException {
		final ListView lv = (ListView)findViewById(R.id.lvItemsToDelete);
		mAdapter = new PreferencesToTextViewAdapter(this, R.layout.input, UDIService.UDI_PREFERENCE);		
		lv.setAdapter(mAdapter);		
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position,
					long id) {
				
				MessageBoxYesNo(getString(R.string.confirm_delete_dialog_title), getString(R.string.confirm_delete_dialog_message), (TextView)v);				
			}
			
					
		});
		
	}
	
	private void DeleteStringPreference(String key) {
		int mode = 0;
		SharedPreferences prefs = getSharedPreferences(UDIService.UDI_PREFERENCE, mode);
		Editor e =  prefs.edit();				
		e.remove(key);
		e.remove("");
		e.remove(" ");
		e.commit();
	}	
	
	private void MessageBoxYesNo(String dialogTitle, String dialogMessage, TextView v) {
		
		Context context = UDIManagementActivity.this;				
		AlertDialog.Builder ad = new AlertDialog.Builder(context);
		final TextView view = v;		
		
		ad.setTitle(dialogTitle);
		ad.setMessage(dialogMessage);
		ad.setPositiveButton(getString(R.string.dialog_yes), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {													
					DeleteStringPreference(view.getTag().toString());
					mAdapter.notifyDataSetInvalidated();					
			}
		});
		
		ad.setNegativeButton(getString(R.string.dialog_no), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				mIsVerified = false;						
			}
		});
			
		
		ad.setCancelable(true);
		ad.setOnCancelListener(new OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				mIsVerified = false;
				}
			});
		
		ad.show();
	}

	private void SetupAddDefinitionButton() {
		Button submit = (Button)findViewById(R.id.btnSubmit);
		submit.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				
				EditText name = (EditText)findViewById(R.id.pref_name);
				EditText value = (EditText)findViewById(R.id.pref_value);
				String strName = name.getText().toString();
				String strValue = value.getText().toString();
				
				if(name.getText().length() == 0){
					Toast.makeText(UDIManagementActivity.this, "Provide a name", Toast.LENGTH_LONG).show();				
				}
				else if(value.getText().length() == 0) {
					Toast.makeText(UDIManagementActivity.this, "Provide a value", Toast.LENGTH_SHORT).show();					
				} else {
					String successText = "";
					Boolean hasDefinition = AddStringPreference(name.getText().toString(), value.getText().toString());
					if(hasDefinition) {
						successText = getString(R.string.management_changed);
					} else {						
						successText = getString(R.string.management_added);
					}					
					Toast.makeText(UDIManagementActivity.this, strName + ":" + strValue + " " + successText, Toast.LENGTH_SHORT).show();
					name.setText("");
					value.setText("");
					mAdapter.notifyDataSetInvalidated();
				}
		    }			
			
			private Boolean AddStringPreference(String key, String value) {
				int mode = 0;
				SharedPreferences prefs = getSharedPreferences(UDIService.UDI_PREFERENCE, mode);
				String v = value.trim();
				String k = key.trim();
				Editor e =  prefs.edit();
				Boolean hasDefinition = prefs.contains(k);
				e.putString(k,v);
				e.remove("");
				e.remove(" ");
				e.remove("\t");
				e.commit();
				return hasDefinition;
			}			
			
		});	
		
	}
}
