import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class Sudoku {
    class Tile extends JButton {
        int r,c;
        Tile(int r,int c){
            this.r=r;
            this.c=c;
        }
    }

    int number_of_show=10;
    int boardWidth= 600;
    int boardHeight= 650;

//    String[] p={
//            "---------",
//            "---------",
//            "---------",
//            "---------",
//            "---------",
//            "---------",
//            "---------",
//            "---------",
//            "---------"
//    };
    String[] puzzle={
            "53--7----",
            "6--195---",
            "-98----6-",
            "8---6---3",
            "4--8-3--1",
            "7---2---6",
            "-6----28-",
            "---419--5",
            "----8--79"
    };
    String[] Solution={
            "534678912",
            "672195348",
            "198342567",
            "859761423",
            "426853791",
            "713924856",
            "961537284",
            "287419635",
            "345286179"
    };


    JFrame frame = new JFrame("My Sudoku Game");
    JLabel label = new JLabel();
    JPanel panel = new JPanel();
    JPanel BoardPanel =new JPanel();
    JPanel NumbersPanel =new JPanel();

    JButton numSelected =null;
    int errors =0;

    Sudoku(){
        frame.setSize(boardWidth,boardHeight);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        label.setFont(new Font("Arial", Font.PLAIN, 20));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setText("Sudoku Game : ");

        panel.add(label);
        frame.add(panel,BorderLayout.NORTH);

        BoardPanel.setLayout(new GridLayout(9,9));
        setupTiles();
        frame.add(BoardPanel,BorderLayout.CENTER);

        NumbersPanel.setLayout(new GridLayout(1,9));
        frame.add(NumbersPanel,BorderLayout.SOUTH);

        frame.setVisible(true);
        setupNumbers();

    }
    void setupTiles(){
        for(int r=0;r<9;r++){
            for(int c=0;c<9;c++){
                Tile tile = new Tile(r,c);
                char TileChar = puzzle[r].charAt(c);
                if(TileChar!='-') {
                    tile.setFont(new Font("Arial", Font.BOLD, 20));
                    tile.setText(String.valueOf(TileChar));
                    tile.setBackground(Color.LIGHT_GRAY);
                }
                else{
                    tile.setFont(new Font("Arial", Font.PLAIN, 20));
                    tile.setBackground(Color.white);
                }

                if((r==2&&c==2)||(r==2&&c==5)||(r==5&&c==2)||(r==5&&c==5)){
                    tile.setBorder(BorderFactory.createMatteBorder(1,1,5,5,Color.black));

                }
                else if(r==2||r==5){
                    tile.setBorder(BorderFactory.createMatteBorder(1,1,5,1,Color.black));
                }
                else if(c==2||c==5){
                        tile.setBorder(BorderFactory.createMatteBorder(1,1,1,5,Color.black));
                }
                else{
                    tile.setBorder(BorderFactory.createLineBorder(Color.black));
                }



                tile.setFocusable(false);
                BoardPanel.add(tile);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Tile tile=(Tile)e.getSource();
                        int r=tile.r;
                        int c=tile.c;
                        if(numSelected!=null){
                            if(tile.getText() !="")
                                return;
                            String numSelectedText =numSelected.getText();
                            String tileSolution = String.valueOf(Solution[r].charAt(c));
                            if(tileSolution.equals(numSelectedText)){
                                tile.setText(numSelectedText);
                            }
                            else{
                                errors++;
                                label.setText("Sudoku Game : "+String.valueOf(errors));
                            }
                        }
                    }
                });

            }
        }
    }

    void setupNumbers(){
        for(int r=0;r<9;r++){
            JButton tile = new JButton();
            tile.setText(String.valueOf(r+1));
            tile.setFont(new Font("Arial", Font.BOLD, 20));
            tile.setBackground(Color.white);
            tile.setFocusable(false);
            NumbersPanel.add(tile);

            tile.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton tile = (JButton)e.getSource();
                    if(numSelected!=null){
                        numSelected.setBackground(Color.white);
                    }
                    numSelected = tile;
                    numSelected.setBackground(Color.LIGHT_GRAY);
                }
            });

        }
    }

//    void fullGame(){
//        int k=0;
//        for(int r=0;r<9;r++){
//            for(int c=0;c<9;c++){
//                Random rand=new Random();
//                int x=rand.nextInt(boardWidth);
//                int y=rand.nextInt(boardHeight);
//                int i=rand.nextInt(9);
//
//            }
//        }
//    }
//
//    int check(int x,int y,int k){
//        for(int r=0;r<9;r++){
//            if(k==p[x].charAt(r)||k==p[r].charAt(y)){
//                return 0;
//            }
//            else {
//                int wherex=x%3;
//                int wherey=y%3;
//                for(int i=wherex;i<wherex+3;i++){}
//            }
//        }
//    }
}
