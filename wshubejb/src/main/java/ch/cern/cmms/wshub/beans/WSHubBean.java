package ch.cern.cmms.wshub.beans;

import ch.cern.eam.wshub.core.client.InforClient;
import ch.cern.eam.wshub.core.client.InforContext;
import ch.cern.eam.wshub.core.services.entities.*;
import ch.cern.eam.wshub.core.services.equipment.entities.*;
import ch.cern.eam.wshub.core.services.material.entities.*;
import ch.cern.eam.wshub.core.services.workorders.entities.*;
import ch.cern.eam.wshub.core.services.entities.BatchResponse;
import ch.cern.cmms.wshub.entities.AsyncExecution;
import ch.cern.eam.wshub.core.services.entities.CustomField;
import ch.cern.eam.wshub.core.services.equipment.entities.Equipment;
import ch.cern.eam.wshub.core.services.grids.entities.*;
import ch.cern.eam.wshub.core.services.documents.entities.InforDocument;
import ch.cern.cmms.wshub.interceptors.*;
import ch.cern.eam.wshub.core.services.material.entities.PartAssociation;
import ch.cern.eam.wshub.core.services.material.entities.PurchaseOrder;
import ch.cern.eam.wshub.core.tools.InforException;
import ch.cern.eam.wshub.core.services.workorders.entities.Employee;
import ch.cern.cmms.wshub.workorders.*;
import ch.cern.eam.wshub.core.services.workorders.entities.Activity;
import ch.cern.eam.wshub.core.services.workorders.entities.Aspect;
import ch.cern.eam.wshub.core.services.workorders.entities.InforCaseTask;
import ch.cern.eam.wshub.core.services.workorders.entities.TaskplanCheckList;
import ch.cern.eam.wshub.core.services.workorders.entities.WorkOrderActivityCheckList;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.ExcludeClassInterceptors;
import javax.interceptor.Interceptors;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import java.util.*;

@Stateless(name = "WSHub")
@TransactionAttribute(TransactionAttributeType.NEVER)
@Interceptors({ ValidationInterceptor.class })
@HandlerChain(file = "/handlers.xml")
@org.apache.cxf.interceptor.InInterceptors(interceptors = {"ch.cern.cmms.wshub.interceptors.SOAPActionCXFInInterceptor" })
@WebService(name = "InforWSPortType", portName = "InforWSPort", serviceName = "InforWSService", targetNamespace = "http://cern.ch/cmms/infor/wshub", endpointInterface = "ch.cern.cmms.wshub.beans.WSHub")
public class WSHubBean implements WSHub {

	@Inject
	private CaseTasks caseTasks;
	@Inject
	private MessageSender messageSender;
    @Inject
	private InforClient inforClient;
    
	//
	// WORK ORDERS CRUD
	//
	@Override
	public WorkOrder readWorkOrder(InforContext inforContext, String number) throws InforException {
		return inforClient.getWorkOrderService().readWorkOrder(inforContext, number);
	}

	@Override
	public WorkOrder readStandardWorkOrder(InforContext inforContext, WorkOrder workOrderParam) throws InforException {
		return inforClient.getWorkOrderService().readStandardWorkOrder(inforContext, workOrderParam);
	}

	@Override
	public String createWorkOrder(InforContext inforContext, WorkOrder workOrderParam) throws InforException {
		return inforClient.getWorkOrderService().createWorkOrder(inforContext, workOrderParam);
	}

	@Override
	public BatchResponse<String> createWorkOrderBatch(InforContext inforContext, List<WorkOrder> workOrderParam)  throws InforException {
		return inforClient.getWorkOrderService().createWorkOrderBatch(inforContext, workOrderParam);
	}

	@Override
	public BatchResponse<WorkOrder> readWorkOrderBatch(InforContext inforContext, List<String> numbers) {
		return inforClient.getWorkOrderService().readWorkOrderBatch(inforContext, numbers);
	}

	@Override
	public BatchResponse<String> updateWorkOrderBatch(InforContext inforContext, List<WorkOrder> workOrderParam) throws InforException {
		return inforClient.getWorkOrderService().updateWorkOrderBatch(inforContext, workOrderParam);
	}

	@Override
	public String updateWorkOrder(InforContext inforContext, WorkOrder workorderParam) throws InforException {
		return inforClient.getWorkOrderService().updateWorkOrder(inforContext, workorderParam);
	}

	@Override
	public String deleteWorkOrder(InforContext inforContext, String workOrderNumber) throws InforException {
		return inforClient.getWorkOrderService().deleteWorkOrder(inforContext, workOrderNumber);
	}

	@Override
	public String updateWOStatus(InforContext inforContext, WorkOrder workOrder) throws InforException {
		return inforClient.getWorkOrderService().updateWorkOrderStatus(inforContext, workOrder.getNumber(), workOrder.getStatusCode());
	}

	//
	// ACTIVITIES
	//
	@Override
	public String createActivity(InforContext inforContext, Activity activityParam) throws InforException {
		return inforClient.getLaborBookingService().createActivity(inforContext, activityParam);
	}

	@Override
	public String updateActivity(InforContext inforContext, Activity activityParam) throws InforException {
		return inforClient.getLaborBookingService().updateActivity(inforContext, activityParam);
	}

	@Override
	public Activity[] readActivities(InforContext inforContext, String workOrderNumber) throws InforException {
		return inforClient.getLaborBookingService().readActivities(inforContext, workOrderNumber);
	}

	//
	// LABOR BOOKING
	//
	@Override
	public LaborBooking[] readBookedLabor(InforContext inforContext, String workOrderNumber) throws InforException {
		return inforClient.getLaborBookingService().readLaborBookings(inforContext, workOrderNumber);
	}

	@Override
	public String createLaborBooking(InforContext inforContext, LaborBooking laborBookingParam) throws InforException {
		return inforClient.getLaborBookingService().createLaborBooking(inforContext, laborBookingParam);
	}

	//
	// PURCHASE ORDERS
	//
	@Override
	public String updatePurchaseOrder(InforContext inforContext, PurchaseOrder purchaseOrderParam) throws InforException {
		return  inforClient.getPurchaseOrdersService().updatePurchaseOrder(inforContext, purchaseOrderParam);
	}

	//
	// CASE MANAGEMENT
	//
	@Override
	public InforCase readCase(InforContext inforContext, String caseID) throws InforException {
		return inforClient.getCaseService().readCase(inforContext, caseID);
	}

	@Override
	public String createCase(InforContext inforContext, InforCase caseMT) throws InforException {
		return inforClient.getCaseService().createCase(inforContext, caseMT);
	}

	@Override
	public String updateCase(InforContext inforContext, InforCase caseMT) throws InforException {
		return inforClient.getCaseService().updateCase(inforContext, caseMT);
	}

	@Override
	public String deleteCase(InforContext inforContext, String caseID) throws InforException {
		return inforClient.getCaseService().deleteCase(inforContext, caseID);
	}

	//
	// CASE TASK MANAGEMENT
	//
	@Override
	public InforCaseTask readCaseTask(InforContext inforContext, String caseTaskID) throws InforException {
		return inforClient.getCaseTaskService().readCaseTask(inforContext, caseTaskID);
	}

	@Override
	public String createCaseTask(InforContext inforContext, InforCaseTask caseTaskMT) throws InforException {
		return inforClient.getCaseTaskService().createCaseTask(inforContext, caseTaskMT);
	}

	@Override
	public String updateCaseTask(InforContext inforContext, InforCaseTask caseTaskMT) throws InforException {
		return inforClient.getCaseTaskService().updateCaseTask(inforContext, caseTaskMT);
	}

	@Override
	public String deleteCaseTask(InforContext inforContext, String caseTaskID) throws InforException {
		return inforClient.getCaseTaskService().deleteCaseTask(inforContext, caseTaskID);
	}

	//
	// WORK ORDERS MISC
	//
	@Override
	public String createMeterReading(InforContext inforContext, MeterReading meterReading) throws InforException {
		return inforClient.getWorkOrderMiscService().createMeterReading(inforContext, meterReading);
	}

	@Override
	public String createWorkOrderAdditionalCost(InforContext inforContext, WorkOrderAdditionalCosts workOrderAddCostsParam) throws InforException {
		return inforClient.getWorkOrderMiscService().createWorkOrderAdditionalCost(inforContext, workOrderAddCostsParam);
	}

	@Override
	public String addWorkOrderPart(InforContext inforContext, WorkOrderPart workOrderPart) throws InforException {
		return inforClient.getWorkOrderMiscService().addWorkOrderPart(inforContext, workOrderPart);
	}

	@Override
	public String createMatarialList(InforContext inforContext, MaterialList materialList) throws InforException {
		return inforClient.getWorkOrderMiscService().createMaterialList(inforContext, materialList);
	}

	@Override
	public String createTaskplanChecklist(InforContext inforContext, TaskplanCheckList taskChecklist) throws InforException {
		return inforClient.getChecklistService().createTaskplanChecklist(inforContext, taskChecklist);
	}

	@Override
	public String createTaskPlan(InforContext inforContext, TaskPlan taskPlan) throws InforException {
		return inforClient.getWorkOrderMiscService().createTaskPlan(inforContext, taskPlan);
	}

	@Override
	public WorkOrderActivityCheckList[] readWorkOrderChecklists(InforContext inforContext, Activity activity) throws InforException {
		return inforClient.getChecklistService().readWorkOrderChecklists(inforContext, activity);
	}

	@Override
	public String updateWorkOrderChecklists(InforContext inforContext, WorkOrderActivityCheckList WorkOrderChecklist) throws InforException {
		return inforClient.getChecklistService().updateWorkOrderChecklist(inforContext, WorkOrderChecklist);
	}

	@Override
	public String createRouteEquipment(InforContext inforContext, RouteEquipment routeEquipment) throws InforException {
		return inforClient.getWorkOrderMiscService().createRouteEquipment(inforContext, routeEquipment);
	}

	@Override
	public String deleteRouteEquipment(InforContext inforContext, RouteEquipment routeEquipment) throws InforException {
		return inforClient.getWorkOrderMiscService().deleteRouteEquipment(inforContext, routeEquipment);
	}

	/*
	@MethodData(logOperation = "WO_ROUTEEQ_U")
	@Override
	public String syncRoutes(String routeEquipment) throws InforException {
		return inforClient.getWorkOrderMiscService().syncRoutes(routeEquipment, credentials, sessionID);
	}
	*/

	//
	// InspectionService
	//
	@Override
	public String createAspect(InforContext inforContext, Aspect aspect) throws InforException {
		return inforClient.getInspectionService().addAspect(inforContext, aspect);
	}

	//
	// COMMENTS
	//
	@Override
	public String createComment(InforContext inforContext, Comment commentParam) throws InforException {
		return inforClient.getCommentService().createComment(inforContext, commentParam);
	}

	@Override
	public String updateComment(InforContext inforContext, Comment commentParam) throws InforException {
		return inforClient.getCommentService().updateComment(inforContext, commentParam);
	}

	@Override
	public Comment[] readComments(InforContext inforContext, Comment commentParam) throws InforException {
		return inforClient.getCommentService().readComments(inforContext, commentParam.getEntityCode(), commentParam.getEntityKeyCode(), commentParam.getTypeCode());
	}

	//
	// EQUIPMENT - CRUD
	//
	@Override
	public String updateEquipment(InforContext inforContext, Equipment equipmentParam) throws InforException {
		return inforClient.getEquipmentFacadeService().updateEquipment(inforContext, equipmentParam);
	}

	@Override
	public String createEquipment(InforContext inforContext, Equipment equipmentParam) throws InforException {
		return inforClient.getEquipmentFacadeService().createEquipment(inforContext, equipmentParam);
	}

	@Override
	public Equipment readEquipment(InforContext inforContext, Equipment equipment) throws InforException {
		return inforClient.getEquipmentFacadeService().readEquipment(inforContext, equipment);
	}

	@Override
	public String deleteEquipment(InforContext inforContext, Equipment equipment) throws InforException {
		return inforClient.getEquipmentFacadeService().deleteEquipment(inforContext, equipment);
	}

	@Override
	public BatchResponse<String> createEquipmentBatch(InforContext inforContext, List<Equipment> equipmentList) throws InforException {
		return inforClient.getEquipmentFacadeService().createEquipmentBatch(inforContext, equipmentList);
	}

	@Override
	public BatchResponse<Equipment> readEquipmentBatch(InforContext inforContext, List<Equipment> equipmentList) {
		return inforClient.getEquipmentFacadeService().readEquipmentBatch(inforContext, equipmentList);
	}

	@Override
	public BatchResponse<String> updateEquipmentBatch(InforContext inforContext, List<Equipment> equipmentList) throws InforException {
		return inforClient.getEquipmentFacadeService().updateEquipmentBatch(inforContext, equipmentList);
	}

	//
	// EQUIPMENT LINEAR REFERENCES
	//
	@Override
	public String createEquipmentLinearReference(InforContext inforContext, LinearReference linearReference) throws InforException {
		return inforClient.getLinearReferenceService().createEquipmentLinearReference(inforContext, linearReference);
	}

	@Override
	public String updateEquipmentLinearReference(InforContext inforContext, LinearReference linearReference) throws InforException {
		return inforClient.getLinearReferenceService().updateEquipmentLinearReference(inforContext, linearReference);
	}

	@Override
	public String deleteEquipmentLinearReference(InforContext inforContext, String linearReferenceID) throws InforException {
		return inforClient.getLinearReferenceService().deleteEquipmentLinearReference(inforContext, linearReferenceID);
	}

	//
	// EQUIPMENT STRUCTURE
	//
	@Override
	public String addEquipmentToStructure(InforContext inforContext, EquipmentStructure equipmentStructure) throws InforException {
		return inforClient.getEquipmentStructureService().addEquipmentToStructure(inforContext, equipmentStructure);
	}

	@Override
	public String updateEquipmentStructure(InforContext inforContext, EquipmentStructure equipmentStructure) throws InforException {
		return inforClient.getEquipmentStructureService().updateEquipmentStructure(inforContext, equipmentStructure);
	}

	@Override
	public String removeEquipmentFromStructure(InforContext inforContext, EquipmentStructure equipmentStructure) throws InforException {
		return inforClient.getEquipmentStructureService().removeEquipmentFromStructure(inforContext, equipmentStructure);
	}

	//
	// EQUIPMENT WARRANTY COVERAGE
	//
	@Override
	public String createEquipmentWarrantyCoverage(InforContext inforContext, EquipmentWarranty equipmentWarrantyParam) throws InforException {
		return inforClient.getEquipmentWarrantyCoverageService().createEquipmentWarrantyCoverage(inforContext, equipmentWarrantyParam);
	}

	@Override
	public String updateEquipmentWarrantyCoverage(InforContext inforContext, EquipmentWarranty equipmentWarrantyParam) throws InforException {
		return inforClient.getEquipmentWarrantyCoverageService().updateEquipmentWarrantyCoverage(inforContext, equipmentWarrantyParam);
	}

	//
	// EQUIPMENT PM SCHEDULES
	//
	@Override
	public String createEquipmentPMSchedule(InforContext inforContext, EquipmentPMSchedule pmSchedule) throws InforException {
		return inforClient.getPmScheduleService().createEquipmentPMSchedule(inforContext, pmSchedule);
	}

	@Override
	public String deleteEquipmentPMSchedule(InforContext inforContext, EquipmentPMSchedule pmSchedule) throws InforException {
		return inforClient.getPmScheduleService().deleteEquipmentPMSchedule(inforContext, pmSchedule);
	}

	@Override
	public String updateEquipmentPMSchedule(InforContext inforContext, EquipmentPMSchedule pmSchedule) throws InforException {
		return inforClient.getPmScheduleService().updateEquipmentPMSchedule(inforContext, pmSchedule);
	}

	//
	// EQUIPMENT DEPRECIATION
	//
	@Override
	public String createEquipmentDepreciation(InforContext inforContext, EquipmentDepreciation equipmentDepreciation) throws InforException {
		return inforClient.getEquipmentOtherService().createEquipmentDepreciation(inforContext, equipmentDepreciation);
	}

	@Override
	public String updateEquipmentDepreciation(InforContext inforContext, EquipmentDepreciation equipmentDepreciation) throws InforException {
		return inforClient.getEquipmentOtherService().updateEquipmentDepreciation(inforContext, equipmentDepreciation);
	}
	//
	// EQUIPMENT OTHER
	//

	@Override
	public String createEquipmentCampaign(InforContext inforContext, EquipmentCampaign equipmentCampaign) throws InforException {
		return inforClient.getEquipmentOtherService().createEquipmentCampaign(inforContext, equipmentCampaign);
	}

	//
	// MATERIAL - CRUD
	//
	@Override
	public String createPart(InforContext inforContext, Part partParam) throws InforException {
		return inforClient.getPartService().createPart(inforContext, partParam);
	}

	@Override
	public String updatePart(InforContext inforContext, Part partParam) throws InforException {
		return inforClient.getPartService().updatePart(inforContext, partParam);
	}

	@Override
	public Part readPart(InforContext inforContext, String partCode) throws InforException {
		return inforClient.getPartService().readPart(inforContext, partCode);
	}

	@Override
	public String deletePart(InforContext inforContext, String partCode) throws InforException {
		return inforClient.getPartService().deletePart(inforContext, partCode);
	}

	@Override
	public String createIssueReturnPartTransaction(InforContext inforContext, IssueReturnPartTransaction issueReturnPartTransaction) throws InforException {
		return inforClient.getPartMiscService().createIssueReturnTransaction(inforContext, issueReturnPartTransaction);
	}

	@Override
	public String addPartStore(InforContext inforContext, PartStore partStoreParam) throws InforException {
		return inforClient.getPartStoreService().addPartStore(inforContext, partStoreParam);
	}

	@Override
	public String updatePartStore(InforContext inforContext, PartStore partStoreParam) throws InforException {
		return inforClient.getPartStoreService().updatePartStore(inforContext, partStoreParam);
	}

	@Override
	public String addPartSupplier(InforContext inforContext, PartSupplier partSupplierParam) throws InforException {
		return inforClient.getPartMiscService().addPartSupplier(inforContext, partSupplierParam);
	}

	@Override
	public String addPartStock(InforContext inforContext, PartStock partStockParam) throws InforException {
		return inforClient.getPartBinStockService().addPartStock(inforContext, partStockParam);
	}

	@Override
	public String updatePartStock(InforContext inforContext, PartStock partStockParam) throws InforException {
		return inforClient.getPartBinStockService().updatePartStock(inforContext, partStockParam);
	}

	//
	// PART MANUFACTURER
	//
	@Override
	public PartManufacturer[] readPartManufacturers(InforContext inforContext, String partCode) throws InforException {
		return inforClient.getPartMiscService().getPartManufacturers(inforContext, partCode);
	}

	@Override
	public String addPartManufacturer(InforContext inforContext, PartManufacturer partManufacturerParam)
			throws InforException {
		return inforClient.getPartManufacturerService().addPartManufacturer(inforContext, partManufacturerParam);
	}

	@Override
	public String updatePartManufacturer(InforContext inforContext, PartManufacturer partManufacturerParam) throws InforException {
		return inforClient.getPartManufacturerService().updatePartManufacturer(inforContext, partManufacturerParam);
	}

	@Override
	public String deletePartManufacturer(InforContext inforContext, PartManufacturer partManufacturerParam) throws InforException {
		return inforClient.getPartManufacturerService().deletePartManufacturer(inforContext, partManufacturerParam);
	}

	//
	// PART ASSOCIATION
	//
	@Override
	public String createPartAssociation(InforContext inforContext, PartAssociation partAssociation)
			throws InforException {
		return inforClient.getPartMiscService().createPartAssociation(inforContext, partAssociation);
	}

	@Override
	public String deletePartAssociation(InforContext inforContext, PartAssociation partAssociation)
			throws InforException {
		return inforClient.getPartMiscService().deletePartAssociation(inforContext, partAssociation);
	}

	//TODO
	/*
	@MethodData(logOperation = "PARTMANS_R")
	@Override
	public PartManufacturer[] readPartManufacturers(String partCode)
			throws InforException {
		return inforClient.getPartManufacturerService().readPartManufacturers(partCode, credentials, sessionID);
	}
	*/

	@Override
	public String createPartSubstitute(InforContext inforContext, PartSubstitute partSubstitute) throws InforException {
		return inforClient.getPartMiscService().createPartSubstitute(inforContext, partSubstitute);
	}

	@Override
	public String createStoreBin(InforContext inforContext, Bin bin) throws InforException {
		return inforClient.getPartMiscService().addStoreBin(inforContext, bin);
	}

	//
	// Async Execution
	//
	@ExcludeClassInterceptors
	@Override
	public String executeAsync(InforContext inforContext, AsyncExecution asyncExecution) throws InforException {
		return messageSender.executeAsync(inforContext, asyncExecution);
	}

	//
	//
	//
	@Override
	public String login(InforContext inforContext) throws InforException {
		return inforClient.getUserSetupService().login(inforContext);
	}

	@Override
	public CustomField[] readMTCustomFields(InforContext inforContext, String entity, String inforClass) throws InforException {
		return inforClient.getTools().getCustomFieldsTools().getMTCustomFields(inforContext, entity, inforClass);
	}

	//
	// GRIDS
	//
	@Override
	public GridRequestResult getGridResult(InforContext inforContext, GridRequest gridRequest) throws InforException {
		return inforClient.getGridsService().executeQuery(inforContext, gridRequest);
	}

	@Override
	public GridMetadataRequestResult getGridMetadata(InforContext inforContext, String gridCode, String viewType, String language) throws InforException {
		return inforClient.getGridsService().getGridMetadata(inforContext, gridCode, viewType, language);
	}

	@Override
	public GridDataspy getDefaultDataspy(InforContext inforContext, String gridCode, String viewType)
			throws InforException {
		return inforClient.getGridsService().getDefaultDataspy(inforContext, gridCode, viewType);
	}

	@Override
	public GridDDSpyFieldsResult getDDspyFields(InforContext inforContext, String gridCode, String viewType, String ddSpyId, String language) throws InforException {
		return inforClient.getGridsService().getDDspyFields(inforContext, gridCode, viewType, ddSpyId, language);
	}

	//
	// INFOR DOCUMENTS
	//
	@Override
	public List<InforDocument> readInforDocuments(InforContext inforContext, String entity, String objectCode) throws InforException {
		return inforClient.getDocumentsService().readInforDocuments(inforContext, entity, objectCode);
	}

	@Override
	public String createInforDocumentAssociation(InforContext inforContext, String document, String entity, String objectCode) throws InforException {
		return inforClient.getDocumentsService().createInforDocumentAssociation(document, entity, objectCode, inforContext);
	}

	@Override
	public String createInforDocument(InforContext inforContext, InforDocument inforDocument) throws InforException {
		return inforClient.getDocumentsService().createInforDocument(inforDocument, inforContext);
	}

	@Override
	public String createInforDocumentAndAssociation(InforContext inforContext, InforDocument inforDocument, String entity, String objectCode) throws InforException {
		return inforClient.getDocumentsService().createInforDocumentAndAssociation(inforContext, inforDocument, entity, objectCode);
	}

	//
	// USER SETUP
	//
	@Override
	public EAMUser readUserSetup(InforContext inforContext, String userCode) throws InforException {
		return inforClient.getUserSetupService().readUserSetup(inforContext, userCode);
	}

	@Override
	public String createUserSetup(InforContext inforContext, EAMUser user) throws InforException {
		return inforClient.getUserSetupService().createUserSetup(inforContext, user);
	}

	@Override
	public String updateUserSetup(InforContext inforContext, EAMUser user) throws InforException {
		return inforClient.getUserSetupService().updateUserSetup(inforContext, user);
	}

	@Override
	public String deleteUserSetup(InforContext inforContext, String userCode) throws InforException {
		return inforClient.getUserSetupService().deleteUserSetup(inforContext, userCode);
	}


	//
	// EMPLOYEES
	//

	@Override
	public Employee readEmployee(InforContext inforContext, String employeeCode) throws InforException {
		return inforClient.getEmployeeService().readEmployee(inforContext, employeeCode);
	}

	@Override
	public String createEmployee(InforContext inforContext, Employee employee) throws InforException {
		return inforClient.getEmployeeService().createEmployee(inforContext, employee);
	}

	@Override
	public String updateEmployee(InforContext inforContext, Employee employee) throws InforException {
		return inforClient.getEmployeeService().updateEmployee(inforContext, employee);
	}

	@Override
	public String deleteEmployee(InforContext inforContext, String employeeCode) throws InforException {
		return inforClient.getEmployeeService().deleteEmployee(inforContext, employeeCode);
	}

}
