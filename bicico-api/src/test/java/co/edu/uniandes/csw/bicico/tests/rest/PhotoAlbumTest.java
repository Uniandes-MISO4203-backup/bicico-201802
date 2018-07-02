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
package co.edu.uniandes.csw.bicico.tests.rest;

import co.edu.uniandes.csw.auth.api.AuthenticationApi;
import co.edu.uniandes.csw.auth.model.UserDTO;
import co.edu.uniandes.csw.bicico.entities.PhotoAlbumEntity;
import co.edu.uniandes.csw.bicico.entities.BicycleEntity;
import co.edu.uniandes.csw.bicico.dtos.detail.PhotoAlbumDetailDTO;
import co.edu.uniandes.csw.bicico.resources.PhotoAlbumResource;
import co.edu.uniandes.csw.bicico.tests.Utils;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.inject.Inject;
import javax.transaction.UserTransaction;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/*
 * Testing URI: bicycles/{photoAlbumId: \\d+}/photoAlbum/
 */
@RunWith(Arquillian.class)
public class PhotoAlbumTest {

    private WebTarget target;
    private final String apiPath = Utils.apiPath;
    private final String username = Utils.username;
    private final String password = Utils.password;
    PodamFactory factory = new PodamFactoryImpl();

    private final int Ok = Status.OK.getStatusCode();
    private final int Created = Status.CREATED.getStatusCode();
    private final int OkWithoutContent = Status.NO_CONTENT.getStatusCode();

    private final static List<PhotoAlbumEntity> oraculo = new ArrayList<>();
    private  AuthenticationApi auth;

    private final String bicyclePath = "bicycles";
    private final String photoAlbumPath = "photoAlbum";

    BicycleEntity fatherBicycleEntity;

    @ArquillianResource
    private URL deploymentURL;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                // Se agrega las dependencias
                .addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml")
                        .importRuntimeDependencies().resolve()
                        .withTransitivity().asFile())
                // Se agregan los compilados de los paquetes de servicios
                .addPackage(PhotoAlbumResource.class.getPackage())
                .addPackage("co.edu.uniandes.csw.auth.properties")
                // El archivo que contiene la configuracion a la base de datos.
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                // El archivo beans.xml es necesario para injeccion de dependencias.
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"))
                
                // El archivo web.xml es necesario para el despliegue de los servlets
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"));
    }

    private WebTarget createWebTarget() {
        return ClientBuilder.newClient().target(deploymentURL.toString()).path(apiPath);
    }

    @PersistenceContext(unitName = "bicicoPU")
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private void clearData() {
        em.createQuery("delete from PhotoAlbumEntity").executeUpdate();
        em.createQuery("delete from BicycleEntity").executeUpdate();
        oraculo.clear();
    }

   /**
     * Datos iniciales para el correcto funcionamiento de las pruebas.
     *
     * @generated
     */
    public void insertData() {
        fatherBicycleEntity = factory.manufacturePojo(BicycleEntity.class);
        fatherBicycleEntity.setId(1L);
        em.persist(fatherBicycleEntity);

        for (int i = 0; i < 3; i++) {            
            PhotoAlbumEntity photoAlbum = factory.manufacturePojo(PhotoAlbumEntity.class);
            photoAlbum.setId(i + 1L);
            photoAlbum.setBicycle(fatherBicycleEntity);
            em.persist(photoAlbum);
            oraculo.add(photoAlbum);
        }
    }

    /**
     * Configuración inicial de la prueba.
     *
     * @generated
     */
    @Before
    public void setUpTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        target = createWebTarget()
                .path(bicyclePath)
                .path(fatherBicycleEntity.getId().toString())
                .path(photoAlbumPath);
    }

    /**
     * Login para poder consultar los diferentes servicios
     *
     * @param username Nombre de usuario
     * @param password Clave del usuario
     * @return Cookie con información de la sesión del usuario
     * @generated
     */
   public String login() throws IOException, UnirestException, JSONException, InterruptedException, ExecutionException { 
        auth=new AuthenticationApi();
        UserDTO user = new UserDTO();
        user.setUserName(auth.getProp().getProperty("username").trim());
        user.setPassword(auth.getProp().getProperty("password").trim());
        JSONObject json = new JSONObject(auth.authenticationToken(user).getBody()); 
        return (String)json.get("id_token");
    }
   
    public String getUsername() throws IOException, UnirestException, JSONException, InterruptedException, ExecutionException{
     auth=new AuthenticationApi();
    return auth.getProp().getProperty("username").trim();
    }

    /**
     * Prueba para crear un PhotoAlbum
     *
     * @generated
     */
    @Test
    public void createPhotoAlbumTest() throws IOException, UnirestException, JSONException, InterruptedException, ExecutionException {
        PhotoAlbumDetailDTO photoAlbum = factory.manufacturePojo(PhotoAlbumDetailDTO.class);
        String token= login();

        Response response = target
            .request()
             .cookie("username",getUsername())
             .cookie("id_token",token)
            .post(Entity.entity(photoAlbum, MediaType.APPLICATION_JSON));

        PhotoAlbumDetailDTO  photoalbumTest = (PhotoAlbumDetailDTO) response.readEntity(PhotoAlbumDetailDTO.class);

        Assert.assertEquals(Created, response.getStatus());

        Assert.assertEquals(photoAlbum.getName(), photoalbumTest.getName());

        PhotoAlbumEntity entity = em.find(PhotoAlbumEntity.class, photoalbumTest.getId());
        Assert.assertNotNull(entity);
    }

    /**
     * Prueba para consultar un PhotoAlbum
     *
     * @generated
     */
    @Test
    public void getPhotoAlbumByIdTest() throws IOException, UnirestException, JSONException, InterruptedException, ExecutionException {
        String token= login();

        PhotoAlbumDetailDTO photoalbumTest = target
            .path(oraculo.get(0).getId().toString())
            .request()
            .cookie("username",getUsername())
            .cookie("id_token",token)
            .get(PhotoAlbumDetailDTO.class);
        
        Assert.assertEquals(photoalbumTest.getId(), oraculo.get(0).getId());
        Assert.assertEquals(photoalbumTest.getName(), oraculo.get(0).getName());
    }

    /**
     * Prueba para consultar la lista de PhotoAlbums
     *
     * @generated
     */
    @Test
    public void listPhotoAlbumTest() throws IOException, UnirestException, JSONException, InterruptedException, ExecutionException {
         String token= login();

        Response response = target
            .request()
            .cookie("username",getUsername())
            .cookie("id_token",token)
            .get();

        String listPhotoAlbum = response.readEntity(String.class);
        List<PhotoAlbumDetailDTO> listPhotoAlbumTest = new ObjectMapper().readValue(listPhotoAlbum, List.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(oraculo.size(), listPhotoAlbumTest.size());
    }

    /**
     * Prueba para actualizar un PhotoAlbum
     *
     * @generated
     */
    @Test
    public void updatePhotoAlbumTest() throws IOException, UnirestException, JSONException, InterruptedException, ExecutionException {
        String token= login();
        PhotoAlbumDetailDTO photoAlbum = new PhotoAlbumDetailDTO(oraculo.get(0));

        PhotoAlbumDetailDTO photoAlbumChanged = factory.manufacturePojo(PhotoAlbumDetailDTO.class);

        photoAlbum.setName(photoAlbumChanged.getName());

        Response response = target
            .path(photoAlbum.getId().toString())
            .request()
            .cookie("username",getUsername())
            .cookie("id_token",token)
            .put(Entity.entity(photoAlbum, MediaType.APPLICATION_JSON));

        PhotoAlbumDetailDTO photoalbumTest = (PhotoAlbumDetailDTO) response.readEntity(PhotoAlbumDetailDTO.class);

        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(photoAlbum.getName(), photoalbumTest.getName());
    }

    /**
     * Prueba para eliminar un PhotoAlbum
     *
     * @generated
     */
    @Test
    public void deletePhotoAlbumTest() throws IOException, UnirestException, JSONException, InterruptedException, ExecutionException {
        String token= login();
        PhotoAlbumDetailDTO photoAlbum = new PhotoAlbumDetailDTO(oraculo.get(0));
        Response response = target
            .path(photoAlbum.getId().toString())
            .request()
            .cookie("username",getUsername())
            .cookie("id_token",token)
            .delete();

        Assert.assertEquals(OkWithoutContent, response.getStatus());
    }
}
