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
package co.edu.uniandes.csw.bicico.resources;

import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import co.edu.uniandes.csw.bicico.api.ICategoryLogic;
import co.edu.uniandes.csw.bicico.dtos.detail.BicycleDetailDTO;
import co.edu.uniandes.csw.bicico.entities.BicycleEntity;
import java.util.ArrayList;
/**
 * URI: categorys/{categorysId: \\d+}/bicycle
 * @generated
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoryBicycleResource {

    @Inject private ICategoryLogic categoryLogic;
    @Context private HttpServletResponse response;

    /**
     * Convierte una lista de BicycleEntity a una lista de BicycleDetailDTO.
     *
     * @param entityList Lista de BicycleEntity a convertir.
     * @return Lista de BicycleDetailDTO convertida.
     * @generated
     */
    private List<BicycleDetailDTO> bicycleListEntity2DTO(List<BicycleEntity> entityList){
        List<BicycleDetailDTO> list = new ArrayList<>();
        for (BicycleEntity entity : entityList) {
            list.add(new BicycleDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de BicycleDetailDTO a una lista de BicycleEntity.
     *
     * @param dtos Lista de BicycleDetailDTO a convertir.
     * @return Lista de BicycleEntity convertida.
     * @generated
     */
    private List<BicycleEntity> bicycleListDTO2Entity(List<BicycleDetailDTO> dtos){
        List<BicycleEntity> list = new ArrayList<>();
        for (BicycleDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }

    /**
     * Obtiene una colección de instancias de BicycleDetailDTO asociadas a una
     * instancia de Category
     *
     * @param categorysId Identificador de la instancia de Category
     * @return Colección de instancias de BicycleDetailDTO asociadas a la instancia de Category
     * @generated
     */
    @GET
    public List<BicycleDetailDTO> listBicycle(@PathParam("categorysId") Long categorysId) {
        return bicycleListEntity2DTO(categoryLogic.listBicycle(categorysId));
    }

    /**
     * Obtiene una instancia de Bicycle asociada a una instancia de Category
     *
     * @param categorysId Identificador de la instancia de Category
     * @param bicycleId Identificador de la instancia de Bicycle
     * @generated
     */
    @GET
    @Path("{bicycleId: \\d+}")
    public BicycleDetailDTO getBicycle(@PathParam("categorysId") Long categorysId, @PathParam("bicycleId") Long bicycleId) {
        return new BicycleDetailDTO(categoryLogic.getBicycle(categorysId, bicycleId));
    }

    /**
     * Asocia un Bicycle existente a un Category
     *
     * @param categorysId Identificador de la instancia de Category
     * @param bicycleId Identificador de la instancia de Bicycle
     * @return Instancia de BicycleDetailDTO que fue asociada a Category
     * @generated
     */
    @POST
    @Path("{bicycleId: \\d+}")
    public BicycleDetailDTO addBicycle(@PathParam("categorysId") Long categorysId, @PathParam("bicycleId") Long bicycleId) {
        return new BicycleDetailDTO(categoryLogic.addBicycle(categorysId, bicycleId));
    }

    /**
     * Remplaza las instancias de Bicycle asociadas a una instancia de Category
     *
     * @param categorysId Identificador de la instancia de Category
     * @param bicycles Colección de instancias de BicycleDTO a asociar a instancia de Category
     * @return Nueva colección de BicycleDTO asociada a la instancia de Category
     * @generated
     */
    @PUT
    public List<BicycleDetailDTO> replaceBicycle(@PathParam("categorysId") Long categorysId, List<BicycleDetailDTO> bicycles) {
        return bicycleListEntity2DTO(categoryLogic.replaceBicycle(categorysId, bicycleListDTO2Entity(bicycles)));
    }

    /**
     * Desasocia un Bicycle existente de un Category existente
     *
     * @param categorysId Identificador de la instancia de Category
     * @param bicycleId Identificador de la instancia de Bicycle
     * @generated
     */
    @DELETE
    @Path("{bicycleId: \\d+}")
    public void removeBicycle(@PathParam("categorysId") Long categorysId, @PathParam("bicycleId") Long bicycleId) {
        categoryLogic.removeBicycle(categorysId, bicycleId);
    }

}
