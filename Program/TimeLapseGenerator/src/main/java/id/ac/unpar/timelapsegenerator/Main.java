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

        CommandLineOptions commandLineoptions=new CommandLineOptions(args);
        
        
        Repository repo = new FileRepository(commandLineoptions.getOptionValue("project-path"));

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
        cmdWindows[2] = "php "+commandLineoptions.getOptionValue("before-capture");
        Runtime rt = Runtime.getRuntime();
        Process proc;

           
        for (int i = 0; i < commitID.size(); i++) {
            
            git.checkout().setName(commitID.get(i)).call();
            
            proc = rt.exec(cmdWindows); 
            proc.waitFor();
            driver.navigate().to(commandLineoptions.getOptionValue("capture-url"));
            //file gambar masih disimpan di folder hasil screenshot. Harusnya ga perlu, nanti akan ditambahkan var array of file untuk menampung image Buffered
            
            File scrFile
                    = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.moveFile(scrFile, new File("hasil_screenshot/z" + i + ".png")); 
                
            git.reset().setMode(ResetType.HARD).call();
        }
        driver.quit();
        git.checkout().setName("master").call();

        BufferedImage firstImage = ImageIO.read(new File("hasil_screenshot/z0.png")); //file pertama

        
        ImageOutputStream output
                = new FileImageOutputStream(new File("hasil_screenshot/output.gif")); //file output
        int frameDelay=Integer.parseInt(commandLineoptions.getOptionValue("seconds-per-commit"))*1000;
        GifSequenceWriter writer= new GifSequenceWriter(output, firstImage.getType(),frameDelay , false);
        
        writer.writeToSequence(firstImage);
        for (int i = 1; i < commitID.size(); i++) {
            BufferedImage nextImage = ImageIO.read(new File("hasil_screenshot/z"+i+".png"));
            writer.writeToSequence(nextImage);
        }

        writer.close();
        output.close();
    }
}
