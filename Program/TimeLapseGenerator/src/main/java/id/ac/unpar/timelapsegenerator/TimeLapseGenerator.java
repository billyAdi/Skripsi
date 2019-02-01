/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.unpar.timelapsegenerator;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import org.eclipse.jgit.api.errors.GitAPIException;

/**
 *
 * @author user
 */
public class TimeLapseGenerator {

    private CommandLineOptions commandLineoptions;
    private VCS vcs;
    private SeleniumWebDriver seleniumWebDriver;

    public TimeLapseGenerator(CommandLineOptions commandLineoptions, VCS vcs, SeleniumWebDriver seleniumWebDriver) {
        this.commandLineoptions = commandLineoptions;
        this.vcs = vcs;
        this.seleniumWebDriver = seleniumWebDriver;
    }

    public void generateTimelapse() throws IOException, GitAPIException, InterruptedException {
        int indexAwal = 0;
        int indexAkhir = this.vcs.getCommitSize() - 1;

        if (!commandLineoptions.getOptionValue("start-commit").equals("")) {
            indexAwal = this.vcs.getIndexCommit(commandLineoptions.getOptionValue("start-commit"));
        }

        if (!commandLineoptions.getOptionValue("stop-commit").equals("")) {
            indexAkhir = this.vcs.getIndexCommit(commandLineoptions.getOptionValue("stop-commit"));
        }

        for (int i = indexAwal; i <= indexAkhir; i++) {
            this.vcs.checkoutCommit(i);
            this.commandLineoptions.runScript();

            this.seleniumWebDriver.changePage(commandLineoptions.getOptionValue("capture-url"));
            this.seleniumWebDriver.takeScreenshot();

            this.vcs.reset();
        }
        this.seleniumWebDriver.quit();
//        this.vcs.checkoutMaster();

        ArrayList<File> fileScreenshot = new ArrayList<File>();

        fileScreenshot = seleniumWebDriver.getFileScreenshot();
        BufferedImage[] bufferedImage = new BufferedImage[fileScreenshot.size()];
        for (int i = 0; i < fileScreenshot.size(); i++) {
            bufferedImage[i] = ImageIO.read(fileScreenshot.get(i));
            if (!this.commandLineoptions.getOptionValue("title").equals("")) {
                Graphics g = bufferedImage[i].getGraphics();
                g.setFont(new Font("TimesRoman", Font.BOLD, 20));
                g.setColor(Color.black);
                g.drawString(this.commandLineoptions.getOptionValue("title"), 20, 580);
                g.dispose();
            }
            if (!this.commandLineoptions.getOptionValue("logo").equals("")) {
                BufferedImage logo = ImageIO.read(new File(this.commandLineoptions.getOptionValue("logo")));

                Graphics g = bufferedImage[i].getGraphics();
                g.drawImage(logo, 100, 100,null);
                g.dispose();
            }

        }

//        BufferedImage firstImage = ImageIO.read(this.fileScreenshot.get(0));
        ImageOutputStream output = new FileImageOutputStream(new File("hasil_screenshot/output.gif"));
        int frameDelay = Integer.parseInt(this.commandLineoptions.getOptionValue("seconds-per-commit")) * 1000;

//        GifSequenceWriter writer = new GifSequenceWriter(output, firstImage.getType(), frameDelay, false);
        GifSequenceWriter writer = new GifSequenceWriter(output, bufferedImage[0].getType(), frameDelay, false);

//        writer.writeToSequence(firstImage);
        writer.writeToSequence(bufferedImage[0]);
        for (int i = 1; i < fileScreenshot.size(); i++) {
//            BufferedImage nextImage = ImageIO.read(this.fileScreenshot.get(i));
//            writer.writeToSequence(nextImage);
            writer.writeToSequence(bufferedImage[i]);
        }

        writer.close();
        output.close();
    }

}
