package jp.co.myself.jcommon.file.visitor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * ファイルビジターです。(ディレクトリ削除用)
 */
public class DirectoryDeleteVisitor implements FileVisitor<Path> {

	@Override
	public FileVisitResult postVisitDirectory(Path arg0, IOException arg1)
			throws IOException {
		Files.delete(arg0);
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path arg0, BasicFileAttributes arg1)
			throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path arg0, BasicFileAttributes arg1)
			throws IOException {
		Files.delete(arg0);
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path arg0, IOException arg1)
			throws IOException {
		return FileVisitResult.CONTINUE;
	}
	
}
