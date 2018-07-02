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
package co.edu.uniandes.csw.bicico.ejbs;

import co.edu.uniandes.csw.bicico.api.IBrandLogic;
import co.edu.uniandes.csw.bicico.entities.BrandEntity;
import co.edu.uniandes.csw.bicico.persistence.BrandPersistence;
import co.edu.uniandes.csw.bicico.entities.BicycleEntity;
import co.edu.uniandes.csw.bicico.api.IBicycleLogic;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;



/**
 * @generated
 */
@Stateless
public class BrandLogic implements IBrandLogic {

    @Inject private BrandPersistence persistence;


    @Inject private IBicycleLogic bicycleLogic;

    /**
     * Obtiene el número de registros de Brand.
     *
     * @return Número de registros de Brand.
     * @generated
     */
    public int countBrands() {
        return persistence.count();
    }

    /**
     * Obtiene la lista de los registros de Brand.
     *
     * @return Colección de objetos de BrandEntity.
     * @generated
     */
    @Override
    public List<BrandEntity> getBrands() {
        return persistence.findAll();
    }

    /**
     * Obtiene la lista de los registros de Brand indicando los datos para la paginación.
     *
     * @param page Número de página.
     * @param maxRecords Número de registros que se mostraran en cada página.
     * @return Colección de objetos de BrandEntity.
     * @generated
     */
    @Override
    public List<BrandEntity> getBrands(Integer page, Integer maxRecords) {
        return persistence.findAll(page, maxRecords);
    }

    /**
     * Obtiene los datos de una instancia de Brand a partir de su ID.
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de BrandEntity con los datos del Brand consultado.
     * @generated
     */
    public BrandEntity getBrand(Long id) {
        return persistence.find(id);
    }

    /**
     * Se encarga de crear un Brand en la base de datos.
     *
     * @param entity Objeto de BrandEntity con los datos nuevos
     * @return Objeto de BrandEntity con los datos nuevos y su ID.
     * @generated
     */
    @Override
    public BrandEntity createBrand(BrandEntity entity) {

        persistence.create(entity);
        return entity;
    }

    /**
     * Actualiza la información de una instancia de Brand.
     *
     * @param entity Instancia de BrandEntity con los nuevos datos.
     * @return Instancia de BrandEntity con los datos actualizados.
     * @generated
     */
    @Override
    public BrandEntity updateBrand(BrandEntity entity) {
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Brand de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     * @generated
     */
    @Override
    public void deleteBrand(Long id) {
        persistence.delete(id);
    }
  

    /**
     * Obtiene una colección de instancias de BicycleEntity asociadas a una
     * instancia de Brand
     *
     * @param brandId Identificador de la instancia de Brand
     * @return Colección de instancias de BicycleEntity asociadas a la instancia de Brand
     * @generated
     */
    @Override
    public List<BicycleEntity> listBicycle(Long brandId) {
        return getBrand(brandId).getBicycle();
    }

    /**
     * Obtiene una instancia de BicycleEntity asociada a una instancia de Brand
     *
     * @param brandId Identificador de la instancia de Brand
     * @param bicycleId Identificador de la instancia de Bicycle
     * @generated
     */
    @Override
    public BicycleEntity getBicycle(Long brandId, Long bicycleId) {
        List<BicycleEntity> list = getBrand(brandId).getBicycle();
        BicycleEntity bicycleEntity = new BicycleEntity();
        bicycleEntity.setId(bicycleId);
        int index = list.indexOf(bicycleEntity);
        if (index >= 0) {
            return list.get(index);
        }
        return null;
    }

    /**
     * Asocia un Bicycle existente a un Brand
     *
     * @param brandId Identificador de la instancia de Brand
     * @param bicycleId Identificador de la instancia de Bicycle
     * @return Instancia de BicycleEntity que fue asociada a Brand
     * @generated
     */
    @Override
    public BicycleEntity addBicycle(Long brandId, Long bicycleId) {
        BrandEntity brandEntity = getBrand(brandId);
        BicycleEntity bicycleEntity = bicycleLogic.getBicycle(bicycleId);
        bicycleEntity.setBrand(brandEntity);
        return bicycleEntity;
    }

    /**
     * Remplaza las instancias de Bicycle asociadas a una instancia de Brand
     *
     * @param brandId Identificador de la instancia de Brand
     * @param list Colección de instancias de BicycleEntity a asociar a instancia de Brand
     * @return Nueva colección de BicycleEntity asociada a la instancia de Brand
     * @generated
     */
    @Override
    public List<BicycleEntity> replaceBicycle(Long brandId, List<BicycleEntity> list) {
        BrandEntity brandEntity = getBrand(brandId);
        List<BicycleEntity> bicycleList = bicycleLogic.getBicycles();
        for (BicycleEntity bicycle : bicycleList) {
            if (list.contains(bicycle)) {
                bicycle.setBrand(brandEntity);
            } else {
                if (bicycle.getBrand() != null && bicycle.getBrand().equals(brandEntity)) {
                    bicycle.setBrand(null);
                }
            }
        }
        brandEntity.setBicycle(list);
        return brandEntity.getBicycle();
    }

    /**
     * Desasocia un Bicycle existente de un Brand existente
     *
     * @param brandId Identificador de la instancia de Brand
     * @param bicycleId Identificador de la instancia de Bicycle
     * @generated
     */
    @Override
    public void removeBicycle(Long brandId, Long bicycleId) {
        BicycleEntity entity = bicycleLogic.getBicycle(bicycleId);
        entity.setBrand(null);
    }
}
