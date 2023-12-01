package ui;

// (REFERENCED: https://docs.oracle.com/javase/tutorial/uiswing/layout/card.html)

import model.EventLog;
import model.RoundHistory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

// Represents a visual round player
public class RoundPlayerVisual implements ActionListener, WindowListener {
    private static final String GAME_PANEL_TXT = "Round";
    private static final String MENU_PANEL_TXT = "Menu";
    private static final String HIST_PANEL_TXT = "History";
    private final GamePanel gp;
    private final HistoryPanel hp;
    private final JPanel cards;
    private RoundHistory roundHistory;

    // EFFECTS: constructs a visual round player with new game, menu and history panels. Displays the menu panel.
    public RoundPlayerVisual() {
        roundHistory = new RoundHistory();
        gp = new GamePanel(this);
        MenuPanel mp = new MenuPanel(this);
        hp = new HistoryPanel(this);
        JFrame frame = new JFrame();

        cards = new JPanel(new CardLayout());
        cards.setPreferredSize(new Dimension(500, 500));
        cards.add(gp, GAME_PANEL_TXT);
        cards.add(mp, MENU_PANEL_TXT);
        cards.add(hp, HIST_PANEL_TXT);
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, MENU_PANEL_TXT);

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 500));
        ((JPanel) frame.getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        frame.add(cards);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.addWindowListener(this);
    }

    // MODIFIES: this
    // EFFECTS: according to the event, switches the visible panel and preps those panels as needed.
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        CardLayout cl = (CardLayout)(cards.getLayout());

        if (command.equals("menu: new round")) {
            cl.show(cards, GAME_PANEL_TXT);
            gp.reset();
        }
        if (command.equals("menu: history")) {
            hp.resetFilterDisplay();
            hp.update();
            cl.show(cards, HIST_PANEL_TXT);
        }
        if (command.equals("hist: menu") || command.equals("game: menu")) {
            cl.show(cards, MENU_PANEL_TXT);
        }
    }

    // (REFRENCED: AlarmSystem - Screen Printer - printLog())
    // EFFECTS: upon window closing, prints out all activity from startup
    @Override
    public void windowClosed(WindowEvent e) {
        for (model.Event next : EventLog.getInstance()) {
            System.out.println(next.toString() + "\n\n");
        }
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    // setters
    public void setRoundHistory(RoundHistory rh) {
        roundHistory = rh;
    }

    // getters
    public RoundHistory getRoundHistory() {
        return roundHistory;
    }

    public HistoryPanel getHistoryPanel() {
        return hp;
    }
}
