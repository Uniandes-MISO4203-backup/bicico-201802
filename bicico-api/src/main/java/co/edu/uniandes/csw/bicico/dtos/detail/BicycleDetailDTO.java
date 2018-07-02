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
import co.edu.uniandes.csw.bicico.entities.BicycleEntity;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.ArrayList;
import co.edu.uniandes.csw.bicico.entities.PhotoAlbumEntity;
import co.edu.uniandes.csw.bicico.entities.BrandEntity;
import co.edu.uniandes.csw.bicico.entities.CategoryEntity;

import uk.co.jemos.podam.common.PodamExclude;

/**
 * @generated
 */
@XmlRootElement
public class BicycleDetailDTO extends BicycleDTO{



@PodamExclude
private List<PhotoAlbumDTO> photoAlbum;
@PodamExclude
private BrandDTO brand;
@PodamExclude
private CategoryDTO category;


public BicycleDetailDTO() {
        super();
    }

 public BicycleDetailDTO(BicycleEntity entity) {
  super(entity); 
 List<PhotoAlbumDTO> lphotoAlbum = new ArrayList<>();
 if (entity.getPhotoAlbum()!=null){
  for(PhotoAlbumEntity ent : entity.getPhotoAlbum()){
  lphotoAlbum.add(new PhotoAlbumDTO(ent));
 }
 this.photoAlbum = lphotoAlbum;
 }
 if (entity.getBrand()!=null){
 this.brand = new BrandDTO(entity.getBrand());
 }
 if (entity.getCategory()!=null){
 this.category = new CategoryDTO(entity.getCategory());
 }
 }

   public BicycleEntity toEntity() {
  BicycleEntity entity = (BicycleEntity) super.toEntity();
  List<PhotoAlbumEntity> lphotoAlbum = new ArrayList<>();
  if (this.getPhotoAlbum()!=null){
    for(PhotoAlbumDTO dto : this.getPhotoAlbum()){
  lphotoAlbum.add(dto.toEntity());
      
   }       
    entity.setPhotoAlbum(lphotoAlbum);  
   }
   if (this.getBrand()!=null){
          entity.setBrand(this.getBrand().toEntity());
          }
   if (this.getCategory()!=null){
          entity.setCategory(this.getCategory().toEntity());
          }
  return entity;
  }

 public List<PhotoAlbumDTO> getPhotoAlbum() {
        return photoAlbum;
    }
 
    public void setPhotoAlbum(List<PhotoAlbumDTO> photoAlbum) {
        this.photoAlbum = photoAlbum;
    }

public BrandDTO getBrand() {
        return brand;
    }
 
    public void setBrand(BrandDTO brand) {
        this.brand = brand;
    }
public CategoryDTO getCategory() {
        return category;
    }
 
    public void setCategory(CategoryDTO category) {
        this.category = category;
    }


}
