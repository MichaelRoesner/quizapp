package app.utility;

import javax.swing.*;
import java.awt.*;

import app.MainFrame;
import app.logic.SoundPlayer;
import app.utility.ResourcePath;

public class CloseButton extends JButton {

    private final MainFrame parentFrame;

    public CloseButton(MainFrame parentFrame) {
        super("X");
        this.parentFrame = parentFrame;
        
        init();
    }

    private void init() {
        setFont(new Font("Arial", Font.BOLD, 15));
        setBackground(Color.GRAY);
        setOpaque(true);
        setBorder(null);
        setFocusPainted(false);

        addActionListener(e -> {
            SoundPlayer.playSound(ResourcePath.SOUND_CLICK_PATH);
            if(parentFrame == null) {
                SwingUtilities.getWindowAncestor(this).dispose();
            } else {
                parentFrame.switchToWelcomePanel();
            }



        });
    }
}
