import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HollomonClient gameClient = new HollomonClient();

        try {
            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            gameClient.connect();

            boolean success = gameClient.login(username, password);
            if (!success) {
                System.out.println("Login failed.");
                return;
            }

            List<Card> initialCards = gameClient.readInitialCards();

            Collections.sort(initialCards);
            System.out.println("Welcome to Hollomon Trading!");
            System.out.println("You received/own these cards:");
            printCards(initialCards);

            boolean running = true;
            // Main loop: repeatedly show menu and handle user choices
            while (running) {
                printMenu();
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        handleListCards(gameClient);
                        break;

                    case "2":
                        handleCredits(gameClient);
                        break;

                    case "3":
                        handleOffers(gameClient);
                        break;

                    case "4":
                        handleBuy(scanner, gameClient);
                        break;

                    case "5":
                        handleSell(scanner, gameClient);
                        break;

                    case "6":
                        running = false;
                        System.out.println("Thank you for playing!");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }
            }

        } catch (IOException e) {
            System.out.println("Network error: " + e.getMessage());
        } finally {
            gameClient.close();
            scanner.close();
        }
    }

    // Retrieves and displays the user's cards from the server
    private static void handleListCards(HollomonClient gameClient) throws IOException {
        List<Card> myCards = gameClient.getMyCards();
        Collections.sort(myCards);
        printCards(myCards);
    }

    // Displays the user's current credit balance
    private static void handleCredits(HollomonClient gameClient) throws IOException {
        int credits = gameClient.getCredits();
        System.out.println("Your Credit balance is: " + credits);
    }

    // Retrieves and displays available cards for purchase
    private static void handleOffers(HollomonClient gameClient) throws IOException {
        List<Card> offers = gameClient.getOffers();
        Collections.sort(offers);
        printCards(offers);
    }

    // Handles buying a card using a validated card ID
    private static void handleBuy(Scanner scanner, HollomonClient gameClient) throws IOException {
        long buyId = readLong(scanner, "Enter card ID to buy: ");

        if (gameClient.buyCard(buyId)) {
            System.out.println("Card bought successfully.");
        } else {
            System.out.println("Purchase failed. Card may not exist, check card ID");
        }
    }

    // Handles selling a card with user-specified price
    private static void handleSell(Scanner scanner, HollomonClient gameClient) throws IOException {
        long sellId = readLong(scanner, "Enter card ID to sell: ");
        int price = readInt(scanner, "Enter selling price: ");

        if (gameClient.sellCard(sellId, price)) {
            System.out.println("Card listed successfully.");
        } else {
            System.out.println("Could not sell card. Card may not exist, check card ID.");
        }
    }

    // Reads and validates integer input from user (prevents crashes)
    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // Reads and validates long input for large card IDs
    private static long readLong(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            try {
                return Long.parseLong(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1. list my cards");
        System.out.println("2. show my credits");
        System.out.println("3. list available cards");
        System.out.println("4. buy card");
        System.out.println("5. sell card");
        System.out.println("6. exit");
        System.out.print("Please enter your choice: ");
    }



    private static void printCards(List<Card> cards) {
        if (cards.isEmpty()) {
            System.out.println("No cards found.");
            return;
        }

        System.out.println("----------------------------------------------------------");
        System.out.printf("%-10s %-30s %-10s %-10s%n", "ID", "Name", "Rank", "Price");
        System.out.println("----------------------------------------------------------");

        for (Card card : cards) {
            System.out.printf("%-10d %-30s %-10s %-10d%n",
                    card.getId(),
                    card.getName(),
                    card.getRank(),
                    card.getPrice());
        }

        System.out.println("----------------------------------------------------------");
    }
}