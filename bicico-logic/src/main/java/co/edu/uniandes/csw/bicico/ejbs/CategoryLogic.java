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

import co.edu.uniandes.csw.bicico.api.ICategoryLogic;
import co.edu.uniandes.csw.bicico.entities.CategoryEntity;
import co.edu.uniandes.csw.bicico.persistence.CategoryPersistence;
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
public class CategoryLogic implements ICategoryLogic {

    @Inject private CategoryPersistence persistence;


    @Inject private IBicycleLogic bicycleLogic;

    /**
     * Obtiene el número de registros de Category.
     *
     * @return Número de registros de Category.
     * @generated
     */
    public int countCategorys() {
        return persistence.count();
    }

    /**
     * Obtiene la lista de los registros de Category.
     *
     * @return Colección de objetos de CategoryEntity.
     * @generated
     */
    @Override
    public List<CategoryEntity> getCategorys() {
        return persistence.findAll();
    }

    /**
     * Obtiene la lista de los registros de Category indicando los datos para la paginación.
     *
     * @param page Número de página.
     * @param maxRecords Número de registros que se mostraran en cada página.
     * @return Colección de objetos de CategoryEntity.
     * @generated
     */
    @Override
    public List<CategoryEntity> getCategorys(Integer page, Integer maxRecords) {
        return persistence.findAll(page, maxRecords);
    }

    /**
     * Obtiene los datos de una instancia de Category a partir de su ID.
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de CategoryEntity con los datos del Category consultado.
     * @generated
     */
    public CategoryEntity getCategory(Long id) {
        return persistence.find(id);
    }

    /**
     * Se encarga de crear un Category en la base de datos.
     *
     * @param entity Objeto de CategoryEntity con los datos nuevos
     * @return Objeto de CategoryEntity con los datos nuevos y su ID.
     * @generated
     */
    @Override
    public CategoryEntity createCategory(CategoryEntity entity) {

        persistence.create(entity);
        return entity;
    }

    /**
     * Actualiza la información de una instancia de Category.
     *
     * @param entity Instancia de CategoryEntity con los nuevos datos.
     * @return Instancia de CategoryEntity con los datos actualizados.
     * @generated
     */
    @Override
    public CategoryEntity updateCategory(CategoryEntity entity) {
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Category de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     * @generated
     */
    @Override
    public void deleteCategory(Long id) {
        persistence.delete(id);
    }
  

    /**
     * Obtiene una colección de instancias de BicycleEntity asociadas a una
     * instancia de Category
     *
     * @param categoryId Identificador de la instancia de Category
     * @return Colección de instancias de BicycleEntity asociadas a la instancia de Category
     * @generated
     */
    @Override
    public List<BicycleEntity> listBicycle(Long categoryId) {
        return getCategory(categoryId).getBicycle();
    }

    /**
     * Obtiene una instancia de BicycleEntity asociada a una instancia de Category
     *
     * @param categoryId Identificador de la instancia de Category
     * @param bicycleId Identificador de la instancia de Bicycle
     * @generated
     */
    @Override
    public BicycleEntity getBicycle(Long categoryId, Long bicycleId) {
        List<BicycleEntity> list = getCategory(categoryId).getBicycle();
        BicycleEntity bicycleEntity = new BicycleEntity();
        bicycleEntity.setId(bicycleId);
        int index = list.indexOf(bicycleEntity);
        if (index >= 0) {
            return list.get(index);
        }
        return null;
    }

    /**
     * Asocia un Bicycle existente a un Category
     *
     * @param categoryId Identificador de la instancia de Category
     * @param bicycleId Identificador de la instancia de Bicycle
     * @return Instancia de BicycleEntity que fue asociada a Category
     * @generated
     */
    @Override
    public BicycleEntity addBicycle(Long categoryId, Long bicycleId) {
        CategoryEntity categoryEntity = getCategory(categoryId);
        BicycleEntity bicycleEntity = bicycleLogic.getBicycle(bicycleId);
        bicycleEntity.setCategory(categoryEntity);
        return bicycleEntity;
    }

    /**
     * Remplaza las instancias de Bicycle asociadas a una instancia de Category
     *
     * @param categoryId Identificador de la instancia de Category
     * @param list Colección de instancias de BicycleEntity a asociar a instancia de Category
     * @return Nueva colección de BicycleEntity asociada a la instancia de Category
     * @generated
     */
    @Override
    public List<BicycleEntity> replaceBicycle(Long categoryId, List<BicycleEntity> list) {
        CategoryEntity categoryEntity = getCategory(categoryId);
        List<BicycleEntity> bicycleList = bicycleLogic.getBicycles();
        for (BicycleEntity bicycle : bicycleList) {
            if (list.contains(bicycle)) {
                bicycle.setCategory(categoryEntity);
            } else {
                if (bicycle.getCategory() != null && bicycle.getCategory().equals(categoryEntity)) {
                    bicycle.setCategory(null);
                }
            }
        }
        categoryEntity.setBicycle(list);
        return categoryEntity.getBicycle();
    }

    /**
     * Desasocia un Bicycle existente de un Category existente
     *
     * @param categoryId Identificador de la instancia de Category
     * @param bicycleId Identificador de la instancia de Bicycle
     * @generated
     */
    @Override
    public void removeBicycle(Long categoryId, Long bicycleId) {
        BicycleEntity entity = bicycleLogic.getBicycle(bicycleId);
        entity.setCategory(null);
    }
}
