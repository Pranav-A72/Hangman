import javax.swing.*;

public class HangmanLogic {
    private String secretPhrase;
    private char[] progress;
    private int incorrectGuesses;
    private int maxIncorrectGuesses = 6;

    private String[] phraseArray = {"Chestnuts roasting on an open fire.", "Milk and Cookies.", "Ho, Ho, Ho", "Santa Claus is coming to town.", "Presents under the tree!", "All I want for Christmas is you!", "You got coal!", "You've been super naughty...", "Christmas Spirit.", "The Grinch will steal your Christmas!", "Home alone!", "Frosty the Snowman...", "Rudolph the red nose reindeer; had a very shiny nose", "Jingle Bells, Batman smells", "Time to Open presents!"};

    private ImageIcon hangmanImage;
    public HangmanLogic() {
        resetGame();
        setImage("hangman"+incorrectGuesses+".png");
    }

    public void selectSecretPhrase() {
        secretPhrase = phraseArray[(int) (Math.random() * phraseArray.length)];
    }

    public String returnSecretPhrase() {
        return secretPhrase;
    }

    public void initializeDisplay() {
        progress = new char[secretPhrase.length()];
        for (int i = 0; i < secretPhrase.length(); i++) {
            char c = secretPhrase.charAt(i);
            if (Character.isLetter(c)) {
                progress[i] = (char) 717;
            } else {
                progress[i] = c;
            }
        }
    }

    public char[] getProgress() {
        return progress;
    }

    public int getIncorrectGuesses() {
        return incorrectGuesses;
    }

    public int getMaxIncorrectGuesses() {
        return maxIncorrectGuesses;
    }

    public void updateProgress(char guess) {
        for (int i = 0; i < secretPhrase.length(); i++) {
            if (Character.toUpperCase(secretPhrase.charAt(i)) == Character.toUpperCase(guess)) {
                progress[i] = secretPhrase.charAt(i);
            }
        }
    }

    public void processGuess(char guess) {
        if (!secretPhrase.toUpperCase().contains(String.valueOf(guess))) {
            incorrectGuesses++;
            System.out.println(incorrectGuesses);
        }
    }

    public boolean isGameOver() {
        return new String(progress).equals(secretPhrase) || incorrectGuesses >= maxIncorrectGuesses;
    }

    public void resetGame() {
        selectSecretPhrase();
        initializeDisplay();
        incorrectGuesses = 0;
    }
    public ImageIcon getImage()
    {
        return hangmanImage;
    }
    public void setImage(String fileName) {
        try {
            /**
             * instructions to set up your project to use files
             * Right-click your project to make a new directory - name it something(images for this project)
             * Right-click the folder you just made.  Select Mark As, then Resources Root
             * without this the code below will not work
             */
            hangmanImage = new ImageIcon(getClass().getResource(fileName));
        } catch (NullPointerException error) {
            System.err.println("Image cannot be found in the resources root.  " +
                    "Check your file name spelling and/or that you have marked " +
                    "the correct directory as resources root");

        }
    }
}
