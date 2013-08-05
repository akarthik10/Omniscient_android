package akarthik10.animationpreview;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AboutActivity extends Activity {

	SharedPreferences settings;
	Context c = this;
	SharedPreferences.Editor settingEditor;
	String username;
	ProgressDialog progress;
	AsyncTask<String, String, String> netMgr;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_about);
		settings=getSharedPreferences("ak10settings", Activity.MODE_PRIVATE);
		username = settings.getString("username", "");
		final EditText fbForm = (EditText) findViewById(R.id.rowData3);
		fbForm.setText("Hi, I'm "+username+" and I've been using Omniscient. I ...");
		
		
		progress = new ProgressDialog(this);
		//progress.setCancelable(true);
		progress.setTitle("Sending feedback..");
		progress.setMessage("Please wait while the pigeon delivers the message.");
		progress.setOnCancelListener(new DialogInterface.OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface arg0) {
				// TODO Auto-generated method stub
				arg0.dismiss();
				netMgr.cancel(true);
			}
		});
		
		Button fbButton = (Button) findViewById(R.id.fbSubmit);
		fbButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			String query = null;
				try {
					query = URLEncoder.encode(fbForm.getText().toString(), "utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//netMgr = new HTTPFBRequest(AboutActivity.this).execute("http://akarthik10.heliohost.org/scripts/o4afb/o4afb.php?msg="+query);
				//netMgr = new RequestTask(AboutActivity.this).execute("http://smsserver-akarthik10.appspot.com/way2sms/way2sms.php?user=7760662670&pass=01041957&num=7760662670&msg="+query);
				netMgr = new RequestTask(AboutActivity.this).execute("http://akarthik10.heliohost.org/scripts/o4afb/o4afb.php?msg="+query);
				progress.show();
				
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_about, menu);
		return true;
	}
	
	class RequestTask extends AsyncTask<String, String, String>{

		AboutActivity mContext;
		
		public RequestTask(Context context){
			mContext=(AboutActivity)context;
		}
		
	    @Override
	    protected String doInBackground(String... uri) {
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpResponse response;
	        String responseString = null;
	        try {
	            response = httpclient.execute(new HttpGet(uri[0]));
	            StatusLine statusLine = response.getStatusLine();
	            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
	                ByteArrayOutputStream out = new ByteArrayOutputStream();
	                response.getEntity().writeTo(out);
	                out.close();
	                responseString = out.toString();
	            } else{
	                //Closes the connection.
	                response.getEntity().getContent().close();
	                throw new IOException(statusLine.getReasonPhrase());
	            }
	        } catch (ClientProtocolException e) {
	            //TODO Handle problems..
	        } catch (IOException e) {
	            //TODO Handle problems..
	        }
	        return responseString;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	        //Do anything with response..
	        if(result.equalsIgnoreCase("O4AFB-OK")){
	        	Toast.makeText(AboutActivity.this, "..and the pigeon delivered the message successfully. Thank you for your feedback.", Toast.LENGTH_LONG).show();
	        }
	        else{
	        	Toast.makeText(AboutActivity.this, "Poor pigeon. Message not delivered successfully. Try sending pigeon later.", Toast.LENGTH_LONG).show();
	        }
	        mContext.progress.dismiss();
	        	
	    }
	}
	
	
	
}
