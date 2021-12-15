package dataDrivenFrameWork;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class DataProvider {
    public WebDriver driver;
    String userWorkingDirectory = System.getProperty("user.dir");
    String pathSeparator = System.getProperty("file.separator");

    public String configFilePath() {
        return userWorkingDirectory + pathSeparator + "src" + pathSeparator + "main" +
                pathSeparator + "java" + pathSeparator + "dataDrivenFrameWork"
                + pathSeparator + "files" + pathSeparator + "config.properties";
    }

    public Properties getPropertiesObject() {
        Properties property = new Properties();
        try {
            FileInputStream file = new FileInputStream(configFilePath());
            property.load(file);
        } catch (FileNotFoundException exception) {
            System.out.println("The specified file is not present in the given path.");
        } catch (IOException exception) {
            System.out.println("Check the file in the specified path.");
        }
        return property;
    }

    public WebDriver initiateDriver() {

        String browserName = getPropertiesObject().getProperty("browser");
        if (browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else if (browserName.equalsIgnoreCase("safari")) {
            WebDriverManager.safaridriver().setup();
            driver = new SafariDriver();
        }
        return driver;
    }

    public String getXlsxFilePath() {
        return userWorkingDirectory + pathSeparator + "src" + pathSeparator + "main" +
                pathSeparator + "java" + pathSeparator + "dataDrivenFrameWork"
                + pathSeparator + "files" + pathSeparator + getPropertiesObject().getProperty("xlsxFileName");
    }

    public String getColumnOneHeader() {
        return getPropertiesObject().getProperty("columnOneHeader");
    }

    public String getColumnTwoHeader() {
        return getPropertiesObject().getProperty("columnTwoHeader");
    }

    public String getColumnThreeHeader() {
        return getPropertiesObject().getProperty("columnThreeHeader");
    }

    public String getColumnFourHeader() {
        return getPropertiesObject().getProperty("columnFourHeader");
    }

    public String getColumnFiveHeader() {
        return getPropertiesObject().getProperty("columnFiveHeader");
    }

    public String getColumnSixHeader() {
        return getPropertiesObject().getProperty("columnSixHeader");
    }

    public String getUrl() {
        return getPropertiesObject().getProperty("url");
    }

}
