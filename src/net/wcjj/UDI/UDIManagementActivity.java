package net.wcjj.UDI;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UDIManagementActivity extends Activity {

	
	@Override	
	protected void onCreate(Bundle icicle) {		
		super.onCreate(icicle);
		
		setContentView(R.layout.manage_input);		
		
		Button submit = (Button) findViewById(R.id.submit);
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
					AddStringPreference(name.getText().toString(), value.getText().toString());
					Toast.makeText(UDIManagementActivity.this, strName + ":" + strValue + " added successfully", Toast.LENGTH_SHORT).show();
					name.setText("");
					value.setText("");
				}
		    }			
			
			private void AddStringPreference(String key, String value) {
				int mode = 0;
				SharedPreferences prefs = getSharedPreferences(UDIService.UDI_PREFERENCE, mode);
				Editor e =  prefs.edit();
				e.putString(key, value);
				e.commit();
			}			
		});
		
		
	}
}
