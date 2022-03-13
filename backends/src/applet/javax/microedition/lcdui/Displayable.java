package javax.microedition.lcdui;

import java.awt.Component;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Button;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Hashtable;

public class Displayable implements ActionListener {
    Component component;
    Component centerComponent;
    Label label;
    Panel buttonPanel;

    Hashtable commands;

    protected CommandListener listener;

    Displayable() {
        Panel p;

        commands = new Hashtable();
        
        label = new Label();
        label.setAlignment(Label.CENTER);
        component = p = new Panel();
        p.setLayout(new BorderLayout());
        p.add(label, BorderLayout.NORTH);
        buttonPanel = new Panel();
        buttonPanel.setLayout(new FlowLayout());
        p.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void addCommand(Command cmd) {
        Button b = new Button(cmd.getLabel());
        b.addActionListener(this);
        b.setActionCommand(cmd.getLabel());
        buttonPanel.add(b);
        commands.put(cmd.getLabel(), cmd);
    }
    
    public boolean isShown() {
        return true;
    }
    
    public void removeCommand(Command cmd) {
        System.out.println("Displayable::removeCommand NOT implemented!");
    }
    
    public void setCommandListener(CommandListener l)  {
        listener = l;
    }

    public void actionPerformed(ActionEvent e) {
        listener.commandAction(
                (Command)commands.get(e.getActionCommand()), this);
    }
}
