package com.julia;

public class Account {

    private String iban;

    private AccountType type;

    private int daysOverdrawn;

    Money money;
    Customer customer;

    public Account(AccountType type, int daysOverdrawn) {
        super();
        this.type = type;
        this.daysOverdrawn = daysOverdrawn;
        money = new Money();
    }

    public double bankcharge() {
        double result = 4.5;

        result += overdraftCharge();

        return result;
    }

    private double overdraftCharge() {
        if (type.isPremium()) {
            double result = 10;
            if (getDaysOverdrawn() > 7)
                result += (getDaysOverdrawn() - 7) * 1.0;
            return result;
        } else
            return getDaysOverdrawn() * 1.75;
    }

    public double overdraftFee() {
        if (type.isPremium()) {
            return 0.10;
        } else {
            return 0.20;
        }
    }


    public int getDaysOverdrawn() {
        return daysOverdrawn;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public AccountType getType() {
        return type;
    }

    public String printCustomer() {
        return customer.getName() + " " + customer.getEmail();
    }

    public String getCurrency() {
        return money.getCurrency();
    }

    public void setCurrency(String currency) {
        this.money.currency = currency;
    }

    public String printCustomerAccount(Customer customer) {
        return "Account: IBAN: " + getIban() + ", Money: "
                + money.getMoney() + ", Account type: " + getType();
    }

    public void withdraw(double sum, String currency) {
        if (!money.getCurrency().equals(currency)) {
            throw new RuntimeException("Can't extract withdraw " + currency);
        }
        if (getType().isPremium()) {
            calculate(sum, 2);
        } else {
            calculate(sum, 1);
        }
    }
    private void calculate(double sum, int i) {
        switch (customer.getCustomerType()) {
            case COMPANY:
                extractedCompany(sum, i);
                break;
            case PERSON:
                extractPerson(sum);
                break;
        }
    }
    private void extractPerson(double sum) {
        extractedCompany(sum, 1);

    }

    private void extractedCompany(double sum, int kof) {
        if (money.getMoney() < 0) {
            // 50 percent discount for overdraft for premium account
            money.setMoney((money.getMoney() - sum) - sum * overdraftFee() * customer.getType().getOverdraftDiscount() / kof);
        } else {
            money.setMoney(money.getMoney() - sum);
        }
    }
    public String printCustomerDaysOverdrawn(Customer customer) {
        String fullName = customer.fullName();

        String accountDescription = "Account: IBAN: " + getIban() + ", Days Overdrawn: " + getDaysOverdrawn();
        return fullName + accountDescription;
    }

    public double getMoney() {
        return money.getMoney();
    }

    public static class AccountType {
        private boolean premium;

        AccountType(boolean premium) {
            this.premium = premium;
        }

        public boolean isPremium() {
            return premium;
        }

        @Override
        public String toString() {
            return premium ? "premium" : "normal";
        }
    }
}
