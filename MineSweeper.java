import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.ArrayList;
import javax.swing.*;

public class MineSweeper {
    class Block extends JButton{
        int r,c;
        Block(int r, int c){
            this.r=r;
            this.c=c;
        }
    }
    int numberOfMines=10;
    int boardWidth=800;
    int boardHeight=800;
    int boardSize=9;
    int tilesClicked=0;
    boolean GameOver=false;


    ArrayList<Block> mineBlocks =new ArrayList<>();
    Block[][] Board =new Block[boardSize][boardSize];


    JFrame frame= new JFrame("My MineSweeper Game");
    JLabel label = new JLabel();


    JPanel panel = new JPanel();
    JPanel boardPanel = new JPanel();

    MineSweeper(){

        frame.setSize(boardWidth,boardHeight);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        label.setFont(new Font("Arial",Font.BOLD,25));
        label.setText("Number of Mines: "+numberOfMines);
        label.setOpaque(true);
        panel.setBackground(Color.white);
        panel.add(label);
        frame.add(panel,BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(boardSize,boardSize));
        frame.add(boardPanel,BorderLayout.CENTER);
        setupTiles();
        setupMines();

        frame.setVisible(true);
    }

    void setupTiles(){
        for(int i=0;i<boardSize;i++){
            for(int j=0;j<boardSize;j++){
                Block block =new Block(i,j);
                Board[i][j]= block;
                block.setBackground(Color.gray);
                block.setBorder(BorderFactory.createLineBorder(Color.black));
                block.setFocusable(false);
                block.addMouseListener(new MouseAdapter() {
                   @Override
                   public void mousePressed(MouseEvent e){
                       if(GameOver)
                            return;
                       Block block =(Block)e.getSource();
                       //left Click
                       if(e.getButton()==MouseEvent.BUTTON1){
                           if(block.getText()==""){
                               if(mineBlocks.contains(block)){
                                   GameOver=true;
                                   revealMines();
                               }
                               else{
                                    checkMine(block.r, block.c);
                               }
                           }
                       }
                       else if(e.getButton()==MouseEvent.BUTTON3){
                            if(block.getText()=="" && block.isEnabled()){
                                block.setText("🚩");
                                block.setEnabled(false);
                            }
                            else if(block.isEnabled()) {
                                block.setText("");
                                block.setEnabled(true);
                            };

                       }
                   }
                });
                boardPanel.add(block);
            }
        }
    }

    void setupMines() {
        Random random = new Random();
        int placed = 0;
        while (placed < numberOfMines) {
            int x = random.nextInt(boardSize);
            int y = random.nextInt(boardSize);
            if (!mineBlocks.contains(Board[x][y])) {
                mineBlocks.add(Board[x][y]);
                placed++;
            }
        }
    }

    void revealMines(){
        for(Block block : mineBlocks){
            block.setBackground(Color.white);
            block.setText("💣");
        }
    }

    void checkMine(int r, int c) {
        Block block = Board[r][c];
        if (!block.isEnabled()) {
            return;
        }
        block.setEnabled(false);
        tilesClicked += 1;

        int minesFound = 0;

        for(int i=-1;i<=1;i++){
            for(int j=-1;j<=1;j++){
                if((r+i>=0&&c+j>=0&&(r+i)<boardSize&&(c+j)<boardSize) && mineBlocks.contains(Board[r+i][c+j]))
                    minesFound+=1;
            }
        }

        if (minesFound > 0) {
            block.setText(Integer.toString(minesFound));
            block.setBackground(Color.white);
            block.setEnabled(false);
        }
        else {
            block.setText("");
            block.setBackground(Color.white);
            block.setEnabled(false);

            for(int i=-1;i<=1;i++){
                for(int j=-1;j<=1;j++){
                    if((r+i>=0&&c+j>=0&&r+i<boardSize&&c+j<boardSize))
                        checkMine(r+i,c+j);
                }
            }
        }


        if (tilesClicked == boardSize * boardSize - mineBlocks.size()) {
            GameOver = true;
            label.setText("Mines Cleared!");
        }
    }

}
