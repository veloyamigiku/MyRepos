package jp.co.myself.MyselfUI.activity;

import jp.co.myself.MyselfUI.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 画像ビューアのアクティビティです。
 */
public class ImageActivity extends Activity {
	
	/**
	 * 
	 */
	public static final String INTENT_KEY_DATA = "DATA";
	
	/**
	 * 画像のデータです。
	 */
	private byte[] imageData = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		
		Intent intent = getIntent();
		setImageData(intent.getByteArrayExtra(INTENT_KEY_DATA));
		
		ImageView iv = (ImageView) findViewById(R.id.imageView1);
		Bitmap bmp = null;
		byte[] data = getImageData();
		if (data != null && data.length > 0) {
			bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
			iv.setImageBitmap(bmp);
		} else {
			Toast.makeText(getApplicationContext(), "画像表示エラー", Toast.LENGTH_LONG);
		}
	}
	
	/**
	 * 以降は上記のプロパティのゲッタ・セッタです。
	 */
	public byte[] getImageData() {
		return imageData;
	}
	
	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}
	
}
