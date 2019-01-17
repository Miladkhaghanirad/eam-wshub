package ch.cern.cmms.wshub.entities;

import java.util.LinkedList;

public class DocInfo {
	private String workOrderNumber;
	private LinkedList<String> docCodes;
	private LinkedList<String> fileNames;
	private String context;
	
	public DocInfo() {
		docCodes = new LinkedList<String>();
		fileNames = new LinkedList<String>();
	}
	
	public String getWorkOrderNumber() {
		return workOrderNumber;
	}
	public void setWorkOrderNumber(String workOrderNumber) {
		this.workOrderNumber = workOrderNumber;
	}

	public LinkedList<String> getDocCodes() {
		return docCodes;
	}

	public LinkedList<String> getFileNames() {
		return fileNames;
	}

	@Override
	public String toString() {
		return "DocInfo ["
				+ (workOrderNumber != null ? "workOrderNumber="
						+ workOrderNumber + ", " : "")
				+ (docCodes != null ? "docCodes=" + docCodes + ", " : "")
				+ (fileNames != null ? "fileNames=" + fileNames : "") + "]";
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	
	

	
}
