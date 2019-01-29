/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.unpar.timelapsegenerator;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author user
 */
public class SeleniumWebDriver {
   private WebDriver driver;
   
  
   public SeleniumWebDriver(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
   }
   
   public void changePage(String url){
         driver.navigate().to(url);
   }
    
   public void quit(){
         driver.quit();
   }
   
    //screenshot belum
   public void takeScreenshot(){
       
   }
}
