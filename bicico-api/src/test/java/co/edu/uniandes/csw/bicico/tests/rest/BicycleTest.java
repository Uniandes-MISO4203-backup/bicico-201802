/*
The MIT License (MIT)

Copyright (c) 2015 Los Andes University

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

`The above copyright notice and this permission notice shall be included in all
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
import co.edu.uniandes.csw.bicico.entities.BicycleEntity;
import co.edu.uniandes.csw.bicico.dtos.detail.BicycleDetailDTO;
import co.edu.uniandes.csw.bicico.resources.BicycleResource;
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
 * Testing URI: bicycles/
 */
@RunWith(Arquillian.class)
public class BicycleTest {

    private WebTarget target;
    private final String apiPath = Utils.apiPath;
    private final String username = Utils.username;
    private final String password = Utils.password;
    PodamFactory factory = new PodamFactoryImpl();

    private final int Ok = Status.OK.getStatusCode();
    private final int Created = Status.CREATED.getStatusCode();
    private final int OkWithoutContent = Status.NO_CONTENT.getStatusCode();

    private final static List<BicycleEntity> oraculo = new ArrayList<>();
    private  AuthenticationApi auth;

    private final String bicyclePath = "bicycles";


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
                .addPackage(BicycleResource.class.getPackage())
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
        em.createQuery("delete from BicycleEntity").executeUpdate();
        oraculo.clear();
    }

   /**
     * Datos iniciales para el correcto funcionamiento de las pruebas.
     *
     * @generated
     */
    public void insertData() {
        for (int i = 0; i < 3; i++) {            
            BicycleEntity bicycle = factory.manufacturePojo(BicycleEntity.class);
            bicycle.setId(i + 1L);
            em.persist(bicycle);
            oraculo.add(bicycle);
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
                .path(bicyclePath);
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
     * Prueba para crear un Bicycle
     *
     * @generated
     */
    @Test
    public void createBicycleTest() throws IOException, UnirestException, JSONException, InterruptedException, ExecutionException {
        BicycleDetailDTO bicycle = factory.manufacturePojo(BicycleDetailDTO.class);
        String token= login();

        Response response = target
            .request()
             .cookie("username",getUsername())
             .cookie("id_token",token)
            .post(Entity.entity(bicycle, MediaType.APPLICATION_JSON));

        BicycleDetailDTO  bicycleTest = (BicycleDetailDTO) response.readEntity(BicycleDetailDTO.class);

        Assert.assertEquals(Created, response.getStatus());

        Assert.assertEquals(bicycle.getName(), bicycleTest.getName());

        BicycleEntity entity = em.find(BicycleEntity.class, bicycleTest.getId());
        Assert.assertNotNull(entity);
    }

    /**
     * Prueba para consultar un Bicycle
     *
     * @generated
     */
    @Test
    public void getBicycleByIdTest() throws IOException, UnirestException, JSONException, InterruptedException, ExecutionException {
        String token= login();

        BicycleDetailDTO bicycleTest = target
            .path(oraculo.get(0).getId().toString())
            .request()
            .cookie("username",getUsername())
            .cookie("id_token",token)
            .get(BicycleDetailDTO.class);
        
        Assert.assertEquals(bicycleTest.getId(), oraculo.get(0).getId());
        Assert.assertEquals(bicycleTest.getName(), oraculo.get(0).getName());
    }

    /**
     * Prueba para consultar la lista de Bicycles
     *
     * @generated
     */
    @Test
    public void listBicycleTest() throws IOException, UnirestException, JSONException, InterruptedException, ExecutionException {
         String token= login();

        Response response = target
            .request()
            .cookie("username",getUsername())
            .cookie("id_token",token)
            .get();

        String listBicycle = response.readEntity(String.class);
        List<BicycleDetailDTO> listBicycleTest = new ObjectMapper().readValue(listBicycle, List.class);
        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(oraculo.size(), listBicycleTest.size());
    }

    /**
     * Prueba para actualizar un Bicycle
     *
     * @generated
     */
    @Test
    public void updateBicycleTest() throws IOException, UnirestException, JSONException, InterruptedException, ExecutionException {
        String token= login();
        BicycleDetailDTO bicycle = new BicycleDetailDTO(oraculo.get(0));

        BicycleDetailDTO bicycleChanged = factory.manufacturePojo(BicycleDetailDTO.class);

        bicycle.setName(bicycleChanged.getName());

        Response response = target
            .path(bicycle.getId().toString())
            .request()
            .cookie("username",getUsername())
            .cookie("id_token",token)
            .put(Entity.entity(bicycle, MediaType.APPLICATION_JSON));

        BicycleDetailDTO bicycleTest = (BicycleDetailDTO) response.readEntity(BicycleDetailDTO.class);

        Assert.assertEquals(Ok, response.getStatus());
        Assert.assertEquals(bicycle.getName(), bicycleTest.getName());
    }

    /**
     * Prueba para eliminar un Bicycle
     *
     * @generated
     */
    @Test
    public void deleteBicycleTest() throws IOException, UnirestException, JSONException, InterruptedException, ExecutionException {
        String token= login();
        BicycleDetailDTO bicycle = new BicycleDetailDTO(oraculo.get(0));
        Response response = target
            .path(bicycle.getId().toString())
            .request()
            .cookie("username",getUsername())
            .cookie("id_token",token)
            .delete();

        Assert.assertEquals(OkWithoutContent, response.getStatus());
    }
}
