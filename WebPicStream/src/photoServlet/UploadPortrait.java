package photoServlet;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import library.DecodeTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is used to upload the portrait for each user.
 * @author Group 10
 */
@WebServlet(name = "UploadPortrait")
public class UploadPortrait extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String encryptedPhone = request.getParameter("phone");
        String phoneNumber = DecodeTool.Base64Decoder(encryptedPhone);
        String encryptedImg = request.getParameter("img");
        String path = "C:\\Users\\YYK\\Desktop\\coursework\\UserPhotoLibrary\\" + phoneNumber + "\\UserPortrait\\";
        try {
            byte[] imageByteArray = Base64.decode(encryptedImg);
            FileOutputStream imageOutFile = new FileOutputStream(path + "head.jpg");
            imageOutFile.write(imageByteArray);
            imageOutFile.close();
        } catch (Base64DecodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

}
