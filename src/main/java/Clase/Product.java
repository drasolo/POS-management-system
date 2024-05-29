package Clase;

public class Product {

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//to-dos
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    //de implementat un geter de tara de la codul de bare
    //primele 2-3 cifre reprezinta tara
    //de implementat categorii dinamic in baza de date care pot fi modificate doar de admin
    //si basically sa fie o interfata la admin ca la client in care poate modifica baza de date(produse pe stoc/categorii/Priceuri/Discount)
    //MinorRestricted ---> metoda care verifica daca un minor poate cumpara ceva dintr-o categorie de produse


//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//variables
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    protected String Name;
    protected Integer Stock;
    protected String barcode; // basically an id
    protected String CategoryId;
    protected Double Price;
    protected int Discount;

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//validators
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public static boolean validateEAN13Barcode(String barcode) {
        if (barcode == null || !barcode.matches("\\d{13}")) {
            return false; // Barcode should be exactly 13 digits long
        }

        int sum = 0;
        // Calculate the checksum
        for (int i = 0; i < 12; i++) {
            int digit = Character.digit(barcode.charAt(i), 10);
            // Odd digits have an index of even number since we start from 0
            sum += (i % 2 == 0) ? digit : digit * 3;
        }

        // Checksum digit is the number that must be added to 'sum' to make it divisible by 10
        int checksumDigit = 10 - (sum % 10);
        // If the result is 10, set it to 0
        checksumDigit = (checksumDigit == 10) ? 0 : checksumDigit;

        // The last digit of the barcode is the checksum digit
        return checksumDigit == Character.digit(barcode.charAt(12), 10);
    }

    public boolean validateDiscount() {
        return Discount >= 0 && Discount <= 100;
    }

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//getters and setters
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Getter and Setter for Name
    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    // Getter and Setter for Stock
    public Integer getStock() {
        return Stock;
    }

    public void setStock(Integer stock) {
        this.Stock = stock;
    }

    // Getter and Setter for barcode
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    // Getter and Setter for CategoryId
    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        this.CategoryId = categoryId;
    }

    // Getter and Setter for Price
    public Double getPrice() {
        return Price;
    }

    public String getCountryCode() {
        if (barcode != null && barcode.length() >= 2) {
            return barcode.substring(0, 3);
        } else if (barcode != null && barcode.length() >= 3) {
            return barcode.substring(0, 2);
        }
        return "";
    }

    public void setPrice(Double price) {
        this.Price = price;
    }

    // Getter and Setter for Discount
    public int getDiscount() {
        return Discount;
    }

    public void setDiscount(int discount) {
        this.Discount = discount;
    }

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//constructors
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    public Product(String barcode, String name, String categoryId, double price, int stock, int discount) {
        this.barcode = barcode;
        this.Name = name;
        this.CategoryId = categoryId;
        this.Price = price;
        this.Stock = stock;
        this.Discount = discount;

        if (!validateEAN13Barcode(barcode)) {
            throw new IllegalArgumentException("Invalid barcode format.");
        }
        if (!validateDiscount()) {
            throw new IllegalArgumentException("Invalid discount value. Discount must be between 0 and 100.");
        }
    }

    public Product(String Name, Integer stock, String barcode, String categoryId, Double Price, int discount) {
        this.Name = Name;
        this.Stock = stock;
        this.barcode = barcode;
        this.CategoryId = categoryId;
        this.Price = Price;
        this.Discount = discount;

        if (!validateEAN13Barcode(barcode)) {
            throw new IllegalArgumentException("Invalid barcode format.");
        }
        if (!validateDiscount()) {
            throw new IllegalArgumentException("Invalid discount value. Discount must be between 0 and 100.");
        }
    }

    public Product(String barcode, String categoryId, String name, double price, int stock) {
        this.barcode = barcode;
        this.CategoryId = categoryId;
        this.Name = name;
        this.Price = price;
        this.Stock = stock;
        this.Discount = 0;
    }

    public Product(String productCode, String CategoryId, double Price) {
        this.barcode = productCode;
        this.CategoryId = CategoryId;
        this.Price = Price;
    }
}
