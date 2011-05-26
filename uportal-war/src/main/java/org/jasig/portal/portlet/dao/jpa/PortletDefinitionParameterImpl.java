/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jasig.portal.portlet.dao.jpa;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jasig.portal.portlet.om.IPortletDefinitionParameter;

/**
 * JPA implementation of the IPortletParameter interface.
 * 
 * @author Jen Bourey, jennifer.bourey@gmail.com
 */
@Embeddable
@Table(name = "UP_PORTLET_PARAM")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PortletDefinitionParameterImpl implements IPortletDefinitionParameter, Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "PORTLET_PARM_NAME", length = 255, nullable = false)
	String name;

	@Column(name = "PORTLET_PARM_VAL", length = 2000)
	String value;

	@Column(name = "PORTLET_PARM_DESC", length = 255)
	String descr;
	
	
	/**
	 * Default constructor required by Hibernate
	 */
	public PortletDefinitionParameterImpl() { }

    /**
     * Instantiate a ChannelParameter with a particular name, default value,
     * and indication of whether it can be overridden.
     * @param name name of the channel parameter.
     * @param value default value for the parameter.
     */
    public PortletDefinitionParameterImpl(String name, String value) {
      this.name = name;
      this.value = value;
    }
    
    /**
     * Construct a new ChannelParameterImpl from an IChannelParameter 
     * 
     * @param param
     */
    public PortletDefinitionParameterImpl(IPortletDefinitionParameter param) {
    	this.name = param.getName();
    	this.value = param.getValue();
    	this.descr = param.getDescription();
    }
    
    
	// Getter methods

    /*
     * (non-Javadoc)
     * @see org.jasig.portal.channel.IChannelParameter#getName()
     */
	@Override
    public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jasig.portal.channel.IChannelParameter#getValue()
	 */
	@Override
    public String getValue() {
		return this.value;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jasig.portal.channel.IChannelParameter#getDescription()
	 */
	@Override
    public String getDescription() {
		return this.descr;
	}

	
	// Setter methods

	/*
	 * (non-Javadoc)
	 * @see org.jasig.portal.channel.IChannelParameter#setName(java.lang.String)
	 */
	@Override
    public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jasig.portal.channel.IChannelParameter#setValue(java.lang.String)
	 */
	@Override
    public void setValue(String value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jasig.portal.channel.IChannelParameter#setDescription(java.lang.String)
	 */
	@Override
    public void setDescription(String descr) {
		this.descr = descr;
	}

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (IPortletDefinitionParameter.class.isAssignableFrom(obj.getClass()))
            return false;
        IPortletDefinitionParameter other = (IPortletDefinitionParameter) obj;
        if (this.name == null) {
            if (other.getName() != null)
                return false;
        }
        else if (!this.name.equals(other.getName()))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "PortletDefinitionParameter [name=" + this.name + ", value=" + this.value + ", descr=" + this.descr + "]";
    }
    
}