package photoServlet;

import library.DecodeTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * This class is used to accept the requirement about creating an account.
 * @author Group 10
 */
@WebServlet(name = "CreateAccount")
public class CreateAccount extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String encryptedPhone = request.getParameter("phone");
        DecodeTool decode = new DecodeTool();
        String phoneNumber = decode.Base64Decoder(encryptedPhone);
        System.out.println("phone:"  + phoneNumber);
        String libraryPath = "C:\\Users\\YYK\\Desktop\\coursework\\UserPhotoLibrary\\";
        //Create a folder of the account information of user.
        File dir = new File(libraryPath, phoneNumber);
        dir.mkdirs();
        //Create a folder which stores the portrait images of the user.
        File portraitFile = new File(libraryPath + "\\" + phoneNumber, "UserPortrait");
        portraitFile.mkdirs();
    }

}
