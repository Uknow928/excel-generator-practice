import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;

public class ResumeController {
    private XSSFWorkbook workbook;
    private ResumeView resumeView;

    ResumeController(){
        workbook = new XSSFWorkbook();
        resumeView = new ResumeView();
    }

    public static void main(String[] args) throws IOException {
        ResumeController resumeController = new ResumeController();
        resumeController.createResume();
    }

    public void createResume() throws IOException {
        PersonInfo personInfo = resumeView.inputPersonInfo();
        List<Education> educationList = resumeView.inputEducationList();
        List<Career> careerList = resumeView.inputCareerList();
        createResumeSheet(personInfo, educationList, careerList);
        createSelfIntroductionSheet(resumeView.inputSelfIntroduction());
        saveWorkbookToFile();
        System.out.println("이력서 생성이 완료되었습니다.");
    }

    public void createResumeSheet(PersonInfo personInfo, List<Education> educationList, List<Career> careerList) throws IOException {
        Sheet sheet = workbook.createSheet("이력서");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("사진");
        headerRow.createCell(1).setCellValue("이름");
        headerRow.createCell(2).setCellValue("이메일");
        headerRow.createCell(3).setCellValue("주소");
        headerRow.createCell(4).setCellValue("전화번호");
        headerRow.createCell(5).setCellValue("생년월일");

        Row row = sheet.createRow( 1);
//        Todo 사진 파일 불러오기 기능
        row.createCell(0).setCellValue(personInfo.getPhoto());

        row.createCell(1).setCellValue(personInfo.getName());
        row.createCell(2).setCellValue(personInfo.getEmail());
        row.createCell(3).setCellValue(personInfo.getAddress());
        row.createCell(4).setCellValue(personInfo.getPhoneNumber());
        row.createCell(5).setCellValue(personInfo.getBirthDate());

        Row eduHeaderRow = sheet.createRow(2);
        eduHeaderRow.createCell(0).setCellValue("졸업년도");
        eduHeaderRow.createCell(1).setCellValue("학교명");
        eduHeaderRow.createCell(2).setCellValue("전공");
        eduHeaderRow.createCell(3).setCellValue("졸업여부");
        int rowNum = 2;
        for (Education education : educationList) {
            Row eduRow = sheet.createRow(++rowNum);
            eduRow.createCell(0).setCellValue(education.getGraduationYear());
            eduRow.createCell(1).setCellValue(education.getSchoolName());
            eduRow.createCell(2).setCellValue(education.getMajor());
            eduRow.createCell(3).setCellValue(education.getGraduationStatus());
        }

        Row careerHeaderRow = sheet.createRow(++rowNum);
        careerHeaderRow.createCell(0).setCellValue("근무기간");
        careerHeaderRow.createCell(1).setCellValue("근무처");
        careerHeaderRow.createCell(2).setCellValue("담당업무");
        careerHeaderRow.createCell(3).setCellValue("근속연수");
        for (Career career : careerList) {
            Row careerRow = sheet.createRow(++rowNum);
            careerRow.createCell(0).setCellValue(career.getWorkPeriod());
            careerRow.createCell(1).setCellValue(career.getCompanyName());
            careerRow.createCell(2).setCellValue(career.getJobTitle());
            careerRow.createCell(3).setCellValue(career.getEmploymentYears());
        }
    }


    public void createSelfIntroductionSheet(String selfIntroduction){
        Sheet sheet = workbook.createSheet("자기소개서");
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue(selfIntroduction);
    }

    public CellStyle getWrapCellStyle(){
        //Todo
        return null;
    }

    public void saveWorkbookToFile() {
        String filename = "이력서.xlsx";
        try {
            FileOutputStream outputStream = new FileOutputStream(new File(filename));
            workbook.write(outputStream);
            workbook.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
