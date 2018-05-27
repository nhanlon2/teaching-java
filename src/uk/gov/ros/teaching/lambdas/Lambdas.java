package uk.gov.ros.teaching.lambdas;

import java.io.File;
import java.io.FileFilter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Lambdas {
	
	private BiFunction<File,Function<File,Boolean>, List<File>> getRelatedFiles = (File currentDir, Function<File,Boolean> filter) -> {
		List<File> allSubDirectories = new ArrayList<>();
		for (File child : currentDir.listFiles()) {
			if (filter.apply(child)) {
				allSubDirectories.add(child);
				allSubDirectories.addAll(getSubDirectories(child));
			}
		}
		return allSubDirectories;
	};
	
	public List <File> getSubDirectories(File currentDir){
		return getRelatedFiles.apply(currentDir, File::isDirectory);
	}
	
	public File [] getSubDirectoriesArray(File currentDir){
		List<File> all = new ArrayList<>();
		File [] subDirs = currentDir.listFiles(File::isDirectory);
		all.addAll(Arrays.asList(subDirs));
		for (File f: subDirs){
			all.addAll(Arrays.asList(getSubDirectoriesArray(f)));
		}
		return all.toArray(new File[all.size()]);
	}
	
	public File [] getFilesByExtention(File currentDir,String ext){
		return currentDir.listFiles(f -> f.toPath().endsWith(ext));
	}
	
	
	
	

}
