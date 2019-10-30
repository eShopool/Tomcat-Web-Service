package photoServlet;

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
 * This class is used to get the electronic commodity list from the web user.
 * @author Group 10
 */
@WebServlet(name = "GetElectronicsTXT")
public class GetElectronicsTXT extends HttpServlet {

    private FileHelper fileHelper;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            MySQLHelper sqlHelper = new MySQLHelper("commodity");
            String paths = "";
            if(sqlHelper.conn != null) {
                paths = sqlHelper.updateElectronic();
                sqlHelper.closedMySQL();
            }

            if(!paths.equals("")){
                fileHelper = new FileHelper();
                fileHelper.cleanTxtFile("Electronic");
                fileHelper.writeTxtFile("Electronic", paths);
                System.out.println("Have read to txt");

                response.getOutputStream().write(FileHelper.getTxt("Electronic"));
            }
            else response.getOutputStream().write("FAIL".getBytes());

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
