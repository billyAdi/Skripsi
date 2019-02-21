/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.unpar.timelapsegenerator;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import org.eclipse.jgit.api.errors.GitAPIException;

/**
 * Kelas ini digunakan untuk membangkitkan animasi timelapse.
 *
 * @author Billy Adiwijaya
 */
public class TimeLapseGenerator {

    /**
     * Method ini berfungsi untuk membangkitkan animasi timelapse. Hasil dari
     * animasi berupa File dengan tipe GIF.
     *
     * @param properties variabel yang menampung key dan value Option yang sudah
     * diparsing.
     * @param vcs variabel bertipe VCS yang digunakan untuk berinteraksi pada
     * proyek perangkat lunak berbasis web yang terekam oleh Git.
     * @param browserController variabel betipe BrowserController untuk mengatur
     * browser.
     * @throws GitAPIException jika terjadi masalah saat melakukan operasi Git
     * Checkout atau Git Reset.
     * @throws IOException jika terjadi masalah saat membaca file Gambar.
     * @throws InterruptedException jika terjadi interupsi pada thread saat menjalankan script PHP.
     */
    public void generateTimelapse(Properties properties, VCS vcs, BrowserController browserController) throws GitAPIException, IOException, InterruptedException {
        int indexAwal = 0;
        int indexAkhir = vcs.getNumberOfCommit() - 1;

        if (properties.getProperty("start-commit") != null) {
            indexAwal = vcs.getCommitIndex(properties.getProperty("start-commit"));
        }

        if (properties.getProperty("stop-commit") != null) {
            indexAkhir = vcs.getCommitIndex(properties.getProperty("stop-commit"));
        }

        String captureURL[] = properties.getProperty("capture-url").split(";");
        browserController.open();
        for (int i = indexAwal; i <= indexAkhir; i++) {
            vcs.checkoutCommit(i);

            if (properties.getProperty("before-capture") != null) {
                String argument = String.format("php %s", properties.getProperty("before-capture"));
                Process process;

                process = Runtime.getRuntime().exec(argument);
                process.waitFor();
            }

            for (int j = 0; j < captureURL.length; j++) {
                browserController.changePage(j, captureURL[j]);
                browserController.takeScreenshot(j);
            }

            vcs.hardReset();
        }
        browserController.quit();
        vcs.checkoutMaster();

        List<File> screenshotFiles = new ArrayList<>();

        screenshotFiles = browserController.getScreenshotFiles();
        BufferedImage[] bufferedImages = new BufferedImage[screenshotFiles.size()];

        for (int i = 0; i < screenshotFiles.size(); i++) {
            bufferedImages[i] = ImageIO.read(screenshotFiles.get(i));
        }

        BufferedImage[] bufferedResultImages = new BufferedImage[screenshotFiles.size() / browserController.getNumberOfBrowser()];
        switch (browserController.getNumberOfBrowser()) {
            case 1:
                bufferedResultImages = bufferedImages;
                break;
            case 2:
                for (int i = 0, j = 0; i < bufferedResultImages.length; i++, j += 2) {
                    bufferedResultImages[i] = new BufferedImage(bufferedImages[0].getWidth(), bufferedImages[0].getHeight(), BufferedImage.TYPE_INT_RGB);
                    Graphics2D graphics = bufferedResultImages[i].createGraphics();
                    graphics.setBackground(Color.WHITE);
                    graphics.fillRect(0, 0, bufferedImages[j].getWidth(), bufferedImages[j].getHeight());

                    graphics.drawImage(bufferedImages[j], bufferedImages[j].getWidth() / 4, 0, bufferedImages[j].getWidth() / 2, bufferedImages[j].getHeight() / 2, null);
                    graphics.drawImage(bufferedImages[j + 1], bufferedImages[j].getWidth() / 4, bufferedImages[j].getHeight() / 2, bufferedImages[j].getWidth() / 2, bufferedImages[j].getHeight() / 2, null);
                    graphics.dispose();
                }
                break;
            case 3:
                for (int i = 0, j = 0; i < bufferedResultImages.length; i++, j += 3) {
                    bufferedResultImages[i] = new BufferedImage(bufferedImages[0].getWidth(), bufferedImages[0].getHeight(), BufferedImage.TYPE_INT_RGB);
                    Graphics2D graphics = bufferedResultImages[i].createGraphics();
                    graphics.setBackground(Color.WHITE);
                    graphics.fillRect(0, 0, bufferedImages[j].getWidth(), bufferedImages[j].getHeight());

                    graphics.drawImage(bufferedImages[j], bufferedImages[j].getWidth() / 4, 0, bufferedImages[j].getWidth() / 2, bufferedImages[j].getHeight() / 2, null);
                    graphics.drawImage(bufferedImages[j + 1], 0, bufferedImages[j].getHeight() / 2, bufferedImages[j].getWidth() / 2, bufferedImages[j].getHeight() / 2, null);
                    graphics.drawImage(bufferedImages[j + 2], bufferedImages[j].getWidth() / 2, bufferedImages[j].getHeight() / 2, bufferedImages[j].getWidth() / 2, bufferedImages[j].getHeight() / 2, null);
                    graphics.dispose();
                }
                break;
            case 4:
                for (int i = 0, j = 0; i < bufferedResultImages.length; i++, j += 4) {
                    bufferedResultImages[i] = new BufferedImage(bufferedImages[0].getWidth(), bufferedImages[0].getHeight(), BufferedImage.TYPE_INT_RGB);
                    Graphics2D graphics = bufferedResultImages[i].createGraphics();
                    graphics.setBackground(Color.WHITE);
                    graphics.fillRect(0, 0, bufferedImages[j].getWidth(), bufferedImages[j].getHeight());

                    graphics.drawImage(bufferedImages[j], 0, 0, bufferedImages[j].getWidth() / 2, bufferedImages[j].getHeight() / 2, null);
                    graphics.drawImage(bufferedImages[j + 1], bufferedImages[j].getWidth() / 2, 0, bufferedImages[j].getWidth() / 2, bufferedImages[j].getHeight() / 2, null);
                    graphics.drawImage(bufferedImages[j + 2], 0, bufferedImages[j].getHeight() / 2, bufferedImages[j].getWidth() / 2, bufferedImages[j].getHeight() / 2, null);
                    graphics.drawImage(bufferedImages[j + 3], bufferedImages[j].getWidth() / 2, bufferedImages[j].getHeight() / 2, bufferedImages[j].getWidth() / 2, bufferedImages[j].getHeight() / 2, null);
                    graphics.dispose();
                }
                break;
            default:
                break;
        }

        for (BufferedImage bufferedResultImage : bufferedResultImages) {
            if (properties.getProperty("title") == null && properties.getProperty("logo") == null) {
                break;
            } else {
                if (properties.getProperty("title") != null) {
                    Graphics2D graphic = (Graphics2D) bufferedResultImage.getGraphics();
                    graphic.setFont(new Font("Times New Roman", Font.BOLD, 18));
                    FontMetrics fontMetrics = graphic.getFontMetrics(graphic.getFont());
                    graphic.setColor(Color.black);
                    graphic.drawString(properties.getProperty("title"), 5, bufferedResultImage.getHeight() - fontMetrics.getDescent() - 5);
                    graphic.dispose();
                }
                if (properties.getProperty("logo") != null) {
                    BufferedImage logo = ImageIO.read(new File(properties.getProperty("logo")));
                    Graphics2D graphic = (Graphics2D) bufferedResultImage.getGraphics();
                    graphic.drawImage(logo, bufferedResultImage.getWidth() - logo.getWidth() - 5, bufferedResultImage.getHeight() - logo.getHeight() - 5, null);
                    graphic.dispose();
                }
            }
        }
        String fileName = String.format("hasil_screenshot/%s.gif", new SimpleDateFormat("yyyy-MM-dd.HH.mm.ss").format(new Date()));
        try (ImageOutputStream output = new FileImageOutputStream(new File(fileName))) {
            int frameDelay = (int)(Double.parseDouble(properties.getProperty("seconds-per-commit")) * 1000);

            GifSequenceWriter writer = new GifSequenceWriter(output, bufferedResultImages[0].getType(), frameDelay, false);
            for (BufferedImage bufferedResultImage : bufferedResultImages) {
                writer.writeToSequence(bufferedResultImage);
            }
            writer.close();
        }
    }

}
