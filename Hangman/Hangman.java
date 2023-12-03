import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Hangman extends JFrame {
    // Instance variables
    // Logic
    private String secretPhrase; // Stores the secret phrase.
    private char[] progress; // Stores the progress the user has made in guessing the phrase.
    private int incorrectGuesses; // Stores the number of incorrect guesses.
    private int maxIncorrectGuesses = 5; // Maximum allowed incorrect guesses before the game is over.
    // GUI
    private Container window;
    private JLabel secretPhraseLabel; // Label to display the secret phrase with underscores for hidden letters, updates on each guess
    private JLabel statusLabel; // Label to display the game status either "you win", "you lose", or "guess a letter"
    // Arrays
    private JButton[] letterButtons; // Array of buttons for each letter A-Z.
    private String[] phraseArray = {"Chestnuts roasting on an open fire.", "Milk and Cookies.", "Ho, Ho, Ho", "Santa Claus is coming to town.", "Presents under the tree!", "All I want for Christmas is you!", "You got coal!", "You've been super naughty...", "Christmas Spirit.", "The Grinch will steal your Christmas!", "Home alone!", "Frosty the Snowman...", "Rudolph the red nose reindeer; had a very shiny nose", "Jingle Bells, Batman smells", "Time to Open presents!"};

    private JPanel keyboardPanel; // Panel to hold the letter buttons
    private JPanel hangmanPanel; // Panel to hold the hangman images

    public Hangman() {
        // Initialize instance variables, set up the UI
        window = getContentPane();
        window.setLayout(null); // Use null layout for precise pixel positioning

        // Create a panel for the QWERTY keyboard
        keyboardPanel = new JPanel(new GridLayout(4, 7, 5, 5));
        keyboardPanel.setBounds(10, 10, 700, 300); // Set the position and size in pixels
        window.add(keyboardPanel);

        secretPhraseLabel = new JLabel();
        secretPhraseLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        secretPhraseLabel.setForeground(Color.RED);
        secretPhraseLabel.setBounds(10, 320, 700, 40); // Set the position and size in pixels
        window.add(secretPhraseLabel);

        statusLabel = new JLabel();
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        statusLabel.setForeground(Color.GREEN);
        statusLabel.setBounds(10, 370, 700, 40); // Set the position and size in pixels
        window.add(statusLabel);

        // Create a panel for the hangman images
        hangmanPanel = new JPanel();
        hangmanPanel.setBounds(720, 10, 350, 500); // Set the position and size in pixels
        window.add(hangmanPanel);

        letterButtons = new JButton[26];
        int buttonX = 10;
        int buttonY = 10;
        for (char c = 'A'; c <= 'Z'; c++) {
            JButton button = new JButton(String.valueOf(c));
            button.addActionListener(new ButtonClickListener());
            button.setFont(new Font("Arial", Font.PLAIN, 20));
            button.setForeground(Color.WHITE);
            button.setBackground(Color.RED);
            button.setBounds(buttonX, buttonY, 50, 50); // Set the position and size in pixels
            letterButtons[c - 'A'] = button;
            keyboardPanel.add(button);

            // Adjust the next button position
            buttonX += 60;
            if (buttonX > 600) {
                buttonX = 10;
                buttonY += 60;
            }
        }

        resetGame();
    }

    public void selectSecretPhrase() {
        // Select a secret phrase from a predefined list using Math.random
        secretPhrase = phraseArray[(int) (Math.random() * phraseArray.length)];
    }

    public String returnSecretPhrase() {
        return secretPhrase;
    }

    public void initializeDisplay() {
        // Initialize the display of the secret phrase with underscores for hidden letters.
        progress = new char[secretPhrase.length()];
        for (int i = 0; i < secretPhrase.length(); i++) {
            char c = secretPhrase.charAt(i);
            if (Character.isLetter(c)) {
                progress[i] = (char) 717;
            } else {
                progress[i] = c;
            }
        }
        updateSecretPhraseLabel();
    }

    public void updateSecretPhraseLabel() {
        secretPhraseLabel.setText("Secret Phrase: " + new String(progress));
    }

    public void updateProgress(char guess) {
        // Update the progress based on the correct guess.
        for (int i = 0; i < secretPhrase.length(); i++) {
            if (Character.toUpperCase(secretPhrase.charAt(i)) == Character.toUpperCase(guess)) {
                progress[i] = secretPhrase.charAt(i);
            }
        }
        updateSecretPhraseLabel();
    }

    public void processGuess(char guess) {
        // Process a user's single letter guess.
        // Check if the guess is correct or incorrect.
        // Update progress for correct guesses or track incorrect guesses.
        if (secretPhrase.toUpperCase().contains(String.valueOf(guess))) {
            updateProgress(guess);
        }

        // Disable the button after being clicked
        letterButtons[guess - 'A'].setEnabled(false);

        updateGameStatus();
    }

    public void updateGameStatus() {
        // Update the game status label based on the current state.
        if (new String(progress).equals(secretPhrase)) {
            statusLabel.setText("You Win!");
        } else if (incorrectGuesses >= maxIncorrectGuesses) {
            statusLabel.setText("You Lose! The correct phrase was: " + secretPhrase);
        } else {
            statusLabel.setText("Guess a letter");
        }

        // You can add logic here to update the hangman images panel based on incorrectGuesses.
        // Load and display the appropriate image from your hangman images array.
    }

    public boolean isGameOver() {
        // Check if the game is over (either the user won or lost).
        return new String(progress).equals(secretPhrase) || incorrectGuesses >= maxIncorrectGuesses;
    }

    public void resetGame() {
        // Reset the game state to start a new game.
        selectSecretPhrase();
        initializeDisplay();
        incorrectGuesses = 0;
        enableAllButtons(); // Enable all buttons at the start of a new game
        updateGameStatus();
    }

    private void enableAllButtons() {
        // Enable all letter buttons
        for (JButton button : letterButtons) {
            button.setEnabled(true);
        }
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (!isGameOver()) {
                JButton source = (JButton) event.getSource();
                char guess = source.getText().charAt(0);
                processGuess(guess);
            }
        }
    }

    public static void main(String[] args) {
        Hangman game = new Hangman();
        game.setSize(960, 720);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setTitle("Hangman Game");
        game.setVisible(true);
}
}