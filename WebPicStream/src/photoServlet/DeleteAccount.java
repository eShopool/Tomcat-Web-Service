package photoServlet;

import library.DecodeTool;
import library.DeleteFile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * This class is used to accept the requirement about deleting an account.
 * @author Group 10
 */
@WebServlet(name = "DeleteAccount")
public class DeleteAccount extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String encryptedPhone = request.getParameter("phone");
        String phoneNumber = DecodeTool.Base64Decoder(encryptedPhone);
        System.out.println("phone: "+ phoneNumber);
        String path = "C:\\Users\\YYK\\Desktop\\coursework\\UserPhotoLibrary\\" + phoneNumber;
        System.out.println("File path:" + path);
        File personalFile = new File(path);
        DeleteFile.deleteDir(personalFile);
    }
}
