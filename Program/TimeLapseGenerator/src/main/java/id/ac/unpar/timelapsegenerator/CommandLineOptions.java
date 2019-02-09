/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.unpar.timelapsegenerator;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author user
 */
public class CommandLineOptions {

    private Options options;
    private CommandLineParser parser;
    private CommandLine commandLine;

    public CommandLineOptions(String[] args) throws ParseException {
        this.options = new Options();
        ArrayList<Option> listOption = new ArrayList<Option>();

        listOption.add(Option.builder().longOpt("capture-url").argName("url").hasArg().desc("link yang akan di capture").build());
        listOption.add(Option.builder().longOpt("seconds-per-commit").argName("seconds").hasArg().desc("durasi satu commit").build());
        listOption.add(Option.builder().longOpt("project-path").argName("path").hasArg().desc("path proyek perangkat lunak").build());
        listOption.add(Option.builder().longOpt("before-capture").argName("script").hasArg().desc("php script yang dijalankan sebelum melakukan screenshot").build());
        listOption.add(Option.builder().longOpt("start-commit").argName("commit id").hasArg().desc("commit id awal untuk memangkitkan animasi").build());
        listOption.add(Option.builder().longOpt("stop-commit").argName("commit id").hasArg().desc("commit id akhir untuk memangkitkan animasi").build());
        listOption.add(Option.builder().longOpt("title").argName("title").hasArg().desc("judul proyek yang akan ditampilkan di pojok kiri bawah").build());
        listOption.add(Option.builder().longOpt("logo").argName("image path").hasArg().desc("logo yang akan ditampilkan di pojok kanan bawah").build());

        for (int i = 0; i < listOption.size(); i++) {
            this.options.addOption(listOption.get(i));
        }
        this.parser = new DefaultParser();
        this.commandLine = parser.parse(this.options, args);
    }

    public String getOptionValue(String optionName) {
        return this.commandLine.getOptionValue(optionName);
    }

    public void runScript() throws IOException, InterruptedException {
            String argument = String.format("php %s", getOptionValue("before-capture"));
            Process process = Runtime.getRuntime().exec(argument);
            process.waitFor();
    }
}
