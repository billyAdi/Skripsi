/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.unpar.timelapsegenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevWalk;

/**
 * Kelas ini berfungsi untuk berinteraksi pada proyek perangkat lunak berbasis web yang terekam oleh Git.
 * @author @author Billy Adiwijaya
 */
public class VCS {

    private Git git;
    private List<String> commitID;

     /**
     * Constructor dari kelas ini.
     * Berfungsi untuk menginisialisasi variabel git dan mendapatkan seluruh histori commit pada proyek perangkat lunak berbasis web
     * @param path merupakah path dari proyek perangkat lunak berbasis web.
     * throws IOException jika path tidak valid
     */
    public VCS(String path) throws IOException {
        Repository repository = new FileRepository(path);
        this.git = new Git(repository);
        RevWalk revWalk = new RevWalk(repository);
        revWalk.markStart(revWalk.parseCommit(repository.resolve(Constants.HEAD)));
        revWalk.sort(RevSort.REVERSE);

        this.commitID = new ArrayList<String>();
        for (RevCommit commit : revWalk) {
            this.commitID.add(commit.getName().substring(0, 7));
        }
    }

    /**
     * Berfungsi untuk melakukan checkout ke commit tertentu
     * @param indexCommit indeks dari variabel commitID.
     */
    public void checkoutCommit(int commitIndex) {
        try {
            this.git.checkout().setName(this.commitID.get(commitIndex)).call();
        } catch (GitAPIException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Berfungsi untuk melakukan checkout ke commit terakhir.  
     */
    public void checkoutMaster() {
        try {
            this.git.checkout().setName("master").call();
        } catch (GitAPIException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Berfungsi untuk melakukan operasi Git Reset.
     * Operasi ini menghapus perubahan pada working tree dan staging area di commit tertentu.
     */
    public void hardReset() {
        try {
            this.git.reset().setMode(ResetType.HARD).call();
        } catch (GitAPIException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * Berfungsi untuk mendapatkan jumlah commit.
     * @return jumlah commit.
     */
    public int getCommitSize(){
        return this.commitID.size();
    }
    
     /**
     * Berfungsi untuk mendapatkan indeks dari variabel commitID.
     * @param commitID merupakan Commit ID yang akan dicari indeksnya.
     * @return indeks dari variabel commitID.
     */
    public int getCommitIndex(String commitID){
        int result=-1;
        for (int i = 0; i < this.commitID.size(); i++) {
            if(commitID.equals(this.commitID.get(i))){
                result=i;
                break;
            }
        }
        return result;
    }
}
