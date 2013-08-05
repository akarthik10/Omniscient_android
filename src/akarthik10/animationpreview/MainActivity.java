package akarthik10.animationpreview;

import java.util.Random;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	String welcome[]={"Welcome!", "Bonjour!", "Namaskara!", "Hola!", "Salut!", "Ciao!", "Guten Tag!"};
	String languages[]={"", "Bonjour (French): Hello", "Namaskara (Kannada): Greetings", "Hola (Spanish): Hello", "Salut (French): Hi", "Ciao (Italian): Greetings", "Guten Tag (German): Good Day"};
	String[] forgotname = new String[] {"C'mon, this is not a bug but I need your name.","Did you forgot to put cheese in your sandwich?", "Did you forgot to put food in the microwave?", "Wait! I must warn you for not letting me know your name.", "Pay no attention to the man behind the curtain, but I need your name.", "Why so serious? I just asked your name.", "I know you have a great name, but please let me know it.", "Err.. Your name please?", "Chill, I'm not asking your credit card details. Just your name."};
	String[] nameadj = new String[] {"Great name!", "Sweet name!", "Nice name!"};
	String username;
	int rando;
	SharedPreferences settings;
	SharedPreferences.Editor settingEditor;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);
        
    	//requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        Button nameButton = (Button) findViewById(R.id.buttonName);
        
        nameButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				EditText name = (EditText) findViewById(R.id.editTextName);
				username = name.getText().toString();
				if(username.equalsIgnoreCase(""))
				{
					Toast.makeText(MainActivity.this, forgotname[new Random().nextInt(forgotname.length)], Toast.LENGTH_LONG).show();
					return;
				}
				
				Toast.makeText(MainActivity.this, nameadj[new Random().nextInt(nameadj.length)], Toast.LENGTH_LONG).show();
				
				settingEditor.putString("username", username);
				settingEditor.commit();
				
				//setUp();
				Intent intent = new Intent(MainActivity.this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(intent);
				overridePendingTransition(R.anim.appear, R.anim.vanish);
				finish();
				
			}
		});
        
        settings=getSharedPreferences("ak10settings", Activity.MODE_PRIVATE);
        settingEditor =settings.edit();
        
        //settingEditor.putString("username", "");
        //settingEditor.commit();
        
        rando = new Random().nextInt(welcome.length);
        
        username = settings.getString("username", "");
        if(username=="")
        {
        
        
        TextView tv1 = (TextView) findViewById(R.id.textView1);
        tv1.setText("");
        final TextView mytext = (TextView) findViewById(R.id.mytext);
        rando = new Random().nextInt(welcome.length);
        mytext.setText(welcome[rando]);
        final Animation myanimation = AnimationUtils.loadAnimation(this, R.anim.myani);
        
       myanimation.setAnimationListener(new AnimationListener() {
		
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
			fade("Let me know your name");
		}
	});
       
       mytext.startAnimation(myanimation);
       if(!languages[rando].equalsIgnoreCase(""))
           Toast.makeText(MainActivity.this, languages[rando], Toast.LENGTH_SHORT).show();
       
        
        }
        else 
        {
        	setUp();
        	
        }
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void fade(final String s){
    	
    	final TextView mytext = (TextView) findViewById(R.id.mytext);
        final Animation myanimation = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        final Animation in =  AnimationUtils.loadAnimation(this, R.anim.fade_in);
        final Animation move_up =  AnimationUtils.loadAnimation(this, R.anim.move_up);
        final Animation showFields = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        myanimation.setAnimationListener(new AnimationListener() {
    		
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
    			mytext.setText(s);
    			mytext.setTextSize(25);
    			mytext.startAnimation(in);
    	    
    		}
    	});
        mytext.startAnimation(myanimation);
        
        
        in.setAnimationListener(new AnimationListener() {
			
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
				mytext.startAnimation(move_up);
				
			}
		});
        
        
        move_up.setAnimationListener(new AnimationListener() {
			
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
				
				EditText name = (EditText) findViewById(R.id.editTextName);
				Button buttonName = (Button) findViewById(R.id.buttonName);
				name.setVisibility(View.VISIBLE);
				buttonName.setVisibility(View.VISIBLE);
				name.startAnimation(showFields);
				buttonName.startAnimation(showFields);
				
			}
		});
        
        
        
    }
    
    
    public void setUp(){
    	EditText nameip = (EditText) findViewById(R.id.editTextName);
		Button buttonName = (Button) findViewById(R.id.buttonName);
		nameip.setVisibility(View.INVISIBLE);
		buttonName.setVisibility(View.INVISIBLE);
		final TextView mytext = (TextView) findViewById(R.id.mytext);
        mytext.setText(welcome[rando]);
		mytext.setVisibility(View.VISIBLE);
		mytext.setTextSize(33);
		final TextView myname = (TextView) findViewById(R.id.nameShow);
		myname.setTextSize(35);
		
		
		final Animation greet = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		final Animation namein = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		final Animation move_up = AnimationUtils.loadAnimation(this, R.anim.move_up);
		mytext.startAnimation(greet);
		
		if(!languages[rando].equalsIgnoreCase(""))
            Toast.makeText(MainActivity.this, languages[rando], Toast.LENGTH_SHORT).show();
		
		greet.setAnimationListener(new AnimationListener() {
			
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
				mytext.startAnimation(move_up);
			}
		});
		
		
		move_up.setAnimationListener(new AnimationListener() {
			
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
				myname.setVisibility(View.VISIBLE);
				myname.setText(username);
				myname.startAnimation(namein);
			}
		});
		
		
		namein.setAnimationListener(new AnimationListener() {
			
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
				Intent intent = new Intent(MainActivity.this, MainUI.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(intent);
				overridePendingTransition(R.anim.appear, R.anim.vanish);
				finish();
				
			}
		});
    }
    
}
