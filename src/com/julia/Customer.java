package com.julia;

public class Customer extends Person {

    private String email;
    private Type type;
    Account account;


    public class Type {
        private CustomerType customerType;
        private double overdraftDiscount;
        private double dividerPremium;

        public Type(CustomerType type, double overdraftDiscount, double dividerPremium) {
            customerType = type;
            this.overdraftDiscount = overdraftDiscount;
            this.dividerPremium = dividerPremium;
        }
        public double getOverdraftDiscount() {
            return overdraftDiscount;
        }

        public double getDividerPremium() {
            return dividerPremium;
        }

        public CustomerType getCustomerType() {
            return customerType;
        }
    }
    public Customer(String name, String surname, String email, CustomerType customerType, Account account) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.type = new Type(customerType, 1, 1);
        this.account = account;
    }

    // use only to create companies
    public Customer(String name, String email, Account account, double companyOverdraftDiscount) {
        this.name = name;
        this.email = email;
        this.type = new Type(CustomerType.COMPANY, companyOverdraftDiscount, 2);
        this.account = account;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CustomerType getCustomerType() {
        return type.customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.type.customerType = customerType;
    }

    public Type getType() {
        return type;
    }

    public String printCustomerDaysOverdrawn() {
        String fullName = fullName();

        String accountDescription = "Account: IBAN: " + account.getIban() + ", Days Overdrawn: " + account.getDaysOverdrawn();
        return fullName + accountDescription;
    }

    public String printCustomerMoney() {
        String fullName = fullName();
        String accountDescription = "";
        accountDescription += "Account: IBAN: " + account.getIban() + ", Money: " + account.getMoney();
        return fullName + accountDescription;
    }

}
