/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.unpar.timelapsegenerator;

import java.io.IOException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Kelas ini berfungsi untuk menyimpan semua Option yang terdapat pada program dan melakukan parsing
 * argumen Command Line Option.
 * 
 * @author @author Billy Adiwijaya
 */
public class CommandLineOptions {
    private CommandLine commandLine;
    
    /**
     * Constructor dari kelas ini.
     * Berfungsi untuk menentukan Option yang terdapat pada program dan melakukan parsing argumen Command Line. 
     * @param args merupakan argumen Command Line Option yang didapatkan dari kelas Main.
     * @throws ParseException jika terjadi masalah saat melakukan parsing.
     */
    public CommandLineOptions(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();

        Options options = new Options();
        
        options.addOption(Option.builder().required().hasArgs().longOpt("capture-url").argName("url").desc("link yang akan di capture").build());
        options.addOption(Option.builder().required().longOpt("seconds-per-commit").argName("seconds").hasArg().desc("durasi satu commit").build());
        options.addOption(Option.builder().required().longOpt("project-path").argName("path").hasArg().desc("path proyek perangkat lunak").build());
        options.addOption(Option.builder().longOpt("before-capture").argName("script").hasArg().desc("php script yang dijalankan sebelum melakukan screenshot").build());
        options.addOption(Option.builder().longOpt("start-commit").argName("commit id").hasArg().desc("commit id awal untuk memangkitkan animasi").build());
        options.addOption(Option.builder().longOpt("stop-commit").argName("commit id").hasArg().desc("commit id akhir untuk memangkitkan animasi").build());
        options.addOption(Option.builder().longOpt("title").argName("title").hasArg().desc("judul proyek yang akan ditampilkan di pojok kiri bawah").build());
        options.addOption(Option.builder().longOpt("logo").argName("image path").hasArg().desc("logo yang akan ditampilkan di pojok kanan bawah").build());

        this.commandLine = parser.parse(options, args);
    }

    /**
     * Method ini berfungsi untuk mengembalikan option yang sudah diparsing
     * @return option yang sudah diparsing berupa array of Option
     */
    public Option[] getParsedOptions() {
        return this.commandLine.getOptions();
    }
}
