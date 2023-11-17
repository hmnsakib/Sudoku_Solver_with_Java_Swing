import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainInterface extends JFrame {
    JLabel h1, h2, t0, t1, t2;
    JButton solve, reset, sample1, sample2;
    Font h1Font, h2Font, h3Font;
    MainInterface(){
        //main frame
        setTitle("Sudoku Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1110, 680));
        //ImageIcon icon = new ImageIcon(getClass().getResource("sudoku.png"));
        //this.setIconImage(icon.getImage());

        //main container
        Container c = this.getContentPane();
        c.setLayout(null);
        c.setBackground(new Color(25, 73, 114));

        //fonts
        h1Font = new Font("Arial", Font.BOLD, 20);
        h2Font = new Font("Arial",Font.PLAIN, 16);
        h3Font = new Font("Arial",Font.PLAIN, 18);

        //size
        int frameWidth = c.getWidth();
        int w50 = frameWidth/2;
        int w20 = frameWidth/5;

        //header
        h1 = new JLabel("Sudoku Solver", SwingConstants.CENTER);
        h1.setBounds(0, 0,1000,50);
        h1.setFont(h1Font);
        h1.setForeground(Color.WHITE);
        c.add(h1);

        //sub header
        h2 = new JLabel("Please enter Sudoku numbers in specific position", SwingConstants.CENTER);
        h2.setBounds(0, 30,1000,50);
        h2.setFont(h2Font);
        h2.setForeground(Color.WHITE);
        c.add(h2);

        //create input field
        JPanel l = new JPanel(new GridLayout(9,9,2,2));
        l.setBackground(new Color(25, 73, 114));
        l.setBounds(30,100,500,500);
        c.add(l);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField field = new JTextField();
                field.setHorizontalAlignment(JTextField.CENTER);
                field.setFont(h2Font);
                Border border1 = BorderFactory.createLineBorder(Color.BLACK, 0, false);
                field.setBorder(border1);
                l.add(field);
                field.addFocusListener(new CellFocusListener(field, i, j, l));
            }
        }

        //right container
        JPanel r = new JPanel();
        r.setBackground(new Color(25, 73, 114));
        Border border2 = BorderFactory.createLineBorder(Color.WHITE, 1, true);
        r.setBorder(border2);
        r.setBounds(560,100,500,500);
        r.setLayout(null);
        c.add(r);

        t0 = new JLabel("Features", SwingConstants.CENTER);
        t0.setBounds(0, 0,500,50);
        t0.setFont(h3Font);
        t0.setForeground(Color.WHITE);
        r.add(t0);

        t1 = new JLabel("Solving Features", SwingConstants.LEFT);
        t1.setBounds(20, 50,500,50);
        t1.setFont(h2Font);
        t1.setForeground(Color.WHITE);
        r.add(t1);

        solve = new JButton("Solve");
        solve.setBounds(20,100,110,30);
        solve.setBackground(new Color(211,211,211));
        solve.setFont(h2Font);
        r.add(solve);

        reset = new JButton("Reset");
        reset.setBounds(150,100,110,30);
        reset.setBackground(new Color(211,211,211));
        reset.setFont(h2Font);
        r.add(reset);

        t2 = new JLabel("Load Sample", SwingConstants.LEFT);
        t2.setBounds(20, 150,500,50);
        t2.setFont(h2Font);
        t2.setForeground(Color.WHITE);
        r.add(t2);

        sample1 = new JButton("Sample 1");
        sample1.setBounds(20,200,110,30);
        sample1.setBackground(new Color(211,211,211));
        sample1.setFont(h2Font);
        r.add(sample1);

        sample2 = new JButton("Sample 2");
        sample2.setBounds(150,200,110,30);
        sample2.setBackground(new Color(211,211,211));
        sample2.setFont(h2Font);
        r.add(sample2);
        


        setVisible(true);
    }

    public static void main(String[] args) {
        MainInterface mainWin = new MainInterface();
    }
}

//