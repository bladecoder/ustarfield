package javax.microedition.lcdui;

import java.awt.TextArea;
import java.awt.BorderLayout;

public class Form extends Screen {
    
    public Form(String title) {     
        centerComponent = new java.awt.Panel();
        ((java.awt.Panel)component).add(centerComponent, BorderLayout.CENTER);
        setTitle(title);
    }

    public Form(String title, Item[] items) {
    }

    public int append(String str) {
        TextArea ta = new TextArea( "", 0, 0,
                        TextArea.SCROLLBARS_VERTICAL_ONLY);
        //ta.setBackground(java.awt.Color.WHITE);
        ta.setBackground(new java.awt.Color(0xffffff));
        ta.setEditable(false);
        ta.append(str+"\n");
        //((java.awt.Panel)centerComponent).add(ta);
        ((java.awt.Panel)component).remove(centerComponent);
        ((java.awt.Panel)component).add(ta, BorderLayout.CENTER);
        return 0;
    }
    
    public int append(Item item) {
        ((java.awt.Panel)centerComponent).add(item.c);
        return 0;
    }
}
