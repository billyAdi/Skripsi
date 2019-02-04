/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.unpar.timelapsegenerator;

import java.io.IOException;
import org.apache.commons.cli.ParseException;
import org.eclipse.jgit.api.errors.GitAPIException;

/**
 *
 * @author user
 */
public class Main {

    
    public static void main(String[] args) throws IOException, GitAPIException, ParseException, InterruptedException {
        CommandLineOptions commandLineoptions = new CommandLineOptions(args);
        VCS vcs = new VCS(commandLineoptions.getOptionValue("project-path"));
        BrowserController seleniumWebDriver = new BrowserController();
        TimeLapseGenerator timeLapseGenerator=new TimeLapseGenerator(commandLineoptions,vcs,seleniumWebDriver);
        timeLapseGenerator.generateTimelapse();
    }

}
