import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


// video to load jar
//https://www.youtube.com/watch?v=QAJ09o3Xl_0

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// Program for print data in JSON format.
public class ReadJson {
    public static void main(String args[]) throws ParseException {
        // In java JSONObject is used to create JSON object
        // which is a subclass of java.util.HashMap.

        JSONObject file = new JSONObject();
        file.put("Full Name", "Ritu Sharma");
        file.put("Roll No.", new Integer(1704310046));
        file.put("Tuition Fees", new Double(65400));


        // To print in JSON format.
//        System.out.print(file.get("Tuition Fees"));
//        System.out.println(file.get("Full Name" ));
        pull();

    }

    public static void pull() throws ParseException {
        String output = "abc";
        String totlaJson="";
        try {
            URL url = new URL("https://last-airbender-api.fly.dev/api/v1/characters");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {

                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));


            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                totlaJson+=output;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
        //System.out.println(str);
        org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) parser.parse(totlaJson);
        for(int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);

            try {
                System.out.println();
                String name = (String) jsonObject.get("name");
                System.out.println("Name: " + name);
                org.json.simple.JSONArray msg = (org.json.simple.JSONArray) jsonObject.get("allies");
                int n = msg.size(); //(msg).length();
                for (int k = 0; k < n; k++) {
                    String test = (String) msg.get(k);
                    System.out.println("Allies: " + test);
                    // System.out.println(person.getInt("key"));
                }

                msg = (org.json.simple.JSONArray) jsonObject.get("enemies");
                n = msg.size(); //(msg).length();
                for (int k = 0; k < n; k++) {
                    String test = (String) msg.get(k);
                    System.out.println("Enemies: " + test);
                    // System.out.println(person.getInt("key"));
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }






    }
}


