/**
 * 
 */
package edu.upenn.cis350;

import android.app.Activity;
import android.os.Bundle;

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
	
}
