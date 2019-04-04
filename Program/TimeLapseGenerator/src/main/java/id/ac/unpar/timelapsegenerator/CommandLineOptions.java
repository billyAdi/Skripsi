package id.ac.unpar.timelapsegenerator;

import java.util.Properties;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Kelas ini berfungsi untuk menyimpan semua Option yang terdapat pada program
 * dan melakukan parsing argumen Command Line Option.
 *
 * @author Billy Adiwijaya
 */
public class CommandLineOptions {

    private final CommandLine commandLine;

    /**
     * Constructor dari kelas ini. Berfungsi untuk menentukan Option yang
     * terdapat pada program dan melakukan parsing argumen Command Line.
     *
     * @param args merupakan argumen Command Line Option yang didapatkan dari
     * kelas Main.
     * @throws ParseException jika terjadi masalah saat melakukan parsing atau
     * jumlah argumen capture-url lebih dari 4.
     */
    public CommandLineOptions(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();

        Options options = new Options();
        options.addOption(Option.builder().required().hasArgs().longOpt("capture-url").argName("url").desc("link yang akan di capture").build());
        options.addOption(Option.builder().required().longOpt("project-path").argName("path").hasArg().desc("path proyek perangkat lunak").build());
        options.addOption(Option.builder().longOpt("seconds-per-commit").argName("seconds").hasArg().desc("durasi satu commit").build());
        options.addOption(Option.builder().longOpt("before-capture").argName("terminal command").hasArg().desc("terminal command yang dijalankan sebelum melakukan screenshot").build());
        options.addOption(Option.builder().longOpt("start-commit").argName("commit id").hasArg().desc("commit id awal untuk memangkitkan animasi").build());
        options.addOption(Option.builder().longOpt("stop-commit").argName("commit id").hasArg().desc("commit id akhir untuk memangkitkan animasi").build());
        options.addOption(Option.builder().longOpt("title").argName("title").hasArg().desc("judul proyek yang akan ditampilkan di pojok kiri bawah").build());
        options.addOption(Option.builder().longOpt("logo").argName("image path").hasArg().desc("logo yang akan ditampilkan di pojok kanan bawah").build());

        this.commandLine = parser.parse(options, args);
        if (this.commandLine.getOptionValues("capture-url").length > 4) {
            throw new ParseException("Jumlah url yang akan dicapture maksimal 4");
        }
    }

    /**
     * Method ini berfungsi untuk mengembalikan Option yang sudah diparsing.
     *
     * @return Option yang sudah diparsing berupa objek dengan tipe Properties.
     */
    public Properties getParsedOptions() {
        Properties properties = new Properties();
        for (Option option : this.commandLine.getOptions()) {
            if (option.getLongOpt().equals("capture-url")) {
                String[] values = option.getValues();
                String value = values[0];

                for (int i = 1; i < values.length; i++) {
                    value = value + ";" + values[i];
                }
                properties.setProperty(option.getLongOpt(), value);
            } else {
                properties.setProperty(option.getLongOpt(), option.getValue());
            }
        }
        return properties;
    }
}
