package detect.refactoring.method;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.api.GitService;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringHandler;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.GitServiceImpl;

public class ExtractMethodPerCommitModule {
	
	
public static void main(String[] args) throws IOException {
		
		GitService gitService = new GitServiceImpl();
		GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();
        File historicProject = new File("path\\projectName_ExtractHistoric.txt");
		
        if (!historicProject.exists()) {
			historicProject.createNewFile();
		}
        
        FileWriter fw = new FileWriter(historicProject.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        List<String> historicList = new ArrayList<>();
        Repository repo;
        
		try {
			repo = gitService.openRepository("path_project");
			     miner.detectAtCommit(repo, "commitHash", new RefactoringHandler() {
			
				  public void handle(RevCommit commitData, List<Refactoring> refactorings) {
	 		
					  System.out.println(refactorings.size());
						for (Refactoring ref : refactorings) {
							
							
							String historicRefactoring = ref.toString();
							if(historicRefactoring != null){
								historicList.add("Commit:"+ commitData.getId().getName());
								historicList.add(historicRefactoring);
							}
							
						}
				  }
				});
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(historicList.size());
		for(String historic : historicList){
			try{
				bw.write(historic);
				bw.newLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		bw.close();
	}
		

}
