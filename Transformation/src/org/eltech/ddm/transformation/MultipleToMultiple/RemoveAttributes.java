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

import java.util.Vector;

import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.transformation.MultipleToMultipleMapping;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.MiningAttribute;

/**
 * Removes multiple attributes from source attributes. Not invertable,
 * counterpart of AddAttributes. Like RemoveAttribute, but for multiple ones.
 *
 * Target name array is ignored since attributes are not copied
 * but identical. Attention: After the remove the meta data may not be empty.
 */
@SuppressWarnings("serial")
public class RemoveAttributes extends MultipleToMultipleMapping
{
  // -----------------------------------------------------------------------
  //  Variables declarations
  // -----------------------------------------------------------------------
  /** Names of attribute to be remove. */
  @SuppressWarnings("rawtypes")
  private Vector removeAttributeNames;

  /** Positions of the attributes to be removed. */
  private int[] removeAttributePos;

  // -----------------------------------------------------------------------
  //  Constructor
  // -----------------------------------------------------------------------
  /**
   * Empty constructor.
   */
  public RemoveAttributes()
  {
  }

  // -----------------------------------------------------------------------
  //  Getter and setter methods
  // -----------------------------------------------------------------------
  /**
   * Returns names of attributes to be removed.
   *
   * @return names of attributes to be removed
   */
  @SuppressWarnings("rawtypes")
public Vector getRemoveAttributeNames() {
    return removeAttributeNames;
  }

  /**
   * Sets attribute names to be removed.
   *
   * @param removeAttributeNames attribute names to be removed
   */
  @SuppressWarnings("rawtypes")
  public void setRemoveAttributeNames(Vector removeAttributeNames)
  {
    this.removeAttributeNames = removeAttributeNames;
  }

  // -----------------------------------------------------------------------
  //  Transformation methods
  // -----------------------------------------------------------------------
  /**
   * Remove attributes from source attributes.
   *
   * @return smaller attribute set
   * @exception MiningException cannot remove attribute
   */
  public MiningAttribute[] transformAttribute() throws MiningException
  {
      if (removeAttributeNames == null)
        throw new MiningException(MiningErrorCode.INVALID_INPUT_DATA);//"No attribute array for removing defined");

      String[] sourceNameDyn = getSourceNameDynamic();
      int nSource = sourceNameDyn.length;
      int nTar    = nSource - removeAttributeNames.size();
      if (nTar <= 0)
        throw new MiningException(MiningErrorCode.INVALID_INDEX);//"There must be more than zero attributes after removal");

      MiningAttribute transformedAttribute[] = new MiningAttribute[nTar];
      removeAttributePos = new int[ removeAttributeNames.size() ];
      int ip  = 0;
      int ind = 0;
      for (int i = 0; i < nSource; i++) {
        if (removeAttributeNames.indexOf(sourceNameDyn[i]) == -1) {
          if (ind == nTar)
            throw new MiningException(MiningErrorCode.INVALID_INDEX);//"Could not remove attribute because name not found");
          transformedAttribute[ind] = getSourceAttribute(i);
          ind = ind + 1;
        }
        else {
          removeAttributePos[ip] = i;
          ip = ip + 1;
        };
      };

      return transformedAttribute;
  }

  /**
   * Copies all attribute values except that of the attributes to be removed.
   *
   * @param attributeValues values of attributes to be transformed
   * @return tranformed values
   * @exception MiningException cannot transform attribute values
   */
  public double[] transformAttributeValue( double[] attributeValues ) throws MiningException
  {
    int n    = attributeValues.length;
    int nTar = n - removeAttributePos.length;

    double transformedValues[] = new double[nTar];

    int ip  = 0;
    int ind = 0;
    for (int i = 0; i < n; i++) {
      if ( ip == removeAttributePos.length || i < removeAttributePos[ip] ) {
        transformedValues[ind] = attributeValues[i];
        ind = ind + 1;
      }
      else {
        ip = ip + 1;
      };
    }

    return transformedValues;
  }
}
