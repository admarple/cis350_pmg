/**
 * 
 */
package edu.upenn.cis350;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * @author Alex Marple
 *
 */
public class VisitorInfoActivity extends Activity {
	
	public static final String VISITOR_INFO_SECTION_KEY = "visitorInfoSection";
	public static final int VISITOR_INFO_CODE = 0;
	public static final int CONTACT_CODE = 1;
	public static final int HOURS_CODE = 2;
	public static final int ADMISSIONS_CODE = 3;
	public static final int POLICY_CODE = 4;
	public static final int PARKING_CODE = 5;
	public static final int ACCESSIBILITY_CODE = 6;
	final Activity pushThis = this;
	
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
        
        int sn = getIntent().getIntExtra(VISITOR_INFO_SECTION_KEY, 0);
        setSection(sn);
        if (sn == 0) {
        	Button contactButton = (Button) findViewById(R.id.contact_button);
            contactButton.setOnClickListener(new View.OnClickListener() {
            	public void onClick(View view) {
            		Intent i = new Intent(pushThis, VisitorInfoActivity.class);
            		i.putExtra(VISITOR_INFO_SECTION_KEY, CONTACT_CODE);
            		startActivity(i);
            	}
            });
            
            Button hoursButton = (Button) findViewById(R.id.hours_button);
            hoursButton.setOnClickListener(new View.OnClickListener() {
            	public void onClick(View view) {
            		Intent i = new Intent(pushThis, VisitorInfoActivity.class);
            		i.putExtra(VISITOR_INFO_SECTION_KEY, HOURS_CODE);
            		startActivity(i);
            	}
            });
            
            Button admissionsButton = (Button) findViewById(R.id.admission_button);
            admissionsButton.setOnClickListener(new View.OnClickListener() {
            	public void onClick(View view) {
            		Intent i = new Intent(pushThis, VisitorInfoActivity.class);
            		i.putExtra(VISITOR_INFO_SECTION_KEY, ADMISSIONS_CODE);
            		startActivity(i);
            	}
            });
            
            Button policyButton = (Button) findViewById(R.id.policy_button);
            policyButton.setOnClickListener(new View.OnClickListener() {
            	public void onClick(View view) {
            		Intent i = new Intent(pushThis, VisitorInfoActivity.class);
            		i.putExtra(VISITOR_INFO_SECTION_KEY, POLICY_CODE);
            		startActivity(i);
            	}
            });
            
            Button parkingButton = (Button) findViewById(R.id.parking_button);
            parkingButton.setOnClickListener(new View.OnClickListener() {
            	public void onClick(View view) {
            		Intent i = new Intent(pushThis, VisitorInfoActivity.class);
            		i.putExtra(VISITOR_INFO_SECTION_KEY, PARKING_CODE);
            		startActivity(i);
            	}
            });
            
            Button accessibilityButton = (Button) findViewById(R.id.accessibility_button);
            accessibilityButton.setOnClickListener(new View.OnClickListener() {
            	public void onClick(View view) {
            		Intent i = new Intent(pushThis, VisitorInfoActivity.class);
            		i.putExtra(VISITOR_INFO_SECTION_KEY, ACCESSIBILITY_CODE);
            		startActivity(i);
            	}
            });
        }
    }
    
    private void setSection(int sectionNumber) {
    	setContentView(layouts[sectionNumber]);
    }

}
