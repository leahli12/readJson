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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

//- Wit-Sharpening Potion
//- Fire-Protection Potion
//- Hair-Raising Potion
//- Memory Potion
//- Pepperup Potion
//- Scintillation Solution
//    - Powdered unicorn horn
//    - Runespoor eggs
//- Strengthening Solution
//- Draught of Peace
//- Essence of Insanity
//- Babbling Beverage
//- Baruffio’s Brain Elixir
//- Befuddlement Draught
//- Blood-Replenishing Potion
//- Calming Draught
//- Cheese-Based Potions
//- Sleeping Draught
//
// Manual Image Adding
//- Polyjuice Potion
//- Felix Felicis

// Program for print data in JSON format.
public class potionsClass implements ActionListener {

    private JFrame mainFrame;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private JMenuBar mb;
    private JMenu file, edit, help;
    private JMenuItem cut, copy, paste, selectAll;
    private JTextArea ta; //typing area
    private int WIDTH=800;
    private int HEIGHT=700;

    public static String url;
    public String id = "";
    public String name = "";
    public static String totlaJson="";
    public ArrayList<String> linkStubs;
    public int index = 0;
    public String optImg = ""; // For the ones that need manual images
    public ArrayList<String> potionNames;
    public static Map<String, Set<String>> cookbook;
    public boolean makingCookbook;


    public static void main(String args[]) throws ParseException {
        // In java JSONObject is used to create JSON object
        // which is a subclass of java.util.HashMap.
        potionsClass screen = new potionsClass();
        screen.showEventDemo();
    }

    public potionsClass(){
        identifyPotion(); // remove/reconfigure later
        makeCookbook();
        potionNames = new ArrayList<>();
        potionNames.add("Wit-Sharpening Potion");
        potionNames.add("Fire-Protection Potion");
        potionNames.add("Hair-Raising Potion");
        potionNames.add("Memory Potion");
        potionNames.add("Pepperup Potion");
        potionNames.add("Scintillation Solution");
        potionNames.add("Strengthening Solution");
        potionNames.add("Draught of Peace");
        potionNames.add("Essence of Insanity");
        potionNames.add("Babbling Beverage");
        potionNames.add("Baruffio's Brain Elixir");
        potionNames.add("Befuddlement Draught");
        potionNames.add("Calming Draught");
        potionNames.add("Cheese-Based Potions");
        potionNames.add("Sleeping Draught");
        System.out.println(potionNames);
        linkStubs = new ArrayList<>();
        linkStubs.add("?name=Wit");
        linkStubs.add("?name=Fire-Protection");
        linkStubs.add("?name=Hair-Raising");
        linkStubs.add("?name=Memory");
        linkStubs.add("?name=Pepperup");
        linkStubs.add("?name=Scintillation");
        linkStubs.add("?name=Strengthening");
        linkStubs.add("?name=Draught");
        linkStubs.add("?name=Essence");
        linkStubs.add("?name=Babbling");
        linkStubs.add("?name=Baruffio's");
        linkStubs.add("?name=Befuddlement");
        linkStubs.add("?name=Calming");
        linkStubs.add("?name=Cheese");
        linkStubs.add("?name=Sleeping");
        linkStubs.add("?name=Polyjuice");
        linkStubs.add("?name=Felix");

        prepareGUI();
    }

    public static void pull() throws ParseException {
        String output = "abc";
        try {
            String urlTemp = "https://wizard-world-api.herokuapp.com/Elixirs";
            urlTemp += url;
//            System.out.println(urlTemp);
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


//            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                totlaJson = output;
            }

            if(!cookbook.isEmpty()) {
                JSONParser parser = new JSONParser();
                org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) parser.parse(totlaJson);
                JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                String nameTemp = (String) jsonObject.get("name");
                String name = "Name: " + nameTemp;
                System.out.println(name);
            } else {

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
        mainFrame.setLayout(new GridLayout(2, 1));

        //menu at top
        cut = new JMenuItem("cut");
        copy = new JMenuItem("copy");
        paste = new JMenuItem("paste");
        selectAll = new JMenuItem("selectAll");
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);

//            mb = new JMenuBar();
//            file = new JMenu("File");
//            edit = new JMenu("Edit");
//            help = new JMenu("Help");
//            edit.add(cut);
//            edit.add(copy);
//            edit.add(paste);
//            edit.add(selectAll);
//            mb.add(file);
//            mb.add(edit);
//            mb.add(help);
        //end menu at top

        ta = new JTextArea();
        ta.setBounds(50, 5, WIDTH-100, HEIGHT-50);
//            mainFrame.add(mb);  //add menu bar
//            mainFrame.add(ta);//add typing area
//            mainFrame.setJMenuBar(mb); //set menu bar

        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setSize(350, 100);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1, 2)); //set the layout of the panel
        mainFrame.add(statusLabel);
        mainFrame.add(controlPanel);
//            mainFrame.setVisible(true);
    }

    private void showEventDemo() {

        JButton button1 = new JButton("<-- Previous");
        JButton button2 = new JButton("Next -->");
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
                try {
                    browse(false);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }

            if (command.equals("button2")) { // Checking what is being broadcasted
                try {
//                    browse(true);
                    url = linkStubs.get(index);
                    index++;
//                    System.out.println(url);
                    pull();
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
//        statusLabel.setText(test);
    }
    // statusLabel.setText("Ok Button clicked.");

    public void browse(boolean isNext) throws ParseException {


        statusLabel.setText("temp");

        JSONParser parser = new JSONParser();
        //System.out.println(str);
        org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) parser.parse(totlaJson);
        JSONObject jsonObject = (JSONObject) jsonArray.get(0);

        try {
            String nameTemp = (String) jsonObject.get("name");
            name = "Name: " + nameTemp;
            System.out.println(name);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void identifyPotion(){
        String tester = "Salamander blood, bursting mushrooms, Wartcap powder";
        tester = tester.toLowerCase();
        // One big if check
        // Make an arraylist, scraping for each ingredient separated by comma (turn to lower)
        // If the arraylist index is not -1 (not found), then that's a true statement part of the ifs
        // If I want to generate random recipes in the future, I'd have to make an arraylist for each potion's ingredients
        // then check against each specific arraylist which might be horrific
        // Or just pull ingredients from the API for one of the potions? Then I'd only need to make an ArrayList for the names of the potions which it pulls from randomly
        HashSet<String> testingSet = new HashSet<>();
                // Add some kind of function that can scrape names + ingredients of each potion as an ArrayList?
        int lastIndex = 0;
//        for (int i = 0; i < tester.length() - 1; i++){
//            String ingredient = "";
//            String letter = tester.substring(i, i+1);
//            if(letter.equals(",")){
//                System.out.println(i);
//                ingredient = tester.substring(lastIndex, i);
//                lastIndex = i + 2;
//                userIngredients.add(ingredient);
//                System.out.println(userIngredients);
//            }
//            if (i == tester.length() - 2){
//                ingredient = tester.substring(lastIndex, tester.length());
//                userIngredients.add(ingredient);
//                System.out.println(userIngredients);
//            }
//        }

        for (int i = 0; i < tester.length() - 1; i++){
            String ingredient = "";
            String letter = tester.substring(i, i+1);
            if(letter.equals(",")){
                System.out.println(i);
                ingredient = tester.substring(lastIndex, i);
                lastIndex = i + 2;
                testingSet.add(ingredient);
                System.out.println(testingSet);
            }
            if (i == tester.length() - 2){
                ingredient = tester.substring(lastIndex, tester.length());
                testingSet.add(ingredient);
                System.out.println(testingSet);
            }
        }

        for(Map.Entry<String, Set<String>> entry: cookbook.entrySet()){
            System.out.println(entry);
                if (testingSet.containsAll(entry.getValue())) {
                    System.out.println(entry.getKey());
                    System.out.println("whoooo");
                }
            System.out.println("hi");
        }
    }

    public void makeCookbook(){
        cookbook = new HashMap<>();
        HashSet<String> hmm = new HashSet<String>();
        hmm.add("bursting mushrooms");
        hmm.add("wartcap powder");
        hmm.add("salamander blood");
        cookbook.put("Felix Felicis", new HashSet<>(hmm)); // this would be potionNames.get(i)
        for(int i = 0; i < potionNames.size(); i++){ // Iterate through potionNames

        }
    }
}


