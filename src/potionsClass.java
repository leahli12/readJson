//    public static void main(String[] args) {
//        Easy1 Practice = new Easy1();
//        Practice.showEventDemo();
//    }

//    public Easy1() {
//        prepareGUI();
//    }

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

// Program for print data in JSON format.
public class potionsClass implements ActionListener {

    private JFrame mainFrame;
    private JLabel statusLabel;
    private JLabel imageLabel;
    private JPanel imagePanel;
    private JPanel controlPanel;
    private JPanel potionPanel;
    private JMenuBar mb;
    private JMenu file, edit, help;
    private JMenuItem cut, copy, paste, selectAll;
    private JTextArea ta; //typing area
    public String taInput;
    private int WIDTH=800;
    private int HEIGHT=700;

    public static String urlStub;
    public String id = "";
    public String name = "";
    public static String totlaJson="";
    public static ArrayList<String> linkStubs;
    public static ArrayList<String> imageStubs;
    public int index = 0;
    public String optImg = ""; // For the ones that need manual images
    public ArrayList<String> potionNames;
    public Map<String, Set<String>> cookbook;
    public static int potionIndex;
    public static HashSet<String> tempSet;
    public static boolean cookbookDone;
    public static String imageLink;
    public static String potionInfo;
    public static ArrayList<String> characteristics;
    public static ArrayList<String> effects;


    public static void main(String args[]) throws ParseException {
        // In java JSONObject is used to create JSON object
        // which is a subclass of java.util.HashMap.
        potionsClass screen = new potionsClass();
        screen.showEventDemo();
    }

    public potionsClass() throws ParseException {
        cookbookDone = false;
        potionNames = new ArrayList<>();
        potionNames.add("Wit-Sharpening Potion");
        potionNames.add("Fire-Protection Potion");
        potionNames.add("Hair-Raising Potion");
        potionNames.add("Memory Potion");
        potionNames.add("Pepperup Potion");
        potionNames.add("Strengthening Solution");
        potionNames.add("Draught of Peace");
        potionNames.add("Essence of Insanity");
        potionNames.add("Babbling Beverage");
        potionNames.add("Baruffio's Brain Elixir");
        potionNames.add("Befuddlement Draught");
        potionNames.add("Calming Draught");
        potionNames.add("Cheese-Based Potions");
        potionNames.add("Sleeping Draught");

        linkStubs = new ArrayList<>();
        linkStubs.add("?name=Wit");
        linkStubs.add("?name=Fire-Protection");
        linkStubs.add("?name=Hair-Raising");
        linkStubs.add("?name=Memory");
        linkStubs.add("?name=Pepperup");
        linkStubs.add("?name=Strengthening");
        linkStubs.add("?name=Draught");
        linkStubs.add("?name=Essence");
        linkStubs.add("?name=Babbling");
        linkStubs.add("?name=Baruffio's");
        linkStubs.add("?name=Befuddlement");
        linkStubs.add("?name=Calming");
        linkStubs.add("?name=Cheese");
        linkStubs.add("?name=Sleeping");

        imageStubs = new ArrayList<>();
        imageStubs.add("wit-sharpening-potion");
        imageStubs.add("fire-protection-potion");
        imageStubs.add("hair-raising-potion");
        imageStubs.add("memory-potion");
        imageStubs.add("pepperup-potion");
        imageStubs.add("strengthening-solution");
        imageStubs.add("draught-of-peace");
        imageStubs.add("essence-of-insanity");
        imageStubs.add("babbling-beverage");
        imageStubs.add("baruffio-s-brain-elixir");
        imageStubs.add("befuddlement-draught");
        imageStubs.add("calming-draught");
        imageStubs.add("cheese-based-potions");
        imageStubs.add("sleeping-draught");

        characteristics = new ArrayList<>();
        effects = new ArrayList<>();
        makeCookbook();
//        identifyPotion(); // remove/reconfigure later

        prepareGUI();
    }

    public static void pull() throws ParseException {
        String output = "abc";
        try {
            String urlTemp = "https://wizard-world-api.herokuapp.com/Elixirs";
            urlStub = linkStubs.get(potionIndex);
            urlTemp += urlStub;
            URL url = new URL(urlTemp);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {

                throw new RuntimeException("Darn! : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));


            while ((output = br.readLine()) != null) {
                totlaJson = output;
            }
            if(!cookbookDone) {
                JSONParser parser = new JSONParser();
                org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) parser.parse(totlaJson);
                JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                org.json.simple.JSONArray ingArray = (org.json.simple.JSONArray) jsonObject.get("ingredients");
                for (int i = 0; i < ingArray.size(); i++) {
                    JSONObject allInfo = (JSONObject) (ingArray.get(i));
                    String ingName = (String) (allInfo.get("name"));
                    tempSet.add(ingName.toLowerCase());
                }
                String details = (String) jsonObject.get("characteristics");
                characteristics.add(details);
                details = (String) jsonObject.get("effect");
                effects.add(details);

            } else {
                urlTemp = "https://api.potterdb.com/v1/potions/";
                urlStub = imageStubs.get(potionIndex);
                urlTemp += urlStub;
                url = new URL(urlTemp);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() != 200) {

                    throw new RuntimeException("Darn! : HTTP error code : "
                            + conn.getResponseCode());
                }

                br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));


                while ((output = br.readLine()) != null) {
                    totlaJson = output;
                }

                JSONParser parser = new JSONParser();
//                org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) parser.parse(totlaJson);
                JSONObject jsonObject = (JSONObject) parser.parse(totlaJson);
                jsonObject = (JSONObject) jsonObject.get("data");
                jsonObject = (JSONObject) jsonObject.get("attributes");
                imageLink = (String) jsonObject.get("image");

            }
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void prepareGUI() {
        // Sets up our "canvas"
        mainFrame = new JFrame("Potions Class");
        mainFrame.setSize(WIDTH, HEIGHT); // Use this to change size
        mainFrame.setLayout(new GridLayout(3, 1));

        //menu at top
        cut = new JMenuItem("cut");
        copy = new JMenuItem("copy");
        paste = new JMenuItem("paste");
        selectAll = new JMenuItem("selectAll");
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);


        ta = new JTextArea("Ingredients");
        ta.setLineWrap(true);
        ta.setFont(new Font("Times", Font.PLAIN, 14));

        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setSize(350, 100);

        imageLabel = new JLabel();
        imageLabel.setSize(200, 200);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 1)); //set the layout of the panel
        imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(1, 1));
        potionPanel = new JPanel();
        potionPanel.setLayout(new GridLayout(1, 2));

        mainFrame.add(ta);
        potionPanel.add(statusLabel);
        imagePanel.add(imageLabel);
        potionPanel.add(imagePanel);
        mainFrame.add(potionPanel);
        mainFrame.add(controlPanel);
//            mainFrame.setVisible(true);
    }

    private void showEventDemo() {

        JButton button1 = new JButton("Generate Random");
        JButton button2 = new JButton("Brew!");
//        JButton button3 = new JButton("button 3");
//        JButton button4 = new JButton("button 4");
//        JButton button5 = new JButton("button 5");

        button1.setActionCommand("button1");
        button2.setActionCommand("button2");
//        button3.setActionCommand("button3");
//        button4.setActionCommand("button4");
//        button5.setActionCommand("button5");

        button1.addActionListener(new ButtonClickListener());
        button2.addActionListener(new ButtonClickListener());
//        button3.addActionListener(new ButtonClickListener());
//        button4.addActionListener(new ButtonClickListener());
//        button5.addActionListener(new ButtonClickListener());

        controlPanel.add(button1);
        controlPanel.add(button2);
//        mainFrame.add(button3);
//        mainFrame.add(button4);
//        mainFrame.add(button5);

        mainFrame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        // Because it inherits ActionLister, it must have actionPerformed defined
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("button1")) { // Checking what is being broadcasted
                int random = (int) (Math.random() * 14);
                String randomPotion = potionNames.get(random);
                ArrayList<String> ingredients = new ArrayList<String>(cookbook.get(randomPotion));
                String ingString = "";
                for(int i = 0; i < ingredients.size(); i++){
                    if (i == ingredients.size() - 1){
                        ingString += ingredients.get(i);
                    } else {
                        ingString += ingredients.get(i);
                        ingString += ", ";
                    }
                }
                ta.setText(ingString);
            }

            if (command.equals("button2")) { // Checking what is being broadcasted

//                    browse(true);
                urlStub = linkStubs.get(index);
                taInput = ta.getText();
//                try {
//                  pull();
//                } catch (ParseException ex) {
//                    throw new RuntimeException(ex);
//                }
                try {
                    identifyPotion();
                    pull();
                    imagePanel.removeAll();
                    addImage();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }


//                else if (command.equals("Submit")) {
//                    statusLabel.setText("Submit Button clicked.");
//                } else {
//                    statusLabel.setText("Cancel Button clicked.");
//                }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cut)
            ta.cut();
        if (e.getSource() == paste)
            ta.paste();
        if (e.getSource() == copy)
            ta.copy();
        if (e.getSource() == selectAll)
            ta.selectAll();
    }

    public void browse(boolean isNext) throws ParseException {


        statusLabel.setText("temp");

        JSONParser parser = new JSONParser();
        org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) parser.parse(totlaJson);
        JSONObject jsonObject = (JSONObject) jsonArray.get(0);

        try {
            String nameTemp = (String) jsonObject.get("name");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void identifyPotion() throws IOException {
        taInput = taInput.toLowerCase();
        // One big if check
        // Make an arraylist, scraping for each ingredient separated by comma (turn to lower)
        // If the arraylist index is not -1 (not found), then that's a true statement part of the ifs
        // If I want to generate random recipes in the future, I'd have to make an arraylist for each potion's ingredients
        // then check against each specific arraylist which might be horrific
        // Or just pull ingredients from the API for one of the potions? Then I'd only need to make an ArrayList for the names of the potions which it pulls from randomly
        HashSet<String> testingSet = new HashSet<>();
                // Add some kind of function that can scrape names + ingredients of each potion as an ArrayList?
        int lastIndex = 0;
        for (int i = 0; i < taInput.length() - 1; i++){
            String ingredient = "";
            String letter = taInput.substring(i, i+1);
            if(letter.equals(",")){
                ingredient = taInput.substring(lastIndex, i);
                lastIndex = i + 2;
                testingSet.add(ingredient);
            }
            if (i == taInput.length() - 2){
                ingredient = taInput.substring(lastIndex, taInput.length());
                testingSet.add(ingredient);
            }
        }

        for(Map.Entry<String, Set<String>> entry: cookbook.entrySet()){
                if (testingSet.containsAll(entry.getValue())) {
                    String tempName = entry.getKey();
                    potionIndex = potionNames.indexOf(tempName);
                    // write characteristics
                    potionInfo = "<html><body>Congratulations! You created ";
                    potionInfo += tempName;
                    potionInfo += "<br>Appearance: " + characteristics.get(potionIndex);
                    potionInfo += "<br>Effect: " + effects.get(potionIndex);
                    potionInfo += "</body></html>";
                    statusLabel.setText(potionInfo);
                    break;
                } else {
                    statusLabel.setText("Your potion blew up!");
                    imagePanel.removeAll();
                    addImage();
                }
         }
    }

    public void makeCookbook() throws ParseException {
        cookbook = new HashMap<>();
//        cookbook.put("Felix Felicis", new HashSet<>(tempSet)); // this would be potionNames.get(i)
        for(potionIndex = 0; potionIndex < potionNames.size(); potionIndex++) { // Iterate through potionNames
            tempSet = new HashSet<>(); // creates new identity
            pull();
            cookbook.put(potionNames.get(potionIndex), tempSet);
        }
        cookbookDone = true;
    }

    private void addImage() throws IOException {
        try {
            String path = "";

            if (statusLabel.getText().contains("blew")) { // if it exploded
                path = "https://i.pinimg.com/474x/88/a4/41/88a441db125792c6d982c0a955b79d5c.jpg";
            } else {

                path = imageLink;
                if (path.contains("https")) {
                    path = path.substring(path.indexOf("http"));
                }
            }


            URL url = new URL(path);
            BufferedImage ErrorImage = ImageIO.read(new File("Error.jpg"));
            BufferedImage inputImageBuff = ImageIO.read(url.openStream());


            ImageIcon inputImage;
            if (inputImageBuff != null) {
                inputImage = new ImageIcon(inputImageBuff.getScaledInstance(160, 150, Image.SCALE_SMOOTH));
                // = new JLabel();
                if (inputImage != null) {
                    imageLabel = new JLabel(inputImage);
                } else {
                    imageLabel =new JLabel(new ImageIcon(ErrorImage.getScaledInstance(160, 150, Image.SCALE_SMOOTH)));
                }
                imagePanel.removeAll();
                imagePanel.repaint();

                imagePanel.add(imageLabel);
                potionPanel.add(imagePanel);


            }
            else{
                imageLabel =new JLabel(new ImageIcon(ErrorImage.getScaledInstance(160, 150, Image.SCALE_SMOOTH)));

            }

        } catch (IOException e) {
            System.out.println(e);
            System.out.println("sadness");
            BufferedImage ErrorImage = ImageIO.read(new File("Error.jpg"));
            JLabel imageLabel = new JLabel(new ImageIcon(ErrorImage.getScaledInstance(160, 150, Image.SCALE_SMOOTH)));

            imagePanel.removeAll();
            imagePanel.repaint();
            imagePanel.add(imageLabel);
            potionPanel.add(imagePanel);

        }

        mainFrame.setVisible(true);
    }
}


