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
package org.eltech.ddm.transformation.OneToMultiple;

import java.util.Vector;

import org.eltech.ddm.miningcore.MiningDataException;
import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ECategoricalAttributeProperties;
import org.eltech.ddm.miningcore.miningdata.ECategory;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.transformation.OneToMultipleMapping;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeType;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.MiningAttribute;

//import com.prudsys.pdm.Core.NumericAttribute;

/**
 * Transforms a categorical attribute into a set of binary attributes
 * which are of numeric attribute type. The number of binary attributes
 * corresponds to the number of categories of the source attribute.
 * In general, not invertable.
 */
@SuppressWarnings("serial")
public class Binning extends OneToMultipleMapping
{
  // -----------------------------------------------------------------------
  //  Variables declarations
  // -----------------------------------------------------------------------
  /** Vector containing all categories of source attribute in given order. */
  @SuppressWarnings("rawtypes")
  private Vector categories = null;

  // -----------------------------------------------------------------------
  //  Constructor
  // -----------------------------------------------------------------------
  /**
   * Empty constructor.
   */
  public Binning()
  {
    oneToMultipleMapping = true;
  }

  // -----------------------------------------------------------------------
  //  Getter and setter methods
  // -----------------------------------------------------------------------
  /**
   * Returns category list.
   *
   * @return category list
   */
  @SuppressWarnings("rawtypes")
  public Vector getCategories()
  {
    return categories;
  }

  /**
   * Sets category list. If null is set, the list is created during the
   * transformAttribute operation.
   *
   * @param categories new category list
   */
  @SuppressWarnings("rawtypes")
  public void setCategories(Vector categories)
  {
    this.categories = categories;
  }

  // -----------------------------------------------------------------------
  //  Transformation methods
  // -----------------------------------------------------------------------
  /**
   * Transforms the source attribute. The result is the set of binary attributes
   * representing each category of the source attribute.
   *
   * If the category list is empty, the list is created from the source
   * attribute in the same order as the categories appear in the meta data.
   * Otherwise, the category list is used to define the order of the
   * binary attributes.
   *
   * If no target attribute names are defined (classifierName is null),
   * they are automatically created using the following rule:
   * <name of binary attribute i> = "tb_"+<source att. name>+<i-th category display value>
   *
   * @return transformed attributes
   * @exception MiningException could not transform attributes
   */
  public MiningAttribute[] transformAttribute() throws MiningException
  {
      // Get source attribute:
      MiningAttribute miningAtt = getSourceAttribute(-1);
      if (miningAtt == null)
        throw new MiningException(MiningErrorCode.INVALID_INDEX);//"Could not find source attribute");
      if (miningAtt.getAttributeType() != AttributeType.categorical)
        throw new MiningException(MiningErrorCode.UNSUPPORTED);//"Source attribute must be categorical");
      ELogicalAttribute categoricalAttribute = (ELogicalAttribute) miningAtt;
      ECategoricalAttributeProperties cap = categoricalAttribute.getCategoricalProperties();
      int n = cap.getSize();

      // Check for given category list:
      boolean useCatList = false;
      if (categories != null && categories.size() > 0)
        useCatList = true;
      else
        categories = new Vector();
      if (useCatList) n = categories.size();

      // Check for given target names:
      boolean useTarNames = false;
      if (classifierName != null && classifierName.length >= n)
        useTarNames = true;
      else
        classifierName = new String[n];

      // Create set of binary attributes:
     // ELogicalAttribute transformedAttribute[] = new ELogicalAttribute[n];
      /*NumericAttribute*/ELogicalAttribute transformedAttribute[] = new ELogicalAttribute[n];
      for (int i = 0; i < n; i++)
      {
          // No category list => fill list:
          if (! useCatList) {
            ECategory categ = categoricalAttribute.getCategoricalProperties().getCategory(i);//.getCategory(i);

            categories.addElement(categ);
          };

          // Get current category and key:
          ECategory categ = (ECategory) categories.elementAt(i);
          double key     = (double)categoricalAttribute.getCategoricalProperties().getIndex(categ);//categoricalAttribute.getKey(categ);
          if ( /*ECategory.isMissingValue(key)*/categ.isNullCategory() )
             throw new /*MiningException*/MiningDataException("source attribute contains unknown category");

          // Get target name:
          String bname;
          if (!useTarNames) {
            bname = "tb_" + featureName + "_" + categ.getDisplayName();//getDisplayValue();
            classifierName[i] = bname;
          }
          else {
            int ikey = (int) key;
            if ( ikey >= classifierName.length )
               throw new /*MiningException*/MiningDataException("classifierName index larger than key");
            bname = classifierName[ ikey ];
          };

          // Create binary attribute:
          transformedAttribute[i] = new ELogicalAttribute();//NumericAttribute();
          transformedAttribute[i].setName( bname );
          /*
          transformedAttribute[i].setCyclic( false );
          transformedAttribute[i].setDiscrete( true );
          transformedAttribute[i].setTime( false );
          transformedAttribute[i].setLowerBound( 0 );
          transformedAttribute[i].setUpperBound( 1 );*/
      }
      return transformedAttribute;
  }

  /**
   * Transforms one attribute value representing the key of a category.
   * The result is the array of values of all binary attributes.
   *
   * @param attributeValues value of attribute to be transformed
   * @return tranformed values
   * @exception MiningException could not transform attribute values
   */
  public double[] transformAttributeValue( double[] attributeValues ) throws MiningException
  {
    if (attributeValues == null || attributeValues.length == 0)
      throw new /*MiningException*/MiningDataException("No value to transform");

    int n = categories.size();
    double transformedValue[] = new double[n];
    for (int i = 0; i < n; i++)
    {   	
      if ( /*Category.isMissingValue(attributeValues[0])*/ attributeValues[0]==Double.NaN)
      {
          transformedValue[i] = Double.NaN;//Category.MISSING_VALUE;
          continue;
      }

      if( i == attributeValues[0] )
      {
          transformedValue[i] = 1;
      }
      else
      {
          transformedValue[i] = 0;
      }
    };

    return transformedValue;
  }
}