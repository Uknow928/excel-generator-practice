package controller;

import model.Career;
import model.Education;
import model.PersonInfo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import view.ResumeView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
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
        createPersonInfoResume(sheet, personInfo);
        createEducationResume(sheet, educationList);
        createCareerResume(sheet, careerList);

    }

    private void createCareerResume(Sheet sheet, List<Career> careerList) {
        int rowNum = sheet.getLastRowNum();
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

    private void createEducationResume(Sheet sheet, List<Education> educationList) {
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
    }

    private void createPersonInfoResume(Sheet sheet, PersonInfo personInfo) {

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("사진");
        headerRow.createCell(1).setCellValue("이름");
        headerRow.createCell(2).setCellValue("이메일");
        headerRow.createCell(3).setCellValue("주소");
        headerRow.createCell(4).setCellValue("전화번호");
        headerRow.createCell(5).setCellValue("생년월일");

        Row row = sheet.createRow( 1);

        String photoFileName = personInfo.getPhoto();
        addPicture(photoFileName, row, sheet);

        row.createCell(1).setCellValue(personInfo.getName());
        row.createCell(2).setCellValue(personInfo.getEmail());
        row.createCell(3).setCellValue(personInfo.getAddress());
        row.createCell(4).setCellValue(personInfo.getPhoneNumber());
        row.createCell(5).setCellValue(personInfo.getBirthDate());
    }

    private void addPicture(String photoFileName, Row row, Sheet sheet) {

        try (FileInputStream photoStream = new FileInputStream(photoFileName)){
            BufferedImage originalImage = ImageIO.read(photoStream);

            int newWidth = (int) (35 * 2.83465);
            int newHeight = (int) (45 * 2.83465);
            Image resizedImage = originalImage.getScaledInstance(newWidth,newHeight, Image.SCALE_SMOOTH);
            BufferedImage resizedBufferedImage = new BufferedImage(newWidth , newHeight , BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2d = resizedBufferedImage.createGraphics();
            g2d.drawImage(resizedImage , 0, 0, null);
            g2d.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedBufferedImage,"png", baos);
            byte[] imageBytes = baos.toByteArray();
            int imageIndex = workbook.addPicture(imageBytes, workbook.PICTURE_TYPE_PNG);

            XSSFDrawing drawing = (XSSFDrawing)sheet.createDrawingPatriarch();
            XSSFClientAnchor anchor = new XSSFClientAnchor(0,0,0,0,0,1,1,2);
            drawing.createPicture(anchor, imageIndex);

            row.setHeightInPoints(newHeight*72/96);

            int columnWidth = (int) Math.floor(((float) newWidth / (float) 8) * 256);
            sheet.setColumnWidth(0,columnWidth);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void createSelfIntroductionSheet(String selfIntroduction){
        Sheet sheet = workbook.createSheet("자기소개서");
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellStyle(getWrapCellStyle());
        cell.setCellValue(selfIntroduction);
        sheet.autoSizeColumn(0);
    }

    public CellStyle getWrapCellStyle(){
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setWrapText(true);
        return cellStyle;
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
