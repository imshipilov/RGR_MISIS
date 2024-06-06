class GaussianMembershipFunction {
    private double lambda1;
    private double lambda2;

    public GaussianMembershipFunction(double lambda1, double lambda2) {
        this.lambda1 = lambda1;
        this.lambda2 = lambda2;
    }

    public double calculateMembership(double x) {
        if (x < lambda1 || x > lambda2) {
            return 0.0;
        }

        double numerator = 4 * (lambda2 - x) * (x - lambda1) - Math.pow(lambda2 - lambda1, 2);
        double denominator = 4 * (lambda2 - x) * (x - lambda1);
        double exponent = numerator / denominator;
        double membershipValue = Math.exp(exponent);

        // Значение функции принадлежности
        return membershipValue;
    }
}