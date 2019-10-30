package photoServlet;

import library.DecodeTool;
import library.FileHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class is used to get the portrait from the web user.
 * @author Group 10
 */
@WebServlet(name = "GetPortrait")
public class GetPortrait extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String encryptedPhone = request.getParameter("phone");
        System.out.println("encryptedPhone:" + encryptedPhone);
        String phoneNumber = DecodeTool.Base64Decoder(encryptedPhone);
        System.out.println("phone: " + phoneNumber);
        byte[] data = FileHelper.getPhoto(phoneNumber, "\\UserPortrait\\head.jpg");
        System.out.println("data: " + data);
        try {
            response.getOutputStream().write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
