// Enum representing card rarity levels
public enum CardRank {
    UNIQUE,
    RARE,
    UNCOMMON,
    COMMON;

    public static CardRank fromString(String text) {
        return CardRank.valueOf(text.trim().toUpperCase());
    }
}
