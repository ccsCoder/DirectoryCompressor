
package directorycompressor.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author Neo
 */
public class Compressor {

    private String outputPath;
    private String zipFileName;

    public Compressor(String outputPath, String zipFileName) {
        this.outputPath = outputPath;
        this.zipFileName = zipFileName+".zip";
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String getZipFileName() {
        return zipFileName;
    }

    public void setZipFileName(String zipFileName) {
        this.zipFileName = zipFileName;
    }
    
    /**
     * Magic happens here ;)
     * @param files
     * @return success/failure.
     */
    public boolean zip(List<Path> files) {
        ZipOutputStream zipOS = null;
        boolean success = false;
        byte[] buffer = new byte[1024];

        try {
            success = true;
            zipOS = new ZipOutputStream(new FileOutputStream(this.zipFileName));

            //go through the files.
            for (Path path : files) {
                System.out.println("Compressor: Picked up:" + path.toString()+" size="+ (path.toFile().length()/1024.0)+" KB");
                ZipEntry ze = new ZipEntry(path.toString());
                //add the file to stream.
                zipOS.putNextEntry(ze);

                FileInputStream fis = new FileInputStream(path.toFile());

                //Read and output to zip stream.
                int len;
                while ((len = fis.read(buffer)) > 0) {
                    zipOS.write(buffer, 0, len);
                }
                
                fis.close();
                System.out.println("Compressor: Done with it, new size:"+ (ze.getCompressedSize()/1024.0)+" KB");
            }

            zipOS.closeEntry(); //imp! in order to able to write the next entry.
            zipOS.close();

        } catch (FileNotFoundException ex) {
            System.out.println("OOPS!" + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("OOPS!" + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                zipOS.close();
            } catch (IOException ex) {
                System.out.println("OOPS!" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        System.out.println("");
        return success;
    }

}
