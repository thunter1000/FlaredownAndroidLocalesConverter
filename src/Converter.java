
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

        JSONParser jsonParser = new JSONParser();


        try {
            Object obj = jsonParser.parse(JSONString);
            JSONObject jObject = (JSONObject) obj;

            extractJson(jObject, "",  "");
        } catch (ParseException e) {
            System.out.println("position: " + e.getPosition());
            System.out.println(e);
        }
    }

    private static String extractJson(JSONObject jsonObject, String preVariable, String returnS) {
        for(Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext(); ) {
            String key = (String) iterator.next();
            Object value = jsonObject.get(key);
            analyseObject(value, preVariable, key);
        }
        return "";
    }

    private static String extractJson(JSONArray jsonArray, String preVariable, String returnS) {
        for(int i = 0; i < jsonArray.size(); i++) {
            Object value = jsonArray.get(i);
            analyseObject(value, preVariable, i + "");
        }

        return "";
    }

    private static void analyseObject(Object value, String preVariable, String key) {
        if(value instanceof JSONObject) {
            extractJson((JSONObject) value, preVariable + key + ".", "");
        } else if(value instanceof JSONArray) {
            extractJson((JSONArray) value, preVariable + key + ".", "");
        } else if(value instanceof String) {
            String vString = (String) value;
            String append = "<string name=\"" + preVariable + key + "\">" + vString + "</string>";
            System.out.println(append);
        }
    }
}
