package ch.cern.cmms.wshub.beans;

import java.util.List;

import javax.ejb.Local;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import ch.cern.eam.wshub.core.client.InforContext;
import ch.cern.eam.wshub.core.services.comments.entities.Comment;
import ch.cern.eam.wshub.core.services.entities.*;
import ch.cern.eam.wshub.core.services.equipment.entities.*;
import ch.cern.eam.wshub.core.services.material.entities.*;
import ch.cern.eam.wshub.core.services.workorders.entities.*;
import ch.cern.eam.wshub.core.services.entities.BatchResponse;
import ch.cern.cmms.wshub.entities.AsyncExecution;
import ch.cern.eam.wshub.core.services.entities.CustomField;
import ch.cern.eam.wshub.core.services.grids.entities.GridDDSpyFieldsResult;
import ch.cern.eam.wshub.core.services.grids.entities.GridDataspy;
import ch.cern.eam.wshub.core.services.grids.entities.GridMetadataRequestResult;
import ch.cern.eam.wshub.core.services.grids.entities.GridRequest;
import ch.cern.eam.wshub.core.services.grids.entities.GridRequestResult;
import ch.cern.eam.wshub.core.services.documents.entities.InforDocument;
import ch.cern.eam.wshub.core.tools.InforException;
import ch.cern.eam.wshub.core.services.workorders.entities.Employee;
import ch.cern.eam.wshub.core.services.workorders.entities.Activity;
import ch.cern.eam.wshub.core.services.workorders.entities.Aspect;
import ch.cern.eam.wshub.core.services.workorders.entities.InforCaseTask;
import ch.cern.eam.wshub.core.services.workorders.entities.TaskplanCheckList;
import ch.cern.eam.wshub.core.services.workorders.entities.WorkOrderActivityCheckList;

@Local
@WebService(targetNamespace = "http://cern.ch/cmms/infor/wshub", name = "InforWSPortType")
public interface WSHub {

	//
	// DOCUMENTS
	//
	List<InforDocument> readInforDocuments(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			String entity, String objectCode)
			throws InforException;

	String createInforDocumentAssociation(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			String document, String entity, String objectCode)
			throws InforException;

	String createInforDocument(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			InforDocument inforDocument)
			throws InforException;

	String createInforDocumentAndAssociation(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			InforDocument inforDocument, String entity, String objectCode)
			throws InforException;

	//
	// WORK
	//
	@WebResult(name = "workOrder")
	WorkOrder readWorkOrder(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "number", header = false) String number)
		throws InforException;



	@WebResult(name = "workOrder")
	WorkOrder readStandardWorkOrder(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "workOrder") WorkOrder workorder)
		throws InforException;

	@WebResult(name = "workOrderNumber")
	String updateWorkOrder(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "workOrder") WorkOrder workorder)
			throws InforException;

	@WebResult(name = "workOrderNumber")
	String createWorkOrder(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "workOrder") WorkOrder workorder)
			throws InforException;

	@WebResult(name = "workOrderNumber")
	BatchResponse<String> createWorkOrderBatch(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "workOrder") List<WorkOrder> workorder)
			throws InforException;


	@WebResult(name = "workOrder")
	BatchResponse<WorkOrder> readWorkOrderBatch(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "number", header = false) List<String> number)
			throws InforException;

	@WebResult(name = "workOrderNumber")
	BatchResponse<String> updateWorkOrderBatch(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "workOrder") List<WorkOrder> workorder)
			throws InforException;

	String deleteWorkOrder(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "workOrderNumber") String workorderNumber)
			throws InforException;

	String createLaborBooking(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "laborBooking") LaborBooking laborBookingParam)
			throws InforException;

	String createActivity(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "activity") Activity activityParam)
			throws InforException;

	String updateActivity(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "activity") Activity activityParam)
			throws InforException;

	Activity[] readActivities(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "workOrderNumber") String workOrderNumber)
			throws InforException;

	List<LaborBooking> readBookedLabor(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "workOrderNumber") String workOrderNumber)
			throws InforException;

	String createTaskPlan(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "taskPlan") TaskPlan taskPlan)
			throws InforException;

	WorkOrderActivityCheckList[] readWorkOrderChecklists(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "workOrderChecklist") Activity activity)
			throws InforException;

	String updateWorkOrderChecklists(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "workOrderChecklist") WorkOrderActivityCheckList WorkOrderChecklist)
			throws InforException;

	String createRouteEquipment(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "routeEquipment") RouteEquipment routeEquipment)
			throws InforException;

	String deleteRouteEquipment(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "routeEquipment") RouteEquipment routeEquipment)
			throws InforException;

	//
	// PURCHASE ORDER
	//
	@WebResult(name = "purchaseOrderCode")
	String updatePurchaseOrder(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "PurchaseOrder") PurchaseOrder purchaseOrderParam)
			throws InforException;

	//
	// CASE MANAGEMENT
	//
	@WebResult(name = "case")
	InforCase readCase(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "caseID") String caseID)
			throws InforException;

	@WebResult(name = "caseCode")
	String createCase(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "InforCase") InforCase caseMT)
			throws InforException;

	@WebResult(name = "caseCode")
	String updateCase(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "InforCase") InforCase caseMT)
			throws InforException;

	@WebResult(name = "caseCode")
	String deleteCase(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "caseID") String caseID)
			throws InforException;

	//
	// CASE TASK MANAGEMENT
	//
	@WebResult(name = "caseTaskCode")
	InforCaseTask readCaseTask(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "taskID") String caseTaskID)
			throws InforException;

	@WebResult(name = "caseTaskCode")
	String createCaseTask(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "InforCaseTask") InforCaseTask caseTaskMT)
			throws InforException;

	@WebResult(name = "caseTaskCode")
	String updateCaseTask(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "InforCaseTask") InforCaseTask caseTaskMT)
			throws InforException;

	@WebResult(name = "caseTaskCode")
	String deleteCaseTask(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "caseTaskID") String caseTaskID)
			throws InforException;


	//
	//
	//
	@WebResult(name = "meterCode")
	String createMeterReading(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "MeterReading") MeterReading meterReading)
			throws InforException;

	String createWorkOrderAdditionalCost(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "workOrderAddCosts") WorkOrderAdditionalCosts workOrderAddCostsParam)
			throws InforException;

	String addWorkOrderPart(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "workOrderPart") WorkOrderPart workOrderPart)
			throws InforException;

	String createMatarialList(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "materialList") MaterialList materialList)
			throws InforException;

	String updateWOStatus(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "workOrder") WorkOrder workOrder)
			throws InforException;

	String createTaskplanChecklist(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "taskPlanChecklist") TaskplanCheckList taskChecklist)
			throws InforException;

	//
	// InspectionService
	//
	@WebResult(name = "status")
	String createAspect(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "aspect") Aspect aspect)
			throws InforException;

	//
	// COMMENTS
	//
	String createComment(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "comment") Comment commentParam)
			throws InforException;

	String updateComment(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "comment") Comment commentParam)
			throws InforException;

	@WebResult(name = "comments")
	Comment[] readComments(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "comment") Comment commentParam)
			throws InforException;

	//
	// EQUIPMENT
	//
	String updateEquipment(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "equipment") Equipment equipmentParam)
			throws InforException;

	@WebResult(name = "equipmentCode")
	String createEquipment(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "equipment") Equipment equipmentParam)
			throws InforException;

	Equipment readEquipment(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "equipment") String equipmentCode)
			throws InforException;

	String deleteEquipment(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "equipment") String equipmentCode)
			throws InforException;

	@WebResult(name = "equipmentCode")
	BatchResponse<String> createEquipmentBatch(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "equipment") List<Equipment> workorder)
			throws InforException;


	BatchResponse<Equipment> readEquipmentBatch(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "equipmentList", header = false) List<String> equipmentList)
			throws InforException;

	BatchResponse<String> updateEquipmentBatch(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "equipmentList") List<Equipment> equipmentList)
			throws InforException;

	@WebResult(name = "linearReferenceID")
	String createEquipmentLinearReference(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "linearReference") LinearReference linearReference)
			throws InforException;

	@WebResult(name = "linearReferenceID")
	String updateEquipmentLinearReference(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "linearReference") LinearReference linearReference)
			throws InforException;

	@WebResult(name = "linearReferenceID")
	String deleteEquipmentLinearReference(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "linearReferenceID") String linearReferenceID)
			throws InforException;

	@WebResult(name = "status")
	String addEquipmentToStructure(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "equipmentStructure") EquipmentStructure equipmentStructure)
			throws InforException;

	@WebResult(name = "status")
	String updateEquipmentStructure(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "equipmentStructure") EquipmentStructure equipmentStructure)
			throws InforException;

	@WebResult(name = "status")
	String removeEquipmentFromStructure(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "equipmentStructure") EquipmentStructure equipmentStructure)
			throws InforException;

	String createEquipmentWarrantyCoverage(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "equipmentWarranty") EquipmentWarranty equipmentWarrantyParam)
			throws InforException;

	String updateEquipmentWarrantyCoverage(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "equipmentWarranty") EquipmentWarranty equipmentWarrantyParam)
			throws InforException;

	String createEquipmentPMSchedule(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "equipmentPMSchedule") EquipmentPMSchedule pmSchedule)
			throws InforException;

	String deleteEquipmentPMSchedule(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "equipmentPMSchedule") EquipmentPMSchedule pmSchedule)
			throws InforException;

	String updateEquipmentPMSchedule(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "equipmentPMSchedule") EquipmentPMSchedule pmSchedule)
			throws InforException;

	String createEquipmentDepreciation(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "equipmentDepreciation") EquipmentDepreciation equipmentDepreciation)
			throws InforException;

	String updateEquipmentDepreciation(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "equipmentDepreciation") EquipmentDepreciation equipmentDepreciation)
			throws InforException;

	String createEquipmentCampaign(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "equipmentCampaign") EquipmentCampaign equipmentCampaign)
			throws InforException;

	//
	// MATERIAL MANAGEMENT
	//
	String createPart(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "part") Part partParam)
			throws InforException;

	String updatePart(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "part") Part partParam)
			throws InforException;

	Part readPart(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "part") String partCode)
			throws InforException;

	String deletePart(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "partCode") String partCode)
			throws InforException;

	String createIssueReturnPartTransaction(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "issueReturnTransaction") IssueReturnPartTransaction issueReturnPartTransaction)
			throws InforException;

	String addPartStore(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "partStore") PartStore partStoreParam)
			throws InforException;

	String updatePartStore(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "partStore") PartStore partStoreParam)
			throws InforException;

	String addPartSupplier(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "partSupplier") PartSupplier partSupplierParam)
			throws InforException;

	String addPartStock(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "partStock") PartStock partStockParam)
			throws InforException;

	String updatePartStock(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "partStock") PartStock partStockParam)
			throws InforException;

	String addPartManufacturer(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "partManufacturer") PartManufacturer partManufacturerParam)
			throws InforException;

	String updatePartManufacturer(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "partManufacturer") PartManufacturer partManufacturerParam)
			throws InforException;

	String deletePartManufacturer(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "partManufacturer") PartManufacturer partManufacturerParam)
			throws InforException;

	@WebResult(name = "associationID")
	String createPartAssociation(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "partAssociation") PartAssociation partAssociation)
			throws InforException;

	@WebResult(name = "status")
	String deletePartAssociation(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "partAssociation") PartAssociation partAssociation)
			throws InforException;

	@WebResult(name = "partManufacturers")
	PartManufacturer[] readPartManufacturers(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "partNumber") String partCode)
			throws InforException;

	@WebResult(name = "result")
	String createPartSubstitute(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "partSubstitute") PartSubstitute partSubstitute)
			throws InforException;

	@WebResult(name = "result")
	String createStoreBin(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "bin") Bin bin)
			throws InforException;

	//
	//
	//
	String executeAsync(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "asyncExecution") AsyncExecution asyncExecution)
			throws InforException;

	//
	// Base
	//

	@WebResult(name = "customFields")
	GridRequestResult getGridResult(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "gridRequest") GridRequest gridRequest)
			throws InforException;

	@WebResult(name = "gridMetadata")
	GridMetadataRequestResult getGridMetadata(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "gridCode") String gridCode,
			@WebParam(name = "viewType") String viewType,
			@WebParam(name = "language") String language)
			throws InforException;

	@WebResult(name = "gridDataspy")
	GridDataspy getDefaultDataspy(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "gridCode") String gridCode,
			@WebParam(name = "viewType") String viewType)
			throws InforException;

	@WebResult(name = "ddspyFields")
	GridDDSpyFieldsResult getDDspyFields(@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "gridCode") String gridCode,
			@WebParam(name = "viewType") String viewType, @WebParam(name = "ddSpyId") String ddSpyId,
			@WebParam(name = "language") String languagecontext)
			throws InforException;

	//
	// GRID
	//
	@WebResult(name = "customFields")
	CustomField[] readMTCustomFields(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "entityCode") String entity,
			@WebParam(name = "classCode") String inforClass)
			throws InforException;







	//
	// USER SETUP CRUD
	//
	@WebResult(name = "user")
	EAMUser readUserSetup(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "userCode") String userCode)
			throws InforException;

	@WebResult(name = "userCode")
	String createUserSetup(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "user") EAMUser user)
			throws InforException;

	@WebResult(name = "userCode")
	String updateUserSetup(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "user") EAMUser caseMT)
			throws InforException;

	@WebResult(name = "userCode")
	String deleteUserSetup(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "userCode") String userCode)
			throws InforException;


	//
	// EMPLOYEES
	//

	@WebResult(name = "sessionID")
	String login(
			@WebParam(name = "inforContext", header = true) InforContext inforContext, String useCode)
			throws InforException;

	@WebResult(name = "employee")
	Employee readEmployee(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "employeeCode") String employeeCode)
			throws InforException;

	@WebResult(name = "employeeCode")
	String createEmployee(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "employee") Employee employee)
			throws InforException;

	@WebResult(name = "employeeCode")
	String updateEmployee(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "employee") Employee employee)
			throws InforException;

	@WebResult(name = "employeeCode")
	String deleteEmployee(
			@WebParam(name = "inforContext", header = true) InforContext inforContext,
			@WebParam(name = "employeeCode") String employeeCode)
			throws InforException;



}
