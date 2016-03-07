
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by thunter on 07/03/2016.
 */
public class Converter {
    public static void main(String[] args) {
        System.out.println("Please enter the locales JSON to convert followed ");
        System.out.println("by a newline containing \"---\"");

        String input = "";
        String JSONString = "";

        Scanner in = new Scanner(System.in);

        do {
            if(!input.equals(""))
                JSONString += input + "\n";
            input = in.nextLine();

        } while(!input.equals("---"));

        try {
            // Clear the console
            if (System.getProperty("os.name").startsWith("Window"))
                Runtime.getRuntime().exec("cls");
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException e ) {}

        JSONParser jsonParser = new JSONParser();


        try {
            Object obj = jsonParser.parse(JSONString);
            JSONObject jObject = (JSONObject) obj;

            System.out.println(extractJson(jObject, "",  ""));
        } catch (ParseException e) {
            System.out.println("position: " + e.getPosition());
            System.out.println(e);
        }
    }

    private static String extractJson(JSONObject jsonObject, String preVariable, String returnS) {
        for(Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext(); ) {
            String key = (String) iterator.next();
            Object value = jsonObject.get(key);
            returnS = analyseObject(value, preVariable, key, returnS);
        }
        return returnS;
    }

    private static String extractJson(JSONArray jsonArray, String preVariable, String returnS) {
        for(int i = 0; i < jsonArray.size(); i++) {
            Object value = jsonArray.get(i);
            returnS = analyseObject(value, preVariable, i + "", returnS);
        }

        return returnS;
    }

    private static String analyseObject(Object value, String preVariable, String key, String returnS) {
        key = key.replace("\"", "");
        if(value instanceof JSONObject) {
            returnS = extractJson((JSONObject) value, preVariable + key + "_", returnS);
        } else if(value instanceof JSONArray) {
            returnS = extractJson((JSONArray) value, preVariable + key + "_", returnS);
        } else if(value instanceof String) {
            String vString = (String) value;
            vString = vString.replace("<", "&lt;"); // Format a string for android strings.
            vString = vString.replace(">", "&gt;"); // Format a string for android strings.
            vString = vString.replace("&", "&amp;"); // Format a string for android strings.
            vString = vString.replace("'", "\\'"); // Format a string for android strings.
            returnS += "\t<string name=\"" + preVariable + key + "\">" + vString + "</string>\n";
            //System.out.println(append);
        }
        return returnS;
    }
}
