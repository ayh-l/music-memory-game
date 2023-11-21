package ui;

// (REFERENCED: https://docs.oracle.com/javase/tutorial/uiswing/components/button.html,
//              https://stackoverflow.com/questions/6578205/swing-jlabel-text-change-on-the-running-application
//              https://docs.oracle.com/javase/tutorial/uiswing/layout/box.html)

//TODO cleanup, max method length

import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

//TODO: add documentation for all new classes!
public class MenuPanel extends JPanel implements ActionListener {
    private static final String JSON_STORE = "./data/roundHistory.json";
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private JButton newRoundButton;
    private JButton roundHistoryButton;
    private JButton saveButton;
    private JButton loadButton;
    private JLabel titleLabel;
    private JLabel message;
    private final RoundPlayerVisual roundPlayerVisual;

    // EFFECTS: constructs a menu panel matching given round player
    public MenuPanel(RoundPlayerVisual rpv) {
        this.roundPlayerVisual = rpv;
        this.setPreferredSize(new Dimension(500, 500));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        initializeButtons(rpv);
        initializeLabels();

        display();
    }

    private void display() {
        this.add(titleLabel);
        this.add(Box.createRigidArea(new Dimension(0, 15)));
        this.add(newRoundButton, BorderLayout.PAGE_START);
        this.add(saveButton, BorderLayout.LINE_START);
        this.add(loadButton, BorderLayout.LINE_END);
        this.add(roundHistoryButton, BorderLayout.PAGE_END);
        this.add(message);
        setVisible(true);
    }

    private void initializeLabels() {
        titleLabel = new JLabel("MENU");
        message = new JLabel("");
    }

    private void initializeButtons(RoundPlayerVisual rpv) {
        newRoundButton = new JButton("Start new round");
        roundHistoryButton = new JButton("View game history");
        loadButton = new JButton("Load round history");
        saveButton = new JButton("Save round history");
        newRoundButton.setActionCommand("menu: new round");
        roundHistoryButton.setActionCommand("menu: history");
        saveButton.setActionCommand("save");
        loadButton.setActionCommand("load");
        newRoundButton.addActionListener(rpv);
        roundHistoryButton.addActionListener(rpv);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: saves or loads round history according to action event
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("save")) {
            this.saveRoundHistory();
            this.roundPlayerVisual.getHistoryPanel().resetFilterDisplay();
        }
        if (command.equals("load")) {
            this.loadRoundHistory();
        }
    }

    // EFFECTS: saves round history to file
    private void saveRoundHistory() {
        try {
            jsonWriter.open();
            jsonWriter.write(roundPlayerVisual.getRoundHistory());
            jsonWriter.close();
            message.setText("Saved successfully to file " + JSON_STORE);
        } catch (FileNotFoundException e) {
            message.setText("Could not write to file " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads saved round history from file
    private void loadRoundHistory() {
        try {
            roundPlayerVisual.setRoundHistory(jsonReader.read());
            message.setText("Successfully loaded from file " + JSON_STORE);
        } catch (IOException e) {
            message.setText("Could not load from file " + JSON_STORE);
        }
    }
}
