package photoServlet;

import library.FileHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class is used to get the photo from the web user.
 * @author Group 10s
 */
@WebServlet(name = "GetPhoto")
public class GetPhoto extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String imgPath = request.getParameter("path");
        byte[] data = FileHelper.getPhoto(imgPath);
        try {
            response.getOutputStream().write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
