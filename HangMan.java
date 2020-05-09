import java.util.Scanner;

/**
 * Text-Based Hangman Simulator.
 *
 * @author Jamie Tucker
 *
 */
public final class HangMan {

    /**
     * Generates a random word that will be selected for the player to guess.
     * Returns a String
     */
    private static String generateWord() {
        String[] words = { "apple", "portland", "seagull", "snowman", "thrill",
                "cat", "penguin", "dictionary", "uncharted", "hero", "all star",
                "big chungus", "pit", "doctor", "sauce", "ferromagnetic",
                "friction", "xylophone", "wizard", "destiny", "league",
                "rocket", "cow", "scooby", "google", "basketball", "football",
                "runescape", "risk", "monopoly", "economics", "water", "thorn",
                "illegal", "sword", "microsoft", "skittles", "message",
                "minecraft", "backyard", "baseball", "royal", "developer",
                "computer", "stadia", "playstation" };
        // Selects a random string between 0 and the length of the array words
        String selectedWord = words[(int) (Math.random() * (words.length))];
        return selectedWord;
    }

    /**
     * Generates the Hangman playing board
     */
    private static void generateBoard(int attempts, String word,
            String[] guesses) {

        ////////////////////////////////////////////////
        // Hangman Boards depending on the turn count //
        ////////////////////////////////////////////////
        String boardOne = "  --------" + "\n |       |" + "\n |" + "\n |"
                + "\n |" + "\n |\n---";

        String boardTwo = "  --------" + "\n |       |" + "\n |       O"
                + "\n |" + "\n |" + "\n |\n---";

        String boardThree = "  --------" + "\n |       |" + "\n |       O"
                + "\n |       |" + "\n |" + "\n |\n---";

        String boardFour = "  --------" + "\n |       |" + "\n |       O"
                + "\n |      \\|" + "\n |" + "\n |\n---";

        String boardFive = "  --------" + "\n |       |" + "\n |       O"
                + "\n |      \\|/" + "\n |" + "\n |\n---";

        String boardSix = "  --------" + "\n |       |" + "\n |       O"
                + "\n |      \\|/" + "\n |       |" + "\n |\n---";

        String boardSeven = "  --------" + "\n |       |" + "\n |       O"
                + "\n |      \\|/" + "\n |       |" + "\n |      /\n---";

        String boardEight = "  --------" + "\n |       |" + "\n |       O"
                + "\n |      \\|/" + "\n |       |" + "\n |      / \\\n---";

        /*
         * Display the certain board depending on the turn count
         */
        if (attempts == 8) {
            System.out.println(boardOne);
        }
        if (attempts == 7) {
            System.out.println(boardTwo);
        }
        if (attempts == 6) {
            System.out.println(boardThree);
        }
        if (attempts == 5) {
            System.out.println(boardFour);
        }
        if (attempts == 4) {
            System.out.println(boardFive);
        }
        if (attempts == 3) {
            System.out.println(boardSix);
        }
        if (attempts == 2) {
            System.out.println(boardSeven);
        }
        if (attempts == 1) {
            System.out.println(boardEight);
        }

        /*
         * Goes through each letter in the guesses array to see if the letter
         * fits in the word. It also checks to make sure the letters are in the
         * correct position. If the letter does not fit in the word, it prints
         * out an underscore.
         */
        int k = 0; // Will be used later in the for loop
        boolean guessLetterFound = false;
        for (int i = 1; i < word.length() + 1; i++) {
            k = 0;
            String currLetter = word.substring(i - 1, i);
            if (currLetter.equals(" ")) {
                System.out.print("  ");
            } else {
                while (k < guesses.length - 1 && guessLetterFound != true) {
                    guessLetterFound = letterInWord(guesses[k], word);
                    if (guessLetterFound == false) {
                        k++;
                    }
                }
                if (guessLetterFound == true && guesses[k].equals(currLetter)) {
                    System.out.print(guesses[k] + " ");
                } else if (guessLetterFound == true
                        && !guesses[k].equals(currLetter)
                        && k < guesses.length - 1) {
                    int m = k;
                    for (m = k; m < guesses.length; m++) {
                        if (guessLetterFound == true
                                && guesses[m].equals(currLetter)) {
                            System.out.print(guesses[m] + " ");
                            break;
                        }
                    }
                    if (!guesses[m - 1].equals(currLetter)
                            && m == guesses.length) {
                        System.out.print("_ ");
                    }
                } else {
                    System.out.print("_ ");
                    k++;
                }
            }
        }

    }

    /**
     * Returns true or false if the letter given is in the generated word
     */
    private static boolean letterInWord(String l, String word) {
        boolean inWord = false;
        // Surf through the selected word to see if the letter is inside it
        if (word.length() == 1 && word.equals(l)) {
            inWord = true;
        }
        if (word.length() > 1) {
            for (int i = 1; i < word.length() + 1; i++) {
                if (word.substring(i - 1, i).equals(l)) {
                    inWord = true;
                    break;
                }
            }
        }
        return inWord;
    }

    /**
     * Return the count of the given letter inside of the given word
     */
    private static int letterCount(String l, String word) {
        int count = 0;
        for (int i = 1; i < word.length() + 1; i++) {
            if (word.substring(i - 1, i).equals(l)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        int attempts = 8;
        int numGuesses = 0;
        int correctLetters = 0;
        String letterGuess = "";
        boolean wordFound = false;
        boolean inWord = false;
        boolean alreadyGuessed = false;
        /*
         * Step 1: Generate random word to guess
         */
        String word = generateWord();
        int possibleAttempts = word.length() * attempts;
        // String array that will store the user's letter guesses
        String[] guesses = new String[possibleAttempts];
        // Making sure the array has no null values inside of it
        for (int i = 0; i < guesses.length; i++) {
            guesses[i] = "";
        }
        /*
         * Step 2: While the word is not guessed or eight attempts have passed,
         * generate the hangman board and let the user guess a letter
         */
        while (attempts > 0 && wordFound == false) {
            alreadyGuessed = false;
            if (correctLetters == word.length()) {
                System.out.println(
                        "\nYou have guessed all the correct letters in the word! The word was "
                                + word + ".\nYou win!");
                Thread.sleep(1500);
                wordFound = true;
                break;
            }
            generateBoard(attempts, word, guesses);
            System.out.println(
                    "\n\nWhat would you like to do? \n1: Guess a Letter 2: Guess the Word");
            String option = in.nextLine();
            if (option.equals("1")) {
                System.out.print("What letter would you like to guess? ");
                letterGuess = in.nextLine();
                for (int i = 0; i < guesses.length; i++) {
                    if (letterGuess.equals(guesses[i])) {
                        alreadyGuessed = true;
                        break;
                    }
                }
                inWord = letterInWord(letterGuess, word);
                if (inWord == false && alreadyGuessed == false) {
                    System.out.println(
                            "\n" + letterGuess + " is not in the word.");
                    attempts--;
                    System.out.println(
                            "You have " + attempts + " guesses left.\n");
                    guesses[numGuesses] = letterGuess;
                    numGuesses++;
                    Thread.sleep(1500);
                } else if (inWord == true && alreadyGuessed == false) {
                    System.out
                            .println("\n" + letterGuess + " is in the word!\n");
                    guesses[numGuesses] = letterGuess;
                    numGuesses++;
                    correctLetters += letterCount(letterGuess, word);
                    Thread.sleep(1500);
                } else if ((inWord == false && alreadyGuessed == true)
                        || (inWord == true && alreadyGuessed == true)) {
                    System.out.println("\nYou have already guessed the letter "
                            + letterGuess
                            + ". Please choose another letter.\n");
                    Thread.sleep(1500);
                }
            } else if (option.equals("2")) {
                System.out.print(
                        "Enter your guess. If you guess incorrectly, you lose: ");
                String wordGuess = in.nextLine();
                if (wordGuess.equals(word)) {
                    System.out.println("\nYou guess was correct! The word was "
                            + word + "! You win!");
                    Thread.sleep(1500);
                    wordFound = true;
                } else {
                    System.out.println(
                            "\nYour guess was incorrect. The correct word was "
                                    + word + ". \nYou lose...");
                    Thread.sleep(1500);
                    attempts = -1;
                }
            } else {
                System.out.println(
                        "Invalid character typed. Please enter 1 or 2.");
                Thread.sleep(1500);
            }
        }
        /*
         * In case the user takes more than 8 attempts, they will lose the game
         * and this message will be printed
         */
        if (attempts == 0 && wordFound == false) {
            System.out.println(
                    "You've used up all of your guesses. The correct word was "
                            + word + ".");
            System.out.println("You lose...");
            Thread.sleep(1500);
        }
        /*
         * Closing the input stream
         */
        in.close();
    }

}
