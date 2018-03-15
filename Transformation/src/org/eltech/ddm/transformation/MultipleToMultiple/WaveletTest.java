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
  * @version 1.0
  */
package org.eltech.ddm.transformation.MultipleToMultiple;

/**
 * Tests of wavelets.
 */
public class WaveletTest
{

  public static void main(String[] args)
  {
    double[] idata = new double[1024];
    DaubechiesWaveletTransform dwt = new DaubechiesWaveletTransform();
    int i;

    // Fill data:
    for (i=0; i<1024; i++)
//      idata[i]=-512+((double)i);
        idata[i] = i*i;

    // Forward transform:
    dwt.Transform(idata,1024,+1);

    System.out.println("Wavelet basis coefficients:");
    System.out.print("{");

    for (i=1; i<1024; i++)
    {
      System.out.print("[");
      System.out.print(i);
      System.out.print(",");
      if (Math.abs(idata[i])>0.0001) System.out.print(idata[i]);
      else System.out.print("0");
      System.out.print("]");
      if (i<1023) System.out.print(",");
    }
    System.out.println("}");
    System.out.println();

//*******************************************************************
//  ... put coefficient thresholding for smooting here ...
//*******************************************************************

    // Backward transform:
    dwt.Transform(idata,1024,-1);

    System.out.println("Reconstructed function:");
    System.out.print("{");

    for (i=1; i<1024; i++)
    {
      System.out.print("[");
      System.out.print(i);
      System.out.print(",");
      if (Math.abs(idata[i])>0.0001) System.out.print(idata[i]);
      else System.out.print("0");
      System.out.print("]");
      if (i<1023) System.out.print(",");
    }
    System.out.println("}");
  }

}

