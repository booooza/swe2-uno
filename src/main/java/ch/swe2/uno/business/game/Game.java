package ch.swe2.uno.business.game;

import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.deck.Deck;
import ch.swe2.uno.business.player.Player;
import ch.swe2.uno.business.player.PlayerInterface;
import ch.swe2.uno.business.state.State;
import com.google.gson.Gson;
import org.hildan.fxgson.FxGson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Game {
	private static final Logger logger = LoggerFactory.getLogger(Game.class);
	private State state;
	private Deck deck = new Deck();
	private Gson fxGson = FxGson.create();
	private boolean isRunning;
	private List<String> playerNames = new ArrayList<>();

	public Deck getDeck() {
		return deck;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public synchronized void addPlayer(String playerName) {
		if (!isRunning) {
			playerNames.add(playerName);
		}
	}

	public synchronized State initialize() {
		logger.info("Starting the game");

		if (!isRunning && playerNames.size() >= 2) {
			// Create players
			List<PlayerInterface> players = playerNames.stream()
					.map(Player::new)
					.collect(Collectors.toList());

			// Create initial state
			state = new State(players, "Initial state");

			// Create deck & distribute cards
			deck = new Deck();
			deck.create();
			deck.distribute(players);

			// Choose first player (TODO: randomize)
			players.get(0).setCurrentTurn(true);

			// Write player info to state
			state.setPlayers(players);
			state.setTopDiscardPileCard(deck.getTopCardOfDiscardPile());
			isRunning = true;
			state.setMessage("Game initialized");
			return state;
		}
		return new State();
	}

	public synchronized State playCard(String playerName, CardInterface card, boolean uno) {
		PlayerInterface player;
		Optional<PlayerInterface> optionalOfPlayer = state.getPlayerByName(playerName);
		if (optionalOfPlayer.isPresent()) {
			player = optionalOfPlayer.get();
		} else {
			throw new IllegalArgumentException("playerName");
		}
		// Check if is the players turn and if the players hand contains the mentioned card
		if (player.isCurrentTurn() && playersHandContainsExactCard(player, card)) {
			// Check if card matches current top card
			if (card.getColor().equals(state.getTopDiscardPileCard().getColor()) ||
					card.getNumber() == state.getTopDiscardPileCard().getNumber()) {
				// Remove from players hand
				removeCardFromPlayersHand(player, card);
				player.setUno(uno);

				logger.info("Player {} played card {} / {}", player.getName(), card.getColor(), card.getNumber());
				logger.info("Player {} has {} cards remaining in hand", player.getName(), player.getHand().size());

				// Check if player has won the game
				if (player.getHand().size() == 0) {
					state.setWinner(player.getName());
					state.setMessage("Player " + player.getName() + " has won the game");
					logger.info("Player {} has won the game", player.getName());
				} else {
					// Make new top card
					deck.addCardToDiscardPile(card);
					state.setTopDiscardPileCard(card);
					logger.info("Top card is {} / {}", card.getColor(), card.getNumber());
					// Toggle current turn flags
					checkUno();
					state.toggleCurrentTurn();
					// If its the players move let the bot play
					if (!playerName.equals("Bot")) {
						botAction();
					}
				}
			} else {
				state.setMessage("Invalid turn");
			}
		} else {
			state.setMessage("Invalid turn");
		}
		return state;
	}

	public synchronized State check(String playerName) {
		// Only Check and Play can trigger the next players turn

		// If its the players move let the bot play
		if (playerNames.contains("Bot") && !playerName.equals("Bot")) {
			checkUno();
			state.toggleCurrentTurn();
			botAction();
		}
		return state;
	}

	public synchronized State drawCard(String playerName) {
		PlayerInterface player;
		Optional<PlayerInterface> optionalOfPlayer = state.getPlayerByName(playerName);

		if (optionalOfPlayer.isPresent()) {
			player = optionalOfPlayer.get();
		} else {
			throw new IllegalArgumentException("playerName");
		}

		// Check if is the players turn and he didn't already draw
		if (player.isCurrentTurn() && player.canDraw()) {
			player.addCard(deck.drawCard());
			player.setCanDraw(false);
			logger.info("Player {} drawed card", player.getName());
			logger.info("Player {} has {} cards remaining in hand", player.getName(), player.getHand().size());

			if (playerNames.contains("Bot") && playerName.equals("Bot")) {
				botAction();
				checkUno();
				state.toggleCurrentTurn();
			}
		} else {
			state.setMessage("Invalid turn");
		}
		return state;
	}

	public void botAction() {
		// If hand matches topcard play first match
		Optional<CardInterface> optionalMatchingCard = randomCardMatchingTopCard("Bot");
		if (optionalMatchingCard.isPresent()) {
			CardInterface matchingCard = optionalMatchingCard.get();
			playCard("Bot", matchingCard, cardsLeftInPlayersHand("Bot") == 1);
			state.setMessage("Bot has played card " + matchingCard.getColor().toString() + " " + matchingCard.getNumber());
			logger.info("Bot has played card {} {}", matchingCard.getColor().toString(), matchingCard.getNumber());
		} else {
			PlayerInterface player;
			Optional<PlayerInterface> optionalOfPlayer = state.getPlayerByName("Bot");
			if (optionalOfPlayer.isPresent()) {
				player = optionalOfPlayer.get();
			} else {
				throw new IllegalArgumentException("playerName");
			}
			if (!player.canDraw()) {
				checkUno();
				state.toggleCurrentTurn();
				state.setMessage("Bot has already drawn a card, next player");
				logger.info("Bot has already drawn a card, next player");
			} else {
				drawCard("Bot");
				state.setMessage("Bot has drawn a card");
				logger.info("Bot has drawn a card");
			}

		}

		// If only one card is left in hand say uno
		// Else draw card

	}

	public State getState() {
		return Objects.requireNonNullElseGet(state, State::new);
	}

	private Optional<CardInterface> randomCardMatchingTopCard(String playerName) {
		if (state.getPlayerByName(playerName).isPresent()) {
			return state.getPlayerByName(playerName)
					.get().getHand()
					.stream()
					.filter(card -> card.getColor().equals(state.getTopDiscardPileCard().getColor()) ||
							card.getNumber() == state.getTopDiscardPileCard().getNumber())
					.findAny();
		} else {
			throw new NullPointerException();
		}
	}

	private int cardsLeftInPlayersHand(String playerName) {
		if (state.getPlayerByName(playerName).isPresent()) {
			return state.getPlayerByName(playerName)
					.get().getHand().size();
		} else {
			throw new NullPointerException();
		}
	}

	private boolean playersHandContainsExactCard(PlayerInterface player, CardInterface playedCard) {
		return player.getHand().stream()
				.anyMatch(card ->
						(card.getColor().equals(playedCard.getColor()) && card.getNumber() == playedCard.getNumber()));
	}

	private void removeCardFromPlayersHand(PlayerInterface player, CardInterface playedCard) {
		player.getHand().stream()
				.filter(card -> card.getColor().equals(playedCard.getColor()) && card.getNumber() == playedCard.getNumber())
				.findFirst()
				.ifPresent(card -> player.getHand().remove(card));
	}

	private void checkUno(){
		Optional<PlayerInterface> currentPlayerOptional = state.getCurrentPlayer();
		if(currentPlayerOptional.isPresent()){
			PlayerInterface currentPlayer = currentPlayerOptional.get();
			// Check if player forgot to say UNO
			if(currentPlayer.getHand().size() == 1 && !currentPlayer.isUno()){
				currentPlayer.addCard(deck.drawCard());
				logger.info("{} forgot to say UNO and therefore drawn 1 penalty card", currentPlayer.getName());
			} else if(currentPlayer.getHand().size() > 1 && currentPlayer.isUno()){
				currentPlayer.addCard(deck.drawCard());
				logger.info("{} said UNO but had more than 1 card and therefore drawn 1 penalty card", currentPlayer.getName());
			}
		}
	}
}
