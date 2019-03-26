/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.unpar.timelapsegenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 * Kelas ini berfungsi untuk berinteraksi pada proyek perangkat lunak berbasis
 * web yang terekam oleh Git.
 *
 * @author Billy Adiwijaya
 */
public class VCS {

    private final Git git;
    private final List<String> commitIDs;

    /**
     * Constructor dari kelas ini. Berfungsi untuk menginisialisasi variabel git
     * dan mendapatkan seluruh histori commit pada proyek perangkat lunak
     * berbasis web. 
     *
     * @param path merupakah path dari proyek perangkat lunak berbasis web.
     * @throws IOException jika path proyek tidak valid atau repository tidak bisa diakses.
     * @throws GitAPIException jika terjadi masalah saat melakukan operasi Git Log.
     */
    public VCS(String path) throws IOException, GitAPIException   {
        Repository repository = new FileRepository(path);
        if(repository.getRef("HEAD")==null){
            throw new IOException("Path proyek tidak valid");
        }
        this.git = new Git(repository);
//        Iterable<RevCommit> commits=git.log().all().call();
        Iterable<RevCommit> commits=git.log().call();
        this.commitIDs = new ArrayList<>();
        for (RevCommit commit : commits) {
            this.commitIDs.add(commit.getName().substring(0, 7));
        }
        Collections.reverse(commitIDs);
    }

    /**
     * Berfungsi untuk melakukan checkout ke commit tertentu.
     *
     * @param commitIndex indeks dari variabel commitIDs.
     * @throws GitAPIException jika terjadi masalah saat melakukan operasi Git Checkout.
     */
    public void checkoutCommit(int commitIndex) throws GitAPIException {
        this.git.checkout().setName(this.commitIDs.get(commitIndex)).call();
    }

    /**
     * Berfungsi untuk melakukan checkout ke commit terakhir.
     *
     * @throws GitAPIException jika terjadi masalah saat melakukan operasi Git Checkout.
     */
    public void checkoutMaster() throws GitAPIException {
        this.git.checkout().setName("master").call();
    }

    /**
     * Berfungsi untuk melakukan operasi Git Reset. Operasi ini menghapus
     * perubahan pada working tree dan staging area di commit tertentu.
     *
     * @throws GitAPIException jika terjadi masalah saat melakukan operasi Git Reset.
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
     * @param commitID merupakan Commit ID yang akan dicari indeksnya.
     * @return indeks dari variabel commitIDs.
     */
    public int getCommitIndex(String commitID) {
        int result = -1;
        for (int i = 0; i < this.commitIDs.size(); i++) {
            if (commitID.equals(this.commitIDs.get(i))) {
                result = i;
                break;
            }
        }
        return result;
    }
}
