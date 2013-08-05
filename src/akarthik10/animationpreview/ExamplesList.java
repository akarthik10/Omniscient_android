package akarthik10.animationpreview;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ExamplesList extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_examples_list);
		populateList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_examples_list, menu);
		return true;
	}
	
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		overridePendingTransition(R.anim.appear, R.anim.vanish);
	}

	public void populateList()
	{
		
		 final ListView listview = (ListView) findViewById(R.id.listview);
		    final String[] values = new String[] { "When was Nehru born?", "iron man 3 release date", "usain bolt height","Fast and Furious 6",
		        "sin 15 degree", "distance between bangalore and mysore", "500 MB download at 100 kBps", "father's mother's sister's son", "sierpinski gasket",
		        "factor 2x^5 - 19x^4 + 58x^3 - 67x^2 + 56x - 48", "derivative of x^4 sin x", "Oscar for best actress 2013", "120 meters", "10 miles + 14 kilometers", "How old was Queen Elizabeth II in 1974?",
		        "C6H5COOH", "trimethylamine gas", "work F=30N, d=100m", "centripetal acceleration, 30mph, 500 ft", "Joules law u=3V, R=1ohm for 10s", "1980 Nobel Prize in Chemistry",
		        "third Tuesday in September", "Which months have 31 days?", "define triangulate", ".pdf file format", "32 coin tosses" };
		   
		    String[] values2 = new String[] { "1", "2", "3", "4", "5", "6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23" };
		    
		   // ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
		   // listview.setAdapter(arrayAdapter);
		    
		   
		    final ExamplesAdapter adapter = new ExamplesAdapter(this, values);
		    listview.setAdapter(adapter);

		    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		      @Override
		      public void onItemClick(AdapterView<?> parent, final View view,
		          final int position, long id) {
		        final String item = (String) parent.getItemAtPosition(position);
		        
		        final Animation barmove = AnimationUtils.loadAnimation(ExamplesList.this, R.anim.bar_move);
		        final Animation barreset = AnimationUtils.loadAnimation(ExamplesList.this, R.anim.bar_reset);
		        final TextView bar = (TextView) view.findViewById(R.id.rowBar);
		        
		       
		        bar.startAnimation(barmove);
		        barmove.setAnimationListener(new AnimationListener() {
					
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
						//Toast.makeText(getApplicationContext(), "Loading example..", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent();
						intent.putExtra("example", values[position]);
						setResult(1, intent);
						finish();
					}
				});

		      
                
		        
		      }

		    });
		
	}
	

	private class ExamplesAdapter extends ArrayAdapter<String> {
		  private final Context context;
		  private final String[] values;

		  public ExamplesAdapter(Context context, String[] values) {
		    super(context, R.layout.row, values);
		    this.context = context;
		    this.values = values;
		  }

		  @Override
		  public View getView(int position, View convertView, ViewGroup parent) {
		    LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    View rowView = inflater.inflate(R.layout.row, parent, false);
		    TextView abstractor = (TextView) rowView.findViewById(R.id.rowData);
		    
		    abstractor.setText(values[position]);
		    // Change the icon for Windows and iPhone
		    

		    return rowView;
		  }
		} 
	

}
