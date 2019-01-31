/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.unpar.timelapsegenerator;

import java.io.File;
import java.util.ArrayList;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author user
 */
public class SeleniumWebDriver {

    private WebDriver driver;
    private ArrayList<File> fileScrenshot;

    public SeleniumWebDriver() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        this.fileScrenshot = new ArrayList<File>();
    }

    public void changePage(String url) {
        driver.navigate().to(url);
    }

    public void quit() {
        driver.quit();
    }

    public void takeScreenshot() {
        fileScrenshot.add(((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE));
    }
    
    public ArrayList<File> getFileScreenshot(){
        return fileScrenshot;
    }
}
