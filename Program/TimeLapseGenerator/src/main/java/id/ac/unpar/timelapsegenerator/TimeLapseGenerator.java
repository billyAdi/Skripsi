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
 * @author @author Billy Adiwijaya
 */
public class TimeLapseGenerator {

    /**
     * Method ini berfungsi untuk membangkitkan animasi timelapse.
     * Hasil dari animasi berupa File dengan tipe GIF.
     * @param properties variabel yang menampung key dan value Option yang sudah diparsing.
     * @param vcs variabel bertipe VCS yang digunakan untuk berinteraksi pada proyek perangkat lunak berbasis web yang terekam oleh Git.
     * @param browserController variabel betipe BrowserController untuk mengatur browser.
     * @throws InterruptedException jika terjadi interupsi pada thread.
     * @throws IOException jika terjadi masalah saat membaca file.
     */
    public void generateTimelapse(Properties properties, VCS vcs, BrowserController browserController) throws IOException, InterruptedException {
        int indexAwal = 0;
        int indexAkhir = vcs.getCommitSize() - 1;

        if (properties.getProperty("start-commit") != null) {
            if (vcs.getCommitIndex(properties.getProperty("start-commit")) != -1) {
                indexAwal = vcs.getCommitIndex(properties.getProperty("start-commit"));
            }
        }

        if (properties.getProperty("stop-commit") != null) {
            if (vcs.getCommitIndex(properties.getProperty("stop-commit")) != -1) {
                indexAkhir = vcs.getCommitIndex(properties.getProperty("stop-commit"));
            }
        }

        String captureURL[] = properties.getProperty("capture-url").split(";");
        browserController.open();
        for (int i = indexAwal; i <= indexAkhir; i++) {
            vcs.checkoutCommit(i);

            if (properties.getProperty("before-capture") != null) {
                String argument = String.format("php %s", properties.getProperty("before-capture"));
                Process process = Runtime.getRuntime().exec(argument);
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

        List<File> fileScreenshot = new ArrayList<File>();

        fileScreenshot = browserController.getFileScreenshot();
        BufferedImage[] bufferedImage = new BufferedImage[fileScreenshot.size()];

        for (int i = 0; i < fileScreenshot.size(); i++) {
            bufferedImage[i] = ImageIO.read(fileScreenshot.get(i));
        }

        BufferedImage[] bufferedImageResult = new BufferedImage[fileScreenshot.size() / browserController.getNumberOfBrowsers()];
        if (browserController.getNumberOfBrowsers() == 1) {
            bufferedImageResult = bufferedImage;
        } else if (browserController.getNumberOfBrowsers() == 2) {
            for (int i = 0, j = 0; i < bufferedImageResult.length; i++, j += 2) {
                bufferedImageResult[i] = new BufferedImage(bufferedImage[0].getWidth(), bufferedImage[0].getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = bufferedImageResult[i].createGraphics();
                graphics.setBackground(Color.WHITE);
                graphics.fillRect(0, 0, bufferedImage[j].getWidth(), bufferedImage[j].getHeight());

                graphics.drawImage(bufferedImage[j], bufferedImage[j].getWidth() / 4, 0, bufferedImage[j].getWidth() / 2, bufferedImage[j].getHeight() / 2, null);
                graphics.drawImage(bufferedImage[j + 1], bufferedImage[j].getWidth() / 4, bufferedImage[j].getHeight() / 2, bufferedImage[j].getWidth() / 2, bufferedImage[j].getHeight() / 2, null);
                graphics.dispose();
            }
        } else if (browserController.getNumberOfBrowsers() == 3) {
            for (int i = 0, j = 0; i < bufferedImageResult.length; i++, j += 3) {
                bufferedImageResult[i] = new BufferedImage(bufferedImage[0].getWidth(), bufferedImage[0].getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = bufferedImageResult[i].createGraphics();
                graphics.setBackground(Color.WHITE);
                graphics.fillRect(0, 0, bufferedImage[j].getWidth(), bufferedImage[j].getHeight());

                graphics.drawImage(bufferedImage[j], bufferedImage[j].getWidth() / 4, 0, bufferedImage[j].getWidth() / 2, bufferedImage[j].getHeight() / 2, null);
                graphics.drawImage(bufferedImage[j + 1], 0, bufferedImage[j].getHeight() / 2, bufferedImage[j].getWidth() / 2, bufferedImage[j].getHeight() / 2, null);
                graphics.drawImage(bufferedImage[j + 2], bufferedImage[j].getWidth() / 2, bufferedImage[j].getHeight() / 2, bufferedImage[j].getWidth() / 2, bufferedImage[j].getHeight() / 2, null);
                graphics.dispose();
            }
        } else if (browserController.getNumberOfBrowsers() == 4) {
            for (int i = 0, j = 0; i < bufferedImageResult.length; i++, j += 4) {
                bufferedImageResult[i] = new BufferedImage(bufferedImage[0].getWidth(), bufferedImage[0].getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = bufferedImageResult[i].createGraphics();
                graphics.setBackground(Color.WHITE);
                graphics.fillRect(0, 0, bufferedImage[j].getWidth(), bufferedImage[j].getHeight());

                graphics.drawImage(bufferedImage[j], 0, 0, bufferedImage[j].getWidth() / 2, bufferedImage[j].getHeight() / 2, null);
                graphics.drawImage(bufferedImage[j + 1], bufferedImage[j].getWidth() / 2, 0, bufferedImage[j].getWidth() / 2, bufferedImage[j].getHeight() / 2, null);
                graphics.drawImage(bufferedImage[j + 2], 0, bufferedImage[j].getHeight() / 2, bufferedImage[j].getWidth() / 2, bufferedImage[j].getHeight() / 2, null);
                graphics.drawImage(bufferedImage[j + 3], bufferedImage[j].getWidth() / 2, bufferedImage[j].getHeight() / 2, bufferedImage[j].getWidth() / 2, bufferedImage[j].getHeight() / 2, null);
                graphics.dispose();
            }
        }

        for (int i = 0; i < bufferedImageResult.length; i++) {
            if (properties.getProperty("title") == null && properties.getProperty("logo") == null) {
                break;
            } else {
                if (properties.getProperty("title") != null) {
                    Graphics2D graphic = (Graphics2D) bufferedImageResult[i].getGraphics();
                    graphic.setFont(new Font("Times New Roman", Font.BOLD, 18));
                    FontMetrics fontMetrics = graphic.getFontMetrics(graphic.getFont());
                    graphic.setColor(Color.black);
                    graphic.drawString(properties.getProperty("title"), 5, bufferedImageResult[i].getHeight() - fontMetrics.getDescent() - 5);
                    graphic.dispose();
                }
                if (properties.getProperty("logo") != null) {
                    BufferedImage logo = ImageIO.read(new File(properties.getProperty("logo")));
                    Graphics2D graphic = (Graphics2D) bufferedImageResult[i].getGraphics();
                    graphic.drawImage(logo, bufferedImageResult[i].getWidth() - logo.getWidth() - 5, bufferedImageResult[i].getHeight() - logo.getHeight() - 5, null);
                    graphic.dispose();
                }
            }

        }
        String fileName = String.format("hasil_screenshot/%s.gif", new SimpleDateFormat("yyyy-MM-dd.HH.mm.ss").format(new Date()));
        ImageOutputStream output = new FileImageOutputStream(new File(fileName));

        int frameDelay = Integer.parseInt(properties.getProperty("seconds-per-commit")) * 1000;

        GifSequenceWriter writer = new GifSequenceWriter(output, bufferedImageResult[0].getType(), frameDelay, false);
        for (int i = 0; i < bufferedImageResult.length; i++) {
            writer.writeToSequence(bufferedImageResult[i]);
        }
        writer.close();
        output.close();
    }

}
