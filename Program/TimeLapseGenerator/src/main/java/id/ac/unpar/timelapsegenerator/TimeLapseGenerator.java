/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.unpar.timelapsegenerator;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import org.eclipse.jgit.api.errors.GitAPIException;

/**
 *
 * @author user
 */
public class TimeLapseGenerator {

    public void generateTimelapse(Properties properties, VCS vcs, BrowserController seleniumWebDriver) throws IOException, GitAPIException, InterruptedException {
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

        String captureURL = "http://localhost";

        if (properties.getProperty("capture-url") != null) {
            captureURL = properties.getProperty("capture-url");
        }

        for (int i = indexAwal; i <= indexAkhir; i++) {
            vcs.checkoutCommit(i);

            if (properties.getProperty("before-capture") != null) {
                String argument = String.format("php %s", properties.getProperty("before-capture"));
                Process process = Runtime.getRuntime().exec(argument);
                process.waitFor();
            }

            seleniumWebDriver.changePage(captureURL);
            seleniumWebDriver.takeScreenshot();

            vcs.hardReset();
        }
        seleniumWebDriver.quit();
//        this.vcs.checkoutMaster();

        List<File> fileScreenshot = new ArrayList<File>();

        fileScreenshot = seleniumWebDriver.getFileScreenshot();
        BufferedImage[] bufferedImage = new BufferedImage[fileScreenshot.size()];
        for (int i = 0; i < fileScreenshot.size(); i++) {
            bufferedImage[i] = ImageIO.read(fileScreenshot.get(i));
            if (properties.getProperty("title") != null) {

                Graphics graphic = bufferedImage[i].getGraphics();
                graphic.setFont(new Font("Times New Roman", Font.BOLD, 18));
                FontMetrics fontMetrics = graphic.getFontMetrics(graphic.getFont());
                graphic.setColor(Color.black);
                graphic.drawString(properties.getProperty("title"), 5,bufferedImage[i].getHeight()-fontMetrics.getDescent()-5);
                graphic.dispose();
            }
            if (properties.getProperty("logo") != null) {
                BufferedImage logo = ImageIO.read(new File(properties.getProperty("logo")));
                Graphics graphic = bufferedImage[i].getGraphics();
                graphic.drawImage(logo, bufferedImage[i].getWidth()-logo.getWidth()-5,bufferedImage[i].getHeight()-logo.getHeight()-5, null);
                graphic.dispose();
            }

        }

        ImageOutputStream output = new FileImageOutputStream(new File("hasil_screenshot/output.gif"));
        int frameDelay = 1000;
        if (properties.getProperty("seconds-per-commit") != null) {
            frameDelay = Integer.parseInt(properties.getProperty("seconds-per-commit")) * 1000;
        }

        GifSequenceWriter writer = new GifSequenceWriter(output, bufferedImage[0].getType(), frameDelay, false);
        writer.writeToSequence(bufferedImage[0]);
        for (int i = 1; i < fileScreenshot.size(); i++) {
            writer.writeToSequence(bufferedImage[i]);
        }
        writer.close();
        output.close();
    }

}
