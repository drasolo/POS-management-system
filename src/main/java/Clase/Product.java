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

    protected String NumeProdus;

    protected String Stock;

    protected String barcode; //basically an id

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
//some valid codes
//3381374195170
//3234454078736
//2467189881307
//5585183051025
//6916400080396
//0419450379961
//7633320050559
//9080719606312
//7574253197785
//6771222644436

    public boolean validateDiscount() {
        return Discount >= 0 && Discount <= 100;
    }

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//validators
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Getter for the barcode
    public String getBarcode() {
        return barcode;
    }

    // Getter for the CategoryId
    public String getCategoryId() {
        return CategoryId;
    }

    // Getter for the Price
    public Double getPrice() {
        return Price;
    }

    // Getter for the Discount
    public int getDiscount() {
        return Discount;
    }




    public Product(String numeProdus, String stock, String barcode, String categoryId, Double Price, int discount) {
        this.NumeProdus = numeProdus;
        this.Stock = stock;
        this.barcode = barcode;
        this.CategoryId = categoryId;
        this.Price = Price;
        this.Discount = discount;

        // Optional: Add validation checks here
        if (!validateEAN13Barcode(barcode)) {
            throw new IllegalArgumentException("Invalid barcode format.");
        }
        if (!validateDiscount()) {
            throw new IllegalArgumentException("Invalid discount value. Discount must be between 0 and 100.");
        }
    }

    public Product(String productCode, String CategoryId, double Price) {
        this.barcode = productCode;
        this.CategoryId = CategoryId;
        this.Price = Price;
    }

}
