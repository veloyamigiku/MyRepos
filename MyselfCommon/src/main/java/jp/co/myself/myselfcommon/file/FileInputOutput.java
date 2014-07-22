package jp.co.myself.myselfcommon.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import com.orangesignal.csv.Csv;
import com.orangesignal.csv.CsvConfig;
import com.orangesignal.csv.handlers.StringArrayListHandler;

import jp.kddilabs.dag.common.exception.CommonException;
import android.content.Context;
import android.os.Build;
import android.os.Environment;

/**
 * (共通)Android端末のローカルファイル入出力クラスです。
 */
public class FileInputOutput {
	
	/**
	 * ローカルファイルのディレクトリです。
	 */
	private static final String LOCAL_DIRECTORY = "/data/data";
	
	/**
	 * マウント設定情報を含むファイルのパスです。
	 */
	private static final String VOLD_FSTAB_PATH = "/system/etc/vold.fstab";
	
	/**
	 * マウント設定情報の行の接頭辞です。
	 */
	private static final String MOUNT_LINE_PREFIX = "dev_mount";
	
	/**
	 * マウント情報のファイルのパスです。
	 */
	private static final String MOUNT_PATH = "/proc/mounts";
	
	/**
	 * ファイル読み込み書き込み時のエンコードは[UTF-8]固定とします。
	 */
	private static final String CHAR_ENCODE = "UTF-8";
	
	/**
	 * Android端末内のアプリケーション領域(/data/data/[アプリケーションのパッケージ名]/files)へのファイル書き込み用インスタンスを返却します。
	 * 書き込み用インスタンスの取得に失敗した場合は、[null]を返却します。
	 * 指定のファイルが存在しない場合は、新規作成します。
	 * 指定のファイルが既に存在する場合は、追記します。
	 * @param context
	 * @param fileName
	 * @param isInternal
	 * @return PrintWriter
	 * @throws CommonException 
	 */
	public static PrintWriter getPrintWriterForLocalFile(Context context, String fileName) throws CommonException {
		
		PrintWriter writer = null;
		
		if (fileName != null && fileName.length() > 0) {
			try {
				OutputStream out = context.openFileOutput(fileName, Context.MODE_PRIVATE | Context.MODE_APPEND);
				writer = new PrintWriter(new OutputStreamWriter(out, CHAR_ENCODE));
			} catch (FileNotFoundException e) {
				throw new CommonException(e);
			} catch (UnsupportedEncodingException e) {
				throw new CommonException(e);
			}
			
		}
		
		return writer;
	}
	
	/**
	 * Android端末内のアプリケーション領域(/data/data/[アプリケーションのパッケージ名]/files)へのCSVファイル書き込み用インスタンスを返却します。
	 * 書き込み用インスタンスの取得に失敗した場合は、[null]を返却します。
	 * 指定のファイルが存在しない場合は、新規作成します。併せてヘッダ情報を指定した場合は、先頭行に出力します。
	 * 指定のファイルが既に存在する場合は、追記します。
	 * @param context
	 * @param fileName
	 * @param headers
	 * @return PrintWriter
	 * @throws CommonException
	 */
	public static PrintWriter getPrintWriterForLocalCsvFile(Context context, String fileName, String[] headers) throws CommonException {
		
		// オープンするファイルの存在確認をします。
		boolean isNewFile = isExists(LOCAL_DIRECTORY + "/" + context.getPackageName() + "/" + fileName);
		
		PrintWriter writer = getPrintWriterForLocalFile(context, fileName);
		
		if (isNewFile) {
			//ヘッダを先頭行に出力します。
			writeCsvHeader(writer, headers);
		}
		
		return writer;
	}
	
	/**
	 * Android端末内のストレージ(内蔵・外部)へのファイル書き込み用インスタンスを返却します。
	 * 書き込み用インスタンスの取得に失敗した場合は、[null]を返却します。
	 * 指定のファイルが存在しない場合は、新規作成します。
	 * 指定のファイルが既に存在する場合は、追記します。
	 * @param storagePath
	 * @param context
	 * @param fileName
	 * @return PrintWriter
	 * @throws CommonException
	 */
	private static PrintWriter getPrintWriterForStorageFile(String storagePath, Context context, String fileName) throws CommonException {
		
		PrintWriter writer = null;
		
		if (storagePath != null & fileName != null && context != null) {
			
			// パッケージ名を取得します。
			String packageName = context.getPackageName();
			
			try {
				// ストレージ内のファイルパスを構築します。
				String filePath = storagePath + "/" + packageName + "/" + fileName;
				
				File file = new File(filePath.toString());
				File parentDir = new File(file.getParent());
				if (!parentDir.isDirectory()) {
					// オープンするファイルの親ディレクトリが存在しない場合に、作成します。
					if (!parentDir.mkdirs()) {
						throw new CommonException(new Exception());
					}
				}
				FileOutputStream fos = new FileOutputStream(filePath.toString(), true);
				OutputStreamWriter osw = new OutputStreamWriter(fos, CHAR_ENCODE);
				writer = new PrintWriter(osw);
			} catch (FileNotFoundException e) {
				throw new CommonException(e);
			} catch (UnsupportedEncodingException e) {
				throw new CommonException(e);
			}
		}
		
		return writer;
	}
	
	/**
	 * Android端末の外部ストレージへのファイル書き込み用インスタンスを返却します。
	 * 書き込み用インスタンスの取得に失敗した場合は、[null]を返却します。
	 * ファイルパスは[外部ストレージのルートパス]/[アプリケーションのパッケージ名(例：jp.co.xxx.appName)]/[指定ファイル名]となります。
	 * 指定のファイルが存在しない場合は、新規作成します。
	 * 指定のファイルが既に存在する場合は、追記します。
	 * @param fileName
	 * @param context
	 * @return PrintWriter
	 * @throws CommonException
	 */
	public static PrintWriter getPrintWriterForExternalStorageFile(String fileName, Context context) throws CommonException {
		
		String externalStoragePath = getSdcardPath();
		
		return getPrintWriterForStorageFile(externalStoragePath, context, fileName);
	}
	
	/**
	 * Android端末の外部ストレージへのCSVファイル書き込み用インスタンスを返却します。
	 * 書き込み用インスタンスの取得に失敗した場合は、[null]を返却します。
	 * ファイルパスは[外部ストレージのルートパス]/[アプリケーションのパッケージ名(例：jp.co.xxx.appName)]/[指定ファイル名]となります。
	 * 指定のファイルが存在しない場合は、新規作成します。併せてヘッダ情報を指定した場合は、先頭行に出力します。
	 * 指定のファイルが既に存在する場合は、追記します。
	 * @param fileName
	 * @param context
	 * @param headers
	 * @return
	 * @throws CommonException
	 */
	public static PrintWriter getPrintWriterForExternalStorageCsvFile(String fileName, Context context, String[] headers) throws CommonException {
		
		// オープンするファイルの存在確認をします。
		boolean isNewFile = isExists(getSdcardPath() + "/" + context.getPackageName() + "/" + fileName);
		
		PrintWriter writer = getPrintWriterForExternalStorageFile(fileName, context);
		
		if (isNewFile) {
			//ヘッダを先頭行に出力します。
			writeCsvHeader(writer, headers);
		}
		
		return writer;
	}
	
	/**
	 * Android端末の内蔵ストレージへのファイル書き込み用インスタンスを返却します。
	 * 書き込み用インスタンスの取得に失敗した場合は、[null]を返却します。
	 * ファイルパスは[内蔵ストレージのルートパス]/[アプリケーションのパッケージ名(例：jp.co.xxx.appName)]/[指定ファイル名]となります。
	 * 指定のファイルが存在しない場合は、新規作成します。
	 * 指定のファイルが既に存在する場合は、追記します。
	 * @param fileName
	 * @param context
	 * @return
	 * @throws CommonException
	 */
	public static PrintWriter getPrintWriterForInternalStorageFile(String fileName, Context context) throws CommonException {
		
		String internalStoragePath = Environment.getExternalStorageDirectory().toString();
		
		return getPrintWriterForStorageFile(internalStoragePath, context, fileName);
	}
	
	/**
	 * Android端末の内蔵ストレージへのCSVファイル書き込み用インスタンスを返却します。
	 * 書き込み用インスタンスの取得に失敗した場合は、[null]を返却します。
	 * ファイルパスは[内蔵ストレージのルートパス]/[アプリケーションのパッケージ名(例：jp.co.xxx.appName)]/[指定ファイル名]となります。
	 * 指定のファイルが存在しない場合は、新規作成します。併せてヘッダ情報を指定した場合は、先頭行に出力します。
	 * 指定のファイルが既に存在する場合は、追記します。
	 * @param fileName
	 * @param context
	 * @param headers
	 * @return
	 * @throws CommonException
	 */
	public static PrintWriter getPrintWriterForInternalStorageCsvFile(String fileName, Context context, String[] headers) throws CommonException {
		
		// オープンするファイルの存在確認をします。
		boolean isNewFile = isExists(Environment.getExternalStorageDirectory() + "/" + context.getPackageName() + "/" + fileName);
		
		PrintWriter writer = getPrintWriterForInternalStorageFile(fileName, context);
		
		if (isNewFile) {
			//ヘッダを先頭行に出力します。
			writeCsvHeader(writer, headers);
		}
		
		return writer;
		
	}
	
	/**
	 * Android端末内の指定ローカルファイルへの読み込み用インスタンスを返却します。
	 * 読み込み用インスタンスの取得に失敗した場合は、[null]を返却します。
	 * @param context
	 * @param fileName
	 * @return BufferedReader
	 */
	public static BufferedReader getBufferedReader(Context context, String fileName) {
		
		BufferedReader reader = null;
		
		if (fileName != null && fileName.length() > 0) {
			try {
				InputStream in = context.openFileInput(fileName);
				reader = new BufferedReader(new InputStreamReader(in, CHAR_ENCODE));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		return reader;
	}
	
	/**
	 * ファイルの存在確認をします。
	 * @param filePath
	 */
	private static boolean isExists(String filePath) {
		
		boolean isNewFile = false;
		if (!new File(filePath).exists()) {
			isNewFile = true;
		}
		return isNewFile;
		
	}
	
	/**
	 * CSVヘッダを出力します。
	 * @param writer
	 * @param headers
	 * @throws CommonException
	 */
	private static void writeCsvHeader(PrintWriter writer, String[] headers) throws CommonException {
		if (writer != null && headers != null) {
			//ヘッダを先頭行に出力します。
			ArrayList<String[]> headersList = new ArrayList<String[]>();
			headersList.add(headers);
			try {
				Csv.save(headersList, writer, new CsvConfig(), new StringArrayListHandler());
			} catch (IOException e) {
				throw new CommonException(e);
			}
		}
	}
	
	/**
	 * 外部ストレージのパスを返却します。
	 * パス取得に失敗した/パスが存在しない場合はnullを返却します。
	 * @return String
	 * @throws CommonException
	 */
	private static String getSdcardPath() throws CommonException {
		
		if (Build.VERSION.SDK_INT > 18) {
			return Environment.getExternalStorageDirectory().toString();
		}
		
		// マウントパスのリストです。
		ArrayList<String> pathList = new ArrayList<String>();
		
		// マウント情報のファイルを一行ごと解析して、マウントパスをリストに保管します。
		File vold_fstab = new File(VOLD_FSTAB_PATH);
		Scanner scanner = null;
		try {
			scanner = new Scanner(vold_fstab);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.startsWith(MOUNT_LINE_PREFIX)) {
					String path = line.replaceAll("\t", " ").split(" ")[2];
					if (!pathList.contains(path)) {
						pathList.add(path);
					}
				}
			}
		} catch (FileNotFoundException e) {
			throw new CommonException(e);
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
		
		// マウントパスのリストから、内蔵ストレージ(取り外し不可)のパスを除去します。
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			if (!Environment.isExternalStorageRemovable()) {
				pathList.remove(Environment.getExternalStorageDirectory().getPath());
			}
		}
		
		// マウント設定情報から取得したパスリストから、マウントされていないパスを除去します。
		for (int i = pathList.size() - 1; i >= 0; i--) {
			if (!isMountedPath(pathList.get(i))) {
				pathList.remove(i);
			}
		}
		
		String sdcardPath = null;
		if (pathList.size() > 0) {
			sdcardPath = pathList.get(0);
		}
		
		return sdcardPath;
	}
	
	/**
	 * 指定のパスがマウント済みか判定します。
	 * @param path
	 * @return boolean
	 * @throws CommonException 
	 */
	private static boolean isMountedPath(String path) throws CommonException {
		
		File mounts = new File(MOUNT_PATH);
		Scanner scanner = null;
		boolean isMounted = false;
		try {
			scanner = new Scanner(mounts);
			while (scanner.hasNextLine()) {
				if (scanner.nextLine().contains(path)) {
					isMounted = true;
					break;
				}
			}
		} catch (FileNotFoundException e) {
			throw new CommonException(e);
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
		
		return isMounted;
	}
	
}
