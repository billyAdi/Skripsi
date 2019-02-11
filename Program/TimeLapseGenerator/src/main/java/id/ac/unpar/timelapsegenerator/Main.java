/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.unpar.timelapsegenerator;

import java.io.IOException;
import java.util.Properties;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.eclipse.jgit.api.errors.GitAPIException;

/**
 *
 * @author user
 */
public class Main {

    public static void main(String[] args) throws IOException, GitAPIException, ParseException, InterruptedException {
        CommandLineOptions commandLineOptions = new CommandLineOptions(args);
        Properties properties = new Properties();
        for (Option option : commandLineOptions.getParsedOptions()) {
            properties.setProperty(option.getLongOpt(), option.getValue());
        }
        VCS vcs = new VCS(properties.getProperty("project-path"));
        BrowserController seleniumWebDriver = new BrowserController();
        TimeLapseGenerator timeLapseGenerator = new TimeLapseGenerator();
        timeLapseGenerator.generateTimelapse(properties,vcs, seleniumWebDriver);
    }

}
