package akarthik10.animationpreview;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends Activity {
	ArrayList<String> response;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i = getIntent();  
		response = i.getStringArrayListExtra("response_array");
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_result);
		
		
		
		//Toast.makeText(this, response.size()+"" , Toast.LENGTH_SHORT).show();
		
		populateList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_result, menu);
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        // your code
	    	Intent intent = new Intent();
			setResult(2, intent);
			finish();
	    }

	    return super.onKeyDown(keyCode, event);
	}
	
	
	public void populateList()
	{
		
		 final ListView listview = (ListView) findViewById(R.id.resview);
		    
		   	
		 	
		 	String[] respArray = new String[response.size()];
		 	for (int i = 0; i < response.size(); i++) {
		 	    respArray[i] = response.get(i);  
		 	   
		 	}
		 	
		 	
		    final ExamplesAdapter adapter = new ExamplesAdapter(this, respArray);
		    listview.setAdapter(adapter);

		    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		      @Override
		      public void onItemClick(AdapterView<?> parent, final View view,
		          final int position, long id) {
		        final String item = (String) parent.getItemAtPosition(position);
		        
		       		      
                
		        
		      }

		    });
		
	}
	
	private class ExamplesAdapter extends ArrayAdapter<String> {
		  private final Context context;
		  private final String[] values;

		  public ExamplesAdapter(Context context, String[] values) {
		    super(context, R.layout.rowresult, values);
		    this.context = context;
		    this.values = values;
		  }

		  @Override
		  public View getView(int position, View convertView, ViewGroup parent) {
		    LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    View rowView = inflater.inflate(R.layout.rowresult, parent, false);
		    TextView abstractor = (TextView) rowView.findViewById(R.id.rowData);
		    TextView rowbar = (TextView) rowView.findViewById(R.id.rowBar);
		    ImageView imageRes = (ImageView) rowView.findViewById(R.id.imageResult);
		    if(position%2==0){
		    	abstractor.setBackgroundResource(R.color.grey);
		    	abstractor.setTextColor(getResources().getColor(R.color.white));
		    	abstractor.setText(values[position]);
		    	
		    }
		    else{
		    	rowbar.setVisibility(View.INVISIBLE);
		    	abstractor.setText(values[position]);
		    	if(values[position].startsWith("http")){
		    		imageRes.setVisibility(View.VISIBLE);
		    		abstractor.setText("Loading Image..");
		    		//imageRes.setTag((String)values[position]);
		    		/*Bitmap bimage=  getBitmapFromURL(values[position]);
		    		imageRes.setImageBitmap(bimage);*/
		    		//Toast.makeText(getApplicationContext(), "Image:"+values[position], Toast.LENGTH_SHORT).show();
		    		new DownloadImageTask(imageRes, abstractor).execute(values[position].replace("&amp;", "&"));
		    		
		            
		    		
		    		
		    		
		    	}
		    }
		    
		    // Change the icon for Windows and iPhone
		    

		    return rowView;
		  }
		} 
	
	public Bitmap getBitmapFromURL(String src) {
        try {
            //Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
           // Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
           // Log.e("Exception",e.getMessage());
            return null;
        }
    }
	
	
	Drawable drawable_from_url(String url, String src_name) throws 
	   java.net.MalformedURLException, java.io.IOException 
	{
	   return Drawable.createFromStream(((java.io.InputStream)
	      new java.net.URL(url).getContent()), src_name);
	}
	
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;
	    TextView mTextView;
	    public DownloadImageTask(ImageView bmImage, TextView mTextView) {
	        this.bmImage = bmImage;
	        this.mTextView = mTextView;
	    }

	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            Log.e("ErrorImage", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {
	        mTextView.setVisibility(View.GONE);
	    	bmImage.setImageBitmap(result);
	        
	    }
	}
	

}
