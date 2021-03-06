package ch.swe2.uno.business.game;

import ch.swe2.uno.business.card.ActionCard;
import ch.swe2.uno.business.card.CardInterface;
import ch.swe2.uno.business.card.CardType;
import ch.swe2.uno.business.card.UnoColor;
import ch.swe2.uno.business.deck.Deck;
import ch.swe2.uno.business.player.PlayerInterface;
import ch.swe2.uno.business.state.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class Game {
	private static Logger logger = LoggerFactory.getLogger(Game.class);
	private State state;
	private Deck deck;
	private boolean isRunning;

	public Game(Deck deck) {
		this.deck = deck;
		initialize();
	}

	boolean isRunning() {
		return isRunning;
	}

	public synchronized State addPlayer(String playerName) {
		if (!isRunning) {
			state.addPlayer(playerName);
		}
		return state;
	}

	synchronized void initialize() {
		logger.info("Starting the game");
		state = new State(new ArrayList<>(), "Initial state");
	}

	public synchronized State start() {
		if (state.getPlayers().isEmpty()) {
			throw new IllegalStateException("no player joined the game");
		}

		if (state.getPlayers().size() == 1) {
			this.addPlayer("Bot");
		}

		if (!isRunning && state.getPlayers().size() >= 2) {
			// Distribute cards
			deck.distribute(state.getPlayers());

			int randNumber = ThreadLocalRandom.current().nextInt(0, state.getPlayers().size() - 1);
			state.getPlayers().get(randNumber).setCurrentTurn(true);

			// Write player info to state
			state.setPlayers(state.getPlayers());
			state.setTopDiscardPileCard(deck.getTopCardOfDiscardPile());
			isRunning = true;
			state.setMessage("Game initialized");
			return state;
		}
		return new State();
	}

	public synchronized State restart() {
		isRunning = false;
		deck = new Deck();
		return state.restart();
	}

	public synchronized void playCard(String playerName, CardInterface card, boolean uno, UnoColor chosenColor) {
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
					card.getNumber() == state.getTopDiscardPileCard().getNumber() ||
					card.getType().equals(CardType.WILD) ||
					state.getTopDiscardPileCard().getType().equals(CardType.WILD)
			) {
				// Remove from players hand
				removeCardFromPlayersHand(player, card);
				player.setUno(uno);

				// If it's a wildcard change the color
				if (card.getType().equals(CardType.WILD)) {
					((ActionCard) card).chooseColor(chosenColor);
				}

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
					if (state.containsPlayer("Bot") && !playerName.equals("Bot")) {
						botAction();
					}
				}
			} else {
				state.setMessage("Invalid turn");
			}
		} else {
			state.setMessage("Invalid turn");
		}
	}

	public synchronized void check(String playerName) {
		// Only Check and Play can trigger the next players turn
		state.toggleCurrentTurn();
		// If its the players move let the bot play
		if (state.containsPlayer("Bot") && !playerName.equals("Bot")) {
			checkUno();
			botAction();
		}
	}

	public synchronized void drawCard(String playerName) {
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

			logger.info("Player {} has {} cards remaining in hand", player.getName(), player.getHand().size());

			if (state.containsPlayer("Bot") && playerName.equals("Bot")) {
				state.setMessage("Player Bot drawed card");
				botAction();
			}
		} else {
			state.setMessage("Invalid turn");
		}
	}

	private void botAction() {
		// If hand matches topcard play first match
		Optional<CardInterface> optionalMatchingCard = randomCardMatchingTopCard("Bot");
		if (optionalMatchingCard.isPresent()) {
			CardInterface matchingCard = optionalMatchingCard.get();
			UnoColor chosenColor = UnoColor.RED;
			if (matchingCard.getType() == CardType.WILD) {
				chosenColor = getColorFromRemainingCards("Bot");
			}
			playCard("Bot", matchingCard, cardsLeftInPlayersHand("Bot") == 2, chosenColor);
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
			if (player.canDraw()) {
				drawCard("Bot");
				logger.info("Bot has drawn a card");
			} else {
				state.toggleCurrentTurn();
				state.setMessage("Bot has already drawn a card, next player");
				logger.info("Bot has already drawn a card, next player");
			}
		}
	}

	public synchronized State getState() {
		return Objects.requireNonNullElseGet(state, State::new);
	}

	private Optional<CardInterface> randomCardMatchingTopCard(String playerName) {
		Optional<PlayerInterface> optionalCurrentPlayer = state.getPlayerByName(playerName);
		if (optionalCurrentPlayer.isPresent()) {
			PlayerInterface currentPlayer = optionalCurrentPlayer.get();
			Optional<CardInterface> possibleCard = currentPlayer.getHand().stream()
					.filter(card -> card.getColor().equals(state.getTopDiscardPileCard().getColor())
							|| card.getNumber() == state.getTopDiscardPileCard().getNumber())
					.findAny();
			if (!possibleCard.isPresent()) {
				possibleCard = currentPlayer.getHand().stream()
						.filter(c -> c.getType() == CardType.WILD).findAny();
			}
			return possibleCard;
		} else {
			throw new NullPointerException();
		}
	}

	private UnoColor getColorFromRemainingCards(String playerName) {
		Optional<PlayerInterface> optionalCurrentPlayer = state.getPlayerByName(playerName);
		if (optionalCurrentPlayer.isPresent()) {
			Optional<CardInterface> possibleCard = optionalCurrentPlayer.get().getHand().stream().filter(
					card -> card.getType() != CardType.WILD).findFirst();
			if (possibleCard.isPresent()) {
				return possibleCard.get().getColor();
			}
		}
		// return default color
		return UnoColor.RED;
	}

	private int cardsLeftInPlayersHand(String playerName) {
		Optional<PlayerInterface> optionalCurrentPlayer = state.getPlayerByName(playerName);
		if (optionalCurrentPlayer.isPresent()) {
			return optionalCurrentPlayer.get().getHand().size();
		} else {
			throw new NullPointerException();
		}
	}

	private boolean playersHandContainsExactCard(PlayerInterface player, CardInterface playedCard) {
		return player.getHand().stream().anyMatch(
				card -> (card.getColor().equals(playedCard.getColor()) && card.getNumber() == playedCard.getNumber()));
	}

	private void removeCardFromPlayersHand(PlayerInterface player, CardInterface playedCard) {
		player.getHand().stream().filter(
				card -> card.getColor().equals(playedCard.getColor()) && card.getNumber() == playedCard.getNumber())
				.findFirst().ifPresent(card -> player.getHand().remove(card));
	}

	private void checkUno() {
		Optional<PlayerInterface> currentPlayerOptional = state.getCurrentPlayer();
		if (currentPlayerOptional.isPresent()) {
			PlayerInterface currentPlayer = currentPlayerOptional.get();
			// Check if player forgot to say UNO
			if (currentPlayer.getHand().size() == 1 && !currentPlayer.isUno()) {
				currentPlayer.addCard(deck.drawCard());
				logger.info("{} forgot to say UNO and therefore drawn 1 penalty card", currentPlayer.getName());
			} else if (currentPlayer.getHand().size() > 1 && currentPlayer.isUno()) {
				currentPlayer.addCard(deck.drawCard());
				logger.info("{} said UNO but had more than 1 card and therefore drawn 1 penalty card",
						currentPlayer.getName());
			}
		}
	}

	public enum PlayDirection {
		BOTTOM_UP,
		TOP_DOWN
	}
}
