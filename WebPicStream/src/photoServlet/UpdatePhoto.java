package photoServlet;

import library.FileHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class is used to update photos to the user by their requirements.
 * @author Group 10
 */
@WebServlet(name = "UpdatePhoto")
public class UpdatePhoto extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getParameter("filename");
        String encryptedImg = request.getParameter("img");
        FileHelper.updatePhoto(fileName, encryptedImg);
    }
}
