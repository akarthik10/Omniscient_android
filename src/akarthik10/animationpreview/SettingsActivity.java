package akarthik10.animationpreview;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends Activity {

	SharedPreferences settings;
	SharedPreferences.Editor settingEditor;
	String username;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_settings);
		settings=getSharedPreferences("ak10settings", Activity.MODE_PRIVATE);
		username = settings.getString("username", "");
		final TextView uname = (TextView) findViewById(R.id.rowSetting);
		uname.setText(username);
		uname.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alert = new AlertDialog.Builder(SettingsActivity.this);
				alert.setTitle("Enter your name:");
				alert.setMessage("Please enter or modify your name");
				final EditText input = new EditText(SettingsActivity.this);
				input.setText(username);
				alert.setView(input);
				alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						String value = input.getText().toString();
						if(value.equalsIgnoreCase("")){
							Toast.makeText(getApplicationContext(), "You forgot something.", Toast.LENGTH_SHORT).show();	
						}
						else{
					  	settingEditor =settings.edit();
					  	settingEditor.putString("username", value);
				        settingEditor.commit();
				        uname.setText(value);
						}
					  }
					});
				
				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					  public void onClick(DialogInterface dialog, int whichButton) {
					    // Canceled.
						  dialog.dismiss();
					  }
					});

					alert.show();

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_settings, menu);
		return true;
	}

}
