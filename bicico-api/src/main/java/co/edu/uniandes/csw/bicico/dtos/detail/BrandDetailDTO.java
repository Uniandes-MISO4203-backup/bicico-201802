/*
The MIT License (MIT)

Copyright (c) 2015 Los Andes University

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package co.edu.uniandes.csw.bicico.dtos.detail;

import co.edu.uniandes.csw.bicico.dtos.minimum.*;
import co.edu.uniandes.csw.bicico.entities.BrandEntity;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.ArrayList;
import co.edu.uniandes.csw.bicico.entities.BicycleEntity;

import uk.co.jemos.podam.common.PodamExclude;

/**
 * @generated
 */
@XmlRootElement
public class BrandDetailDTO extends BrandDTO{



@PodamExclude
private List<BicycleDTO> bicycle;


public BrandDetailDTO() {
        super();
    }

 public BrandDetailDTO(BrandEntity entity) {
  super(entity); 
 List<BicycleDTO> lbicycle = new ArrayList<>();
 if (entity.getBicycle()!=null){
  for(BicycleEntity ent : entity.getBicycle()){
  lbicycle.add(new BicycleDTO(ent));
 }
 this.bicycle = lbicycle;
 }
 }

   public BrandEntity toEntity() {
  BrandEntity entity = (BrandEntity) super.toEntity();
  List<BicycleEntity> lbicycle = new ArrayList<>();
  if (this.getBicycle()!=null){
    for(BicycleDTO dto : this.getBicycle()){
  lbicycle.add(dto.toEntity());
      
   }       
    entity.setBicycle(lbicycle);  
   }
  return entity;
  }

 public List<BicycleDTO> getBicycle() {
        return bicycle;
    }
 
    public void setBicycle(List<BicycleDTO> bicycle) {
        this.bicycle = bicycle;
    }



}
