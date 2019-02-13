package org.refactoringminer.api;

import java.io.Serializable;
import java.util.List;

public interface Refactoring extends Serializable {

	public RefactoringType getRefactoringType();
	
	public String getName();

	public String toString();

	public String getProcessedClassName();

	public String getProcessedFilePath();
	
	public List<String> getInvolvedClassesBeforeRefactoring();
	
	public List<String> getInvolvedClassesAfterRefactoring();
}