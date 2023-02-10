import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class CasinoWar {
    //deck of cards
    private ArrayList<String> deck = new ArrayList<>();
    //player's starting money
    private int playerM = 1000;
    //player's current wager
    private int wager = 0;
    //number of wins
    private int wins = 0;
    //number of losses
    private int losses = 0;
    //number of money won
    private int moneyW = 0;

    public static void main(String[] args) {
        //create an instance of the CasinoWar game
        CasinoWar game = new CasinoWar();
        //start playing the game
        game.play();
    }

    //constructor method that creates the deck of cards
    public CasinoWar() {
        createDeck();
    }

    //main logic for playing the game
    public void play() {
        //Scanner object for input
        Scanner sc = new Scanner(System.in);
        //flag to keep track of if the player wants to keep playing
        boolean playing = true;

        //loop to keep playing the game as long as the player wants to
        while (playing) {
            //ask the player for their wager
            System.out.println("You have $" + playerM + ". Enter your wager: ");
            wager = sc.nextInt();

            //check if the player has enough money to place the wager
            if (wager > playerM) {
                System.out.println("You don't have enough money to place that wager.");
                //continue to the next iteration of the loop
                continue;
            }
            if (wager <= 0) {
                System.out.println("You can't wager less than $1.");
                //continue to the next iteration of the loop
                continue;
            }

            //deal cards for the player and dealer
            String playerCard = dealCard();
            String dealerCard = dealCard();

            //display the cards for the player and dealer
            System.out.println("Your card is " + playerCard + ". The dealer's card is " + dealerCard + ".");

            //check who wins or if it's a tie
            if (getCardValue(playerCard) > getCardValue(dealerCard)) {
                //player wins, increase their money and add to the wins count
                playerM += wager;
                wins++;
                System.out.println("You win! You now have $" + playerM + ".");
            } else if (getCardValue(playerCard) < getCardValue(dealerCard)) {
                //player loses, decrease their money and add to the losses count
                playerM -= wager;
                losses++;
                System.out.println("You lose. You now have $" + playerM + ".");
            } else {
                System.out.println("Tie!");
                //ask the player if they want to surrender or go to war
                System.out.println("Do you want to surrender (s) or go to war (w)?");
                String response = sc.next();
                if (response.equalsIgnoreCase("s") || response.equalsIgnoreCase("surrender")) {
                    //player surrenders, decrease their money by half and add to the losses count
                    playerM -= wager / 2;
                    losses++;
                    System.out.println("You surrendered. You now have $" + playerM + ".");

                    // Check if the player chose to go to war
                } else if (response.equalsIgnoreCase("w") || response.equalsIgnoreCase("war")) {
                    // Double the wager
                    wager *= 2;
                    System.out.println("You chose to go to war and doubled your stake to $" + wager + ".");
                    String playerWarCard = dealCard();
                    String dealerWarCard = dealCard();
                    System.out.println("Your war card is " + playerWarCard + ". The dealer's war card is " + dealerWarCard + ".");
                    // Determine the winner of the war
                    if (getCardValue(playerWarCard) > getCardValue(dealerWarCard)) {
                        playerM += wager;
                        wins++;
                        System.out.println("You win the war! You now have $" + playerM + ".");
                    } else {
                        playerM -= wager;
                        losses++;
                        System.out.println("You lose the war. You now have $" + playerM + ".");
                    }
                }
            }

            // Check if the player has no money left
            if (playerM <= 0) {
                System.out.println("You have no money left. Game over.");
                playing = false;
            } else {
                System.out.println("You won " + wins + " times and lost " + losses + " times.");
                System.out.println("Do you want to keep playing? (y/n)");
                String response = sc.next();
                // Check if the player wants to keep playing
                if (!response.equalsIgnoreCase("y") && !response.equalsIgnoreCase("yes")) {
                    playing = false;
                }
            }
        }
        moneyW = playerM - 1000;
        if(playerM > 1000) {
            System.out.println("You won " + wins + " times and lost " + losses + " times.");
            System.out.println("You ended the game with $" + playerM + " and you have a gain $" + moneyW +".");
        } else if(playerM < 1000) {
            System.out.println("You won " + wins + " times and lost " + losses + " times.");
            System.out.println("You ended the game with $" + playerM + " and you have lost $" + moneyW +".");
        }
        else{
            System.out.println("You won " + wins + " times and lost " + losses + " times.");
            System.out.println("You ended the game with $" + playerM + " and you have not lost or gain any money" + ".");
        }
    }

    private void createDeck() {
        // Declaring the suits of a deck of cards
        String[] suits = {"Hearts", "Diamonds", "Spades", "Clubs"};
        // Declaring the ranks of a deck of cards
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        // Iterating over the suits array and ranks array to create a deck of cards
        for (String suit : suits) {
            for (String rank : ranks) {
                // Adding each card to the deck list
                deck.add(rank + " of " + suit);
            }
        }
    }

    private String dealCard() {
        // Shuffling the deck to randomize the order
        Collections.shuffle(deck);
        // Removing and returning the first card from the deck
        return deck.remove(0);
    }

    private int getCardValue(String card) {
        // Extracting the rank of the card from the input string
        String rank = card.split(" ")[0];
        // Determining the value of the card based on its rank
        switch (rank) {
            case "A":
                return 14;
            case "K":
                return 13;
            case "Q":
                return 12;
            case "J":
                return 11;
            default:
                return Integer.parseInt(rank);
        }
    }
}
