/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.unpar.timelapsegenerator;

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
    private ArrayList<File> fileScreenshot;

    public TimeLapseGenerator(CommandLineOptions commandLineoptions, VCS vcs, SeleniumWebDriver seleniumWebDriver) {
        this.commandLineoptions = commandLineoptions;
        this.vcs = vcs;
        this.seleniumWebDriver = seleniumWebDriver;
        ArrayList<File> fileScreenshot = new ArrayList<File>();
    }

    public void generateScreenshot() throws GitAPIException, IOException, InterruptedException {
        for (int i = 0; i < vcs.getCommitSize(); i++) {
            vcs.checkoutCommit(i);
            commandLineoptions.runScript();

            seleniumWebDriver.changePage(commandLineoptions.getOptionValue("capture-url"));
            seleniumWebDriver.takeScreenshot();

            vcs.reset();
        }
    }

    public void generateTimelapse() throws IOException {
        this.fileScreenshot = seleniumWebDriver.getFileScreenshot();

        BufferedImage firstImage = ImageIO.read(fileScreenshot.get(0));

        ImageOutputStream output = new FileImageOutputStream(new File("hasil_screenshot/output.gif"));
        int frameDelay = Integer.parseInt(commandLineoptions.getOptionValue("seconds-per-commit")) * 1000;

        GifSequenceWriter writer = new GifSequenceWriter(output, firstImage.getType(), frameDelay, false);

        writer.writeToSequence(firstImage);
        for (int i = 1; i < fileScreenshot.size(); i++) {
            BufferedImage nextImage = ImageIO.read(fileScreenshot.get(i));
            writer.writeToSequence(nextImage);
        }

        writer.close();
        output.close();
    }

}
