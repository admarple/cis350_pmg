/**
 * 
 */
package edu.upenn.cis350;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * @author Alex
 *
 */
public class NavigationActivity extends Activity {

	public static final int ACTIVITY_CreateNewMapActivity = 1;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav);
    }
	
	public void onInfoButtonClick(View v) {
		Intent i = new Intent(this, VisitorInfoActivity.class);
		startActivity(i);
	}
	
}
