package id.ac.unpar.timelapsegenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Kelas ini digunakan untuk mengatur browser.Operasi-operasi yang dilakukan
 * terhadap browser yaitu membuka browser, mengambil screenshot, membuat window
 * browser menjadi maksimal, dan menutup browser.
 *
 * Hasil screenshot disimpan dalam bentuk List of File. Variabel driver
 * merupakan suatu representasi dari browser. Kelas ini dapat memiliki maksimal
 * empat browser.
 *
 * @author Billy Adiwijaya
 */
public class BrowserController {

    private final WebDriver[] drivers;
    private final List<File> screenshotFiles;
    private final int numberOfBrowser;
    
    /**
     * Constructor yang berfungsi untuk menginisialisasi variabel yang dimiliki
     * oleh kelas ini.
     *
     * @param numberOfBrowsers jumlah browser.
     */
    public BrowserController(int numberOfBrowsers) {
        this.drivers = new WebDriver[numberOfBrowsers];
        this.screenshotFiles = new ArrayList<>();
        this.numberOfBrowser = numberOfBrowsers;
    }

    /**
     * Method ini berfungsi untuk membuka semua browser, kemudian mengatur
     * ukuran window browser menjadi maksimal.
     */
    public void open() {
        for (int i = 0; i < numberOfBrowser; i++) {
            this.drivers[i] = new ChromeDriver();  
            this.drivers[i].manage().window().maximize();
        }
    }

    /**
     * Method ini berfungsi untuk mengembalikan jumlah browser yang dimiliki
     * kelas ini.
     *
     * @return jumlah browser yang dimiliki oleh kelas ini.
     */
    public int getNumberOfBrowser() {
        return numberOfBrowser;
    }

    /**
     * Method ini berfungsi untuk berpindah halaman pada browser tertentu.
     *
     * @param browserIndex indeks browser yang akan diubah halamannya.
     * @param url alamat URL untuk berpindah halaman.
     */
    public void changePage(int browserIndex, String url) {
        this.drivers[browserIndex].get(url);
    }

    /**
     * Method ini berfungsi untuk menutup semua browser.
     */
    public void quit() {
        for (WebDriver driver : this.drivers) {
            driver.quit();
        }
    }

    /**
     * Method ini berfungsi untuk mengambil screenshot pada browser tertentu dan
     * menyimpannya ke atribut screenshotFiles.
     *
     * @param browserIndex indeks browser yang akan diambil screenshotnya.
     */
    public void takeScreenshot(int browserIndex) {
        this.screenshotFiles.add(((TakesScreenshot) this.drivers[browserIndex]).getScreenshotAs(OutputType.FILE));
    }

    /**
     * Method ini berfungsi untuk mengembalikan hasil screenshot.
     *
     * @return hasil screenshot berupa List of File.
     */
    public List<File> getScreenshotFiles() {
        return this.screenshotFiles;
    }
}
