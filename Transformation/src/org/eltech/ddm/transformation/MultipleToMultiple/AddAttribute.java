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
  * @version 1.1
  */
package org.eltech.ddm.transformation.MultipleToMultiple;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ECategory;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.transformation.MultipleToMultipleMapping;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeType;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.MiningAttribute;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.Category;

/**
 * Adds new attribute to source attributes. If new attribute is
 * not specified, a numeric attribute is added. In the
 * transformation, a missing value is set as new attribute
 * value. Invertable by RemoveAttribute.
 *
 * Target name array is ignored since attributes are not copied
 * but identical. Attention: There most be at least one
 * source attribute specified i.e. cannot be used for
 * empty meta data).
 */
@SuppressWarnings("serial")
public class AddAttribute extends MultipleToMultipleMapping
{
  // -----------------------------------------------------------------------
  //  Variables declarations
  // -----------------------------------------------------------------------
  /** New attribute to add. */
  private MiningAttribute newAttribute;

  // -----------------------------------------------------------------------
  //  Constructor
  // -----------------------------------------------------------------------
  /**
   * Empty constructor.
   */
  public AddAttribute()
  {
  }

  // -----------------------------------------------------------------------
  //  Getter and setter methods
  // -----------------------------------------------------------------------
  /**
   * Returns new attribute.
   *
   * @return new attribute to add
   */
  public MiningAttribute getNewAttribute()
  {
    return newAttribute;
  }

  /**
   * Sets new attribute.
   *
   * @param newAttribute new attribute to add
   */
  public void setNewAttribute(MiningAttribute newAttribute)
  {
    this.newAttribute = newAttribute;
  }

  // -----------------------------------------------------------------------
  //  Transformation methods
  // -----------------------------------------------------------------------
  /**
   * Add new attribute to source attributes. If no new attribute
   * is specified, a default NumericAttribute with name
   * 'newAtt<numer of source attributes>' is added.
   *
   * @return extended attribute set
   * @exception MiningException cannot transform attributes
   */
  public MiningAttribute[] transformAttribute() throws MiningException
  {
      int nSource = getSourceNameDynamic().length;
      MiningAttribute transformedAttribute[] = new MiningAttribute[ nSource+1 ];
      for (int i = 0; i < nSource; i++)
        transformedAttribute[i] = getSourceAttribute(i);
      if (newAttribute != null)
        transformedAttribute[nSource] = newAttribute;
      else
        transformedAttribute[nSource] = new ELogicalAttribute( "newAtt" + String.valueOf(nSource), AttributeType.numerical);

      return transformedAttribute;
  }

  /**
   * Copies all attribute values and adds missing value of new attribute.
   *
   * @param attributeValues values of attributes to be transformed
   * @return tranformed values
   * @exception MiningException cannot transform attribute values
   */
  public double[] transformAttributeValue( double[] attributeValues ) throws MiningException
  {
    // Copy data to transformation vector:
    int n = attributeValues.length;
    double transformedValues[] = new double[n+1];
    for (int i = 0; i < n; i++)
      transformedValues[i] = attributeValues[i];

    // Add missing value of new attribute:
    transformedValues[n] = Double.NaN;//Category.MISSING_VALUE;

    return transformedValues;
  }
}