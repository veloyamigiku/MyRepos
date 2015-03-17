package jp.co.myself.MyselfUI.pagerview.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * ページアダプタの基底クラスです。
 */
public abstract class BasePagerAdapter<T> extends PagerAdapter {
	
	/**
	 * ページリストです。
	 */
	private ArrayList<T> pageList;
	
	/**
	 * コンテキストです。
	 */
	private Context context;
	
	/**
	 * コンストラクタです。
	 */
	public BasePagerAdapter(Context context) {
		this.context = context;
		pageList = new ArrayList<T>();
	}

	/**
	 * ページアイテムを追加します。
	 * @param item　ページアイテムです。
	 */
	public void add(T item) {
		pageList.add(item);
	}
	
	/**
	 * ページアイテムを削除します。
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View)object);
	}
	
	/**
	 * ページアイテムを配置します。
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = createView(context, position);
		container.addView(view);
		return view;
	}
	
	/**
	 * ページアイテム数を返却します。
	 */
	@Override
	public int getCount() {
		return pageList.size();
	}
	
	/**
	 * オブジェクト内にビューがあるかどうか返却します。
	 */
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0.equals(arg1);
	}
	
	/**
	 * ページに配置するビューを作成します。
	 * @param context コンテキストです。
	 * @param position ページの位置です。
	 * @return　ビュー。
	 */
	abstract protected View createView(Context context, int position);
	
	/**
	 * プロパティのゲッタです。
	 */
	public ArrayList<T> getPageList() {
		return pageList;
	}
	
	public Context getContext() {
		return context;
	}
	
}
