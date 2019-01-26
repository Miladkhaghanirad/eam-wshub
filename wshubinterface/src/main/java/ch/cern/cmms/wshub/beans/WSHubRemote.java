package ch.cern.cmms.wshub.beans;

import java.util.List;

import javax.ejb.Remote;
import javax.jws.WebResult;

import ch.cern.eam.wshub.core.services.entities.Comment;
import ch.cern.eam.wshub.core.services.entities.Credentials;
import ch.cern.eam.wshub.core.services.entities.EAMUser;
import ch.cern.eam.wshub.core.services.entities.IssueReturnPartTransaction;
import ch.cern.eam.wshub.core.services.equipment.entities.EquipmentReplacement;
import ch.cern.eam.wshub.core.services.material.entities.Part;
import ch.cern.eam.wshub.core.services.workorders.entities.*;
import ch.cern.eam.wshub.core.services.entities.BatchResponse;
import ch.cern.eam.wshub.core.services.entities.CustomField;
import ch.cern.eam.wshub.core.services.equipment.entities.Equipment;
import ch.cern.eam.wshub.core.services.equipment.entities.EquipmentStructure;
import ch.cern.eam.wshub.core.services.grids.entities.GridDDSpyFieldsResult;
import ch.cern.eam.wshub.core.services.grids.entities.GridDataspy;
import ch.cern.eam.wshub.core.services.grids.entities.GridMetadataRequestResult;
import ch.cern.eam.wshub.core.services.grids.entities.GridRequest;
import ch.cern.eam.wshub.core.services.grids.entities.GridRequestResult;
import ch.cern.eam.wshub.core.services.documents.entities.InforDocument;
import ch.cern.eam.wshub.core.services.material.entities.PurchaseOrder;
import ch.cern.eam.wshub.core.tools.InforException;

@Remote
public interface WSHubRemote {

	GridRequestResult getGridResult(InforContext inforContext, GridRequest gridRequest) throws InforException;

	GridMetadataRequestResult getGridMetadata(InforContext inforContext, String gridCode, String viewType, String language) throws InforException;

	GridDataspy getDefaultDataspy(InforContext inforContext, String gridCode, String viewType) throws InforException;

	GridDDSpyFieldsResult getDDspyFields(InforContext inforContext, String gridCode, String viewType, String ddSpyId, String language) throws InforException;

	String createIssueReturnPartTransaction(InforContext inforContext, IssueReturnPartTransaction issueReturnPartTransaction) throws InforException;

	//
	// WORK
	//
	WorkOrder readWorkOrder(InforContext inforContext, String number) throws InforException;

	WorkOrder readStandardWorkOrder(InforContext inforContext, WorkOrder workorder) throws InforException;

	String updateWorkOrder(InforContext inforContext, WorkOrder workorder) throws InforException;

	String createWorkOrder(InforContext inforContext, WorkOrder workorder) throws InforException;

	BatchResponse<String> createWorkOrderBatch(InforContext inforContext, List<WorkOrder> workorder) throws InforException;

	String deleteWorkOrder(InforContext inforContext, String workorderNumber) throws InforException;

	CustomField[] readMTCustomFields(InforContext inforContext, String entity, String inforClass) throws InforException;

	WorkOrderActivityCheckList[] readWOActivityChecklists(InforContext inforContext, Activity activity) throws InforException;

	String updateWorkOrderChecklists(InforContext inforContext, WorkOrderActivityCheckList WorkOrderChecklist) throws InforException;

	String updateWOStatus(InforContext inforContext, WorkOrder workOrder) throws InforException;

	//
	// ACTIVITIES
	//
	String createActivity(InforContext inforContext, Activity activityParam) throws InforException;

	Activity[] readActivities(InforContext inforContext, String workOrderNumber) throws InforException;

	//
	// LABOR BOOKING
	//
	String createLaborBooking(InforContext inforContext, LaborBooking laborBookingParam) throws InforException;

	LaborBooking[] readBookedLabor(InforContext inforContext, String workOrderNumber) throws InforException;

	//
	// PURCHASE ORDER
	//
	String updatePurchaseOrder(InforContext inforContext, PurchaseOrder purchaseOrderParam) throws InforException;

	//
	// COMMENTS
	//
	String createComment(InforContext inforContext, Comment commentParam) throws InforException;

	String updateComment(InforContext inforContext, Comment commentParam) throws InforException;

	Comment[] readComments(InforContext inforContext, Comment commentParam) throws InforException;

	//
	// MATERIAL - CRUD
	//
	String createPart(InforContext inforContext, Part partParam) throws InforException;

	String updatePart(InforContext inforContext, Part partParam) throws InforException;

	Part readPart(InforContext inforContext, String partCode) throws InforException;

	String deletePart(InforContext inforContext, String partCode) throws InforException;

	//
	// EQUIPMENT - CRUD
	//
	String updateEquipment(InforContext inforContext, Equipment equipmentParam)
			throws InforException;

	String createEquipment(InforContext inforContext, Equipment equipmentParam)
			throws InforException;

	Equipment readEquipment(InforContext inforContext, String equipmentCode)
			throws InforException;

	String deleteEquipment(InforContext inforContext, String equipmentCode) throws InforException;

	//
	// EQUIPMENT STRUCTURE
	//

	String addEquipmentToStructure(InforContext inforContext, EquipmentStructure equipmentStructure) throws InforException;

	@WebResult(name = "status")
	String updateEquipmentStructure(InforContext inforContext, EquipmentStructure equipmentStructure) throws InforException;

	@WebResult(name = "status")
	String removeEquipmentFromStructure(InforContext inforContext, EquipmentStructure equipmentStructure) throws InforException;
	//
	// METER READING
	//
	String createMeterReading(InforContext inforContext, MeterReading meterReading) throws InforException;

	//
	// CASE MANAGEMENT
	//
	InforCase readCase(InforContext inforContext, String caseID) throws InforException;

	String createCase(InforContext inforContext, InforCase caseMT) throws InforException;

	String updateCase(InforContext inforContext, InforCase caseMT) throws InforException;

	String deleteCase(InforContext inforContext, String caseID) throws InforException;
	//
	// CASE TASK MANAGEMENT
	//
	InforCaseTask readCaseTask(InforContext inforContext, String caseTaskID) throws InforException;

	String createCaseTask(InforContext inforContext, InforCaseTask caseTaskMT) throws InforException;

	String updateCaseTask(InforContext inforContext, InforCaseTask caseTaskMT) throws InforException;

	String deleteCaseTask(InforContext inforContext, String caseTaskID) throws InforException;

	//
	// INFOR DOCUMENTS
	//
	List<InforDocument> readInforDocuments(InforContext inforContext, String entity, String objectCode) throws InforException;

	String createInforDocumentAssociation(InforContext inforContext, String document, String entity, String objectCode) throws InforException;

	String createInforDocument(InforContext inforContext, InforDocument inforDocument) throws InforException;

	String createInforDocumentAndAssociation(InforContext inforContext, InforDocument inforDocument, String entity, String objectCode) throws InforException;
	//
	// USER SETUP
	//
	EAMUser readUserSetup(InforContext inforContext, String userCode) throws InforException;

	String createUserSetup(InforContext inforContext, EAMUser user) throws InforException;

	String updateUserSetup(InforContext inforContext, EAMUser user) throws InforException;

	String deleteUserSetup(InforContext inforContext, String userCode) throws InforException;

	//
	// EMPLOYEES
	//

	Employee readEmployee(InforContext inforContext, String employeeCode) throws InforException;

	String createEmployee(InforContext inforContext, Employee employee) throws InforException;

	String updateEmployee(InforContext inforContext, Employee employee) throws InforException;

	String deleteEmployee(InforContext inforContext, String employeeCode) throws InforException;
	//
	// ROUTE EQUIPMENT
	//

	String createRouteEquipment(InforContext inforContext, RouteEquipment routeEquipment) throws InforException;

	String deleteRouteEquipment(InforContext inforContext, RouteEquipment routeEquipment) throws InforException;



}
