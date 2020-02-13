package org.refactoringminer;

import gr.uom.java.xmi.UMLModel;
import gr.uom.java.xmi.UMLModelASTReader;
import gr.uom.java.xmi.diff.UMLModelDiff;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringMinerTimedOutException;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        try {
            File rootFolder1 = new File("/Users/symbolk/.mergebot/repos/IntelliMerge_mergebot/smart_commit/base/");
            File rootFolder2 = new File("/Users/symbolk/.mergebot/repos/IntelliMerge_mergebot/smart_commit/current/");

            List<String> filePaths1 = listAllJavaFilePaths(rootFolder1.getAbsolutePath());
            List<String> filePaths2 = listAllJavaFilePaths(rootFolder2.getAbsolutePath());
            GitHistoryRefactoringMinerImpl miner = new GitHistoryRefactoringMinerImpl();

            UMLModel model1 = new UMLModelASTReader(rootFolder1, filePaths1, miner.repositoryDirectories(rootFolder1)).getUmlModel();
            UMLModel model2 = new UMLModelASTReader(rootFolder2, filePaths2, miner.repositoryDirectories(rootFolder2)).getUmlModel();
            UMLModelDiff modelDiff = model1.diff(model2);

            List<Refactoring> refactorings = modelDiff.getRefactorings();

            for (Refactoring refactoring : refactorings) {
                System.out.println(refactoring.getName());
                System.out.println(refactoring.leftSide());
                System.out.println(refactoring.rightSide());
            }
        } catch (RefactoringMinerTimedOutException e) {
            e.printStackTrace();
        }
    }

    private static List<String> listAllJavaFilePaths(String dir) {
        List<String> result = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(Paths.get(dir))) {
            result = walk.filter(Files::isRegularFile).map(x -> x.toString()).filter(f -> f.endsWith(".java")).map(s -> s.substring(dir.length())).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
