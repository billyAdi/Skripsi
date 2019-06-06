package id.ac.unpar.timelapsegenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 * Kelas ini berfungsi untuk berinteraksi dengan repositori perangkat lunak berbasis
 * web yang terekam oleh Git.
 *
 * @author Billy Adiwijaya
 */
public class VCS {

    private final Git git;
    private final List<String> commitIDs;

    /**
     * Constructor dari kelas ini. Berfungsi untuk menginisialisasi variabel git
     * dan mendapatkan seluruh commit history repositori perangkat lunak berbasis
     * web pada branch tertentu. Dimana branch tersebut diambil dari parameter
     * constructor.
     *
     * @param path merupakah path dari repositori perangkat lunak berbasis web.
     * @param branch nama branch yang digunakan untuk membangkitkan animasi.
     * @throws IOException jika path repositori tidak valid atau repositori tidak
     * bisa diakses.
     * @throws GitAPIException jika terjadi masalah saat melakukan operasi Git
     * Log.
     * @throws Exception jika branch tidak valid
     */
    public VCS(String path, String branch) throws IOException, GitAPIException, Exception {
        Repository repository = new FileRepository(path);
        if (repository.getRef("HEAD") == null) {
            throw new IOException("Path repositori tidak valid");
        }
        this.git = new Git(repository);
        List<Ref> refs = git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call();
        int refIdx = -1;
        for (int i = 0; i < refs.size(); i++) {
            if (refs.get(i).getName().contains(branch)) {
                refIdx = i;
                break;
            }
        }

        if (refIdx == -1) {
            throw new Exception("Branch tidak valid");
        }
        git.checkout().setName(refs.get(refIdx).getName()).call();
        Iterable<RevCommit> commits = git.log().call();
        this.commitIDs = new ArrayList<>();
        for (RevCommit commit : commits) {
            this.commitIDs.add(commit.getName());
        }
        Collections.reverse(commitIDs);
    }

    /**
     * Berfungsi untuk melakukan checkout ke commit tertentu.
     *
     * @param commitIndex indeks dari variabel commitIDs.
     * @throws GitAPIException jika terjadi masalah saat melakukan operasi Git
     * Checkout.
     */
    public void checkoutCommit(int commitIndex) throws GitAPIException {
        this.git.checkout().setName(this.commitIDs.get(commitIndex)).call();
    }

    /**
     * Berfungsi untuk melakukan checkout ke commit terakhir yang ada di branch master.
     *
     * @throws GitAPIException jika terjadi masalah saat melakukan operasi Git
     * Checkout.
     */
    public void checkoutMaster() throws GitAPIException {
        this.git.checkout().setName("master").call();
    }

    /**
     * Berfungsi untuk melakukan operasi Git Reset. Operasi ini menghapus
     * perubahan pada working directory dan staging area di commit tertentu.
     *
     * @throws GitAPIException jika terjadi masalah saat melakukan operasi Git
     * Reset.
     */
    public void hardReset() throws GitAPIException {
        this.git.reset().setMode(ResetType.HARD).call();
    }

    /**
     * Berfungsi untuk mendapatkan jumlah commit.
     *
     * @return jumlah commit.
     */
    public int getNumberOfCommit() {
        return this.commitIDs.size();
    }

    /**
     * Berfungsi untuk mendapatkan index dari variabel commitID.
     *
     * @param commitID merupakan commit ID yang dicari indeksnya.
     * @return indeks dari variabel commitIDs.
     */
    public int getCommitIndex(String commitID) {
        int result = -1;
        for (int i = 0; i < this.commitIDs.size(); i++) {
            if (commitID.equals(this.commitIDs.get(i).substring(0, commitID.length()))) {
                result = i;
                break;
            }
        }
        return result;
    }
}
