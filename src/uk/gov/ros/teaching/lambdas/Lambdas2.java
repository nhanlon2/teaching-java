package uk.gov.ros.teaching.lambdas;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Lambdas2 {
    
    public static Runnable andThen(Runnable first, Runnable second) {
        return () -> {
            first.run();
            second.run();
        };
    }
    
    public static void main (String [] args) {
       // new Thread(Lambdas2.andThen(() -> System.out.println("whee"), () -> System.out.println("whee2whee"))).start();
        String [] names = {"Pete", "David", "Sue"};
        Stream <String> namesStream = Arrays.stream(names);
        namesStream.map(String::toLowerCase).peek(e-> System.out.println(e)).toArray();// toArray() finalises the stream and so peek runs.
        List <Runnable> runners = new ArrayList<Runnable>();
        for (String name: names) {
            runners.add(()-> System.out.println(name));
        }
//        for (int i = 0; i< names.length ;  i++) {
//            runners.add(()-> System.out.println(names[i])); // illegal as i mutates
//        }
        runners.forEach((r -> new Thread(r).start()));
    }
    
    public List <File> getSubDirectories(File root) {
        return Arrays.asList(root.listFiles(new FileFilter() {

            @Override
            public boolean accept(File aFile) {
                return aFile.isDirectory();
            }
            
        }));
    }
    
    public List <File> getSubDirectoriesLambda(File root) {
        return Arrays.asList(root.listFiles(aFile -> aFile.isDirectory()));
    }
    
    public List <File> getSubDirectoriesMethodRef(File root) {
        return Arrays.asList(root.listFiles(File::isDirectory));
    }
    
    public Runnable uncheck (Runnable wrappee) {
        return () -> {
            try {
                wrappee.run();
            }
             catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

}
