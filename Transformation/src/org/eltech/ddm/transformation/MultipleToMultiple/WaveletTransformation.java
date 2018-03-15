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
  * @author Michael Bolotnicov
  * @author Michael Thess
  * @version 1.1
  */
package org.eltech.ddm.transformation.MultipleToMultiple;

import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.transformation.MultipleToMultipleMapping;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.MiningAttribute;

/**
 * Realization of Wavelet transformation. Daubechies 4 type Wavelets
 * are used. Invertable: direct and inverse Wavelet transformations
 * can be both performed.
 *
 * Missing values are transformed into missing values.
 */
@SuppressWarnings("serial")
public class WaveletTransformation extends MultipleToMultipleMapping
{
  // -----------------------------------------------------------------------
  //  Variables declarations
  // -----------------------------------------------------------------------
  /** Object of wavelet transformation. */
  private DaubechiesWaveletTransform dwt = new DaubechiesWaveletTransform();

  /** Performs inverse wavelet transformation, otherwise direct. */
  private boolean inverseTransform = false;

  // -----------------------------------------------------------------------
  //  Constructor
  // -----------------------------------------------------------------------
  /**
   * Empty constructor.
   */
  public WaveletTransformation()
  {
  }

  // -----------------------------------------------------------------------
  //  Getter and setter methods
  // -----------------------------------------------------------------------
  /**
   * Is transformation inverse wavelet transformation?
   *
   * @return true if inverse, otherwise false
   */
  public boolean isInverseTransform()
  {
    return inverseTransform;
  }

  /**
   * Sets inverse type of wavelet transformation.
   *
   * @param inverseTransform true for inverse transformation, otherwise false
   */
  public void setInverseTransform(boolean inverseTransform)
  {
    this.inverseTransform = inverseTransform;
  }

  // -----------------------------------------------------------------------
  //  Transformation methods
  // -----------------------------------------------------------------------
  /**
   * Transforms the source attributes, identity transformation.
   *
   * @return transformed attributes
   * @exception MiningException cannot transform attributes
   */
  public MiningAttribute[] transformAttribute() throws MiningException
  {
      // Check if number of source attributes is power of 2 and in range:
      String[] sourceNameDyn = getSourceNameDynamic();
      int nSource     = sourceNameDyn.length;
      boolean isPower = false;
      int npow        = 4;
      for (int i = 0; i < 16; i++) {
        if (npow == nSource) isPower = true;
        npow = 2 * npow;
      };
      if (! isPower)
        throw new MiningException(MiningErrorCode.INVALID_ARGUMENT);//"Number of wavelet source attributes must be a power of 2!");

      // Copy source attributes:
      String[] targetNameDyn = getTargetNameDynamic();
      MiningAttribute transformedAttribute[] = new MiningAttribute[ nSource ];
      for(int i = 0; i < nSource; i++)
      {
        MiningAttribute prAtt = getSourceAttribute(i);
        if (prAtt instanceof ELogicalAttribute) {
          transformedAttribute[i] = (ELogicalAttribute) ( (ELogicalAttribute) prAtt).clone();
          transformedAttribute[i].setName( targetNameDyn[i] );
        }
        else {
          throw new MiningException(MiningErrorCode.UNSUPPORTED);//"No wavelet transformation for categorical attributes");
        };
      };

      return transformedAttribute;
  }

  /**
   * Performs wavelet transformation on source attributes.
   *
   * @param attributeValues values of attributes to be transformed
   * @return tranformed values
   * @exception MiningException cannot transform attribute values
   */
  public double[] transformAttributeValue( double[] attributeValues ) throws MiningException
  {
    // Copy data to transformation vector:
    int n = attributeValues.length;
    double transformedValues[] = new double[n];
    for (int i = 0; i < n; i++)
      transformedValues[i] = attributeValues[i];

    // Run transformation:
    int type = 1;
    if (inverseTransform)
      type = -1;
    dwt.Transform(transformedValues, n, type);

    return transformedValues;
  }
}