package client.components;
import javax.swing.*;

import resources.SetPreferences;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class POSDateTimeFrame extends JLabel{
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private LocalDateTime now = LocalDateTime.now();
    private SetPreferences preferences = new SetPreferences(); 
    
    public POSDateTimeFrame() {
        setFont(preferences.getFont(12));
        setText(now.format(formatter));
        setHorizontalAlignment(JLabel.CENTER);
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDateTime();
            }
        });
        timer.start();
    }
    
    private void updateDateTime() {
        LocalDateTime now = LocalDateTime.now();
        setText(now.format(formatter));
        setHorizontalAlignment(JLabel.CENTER);
    }
}
