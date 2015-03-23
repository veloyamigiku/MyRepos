package jp.co.myself.MyselfUI.viewpager.adapter;

import uk.co.senab.photoview.PhotoView;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

public abstract class ImagePagerAdapter<T> extends BasePagerAdapter<T> {
	
	/**
	 * コンストラクタです。
	 * @param context　コンテキストです。
	 */
	public ImagePagerAdapter(Context context) {
		super(context);
	}
	
	@Override
	protected View createView(Context context, int position) {
		byte[] imageData = getImageData(context, position);
		PhotoView photoView = new PhotoView(context);
		Bitmap bmp = null;
		if (imageData != null && imageData.length > 0) {
			bmp = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
			photoView.setImageBitmap(bmp);
		}
		return photoView;
	}
	
	/**
	 * 指定のページ位置の画像データを取得します。
	 * @param context コンテキストです。
	 * @param position ページ位置です。
	 * @return 指定ページ位置の画像データ。
	 */
	abstract protected byte[] getImageData(Context context, int position);
	
}
