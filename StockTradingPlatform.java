import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Stock {
    private String symbol;
    private String companyName;
    private double price;

    public Stock(String symbol, String companyName, double price) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public double getPrice() {
        return price;
    }
}

class Portfolio {
    private double balance;
    private Map<String, Integer> holdings;

    public Portfolio(double balance) {
        this.balance = balance;
        holdings = new HashMap<>();
    }

    public double getBalance() {
        return balance;
    }

    public void buyStock(Stock stock, int quantity) {
        double cost = stock.getPrice() * quantity;

        if (cost > balance) {
            System.out.println("Insufficient Balance!");
            return;
        }

        balance -= cost;

        holdings.put(
                stock.getSymbol(),
                holdings.getOrDefault(stock.getSymbol(), 0) + quantity
        );

        System.out.println("Successfully Purchased " +
                quantity + " shares of " + stock.getSymbol());
    }

    public void sellStock(Stock stock, int quantity) {

        int owned = holdings.getOrDefault(stock.getSymbol(), 0);

        if (owned < quantity) {
            System.out.println("Not enough shares to sell!");
            return;
        }

        balance += stock.getPrice() * quantity;

        holdings.put(stock.getSymbol(), owned - quantity);

        if (holdings.get(stock.getSymbol()) == 0) {
            holdings.remove(stock.getSymbol());
        }

        System.out.println("Successfully Sold " +
                quantity + " shares of " + stock.getSymbol());
    }

    public void displayPortfolio(ArrayList<Stock> marketStocks) {

        System.out.println("\n===== PORTFOLIO =====");

        if (holdings.isEmpty()) {
            System.out.println("No stocks owned.");
        } else {

            double totalValue = 0;

            for (Stock stock : marketStocks) {

                String symbol = stock.getSymbol();

                if (holdings.containsKey(symbol)) {

                    int quantity = holdings.get(symbol);

                    double value = quantity * stock.getPrice();

                    totalValue += value;

                    System.out.println(
                            symbol +
                                    " | Shares: " + quantity +
                                    " | Value: ₹" + value
                    );
                }
            }

            System.out.println("----------------------");
            System.out.println("Portfolio Value: ₹" + totalValue);
        }

        System.out.println("Available Balance: ₹" + balance);
    }
}

public class StockTradingPlatform {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<Stock> market = new ArrayList<>();

        market.add(new Stock("TCS", "Tata Consultancy Services", 3800));
        market.add(new Stock("INFY", "Infosys", 1600));
        market.add(new Stock("RELI", "Reliance Industries", 2900));
        market.add(new Stock("HDFC", "HDFC Bank", 1700));

        Portfolio portfolio = new Portfolio(100000);

        int choice;

        do {

            System.out.println("\n==============================");
            System.out.println(" STOCK TRADING PLATFORM");
            System.out.println("==============================");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");
            System.out.print("Enter Choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:

                    System.out.println("\nMarket Stocks");

                    for (int i = 0; i < market.size(); i++) {

                        Stock stock = market.get(i);

                        System.out.println(
                                (i + 1) + ". " +
                                        stock.getSymbol() +
                                        " - " +
                                        stock.getCompanyName() +
                                        " - ₹" +
                                        stock.getPrice()
                        );
                    }
                    break;

                case 2:

                    System.out.println("\nSelect Stock Number:");

                    for (int i = 0; i < market.size(); i++) {
                        System.out.println(
                                (i + 1) + ". " +
                                        market.get(i).getSymbol()
                        );
                    }

                    int buyIndex = sc.nextInt() - 1;

                    if (buyIndex < 0 || buyIndex >= market.size()) {
                        System.out.println("Invalid Stock!");
                        break;
                    }

                    System.out.print("Enter Quantity: ");
                    int buyQty = sc.nextInt();

                    portfolio.buyStock(
                            market.get(buyIndex),
                            buyQty
                    );

                    break;

                case 3:

                    System.out.println("\nSelect Stock Number:");

                    for (int i = 0; i < market.size(); i++) {
                        System.out.println(
                                (i + 1) + ". " +
                                        market.get(i).getSymbol()
                        );
                    }

                    int sellIndex = sc.nextInt() - 1;

                    if (sellIndex < 0 || sellIndex >= market.size()) {
                        System.out.println("Invalid Stock!");
                        break;
                    }

                    System.out.print("Enter Quantity: ");
                    int sellQty = sc.nextInt();

                    portfolio.sellStock(
                            market.get(sellIndex),
                            sellQty
                    );

                    break;

                case 4:
                    portfolio.displayPortfolio(market);
                    break;

                case 5:
                    System.out.println("Thank You!");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 5);

        sc.close();
    }
}