package javax.microedition.lcdui;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;

public class List extends Screen implements Choice, ActionListener{
            
    public static final Command SELECT_COMMAND = 
        new Command("", Command.SCREEN, 0);

    public List(String title, int listType) {
        centerComponent = new java.awt.List();
        //centerComponent.setBackground(java.awt.Color.WHITE);
        centerComponent.setBackground(new java.awt.Color(0xffffff));

        ((java.awt.List)centerComponent).addActionListener(this);
        ((java.awt.Panel)component).add(centerComponent, BorderLayout.CENTER);
        setTitle(title);
    }

    public List(String title, int listType, String[] stringElements,
            Image[] imageElements) {
    }

    public int size() {
        return ((java.awt.List)centerComponent).getItemCount();
    }

    public String getString(int elementNum) {
        return ((java.awt.List)centerComponent).getItem(elementNum);
    }

    public Image getImage(int elementNum) {
        return null;
    }

    public int append(String stringPart, Image imagePart) {
        ((java.awt.List)centerComponent).add(stringPart);
        ((java.awt.List)centerComponent).select(0);
        
        return size() - 1;
    }

    public void insert(int elementNum,
                   String stringPart, Image imagePart) {
        ((java.awt.List)centerComponent).add(stringPart, elementNum);
        ((java.awt.List)centerComponent).select(0);
    }

    public void delete(int elementNum) {
        ((java.awt.List)centerComponent).remove(elementNum);
    }

    public void set(int elementNum,
                String stringPart,
                Image imagePart) {
    }

    public boolean isSelected(int elementNum) {
        return ((java.awt.List)centerComponent).isIndexSelected(elementNum);
    }

    public int getSelectedIndex() {
        return ((java.awt.List)centerComponent).getSelectedIndex();
    }

    public int getSelectedFlags(boolean[] selectedArray_return) {
        return 0;
    }

    public void setSelectedIndex(int elementNum,
                             boolean selected) {
        ((java.awt.List)centerComponent).select(elementNum);
    }

    public void setSelectedFlags(boolean[] selectedArray) {
    }

    public void actionPerformed(ActionEvent e) {
        if(listener != null) {
            Object c = commands.get(e.getActionCommand());

            if(c == null) c = SELECT_COMMAND;
            
            listener.commandAction((Command)c,this);
            //System.out.println("List: actionPerformed");
        }
    }
}
