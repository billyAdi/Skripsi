/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.unpar.timelapsegenerator;

import java.io.File;
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

    private WebDriver[] driver;
    private List<File> fileScreenshot;

    public BrowserController(int numberOfBrowsers) {
        
        this.driver = new WebDriver[numberOfBrowsers];
        for (int i = 0; i < numberOfBrowsers; i++) {
            this.driver[i] = new ChromeDriver();
            this.driver[i].manage().window().maximize();
        }

        this.fileScreenshot = new ArrayList<File>();
    }

    public void changePage(int browserIndex,String url) {
        this.driver[browserIndex].navigate().to(url);
    }
    
    public void quit() {
        for (int i = 0; i < this.driver.length; i++) {
            this.driver[i].quit();
        }
    }

    public void takeScreenshot() {
        for (int i = 0; i < this.driver.length; i++) {
            this.fileScreenshot.add(((TakesScreenshot) this.driver[i]).getScreenshotAs(OutputType.FILE));
        }
    }

    public void clearFileScreenshot() {
        this.fileScreenshot.clear();
    }

    public List<File> getFileScreenshot() {
        return this.fileScreenshot;
    }
}
