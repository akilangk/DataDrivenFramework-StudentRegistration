package dataDrivenFrameWork;

public class Runner {
    public static void main(String[] args) {
        StudentRegistration run = new StudentRegistration();
        run.getExcelData();
        run.openBrowser();
        run.registerStudent();
    }
}
