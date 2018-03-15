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

import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.transformation.MultipleToMultipleMapping;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.MiningAttribute;

/**
 * Removes attribute from source attributes. Not invertable,
 * counterpart of AddAttribute.
 *
 * Target name array is ignored since attributes are not copied
 * but identical. Attention: There must be more than one
 * source attribute specified (i.e. after the remove the
 * meta data may not be empty).
 */
@SuppressWarnings("serial")
public class RemoveAttribute extends MultipleToMultipleMapping
{
  // -----------------------------------------------------------------------
  //  Variables declarations
  // -----------------------------------------------------------------------
  /** Name of attribute to be remove. */
  private String removeAttributeName;

  /** Position of the attribute to remove. */
  private int removeAttributePos = -1;

  // -----------------------------------------------------------------------
  //  Constructor
  // -----------------------------------------------------------------------
  /**
   * Empty constructor.
   */
  public RemoveAttribute()
  {
  }

  // -----------------------------------------------------------------------
  //  Getter and setter methods
  // -----------------------------------------------------------------------
  /**
   * Returns name of attribute to be removed.
   *
   * @return name of attribute to be removed
   */
  public String getRemoveAttributeName() {

    return removeAttributeName;
  }

  /**
   * Sets attribute name to be removed.
   *
   * @param removeAttributeName attribute name to be removed
   */
  public void setRemoveAttributeName(String removeAttributeName)
  {
    this.removeAttributeName = removeAttributeName;
  }

  // -----------------------------------------------------------------------
  //  Transformation methods
  // -----------------------------------------------------------------------
  /**
   * Remove attribute from source attributes.
   *
   * @return extended attribute set
   * @exception MiningException cannot remove attribute
   */
  public MiningAttribute[] transformAttribute() throws MiningException
  {
      if (removeAttributeName == null)
        throw new MiningException(MiningErrorCode.INVALID_ARGUMENT);//"No attribute name for removing defined");

      String[] sourceNameDyn = getSourceNameDynamic();
      int nSource = sourceNameDyn.length;
      if (nSource <= 1)
        throw new MiningException(MiningErrorCode.INVALID_INDEX);//"There must be more than zero attributes after removal");

      MiningAttribute transformedAttribute[] = new MiningAttribute[ nSource-1 ];
      int ind = 0;
      for (int i = 0; i < nSource; i++) {
        if (! sourceNameDyn[i].equals(removeAttributeName)) {
          if (ind == nSource-1)
            throw new MiningException(MiningErrorCode.INVALID_INDEX);//"Could not remove attribute because name not found");
          transformedAttribute[ind] = getSourceAttribute(i);
          ind = ind + 1;
        }
        else {
          removeAttributePos = i;
        }
      };

      return transformedAttribute;
  }

  /**
   * Copies all attribute values except that of the attribute to be removed.
   *
   * @param attributeValues values of attributes to be transformed
   * @return tranformed values
   * @exception MiningException cannot transform attribute values
   */
  public double[] transformAttributeValue( double[] attributeValues ) throws MiningException
  {
    int n = attributeValues.length;
    double transformedValues[] = new double[n-1];
    for (int i = 0; i < removeAttributePos; i++)
      transformedValues[i] = attributeValues[i];
    for (int i = removeAttributePos; i < n-1; i++)
      transformedValues[i] = attributeValues[i+1];

    return transformedValues;
  }
}