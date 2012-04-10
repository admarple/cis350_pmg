/**
 * 
 */
package edu.upenn.cis350;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author Alex Marple
 *
 */
public class VisitorInfoActivity extends Activity {
	
	public final String VISITOR_INFO_SECTION_KEY = "visitorInfoSection";
	
	private int[] layouts = {
			R.layout.visitor_info,
			R.layout.contact,
			R.layout.hours,
			R.layout.admissions,
			R.layout.policy,
			R.layout.parking,
			R.layout.accessibility,
	};
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitor_info);
        
        int sn = getIntent().getIntExtra(VISITOR_INFO_SECTION_KEY, 0);
        setSection(sn);
    }
    
    private void setSection(int sectionNumber) {
    	setContentView(layouts[sectionNumber]);
    }

}
