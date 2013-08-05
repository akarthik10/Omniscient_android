package akarthik10.animationpreview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.app.backup.RestoreObserver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HTTPRequest extends AsyncTask<String, Void, String> {
  
	MainUI mContext;
	
	public HTTPRequest(Context context) {
		// TODO Auto-generated constructor stub
		this.mContext=(MainUI) context;
	}
	
	
	
	
	
	
	
	@Override
    protected String doInBackground(String... urls) {
		
		
		
		StringBuilder total = new StringBuilder();
      String response = "";
      for (String url : urls) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
          HttpResponse execute = client.execute(httpGet);
          InputStream content = execute.getEntity().getContent();

          BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
          String s = "";
          while ((s = buffer.readLine()) != null) {
           response += (s+"\n");
        	 
        	  
          }

        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      String r= response.replace("&amp;", "&").replace("&apos;", "'").replace("&quot;", "\"").replace("&lt;", "<").replace("&gt;", ">").replace("\n", "\n\n");
      Log.d("", r);
      return r;
      
      
    }

    @Override
    protected void onPostExecute(String result) {
    	if(mContext.restored){
    		return;
    	}
    	//Log.d("ANI", result);
    
    	
    	
    	
    	
    	
    	
    	
    	Pattern p = Pattern.compile("<pod title='(.*?)'\\s*.*?>\\s*<subpod.*?>\\s*<plaintext>(.*?)</plaintext>\\s*(?:<img src='(.*?)')?", Pattern.DOTALL);
    	ArrayList<String> list = new ArrayList<String>();
    	Matcher m = p.matcher(result);
    	Log.d("ANI", ""+m.groupCount());
    	//Toast.makeText(mContext, m.groupCount()+"", Toast.LENGTH_LONG).show();
    	 while (m.find()) { 
    	     //String name = m.group(1).toString(); 
    	     //Log.d("ANI", ""+m.start());
    	     //Log.d("ANI", ""+m.end());
    		 //for(int i=1;i<=m.groupCount();i++)
    		 //{
    			 
    			 list.add(m.group(1).toString());
    			 if(m.group(2).toString().equalsIgnoreCase("") && m.group(3).toString().startsWith("http"))
			      list.add(m.group(3).toString());
    			 else
    				 list.add(m.group(2).toString()); 
    			
    				
    	     //Toast.makeText(mContext, m.group(i).toString(), Toast.LENGTH_LONG).show();
    		 //}
    	 }
    	 if(list.size()==0){
    		 list.add("No result found :(");
    		 list.add("Did you ask \"Who is my best friend?\"");
    	 }
    	
	mContext.displayResult(list);		
    mContext.progress.dismiss();
    //mContext.restoreState();
      
    }
    
  
    
    



  }