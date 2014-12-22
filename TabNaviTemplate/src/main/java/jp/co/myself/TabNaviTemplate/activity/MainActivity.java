package jp.co.myself.TabNaviTemplate.activity;

import jp.co.myself.TabNaviTemplate.R;
import jp.co.myself.TabNaviTemplate.fragment.FirstTabFragment;
import jp.co.myself.TabNaviTemplate.fragment.SecondTabFragment;
import jp.co.myself.TabNaviTemplate.fragment.ThirdTabFragment;
import jp.co.myself.TabNaviTemplate.fragment.listener.MyTabListener;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {
	
	private final String FRAGMENT_TAG_FIRST = "first";
	private final String FRAGMENT_TAG_SECOND = "second";
	private final String FRAGMENT_TAG_THIRD = "third";
	
	
    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after 
     * previously being shut down then this Bundle contains the data it most 
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        actionBar.addTab(actionBar.newTab()
        		.setText(getString(R.string.frist_fragment_title))
        		.setTabListener(new MyTabListener<FirstTabFragment>(this, FRAGMENT_TAG_FIRST, FirstTabFragment.class)));
        actionBar.addTab(actionBar.newTab()
        		.setText(getString(R.string.second_fragment_title))
        		.setTabListener(new MyTabListener<SecondTabFragment>(this, FRAGMENT_TAG_SECOND, SecondTabFragment.class)));
        actionBar.addTab(actionBar.newTab()
        		.setText(getString(R.string.third_fragment_title))
        		.setTabListener(new MyTabListener<ThirdTabFragment>(this, FRAGMENT_TAG_THIRD, ThirdTabFragment.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(jp.co.myself.TabNaviTemplate.R.menu.main, menu);
	return true;
    }

}

