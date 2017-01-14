package detect.refactoring.method;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;
import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.api.GitService;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringHandler;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.GitServiceImpl;

public class RenameMineratorModule {

	
	
	public static void main(String[] args) throws IOException {
		
		GitService gitService = new GitServiceImpl();
		GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();
        File historicProject = new File("E:\\Projetos_Clip\\Method_Metrics\\Historic_Methods\\CSV_HISTORIC_V2\\RESULTS_RENAME_METHOD\\PainelAdmin_RenameHistoric.txt");
		
        if (!historicProject.exists()) {
			historicProject.createNewFile();
		}
        
        FileWriter fw = new FileWriter(historicProject.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        List<String> historicList = new ArrayList<>();
        Repository repo;
        
		try {
			repo = gitService.openRepository("E:\\Projetos_Clip\\Projects\\clip-admin-panel-v2");
			     miner.detectAll(repo, "master", new RefactoringHandler() {
			
				  public void handle(RevCommit commitData, List<Refactoring> refactorings) {
	 		
						for (Refactoring ref : refactorings) {
							
							String historicRefactoring = ref.toString();
							if(historicRefactoring != null && historicRefactoring.contains("Rename Method")){
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
		
	
	
	@Test
	public void shouldTestWriteFile() throws IOException{
		 File historicProject = new File("C:\\Users\\Ana Carla\\ProjetosAnalisados\\StatsHistoric\\androidRefactoring.txt");
			
	        if (!historicProject.exists()) {
				historicProject.createNewFile();
			}
	        
	        FileWriter fw = new FileWriter(historicProject.getAbsoluteFile());
	        BufferedWriter bw = new BufferedWriter(fw);
	        PrintWriter writer = new PrintWriter("C:\\Users\\Ana Carla\\ProjetosAnalisados\\android2.txt", "UTF-8");
	        for(int i=0; i<10; i++){
	        	bw.write("oi");
	        	writer.println("The first line");
				writer.println("The second line");
	        }
	   
	        bw.close();
	}	

	
}
