package ch.swe2.uno.business.card;

public enum CardType {
    NUMBERCARD("Number"),
    SKIP("Skip"),
    REVERSE("Reverse"),
    WILD("Wild"),
    WILDDRAWFOUR("Wild Draw 4");

    private String type;

    CardType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
