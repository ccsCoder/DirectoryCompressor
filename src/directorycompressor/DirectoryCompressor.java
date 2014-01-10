
package directorycompressor;

import directorycompressor.util.Compressor;
import directorycompressor.util.FileWalker;

/**
 *
 * @author Neo
 */
public class DirectoryCompressor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // args[0]= Which directory to zip
        // args[1]= Where to place the zipped file ?
        
        if (args.length!=2) {
            System.out.println("Invalid Params: Usage:");
            System.out.println("java DirectoryCompressor <SOURCE_DIR> <DESTINATION_DIR>");
            System.out.println("The final zip file will be named <SOURCE_DIR>.zip");
            System.exit(0);
                    
        }
        System.out.println("DirectoryCompressor V 1.0...... ");
        System.out.println("DirectoryCompressor: Please wait while I do my Thing ...!");
        Compressor compressor = new Compressor(args[1],args[0]);
        FileWalker walker = new FileWalker(args[0]);
        walker.walk();
        compressor.zip(walker.getFilesToCompress());
        //too lazy to check return values. Do that urself :P
        System.out.println("DirectoryCompressor: All Done! your zip file is -"+args[1]+".zip");
        System.out.println("~~~ Neo ~~~");
                
    }
    
}
