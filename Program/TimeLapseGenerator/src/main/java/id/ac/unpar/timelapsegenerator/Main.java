/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.unpar.timelapsegenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
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
    //C:\\xampp\\htdocs\\DNArtworks\\.git
    //C:\\xampp\\htdocs\\Piktora\\.git
    public static void main(String[] args) throws IOException, GitAPIException, ParseException {
        Options options = new Options();
        Option captureURL = Option.builder().longOpt("capture-url").argName("url").hasArg().desc("link yang akan di capture").build();
        Option fps = Option.builder().longOpt("fps").argName("fps").hasArg().desc("fps video").build();
        Option beforeCapture = Option.builder().longOpt("before-capture").argName("url").hasArg().desc("migrate database").build();
        Option projectPath=Option.builder().longOpt("project-path").argName("path").hasArg().desc("project path").build();
        options.addOption(beforeCapture);
        options.addOption(captureURL);
        options.addOption(fps);
        options.addOption(projectPath);
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        
        if(cmd.hasOption("fps")){
            String temp=cmd.getOptionValue("fps");
            System.out.println(temp);
        }
        if(cmd.hasOption("before-capture")){
            String temp=cmd.getOptionValue("before-capture");
            System.out.println(temp);
        }
        if(cmd.hasOption("capture-url")){
            String temp=cmd.getOptionValue("capture-url");
            System.out.println(temp);
        }
        if(cmd.hasOption("project-path")){
            String temp=cmd.getOptionValue("project-path");
            System.out.println(temp);
        }
        
//        Repository repo = new FileRepository("C:\\xampp\\htdocs\\Piktora\\.git");
          Repository repo = new FileRepository(cmd.getOptionValue("project-path"));  
//        // System.out.println(repo.getBranch());
//
        Git git = new Git(repo);

        RevWalk revWalk = new RevWalk(repo);
        revWalk.markStart(revWalk.parseCommit(repo.resolve(Constants.HEAD)));
        revWalk.sort(RevSort.REVERSE);

        ArrayList<String> commitID = new ArrayList<String>();

        WebDriver driver = null;

        for (RevCommit commit : revWalk) {
            System.out.println(commit.getName().substring(0, 7) + " " + commit.getFullMessage());
            // Date d=commit.getAuthorIdent().getWhen();
            //System.out.println(commit.getAuthorIdent().getName()+" "+commit.getAuthorIdent().getEmailAddress()+d.getDate()+" "+d.getMonth()+" "+d.getYear());            
            commitID.add(commit.getName().substring(0, 7));
        }
        //driver = new ChromeDriver();
       // driver.get(cmd.getOptionValue("before-capture"));
        //driver.quit();
        driver = new ChromeDriver();
                driver.manage().window().maximize();
        for (int i = 0; i < commitID.size(); i++) {
            if(i==1)continue;
            git.checkout().setName(commitID.get(i)).call();
            if (i == 0) {
                driver.get(cmd.getOptionValue("capture-url"));
            }
            else {
                driver.navigate().refresh();
            }

            File scrFile
                    = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.moveFile(scrFile, new File("hasil_screenshot\\ss" + i + ".png"));

        }
        driver.quit();
        git.checkout().setName("master").call();
    }
}
