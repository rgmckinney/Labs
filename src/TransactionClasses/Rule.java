package TransactionClasses;

public class Rule {
    private ItemSet left, right;

    @Override
    public boolean equals(Object other) {
        Rule otherRule = (Rule)other;
        if (!left.equals(otherRule.getLeft())) {
            return false;
        }

        if (!right.equals(otherRule.getRight())) {
            return false;
        }
        return true;
    }

    public ItemSet getLeft() {
        return this.left;
    }

    public ItemSet getRight() {
        return this.right;
    }
}
