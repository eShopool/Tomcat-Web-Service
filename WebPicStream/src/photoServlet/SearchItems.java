package photoServlet;

import library.DecodeTool;
import library.FileHelper;
import library.MySQLHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * This class is used to fuzzy queries by the requirement of the user.
 * @author Group 10
 */
@WebServlet(name = "SearchItems")
public class SearchItems extends HttpServlet {

    private MySQLHelper sqlHelper;
    private FileHelper fileHelper;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DecodeTool decode = new DecodeTool();
        String encryptedItem = request.getParameter("item");
        String item = decode.Base64Decoder(encryptedItem);
        System.out.println("item: " + item);
        try {
            sqlHelper = new MySQLHelper("commodity");
            String paths = "";
            if(sqlHelper.conn != null) {
                paths = sqlHelper.updateItem(item);
                sqlHelper.closedMySQL();
            }

            if(!paths.equals("")){
                fileHelper = new FileHelper();
                fileHelper.cleanTxtFile("search");
                fileHelper.writeTxtFile("search", paths);

                response.getOutputStream().write(FileHelper.getTxt("search"));

            }
            else response.getOutputStream().write("FAIL".getBytes());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
