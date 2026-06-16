package 회원관리_프로그램_최종;

public interface Member {

    String getName();
    String getEmail();
    String getPhone();
    String getGrade();
    String getBenefit();
    void update(String name, String email, String phone);

    default void printInfo() {
        System.out.println("[" + getGrade() + "] " + getName() + " / "
                + getEmail() + " / " + getPhone() + " (혜택: " + getBenefit() + ")");
    }
}
