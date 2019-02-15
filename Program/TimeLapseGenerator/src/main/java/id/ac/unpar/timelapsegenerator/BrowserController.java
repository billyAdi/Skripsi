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
 * Kelas ini digunakan untuk mengatur browser.Operasi-operasi yang dilakukan 
 * terhadap browser yaitu membuka browser, mengambil screenshot, membuat window 
 * browser menjadi maksimal, dan menutup browser.
 * 
 * Hasil screenshot disimpan dalam bentuk List of File.
 * Variabel driver merupakan suatu representasi dari browser.
 * Kelas ini dapat memiliki maksimal empat browser.
 * 
 * @author Billy Adiwijaya
 */
public class BrowserController {

    private WebDriver[] driver;
    private List<File> fileScreenshot;
    private int numberOfBrowsers;

    /**
     * Constructor yang berfungsi untuk menginisialisasi variabel yang dimiliki oleh kelas ini.
     * @param numberOfBrowsers jumlah browser 
     */
    public BrowserController(int numberOfBrowsers) {
        this.driver = new WebDriver[numberOfBrowsers];
        this.fileScreenshot = new ArrayList<File>();
        this.numberOfBrowsers = numberOfBrowsers;
    }

    /**
     * Method ini berfungsi untuk membuka semua browser kemudian mengatur ukuran window browser menjadi maksimal.
     */
    public void open() {
        for (int i = 0; i < numberOfBrowsers; i++) {
            this.driver[i] = new ChromeDriver();
            this.driver[i].manage().window().maximize();
        }
    }

    /**
     * Method ini berfungsi untuk mengembalikan jumlah browser yang dimiliki
     * kelas ini.
     *
     * @return jumlah browser yang dimiliki oleh kelas ini.
     */
    public int getNumberOfBrowsers() {
        return numberOfBrowsers;
    }

    /**
     * Method ini berfungsi untuk berpindah halaman pada browser tertentu.
     *
     * @param browserIndex indeks browser yang akan diubah halamannya.
     * @url alamat url untuk berpindah halaman.
     */
    public void changePage(int browserIndex, String url) {
        this.driver[browserIndex].navigate().to(url);
    }

    /**
     * Method ini berfungsi untuk menutup semua browser.
     */
    public void quit() {
        for (int i = 0; i < this.driver.length; i++) {
            this.driver[i].quit();
        }
    }

    /**
     * Method ini berfungsi untuk mengambil screenshot pada browser tertentu.
     *
     * @param browserIndex yang akan diambil screenshotnya.
     */
    public void takeScreenshot(int browserIndex) {
        this.fileScreenshot.add(((TakesScreenshot) this.driver[browserIndex]).getScreenshotAs(OutputType.FILE));
    }

    /**
     * Method ini berfungsi untuk menghapus semua file screenshot.
     */
    public void clearFileScreenshot() {
        this.fileScreenshot.clear();
    }

    /**
     * Method ini berfungsi untuk mengembalikan hasil screenshot.
     *
     * @return hasil screenshot berupa List of File.
     */
    public List<File> getFileScreenshot() {
        return this.fileScreenshot;
    }
}
