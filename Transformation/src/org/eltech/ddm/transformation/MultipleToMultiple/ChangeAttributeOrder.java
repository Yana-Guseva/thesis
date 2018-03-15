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

import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.transformation.MultipleToMultipleMapping;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.MiningAttribute;

/**
 * Changes order of attributes. The new order is specified by the
 * order of the attribute names given in the target names.
 */
@SuppressWarnings("serial")
public class ChangeAttributeOrder extends MultipleToMultipleMapping
{
  // -----------------------------------------------------------------------
  //  Variables declarations
  // -----------------------------------------------------------------------
  /** New positions of source attributes. */
  private int[] permut = null;

  // -----------------------------------------------------------------------
  //  Constructor
  // -----------------------------------------------------------------------
  /**
   * Empty constructor.
   */
  public ChangeAttributeOrder()
  {

  }

  // -----------------------------------------------------------------------
  //  Transformation methods
  // -----------------------------------------------------------------------
  /**
   * Changes the order of attributes in correspondence to target names.
   *
   * @return transformed (permuteted) attributes
   * @exception MiningException cannot transform attributes
   */
  public MiningAttribute[] transformAttribute() throws MiningException
  {
      String[] sourceNameDyn = getSourceNameDynamic();
      String[] targetNameDyn = getTargetNameDynamic();

      if (targetNameDyn == null)
        throw new MiningException(MiningErrorCode.INVALID_ARGUMENT);//"no target attributes specified");

      if (sourceNameDyn.length != targetNameDyn.length)
        throw new MiningException(MiningErrorCode.INVALID_INPUT_DATA);//"different numbers of source and attribute names");

      int n  = sourceNameDyn.length;
      permut = new int[n];
      MiningAttribute transformedAttribute[] = new MiningAttribute[n];

      for (int i = 0; i < n; i++) {
        int ind = -1;
        for (int j = 0; j < n; j++)
          if ( targetNameDyn[j].equals(sourceNameDyn[i]) ) {
            ind = j;
            break;
          }
        if (ind == -1)
          throw new MiningException(MiningErrorCode.INVALID_ARGUMENT);//"unknown target attribute name");

        MiningAttribute prAtt = getSourceAttribute(i);
        transformedAttribute[ind] = (MiningAttribute) prAtt.clone();

        permut[i] = ind;
      }

      return transformedAttribute;
  }

  /**
   * Change order of attribute values.
   *
   * @param attributeValues attribute values to be transformed
   * @return tranformed (permutated) values
   * @exception MiningException cannot transform attribute values
   */
  public double[] transformAttributeValue( double[] attributeValues ) throws MiningException
  {
    int n = permut.length;
    if (attributeValues == null || attributeValues.length != n)
      throw new MiningException(MiningErrorCode.INVALID_ARGUMENT);//"wrong number of attribute values");

    double transformedValue[] = new double[n];
    for (int i = 0; i < n; i++)
    {
      int ind = permut[i];
      transformedValue[ind] = attributeValues[i];
    };

    return transformedValue;
  }

}
