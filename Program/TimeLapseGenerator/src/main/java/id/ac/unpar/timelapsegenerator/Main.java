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
        int numberOfBrowsers=0;
        for (Option option : commandLineOptions.getParsedOptions()) {
            if (option.getLongOpt().equals("capture-url")) {
                String[] values = option.getValues();
                numberOfBrowsers=values.length;
                if(numberOfBrowsers>4){
                    numberOfBrowsers=4;
                }
                String value = values[0];
                for (int i = 1; i < numberOfBrowsers; i++) {
                    value = value + ";" + values[i];
                }
                properties.setProperty(option.getLongOpt(), value);
            } else {
                properties.setProperty(option.getLongOpt(), option.getValue());
            }
        }
        VCS vcs = new VCS(properties.getProperty("project-path"));
        BrowserController browserController = new BrowserController(numberOfBrowsers);
        TimeLapseGenerator timeLapseGenerator = new TimeLapseGenerator();
        timeLapseGenerator.generateTimelapse(properties, vcs, browserController);
    }

}
