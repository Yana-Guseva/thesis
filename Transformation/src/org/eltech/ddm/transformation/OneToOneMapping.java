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
import org.omg.java.cwm.analysis.transformation.FeatureMap;

/**
 * Transforms one attribute into one other attribute.
 */
@SuppressWarnings("serial")
public abstract class OneToOneMapping extends FeatureMap
{
  // -----------------------------------------------------------------------
  //  Variables declarations
  // -----------------------------------------------------------------------
  /** Name of source attribute. */
  protected String sourceName;

  /** Name of target attribute. */
  protected String targetName;

  /** Remove source attribute after transformation? */
  protected boolean removeSourceAttribute = true;

  /** Directly assigned source attribute. */
  protected MiningAttribute sourceAttribute = null;

  // -----------------------------------------------------------------------
  //  Constructor
  // -----------------------------------------------------------------------
  /**
   * Empty constructor.
   */
  public OneToOneMapping()
  {
  }

  // -----------------------------------------------------------------------
  //  Getter and setter methods
  // -----------------------------------------------------------------------
  /**
   * Sets new name of source attribute.
   *
   * @param sourceName new name of source attribute
   */
  public void setSourceName(String sourceName)
  {
    this.sourceName = sourceName;
  }

  /**
   * Returns name of source attribute.
   *
   * @return source attribute name
   */
  public String getSourceName()
  {
    return sourceName;
  }

  /**
   * Sets new name of target attribute.
   *
   * @param targetName new name of target attribute
   */
  public void setTargetName(String targetName)
  {
    this.targetName = targetName;
  }

  /**
   * Returns name of target attribute.
   *
   * @return name of target attribute
   */
  public String getTargetName()
  {
    return targetName;
  }

  /**
   * Set remove all source attribute after transformation (default: no).
   *
   * @param removeSourceAttribute set remove source attribute after transformation
   */
  public void setRemoveSourceAttribute(boolean removeSourceAttribute)
  {
    this.removeSourceAttribute = removeSourceAttribute;
  }

  /**
   * Remove source attribute after transformation (default: no)?
   *
   * @return true if remove source attribute after trafo, otherwise false
   */
  public boolean isRemoveSourceAttribute()
  {
    return removeSourceAttribute;
  }

  /**
   * Returns source attribute name.
   *
   * @return dynamic source attributes
   */
  protected String getSourceNameDynamic() {

     return sourceName;
  }

  /**
   * Returns target attribute name. If not target attribute name is defined,
   * the target attribute name is defined as
   * "t_" + <dynamic source attribute name>.
   *
   * @return dynamic target attributes
   */
  protected String getTargetNameDynamic() {

    String tname = targetName;
    if (tname == null && sourceName != null)
      tname = "t_" + getSourceNameDynamic();

    return tname;
  }

  /**
   * Allows to directly assign a source attribute which is used
   * instead of the meta data. This should be done when the ono-to-one mapping
   * is used separately from the multiple-to-multiple mapping, e.g. in
   * Neural Network's input and output units.
   *
   * @param sourceAttribute source attribute of the transformation
   */
  public void setSourceAttribute(MiningAttribute sourceAttribute) {
    this.sourceAttribute = sourceAttribute;
  }

  /**
   * Returns source attribute. If the source attribute is defined
   * directly through the method setSourceAttribute, this one is used.
   * Otherwise the meta data is accessed from the multiple-to-multiple
   * mapping it is assigned to and the attribute of sourceName is found.
   *
   * @return source attribute, null if not found
 * @throws MiningException 
   */
  public MiningAttribute getSourceAttribute() throws MiningException {

    if (sourceAttribute != null)
      return sourceAttribute;

    ELogicalData metaData = getSourceMetaData();
    MiningAttribute attribute = metaData.getAttribute(sourceName);

    return attribute;
  }

  /**
   * Returns meta data of sources, i.e. the pretransformed meta data
   * from the assigned multiple-to-multiple mapping.
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
   * Transforms the source attribute. The result is the target attribute.
   *
   * @return transformed attribute
   * @exception MiningException cannot transform attribute
   */
  public abstract MiningAttribute transformAttribute() throws MiningException;

  /**
   * Transforms attribute value. The result is also a value.
   *
   * @param attributeValue value of attribute to be transformed
   * @return tranformed value
   * @exception MiningException cannot transform attribute value
   */
  public abstract double transformAttributeValue( double attributeValue ) throws MiningException;

  // -----------------------------------------------------------------------
  //  Other methods
  // -----------------------------------------------------------------------
  /**
   * Returns string representation of one-to-one map.
   *
   * @return onoe-to-one map string representation
   */
  public String toString() {

    String res = "----------Ono-to-one map. " + "\n";
    res        = res + "Source name: " + sourceName + "\n";
    res        = res + "Target name: " + targetName + "\n";
    res        = res + "removeSourceAttribute: " + removeSourceAttribute + "\n";
    res        = res + "class name: " + this.getClass().toString();

    return res;
  }
}