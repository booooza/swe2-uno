package ch.swe2.uno.business.card;

public enum UnoColor {
    RED("Red"),
    BLUE("Blue"),
    YELLOW("Yellow"),
    GREEN("Green");

    private String color;

    UnoColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
