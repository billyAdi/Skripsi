/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.unpar.timelapsegenerator;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.cli.*;

/**
 *
 * @author user
 */
public class CommandLineOptions {

    private Options options;
    private ArrayList<Option> listOption;
    private CommandLineParser parser;
    private CommandLine commandLine;
    private String[] cmdArguments;
    
    Runtime rt;
    Process proc;

    public CommandLineOptions(String[] args) throws ParseException {
        this.options = new Options();
        this.listOption = new ArrayList<Option>();
        this.createOptions();
        for (int i = 0; i < listOption.size(); i++) {
            this.options.addOption(listOption.get(i));
        }
        this.parser = new DefaultParser();
        this.commandLine = parser.parse(this.options, args);
        
        rt = Runtime.getRuntime();
         
        this.cmdArguments = new String[3];
        cmdArguments[0] = "cmd.exe";
        cmdArguments[1] = "/C";
        cmdArguments[2] = "php " + getOptionValue("before-capture");//belum menangani kalo before-capture ga dimasukin

    }

    public String getOptionValue(String optionName) {
        String value = "";
        if (this.commandLine.hasOption(optionName)) {
            value = this.commandLine.getOptionValue(optionName);
        } else {
            value = "no input";
        }
        return value;
    }

    public void createOptions() {
        this.listOption.add(Option.builder().longOpt("capture-url").argName("url").hasArg().desc("link yang akan di capture").build());
        this.listOption.add(Option.builder().longOpt("seconds-per-commit").argName("seconds").hasArg().desc("durasi satu commit").build());
        this.listOption.add(Option.builder().longOpt("project-path").argName("path").hasArg().desc("path proyek perangkat lunak").build());
        this.listOption.add(Option.builder().longOpt("before-capture").argName("script").hasArg().desc("php script yang dijalankan sebelum melakukan screenshot").build());
    }

    public void runScript() throws IOException, InterruptedException {
        proc = rt.exec(cmdArguments);
        proc.waitFor();
    }
}
