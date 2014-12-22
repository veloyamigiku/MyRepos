package jp.co.myself.TabNaviTemplate.fragment.listener;

import jp.co.myself.TabNaviTemplate.R;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

public class MyTabListener<T extends Fragment> implements TabListener {
	
	private Fragment mFragment;
	private final Activity mActivity;
	private final String mTag;
	private final Class<T> mClass;
	
	public MyTabListener(Activity activity, String tag, Class<T> clz) {
		mActivity = activity;
		mTag = tag;
		mClass = clz;
		mFragment = mActivity.getFragmentManager().findFragmentByTag(mTag);
	}
	
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		
		if (mFragment == null) {
			mFragment = Fragment.instantiate(mActivity, mClass.getName());
			FragmentManager fm = mActivity.getFragmentManager();
			fm.beginTransaction().add(R.id.container, mFragment, mTag).commit();
		} else {
			if (mFragment.isDetached()) {
				FragmentManager fm = mActivity.getFragmentManager();
				fm.beginTransaction().attach(mFragment).commit();
			}
		}
		
	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		if (mFragment != null) {
			FragmentManager fm = mActivity.getFragmentManager();
			fm.beginTransaction().detach(mFragment).commit();
		}
	}

	public void onTabReselected(Tab tab, FragmentTransaction ft) {}

}
