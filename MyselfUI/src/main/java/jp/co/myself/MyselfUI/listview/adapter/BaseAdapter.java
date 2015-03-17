package jp.co.myself.MyselfUI.listview.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * テーブルビューの基本アダプタのクラスです。
 */
public abstract class BaseAdapter<T> extends ArrayAdapter<T> {
	
	/**
	 * レイアウトインフレイタです。
	 */
	private LayoutInflater layoutInflater;
	
	/**
	 * レイアウトのリソースIDです。
	 */
	private int layoutResourceId;
	
	/**
	 * コンストラクタです。
	 * @param context コンテキストです。
	 * @param textViewResourceId テキストビューのリソースIDです。
	 * @param objects リストビューのアイテムリストです。
	 * @param layoutResourceId リストビューのアイテムのレイアウトです。
	 */
	public BaseAdapter(Context context, int textViewResourceId, List<T> objects, int layoutResourceId) {
		super(context, textViewResourceId, objects);

		setLayoutResourceId(layoutResourceId);
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	/**
	 * 各アイテムを表示します。
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		T item = getItem(position);

		if (null == convertView) {
			convertView = layoutInflater.inflate(getLayoutResourceId(), null);
		}

		setWidget(position, convertView, parent, item);

		return convertView;
	}
	
	/**
	 * アイテム内のウィジェットを設定します。
	 * (本クラスの継承先で実装します。)
	 * @param position　アイテムのインデックスです。
	 * @param convertView　アイテムのビューです。
	 * @param parent ペアレントです。
	 * @param item　アイテムです。
	 */
	protected abstract void setWidget(int position, View convertView,
			ViewGroup parent, T item);
	
	/**
	 * レイアウトリソースIDのゲッタです。
	 * @return レイアウトリソースID。
	 */
	private int getLayoutResourceId() {
		return layoutResourceId;
	}
	
	/**
	 * レイアウトリソースIDのセッタです。
	 * @param layoutResourceId レイアウトリソースIDです。
	 */
	private void setLayoutResourceId(int layoutResourceId) {
		this.layoutResourceId = layoutResourceId;
	}
	
}
