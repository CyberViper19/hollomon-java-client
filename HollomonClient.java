import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

// Handles communication with the Hollomon server (network operations)
public class HollomonClient {
    private static final String HOST = "netsrv.cim.rhul.ac.uk";
    private static final int PORT = 1812;

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public void connect() throws IOException {
        socket = new Socket(HOST, PORT);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public boolean login(String username, String password) throws IOException {
        writer.println(username.toLowerCase());
        writer.println(password);

        String response = reader.readLine();
        if (response == null) {
            return false;
        }

        if (!response.contains("logged in successfully")) {
            return false;
        }

        return true;
    }

    public List<Card> readInitialCards() throws IOException {
        return readCardList();
    }

    public List<Card> getMyCards() throws IOException {
        writer.println("CARDS");
        return readCardList();
    }

    public int getCredits() throws IOException {
        writer.println("CREDITS");
        String line = reader.readLine();
        int credits = Integer.parseInt(line.trim());

        String ok = reader.readLine();
        if (!"OK".equals(ok)) {
            throw new IOException("Expected OK after credits but got: " + ok);
        }

        return credits;
    }

    public List<Card> getOffers() throws IOException {
        writer.println("OFFERS");
        return readCardList();
    }

    public boolean buyCard(long cardId) throws IOException {
        writer.println("BUY " + cardId);
        String response = reader.readLine();
        return "OK".equals(response);
    }

    public boolean sellCard(long cardId, int price) throws IOException {
        writer.println("SELL " + cardId + " " + price);
        String response = reader.readLine();
        return "OK".equals(response);
    }

    // Reads a list of cards from the server until "OK" is received
    private List<Card> readCardList() throws IOException {
        List<Card> cards = new ArrayList<>();

        while (true) {
            String line = reader.readLine();
            if (line == null) {
                throw new IOException("Connection closed unexpectedly.");
            }

            if ("OK".equals(line)) {
                break;
            }

            if (!"CARD".equals(line)) {
                throw new IOException("Expected CARD or OK but got: " + line);
            }

            // Use long because server may send IDs larger than int range
            long id = Long.parseLong(reader.readLine().trim());
            String name = reader.readLine();
            CardRank rank = CardRank.fromString(reader.readLine());
            // Use long for price to handle large values returned by server
            long price = Long.parseLong(reader.readLine().trim());

            // Create Card object and add to list
            cards.add(new Card(id, name, rank, price));
        }

        return cards;
    }

    public void close() {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            // ignore
        }

        if (writer != null) {
            writer.close();
        }

        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            // ignore
        }
    }
}