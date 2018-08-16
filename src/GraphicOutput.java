import javafx.fxml.Initializable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GraphicOutput extends Output{
    Container mainContainer;
    JPanel panel;
    JButton[][] board;
    JLabel textoutput;
    JButton[][] playertokens;

    //size and position variables
    private int top_x = 100;
    private int top_y = 100;
    private int buttonsize = 50;

    //Images
    BufferedImage background = null;
    ImageIcon stoneWhite = null;
    ImageIcon stoneBlack = null;

    public GraphicOutput(){
        this(false);
    }

    public GraphicOutput(boolean isApplet){
        //Load Images
        stoneWhite = new ImageIcon(getClass().getResource("res/"+"StoneWhite.jpg"));
        stoneBlack = new ImageIcon(getClass().getResource("res/"+"StoneBlack.png"));
        try {       background = ImageIO.read(getClass().getResource("res/"+"GameOfUrBoard.jpg"));
        } catch (IOException e) {}

        //Initialize game panel
        panel = new Gamepanel(background,top_x,top_y,buttonsize);
        panel.setLayout(null);

        //Initialize text
        textoutput = new JLabel();
        textoutput.setBounds(top_x, top_y+(int)(5.5*buttonsize),buttonsize*8, buttonsize);
        panel.add(textoutput);

        //Initialize Board
        board = new JButton[8][3];

        for (int i = 0; i < board.length; i++) {
            if(i<=3 || i>=6){
                board[i][0]=new JButton();
                board[i][0].setBounds(top_x+i*buttonsize, top_y+buttonsize, buttonsize, buttonsize);
                board[i][0].setBackground(new Color(255,255,255,0));
                board[i][2]=new JButton();
                board[i][2].setBounds(top_x+i*buttonsize,top_y+3*buttonsize, buttonsize, buttonsize);
                board[i][2].setBackground(new Color(255,255,255,0));

                panel.add(board[i][0]);
                panel.add(board[i][2]);
            }
            board[i][1]=new JButton();
            board[i][1].setBounds(top_x+i*buttonsize,top_y+2*buttonsize, buttonsize, buttonsize);
            board[i][1].setBackground(new Color(255,255,255,0));
            panel.add(board[i][1]);
        }

        //Initialize player token buttons
        playertokens = new JButton[2][7];
        for (int i = 0; i < playertokens[0].length; i++) {
            playertokens[0][i] = new JButton();
            playertokens[0][i].setBounds(top_x+i*buttonsize, top_y-(int)(0.5*buttonsize), buttonsize, buttonsize);
            playertokens[0][i].setIcon(stoneWhite);
            playertokens[0][i].setBackground(new Color(255,255,255,0));
            panel.add(playertokens[0][i]);

            playertokens[1][i] = new JButton();
            playertokens[1][i].setBounds(top_x+i*buttonsize, top_y+(int)(4.5*buttonsize), buttonsize, buttonsize);
            playertokens[1][i].setIcon(stoneBlack);
            playertokens[1][i].setBackground(new Color(255,255,255,0));
            panel.add(playertokens[1][i]);
        }

        //Initialize Outside container (JFrame or Applet)
        if(isApplet){

        }else{
            JFrame frame = new JFrame();
            frame.setSize(800,600);
            frame.setLocationRelativeTo(null);
            frame.setResizable(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(panel);
            frame.setVisible(true);
            mainContainer = frame;
        }

    }


    public void printBoard(Tile[] tiles, Player[] players){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j]!=null){
                    board[i][j].setText("");
                    board[i][j].setIcon(null);
                }
            }
        }

        for (int p = 0; p < players.length; p++) {
            ArrayList<Token> tokens = players[p].getTokens();
            for (Token t:tokens ){
                if(t.getTile()!=null){
                    //Calculate position in String array
                    int r = getRow(p,t.getTile().getID());
                    int c = getColumn(t.getTile().getID());
                    if(players[p].getID()==0){
                        board[c][r].setIcon((Icon)stoneWhite);
                    }else{
                        board[c][r].setIcon((Icon)stoneBlack);
                    }
                    //board[c][r].setText(players[p].getID()+"");
                }
            }
        }

        //enable/disable playertokens
        for (int i = 0; i < playertokens[0].length; i++) {
            playertokens[0][i].setVisible(i<players[0].getNumberTokenNotOnBoard());
            playertokens[1][i].setVisible(i<players[1].getNumberTokenNotOnBoard());
        }

        panel.revalidate();;
        panel.repaint();
    }

    /**This method outputs Strings to console
     * @param s String that should be printed on the console
     */
    public void print(String s){
        textoutput.setText(s);
    }

    /**returns row of a tile on te board
     * @param playerID ID of player whos tile it is (important for private tiles)
     * @param tileID Index of tile
     * @return 0,1 or 2 depending on row
     */
    private static int getRow(int playerID, int tileID) {
        return (tileID >= 4 && tileID <= 11) ? 1 : (playerID == 0) ? 0 : 2;
    }

    /**returns column of a tile on te board
     * @param tileID Index of tile
     * @return 0-7 depending on column
     */
    private static int getColumn(int tileID){
        return (tileID<=3)? 3-tileID : (tileID>=12)? -(tileID-19) : tileID-4;
    }

}
