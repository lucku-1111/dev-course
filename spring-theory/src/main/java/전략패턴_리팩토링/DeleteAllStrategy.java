package 전략패턴_리팩토링;

public class DeleteAllStrategy implements StatementStrategy {
    @Override
    public void run(Database db) {
        db.getUsers().clear();
        System.out.println("[전략-별도클래스] 전체 삭제");
    }
}
