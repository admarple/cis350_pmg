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

public class GlossaryActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScrollView scroll = new ScrollView(this);
		scroll.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		LinearLayout layout = new LinearLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		layout.setOrientation(LinearLayout.VERTICAL);
		
        String[] terms = getResources().getStringArray(R.array.glossary_term);
        String[] defs = getResources().getStringArray(R.array.glossary_def);
        int numTerms = terms.length;
        
        for (int i=0; i < numTerms; i++) {
        	TextView termText = new TextView(this);
        	termText.setTextSize(22);
        	termText.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
        	termText.setText(terms[i]);
            layout.addView(termText);
            
            TextView defText = new TextView(this);
            defText.setTextSize(14);
            defText.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
            defText.setText(defs[i]);
            layout.addView(defText);
        } 
        scroll.addView(layout);
        super.setContentView(scroll);
        
        // does this help garbage collection???  I don't know, but we'll see
        scroll = null;
        layout = null;
    }
	
}
