import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class CellFocusListener implements FocusListener {
    private JTextField field;
    private int i;
    private  int j;
    private  JPanel l;

    public CellFocusListener(JTextField field, int i, int j, JPanel l){
        this.field = field;
        this.i = i;
        this.j = j;
        this.l = l;
    }

    @Override
    public void focusGained(FocusEvent e){
        highlightMethod(true);
    }

    @Override
    public void focusLost(FocusEvent e){
        highlightMethod(false);
    }

    public void highlightMethod(boolean highlight){

        //row
        for(int c = 0; c < 9; c++){
            //highlightCall(highlight, i, c);
            Component component = l.getComponent(i*9+c);
            if(component instanceof JTextField){
                component.setBackground(highlight ? new Color(197, 232, 152) : UIManager.getColor("TextField.background"));
            }
        }

        //col
        for(int r = 0; r < 9; r++){
            //highlightCall(highlight, r, j);
            Component component = l.getComponent(r*9+j);
            if(component instanceof JTextField){
                component.setBackground(highlight ? new Color(197, 232, 152) : UIManager.getColor("TextField.background"));
            }
        }

        //3x3 sub grid
        int startRow = (i/3)*3;
        int startCol = (j/3)*3;

        for(int r = startRow; r < startRow + 3; r++){
            for (int c = startCol; c < startCol + 3; c++){
                highlightCall(highlight, c, r);
            }
        }

        //cell
        field.setBackground(highlight ? new Color(210, 222, 50) : UIManager.getColor("TextField.background"));
    }

    private void highlightCall(boolean highlight, int c, int r){
        Component component = l.getComponent(r*9+c);
        if(component instanceof JTextField){
            component.setBackground(highlight ? new Color(197, 232, 152) : UIManager.getColor("TextField.background"));
        }
    }
}
