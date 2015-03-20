package jp.co.myself.jcommon.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import jp.co.myself.jcommon.file.visitor.DirectoryDeleteVisitor;


/**
 * ディレクトリ操作の共通処理クラスです。
 */
public class DirectoryIO {
	
	/**
	 *　ディレクトリを削除します。
	 * @param dirPath　削除対象のディレクトリパスです。
	 * @return　boolean 削除中に例外が発生した場合はfalse、以外はtrue。
	 */
	public static boolean delete(Path dirPath) {
		boolean result = true;
		DirectoryDeleteVisitor ddv = new DirectoryDeleteVisitor();
		try {
			// 指定パスがディレクトリの場合は、削除します。
			File dir = dirPath.toFile();
			if (dir.exists() && dir.isDirectory()) {
				Files.walkFileTree(dirPath, ddv);
			}
		} catch (IOException e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
}
