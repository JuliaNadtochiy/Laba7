package com.julia;

public class Customer {

    private String name;
    private String surname;
    private String email;
    private CustomerType customerType;
    private Account account;
    private double companyOverdraftDiscount = 1;

    public Customer(String name, String surname, String email, CustomerType customerType, Account account) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.customerType = customerType;
        this.account = account;
    }

    // use only to create companies
    public Customer(String name, String email, Account account, double companyOverdraftDiscount) {
        this.name = name;
        this.email = email;
        this.customerType = CustomerType.COMPANY;
        this.account = account;
        this.companyOverdraftDiscount = companyOverdraftDiscount;
    }

    public void withdraw(double sum, String currency) {
        if (!account.getCurrency().equals(currency)) {
            throw new RuntimeException("Can't extract withdraw " + currency);
        }
        if (account.getType().isPremium()) {
            switch (customerType) {
                case COMPANY:
                    // we are in overdraft
                    extractedCompany(sum, 2);
                    break;
                case PERSON:
                    // we are in overdraft
                    extractPerson(sum);
                    break;
            }
        } else {
            switch (customerType) {
                case COMPANY:
                    // we are in overdraft
                    extractedCompany(sum, 1);
                    break;
                case PERSON:
                    // we are in overdraft
                    extractPerson(sum);
                    break;
            }
        }
    }

    private void extractPerson(double sum) {
        extractedCompany(sum, 1);

    }

    private void extractedCompany(double sum, int kof) {
        if (account.getMoney() < 0) {
            // 50 percent discount for overdraft for premium account
            account.setMoney((account.getMoney() - sum) - sum * account.overdraftFee() * companyOverdraftDiscount / kof);
        } else {
            account.setMoney(account.getMoney() - sum);
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public String printCustomerDaysOverdrawn() {
        String fullName = fullName();

        String accountDescription = "Account: IBAN: " + account.getIban() + ", Days Overdrawn: " + account.getDaysOverdrawn();
        return fullName + accountDescription;
    }

    private String fullName() {
        return name + " " + surname + " ";
    }

    public String printCustomerMoney() {
        String fullName = fullName();
        String accountDescription = "";
        accountDescription += "Account: IBAN: " + account.getIban() + ", Money: " + account.getMoney();
        return fullName + accountDescription;
    }

    public String printCustomerAccount() {
        return "Account: IBAN: " + account.getIban() + ", Money: "
                + account.getMoney() + ", Account type: " + account.getType();
    }
}
