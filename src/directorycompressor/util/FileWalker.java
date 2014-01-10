
package directorycompressor.util;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import static java.nio.file.FileVisitResult.CONTINUE;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Neo 
 */
public class FileWalker {

    private String dir;
    private List<Path> filesToCompress;
    

    /**
     * Constructor
     * @param dir 
     */
    public FileWalker(String dir) {
        this.dir = dir;
        this.filesToCompress = new ArrayList<>();
    }
    
    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public List<Path> getFilesToCompress() {
        return filesToCompress;
    }
    
    
    /**
     * method to recursively walk the directory specified, and add the files above.
     */
    public void walk() {
        try {
            Path p = FileSystems.getDefault().getPath(this.dir);
            //I am too lazy to write a separate Visitor :(
            Files.walkFileTree(p, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file,
                        BasicFileAttributes attr) {
                    if (attr.isSymbolicLink() || attr.isDirectory()) {
                        System.out.println("Walker: Skipping SymLinks/Directory"+file);
                        
                    } 
                    else{ 
                        System.out.println("Walker:Regular file: "+file);
                        //add this to list of files to process.
                        FileWalker.this.filesToCompress.add(file);
                    }
                    return CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir,
                        IOException exc) {
                    System.out.println("Walker: Finished Walking- "+dir);
                    return CONTINUE;
                }

                //Error if any.
                @Override
                public FileVisitResult visitFileFailed(Path file,
                        IOException exc) {
                    System.err.println(exc);
                    return CONTINUE;
                }
            });

        } catch (IOException ex) {
            System.out.println("OOPS!" + ex.getMessage());
            ex.printStackTrace();
        }
    }

}
