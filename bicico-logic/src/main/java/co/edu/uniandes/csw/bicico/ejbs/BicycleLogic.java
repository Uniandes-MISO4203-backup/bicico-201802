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

import co.edu.uniandes.csw.bicico.api.IBicycleLogic;
import co.edu.uniandes.csw.bicico.entities.BicycleEntity;
import co.edu.uniandes.csw.bicico.persistence.BicyclePersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;



/**
 * @generated
 */
@Stateless
public class BicycleLogic implements IBicycleLogic {

    @Inject private BicyclePersistence persistence;


    /**
     * Obtiene el número de registros de Bicycle.
     *
     * @return Número de registros de Bicycle.
     * @generated
     */
    public int countBicycles() {
        return persistence.count();
    }

    /**
     * Obtiene la lista de los registros de Bicycle.
     *
     * @return Colección de objetos de BicycleEntity.
     * @generated
     */
    @Override
    public List<BicycleEntity> getBicycles() {
        return persistence.findAll();
    }

    /**
     * Obtiene la lista de los registros de Bicycle indicando los datos para la paginación.
     *
     * @param page Número de página.
     * @param maxRecords Número de registros que se mostraran en cada página.
     * @return Colección de objetos de BicycleEntity.
     * @generated
     */
    @Override
    public List<BicycleEntity> getBicycles(Integer page, Integer maxRecords) {
        return persistence.findAll(page, maxRecords);
    }

    /**
     * Obtiene los datos de una instancia de Bicycle a partir de su ID.
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de BicycleEntity con los datos del Bicycle consultado.
     * @generated
     */
    public BicycleEntity getBicycle(Long id) {
        return persistence.find(id);
    }

    /**
     * Se encarga de crear un Bicycle en la base de datos.
     *
     * @param entity Objeto de BicycleEntity con los datos nuevos
     * @return Objeto de BicycleEntity con los datos nuevos y su ID.
     * @generated
     */
    @Override
    public BicycleEntity createBicycle(BicycleEntity entity) {

        persistence.create(entity);
        return entity;
    }

    /**
     * Actualiza la información de una instancia de Bicycle.
     *
     * @param entity Instancia de BicycleEntity con los nuevos datos.
     * @return Instancia de BicycleEntity con los datos actualizados.
     * @generated
     */
    @Override
    public BicycleEntity updateBicycle(BicycleEntity entity) {
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Bicycle de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     * @generated
     */
    @Override
    public void deleteBicycle(Long id) {
        persistence.delete(id);
    }
  
}
