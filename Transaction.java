import java.util.*;

class Transaction {
    private String transactionId;
    private String drugId;
    private String from;
    private String to;
    private Date timestamp;
    private String hash;

    public Transaction(String transactionId, String drugId, String from, String to) {
        this.transactionId = transactionId;
        this.drugId = drugId;
        this.from = from;
        this.to = to;
        this.timestamp = new Date();
        this.hash = calculateHash();
    }

    // Change access modifier to public to allow access from outside the class
    public String calculateHash() {
        int hashValue = (transactionId.hashCode() + drugId.hashCode() + from.hashCode() + to.hashCode() + timestamp.hashCode()) % 10000;
        return String.format("%04d", Math.abs(hashValue));
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getDrugId() {
        return drugId;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getHash() {
        return hash;
    }

    @Override
    public String toString() {
        return String.format("%-15s %-10s %-15s %-15s %-30s %-10s", transactionId, drugId, from, to, timestamp, hash);
    }
}

class PharmaSupplyChain {
    private Map<String, Transaction> blockchain;

    public PharmaSupplyChain() {
        blockchain = new HashMap<>();
        Transaction genesisTransaction = new Transaction("0", "0", "Genesis", "Pharma");
        blockchain.put(genesisTransaction.getTransactionId(), genesisTransaction);
    }

    public void addTransaction(String transactionId, String drugId, String from, String to) {
        Transaction transaction = new Transaction(transactionId, drugId, from, to);
        blockchain.put(transactionId, transaction);
        System.out.println("Transaction added: " + transaction);
    }

    public void displayTransactions() {
        System.out.printf("%-15s %-10s %-15s %-15s %-30s %-10s\n", "Transaction ID", "Drug ID", "From", "To", "Timestamp", "Hash");
        System.out.println("-------------------------------------------------------------------------------------------------------");
        for (Transaction transaction : blockchain.values()) {
            System.out.println(transaction);
        }
    }

    public void deleteTransaction(String transactionId) {
        if (blockchain.remove(transactionId) != null) {
            System.out.println("Transaction with ID " + transactionId + " removed.");
        } else {
            System.out.println("Transaction ID not found.");
        }
    }

    public void validateBlockchain() {
        List<Transaction> transactions = new ArrayList<>(blockchain.values());
        for (int i = 1; i < transactions.size(); i++) {
            Transaction currentTransaction = transactions.get(i);
            if (!currentTransaction.getHash().equals(currentTransaction.calculateHash())) {
                System.out.println("Hash mismatch at index " + i);
                return;
            }
        }
        System.out.println("Blockchain is valid.");
    }
}

public class Main {
    public static void main(String[] args) {
        PharmaSupplyChain supplyChain = new PharmaSupplyChain();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nPharma Supply Chain Management System:");
            System.out.println("1. Add Transaction");
            System.out.println("2. View Transactions");
            System.out.println("3. Remove Transaction");
            System.out.println("4. Validate Blockchain");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();  // Clear the newline

            switch (option) {
                case 1:
                    System.out.print("Enter Transaction ID: ");
                    String transactionId = scanner.nextLine();
                    System.out.print("Enter Drug ID: ");
                    String drugId = scanner.nextLine();
                    System.out.print("Enter From: ");
                    String from = scanner.nextLine();
                    System.out.print("Enter To: ");
                    String to = scanner.nextLine();
                    supplyChain.addTransaction(transactionId, drugId, from, to);
                    break;

                case 2:
                    supplyChain.displayTransactions();
                    break;

                case 3:
                    System.out.print("Enter Transaction ID to remove: ");
                    String removeId = scanner.nextLine();
                    supplyChain.deleteTransaction(removeId);
                    break;

                case 4:
                    supplyChain.validateBlockchain();
                    break;

                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
