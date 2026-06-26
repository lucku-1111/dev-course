package 전략패턴_리팩토링;

public class AddStrategy implements StatementStrategy {
    private final User user;

    public AddStrategy(User user) {
        this.user = user;
    }

    @Override
    public void run(Database db) {
        db.getUsers().add(user);
    }
}
