package interest;

public class SimpleInterestStrategy implements InterestStrategy {

    @Override
    public double calculate(double balance) {
        return balance * 0.05; // 5% simple interest
    }
}
