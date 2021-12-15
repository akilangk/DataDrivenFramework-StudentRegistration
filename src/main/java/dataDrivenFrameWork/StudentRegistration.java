package dataDrivenFrameWork;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class StudentRegistration extends DataProvider {
    WebDriver driver;

    List<String> firstName = new ArrayList<>();
    List<String> lastName = new ArrayList<>();
    List<String> email = new ArrayList<>();
    List<String> address = new ArrayList<>();
    List<String> mobile = new ArrayList<>();
    List<String> gender = new ArrayList<>();

    public void getExcelData() {
        List<String> headerNames;
        try {
            File file = new File(getXlsxFilePath());
            FileInputStream fileInputStream = new FileInputStream(file);
            XSSFWorkbook workBook = new XSSFWorkbook(fileInputStream);
            int numberOfSheets = workBook.getNumberOfSheets();
            for (int sheetIndex = 0; sheetIndex < numberOfSheets; sheetIndex++) {
                XSSFSheet sheet = workBook.getSheetAt(sheetIndex);
                int numberOfRows = sheet.getLastRowNum();
                if ((numberOfRows > 0) || (sheet.getPhysicalNumberOfRows() > 0)) {
                    numberOfRows++;
                }
                Row headerRow = sheet.getRow(0);
                headerNames = new ArrayList<>();
                int numberOfColumns = headerRow.getLastCellNum();
                DataFormatter format = new DataFormatter();
                for (int columnIndex = 0; columnIndex < numberOfColumns; columnIndex++) {
                    headerNames.add(format.formatCellValue(headerRow.getCell(columnIndex)));
                }
                for (int columnIndex = 0; columnIndex < numberOfColumns; columnIndex++) {
                    String header = headerNames.get(columnIndex);
                    if (header.equals(getColumnOneHeader())) {
                        for (int rowIndex = 1; rowIndex < numberOfRows; rowIndex++) {
                            Row rowData = sheet.getRow(rowIndex);
                            firstName.add(format.formatCellValue(rowData.getCell(columnIndex)));
                        }
                    } else if (header.equals(getColumnTwoHeader())) {
                        for (int rowIndex = 1; rowIndex < numberOfRows; rowIndex++) {
                            Row rowData = sheet.getRow(rowIndex);
                            lastName.add(format.formatCellValue(rowData.getCell(columnIndex)));
                        }
                    } else if (header.equals(getColumnThreeHeader())) {
                        for (int rowIndex = 1; rowIndex < numberOfRows; rowIndex++) {
                            Row rowData = sheet.getRow(rowIndex);
                            email.add(format.formatCellValue(rowData.getCell(columnIndex)));
                        }
                    } else if (header.equals(getColumnFourHeader())) {
                        for (int rowIndex = 1; rowIndex < numberOfRows; rowIndex++) {
                            Row rowData = sheet.getRow(rowIndex);
                            gender.add(format.formatCellValue(rowData.getCell(columnIndex)));
                        }
                    } else if (header.equals(getColumnFiveHeader())) {
                        for (int rowIndex = 1; rowIndex < numberOfRows; rowIndex++) {

                            Row rowData = sheet.getRow(rowIndex);
                            mobile.add(format.formatCellValue(rowData.getCell(columnIndex)));
                        }
                    } else if (header.equals(getColumnSixHeader())) {
                        for (int rowIndex = 1; rowIndex < numberOfRows; rowIndex++) {
                            Row rowData = sheet.getRow(rowIndex);
                            address.add(format.formatCellValue(rowData.getCell(columnIndex)));
                        }
                    }
                }
            }
        } catch (FileNotFoundException exception) {
            System.out.println("Oops, Check if the file is present in the given location..");
        } catch (IOException exception) {
            System.out.println("Check the file");
        } catch (NullPointerException exception) {
            System.out.println("Sheet has null values");
        }
    }

    public int numberOfStudentDataInFile() {
        return firstName.size();
    }

    public void selectGender(String genderOption, WebDriverWait wait) {
        PageFactory.initElements(driver, StudentRegistrationFormPageObjects.class);
        switch (genderOption) {
            case "Male":
                wait.until(ExpectedConditions.elementToBeClickable
                        (StudentRegistrationFormPageObjects.male(driver))).click();
                break;
            case "Female":
                wait.until(ExpectedConditions.elementToBeClickable
                        (StudentRegistrationFormPageObjects.female(driver))).click();
                break;
            case "Other":
                wait.until(ExpectedConditions.elementToBeClickable
                        (StudentRegistrationFormPageObjects.other(driver))).click();
                break;
            default:
                break;
        }
    }

    public void openBrowser() {
        driver = initiateDriver();
        driver.manage().window().maximize();
    }

    public void registerStudent() {
        for (int dataIndex = 0; dataIndex < numberOfStudentDataInFile(); dataIndex++) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
                driver.get(getUrl());
                PageFactory.initElements(driver, StudentRegistrationFormPageObjects.class);
                StudentRegistrationFormPageObjects.firstName.sendKeys(firstName.get(dataIndex));
                StudentRegistrationFormPageObjects.lastName.sendKeys(lastName.get(dataIndex));
                StudentRegistrationFormPageObjects.eMail.sendKeys(email.get(dataIndex));
                selectGender(gender.get(dataIndex), wait);
                StudentRegistrationFormPageObjects.mobileNumber.sendKeys(mobile.get(dataIndex));
                StudentRegistrationFormPageObjects.currentAddress.sendKeys(address.get(dataIndex));
                JavascriptExecutor execute = (JavascriptExecutor) driver;
                execute.executeScript("arguments[0].click()", StudentRegistrationFormPageObjects.submit);
                Boolean result = wait.until(ExpectedConditions.textToBePresentInElement
                        (StudentRegistrationFormPageObjects.confirmationText, "Thanks for submitting the form"));
                if (result) {
                    System.out.println("Registration successful for student named " +
                            firstName.get(dataIndex) + " " + lastName.get(dataIndex));
                } else {
                    System.out.println("Registration failed for student named " +
                            firstName.get(dataIndex) + " " + lastName.get(dataIndex));
                }
            } catch (TimeoutException exception) {
                System.out.println("Registration failed for student named " +
                        firstName.get(dataIndex) + " " + lastName.get(dataIndex));
            }
        }
        driver.quit();
    }
}
