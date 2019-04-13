package ch.swe2.uno.business.game;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.deck.Deck;
import ch.swe2.uno.business.player.Player;
import ch.swe2.uno.business.player.PlayerInterface;
import ch.swe2.uno.business.state.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Optional;

public class Game {
    private State state;
    private Deck deck = Deck.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(Game.class);

    /**
     * Defines the private instance attribute
     */
    private static Game ourInstance = new Game();

    /**
     * Constructor must be private for singleton
     */
    private Game(){}

    /**
     * Return the singleton instance
     * @return ourInstance
     */
    public static Game getInstance(){
        return ourInstance;
    }

    public void initialize(ArrayList<PlayerInterface> players) {
        logger.info("Starting the game");
        state = new State(players, "Initial state");

        while (players.stream().allMatch(player -> player.getHand().size() == 0)) {
            // Create deck & distribute cards
            deck.create();
            deck.distribute(players);

            // Choose first player
            players.get(0).setCurrentTurn(true);
            players.get(1).setCurrentTurn(false);

            // Write player info to state
            state.setPlayers(players);
            state.setMessage("Game initialized");
            state.setTopCard(deck.getTopCardOfDiscardPile());
        }
    }

    public void playCard(PlayerInterface player, CardInterface card) {
        PlayerInterface playerState = state.getPlayerByName(player.getName());

        // Check if is the players turn and if the players hand contains the mentioned card
        if (playerState.isCurrentTurn() && playerState.getHand().contains(card)) {
            // Check if card matches current top card
            if (card.getColor().equals(state.getTopCard().getColor()) ||
                    card.getNumber() == state.getTopCard().getNumber()) {
                // Remove from players hand
                playerState.getHand().remove(card);
                logger.info("Player {} played card {} / {}", player.getName(), card.getColor(), card.getNumber());
                logger.info("Player {} has {} cards remaining in hand", player.getName(), player.getHand().size());
                // Make new top card
                state.setTopCard(card);
                logger.info("Top card is {} / {}", card.getColor(), card.getNumber());
                // Toggle current turn flags
                state.toggleCurrentTurn();
            } else {
                throw new IllegalStateException();
            }
        } else {
            throw new IllegalStateException();
        }
    }

    public void drawCard(PlayerInterface player) {
        PlayerInterface fromPlayer = state.getPlayerByName(player.getName());

        // Check if is the players turn and if the players hand contains the mentioned card
        if (fromPlayer.isCurrentTurn()) {
            deck.drawCard(player);
            logger.info("Player {} drawed card", player.getName());
            logger.info("Player {} has {} cards remaining in hand", player.getName(), player.getHand().size());
            // Toggle current turn flags
            state.toggleCurrentTurn();
        } else {
            throw new IllegalStateException();
        }
    }

    public PlayerInterface getCurrentPlayer() {
        Optional currentPlayer = state.getPlayers()
                .stream()
                .findFirst()
                .filter(p -> p.isCurrentTurn());

        if (currentPlayer.isPresent()) {
            return (PlayerInterface) currentPlayer.get();
        } else {
            throw new IllegalStateException();
        }
    }

    public State getState() {
        return this.state;
    }
}
