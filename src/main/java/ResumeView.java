import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ResumeView {
    Scanner scanner ;
    public ResumeView() {
        scanner = new Scanner(System.in);
    }

    public PersonInfo inputPersonInfo(){
        System.out.print("사진 파일명을 입력하세요:");
        String photo = scanner.nextLine();
        System.out.print("이름을 입력하세요:");
        String name = scanner.nextLine();
        System.out.print("이메일을 입력하세요:");
        String email = scanner.nextLine();
        System.out.print("주소를 입력하세요:");
        String address = scanner.nextLine();
        System.out.print("전화번호를 입력하세요:");
        String phoneNumber = scanner.nextLine();
        System.out.print("생년월일을 입력하세요 (예: 1990-01-01):");
        String birthDate = scanner.nextLine();

        return new PersonInfo(photo, name, email, address, phoneNumber, birthDate);
    }

    public List<Education> inputEducationList(){
        List<Education> educationList = new ArrayList<>();

        while (true) {
            System.out.println("학력 정보를 입력하세요 (종료는 q):");
            System.out.println("졸업년도 학교명 전공 졸업여부");
            String education = scanner.nextLine();

            if ("q".equals(education)) {
                break;
            }
            String[] eduSplit = education.split(" ");
            educationList.add(new Education(eduSplit[0], eduSplit[1], eduSplit[2], eduSplit[3]));
        }

        return educationList;

    }

    public List<Career> inputCareerList(){
        List<Career> careerList = new ArrayList<>();

        while (true) {
            System.out.println("경력 정보를 입력하세요 (종료는 q):");
            System.out.println("근무기간 근무처 담당업무 근속연수");
            String career = scanner.nextLine();

            if ("q".equals(career)) {
                break;
            }

            String[] careerSplit = career.split(" ");
            careerList.add(new Career(careerSplit[0], careerSplit[1], careerSplit[2], careerSplit[3]));
        }

        return careerList;

    }

    public String inputSelfIntroduction(){
        System.out.println("자기소개서를 입력하세요. 여러 줄을 입력하려면 빈 줄을 입력하세요.");
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).isEmpty()){
            stringBuilder.append(line).append("\n");
        }
        return stringBuilder.toString().trim();

    }

}
