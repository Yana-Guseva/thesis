/*
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

 /**
  * Title: XELOPES Data Mining Library
  * Description: The XELOPES library is an open platform-independent and data-source-independent library for Embedded Data Mining.
  * Copyright: Copyright (c) 2002 Prudential Systems Software GmbH
  * Company: ZSoft (www.zsoft.ru), Prudsys (www.prudsys.com)
  * @author Michael Thess
  * @version 1.0
  */

package org.eltech.ddm.transformation;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.MiningAttribute;
import org.omg.java.cwm.analysis.transformation.ClassifierFeatureMap;

/**
 * Transforms a single attribute into a set of attributes or vice versa.
 */
@SuppressWarnings("serial")
public abstract class OneToMultipleMapping extends ClassifierFeatureMap
{
  // -----------------------------------------------------------------------
  //  Variables declarations
  // -----------------------------------------------------------------------
  /** Target attribute names if oneToMultipleMappind is true, else source names. */
  protected String[] classifierName;

  /** Source name if oneToMultipleMappind is true, else target name. */
  protected String featureName;

  /** Direction of mapping: one-to-multiple or multiple-to-one? */
  protected boolean oneToMultipleMapping = true;

  /** Remove source attribute(s) after transformation? */
  protected boolean removeSourceAttributes = true;

  // -----------------------------------------------------------------------
  //  Constructor
  // -----------------------------------------------------------------------
  /**
   * Empty constructor.
   */
  public OneToMultipleMapping()
  {
  }

  // -----------------------------------------------------------------------
  //  Getter and setter methods
  // -----------------------------------------------------------------------
  /**
   * Returns names of classifier attributes.
   *
   * @return names of classifier attributes
   */
  public String[] getClassifierName()
  {
    return classifierName;
  }

  /**
   * Sets names of classifier attributes.
   *
   * @param classifierName new names of classifier attributes
   */
  public void setClassifierName(String[] classifierName)
  {
    this.classifierName = classifierName;
  }

  /**
   * Returns name of single attribute.
   *
   * @return name of single attribute
   */
  public String getFeatureName()
  {
    return featureName;
  }

  /**
   * Sets name of single attribute.
   *
   * @param featureName new name of single attribute
   */
  public void setFeatureName(String featureName)
  {
    this.featureName = featureName;
  }

  /**
   * Is classifier to feature mapping?
   *
   * @return true if classifier to feature mapping, else false
   */
  public boolean isOneToMultipleMapping()
  {
    return oneToMultipleMapping;
  }

  /**
   * Remove all source attributes after transformation (default: no)?
   *
   * @return true if remove source attributes after trafo, otherwise false
   */
  public boolean isRemoveSourceAttributes()
  {
    return removeSourceAttributes;
  }

  /**
   * Set remove all source attributes after transformation (default: no).
   *
   * @param removeSourceAttributes set remove source attributes after transformation
   */
  public void setRemoveSourceAttributes(boolean removeSourceAttributes)
  {
    this.removeSourceAttributes = removeSourceAttributes;
  }

  /**
   * Returns source attribute names. If for multiple-to-multiple mapping
   * no source attribute names are defined, all attributes names are considered
   * as source names.
   *
   * @return dynamic source attributes
 * @throws MiningException 
   */
  protected String[] getSourceNameDynamic() throws MiningException {

     if ( isOneToMultipleMapping() ) {
       String[] sname = {featureName};
       return sname;
     }
     else {
       if (classifierName == null) {
         ELogicalData metaData = getSourceMetaData();
         int nSource = metaData.getAttributesNumber();
         String[] sn = new String[nSource];
         for (int i = 0; i < nSource; i++)
           sn[i] = metaData.getAttribute(i).getName();
         return sn;
       }
       else
         return classifierName;
    }
  }

  /**
   * Returns target attribute names.
   *
   * @return dynamic target attributes
   */
  protected String[] getTargetNameDynamic() {

    if ( ! isOneToMultipleMapping() ) {
      String[] tname = {featureName};
      return tname;
    }
    else {
      return classifierName;
    }
  }

  /**
   * Returns source attribute. If isOneToMultipleMapping is false,
   * the index of the desired (classifier) attribute must be specified.
   *
   * @param index index of source attribute
   * @return source attribute, null if not found
 * @throws MiningException 
   */
  protected MiningAttribute getSourceAttribute(int index) throws MiningException {

    ELogicalData metaData = getSourceMetaData();

    // One => multiple:
    String sourceName = featureName;
    // Multiple => one:
    if (! oneToMultipleMapping) {
      if (classifierName != null) {
        if (index < 0 || index >= classifierName.length)
          return null;
        sourceName = classifierName[index];
      }
      else {
        return metaData.getAttribute(index);
      }
    };

    MiningAttribute attribute = metaData.getAttribute(sourceName);

    return attribute;
  }

  /**
   * Returns meta data of sources, i.e. the pretransformed meta data.
   *
   * @return source meta data
   */
  protected ELogicalData getSourceMetaData() {
    return ((MultipleToMultipleMapping) classifierMap).pretransformedMetaData;
  }

  // -----------------------------------------------------------------------
  //  Transformation methods
  // -----------------------------------------------------------------------
  /**
   * Transforms the source attribute. The result is one or some attribute
   * (depending on isClassifierToFeature variable).
   *
   * @return transformed attributes
   * @exception MiningException cannot transform attributes
   */
  public abstract MiningAttribute[] transformAttribute() throws MiningException;

  /**
   * Transforms one or some attribute values. The result is also one
   * or some values. Both depends on how the isClassifierToFeature
   * variable is set.
   *
   * @param attributeValues values of attribute to be transformed
   * @return tranformed values
   * @exception MiningException cannot transform attribute values
   */
  public abstract double[] transformAttributeValue( double[] attributeValues ) throws MiningException;

  // -----------------------------------------------------------------------
  //  Methods of PMML handling
  // -----------------------------------------------------------------------
  /**
   * Creates array of PMML DerivedValue.
   *
   * @return array of DerivedValue
   * @throws MiningException
   */
  public Object[] createPmmlObjects() throws MiningException
  {
      throw new java.lang.UnsupportedOperationException("Method createPmmlObjects() not yet implemented.");
  }

  /**
   * Creates this object from an array of PMML DerivedValue.
   *
   * @param pmml array of DerivedValue
   * @throws MiningException
   */
  public void parsePmmlObjects(Object[] pmml) throws MiningException
  {
      throw new java.lang.UnsupportedOperationException("Method parsePmmlObjects() not yet implemented.");
  }

  /**
   * Returns string representation of one-to-multiple map.
   *
   * @return one-to-multiple map string representation
   */
  public String toString() {

    String res = "----------One-to-multiple map. " + "\n";
    res        = res + "Classifier names: ";
    if (classifierName != null)
      for (int i = 0; i < classifierName.length; i++)
        res = res + classifierName[i] + ", ";
    res        = res + "\n";
    res        = res + "Feature name: " + featureName + "\n";
    res        = res + "oneToMultipleMapping: " + oneToMultipleMapping + "\n";
    res        = res + "removeSourceAttributes: " + removeSourceAttributes + "\n";
    res        = res + "class name: " + this.getClass().toString();

    return res;
  }
}