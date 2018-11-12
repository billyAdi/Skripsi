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
import java.util.Date;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevWalk;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author user
 */
public class Main {

    
    public static void main(String[] args) throws IOException, GitAPIException, ParseException, InterruptedException {
        Options options = new Options();
        Option captureURL = Option.builder().longOpt("capture-url").argName("url").hasArg().desc("link yang akan di capture").build();
        Option fps = Option.builder().longOpt("fps").argName("fps").hasArg().desc("fps video").build();
        Option migrateURL = Option.builder().longOpt("migrate-url").argName("url").hasArg().desc("url untuk migrate database").build();
        Option projectPath = Option.builder().longOpt("project-path").argName("path").hasArg().desc("project path").build();
        Option beforeCapture = Option.builder().longOpt("before-capture").argName("path").hasArg().desc("php script yang dijalankan sebelum melakukan screenshot").build();
        options.addOption(migrateURL);
        options.addOption(captureURL);
        options.addOption(fps);
        options.addOption(projectPath);
        options.addOption(beforeCapture);
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("fps")) {
            String temp = cmd.getOptionValue("fps");
            System.out.println(temp);
        }
        if (cmd.hasOption("migrate-url")) {
            String temp = cmd.getOptionValue("migrate-url");
            System.out.println(temp);
        }
        if (cmd.hasOption("capture-url")) {
            String temp = cmd.getOptionValue("capture-url");
            System.out.println(temp);
        }
        if (cmd.hasOption("project-path")) {
            String temp = cmd.getOptionValue("project-path");
            System.out.println(temp);
        }
        if (cmd.hasOption("before-capture")) {
            String temp = cmd.getOptionValue("before-capture");
            System.out.println(temp);
        }
        
        
        Repository repo = new FileRepository(cmd.getOptionValue("project-path"));

        Git git = new Git(repo);

        RevWalk revWalk = new RevWalk(repo);
        revWalk.markStart(revWalk.parseCommit(repo.resolve(Constants.HEAD)));
        revWalk.sort(RevSort.REVERSE);

        ArrayList<String> commitID = new ArrayList<String>();

        WebDriver driver = null;

        for (RevCommit commit : revWalk) {
            System.out.println(commit.getName().substring(0, 7) + " " + commit.getFullMessage());
            //System.out.println(commit.getAuthorIdent().getName()+" "+commit.getAuthorIdent().getEmailAddress()+d.getDate()+" "+d.getMonth()+" "+d.getYear());            
            commitID.add(commit.getName().substring(0, 7));
        }

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        String[] cmdWindows = new String[3];
        cmdWindows[0] = "cmd.exe" ;
        cmdWindows[1] = "/C" ;
        cmdWindows[2] = "php "+cmd.getOptionValue("before-capture");
        Runtime rt = Runtime.getRuntime();
        Process proc;

           
//        for (int i = 0; i < commitID.size(); i++) {
////            if (i == 41) {
////                break;
////            }
//            git.checkout().setName(commitID.get(i)).call();
//            
//            proc = rt.exec(cmdWindows); 
//            proc.waitFor();
//            driver.navigate().to(cmd.getOptionValue("migrate-url"));
//            driver.navigate().to(cmd.getOptionValue("capture-url"));
//            //file gambar masih disimpan di folder hasil screenshot. Harusnya ga perlu, nanti akan ditambahkan var array of file untuk menampung image Buffered
//            
//            File scrFile
//                    = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//            FileUtils.moveFile(scrFile, new File("hasil_screenshot/z" + i + ".png")); 
//                
//            git.reset().setMode(ResetType.HARD).call();
//        }
//        driver.quit();
//        git.checkout().setName("master").call();

        BufferedImage firstImage = ImageIO.read(new File("hasil_screenshot/z0.png")); //file pertama

        
        ImageOutputStream output
                = new FileImageOutputStream(new File("hasil_screenshot/output.gif")); //file output
        double frameDelay=1000.0/Integer.parseInt(cmd.getOptionValue("fps"));
        GifSequenceWriter writer= new GifSequenceWriter(output, firstImage.getType(),(int)frameDelay , false); //500ms adalah jarak antar frame, fpsnya= 1/0.5 s=2 fps. fps belum menggunakan nilai dari parameter
        
        writer.writeToSequence(firstImage);
        for (int i = 1; i < commitID.size(); i++) {
            BufferedImage nextImage = ImageIO.read(new File("hasil_screenshot/z"+i+".png"));
            writer.writeToSequence(nextImage);
        }

        writer.close();
        output.close();
    }
}
