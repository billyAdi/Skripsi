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

    public static void main(String[] args) throws IOException, GitAPIException {
        Repository repo = new FileRepository("C:\\xampp\\htdocs\\Piktora\\.git");
        // System.out.println(repo.getBranch());

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

        for (int i = 0; i < commitID.size(); i++) {
            git.checkout().setName(commitID.get(i)).call();
            if (i == 0) {
                driver = new ChromeDriver();
                driver.manage().window().maximize();
                driver.get("http://localhost/");
            } else {
                driver.navigate().refresh();
            }

            File scrFile
                    = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.moveFile(scrFile, new File("hasil_screenshot\\screenshotss" + i + ".png"));

        }
        driver.quit();
        git.checkout().setName("master").call();
    }
}
