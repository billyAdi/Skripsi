/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.unpar.timelapsegenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
 *
 * @author user
 */
public class VCS {

    private Git git;
    private List<String> commitID;

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

    public void checkoutCommit(int indexCommit) throws GitAPIException {
        this.git.checkout().setName(this.commitID.get(indexCommit)).call();
    }

    public void checkoutMaster() throws GitAPIException {
        this.git.checkout().setName("master").call();
    }

    public void hardReset() throws GitAPIException {
        this.git.reset().setMode(ResetType.HARD).call();
    }
    
    public int getCommitSize(){
        return this.commitID.size();
    }

    public int getCommitIndex(String idCommit){
        int result=-1;
        for (int i = 0; i < this.commitID.size(); i++) {
            if(idCommit.equals(this.commitID.get(i))){
                result=i;
                break;
            }
        }
        return result;
    }
}
