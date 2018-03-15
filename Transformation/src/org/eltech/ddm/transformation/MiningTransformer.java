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
  * @author Valentine Stepanenko (valentine.stepanenko@zsoft.ru)
  * @version 1.0
  */

package org.eltech.ddm.transformation;

import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;

/**
 * An interface that defines an online transformation while reading data from
 * the MiningFilterStream. First of all it allows to transform the mining stream
 * metadata. Then it transforms each mining vector in passing.
 *
 * @see MiningFilterStream
 * @see MiningDataSpecification
 * @see MiningVector
 */
public interface MiningTransformer
{
    // -----------------------------------------------------------------------
    //  Transformation methods
    // -----------------------------------------------------------------------
    /**
     * Transforms mining vector.
     *
     * @param vector mining vector to transform
     * @return transformed mining vector
     * @exception MiningException cannot transform mining vector
     */
    public MiningVector transform( MiningVector vector ) throws MiningException;

    /**
     * Transforms meta data. Often, this is an idential transformation.
     *
     * @param metaData meta data to transform
     * @return transformed meta data
     * @exception MiningException cannot transform meta data
     */
    public ELogicalData transform( ELogicalData metaData ) throws MiningException;
}
