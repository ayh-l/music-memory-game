package ui;

// (REFERENCED: https://docs.oracle.com/javase/tutorial/uiswing/layout/card.html)

//TODO: remember max method length

import model.RoundHistory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoundPlayerVisual implements ActionListener {
    private static final String GAME_PANEL_TXT = "Round";
    private static final String MENU_PANEL_TXT = "Menu";
    private static final String HIST_PANEL_TXT = "History";
    private final GamePanel gp;
    private final MenuPanel mp;
    private final HistoryPanel hp;
    private RoundHistory roundHistory;
    private final JFrame frame;
    private final JPanel cards;

    public RoundPlayerVisual() {
        roundHistory = new RoundHistory();
        gp = new GamePanel(roundHistory);
        mp = new MenuPanel(this);
        hp = new HistoryPanel(this);
        frame = new JFrame();

        cards = new JPanel(new CardLayout());
        cards.setPreferredSize(new Dimension(500, 500));
        cards.add(gp, GAME_PANEL_TXT);
        cards.add(mp, MENU_PANEL_TXT);
        cards.add(hp, HIST_PANEL_TXT);
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, MENU_PANEL_TXT);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 500));
        ((JPanel) frame.getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        frame.add(cards);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        CardLayout cl = (CardLayout)(cards.getLayout());

        if (command.equals("menu: new round")) {
            //TODO
        }
        if (command.equals("menu: history")) {
            hp.resetFilterDisplay();
            hp.update();
            cl.show(cards, HIST_PANEL_TXT);
        }
        if (command.equals("hist: menu")) {
            cl.show(cards, MENU_PANEL_TXT);
        }
    }

    public RoundHistory getRoundHistory() {
        return roundHistory;
    }

    public void setRoundHistory(RoundHistory rh) {
        roundHistory = rh;
    }

    public HistoryPanel getHistoryPanel() {
        return hp;
    }

}
