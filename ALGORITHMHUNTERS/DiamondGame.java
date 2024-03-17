import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class DiamondGame extends JFrame implements ActionListener {
    //JFrame is parent class, ActionListener is interface and it will listen for action events (such as button clicks).
    private JButton[][] buttons;//2D array of button
    private JButton magicTool;//special tool in the game.
    private ArrayList<Point> diamonds;
    private int score;//the player's score.
    private JLabel timerLabel;//it shows remaining time
    private Timer gameTimer;//managing the game timer
    private int timeLeft;//An integer variable to store the remaining time in seconds.
    private JLabel basketLabel;//basket in the game
    public static String time = "0"; //Public static variable time initialized with value "0".
    public DiamondGame() {//constructor
        setTitle("Diamond Game");//Sets the title of the game window to "Diamond Game".
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Allows the program to terminate when the window is closed.
        setLayout(new GridLayout(8, 6)); // : Sets the window's layout to a grid layout (GridLayout).This grid consists of 8 rows and 6 columns.

        buttons = new JButton[7][6]; // This grid consists of 7 rows and 6 columns
        diamonds = new ArrayList<>();//This list can be used to store the locations of diamonds in the game.

        initializeButtons();
        initializeTimerLabel(); //Game buttons and other components are initialized by calling their methods.
        initializeBasketLabel();
        pack();//Automatically adjusts the dimensions of components within the window.
        setLocationRelativeTo(null);//Positions the window in the center of the screen.
        setVisible(true);// Makes the window visible.

        startTimer();//start game timer
    }

    private void initializeButtons() {//initialize the button
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(50, 50));
                add(button);//Each button is added to the panel (JFrame).
                buttons[i][j] = button;//button is 2d array
                button.addActionListener(this);//Buttons are assigned to the 2D buttons array.
            }
        }
        buttons[6][0].setBackground(Color.MAGENTA);
        buttons[6][1].setBackground(Color.MAGENTA);
        buttons[6][2].setBackground(Color.MAGENTA);//Some buttons are assigned colors
        buttons[6][3].setBackground(Color.MAGENTA);
        buttons[6][4].setBackground(Color.MAGENTA);
        buttons[6][5].setBackground(Color.MAGENTA);
        
        /*"U" (up), "D" (down), "L" (left), and "R" (right) texts are added to the buttons in the middle of the row.
        A special method is determined to be called when each button is clicked (moveMagicTool() method).*/
        buttons[6][2].setText("U");
        buttons[6][2].addActionListener(e -> moveMagicTool(-1, 0));

        buttons[6][3].setText("D");
        buttons[6][3].addActionListener(e -> moveMagicTool(1, 0));

        buttons[6][1].setText("L");
        buttons[6][1].addActionListener(e -> moveMagicTool(0, -1));

        buttons[6][4].setText("R");
        buttons[6][4].addActionListener(e -> moveMagicTool(0, 1));

        placeMagicTool();//position of magic
        generateDiamonds(3);//3 diomands are positioned 
    }

    private void initializeTimerLabel() {
        timerLabel = new JLabel("");//It will show the remaining time.
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);//Settings are made using SwingConstants.CENTER to center the content of the label horizontally.
        add(timerLabel);// it becomes viewable in the game window.
    }
    private void initializeBasketLabel() {
    basketLabel = new JLabel();//A JLabel named basket Label is created.
    basketLabel.setHorizontalAlignment(SwingConstants.RIGHT);//Adjustment is made using SwingConstants.RIGHT to right-align the content of the label horizontally.
    ImageIcon emptyIcon = new ImageIcon(); // Empty icon to start with
    basketLabel.setIcon(emptyIcon);//The content of the cart label is initially set as an empty icon.
    add(basketLabel, BorderLayout.EAST);//Using BorderLayout.EAST, the label is placed on the eastern edge of the window.
}
    private void startTimer() {
        timeLeft =  Integer.parseInt(time) * 3; // Score ** 3 will be written instead of 30 seconds
        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
               timerLabel.setText(String.valueOf(timeLeft)); // veya Integer.toString(timeLeft)

                if (timeLeft == 0) {
                    finishGame();
                }
            }
        });
        gameTimer.start();
    }
    //The general purpose of this method is to keep track of the game time by updating the time at regular intervals and to end the game via the finishGame() method when the time runs out.
        
    ImageIcon magicToolIcon = new ImageIcon("C:\\Users\\USER\\OneDrive\\Masaüstü\\magic-wand.png"); // MT simgesi

   private void placeMagicTool() {

    Random rand = new Random();//Generating a random 
    int magicToolX = rand.nextInt(6); //The variables magicToolX and magicToolY determine the X and Y coordinates of the magic tool by taking random numbers between 0 and 5.
    int magicToolY = rand.nextInt(6);
    magicTool = buttons[magicToolX][magicToolY];
    magicTool.setIcon(magicToolIcon);
}

    private void generateDiamonds(int numOfDiamonds) {
        ImageIcon diamondIcon = new ImageIcon("C:\\Users\\USER\\OneDrive\\Masaüstü\\diamond.png");

        Random rand = new Random();//Generating a random 
        for (int i = 0; i < numOfDiamonds; i++) {
            int x, y;
            do {
                x = rand.nextInt(6);     //!!!!! patlatırrrr
                y = rand.nextInt(6);
            } while (diamonds.contains(new Point(x, y)));

            diamonds.add(new Point(x, y));
            buttons[x][y].setText("");
            buttons[x][y].setIcon(diamondIcon);
        }
    }
//The purpose of this method is to randomly place a certain number of diamonds on the game grid. Diamonds are kept in a list called diamonds, and this list is used to check previously selected locations.
 
    
    
    private void moveMagicTool(int dx, int dy) {//the parameter is Specifies the amount of change in x and y coordinates.
    int currentX = -1, currentY = -1;//-1 is the default value assigned to variables currentX and currentY before any matches are found.

    for (int i = 0; i < 6; i++) {
        for (int j = 0; j < 6; j++) {
            if (buttons[i][j] == magicTool) {
                currentX = i;
                currentY = j;
                break;
            }
        }
    }

    int newX = currentX + dx;
    int newY = currentY + dy;

    if (newX >= 0 && newX < 6 && newY >= 0 && newY < 6) {//X and Y coordinates from 0 to 5
        JButton newMagicTool = buttons[newX][newY];
        if (newMagicTool.getText().isEmpty() || newMagicTool.getIcon() != null) {//This checks if the text of the JButton in the new location is empty (isEmpty()) or has no icon (getIcon() != null)
            magicTool.setIcon(null); // Remove the icon from the current position

            if (newMagicTool.getText().isEmpty()) {//Checks whether the text of the JButton in the new location is empty.
                magicTool = newMagicTool;
                magicTool.setIcon(magicToolIcon);

                if (diamonds.contains(new Point(newX, newY))) {
                    diamonds.remove(new Point(newX, newY));
                    score ++ ;
                    basketLabel.setText("Basket: " + "💎".repeat(score));
                    JOptionPane.showMessageDialog(this, "You collected a diamond! Score: " + score*100);//Showing the user a message that a diamond has been collected
                    newMagicTool.setIcon(magicToolIcon); // Set the magic tool icon at the new position
                    placeRandomDiamond();//Placing a new diamond in a random location.
                }
            }
        }
    }
}

private void placeRandomDiamond() {
    ImageIcon diamondIcon = new ImageIcon("C:\\Users\\USER\\OneDrive\\Masaüstü\\diamond.png");

    Random rand = new Random();
    int x, y;
    do {
        x = rand.nextInt(6);
        y = rand.nextInt(6);
    } 
    while (diamonds.contains(new Point(x, y)));//ensures that the diamond is not in a previously used location

    diamonds.add(new Point(x, y));//used to track the location of diamonds
    buttons[x][y].setText("");//Empties the text of the JButton at the diamond's location.
    buttons[x][y].setIcon(diamondIcon);//Adds a diamond icon to the JButton at the diamond's location.
}

   private void finishGame() {
    gameTimer.stop();//stops
    int choice = JOptionPane.showConfirmDialog(this, "Finish Game! Your Score: " + score + "\nDo you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION);/*When the game is finished, a dialog window appears. This dialog window asks the player if the game is over, the score they have achieved, and whether they want to play again. 
    It is assigned to a choice variable that holds the user's choice.*/
    if (choice == JOptionPane.YES_OPTION) {
        resetGame();//starting a new game by resetting the game
    } else {
        System.exit(0);//closes the program
    }
}

private void resetGame() {
    diamonds.clear();
    score = 0;
    for (int i = 0; i < 6; i++) {
        for (int j = 0; j < 6; j++) {
            buttons[i][j].setText("");
            buttons[i][j].setIcon(null);
        }
    }
    placeMagicTool(); // Magic Tool'u yerleştir
    generateDiamonds(3);//3 tane diomand koyar rastgele
    startTimer();
}


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();//Determines the JButton the user clicked.
        if (!clickedButton.getText().equals("MT")) {//If there is no magic tool in the location
            Point currentPos = findButtonPosition(clickedButton);
            if (diamonds.contains(currentPos)) {//If there is a diamond in the location
                diamonds.remove(currentPos);//lifts the diamond
                score++;
                JOptionPane.showMessageDialog(this, "You collected a diamond! Score: " + score*100);
                clickedButton.setText("");//Empties the text of the clicked JButton.
                clickedButton.setIcon(null);//Removes the icon of the clicked JButton.
            }
            if (diamonds.isEmpty()) {//all diamonds collected
                JOptionPane.showMessageDialog(this, "Congratulations! You collected all diamonds. Final Score: " + score*100);
                finishGame();
            }
        }
    }

    private Point findButtonPosition(JButton button) {//find button location
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (buttons[i][j] == button) {
                    return new Point(i, j);
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {//allows the program to be started and the GUI created
        SwingUtilities.invokeLater(DiamondGame::new);
    }
}