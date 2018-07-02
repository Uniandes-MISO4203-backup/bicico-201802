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

import co.edu.uniandes.csw.bicico.api.IPhotoAlbumLogic;
import co.edu.uniandes.csw.bicico.entities.PhotoAlbumEntity;
import co.edu.uniandes.csw.bicico.persistence.PhotoAlbumPersistence;
import co.edu.uniandes.csw.bicico.api.IBicycleLogic;
import co.edu.uniandes.csw.bicico.entities.BicycleEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;



/**
 * @generated
 */
@Stateless
public class PhotoAlbumLogic implements IPhotoAlbumLogic {

    @Inject private PhotoAlbumPersistence persistence;

    @Inject
    private IBicycleLogic bicycleLogic;

    /**
     * Obtiene el número de registros de PhotoAlbum.
     *
     * @return Número de registros de PhotoAlbum.
     * @generated
     */
    public int countPhotoAlbums() {
        return persistence.count();
    }

    /**
     * Obtiene la lista de los registros de PhotoAlbum que pertenecen a un Bicycle.
     *
     * @param bicycleid id del Bicycle el cual es padre de los PhotoAlbums.
     * @return Colección de objetos de PhotoAlbumEntity.
     * @generated
     */
    @Override
    public List<PhotoAlbumEntity> getPhotoAlbums(Long bicycleid) {
        BicycleEntity bicycle = bicycleLogic.getBicycle(bicycleid);
        return bicycle.getPhotoAlbum();
        
    }

    /**
     * Obtiene la lista de los registros de PhotoAlbum que pertenecen a un Bicycle indicando los datos para la paginación.
     *
     * @param page Número de página.
     * @param maxRecords Número de registros que se mostraran en cada página.
     * @param bicycleid id del Bicycle el cual es padre de los PhotoAlbums.
     * @return Colección de objetos de PhotoAlbumEntity.
     * @generated
     */
    @Override
    public List<PhotoAlbumEntity> getPhotoAlbums(Integer page, Integer maxRecords, Long bicycleid) {
        return persistence.findAll(page, maxRecords, bicycleid);
    }

    /**
     * Obtiene los datos de una instancia de PhotoAlbum a partir de su ID.
     *
     * @pre La existencia del elemento padre Bicycle se debe garantizar.
     * @param photoAlbumid) Identificador del PhotoAlbum a consultar
     * @return Instancia de PhotoAlbumEntity con los datos del PhotoAlbum consultado.
     * @generated
     */
    @Override
    public PhotoAlbumEntity getPhotoAlbum(Long photoAlbumid) {
        try {
            return persistence.find(photoAlbumid);
        }catch(NoResultException e){
            throw new IllegalArgumentException("El PhotoAlbum no existe");
        }
    }

    /**
     * Se encarga de crear un PhotoAlbum en la base de datos.
     *
     * @param entity Objeto de PhotoAlbumEntity con los datos nuevos
     * @param bicycleid id del Bicycle el cual sera padre del nuevo PhotoAlbum.
     * @return Objeto de PhotoAlbumEntity con los datos nuevos y su ID.
     * @generated
     */
    @Override
    public PhotoAlbumEntity createPhotoAlbum(Long bicycleid, PhotoAlbumEntity entity)  {

        BicycleEntity bicycle = bicycleLogic.getBicycle(bicycleid);
        entity.setBicycle(bicycle);
        entity = persistence.create(entity);
        return entity;
    }

    /**
     * Actualiza la información de una instancia de PhotoAlbum.
     *
     * @param entity Instancia de PhotoAlbumEntity con los nuevos datos.
     * @param bicycleid id del Bicycle el cual sera padre del PhotoAlbum actualizado.
     * @return Instancia de PhotoAlbumEntity con los datos actualizados.
     * @generated
     */
    @Override
    public PhotoAlbumEntity updatePhotoAlbum(Long bicycleid, PhotoAlbumEntity entity) {
        BicycleEntity bicycle = bicycleLogic.getBicycle(bicycleid);
        entity.setBicycle(bicycle);
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de PhotoAlbum de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     * @param bicycleid id del Bicycle el cual es padre del PhotoAlbum.
     * @generated
     */
    @Override
    public void deletePhotoAlbum(Long id) {
        PhotoAlbumEntity old = getPhotoAlbum(id);
        persistence.delete(old.getId());
    }
  
}
