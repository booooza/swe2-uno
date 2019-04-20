package ch.swe2.uno.business.game;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.deck.Deck;
import ch.swe2.uno.business.player.Player;
import ch.swe2.uno.business.player.PlayerInterface;
import ch.swe2.uno.business.state.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import org.hildan.fxgson.FxGson;

public class Game {
    private State state;
    private Deck deck = new Deck();
    private Gson fxGson = FxGson.create();
    private static final Logger logger = LoggerFactory.getLogger(Game.class);
    private boolean isRunning;
    private List<String> playerNames = new ArrayList<>();

    public void addPlayer(String playerName){
        if(!isRunning) {
            playerNames.add(playerName);
        }
    }

    public State initialize() {
        logger.info("Starting the game");

        if (!isRunning) {
            // Create players
            List<PlayerInterface> players = playerNames.stream()
                    .map(Player::new)
                    .collect(Collectors.toList());


            // Create initial state
            state = new State(players, "Initial state");

            // Create deck & distribute cards
            deck.create();
            deck.distribute(players);

            // Choose first player (TODO: randomize)
            players.get(0).setCurrentTurn(true);

            // Write player info to state
            state.setPlayers(players);
            state.setTopDiscardPileCard(deck.getTopCardOfDiscardPile());
            isRunning = true;
            state.setMessage("Game initialized");
        }
        return state;
    }

    public State playCard(String playerName, CardInterface card) {
        PlayerInterface player;
        Optional<PlayerInterface> optionalOfPlayer = state.getPlayerByName(playerName);
        // TODO: if(player == null) -> throw new IllegalArgumentException();
        // TODO: if(card == null) -> throw new IllegalArgumentException();

        if(optionalOfPlayer.isPresent()){
            player = optionalOfPlayer.get();
        } else {
            throw new IllegalArgumentException("playerName");
        }

        // Check if is the players turn and if the players hand contains the mentioned card
        // TODO: state.isCurrentTurn(player) && player.hasCard(card)
        if (player.isCurrentTurn() && player.getHand().contains(card)) {
            // Check if card matches current top card
            // TODO: state.getTopDiscardPileCard()
            if (card.getColor().equals(state.getTopDiscardPileCard().getColor()) ||
                    card.getNumber() == state.getTopDiscardPileCard().getNumber()) {
                // Remove from players hand
                player.getHand().remove(card);
                logger.info("Player {} played card {} / {}", player.getName(), card.getColor(), card.getNumber());
                logger.info("Player {} has {} cards remaining in hand", player.getName(), player.getHand().size());
                // Make new top card
                state.setTopDiscardPileCard(card);
                logger.info("Top card is {} / {}", card.getColor(), card.getNumber());
                // Toggle current turn flags
                state.toggleCurrentTurn();
            } else {
                state.setMessage("Invalid turn");
            }
        } else {
            state.setMessage("Invalid turn");
        }
        return state;
    }

    public State drawCard(String playerName) {
        PlayerInterface player;
        Optional<PlayerInterface> optionalOfPlayer = state.getPlayerByName(playerName);

        if(optionalOfPlayer.isPresent()){
            player = optionalOfPlayer.get();
        } else {
            throw new IllegalArgumentException("playerName");
        }

        // Check if is the players turn
        if (player.isCurrentTurn()) {
            player.addCard(deck.drawCard());
            player.setCanEndTurn(true);
            logger.info("Player {} drawed card", player.getName());
            logger.info("Player {} has {} cards remaining in hand", player.getName(), player.getHand().size());
            // TODO: handle play after drawing
            // state.toggleCurrentTurn();
        } else {
            state.setMessage("Invalid turn");
        }
        return state;
    }

    public State getState() {
        return state;
    }
}
