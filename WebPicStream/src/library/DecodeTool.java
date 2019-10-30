package library;
import java.util.Base64;

/**
 * This class is used to decode accepted string by Base64.
 * @author Group 10
 */
public class DecodeTool {

    public DecodeTool(){}

    public static String Base64Decoder(String str){
        byte[] data = Base64.getDecoder().decode(str);
        String decode = null;
        decode = new String(data);
        return decode;
    }
}
