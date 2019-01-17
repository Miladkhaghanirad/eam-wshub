package ch.cern.cmms.wshub.equipment;

import ch.cern.eam.wshub.core.client.InforClient;
import ch.cern.eam.wshub.core.client.InforContext;
import ch.cern.eam.wshub.core.services.equipment.entities.Equipment;
import ch.cern.eam.wshub.core.services.equipment.entities.EquipmentReplacement;
import ch.cern.eam.wshub.core.services.equipment.entities.EquipmentStructure;
import ch.cern.eam.wshub.core.tools.InforException;
import ch.cern.cmms.wshub.tools.Tools;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EquipmentReplacementService {

    @Inject
    private Tools tools;

    @Inject
    InforClient inforClient;

    // Replace Equipment modes
    private static final String STANDARD = "Standard";
    private static final String SWAPPING = "Swapping";

    /**
     * Entry point for equipment replacement
     *
     * @param replacement
     * @param context
     * @return
     * @throws InforException
     */
    public String replaceEquipment(InforContext context, EquipmentReplacement replacement)
            throws InforException {
        // If mode is empty, it will assume the standard one
        if (tools.isEmpty(replacement.getReplacementMode()))
            replacement.setReplacementMode(STANDARD);
        // Validate mode
        if (!STANDARD.equals(replacement.getReplacementMode()) && !SWAPPING.equals(replacement.getReplacementMode()))
            throw inforClient.getTools().generateFault("Invalid Replacement Mode. Valid values: [Standard, Swapping]");
        // Read both equipments to see that are valid codes
        Equipment oldEquipment = inforClient.getAssetService().readAsset(context, replacement.getOldEquipment());
        Equipment newEquipment = inforClient.getAssetService().readAsset(context, replacement.getNewEquipment());
        // If status is not provided, it will assign the current status
        if (tools.isEmpty(replacement.getNewEquipmentStatus()))
            replacement.setNewEquipmentStatus(newEquipment.getStatusCode());
        if (tools.isEmpty(replacement.getOldEquipmentStatus()))
            replacement.setOldEquipmentStatus(oldEquipment.getStatusCode());
        // Perform the replacement according to the case
        if (STANDARD.equals(replacement.getReplacementMode()))
            return replaceEquipmentStandard(context, replacement, oldEquipment, newEquipment);
        else if (SWAPPING.equals(replacement.getReplacementMode()))
            return replaceEquipmentSwapping(context, replacement, oldEquipment, newEquipment);
        // Null (It will never happen)
        return null;
    }

    /**
     * Swapping Equipment replacement functionality
     *
     * @param replacement
     *            Replacement data
     * @param context
     *            Infor Context
     * @return Result of performing a swapping in the Structure
     */
    private String replaceEquipmentSwapping(InforContext context,
                                            EquipmentReplacement replacement, Equipment oldEquipment,
                                            Equipment newEquipment) throws InforException {

        /*
         * 1. Validates that the new equipment is at the bottom of the hierarchy
         */
        List<EquipmentStructure> newEqpChildren = getDirectChildren(newEquipment);
        if (!newEqpChildren.isEmpty()) {
            throw inforClient.getTools().generateFault(
                    "The new equipment is not at the bottom of a Hierarchy. Swap option cannot be executed");
        }

        /*
         * 2. Update the status of the new equipment (To installed)
         */
        Equipment toUpdate = new Equipment();
        if (!replacement.getNewEquipmentStatus().equals(newEquipment.getStatusCode())) {
            toUpdate.setCode(newEquipment.getCode());
            toUpdate.setStatusCode(replacement.getNewEquipmentStatus());
            inforClient.getAssetService().updateAsset(context, toUpdate);
        }

        // Parents and children for the old equipment
        List<EquipmentStructure> oldEqpChildren = getDirectChildren(oldEquipment);
        List<EquipmentStructure> oldEqpParents = getDirectParents(oldEquipment);
        // Parents of the new equipment
        List<EquipmentStructure> newEqpParents = getDirectParents(newEquipment);

        /*
         * 3. Remove old equipment children dependency from the parent
         */
        for (EquipmentStructure child : oldEqpChildren) {
            // Equipment structure
            EquipmentStructure structure = new EquipmentStructure();
            structure.setChildCode(child.getChildCode());
            // Parent code
            structure.setParentCode(oldEquipment.getCode());
            // Replace equipment structure
            inforClient.getEquipmentHierarchyService().removeEquipmentFromStructure(context, structure);
        }

        /*
         * 4. The children of the old equipment must point now to the new equipment
         */
        for (EquipmentStructure child : oldEqpChildren) {
            EquipmentStructure structure = new EquipmentStructure();
            structure.setChildCode(child.getChildCode());
            structure.setNewParentCode(newEquipment.getCode());
            // Dependent and cost roll up
            structure.setDependent(child.getDependent());
            structure.setCostRollUp(child.getCostRollUp());
            // Add the structure
            inforClient.getEquipmentHierarchyService().addEquipmentToStructure(context, structure);
        }

        /*
         * 5. Remove dependency oldEquipment with all of its parents
         */
        for (EquipmentStructure oldEquipmentParent : oldEqpParents) {
            EquipmentStructure structure = new EquipmentStructure();
            structure.setChildCode(oldEquipment.getCode());
            structure.setParentCode(oldEquipmentParent.getParentCode());
            // Remove the structure
            inforClient.getEquipmentHierarchyService().removeEquipmentFromStructure(context, structure);
        }

        /*
         * 6. Remove dependency between new Equipment and all its parents
         */
        for (EquipmentStructure newEquipmentParent : newEqpParents) {
            EquipmentStructure structure = new EquipmentStructure();
            structure.setChildCode(newEquipment.getCode());
            structure.setParentCode(newEquipmentParent.getParentCode());
            // Remove the structure
            inforClient.getEquipmentHierarchyService().removeEquipmentFromStructure(context, structure);
        }

        /*
         * 7. Attach the new equipment to all the parents of the old equipment
         */
        for (EquipmentStructure parent : oldEqpParents) {
            EquipmentStructure structure = new EquipmentStructure();
            structure.setChildCode(newEquipment.getCode());
            structure.setNewParentCode(parent.getParentCode());
            // Dependent and cost roll up
            structure.setDependent(parent.getDependent());
            structure.setCostRollUp(parent.getCostRollUp());
            // Add the structure
            inforClient.getEquipmentHierarchyService().addEquipmentToStructure(context, structure);
        }

        /*
         * 8. Update statuses of the equipments if necessary
         */
        if (!replacement.getOldEquipmentStatus().equals(oldEquipment.getStatusCode())) {
            toUpdate.setCode(oldEquipment.getCode());
            toUpdate.setStatusCode(replacement.getOldEquipmentStatus());
            inforClient.getAssetService().updateAsset(context, toUpdate);
        }

        // Finish ok
        return replacement.getOldEquipment() + " was replaced by " + replacement.getNewEquipment();
    }

    /**
     * Standard Equipment replacement functionality
     *
     * @param replacement
     *            Replacement data
     * @param context
     *            Infor Context
     * @return Result of performing a standard replacement of equipment
     */
    private String replaceEquipmentStandard(InforContext context,
                                            EquipmentReplacement replacement, Equipment oldEquipment,
                                            Equipment newEquipment) throws InforException {

        // Get all the parents from the old equipment
        List<EquipmentStructure> oldEquipmentParents = getDirectParents(oldEquipment);
        // Check that there are some parents
        if (oldEquipmentParents.isEmpty())
            throw inforClient.getTools().generateFault("Equipment [" + oldEquipment.getCode() + "] does not belong to any hierarchy.");

        /*
         * 1. Update the status of the new equipment (To installed)
         */
        Equipment toUpdate = new Equipment();
        if (!replacement.getNewEquipmentStatus().equals(newEquipment.getStatusCode())) {
            toUpdate.setCode(newEquipment.getCode());
            toUpdate.setStatusCode(replacement.getNewEquipmentStatus());
            inforClient.getAssetService().updateAsset(context, toUpdate);
        }

        /*
         * 2. Remove the old equipment from its structure
         */
        // Remove one by one
        for (EquipmentStructure oldEquipmentParent : oldEquipmentParents) {
            EquipmentStructure structure = new EquipmentStructure();
            structure.setChildCode(oldEquipment.getCode());
            structure.setParentCode(oldEquipmentParent.getParentCode());
            // Remove the structure
            inforClient.getEquipmentHierarchyService().removeEquipmentFromStructure(context, structure);
        }

        /*
         * 3. Detach the new equipment from all it's parents (if any)
         */
        List<EquipmentStructure> newEquipmentParents = getDirectParents(newEquipment);
        // Remove one by one
        for (EquipmentStructure newEquipmentParent : newEquipmentParents) {
            EquipmentStructure structure = new EquipmentStructure();
            structure.setChildCode(newEquipment.getCode());
            structure.setParentCode(newEquipmentParent.getParentCode());
            // Remove the structure
            inforClient.getEquipmentHierarchyService().removeEquipmentFromStructure(context, structure);
        }

        /*
         * 4. Attach the new Equipment to all Old Equipment parents
         */
        for (EquipmentStructure oldEquipmentParent : oldEquipmentParents) {
            EquipmentStructure structure = new EquipmentStructure();
            structure.setChildCode(newEquipment.getCode());
            structure.setNewParentCode(oldEquipmentParent.getParentCode());
            // Dependent and cost roll up
            structure.setDependent(oldEquipmentParent.getDependent());
            structure.setCostRollUp(oldEquipmentParent.getCostRollUp());
            // Add the structure
            inforClient.getEquipmentHierarchyService().addEquipmentToStructure(context, structure);
        }

        /*
         * 5. Update the statuses of the equipments if necessary
         */

        if (!replacement.getOldEquipmentStatus().equals(oldEquipment.getStatusCode())) {
            toUpdate.setCode(oldEquipment.getCode());
            toUpdate.setStatusCode(replacement.getOldEquipmentStatus());
            System.out.println("Updating status for Equipment: " + toUpdate);
            inforClient.getAssetService().updateAsset(context, toUpdate);
        }

        // Finish ok
        return replacement.getOldEquipment() + " was replaced by " + replacement.getNewEquipment();
    }

    /**
     * Gets the direct parents of an equipment
     *
     * @param equipment
     *            Equipment
     * @return Direct parents
     */
    private List<EquipmentStructure> getDirectParents(Equipment equipment) {
        return this.getDirectRelations(equipment, false);
    }

    /**
     * Gets the direct children of an equipment
     *
     * @param equipment
     *            Equipment
     * @return Direct children of the equipment
     */
    private List<EquipmentStructure> getDirectChildren(Equipment equipment) {
        return this.getDirectRelations(equipment, true);
    }

    /**
     * Gets the direct relations for an equipment (True for children, otherwise will
     * fetch parents)
     *
     * @param equipment
     *            Equipment from which the children or parent will be fetched
     * @return The list of the Parent/Children equipment codes (First level)
     */
    private List<EquipmentStructure> getDirectRelations(Equipment equipment, boolean children) {
        // Variables
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        List<EquipmentStructure> relations = new ArrayList<>();
        try {
            String query = "SELECT STC_PARENT, STC_CHILD, STC_ROLLDOWN, STC_ROLLUP FROM R5STRUCTURES WHERE "
                    + (children ? "STC_PARENT = ?" : "STC_CHILD = ?");
            // Create connection
            connection = tools.getConnection();
            // Create Statement
            statement = connection.prepareStatement(query);
            statement.setString(1, equipment.getCode());
            // Execute query
            result = statement.executeQuery();
            while (result.next()) {/* There is a result */
                int i = 0;
                EquipmentStructure structure = new EquipmentStructure();
                structure.setParentCode(result.getString(++i));
                structure.setChildCode(result.getString(++i));
                structure.setDependent(inforClient.getTools().getDataTypeTools().decodeBoolean(result.getString(++i)));
                structure.setCostRollUp(inforClient.getTools().getDataTypeTools().decodeBoolean(result.getString(++i)));
                relations.add(structure);
            }
        } catch (Exception e) {/* Error reading information */
            tools.log(Logger.Level.FATAL,
                    "Error reading children for equipment [" + equipment.getCode() + "] :" + e.getMessage());
            e.printStackTrace();
        } finally {/* Close connections */
            inforClient.getTools().closeConnection(connection, statement, result);
        }
        // Return result
        return relations;
    }
}
