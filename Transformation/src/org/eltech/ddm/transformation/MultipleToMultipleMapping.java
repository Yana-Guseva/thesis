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
  * @author Valentine Stepanenko (ValentineStepanenko@zsoft.ru)
  * @author Michael Thess
  * @version 1.1
  */

package org.eltech.ddm.transformation;

import java.util.Vector;

import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.MiningAttribute;
import org.omg.java.cwm.analysis.transformation.ClassifierFeatureMap;
import org.omg.java.cwm.analysis.transformation.ClassifierMap;
import org.omg.java.cwm.analysis.transformation.FeatureMap;


/**
 * Transforms multiple attributes into multiple attributes.<p>
 *
 * This is done in two ways: an inherit multiple-to-multiple
 * mapping can be defined via the transformAttribute and
 * transformAttributeValue methods in all transformations
 * extending this class. <p>
 *
 * In addition, the arrays featureMap and cfMap (of the mother
 * class ClassifierMap) allow to include objects for ono-to-one
 * mappings (featureMap) and one-to-multiple mappings (cfMap)
 * representing the corresponding special cases of transformations. <p>
 *
 * The following correspondends of terms to CWM transformation holds:
 * classifier map (CWM)         <-> multiple-to-multiple map (XELOPES)
 * classifier feature map (CWM) <-> one-to-multiple map (XELOPES)
 * feature map (CWM)            <-> one-to-one map (XELOPES)
 */
@SuppressWarnings("serial")
public class MultipleToMultipleMapping extends ClassifierMap implements MiningTransformer
{
  // -----------------------------------------------------------------------
  //  Variables declarations
  // -----------------------------------------------------------------------
  /** Names of source attributes. */
  protected String[] sourceName;

  /** Names of target attributes. */
  protected String[] targetName;

  /** Remove source attribute after its transformation? */
  protected boolean removeSourceAttributes = true;

  /** Pretransformed meta data. */
  protected ELogicalData pretransformedMetaData;

  /** Transformed meta data. */
  protected ELogicalData transformedMetaData;

  // -----------------------------------------------------------------------
  //  Constructor
  // -----------------------------------------------------------------------
  /**
   * Empty constructor.
   */
  public MultipleToMultipleMapping()
  {
  }

  // -----------------------------------------------------------------------
  //  Getter and setter methods
  // -----------------------------------------------------------------------
  /**
   * Sets new names of source attributes.
   *
   * @param sourceName new names of source attributes
   */
  public void setSourceName(String[] sourceName)
  {
    this.sourceName = sourceName;
  }

  /**
   * Returns names of source attributes.
   *
   * @return source attribute names
   */
  public String[] getSourceName()
  {
    return sourceName;
  }

  /**
   * Sets new names of target attributes.
   *
   * @param targetName new names of target attributes
   */
  public void setTargetName(String[] targetName)
  {
    this.targetName = targetName;
  }

  /**
   * Returns names of target attributes.
   *
   * @return names of target attributes
   */
  public String[] getTargetName()
  {
    return targetName;
  }

  /**
   * Set remove all source attributes after transformation (default: no).
   *
   * @param removeSourceAttributes set remove source attributes after transformation
   */
  public void setRemoveSourceAttributes(boolean removeSourceAttributes)
  {
    this.removeSourceAttributes = removeSourceAttributes;
  }

  /**
   * Remove all source attributes after transformation (default: no)?
   *
   * @return true if remove source attributes after trafo, otherwise false
   */
  public boolean isRemoveSourceAttributes()
  {
    return removeSourceAttributes;
  }

  /**
   * Sets array of feature maps.
   *
   * @param featureMap new array of feature maps
   */
  public void setOneToOneMapping(FeatureMap[] featureMap) {

    this.featureMap = featureMap;

    for (int i = 0; i < featureMap.length; i++)
      featureMap[i].classifierMap = this;
  }

  /**
   * Returns array of feature maps.
   *
   * @return array of feature maps
   */
  public FeatureMap[] getOneToOneMapping() {

    return featureMap;
  }

  /**
   * Sets array of classifier feature maps.
   *
   * @param cfMap new array of classifier feature maps
   */
  public void setOneToMultipleMapping(ClassifierFeatureMap[] cfMap) {

    this.cfMap = cfMap;

    for (int i = 0; i < cfMap.length; i++)
      cfMap[i].classifierMap = this;
  }

  /**
   * Returns array of classifier feature maps.
   *
   * @return array of classifier feature maps
   */
  public ClassifierFeatureMap[] getOneToMultipleMapping() {

    return cfMap;
  }

  /**
   * Returns source attribute names. If no source attribute names are defined,
   * all attributes names are considered as source names.
   *
   * @return dynamic source attributes
 * @throws MiningException 
   */
  protected String[] getSourceNameDynamic() throws MiningException {

     if (sourceName == null) {
       ELogicalData metaData = getSourceMetaData();
       int nSource = metaData.getAttributesNumber();
       String[] sn = new String[nSource];
       for (int i = 0; i < nSource; i++)
         sn[i] = metaData.getAttribute(i).getName();
       return sn;
     }
     else
       return sourceName;
  }

  /**
   * Returns target attribute names.
   *
   * @return dynamic target attributes
   */
  protected String[] getTargetNameDynamic() {

    return targetName;
  }

  /**
   * Returns source attribute at specified index.
   * If no source attributes are defined, all attributes
   * are considered as source.
   *
   * @param index index of source attribute
   * @return source attribute, null if not found
 * @throws MiningException 
   */
  protected MiningAttribute getSourceAttribute(int index) throws MiningException {

    ELogicalData metaData = getSourceMetaData();

    // No source attributes specified => use whole meta data:
    if (sourceName == null)
      return metaData.getAttribute(index);

    // Source attributes given => use index of source attributes:
    if (index < 0 || index >= sourceName.length)
      return null;
    ELogicalAttribute attribute = metaData.getAttribute(sourceName[index]);

    return attribute;
  }

  /**
   * Returns meta data of sources, i.e. the pretransformed meta data.
   *
   * @return source meta data
   */
  protected ELogicalData getSourceMetaData() {
    return pretransformedMetaData;
  }

  /**
   * Returns true if this class implements transformAttribute and
   * transformAttributeValues methods.
   *
   * Not very nice implementation because it checks the absolute
   * class path.
   *
   * @return true if transformations are implemented, else false
   */
  protected boolean containsMultipleToMultipleMapping() {

    if (getClass().getName().equals( "com.prudsys.pdm.Transform.MultipleToMultipleMapping" ))
      return false;

    return true;
  }

  // -----------------------------------------------------------------------
  //  Transformation methods
  // -----------------------------------------------------------------------
  /**
   * Transforms the source attributes. Must be implemented by classes
   * extending this class.
   *
   * @return transformed attributes
   * @exception MiningException cannot transform source attributes
   */
  public MiningAttribute[] transformAttribute() throws MiningException
  {
      throw new java.lang.UnsupportedOperationException("Method transformAttribute not implemented.");
  }

  /**
   * Transforms the attribute values. Must be implemented by classes extending
   * this class.
   *
   * @param attributeValues values of attribute to be transformed
   * @return tranformed values
   * @exception MiningException cannot transform attribute values
   */
  public double[] transformAttributeValue( double[] attributeValues ) throws MiningException
  {
      throw new java.lang.UnsupportedOperationException("Method transformAttributeValue not implemented.");
  }

  /**
   * Transforms mining vector.
   *
   * Notice that this method can also be applied to a mining vector without
   * own meta data. In this case the last meta data transformation is used
   * as transformation ressource. Such last meta data transformation could have
   * been done either explicitly, applying transform to some meta data, or
   * implicitely, when a mining vactor with meta data was transformed. If
   * none of both cases has ever happened, for the mining vector (without
   * meta data) an exception is thrown.
   *
   * @param vector mining vector to transform, must contain meta data
   * @return transformed mining vector, without meta data
   * @exception MiningException cannot transform mining vector
   */
  public MiningVector transform( MiningVector vector ) throws MiningException {

    // Transform meta data:
    ELogicalData vecMetaData = vector.getLogicalData();
    if (vecMetaData != null) {
      if (vecMetaData != pretransformedMetaData)
        transformedMetaData = transform(vecMetaData); // Caching
    }
    else {
      if (transformedMetaData == null)
        throw new MiningException(MiningErrorCode.INVALID_DATA_TYPE); //"No meta data ressource for trafo available");
    }

    // Transform mining vector:
    double values[] = new double[transformedMetaData.getAttributesNumber()];
    int k = 0; // absolute value coordinate index

    // Transformations from mining feature map:
    int nfMap = 0;
    if (featureMap != null)
      nfMap = featureMap.length;
    for (int i = 0; i < nfMap; i++) {
      double attributeValue   = vector.getValue( ((OneToOneMapping) featureMap[i]).getSourceNameDynamic() );
      double transformedValue = ((OneToOneMapping) featureMap[i]).transformAttributeValue( attributeValue );
      values[k]               = transformedValue;
      k = k + 1;
    };

    // Transformations from mining classifier feature map:
    int ncfMap = 0;
    if (cfMap != null)
      ncfMap = cfMap.length;
    for (int i = 0; i < ncfMap; i++) {
      String[] sourceNameDyn = ( (OneToMultipleMapping) cfMap[i] ).getSourceNameDynamic();
      double[] attributeValue = new double[ sourceNameDyn.length ];
      for (int j = 0; j < sourceNameDyn.length; j++)
        attributeValue[j] = vector.getValue( sourceNameDyn[j] );
      double transformedValue[] = ( (OneToMultipleMapping) cfMap[i] ).transformAttributeValue(attributeValue);
      System.arraycopy(transformedValue, 0, values, k, transformedValue.length);
      k = k + transformedValue.length;
    }

    // Transformations from mining classifier class extensions:
    if (containsMultipleToMultipleMapping()) {
      String[] sourceNameDyn  = getSourceNameDynamic();
      double[] attributeValue = new double[ sourceNameDyn.length ];
      for (int j = 0; j < sourceNameDyn.length; j++)
        attributeValue[j] = vector.getValue( sourceNameDyn[j] );
      double transformedValue[] = transformAttributeValue(attributeValue);
      System.arraycopy( transformedValue, 0, values, k, transformedValue.length);
      k = k + transformedValue.length;
    };

    // Add remaining attributes:
    for (int i = k; i < transformedMetaData.getAttributesNumber(); i++)
      values[i] = vector.getValue( transformedMetaData.getAttribute(i) );

    // Create transformed mining vector:
    MiningVector transformedVector = new MiningVector( values );

    return transformedVector;
  }

  /**
   * Transforms meta data. Often, this is an idential transformation.
   *
   * @param metaData meta data to transform
   * @return transformed meta data
   * @exception MiningException cannot transform meta data
   */
  @SuppressWarnings("unchecked")
public ELogicalData transform( ELogicalData metaData ) throws MiningException {

    // Caching:
    pretransformedMetaData = metaData;

    // Init:
    transformedMetaData = new ELogicalData();
    transformedMetaData.setName( "t_" + metaData.getName() );
    String[] sourceNameDyn = getSourceNameDynamic();

    // Vector of attributes already used in transformation:
	@SuppressWarnings("rawtypes")
	Vector usedSourceAtt = new Vector();

    // Transformations from mining feature map:
    int nfMap = 0;
    if (featureMap != null)
      nfMap = featureMap.length;
    for (int i = 0; i < nfMap; i++) {
      MiningAttribute attribute = ((OneToOneMapping) featureMap[i]).transformAttribute();
      transformedMetaData.addAttribute( (ELogicalAttribute) attribute );

      // Add to list of used source attributes:
      if ( ((OneToOneMapping) featureMap[i]).isRemoveSourceAttribute() )
        usedSourceAtt.addElement( ((OneToOneMapping) featureMap[i]).getSourceNameDynamic() );
    };

    // Transformations from mining classifier feature map:
    int ncfMap = 0;
    if (cfMap != null)
      ncfMap = cfMap.length;
    for (int i = 0; i < ncfMap; i++) {
      MiningAttribute[] attributes = ((OneToMultipleMapping) cfMap[i]).transformAttribute();
      for (int j = 0; j < attributes.length; j++)
        transformedMetaData.addAttribute( (ELogicalAttribute) attributes[j] );

      // Add to list of used source attributes:
      if ( ((OneToMultipleMapping) cfMap[i]).isRemoveSourceAttributes() ) {
        String[] sndyn = ((OneToMultipleMapping) cfMap[i]).getSourceNameDynamic();
        for (int j = 0; j < sndyn.length; j++)
          usedSourceAtt.addElement(sndyn[j]);
      };
    };

    // Transformations from mining classifier class extensions:
    if (containsMultipleToMultipleMapping()) {
      MiningAttribute[] attributes = transformAttribute();
      for (int j = 0; j < attributes.length; j++)
        transformedMetaData.addAttribute( (ELogicalAttribute) attributes[j] );

      // Add to list of used source attributes:
      if (isRemoveSourceAttributes()) {
        for (int i = 0; i < sourceNameDyn.length; i++)
          usedSourceAtt.addElement( sourceNameDyn[i] );
      };
    };

    // Add all remaining attributes:
    for (int i = 0; i < metaData.getAttributesNumber(); i++) {
      MiningAttribute att = metaData.getAttribute(i);
      if (! usedSourceAtt.contains( att.getName() ) )
        transformedMetaData.addAttribute((ELogicalAttribute) att);
    };

    return transformedMetaData;
  }

  /**
   * Returns string representation of multiple-to-multiple map.
   *
   * @return mining multiple-to-multiple map string representation
   */
  public String toString() {

    String res = "--------Multiple-to-multiple map. " + "\n";
    res        = res + "Source names: ";
    if (sourceName != null)
      for (int i = 0; i < sourceName.length; i++)
        res = res + sourceName[i] + ", ";
    res        = res + "\n";
    res        = res + "Target names: ";
    if (targetName != null)
      for (int i = 0; i < targetName.length; i++)
        res = res + targetName[i] + ", ";
    res        = res + "\n";
    res        = res + "removeSourceAttributes: " + removeSourceAttributes + "\n";
    int nfMap = 0;
    if (featureMap != null)
      nfMap = featureMap.length;
    int ncfMap = 0;
    if (cfMap != null)
      ncfMap = cfMap.length;
    res        = res + "Contains " +
                 String.valueOf( nfMap ) +
                 " one-to-one maps and " +
                 String.valueOf( ncfMap ) +
                 " one-to-multiple maps: " +
                 "\n";
    for (int i = 0; i < nfMap; i++) {
      res = res + "        OneToOneMapping " + String.valueOf(i) + ":" + "\n" +
            featureMap[i].toString();
      if (i < featureMap.length - 1)
        res = res + "\n";
    };
    for (int i = 0; i < ncfMap; i++) {
      res = res + "        OneToMultipleMapping " + String.valueOf(i) + ":" + "\n" +
            cfMap[i].toString();
      if (i < cfMap.length - 1)
        res = res + "\n";
    };

    return res;
  }
}
