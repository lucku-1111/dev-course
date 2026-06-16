package 회원관리_프로그램_최종;

public class NormalMember implements Member {

    private String name, email, phone;

    public NormalMember(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public String getGrade() {
        return "일반";
    }

    @Override
    public String getBenefit() {
        return "기본 서비스";
    }

    @Override
    public void update(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}
