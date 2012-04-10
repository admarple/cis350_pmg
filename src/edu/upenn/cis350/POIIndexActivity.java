package edu.upenn.cis350;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class POIIndexActivity extends Activity {
	
	final Activity pushThis = this;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ScrollView scroll = new ScrollView(this);
		scroll.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		LinearLayout layout = new LinearLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		layout.setOrientation(LinearLayout.VERTICAL);

		// LinearLayout layout = (LinearLayout) findViewById(R.id.poi_layout);
		
        String[] poiObject = getResources().getStringArray(R.array.poi_theme);
        int numPOIs = poiObject.length;
        
        for (int i=0; i < numPOIs; i++) {
        	final int j = i;
            Button b = new Button (this);
            b.setText(poiObject[i]);
            b.setOnClickListener(new View.OnClickListener() {
        		public void onClick(View view) {
        			Intent intent = new Intent(pushThis, POIActivity.class);
        			intent.putExtra(POIActivity.POI_CODE_KEY, j);
        			startActivity(intent);
        		}
        	});
            layout.addView(b);
        } 
        scroll.addView(layout);
        super.setContentView(scroll);
        
        // does this help garbage collection???  I don't know, but we'll see
        scroll = null;
        layout = null;
	}
	
}
