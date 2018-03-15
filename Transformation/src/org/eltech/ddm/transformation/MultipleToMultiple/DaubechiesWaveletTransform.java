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
 * Defines discrete wavelet transform methods for Daubechies DAUB4 basis.
 */
@SuppressWarnings("serial")
public class DaubechiesWaveletTransform extends org.omg.java.cwm.objectmodel.core.Class
{
		/// <summary>
		/// Daubechies DAUB4 quadrature mirror filter coefficients
		/// </summary>
		private final double C0=0.4829629131445341;
		private final double C1=0.8365163037378079;
		private final double C2=0.2241438680420134;
		private final double C3=-0.1294095225512604;

		/// <summary>
		/// Empty constructor
		/// </summary>
		public DaubechiesWaveletTransform()
		{
		}

		/// <summary>
		/// One step of the DWT on Daubechies' DAUB4 basis
		/// </summary>
		/// <param name="data">Array of input values</param>
		/// <param name="N">Size of transform, must be a power of two!</param>
		/// <param name="tdir">transform direction +1 or -1</param>
		private void Daub4(double[] data,int N, int tdir)
		{
			int i,j,nh=N>>1;
			double[] wksp=new double[N];

			if (tdir>=0)
			{
				// Apply corresponding quadrature mirror filters
				for (i=0, j=0; j<N-3; i++,j+=2)
				{
					wksp[i]=C0*data[j]+C1*data[j+1]+C2*data[j+2]+C3*data[j+3];
					wksp[i+nh]=C3*data[j]-C2*data[j+1]+C1*data[j+2]-C0*data[j+3];
				}
				// Manage the wrap-around
				wksp[i]=C0*data[N-2]+C1*data[N-1]+C2*data[0]+C3*data[1];
				wksp[i+nh]=C3*data[N-2]-C2*data[N-1]+C1*data[0]-C0*data[1];
			}
			else
			{
				// Manage the wrap-around
				wksp[0]=C0*data[0]+C1*data[N-1]+C2*data[nh-1]+C3*data[nh];
				wksp[1]=C3*data[nh-1]-C2*data[nh]+C1*data[0]-C0*data[N-1];
				// Apply corresponding quadrature mirror filters
				for (i=0, j=2; i<(nh-1); i++)
				{
					wksp[j++]=C0*data[i+1]+C1*data[i+nh]+C2*data[i]+C3*data[i+nh+1];
					wksp[j++]=C3*data[i]-C2*data[i+nh+1]+C1*data[i+1]-C0*data[i+nh];
				}
			}

			// Copy workspace back into data
			for (i=0; i<N; i++) data[i]=wksp[i];
		}

        /// <summary>
        /// One-dimentional discrete wavelet transform using Daubechies' DAUB4 basis
        /// </summary>
        /// <param name="data">Array of input values</param>
        /// <param name="N">Size of transform, must be a power of two!</param>
        /// <param name="tdir">transform direction +1 or -1</param>
		public void Transform(double[] data,int N,int tdir)
		{
			int i;
			if (tdir>=0)
			{
				// Work from the largest hierarchy towards the smallest
				for (i=N; i>=4; i>>=1) Daub4(data,i,+1);
			}
			else
			{
				// Work from the smallest hierarchy towards the largest
				for (i=4; i<=N; i<<=1) Daub4(data,i,-1);
			}
		}
}
