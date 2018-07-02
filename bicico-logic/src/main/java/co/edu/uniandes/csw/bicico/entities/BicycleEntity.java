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
package co.edu.uniandes.csw.bicico.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import co.edu.uniandes.csw.crud.spi.entity.BaseEntity;

import uk.co.jemos.podam.common.PodamExclude;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;


/**
 * @generated
 */
@Entity
public class BicycleEntity extends BaseEntity implements Serializable {





    @PodamExclude
    @OneToMany(mappedBy = "bicycle", cascade = CascadeType.REMOVE)
    private List<PhotoAlbumEntity> photoAlbum = new ArrayList<>();

    @PodamExclude
    @ManyToOne
    private BrandEntity brand;

    @PodamExclude
    @ManyToOne
    private CategoryEntity category;

    /**
     * Obtiene el atributo brand.
     *
     * @return atributo brand.
     * @generated
     */
    public BrandEntity getBrand() {
        return brand;
    }

    /**
     * Establece el valor del atributo brand.
     *
     * @param brand nuevo valor del atributo
     * @generated
     */
    public void setBrand(BrandEntity brand) {
        this.brand = brand;
    }

    /**
     * Obtiene el atributo category.
     *
     * @return atributo category.
     * @generated
     */
    public CategoryEntity getCategory() {
        return category;
    }

    /**
     * Establece el valor del atributo category.
     *
     * @param category nuevo valor del atributo
     * @generated
     */
    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    /**
     * Obtiene la colección de photoAlbum.
     *
     * @return colección photoAlbum.
     * @generated
     */
    public List<PhotoAlbumEntity> getPhotoAlbum() {
        return photoAlbum;
    }

    /**
     * Establece el valor de la colección de photoAlbum.
     *
     * @param photoAlbum nuevo valor de la colección.
     * @generated
     */
    public void setPhotoAlbum(List<PhotoAlbumEntity> photoalbum) {
        this.photoAlbum = photoalbum;
    }

}

