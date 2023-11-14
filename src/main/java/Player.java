public class Player {

    private int coins;

    public int getCoins() {
        return coins;
    }

    public void deposit(int coinsToDeposit) {
        coins += coinsToDeposit;
    }

    public void withdraw(int coinsToWithdraw) {
        if (coins - coinsToWithdraw < 0) {
            throw new RuntimeException();
        }
        coins -= coinsToWithdraw;
    }
}
