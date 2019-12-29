
package gui;

import core.WeatherModel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxEditor;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

// from http://www.badkernel.com/2011/06/java-swing-dynamic-combo-box/
public class SearchBoxModel extends AbstractListModel 
                implements ComboBoxModel, KeyListener, ItemListener
{
    //List<String> dataBase; // the list of all potential data
    List<WeatherModel.City> dataBase;
    //List<String> data = new ArrayList<String>(); // the data the combo box will display
    List<WeatherModel.City> data = new ArrayList<WeatherModel.City>();
    
    //String selection;
    WeatherModel.City selection;
    JComboBox searchBox;
    ComboBoxEditor comboBoxEditor;
    int currPos = 0;


    public SearchBoxModel(JComboBox searchBox, List<WeatherModel.City> dataBase)
    {
        this.dataBase = dataBase;
        this.searchBox = searchBox;
        comboBoxEditor = searchBox.getEditor();
        //here we add the key listener to the text field that the combobox is wrapped around
        comboBoxEditor.getEditorComponent().addKeyListener(this);
    }

    public void updateModel(String userInput)
    {
        data.clear();
        //lets find any items which start with the string the user typed, and add it to the popup list
        /*for(String s : dataBase)
            if(s.startsWith(userInput))
                data.add(s);*/
        for (WeatherModel.City city : dataBase) {
            if (city.name.startsWith(userInput)) {
                data.add(city);
            }
        }

        super.fireContentsChanged(this, 0, data.size());

        //this is a hack to get around redraw problems when changing the list length of the displayed popups
        searchBox.hidePopup();
        searchBox.showPopup();
        //if(data.size() != 0)
            //searchBox.setSelectedIndex(0);
    }

    public int getSize(){return data.size();}
    public Object getElementAt(int index){return data.get(index);}
    
    /*public void setSelectedItem(Object anItem)
                                 {selection = (String) anItem;}
    public Object getSelectedItem(){return selection;}*/
    
    public void setSelectedItem(Object anItem)
                                 {selection = (WeatherModel.City) anItem;}
    public Object getSelectedItem(){return selection;}
    
    public void keyTyped(KeyEvent e){}
    public void keyPressed(KeyEvent e){}

    public void keyReleased(KeyEvent e)
    {
        System.out.println("key released called");
        String str = comboBoxEditor.getItem().toString();
        System.out.println("comboxBoxEditor.item = " + str);
        JTextField jtf = (JTextField) comboBoxEditor.getEditorComponent();
        currPos = jtf.getCaretPosition();

        if(e.getKeyChar() == KeyEvent.CHAR_UNDEFINED)
        {
            if(e.getKeyCode() != KeyEvent.VK_ENTER )
            {
                comboBoxEditor.setItem(str);
                jtf.setCaretPosition(currPos);
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_ENTER)
            searchBox.setSelectedIndex(searchBox.getSelectedIndex());
        else
        {
            updateModel(str);
            comboBoxEditor.setItem(str);
            jtf.setCaretPosition(currPos);
        }
    }

    public void itemStateChanged(ItemEvent e)
    {
        System.out.println("searchBoxModel.itemStateChanged() called");
        if (e.getItem() instanceof WeatherModel.City) {
            System.out.println("e.getItem() is a City");
        } else if (e.getItem() instanceof String) {
            System.out.println("e.getItem() is a String");
        }
        comboBoxEditor.setItem(e.getItem().toString());
        searchBox.setSelectedItem(e.getItem());
    }
}
