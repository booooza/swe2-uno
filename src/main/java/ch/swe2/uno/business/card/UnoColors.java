package ch.swe2.uno.business.card;

public enum UnoColors {
    RED("Red"),
    BLUE("Blue"),
    YELLOW("Yellow"),
    GREEN("Green");

    private String color;

    UnoColors(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
