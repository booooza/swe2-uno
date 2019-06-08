package ch.swe2.uno.business.card;

public class ActionCard extends AbstractCard {

    private UnoColor chosenColor;

    /**
     * Constructor
     *
     * @param type   Type of the card
     * @param color  Color of the card
     * @param number Number of the card
     */
    public ActionCard(CardType type, UnoColor color, int number) {
        super(type, color, number);
    }

    @Override
    boolean isValidCardNumber(int number) {
        return number == 22 || number == 33 || number == 44 || number == 88 || number == 99;
    }

    @Override
    public UnoColor getColor() {
        if (chosenColor != null) {
            return this.chosenColor;
        }
        return super.getColor();
    }

    public void chooseColor(UnoColor chosenColor) {
        this.chosenColor = chosenColor;
    }

    public void reset() {
        this.chosenColor = null;
    }
}
