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
import org.apache.commons.io.FileUtils;
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
     * @throws IOException jika terjadi masalah saat menjalankan terminal
     * command.
     * @throws InterruptedException jika terjadi interupsi pada thread saat
     * menjalankan terminal command.
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
                Process process;
                process = Runtime.getRuntime().exec(properties.getProperty("before-capture"));
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
        List<File> resultImages = new ArrayList<>();
        screenshotFiles = browserController.getScreenshotFiles();

        switch (browserController.getNumberOfBrowser()) {
            case 1:
                resultImages = screenshotFiles;
                break;
            case 2:
                for (int i = 0, j = 0; i < screenshotFiles.size() / browserController.getNumberOfBrowser(); i++, j += 2) {
                    resultImages.add(screenshotFiles.get(i));
                    BufferedImage bufferedImage1, bufferedImage2, bufferedImageResult;
                    bufferedImage1 = ImageIO.read(screenshotFiles.get(j));
                    bufferedImage2 = ImageIO.read(screenshotFiles.get(j + 1));
                    bufferedImageResult = new BufferedImage(bufferedImage1.getWidth(), bufferedImage1.getHeight(), bufferedImage1.getType());

                    Graphics2D graphics = bufferedImageResult.createGraphics();
                    graphics.setBackground(Color.WHITE);
                    graphics.fillRect(0, 0, bufferedImageResult.getWidth(), bufferedImageResult.getHeight());

                    graphics.drawImage(bufferedImage1, bufferedImage1.getWidth() / 4, 0, bufferedImage1.getWidth() / 2, bufferedImage1.getHeight() / 2, null);
                    graphics.drawImage(bufferedImage2, bufferedImage2.getWidth() / 4, bufferedImage2.getHeight() / 2, bufferedImage2.getWidth() / 2, bufferedImage2.getHeight() / 2, null);
                    graphics.dispose();
                    ImageIO.write(bufferedImageResult, "png", resultImages.get(i));
                }
                break;
            case 3:
                for (int i = 0, j = 0; i < screenshotFiles.size() / browserController.getNumberOfBrowser(); i++, j += 3) {
                    resultImages.add(screenshotFiles.get(i));
                    BufferedImage bufferedImage1, bufferedImage2, bufferedImage3, bufferedImageResult;
                    bufferedImage1 = ImageIO.read(screenshotFiles.get(j));
                    bufferedImage2 = ImageIO.read(screenshotFiles.get(j + 1));
                    bufferedImage3 = ImageIO.read(screenshotFiles.get(j + 2));
                    bufferedImageResult = new BufferedImage(bufferedImage1.getWidth(), bufferedImage1.getHeight(), bufferedImage1.getType());

                    Graphics2D graphics = bufferedImageResult.createGraphics();
                    graphics.setBackground(Color.WHITE);
                    graphics.fillRect(0, 0, bufferedImageResult.getWidth(), bufferedImageResult.getHeight());

                    graphics.drawImage(bufferedImage1, bufferedImage1.getWidth() / 4, 0, bufferedImage1.getWidth() / 2, bufferedImage1.getHeight() / 2, null);
                    graphics.drawImage(bufferedImage2, 0, bufferedImage2.getHeight() / 2, bufferedImage2.getWidth() / 2, bufferedImage2.getHeight() / 2, null);
                    graphics.drawImage(bufferedImage3, bufferedImage3.getWidth() / 2, bufferedImage3.getHeight() / 2, bufferedImage3.getWidth() / 2, bufferedImage3.getHeight() / 2, null);
                    graphics.dispose();
                    ImageIO.write(bufferedImageResult, "png", resultImages.get(i));
                }
                break;
            case 4:
                for (int i = 0, j = 0; i < screenshotFiles.size() / browserController.getNumberOfBrowser(); i++, j += 4) {
                    resultImages.add(screenshotFiles.get(i));
                    BufferedImage bufferedImage1, bufferedImage2, bufferedImage3, bufferedImage4, bufferedImageResult;
                    bufferedImage1 = ImageIO.read(screenshotFiles.get(j));
                    bufferedImage2 = ImageIO.read(screenshotFiles.get(j + 1));
                    bufferedImage3 = ImageIO.read(screenshotFiles.get(j + 2));
                    bufferedImage4 = ImageIO.read(screenshotFiles.get(j + 3));

                    bufferedImageResult = new BufferedImage(bufferedImage1.getWidth(), bufferedImage1.getHeight(), bufferedImage1.getType());

                    Graphics2D graphics = bufferedImageResult.createGraphics();
                    graphics.setBackground(Color.WHITE);
                    graphics.fillRect(0, 0, bufferedImageResult.getWidth(), bufferedImageResult.getHeight());

                    graphics.drawImage(bufferedImage1, 0, 0, bufferedImage1.getWidth() / 2, bufferedImage1.getHeight() / 2, null);
                    graphics.drawImage(bufferedImage2, bufferedImage2.getWidth() / 2, 0, bufferedImage2.getWidth() / 2, bufferedImage2.getHeight() / 2, null);
                    graphics.drawImage(bufferedImage3, 0, bufferedImage3.getHeight() / 2, bufferedImage3.getWidth() / 2, bufferedImage3.getHeight() / 2, null);
                    graphics.drawImage(bufferedImage4, bufferedImage4.getWidth() / 2, bufferedImage4.getHeight() / 2, bufferedImage4.getWidth() / 2, bufferedImage4.getHeight() / 2, null);
                    graphics.dispose();
                    ImageIO.write(bufferedImageResult, "png", resultImages.get(i));
                }
                break;
            default:
                break;
        }
        int ct = 0;
        for (File resultImage : resultImages) {
            try {
                FileUtils.copyFile(resultImage, new File("D:/Temp/z" + ct + ".png"));
            } catch (IOException ex) {
            }
            ct++;
        }
        for (File resultImage : resultImages) {
            if (properties.getProperty("title") == null && properties.getProperty("logo") == null) {
                break;
            } else {
                if (properties.getProperty("title") != null) {
                    BufferedImage bufferedImage = ImageIO.read(resultImage);
                    Graphics2D graphic = (Graphics2D) bufferedImage.getGraphics();
                    graphic.setFont(new Font("Times New Roman", Font.BOLD, 18));
                    FontMetrics fontMetrics = graphic.getFontMetrics(graphic.getFont());
                    graphic.setColor(Color.black);
                    graphic.drawString(properties.getProperty("title"), 5, bufferedImage.getHeight() - fontMetrics.getDescent() - 5);
                    graphic.dispose();
                    ImageIO.write(bufferedImage, "png", resultImage);
                }

                if (properties.getProperty("logo") != null) {
                    BufferedImage logo = ImageIO.read(new File(properties.getProperty("logo")));
                    BufferedImage bufferedImage = ImageIO.read(resultImage);
                    Graphics2D graphic = (Graphics2D) bufferedImage.getGraphics();
                    graphic.drawImage(logo, bufferedImage.getWidth() - logo.getWidth() - 5, bufferedImage.getHeight() - logo.getHeight() - 5, null);
                    graphic.dispose();
                    ImageIO.write(bufferedImage, "png", resultImage);
                }
            }
        }
        String fileName = String.format("%s.gif", new SimpleDateFormat("yyyy-MM-dd.HH.mm.ss").format(new Date()));
        try (ImageOutputStream output = new FileImageOutputStream(new File(fileName))) {
            int frameDelay = 1000;
            if (properties.getProperty("seconds-per-commit") != null) {
                frameDelay = (int) (Double.parseDouble(properties.getProperty("seconds-per-commit")) * 1000);
                if (frameDelay == 0) {
                    frameDelay = 1000;
                }
            }
            GifSequenceWriter writer = new GifSequenceWriter(output, ImageIO.read(resultImages.get(0)).getType(), frameDelay, false);

            for (File resultImage : resultImages) {
                BufferedImage bufferedImage = ImageIO.read(resultImage);
                writer.writeToSequence(bufferedImage);
            }
            writer.close();
        }
    }
}
