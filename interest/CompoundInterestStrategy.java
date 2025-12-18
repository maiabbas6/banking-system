package interest;

public class CompoundInterestStrategy implements InterestStrategy {

    @Override
    public double calculate(double balance) {
        return balance * Math.pow(1.05, 2) - balance;
    }
}
