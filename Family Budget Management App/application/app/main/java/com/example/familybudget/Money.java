package com.example.familybudget;

import java.math.BigDecimal;
import java.util.Currency;
public class Money {

    private BigDecimal amount;
    private Currency currency;

    /**
     * Βασικός κατασκευαστής της κλάσης.
     * Υπάρχουν και βοηθητικές στατικές μέθοδοι για τη δημιουργία αντικειμένων.
     * @param //amount Το ποσό
     * @param //currency Η νομισματική μονάδα
     */
    public Money(){
        this.amount= new BigDecimal(0);
        this.currency= Currency.getInstance("EUR");
    }
    public Money(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    /**
     * Επιστρέφει το ποσό.
     * @return Το ποσό
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Επιστρέφει τη νομισματική μονάδα.
     * @return Η νομισματική μονάδα
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * Επιστρέφει το άθροισμα με κάποιο άλλο ποσό. Η νομισματική μονάδα του.
     * ποσού που προστίθεται θα πρέπει να είναι ίδια με το αντικείμενο.
     * Το άθροισμα επιστρέφεται ως
     * νέο αντικείμενο. Η κατάσταση του αντικειμένου δεν αλλάζει.
     * @param other Το ποσό που προστίθεται
     * @return Το άθροισμα των δύο ποσών
     * @throws IllegalArgumentException Εάν το ποσό που προστίθεται
     * έχει διαφορετική νομισματική μονάδα
     */
    public Money plus(Money other) {
        checkForSameCurrencies(other);
        return new Money(amount.add(other.amount), currency);
    }


    /**
     * Επιστρέφει την αφαίρεση με κάποιο άλλο ποσό. Η νομισματική μονάδα του
     * ποσού που αφαιρείται θα πρέπει να είναι ίδια με το αντικείμενο.
     * Το αποτέλεσμα της αφαίρεσης
     * επιστρέφεται ως νέο αντικείμενο.
     * Η κατάσταση του αντικειμένου δεν αλλάζει.
     * @param other Το ποσό που αφαιρείται
     * @return Το αποτέλεσμα της αφαίρεσης των δύο ποσών
     * @throws IllegalArgumentException Εάν το ποσό που
     * αφαιρείται έχει διαφορετική νομισματική μονάδα
     */
    public Money minus(Money other) {
        checkForSameCurrencies(other);
        return new Money(amount.subtract(other.amount), currency);
    }

    /**
     * Πολλαπλασιάζει το ποσό με κάποιο παράγοντα.
     * Το ποσό επιστρέφεται ως νέο αντικείμενο.
     * @param factor Ο παράγοντας
     * @return Το αντικείμενο με το αποτέλεσμα του πολλαπλασιασμού
     */
    public Money times(BigDecimal factor) {
        return new Money(amount.multiply(factor), currency);
    }

    /**
     * Πολλαπλασιάζει το ποσό με κάποιο παράγοντα.
     * Το ποσό επιστρέφεται ως νέο αντικείμενο.
     * @param factor Ο παράγοντας
     * @return Το αντικείμενο με το αποτέλεσμα του πολλαπλασιασμού
     */
    public Money times(long factor) {
        return new Money(amount.multiply(BigDecimal.valueOf(factor)), currency);
    }


    /**
     * Διαιρεί το ποσό με κάποιο διαιρέτη. Το ποσό επιστρέφεται
     * ως νέο αντικείμενο.
     * @param divisor Ο διαιρέτης
     * @return Το αντικείμενο με το αποτέλεσμα της διαίρεσης
     */
    public Money divide(BigDecimal divisor) {
        return new Money(amount.divide(divisor), currency);
    }

    /**
     * Διαιρεί το ποσό με κάποιο διαιρέτη.
     * Το ποσό επιστρέφεται ως νέο αντικείμενο.
     * @param divisor Ο διαιρέτης
     * @return Το αντικείμενο με το αποτέλεσμα της διαίρεσης
     */
    public Money divide(long divisor) {
        return new Money(amount.divide(BigDecimal.valueOf(divisor)), currency);
    }

    /**
     * Ελέγχει αν υπάρχει διαφορετική νομισματική μονάδα.
     * @param other Το άλλο αντικείμενο προς έλεγχο
     */
    private void checkForSameCurrencies(Money other) {
        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException("Διαφορετικά Νομίσματα");
        }
    }

    /**
     * Επιστρέφει το χρηματικό ποσό με νομισματική μονάδα το ευρώ.
     * @param amount Το ποσό
     * @return Το αντικείμενο με το ποσό και νομισματική μονάδα ευρώ
     */
    public static Money euros(BigDecimal amount) {
        return new Money(amount, Currency.getInstance("EUR"));
    }

    /**
     * Επιστρέφει το χρηματικό ποσό με νομισματική μονάδα το ευρώ.
     * @param amount Το ποσό
     * @return Το αντικείμενο με το ποσό και νομισματική μονάδα ευρώ
     */
    public static Money euros(long amount) {
        return euros(BigDecimal.valueOf(amount));
    }

    /**
     * Επιστρέφει το χρηματικό ποσό με νομισματική μονάδα το ευρώ.
     * @param amount Το ποσό
     * @return Το αντικείμενο με το ποσό και νομισματική μονάδα δολάριο
     */
    public static Money dollars(BigDecimal amount) {
        return new Money(amount, Currency.getInstance("USD"));
    }


    /**
     * Επιστρέφει το χρηματικό ποσό με νομισματική μονάδα το ευρώ.
     * @param amount Το ποσό
     * @return Το αντικείμενο με το ποσό και νομισματική μονάδα δολάριο
     */
    public static Money dollars(long amount) {
        return dollars(BigDecimal.valueOf(amount));
    }

    /**
     * Επαληθεύει to instance ενός χρηματικού ποσού.
     * @param other Το άλλο αντικείμενο προς έλεγχο
     * @return Το χρηματικό ποσό
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (this == other) {
            return true;
        }

        if (!(other instanceof Money)) {
            return false;
        }

        Money theMoney = (Money) other;
        if (currency == null) {
            return theMoney.currency == null
                    && amount == null && theMoney.amount == null;
        }

        if (!currency.equals(theMoney.currency)) {
            return false;
        }

        return amount == null ? theMoney.amount == null
                : (amount.compareTo(theMoney.amount) == 0);
    }

    /**
     * Το HashCode του χρηματικού ποσού.
     * @return Το HashCode
     */
    @Override
    public int hashCode() {
        return amount == null ? 0 : amount.hashCode();
    }

    /**
     * Το χρηματικό ποσό με την μορφή string.
     * @return Το χρηματικό ποσό
     */
    @Override
    public String toString() {
        return amount.toString() + " " + currency.getSymbol();
    }
}
