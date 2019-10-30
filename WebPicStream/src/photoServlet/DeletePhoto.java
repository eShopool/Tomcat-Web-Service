package photoServlet;

import library.DeleteFile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * This class is used to accept the requirement about deleting a photo.
 * @author Group 10
 */
@WebServlet(name = "DeletePhoto")
public class DeletePhoto extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String list = request.getParameter("list");
        List<String> filePath = Arrays.asList(list.split(","));
        String phoneNumber = filePath.get(0);
        String imgAddr = null;
        String rootPath = "C:\\Users\\YYK\\Desktop\\coursework\\UserPhotoLibrary\\" + phoneNumber + "\\MatchResults\\";
        File photos = new File(rootPath);
        DeleteFile.deleteFileUnderDir(photos);
        File file = new File(rootPath, "matchResult.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> realPath = filePath.subList(1,filePath.size());
        try {
            FileOutputStream fos = new FileOutputStream(file);
            if(!list.isEmpty()){
                for(String path : realPath){
                    imgAddr = path + "\n";
                    fos.write(imgAddr.getBytes());
                }
            }
            else fos.write("".getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
