package akarthik10.animationpreview;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;





public class MainUI extends Activity {

	boolean restored=true;
	EditText input;
	Button submit;
	ProgressDialog progress;
	
	String[] wait = new String[] {"Hang on a sec, I know your answer is here somewhere..", "A few bits tried to escape, but we caught them and they're being sent..", "The server is powered by a lemon and two electrodes, so you'll have to wait a while..", "So, why don't you smile once while we're loading?", "Loading will complete before you find a prime number greater than 32133402514263054457", "Working... no, just kidding", "Working... So, how are you?", "QUIET !!! I'm trying to think here", "Counting backwards from infinity..", "..and you're in a maze of twisty loading screens.", "Waiting for the pigeon to deliver your message..", "I found a typo! Sending.. no,just kidding.", "Making cookies for you..", "Go, get a coffee cup or something..", "Make a Maggi and I'll be ready.", "Loading the loading message..", "I know this is boring, but I'll have to load this.", "Loading completes after I compute 3 x 4..", "Start praying that this ends soon..", "Loading completes when this circle stops rotating."};
	String[] forgotquery = new String[] {"C'mon, this is not a bug but you have to ask me something.","Did you forget to put cheese in your sandwich?", "Did you forget to put food in the microwave?", "Wait! I must warn you for asking me nothing.", "Pay no attention to the man behind the curtain, but you can't ask me nothing.", "Why so serious? I just want you to ask me something.", "Smile once and type a query.", "Err.. Your query please?"};
	AsyncTask<String, Void, String> netMgr;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_ui);
		submit=(Button) findViewById(R.id.buttonSubmit);
		
		progress = new ProgressDialog(this);
		//progress.setCancelable(true);
		progress.setTitle("Please wait..");
		//progress.setMessage(wait[new Random().nextInt(wait.length)]);
		progress.setOnCancelListener(new DialogInterface.OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface arg0) {
				// TODO Auto-generated method stub
				arg0.dismiss();
				netMgr.cancel(true);
				if(!restored){
					restoreState(0);
				}
			}
		});
		
		
		
		submit.setOnClickListener(new View.OnClickListener() {
			String query;	
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				progress.setMessage(wait[new Random().nextInt(wait.length)]);
				
				
				if(submit.getText()!="Cancel"){
				input = (EditText) findViewById(R.id.inputQuery);
				
				if(input.getText().toString().equalsIgnoreCase("")){
					
					Toast.makeText(MainUI.this, /*FORGOT*/ forgotquery[new Random().nextInt(forgotquery.length)], Toast.LENGTH_LONG).show();
					return;
				}
				
				try {
					query = URLEncoder.encode(input.getText().toString(), "utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				netMgr = new HTTPRequest(MainUI.this).execute("http://api.wolframalpha.com/v2/query?input="+query+"&appid=Y47X4R-X4G9JTT33W");
				//netMgr = new HTTPRequest(MainUI.this).execute("http://api.wolframalpha.com/v2/query?input="+query);
				
				
				startLoading();
				
				//progress.show();
				//restored=false;
						
				
				}
				else{
					restoreState(0);
					restored=true;
				}
				
				
				
			}
		});
		
		
		
		
		TextView textExamples = (TextView) findViewById(R.id.textExamples);
		textExamples.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				//INTENT
				//Toast.makeText(getApplicationContext(), "Loading examples..", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(MainUI.this, ExamplesList.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivityForResult(intent,0);
				overridePendingTransition(R.anim.appear, R.anim.vanish);
				
				
			}
		});
		
		
		TextView settings = (TextView) findViewById(R.id.settings);
		settings.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainUI.this, SettingsActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(intent);
				overridePendingTransition(R.anim.appear, R.anim.vanish);
			}
		});
		
		TextView about = (TextView) findViewById(R.id.about);
		about.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainUI.this, AboutActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(intent);
				overridePendingTransition(R.anim.appear, R.anim.vanish);
			}
		});
		
	}
	
	
	
	public void displayResult(ArrayList<String> list){
		
		
	    
		 Intent intent = new Intent(MainUI.this, ResultActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			intent.putExtra("response_array", list);
			startActivityForResult(intent,0);
			overridePendingTransition(R.anim.appear, R.anim.vanish);
	}
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main_ui, menu);
		return true;
	}
	
	
	public void startLoading(){
		final TextView textExamples;
		final TextView textExamplesBar;
		final TextView settings;
		final TextView settingsBar;
		final TextView about;
		final TextView aboutBar;
		textExamples = (TextView) findViewById(R.id.textExamples);
		textExamplesBar = (TextView) findViewById(R.id.textExamplesBar);
		settings = (TextView) findViewById(R.id.settings);
		settingsBar = (TextView) findViewById(R.id.settingsBar);
		about = (TextView) findViewById(R.id.about);
		aboutBar = (TextView) findViewById(R.id.aboutBar);
		Button submit = (Button) findViewById(R.id.buttonSubmit);
		submit.setText("Submitting..");
		final Animation myanimation = AnimationUtils.loadAnimation(MainUI.this, R.anim.vanish);
		final Animation appearAni = AnimationUtils.loadAnimation(MainUI.this, R.anim.appear);
		final Animation vanishDown = AnimationUtils.loadAnimation(MainUI.this, R.anim.vanish_down);
		final Animation vanishDownFinal = AnimationUtils.loadAnimation(MainUI.this, R.anim.vanish_down);
		
		
		textExamplesBar.startAnimation(vanishDownFinal);
		settingsBar.startAnimation(vanishDown);
		aboutBar.startAnimation(vanishDown);
		
		vanishDownFinal.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				
				progress.show();
				restored=false;
			}
		});
		
		textExamples.startAnimation(myanimation);
		settings.startAnimation(myanimation);
		about.startAnimation(myanimation);
		
		

		
		
		//Button submit = (Button)findViewById(R.id.buttonSubmit);
		//submit.setText("Cancel");
		
		
		
		
	}
	
	
	public void restoreState(int i){
		
		final TextView textExamples;
		final TextView textExamplesBar;
		final TextView settings;
		final TextView settingsBar;
		final TextView about;
		final TextView aboutBar;
		EditText ipbox;
		ipbox = (EditText) findViewById(R.id.inputQuery);
		if(i==1)
		ipbox.setText("");
		Button submit = (Button) findViewById(R.id.buttonSubmit);
		submit.setText("Submit");
		textExamples = (TextView) findViewById(R.id.textExamples);
		textExamplesBar = (TextView) findViewById(R.id.textExamplesBar);
		settings = (TextView) findViewById(R.id.settings);
		settingsBar = (TextView) findViewById(R.id.settingsBar);
		about = (TextView) findViewById(R.id.about);
		aboutBar = (TextView) findViewById(R.id.aboutBar);
		final Animation myanimation = AnimationUtils.loadAnimation(MainUI.this, R.anim.appear);
		final Animation appearDown = AnimationUtils.loadAnimation(MainUI.this, R.anim.appear_down);
		
		
		textExamplesBar.startAnimation(appearDown);
		settingsBar.startAnimation(appearDown);
		aboutBar.startAnimation(appearDown);
		
		
		textExamples.startAnimation(myanimation);
		settings.startAnimation(myanimation);
		about.startAnimation(myanimation);

		//Button submit = (Button)findViewById(R.id.buttonSubmit);
		//submit.setText("Submit");
		
		
	}








	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode==1){
			String example = data.getStringExtra("example");
			EditText inputtext = (EditText) findViewById(R.id.inputQuery); 
				inputtext.setText(example);
			Button sbmit = (Button) findViewById(R.id.buttonSubmit);
			sbmit.performClick();
			
			
		}
		else if(resultCode==2){
			restoreState(1);
		}
		
			
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}









