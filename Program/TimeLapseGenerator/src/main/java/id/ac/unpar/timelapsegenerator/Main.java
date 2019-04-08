package id.ac.unpar.timelapsegenerator;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import javax.imageio.ImageIO;
import org.eclipse.jgit.api.errors.GitAPIException;

/**
 * @author Billy Adiwijaya
 */
public class Main {

    public static void main(String[] args) {
        CommandLineOptions commandLineOptions = null;
        try {
            commandLineOptions = new CommandLineOptions(args);
        } catch (Exception e) {
            System.out.println("Animasi timelapse gagal dibuat");
            System.out.println(e.getMessage());
            System.exit(0);
        }
        Properties properties = commandLineOptions.getParsedOptions();
        int numberOfBrowsers = properties.getProperty("capture-url").split(";").length;

        VCS vcs = null;
        try {
            vcs = new VCS(properties.getProperty("project-path"));
            if (properties.getProperty("seconds-per-commit") != null) {
                if (Double.parseDouble(properties.getProperty("seconds-per-commit")) <= 0) {
                    throw new Exception("Seconds per commit harus lebih besar dari 0");
                }
            }

            String[] captureURLs = properties.getProperty("capture-url").split(";");
            for (String captureURL : captureURLs) {
                URL url = new URL(captureURL);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                if (http.getResponseCode() == 404) {
                    throw new Exception("Capture url tidak valid");
                } 
            }

            if (properties.getProperty("logo") != null) {
                File file = new File(properties.getProperty("logo"));
                if (!file.exists() || ImageIO.read(new File(properties.getProperty("logo"))) == null) {
                    throw new Exception("Path gambar tidak valid");
                }
            }

            if (properties.getProperty("start-commit") != null) {
                if (properties.getProperty("start-commit").length() < 7 || properties.getProperty("start-commit").length() > 10) {
                    throw new Exception("Panjang commit ID awal harus berada di antara 7-10 karakter");
                }
                if (vcs.getCommitIndex(properties.getProperty("start-commit")) == -1) {
                    throw new Exception("Commit ID awal tidak ditemukan");
                }
            }

            if (properties.getProperty("stop-commit") != null) {
                if (properties.getProperty("stop-commit").length() < 7 || properties.getProperty("stop-commit").length() > 10) {
                    throw new Exception("Panjang commit ID akhir harus berada di antara 7-10 karakter");
                }
                if (vcs.getCommitIndex(properties.getProperty("stop-commit")) == -1) {
                    throw new Exception("Commit ID akhir tidak ditemukan");
                }
            }

            if (properties.getProperty("start-commit") != null && properties.getProperty("stop-commit") != null) {
                if (vcs.getCommitIndex(properties.getProperty("start-commit")) > vcs.getCommitIndex(properties.getProperty("stop-commit"))) {
                    throw new Exception("Commit ID awal dan akhir terbalik");
                } else if (vcs.getCommitIndex(properties.getProperty("start-commit")) == vcs.getCommitIndex(properties.getProperty("stop-commit"))) {
                    throw new Exception("Commit ID awal dan akhir tidak boleh sama");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Animasi timelapse gagal dibuat");
            System.out.println("Seconds per commit harus berupa bilangan riil atau bilangan bulat");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Animasi timelapse gagal dibuat");
            System.out.println(e.getMessage());
            System.exit(0);
        }

        BrowserController browserController = new BrowserController(numberOfBrowsers);
        TimeLapseGenerator timeLapseGenerator = new TimeLapseGenerator();
        try {
            timeLapseGenerator.generateTimelapse(properties, vcs, browserController);
        } catch (IOException e) {
            try {
                vcs.checkoutMaster();
            } catch (GitAPIException ex) {
            } finally {
                System.out.println("Animasi timelapse gagal dibuat");
                System.out.println("Terminal Command tidak valid");
                System.exit(0);
            }
        } catch (Exception e) {
            try {
                vcs.checkoutMaster();
            } catch (GitAPIException ex) {
            } finally {
                System.out.println("Animasi timelapse gagal dibuat");
                System.out.println(e.getMessage());
                System.exit(0);
            }
        }
        System.out.println("Animasi timelapse berhasil dibuat");
    }
}
