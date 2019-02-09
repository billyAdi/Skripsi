/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.unpar.timelapsegenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author user
 */
public class BrowserController {

    private WebDriver driver;
    private List<File> fileScreenshot;

    public BrowserController() {
        this.driver = new ChromeDriver();
        this.driver.manage().window().maximize();

        this.fileScreenshot = new ArrayList<File>();
    }

    public void changePage(String url) {
        this.driver.navigate().to(url);
    }

    public void quit() {
        this.driver.quit();
    }

    public void takeScreenshot() {
        this.fileScreenshot.add(((TakesScreenshot) this.driver).getScreenshotAs(OutputType.FILE));
    }

    public void clearFileScreenshot() {
        this.fileScreenshot.clear();
    }

    public List<File> getFileScreenshot() {
        return this.fileScreenshot;
    }
}
