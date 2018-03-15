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
package org.eltech.ddm.transformation.MultipleToMultiple;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.transformation.MultipleToMultipleMapping;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.MiningAttribute;

/**
 * Rotates all attributes positions by one. This means, that attribute
 * on position 0  moves to position 1, attribute on position 1 to
 * position 2, ..., attribute on position n-1 to position 0. (Here
 * n is the number of all attributes.)
 *
 * Source and target attribute names are ignored.
 */
@SuppressWarnings("serial")
public class RotateAttributes extends MultipleToMultipleMapping
{
  // -----------------------------------------------------------------------
  //  Constructor
  // -----------------------------------------------------------------------
  /**
   * Empty constructor.
   */
  public RotateAttributes()
  {

  }

  // -----------------------------------------------------------------------
  //  Transformation methods
  // -----------------------------------------------------------------------
  /**
   * Creates the rotated attributes.
   *
   * @return transformed (rotated) attributes
   * @exception MiningException cannot transform attributes
   */
  public MiningAttribute[] transformAttribute() throws MiningException
  {
      int n = getSourceNameDynamic().length;
      MiningAttribute transformedAttribute[] = new MiningAttribute[n];
      for(int i = 0; i < n; i++)
      {
        int ind = i + 1;
        if (ind >= n)
          ind = 0;
        MiningAttribute prAtt = getSourceAttribute(i);
        transformedAttribute[ind] = (MiningAttribute) prAtt.clone();
      };

      return transformedAttribute;
  }

  /**
   * Rotates attribute values.
   *
   * @param attributeValues attribute values to be transformed
   * @return tranformed (rotated) values
   * @exception MiningException cannot transform attribute values
   */
  public double[] transformAttributeValue( double[] attributeValues ) throws MiningException
  {
    int n = getSourceNameDynamic().length;
    double transformedValue[] = new double[n];
    for(int i = 0; i < n; i++)
    {
      int ind = i + 1;
      if (ind >= n)
          ind = 0;

      transformedValue[ind] = attributeValues[i];
    };
    return transformedValue;
  }

}