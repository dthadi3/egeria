/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */

package org.odpi.openmetadata.accessservices.analyticsmodeling.synchronization.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.odpi.openmetadata.accessservices.analyticsmodeling.synchronization.IdMap;
import org.odpi.openmetadata.accessservices.analyticsmodeling.synchronization.beans.SchemaAttribute;
import org.odpi.openmetadata.accessservices.analyticsmodeling.utils.Constants;

/**
 *	Base class for analytics metadata provides common attributes. 
 */
public abstract class AnalyticsMetadata extends SchemaAttribute {

	private String identifier;				// 3rd party system identifier within parent element
	private List<String> sourceGuid;		// repository GUIDs of entities representing external metadata objects
	private List<String> sourceId;			// IDs of external metadata objects
	private String type;					// arbitrary type of the metadata as defined in 3rd party system
											// display name and type should represent element for the user in UI
											// Samples of possible values for types:
											// queryItem - metadata stored in storage like RDBMS, OLAP, CSV files, etc.
											// dataItem - projected metadata within visualization
											// querySubject - group of related metadata items: Relation/Table/View in RDBMS, Dimension in OLAP
											// query - group of metadata items projected in a query: SQL (RDBMS), MDX (OLAP)
											// widget - visual elements with metadata items
											// page - multiple page report logical grouping for widgets
											// tab - multiple tab per page logical grouping for widgets


	/**
	 * Get type of the metadata.
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Set type of the metadata.
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Getter method for attribute identifier
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * Setter method for attribute identifier
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * Get GUID list the element is connected to.
	 * @return the sourceGuid
	 */
	public List<String> getSourceGuid() {
		return sourceGuid;
	}

	/**
	 * Set GUID list the element is connected to.
	 * @param sourceGuid the sourceGuid to set
	 */
	public void setSourceGuid(List<String> sourceGuid) {
		this.sourceGuid = sourceGuid;
	}

	/**
	 * @return the sourceId
	 */
	public List<String> getSourceId() {
		return sourceId;
	}

	/**
	 * @param sourceId the sourceId to set
	 */
	public void setSourceId(List<String> sourceId) {
		this.sourceId = sourceId;
	}
	
	public void addSourceGuid(String guid) {
		if (sourceGuid == null) {
			sourceGuid = new ArrayList<>();
		}
		sourceGuid.add(guid);
	}

	public void addSourceId(String id) {
		if (sourceId == null) {
			sourceId = new ArrayList<>();
		}
		sourceId.add(id);
	}
	
	/**
	 * Convert properties from entity into bean.
	 */
	protected abstract void convertProperties();

	/**
	 * Helper function to load common properties from additional properties.
	 */
	public void convertAnalyticsMetadataProperties() {
		
        if (additionalProperties.get(IdMap.SOURCE_ID) != null) {
        	this.setSourceId(Arrays.asList(additionalProperties.get(IdMap.SOURCE_ID).split(Constants.SYNC_ID_LIST_DELIMITER)));
        }

        this.setType(additionalProperties.get(Constants.TYPE));
        this.setIdentifier(additionalProperties.get(Constants.SYNC_IDENTIFIER));
        
        convertProperties();
	}
	
	/**
	 * Save bean custom properties into entity additional properties.
	 */
	protected abstract void prepareCustomProperties();
	
	/**
	 * Helper function to preserve common properties as additional properties.
	 */
	public void prepareAnalyticsMetadataProperties() {
		
		if (additionalProperties == null) {
			additionalProperties = new HashMap<>();
		}
		
        if (this.getSourceId() != null) {
            additionalProperties.put(IdMap.SOURCE_ID, String.join(Constants.SYNC_ID_LIST_DELIMITER, this.getSourceId()));
        }

        additionalProperties.put(Constants.TYPE, this.getType());
        additionalProperties.put(Constants.SYNC_IDENTIFIER, this.getIdentifier());
        
        prepareCustomProperties();
	}

}
