package jp.co.myself.MyselfUI.activity;

import jp.co.myself.MyselfUI.R;
import jp.co.myself.MyselfUI.viewpager.NonScrollPagerView;
import jp.co.myself.MyselfUI.viewpager.adapter.BasePagerAdapter;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;

public abstract class BasePagerActivity extends Activity {
	
	private NonScrollPagerView vp = null;
	
	private TextView pageTextView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myselfui_viewpager);
		
		vp = (NonScrollPagerView) findViewById(R.id.myselfui_viewpager_vp);
		vp.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});
		vp.setAdapter(createPagerAdapter(this));
		
		pageTextView = (TextView) findViewById(R.id.myselfui_viewpager_page_tv);
		pageTextView.setText(getPageTitle());
		Button prevButton = (Button) findViewById(R.id.myselfui_viewpager_prev_btn);
		prevButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				vp.setCurrentItem(vp.getCurrentItem() - 1, true);
				pageTextView.setText(getPageTitle());
			}
		});
		Button nextButton = (Button) findViewById(R.id.myselfui_viewpager_next_btn);
		nextButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				vp.setCurrentItem(vp.getCurrentItem() + 1, true);
				pageTextView.setText(getPageTitle());
			}
		});
	}
	
	protected abstract <T> BasePagerAdapter<T> createPagerAdapter(Context context);
	
	
	private String getPageTitle() {
		return (vp.getCurrentItem() + 1) + "/" + vp.getAdapter().getCount();
	}
	
}
