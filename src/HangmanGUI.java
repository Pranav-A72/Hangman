import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HangmanGUI extends JFrame implements ActionListener
{
    //instance variables
    private Container window;
    private JLabel secretPhraseLabel;
    private JLabel statusLabel;
    private JLabel hangmanImage;
    private JPanel keyboardPanel;
    private JPanel hangmanPanel;
    private JButton resetButton;
    private JButton quitButton;
    private JButton[] letterButtons;

    private HangmanLogic hangmanLogic;

    public HangmanGUI()
    {
        //creates an instance of hangmanlogic so we can use all the methods in it
        hangmanLogic = new HangmanLogic();

        //UI Setup
        window = getContentPane();
        window.setLayout(null);
        window.setBackground(new Color(255, 255, 255));

        keyboardPanel = new JPanel(new GridLayout(4, 7, 5, 5)); // to organize the letter buttons into rows
        keyboardPanel.setBounds(10, 400, 700, 300);
        keyboardPanel.setBackground(new Color(255, 255, 255));
        window.add(keyboardPanel);

        secretPhraseLabel = new JLabel();
        secretPhraseLabel.setFont(new Font("Arial", Font.PLAIN, 65));
        secretPhraseLabel.setForeground(Color.RED);
        secretPhraseLabel.setBounds(10, 150, 1100, 200);
        window.add(secretPhraseLabel);

        statusLabel = new JLabel();
        statusLabel.setFont(new Font("Arial", Font.BOLD, 30));
        statusLabel.setForeground(new Color(0, 52, 255));//dark green
        statusLabel.setBounds(15, 350, 1000, 40);
        window.add(statusLabel);

        hangmanPanel = new JPanel();
        hangmanPanel.setBounds(1050, 50, 350, 500);
        hangmanPanel.setBackground(new Color(255, 255, 255));
        window.add(hangmanPanel);

        letterButtons = new JButton[26];
        int buttonX = 10;
        int buttonY = 10;
        for (char c = 'A'; c <= 'Z'; c++)
        {
            JButton button = new JButton(String.valueOf(c));
            button.addActionListener(new LetterButtonClickListener());
            button.setFont(new Font("Arial", Font.BOLD, 25));
            button.setForeground(new Color(0, 54, 255));//dark green
            button.setBackground(Color.RED);
            button.setBounds(buttonX, buttonY, 50, 50);
            letterButtons[c - 'A'] = button;
            keyboardPanel.add(button);

            buttonX += 60;
            if (buttonX > 600) {
                buttonX = 10;
                buttonY += 60;
            }
        }

        resetButton = new JButton("Reset");
        resetButton.setBounds(1000, 600, 200, 100);
        resetButton.setFont(new Font("Arial", Font.BOLD, 25));
        resetButton.setForeground(Color.red);
        resetButton.setBackground(new Color(0, 52, 255));//dark green
        resetButton.addActionListener(this);
        window.add(resetButton);

        quitButton = new JButton("Quit");
        quitButton.setBounds(1250, 600, 200, 100);
        quitButton.setFont(new Font("Arial", Font.BOLD, 25));
        quitButton.setForeground(Color.red);
        quitButton.setBackground(new Color(0, 24, 255));//dark green
        quitButton.addActionListener(this);
        window.add(quitButton);

        resetGame();
    }

    //method to be called every time the hangman image needs to be changed, based on incorrect guesses
    public void updateHangmanImage()
    {
        String imagePath = "hangman" + hangmanLogic.getIncorrectGuesses() + ".png";
        hangmanLogic.setImage(imagePath);
        hangmanImage = new JLabel(hangmanLogic.getImage());
        hangmanImage.setBounds(1800, 50, hangmanLogic.getImage().getIconWidth(), hangmanLogic.getImage().getIconHeight());

        hangmanPanel.removeAll();
        hangmanPanel.add(hangmanImage);
        hangmanPanel.revalidate();
        hangmanPanel.repaint();
    }

    //calls all methods required in reseting the game
    public void resetGame()
    {
        hangmanLogic.resetGame();
        updateSecretPhraseLabel();
        updateHangmanImage();
        updateGameStatus();
        enableAllButtons();
    }

    private void enableAllButtons()
    {
        for (JButton button : letterButtons)
        {
            button.setEnabled(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() instanceof JButton)
        {
            JButton clicked = (JButton) e.getSource();
            if (clicked.equals(resetButton))
            {
                hangmanLogic.resetGame();
                resetGame();
            }
            if (clicked.equals(quitButton))
                System.exit(0);
        }
    }


    //seperate class to hold an actionlistener for the letter buttons to not crowd main actionPerformed
    private class LetterButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (!hangmanLogic.isGameOver()) {
                JButton source = (JButton) event.getSource();
                char guess = source.getText().charAt(0);
                hangmanLogic.processGuess(guess);
                hangmanLogic.updateProgress(guess);
                updateSecretPhraseLabel();
                updateGameStatus();
                source.setEnabled(false);
                updateHangmanImage();
            }
        }
    }

    //method called to show progress when a user guesses
    private void updateSecretPhraseLabel()
    {
        secretPhraseLabel.setText(" " + new String(hangmanLogic.getProgress()));
    }


    private void updateGameStatus()
    {
        if (new String(hangmanLogic.getProgress()).equals(hangmanLogic.returnSecretPhrase()))
        {
            statusLabel.setText("You Win!");
        } else if (hangmanLogic.getIncorrectGuesses() >= hangmanLogic.getMaxIncorrectGuesses())
        {
            statusLabel.setText("You Lose! The correct phrase was: " + hangmanLogic.returnSecretPhrase());
        } else
        {
            statusLabel.setText("Guess a letter");
        }
    }

    //main method
    public static void main(String[] args)
    {
            HangmanGUI gameGUI = new HangmanGUI();
            gameGUI.setSize(1920, 1080);
            gameGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameGUI.setTitle("CS Hangman Game");
            gameGUI.setVisible(true);
    }
}
