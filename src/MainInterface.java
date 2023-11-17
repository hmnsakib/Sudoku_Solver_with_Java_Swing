import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainInterface extends JFrame {
    JLabel h1, h2, t0, t1, t2, t3;
    JButton solve, reset, sample1, sample2;
    Font h1Font, h2Font, h3Font;

    JTextArea statusBox;

    public JTextField[][] fieldsRef = new JTextField[9][9];

    //---------------------------------main constructor----------------------------------
    public MainInterface(){
        //main frame
        setTitle("Sudoku Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1110, 680));
        ImageIcon icon = new ImageIcon("sudoku.png");
        setIconImage(icon.getImage());

        //main container
        Container c = this.getContentPane();
        c.setLayout(null);
        c.setBackground(new Color(25, 73, 114));

        //fonts
        h1Font = new Font("Arial", Font.BOLD, 20);
        h2Font = new Font("Arial",Font.PLAIN, 16);
        h3Font = new Font("Arial",Font.PLAIN, 18);

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
        int gap = 2;
        JPanel l = new JPanel(new GridLayout(9,9,gap,gap));
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

                fieldsRef [i][j] = field;
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
        solve.addActionListener(e -> SolveListener());
        r.add(solve);

        reset = new JButton("Reset");
        reset.setBounds(150,100,110,30);
        reset.setBackground(new Color(211,211,211));
        reset.setFont(h2Font);
        reset.addActionListener(e -> ResetListener());
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
        sample1.addActionListener(e -> loadSample1());
        r.add(sample1);

        sample2 = new JButton("Sample 2");
        sample2.setBounds(150,200,110,30);
        sample2.setBackground(new Color(211,211,211));
        sample2.setFont(h2Font);
        sample2.addActionListener(e -> loadSample2());
        r.add(sample2);

        t3 = new JLabel("Status", SwingConstants.LEFT);
        t3.setBounds(20, 250,500,50);
        t3.setFont(h2Font);
        t3.setForeground(Color.WHITE);
        r.add(t3);

        statusBox = new JTextArea();
        statusBox.setBounds(20, 300,460,100);
        statusBox.setFont(h2Font);
        statusBox.setForeground(Color.RED);
        r.add(statusBox);



        setVisible(true);
    }

    //---------------------------------solve button listener----------------------------------
    public void SolveListener(){
        boolean allEmpty = true;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String value = fieldsRef[i][j].getText();
                //System.out.println("Row: " + i + ", Column: " + j + ", Value: " + value);

                if(!value.isEmpty()){
                    allEmpty = false;
                }
            }
        }

        if (allEmpty){
            JOptionPane.showMessageDialog(null, "All cells are empty!", "Warning!", 2);
        }
        else {
            helper(fieldsRef, 0, 0);
            statusBox.setText("Sudoku solved!");
        }
    }

    //---------------------------------highlight invalid input----------------------------------
    /*public void highlightIncorrectCells() {
        boolean isUnique = true;
        // check row
        for( int row = 0; row < 9; row++){
            for (int col = 0; col < 9; col++){
                for (int nrow = 0; nrow < 9; nrow++){
                    for (int ncol = 0; ncol < 9; ncol++){
                        if(fieldsRef[row][col].getText().equals(fieldsRef[nrow][ncol])){
                            fieldsRef[row][col].setBackground(Color.RED);
                            break;
                        }
                    }
                }
            }
        }
    }*/


    //---------------------------------check validation----------------------------------
    public boolean isValid(JTextField[][] fieldsRef, String num, int row, int col) {
        //check safe in row and col
        for (int i = 0 ; i < 9; i++){
            if(num.equals(fieldsRef[row][i].getText())){
                return false;
            }
            if(num.equals(fieldsRef[i][col].getText())){
                return false;
            }

        }
        //check safe in 3x3 grid
        int sRow = (row/3)*3;
        int sCol = (col/3)*3;
        for (int i = sRow; i < sRow + 3; i++){
            for (int j = sCol; j < sCol + 3; j++) {
                if(num.equals(fieldsRef[i][j].getText())){
                    return false;
                }
            }
        }
        return true;
    }

    //---------------------------------implement number with recursion----------------------------------
    public boolean helper(JTextField[][] fieldsRef, int row, int col){

        if(row == fieldsRef.length){
            return true;
        }

        int nrow = 0;
        int ncol = 0;
        if(col != fieldsRef.length-1){
            nrow = row;
            ncol = col + 1;
        }
        else {
            nrow = row + 1;
            ncol = 0;
        }

        if(!fieldsRef[row][col].getText().isEmpty()){
            if(helper(fieldsRef, nrow, ncol)){
                return  true;
            }
        }
        else {
            for (int i = 1; i <=9; i++) {
                if(isValid(fieldsRef, Integer.toString(i), row, col)){
                    fieldsRef[row][col].setText(Integer.toString(i));
                    if(helper(fieldsRef, nrow, ncol)){
                        return  true;
                    }
                    else {
                        fieldsRef[row][col].setText("");
                    }
                }
            }
        }
        return false;
    }

    //---------------------------------reset button listener----------------------------------
    public void ResetListener(){
        boolean allEmpty = true;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String value = fieldsRef[i][j].getText();
                //System.out.println("Row: " + i + ", Column: " + j + ", Value: " + value);

                if(!value.isEmpty()){
                    allEmpty = false;
                }
            }
        }

        if (allEmpty){
            JOptionPane.showMessageDialog(null, "All cells are empty!", "Warning!", 2);
        }
        else {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    fieldsRef[i][j].setText("");
                    statusBox.setText("All inputs are cleared");
                }
            }
        }
    }

    //---------------------------------load sample----------------------------------
    public void loadSample1() {
        String[][] sampleSudoku = {
                {"", "7", "", "", "3", "", "4", "", "9"},
                {"8", "", "", "", "4", "9", "", "", ""},
                {"5", "", "9", "", "", "", "", "1", ""},
                {"", "", "", "4", "", "6", "2", "3", ""},
                {"", "", "6", "", "", "8", "5", "", ""},
                {"2", "", "4", "", "", "", "", "9", ""},
                {"", "", "", "", "7", "4", "", "", ""},
                {"", "", "", "", "8", "5", "", "2", ""},
                {"4", "1", "", "", "", "", "8", "", ""}
        };

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                fieldsRef[row][col].setText(sampleSudoku[row][col]);
            }
        }
        statusBox.setText("Imported Sample 1. Difficulty: Hard (according to sudoku.com)");
    }
    public void loadSample2() {
        String[][] sampleSudoku = {
                {"9", "", "", "", "5", "", "", "", "4"},
                {"", "3", "5", "", "", "2", "", "", "7"},
                {"8", "", "", "", "", "", "3", "", ""},
                {"", "", "9", "", "", "", "", "", ""},
                {"", "", "", "", "", "8", "", "4", ""},
                {"", "5", "7", "", "4", "", "6", "", ""},
                {"", "", "", "1", "", "", "", "", "2"},
                {"", "6", "4", "", "8", "", "7", "", ""},
                {"3", "", "", "", "", "", "", "", ""}
        };

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                fieldsRef[row][col].setText(sampleSudoku[row][col]);
            }
        }
        statusBox.setText("Imported Sample 2. Difficulty: Master (according to sudoku.com)");
    }

    //---------------------------------main method----------------------------------
    public static void main(String[] args) {
        //MainInterface mainWin = new MainInterface();
        SwingUtilities.invokeLater(() -> new MainInterface());
    }
}
