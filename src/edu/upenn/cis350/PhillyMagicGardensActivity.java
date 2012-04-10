package edu.upenn.cis350;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class PhillyMagicGardensActivity extends Activity {
	
	ViewFlipper flipper;
	// This is an ugly, ugly thing to do, but it will have to wait to be fixed
	final Activity pushThis = this;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        flipper = (ViewFlipper) findViewById(R.id.flipper);
        ImageView splash = (ImageView) findViewById(R.id.hello_image);
        
        splash.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		flipper.setInAnimation(inFromBottomAnimation());
        		flipper.setOutAnimation(outToTopAnimation());
        		flipper.showNext();
        	}
        });
        
        Button gardenMapButton = (Button) findViewById(R.id.garden_map_button);
        gardenMapButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		Intent i = new Intent(pushThis, MapActivity.class);
        		i.putExtra(MapActivity.MAP_CODE_KEY, MapActivity.EXTERIOR_CODE);
        		startActivity(i);
        	}
        });
        
        Button studioMapButton = (Button) findViewById(R.id.studio_map_button);
        studioMapButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		Intent i = new Intent(pushThis, MapActivity.class);
        		i.putExtra(MapActivity.MAP_CODE_KEY, MapActivity.STUDIO_CODE);
        		startActivity(i);
        	}
        });
        
        Button indoorsMapButton = (Button) findViewById(R.id.indoors_map_button);
        indoorsMapButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		Intent i = new Intent(pushThis, MapActivity.class);
        		i.putExtra(MapActivity.MAP_CODE_KEY, MapActivity.INDOORS_CODE);
        		startActivity(i);
        	}
        });
        
        Button basementMapButton = (Button) findViewById(R.id.basement_map_button);
        basementMapButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		Intent i = new Intent(pushThis, MapActivity.class);
        		i.putExtra(MapActivity.MAP_CODE_KEY, MapActivity.BASEMENT_CODE);
        		startActivity(i);
        	}
        });
        
        Button infoButton = (Button) findViewById(R.id.info_button);
        infoButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		Intent i = new Intent(pushThis, VisitorInfoActivity.class);
        		startActivity(i);
        	}
        });
        
        Button poiIndexButton = (Button) findViewById(R.id.poi_index_button);
        poiIndexButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		Intent i = new Intent(pushThis, POIIndexActivity.class);
        		startActivity(i);
        	}
        });
        
        Button glossaryButton = (Button) findViewById(R.id.glossary_button);
        glossaryButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		Intent i = new Intent(pushThis, GlossaryActivity.class);
        		startActivity(i);
        	}
        });
    }

    private Animation inFromBottomAnimation() {
    	Animation inFromRight = new TranslateAnimation(
    			Animation.RELATIVE_TO_PARENT,   0.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
    			Animation.RELATIVE_TO_PARENT,  +1.0f, Animation.RELATIVE_TO_PARENT,  0.0f
    			);
    	inFromRight.setDuration(1000);
    	inFromRight.setInterpolator(new AccelerateInterpolator());
    	return inFromRight;
    }
    private Animation outToTopAnimation() {
    	Animation outtoLeft = new TranslateAnimation(
    			Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f,
    			Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,  -1.0f
    			);
    	outtoLeft.setDuration(1000);
    	outtoLeft.setInterpolator(new AccelerateInterpolator());
    	return outtoLeft;
    }
    /*
    private Animation inFromTopAnimation() {
    	Animation inFromLeft = new TranslateAnimation(
    			Animation.RELATIVE_TO_PARENT,   0.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
    			Animation.RELATIVE_TO_PARENT,  -1.0f, Animation.RELATIVE_TO_PARENT,  0.0f
    			);
    	inFromLeft.setDuration(500);
    	inFromLeft.setInterpolator(new AccelerateInterpolator());
    	return inFromLeft;
    }
    private Animation outToBottomAnimation() {
    	Animation outtoRight = new TranslateAnimation(
    			Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f,
    			Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,  +1.0f
    			);
    	outtoRight.setDuration(500);
    	outtoRight.setInterpolator(new AccelerateInterpolator());
    	return outtoRight;
    }
	*/


}