package javax.microedition.lcdui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.microedition.midlet.*;

public class Display {
    private static Display display = new Display();

    private Displayable current = null;
    public java.awt.Panel panel;
    
    public static Display getDisplay(MIDlet m) {
        return display;
    }

    public void setCurrent(Displayable nextDisplayable) {
        if(current != null) panel.remove(current.component);

        current = nextDisplayable;
        current.component.setSize(panel.getSize());
        panel.add("Center", current.component);
        panel.validate();
        panel.repaint();
        if(current.centerComponent != null)
            current.centerComponent.requestFocus();
        else current.component.requestFocus();
    }

    class Close implements ActionListener {
        private java.awt.Dialog d;
        
        public Close(java.awt.Dialog d) {
            this.d = d;
        }
        
        /*
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
              d.dispose();
        }        
    }
    
    public void setCurrent(Alert alert,
                       Displayable nextDisplayable) {
        java.awt.Dialog d = new java.awt.Dialog((java.awt.Frame)panel.getParent().getParent(), alert.getTitle(), true);
        java.awt.Button b = new java.awt.Button("Exit");
        b.addActionListener(new Close(d));
        d.add("Center", new java.awt.Label(alert.getString()));
        d.add("South", b);
        d.setLocation(100,100);
        d.setSize(300,100);
        d.show();
        setCurrent(nextDisplayable);
    }
    
    public Displayable getCurrent() {
        return current;
    }


}
