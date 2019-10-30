package library;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import sun.plugin2.message.Message;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * This class is used to do file I/O operation, which includes write result to txt file and store photos at local.
 * Besides that, it also can turn photos or files into byte streams to send to the user.
 * @author Group 10
 */
public class FileHelper {

    //This is the default path for file storage.
    static final String defaultPath = "C:\\Users\\YYK\\Desktop\\coursework\\UserPhotoLibrary\\";

    /**
     * This method is used to change txt file to byte stream.
     * @param category the target txt file for changing.
     * @return the byte stream.
     */
    public static byte[] getTxt(String category) {
        File txt = new File(defaultPath + category + ".txt");
        try {
            FileInputStream in = new FileInputStream(txt);
            byte[] data = new byte[in.available()];
            in.read(data);
            in.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    /**
     * This method is used to change photos to byte stream.
     * @param imgPath the target photo for changing.
     * @return the byte stream.
     */
    public static byte[] getPhoto(String imgPath){
        File img = new File(defaultPath + imgPath);
        if(!img.exists()){
            System.out.println("The file is not existed at local.");
            try {
                URL url = new URL("http://192.168.0.230:8080/WebPicStream/GetPhoto?path=" + imgPath);
                URLConnection urlConnection = url.openConnection();
                urlConnection.setConnectTimeout(5000);
                InputStream input = urlConnection.getInputStream();
                OutputStream output = new FileOutputStream(img);
                byte[] byt = new byte[1024];
                int length = 0;
                while ((length = input.read(byt)) != -1) {
                    output.write(byt, 0, length);
                }
                input.close();
                output.close();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        ByteArrayOutputStream img_stream = new ByteArrayOutputStream();
        try {
            BufferedImage bi = ImageIO.read(img);
            ImageIO.write(bi, "jpg", img_stream);
            byte[] bytes = img_stream.toByteArray();
            img_stream.close();
            return bytes;
        } catch (IOException e) {
            e.getStackTrace();
        }
        return new byte[0];
    }

    /**
     * This method is used to change photos to byte stream.
     * @param Phone the user's phone number, this method is used for storing the portrait.
     * @param path the path of target photo for changing.
     * @return the byte stream of the photo.
     */
    public static byte[] getPhoto(String Phone, String path){
        File img = new File(defaultPath + Phone + path);

        if(!img.exists()){
            System.out.println("Portrait is not existed at local.");
            try {
                String encryptedPhone = Encoder.Base64Encode(Phone);
                URL url = new URL("http://192.168.0.230:8080/WebPicStream/GetPortrait?phone=" + encryptedPhone);
                URLConnection urlConnection = url.openConnection();
                urlConnection.setConnectTimeout(5000);
                InputStream input = urlConnection.getInputStream();
                OutputStream output = new FileOutputStream(img);
                byte[] byt = new byte[1024];
                int length = 0;
                while ((length = input.read(byt)) != -1) {
                    output.write(byt, 0, length);
                }
                input.close();
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        ByteArrayOutputStream img_stream = new ByteArrayOutputStream();
        try {
            BufferedImage bi = ImageIO.read(img);
            ImageIO.write(bi, "jpg", img_stream);
            byte[] bytes = img_stream.toByteArray();
            img_stream.close();
            return bytes;
        } catch (IOException e) {
            e.getStackTrace();
        }
        return new byte[0];
    }

    /**
     * This method is used to delete relative files if who wants to delete the account.
     * @param encryptedPhone  the user's phone number which is encrypted.
     * @param path
     * @param targetPath
     */
    public static void deleteFile(String encryptedPhone, String path, String targetPath){
        DecodeTool decode = new DecodeTool();
        String phoneNumber = decode.Base64Decoder(encryptedPhone);
        String absolute_path = defaultPath + phoneNumber + path + targetPath;
        File targetFile = new File(absolute_path);
        targetFile.delete();
    }

    /**
     * This method is used to get the updated photo from the user to the server.
     * @param fileName the photo name
     * @param encryptedImg the byte stream of the target photo.
     */
    public static void updatePhoto(String fileName, String encryptedImg){
        String absolutae_path = defaultPath;
        // Base64
        try {
            byte[] imageByteArray = Base64.decode(encryptedImg);
            FileOutputStream imageOutFile = new FileOutputStream(absolutae_path + fileName + ".jpg");
            imageOutFile.write(imageByteArray);
            imageOutFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    /**
     * The method is used to clean the target txt file.
     * @param category the category of target txt file.
     */
    public static void cleanTxtFile(String category){
        String filePath = defaultPath + category + ".txt";
        File file = new File(filePath);
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            fw.write("");
            fw.flush();
            fw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //将从数据库中获得的String写入目标txt

    /**
     * This method is used to write the string to the target txt file.
     * @param category the category of target txt file.
     * @param path the string which is ready to write to txt file.
     */
    public static void writeTxtFile(String category, String path){
        String filePath = defaultPath + category + ".txt";
        File file = new File(filePath);
        try{
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(filePath);
            fw.write(path);
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
