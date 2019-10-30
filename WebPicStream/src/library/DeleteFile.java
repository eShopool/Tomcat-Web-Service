package library;

import java.io.File;

/**
 * This method is used to delete file.
 * @author Group 10
 */
public class DeleteFile {

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //Find the deleted file by recursion.
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static void deleteFileUnderDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //Find the deleted file by recursion.
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));

            }
        }
    }
}
