package dataDrivenFrameWork;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class StudentRegistrationFormPageObjects {
    @FindBy(id = "firstName")
    public static WebElement firstName;
    @FindBy(id = "lastName")
    public static WebElement lastName;
    @FindBy(id = "userEmail")
    public static WebElement eMail;
    @FindBy(id = "userNumber")
    public static WebElement mobileNumber;
    @FindBy(id = "currentAddress")
    public static WebElement currentAddress;
    @FindBy(id = "submit")
    public static WebElement submit;
    @FindBy(xpath = "//div[text()='Thanks for submitting the form']")
    public static WebElement confirmationText;
    @FindBy(xpath = "//div[@id='genterWrapper']/div[2]/div[1]")
    public static WebElement male;
    @FindBy(xpath = "//div[@id='genterWrapper']/div[2]/div[2]")
    public static WebElement female;
    @FindBy(xpath = "//div[@id='genterWrapper']/div[2]/div[3]")
    public static WebElement other;
}
