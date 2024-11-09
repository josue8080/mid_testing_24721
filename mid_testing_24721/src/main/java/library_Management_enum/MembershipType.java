package library_Management_enum;

public enum MembershipType {
    GOLD(5, 50),
    SILVER(3, 30),
    STRIVER(2, 10);

    private final int maxBooks;
    private final int dailyFee;

    MembershipType(int maxBooks, int dailyFee) {
        this.maxBooks = maxBooks;
        this.dailyFee = dailyFee;
    }

    public int getMaxBooks() {
        return maxBooks;
    }

    public int getDailyFee() {
        return dailyFee;
    }
}