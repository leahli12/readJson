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
//Manual Image Adding
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

    public String id = "";
    public String name = "";
    public String affiliation = "";
    public String allies = "";
    public String enemies = "";
    public int index = -1; // max 19
    public static String totlaJson="";
    public String temp;


    public static void main(String args[]) throws ParseException {
        // In java JSONObject is used to create JSON object
        // which is a subclass of java.util.HashMap.
        potionsClass screen = new potionsClass();
        screen.showEventDemo();
        JSONObject file = new JSONObject();
        file.put("Full Name", "Ritu Sharma");
        file.put("Roll No.", new Integer(1704310046));
        file.put("Tuition Fees", new Double(65400));

        // To print in JSON format.
//        System.out.print(file.get("Tuition Fees"));
//        System.out.println(file.get("Full Name" ));
        pull();
    }

    public potionsClass(){
        prepareGUI();
    }

    public static void pull() throws ParseException {
        String output = "abc";
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
                totlaJson += output;
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
        mainFrame = new JFrame("Leah learning Swing");
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

        button1.setActionCommand("button2");
        button2.setActionCommand("button1");
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
                    browse(true);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }

            if (command.equals("button2")) { // Checking what is being broadcasted
                try {
                    browse(false);
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
        if (isNext) {
            if (index < 19) {
                index += 1;
            } else {
                index = 0;
            }
        }
        else {
            if (index <= 0){
                index = 19;
            } else {
                index -= 1;
            }
        }
        temp = "<html>" + name + "<br>";
        if(!affiliation.equals("")){
            temp += affiliation + "<br>";
        } else {
            temp += "No affiliation <br>";
        }
        if(!allies.equals("")){
            temp += allies + "<br>";
        } else {
            temp += "No allies <br>";
        }
        if(!enemies.equals("")){
            temp += enemies + "<br>";
        } else {
            temp += "No enemies <br>";
        }
        temp += "</html>";

        statusLabel.setText(temp);

        JSONParser parser = new JSONParser();
        //System.out.println(str);
        org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) parser.parse(totlaJson);
        JSONObject jsonObject = (JSONObject) jsonArray.get(index);

        try {
            String nameTemp = (String) jsonObject.get("name");
            name = "Name: " + nameTemp;
//                System.out.println(name);

            org.json.simple.JSONArray msg = (org.json.simple.JSONArray) jsonObject.get("allies");
            String test = (String) msg.get(0);
            allies = "";
            if (msg.size() != 0){
                test = (String) msg.get(0);
//                    System.out.println(allies);
                allies = "Allies: " + test;
            }
//                System.out.println(allies);
            // System.out.println(person.getInt("key"));

            msg = (org.json.simple.JSONArray) jsonObject.get("enemies");
            enemies = "";
            if (msg.size() != 0) {
                test = (String) msg.get(0);
                enemies = "Enemies: " + test;
//                    System.out.println(enemies);
                // System.out.println(person.getInt("key"));
            }

            String affTemp = (String) jsonObject.get("affiliation");
            if(affTemp != null){
                affiliation = "Affiliation: " + affTemp;
            } else {
                affiliation = "";
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}


