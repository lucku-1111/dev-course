package 전략패턴_리팩토링;

public class UserDao {
    private Database db;

    public UserDao(Database db) {
        this.db = db;
    }

    public void context(StatementStrategy strategy) {
        db.open();
        strategy.run(db);
        db.close();
    }

//    * 람다
    public void deleteAllLambda() {
        context(db -> {
            db.getUsers().clear();
            System.out.println("[전략-람다] 전체 삭제");
        });
    }

    public void addLambda(User user) {
        context(db -> {
            db.getUsers().add(user);
            System.out.println("[전략-람다] 전체 추가: " + user.getName());
        });
    }

//    * 익명 클래스
    public void deleteAllAnonymous() {
        StatementStrategy strategy = new StatementStrategy() {
            @Override
            public void run(Database db) {
                db.getUsers().clear();
                System.out.println("[전략-익명 전체 삭제]");
            }
        };
        context(strategy);
    }

    public void addAnonymous(User user) {
        StatementStrategy strategy = new StatementStrategy() {
            @Override
            public void run(Database db) {
                db.getUsers().add(user);
                System.out.println("[전략-익명] 추가: " + user.getName());
            }
        };
        context(strategy);
    }

//    * 별도 클래스
    public void deleteAll() {
        context(new DeleteAllStrategy());
    }

    public void add(User user) {
        context(new AddStrategy(user));
    }
}
