import javax.swing.*;

public class HangmanLogic
{
   //instance variables
    private String secretPhrase;
    private char[] progress;
    private int incorrectGuesses;
    private int maxIncorrectGuesses = 6;

    private String[] phraseArray = {"I love C.S.", "Arrays are Fun!", "Object oriented", "Nesting Loops is my pastime", "Linked Lists", "CMD prompt wizard", "I use Linux by the way", "Centering a div right now", "Stack overflow saved me", "What do you mean it's due today?", "'It works on my machine'", "Only tree this Christmas is binary", "Conditionals", "Version Control", "I code in Assembly"};

    private ImageIcon hangmanImage;
    public HangmanLogic()
    {
        resetGame();
        setImage("hangman"+incorrectGuesses+".png");    //resets hangman image back
    }

    public void selectSecretPhrase() {
        secretPhrase = phraseArray[(int) (Math.random() * phraseArray.length)];
    }

    public String returnSecretPhrase() {
        return secretPhrase;
    }

    public void initializeDisplay()
    {
        progress = new char[secretPhrase.length()];
        for (int i = 0; i < secretPhrase.length(); i++)
        {
            char c = secretPhrase.charAt(i);
            if (Character.isLetter(c))
            {
                progress[i] = (char) 717;
            } else
            {
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

    public void updateProgress(char guess)
    {
        for (int i = 0; i < secretPhrase.length(); i++)
        {
            if (Character.toUpperCase(secretPhrase.charAt(i)) == Character.toUpperCase(guess))
            {
                progress[i] = secretPhrase.charAt(i);
            }
        }
    }

    public void processGuess(char guess)
    {
        if (!secretPhrase.toUpperCase().contains(String.valueOf(guess)))
        {
            incorrectGuesses++;
            System.out.println(incorrectGuesses);
        }
    }

    //returns true/false if game is over
    public boolean isGameOver()
    {
        return new String(progress).equals(secretPhrase) || incorrectGuesses >= maxIncorrectGuesses;
    }

    //calls methods required to reset the game on the logic side
    public void resetGame()
    {
        selectSecretPhrase();
        initializeDisplay();
        incorrectGuesses = 0;
    }
    public ImageIcon getImage()
    {
        return hangmanImage;
    }

    //sets the image
    public void setImage(String fileName)
    {
        try
        {
            hangmanImage = new ImageIcon(getClass().getResource(fileName));
        } catch (NullPointerException error) {
            System.err.println("Image cannot be found in the resources root.  " +
                    "Check your file name spelling and/or that you have marked " +
                    "the correct directory as resources root");
        }
    }
}
