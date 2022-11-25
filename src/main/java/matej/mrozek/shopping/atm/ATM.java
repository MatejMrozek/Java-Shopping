package matej.mrozek.shopping.atm;

import matej.mrozek.shopping.Logger;
import matej.mrozek.shopping.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ATM {
    final List<Account> accounts = new ArrayList<>();

    Account loggedAccount = null;

    public void init() {
        boolean leaving = false;
        do {
            Logger.clear();

            if (loggedAccount == null) {
                Logger.printDivider();
                Logger.print("ATM - Login Menu");
                Logger.print();
                Logger.print("1) Log in");
                Logger.print("2) Sign up");
                Logger.print("3) Leave");
                Logger.printDivider();
                Logger.print();

                int option;
                String optionInput = new Scanner(System.in).nextLine();
                Logger.print();
                try {
                    option = Integer.parseInt(optionInput);
                } catch (Exception exception) {
                    option = -1;
                }

                switch (option) {
                    case 1 -> {
                        Logger.clear();

                        Logger.printDivider();
                        Logger.print("ATM - Log in");
                        Logger.printDivider();
                        Logger.print();

                        boolean goBack = false;
                        Account selectedAccount = null;
                        do {
                            Logger.print("Enter your username (leave blank to go back): ", true);
                            String usernameInput = new Scanner(System.in).nextLine();
                            Logger.print();
                            if (usernameInput.equals("")) {
                                goBack = true;
                                break;
                            } else if (usernameInput.length() < 3) {
                                Logger.print("Usernames can't be shorter than 3 characters, try again.");
                            } else if (usernameInput.length() > 16) {
                                Logger.print("Usernames can't be longer than 16 characters, try again.");
                            } else if (usernameInput.matches("[0-9]")) {
                                Logger.print("Usernames can't contain numbers, try again");
                            } else if (usernameInput.matches("[~!@#$%^&*()_+{}\\[\\]:;,.<>/?\n]")) {
                                Logger.print("Usernames can't contain special characters, try again.");
                            } else {
                                boolean invalidAccount = true;
                                for (Account account : accounts) {
                                    if (account.username.equalsIgnoreCase(usernameInput)) {
                                        selectedAccount = account;
                                        invalidAccount = false;
                                        break;
                                    }
                                }

                                if (invalidAccount) {
                                    Logger.print("Account with this username doesn't exist, try again.");
                                }
                            }
                        } while (selectedAccount == null);

                        boolean passwordIncorrect = true;
                        do {
                            Logger.print("Enter your password (leave blank to go back): ", true);
                            String passwordInput = new Scanner(System.in).nextLine();
                            if (passwordInput.equals("")) {
                                goBack = true;
                            } else if (passwordInput.length() < 8) {
                                Logger.print("Passwords can't be shorter than 8 characters, try again.");
                            } else if (passwordInput.length() > 16) {
                                Logger.print("Usernames can't be longer than 16 characters, try again.");
                            } else if (selectedAccount.checkPasswordCorrect(passwordInput)) {
                                passwordIncorrect = false;
                            } else {
                                Logger.print("Incorrect password, try again.");
                            }

                            Logger.print();
                        } while (passwordIncorrect && !goBack);

                        if (goBack) {
                            break;
                        }

                        Logger.print("Logging in...");

                        loggedAccount = selectedAccount;

                        Logger.print("Successfully logged in as " + loggedAccount.username + ".");

                        Main.sleep(2000);
                    }
                    case 2 -> {
                        Logger.clear();

                        Logger.printDivider();
                        Logger.print("ATM - Sign up");
                        Logger.printDivider();
                        Logger.print();

                        boolean goBack = false;
                        String username = null;
                        do {
                            Logger.print("Enter your new username (leave blank to go back): ", true);
                            String usernameInput = new Scanner(System.in).nextLine();
                            Logger.print();
                            if (usernameInput.equals("")) {
                                goBack = true;
                                break;
                            } else if (usernameInput.length() < 3) {
                                Logger.print("Usernames can't be shorter than 3 characters, try again.");
                            } else if (usernameInput.length() > 16) {
                                Logger.print("Usernames can't be longer than 16 characters, try again.");
                            } else if (usernameInput.matches("[0-9]")) {
                                Logger.print("Usernames can't contain numbers, try again");
                            } else if (usernameInput.matches("[~!@#$%^&*()_+{}\\[\\]:;,.<>/?\n]")) {
                                Logger.print("Usernames can't contain special characters, try again.");
                            } else {
                                boolean usernameUsed = false;
                                for (Account account : accounts) {
                                    if (account.username.equalsIgnoreCase(usernameInput)) {
                                        Logger.print("Someone is already using that username, try again.");
                                        usernameUsed = true;
                                    }
                                }

                                if (!usernameUsed) {
                                    username = usernameInput;
                                }
                            }
                        } while (username == null);

                        String password = null;
                        do {
                            Logger.print("Enter your new password (leave blank to go back): ", true);
                            String passwordInput = new Scanner(System.in).nextLine();
                            Logger.print();
                            if (passwordInput.equals("")) {
                                goBack = true;
                                break;
                            } else if (passwordInput.length() < 8) {
                                Logger.print("Passwords can't be shorter than 8 characters, try again.");
                            } else if (passwordInput.length() > 16) {
                                Logger.print("Usernames can't be longer than 16 characters, try again.");
                            } else {
                                password = passwordInput;
                            }
                        } while (password == null && !goBack);

                        if (goBack) {
                            break;
                        }

                        Logger.print("Signing up...");

                        Account newAccount = new Account(username, password);

                        accounts.add(newAccount);

                        Logger.print("Successfully registered with username " + newAccount.username + ".");

                        Main.sleep(2000);
                    }
                    case 3 -> {
                        Logger.print("Leaving the ATM.");

                        leaving = true;
                        Main.sleep(2000);
                    }
                    default -> {
                        Logger.print("Invalid input! Try again.");

                        Main.sleep(2000);
                    }
                }
            } else {
                Logger.printDivider();
                Logger.print("ATM - Main Menu");
                Logger.print();
                Logger.print("Username: " + loggedAccount.username);
                Logger.print("Balance: " + loggedAccount.getBalance() + "CZK");
                Logger.print();
                Logger.print("1) Withdraw money");
                Logger.print("2) Deposit money");
                Logger.print("3) Send money");
                Logger.print("4) Log out");
                Logger.printDivider();
                Logger.print();

                int option;
                String optionInput = new Scanner(System.in).nextLine();
                try {
                    option = Integer.parseInt(optionInput);
                } catch (Exception exception) {
                    option = -1;
                }

                switch (option) {
                    case 1 -> {
                        while (true) {
                            Logger.clear();

                            Logger.printDivider();
                            Logger.print("ATM - Withdraw");
                            Logger.print();
                            Logger.print("Username: " + loggedAccount.username);
                            Logger.print("Balance: " + loggedAccount.getBalance() + "CZK");
                            Logger.printDivider();
                            Logger.print();

                            Logger.print("How much do you want to withdraw (0 to go back)? ", true);
                            int amount;
                            String amountInput = new Scanner(System.in).nextLine();
                            Logger.print();
                            try {
                                amount = Integer.parseInt(amountInput);
                            } catch (Exception exception) {
                                Logger.print("Invalid amount, try again.");
                                Main.sleep(1500);
                                continue;
                            }

                            if (amount < 0) {
                                Logger.print("You can't withdraw negative money.");
                                Main.sleep(1500);
                            } else if (amount == 0) {
                                break;
                            } else {
                                if (loggedAccount.getBalance() - amount < 0) {
                                    Logger.print("You dont have enough money to withdraw " + amount + "CZK.");
                                    Main.sleep(1500);
                                } else {
                                    loggedAccount.removeMoney(amount);
                                    Main.addPurse(amount);
                                    Logger.print("You have withdrawn " + amount + "CZK from your account.");
                                    Main.sleep(2000);
                                    break;
                                }
                            }
                        }
                    }
                    case 2 -> {
                        while (true) {
                            Logger.clear();

                            Logger.printDivider();
                            Logger.print("ATM - Deposit");
                            Logger.print();
                            Logger.print("Username: " + loggedAccount.username);
                            Logger.print("Balance: " + loggedAccount.getBalance() + "CZK");
                            Logger.printDivider();
                            Logger.print();

                            Logger.print("How much do you want to deposit (0 to go back)? ", true);
                            int amount;
                            String amountInput = new Scanner(System.in).nextLine();
                            Logger.print();
                            try {
                                amount = Integer.parseInt(amountInput);
                            } catch (Exception exception) {
                                Logger.print("Invalid amount, try again.");
                                Main.sleep(1500);
                                continue;
                            }

                            if (amount < 0) {
                                Logger.print("You can't deposit negative money.");
                                Main.sleep(1500);
                            } else if (amount == 0) {
                                break;
                            } else {
                                if (Main.getPurse() - amount < 0) {
                                    Logger.print("You dont have enough purse to deposit " + amount + "CZK.");
                                    Main.sleep(1500);
                                } else {
                                    loggedAccount.addMoney(amount);
                                    Main.removePurse(amount);
                                    Logger.print("You have deposited " + amount + "CZK to your account.");
                                    Main.sleep(2000);
                                    break;
                                }
                            }
                        }
                    }
                    case 3 -> {
                        Logger.clear();

                        Logger.printDivider();
                        Logger.print("ATM - Send");
                        Logger.print();
                        Logger.print("Username: " + loggedAccount.username);
                        Logger.print("Balance: " + loggedAccount.getBalance() + "CZK");
                        Logger.printDivider();
                        Logger.print();

                        boolean goBack = false;
                        Account receiverAccount = null;
                        do {
                            Logger.print("Who do you want to send money to (leave blank to go back)? ", true);
                            String usernameInput = new Scanner(System.in).nextLine();
                            if (usernameInput.equals("")) {
                                goBack = true;
                                break;
                            } else if (usernameInput.length() < 3) {
                                Logger.print("Usernames can't be shorter than 3 characters, try again.");
                            } else if (usernameInput.length() > 16) {
                                Logger.print("Usernames can't be longer than 16 characters, try again.");
                            } else if (usernameInput.matches("[0-9]")) {
                                Logger.print("Usernames can't contain numbers, try again");
                            } else if (usernameInput.matches("[~!@#$%^&*()_+{}\\[\\]:;,.<>/?\n]")) {
                                Logger.print("Usernames can't contain special characters, try again.");
                            } else {
                                for (Account account : accounts) {
                                    if (account.username.equalsIgnoreCase(usernameInput)) {
                                        receiverAccount = account;
                                    }
                                }
                            }

                            Logger.print();
                        } while (receiverAccount == null);

                        if (goBack) {
                            break;
                        }

                        while (true) {
                            Logger.print("How much do you want to send to " + receiverAccount.username + " (0 to go back)? ", true);
                            int amount;
                            String amountInput = new Scanner(System.in).nextLine();
                            Logger.print();
                            try {
                                amount = Integer.parseInt(amountInput);
                            } catch (Exception exception) {
                                Logger.print("Invalid amount, try again.");
                                Main.sleep(1500);
                                continue;
                            }

                            if (amount < 0) {
                                Logger.print("You can't withdraw negative money.");
                                Main.sleep(1500);
                            } else if (amount == 0) {
                                break;
                            } else {
                                if (loggedAccount.getBalance() - amount < 0) {
                                    Logger.print("You dont have enough money to send " + amount + "CZK to " + receiverAccount.username + ".");
                                    Main.sleep(1500);
                                } else {
                                    loggedAccount.removeMoney(amount);
                                    receiverAccount.addMoney(amount);
                                    Logger.print("You have sent " + amount + "CZK to " + receiverAccount.username + ".");
                                    Main.sleep(2000);
                                    break;
                                }
                            }
                        }
                    }
                    case 4 -> {
                        Logger.clear();

                        Logger.printDivider();
                        Logger.print("ATM - Log out");
                        Logger.print();
                        Logger.print("Are you sure you want to log out?");
                        Logger.print();
                        Logger.print("1) Yes");
                        Logger.print("2) No");
                        Logger.printDivider();
                        Logger.print();

                        int confirm;
                        String confirmInput = new Scanner(System.in).nextLine();
                        Logger.print();
                        try {
                            confirm = Integer.parseInt(confirmInput);
                        } catch (Exception exception) {
                            confirm = -1;
                        }

                        if (confirm == 1) {
                            Logger.print("Logging out...");

                            loggedAccount = null;

                            Logger.print("Successfully logged out.");

                            Main.sleep(1500);
                        }
                    }
                    default -> {
                        Logger.print("Invalid input! Try again.");

                        Main.sleep(2000);
                    }
                }
            }
        } while (!leaving);
    }

    public void addShowcaseAccounts() {
        Account vojtaAccount = new Account("Vojta", "NovaciJsouDva123");
        vojtaAccount.addMoney(666000);
        accounts.add(vojtaAccount);

        Account marioAccount = new Account("Mario", "ItsMeMario123");
        marioAccount.addMoney(37000);
        accounts.add(marioAccount);

        Account jamalAccount = new Account("JAMAL", "Jamal123");
        jamalAccount.addMoney(45000);
        accounts.add(jamalAccount);

        Account adamAccount = new Account("Adam", "adamPog123");
        adamAccount.addMoney(53000);
        accounts.add(adamAccount);
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
