// Represents a trading card with ID, name, rank, and price
public class Card implements Comparable<Card> {
    private final long id;
    private final String name;
    private final CardRank rank;
    private final long price;

    public Card(long id, String name, CardRank rank, long price) {
        this.id = id;
        this.name = name;
        this.rank = rank;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CardRank getRank() {
        return rank;
    }

    public long getPrice() {
        return price;
    }

    // Defines sorting: rank first, then name, then ID
    @Override
    public int compareTo(Card other) {
        int rankCompare = Integer.compare(this.rank.ordinal(), other.rank.ordinal());
        if (rankCompare != 0) {
            return rankCompare;
        }

        int nameCompare = this.name.compareToIgnoreCase(other.name);
        if (nameCompare != 0) {
            return nameCompare;
        }

        return Long.compare(this.id, other.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Card)) {
            return false;
        }
        Card other = (Card) obj;
        return this.id == other.id
                && this.name.equals(other.name)
                && this.rank == other.rank
                && this.price == other.price;
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(id);
        result = 31 * result + name.hashCode();
        result = 31 * result + rank.hashCode();
        result = 31 * result + Long.hashCode(price);
        return result;
    }
}