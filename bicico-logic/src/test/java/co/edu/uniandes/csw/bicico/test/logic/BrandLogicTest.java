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
package co.edu.uniandes.csw.bicico.test.logic;

import co.edu.uniandes.csw.bicico.ejbs.BrandLogic;
import co.edu.uniandes.csw.bicico.api.IBrandLogic;
import co.edu.uniandes.csw.bicico.entities.BrandEntity;
import co.edu.uniandes.csw.bicico.persistence.BrandPersistence;
import co.edu.uniandes.csw.bicico.entities.BicycleEntity;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;



/**
 * @generated
 */
@RunWith(Arquillian.class)
public class BrandLogicTest {

    /**
     * @generated
     */

    /**
     * @generated
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /**
     * @generated
     */
    @Inject
    private IBrandLogic brandLogic;

    /**
     * @generated
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * @generated
     */
    @Inject
    private UserTransaction utx;

    /**
     * @generated
     */
    private List<BrandEntity> data = new ArrayList<BrandEntity>();
    /**
     * @generated
     */
    private List<BicycleEntity> bicycleData = new ArrayList<>();

    /**
     * @generated
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(BrandEntity.class.getPackage())
                .addPackage(BrandLogic.class.getPackage())
                .addPackage(IBrandLogic.class.getPackage())
                .addPackage(BrandPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     *
     * @generated
     */
    @Before
    public void configTest() {
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
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     *
     * @generated
     */
    private void clearData() {
        em.createQuery("delete from BicycleEntity").executeUpdate();
        em.createQuery("delete from BrandEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     *
     * @generated
     */
    private void insertData() {
            for (int i = 0; i < 3; i++) {
                BicycleEntity bicycle = factory.manufacturePojo(BicycleEntity.class);
                em.persist(bicycle);
                bicycleData.add(bicycle);
            }
        for (int i = 0; i < 3; i++) {
            BrandEntity entity = factory.manufacturePojo(BrandEntity.class);

            em.persist(entity);
            data.add(entity);

            if (i == 0) {
                bicycleData.get(i).setBrand(entity);
            }
        }
    }
    /**
     * Prueba para crear un Brand
     *
     * @generated
     */
    @Test
    public void createBrandTest()  {
        BrandEntity newEntity = factory.manufacturePojo(BrandEntity.class);
        BrandEntity result = brandLogic.createBrand(newEntity);
        Assert.assertNotNull(result);
        BrandEntity entity = em.find(BrandEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
    }

    /**
     * Prueba para consultar la lista de Brands
     *
     * @generated
     */
    @Test
    public void getBrandsTest() {
        List<BrandEntity> list = brandLogic.getBrands();
        Assert.assertEquals(data.size(), list.size());
        for (BrandEntity entity : list) {
            boolean found = false;
            for (BrandEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    
    /**
     * Prueba para consultar un Brand
     *
     * @generated
     */
    @Test
    public void getBrandTest() {
        BrandEntity entity = data.get(0);
        BrandEntity resultEntity = brandLogic.getBrand(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getName(), resultEntity.getName());
    }

    /**
     * Prueba para eliminar un Brand
     *
     * @generated
     */
    @Test
    public void deleteBrandTest() {
        BrandEntity entity = data.get(0);
        brandLogic.removeBicycle(entity.getId(), bicycleData.get(0).getId());
        brandLogic.deleteBrand(entity.getId());
        BrandEntity deleted = em.find(BrandEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Brand
     *
     * @generated
     */
    @Test
    public void updateBrandTest() {
        BrandEntity entity = data.get(0);
        BrandEntity pojoEntity = factory.manufacturePojo(BrandEntity.class);

        pojoEntity.setId(entity.getId());

        brandLogic.updateBrand(pojoEntity);

        BrandEntity resp = em.find(BrandEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getName(), resp.getName());
    }

    /**
     * Prueba para obtener una instancia de Bicycle asociada a una instancia Brand
     *
     * @generated
     */
    @Test
    public void getBicycleTest() {
        BrandEntity entity = data.get(0);
        BicycleEntity bicycleEntity = bicycleData.get(0);
        BicycleEntity response = brandLogic.getBicycle(entity.getId(), bicycleEntity.getId());

        Assert.assertEquals(bicycleEntity.getId(), response.getId());
        Assert.assertEquals(bicycleEntity.getName(), response.getName());
    }

    /**
     * Prueba para obtener una colección de instancias de Bicycle asociadas a una instancia Brand
     *
     * @generated
     */
    @Test
    public void listBicycleTest() {
        List<BicycleEntity> list = brandLogic.listBicycle(data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }

    /**
     *Prueba para asociar un Bicycle existente a un Brand
     *
     * @generated
     */
    @Test
    public void addBicycleTest() {
        BrandEntity entity = data.get(0);
        BicycleEntity bicycleEntity = bicycleData.get(1);
        BicycleEntity response = brandLogic.addBicycle(entity.getId(), bicycleEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(bicycleEntity.getId(), response.getId());
    }

    /**
     * Prueba para remplazar las instancias de Bicycle asociadas a una instancia de Brand
     *
     * @generated
     */
    @Test
    public void replaceBicycleTest() {
        BrandEntity entity = data.get(0);
        List<BicycleEntity> list = bicycleData.subList(1, 3);
        brandLogic.replaceBicycle(entity.getId(), list);

        entity = brandLogic.getBrand(entity.getId());
        Assert.assertFalse(entity.getBicycle().contains(bicycleData.get(0)));
        Assert.assertTrue(entity.getBicycle().contains(bicycleData.get(1)));
        Assert.assertTrue(entity.getBicycle().contains(bicycleData.get(2)));
    }

    /**
     * Prueba para desasociar un Bicycle existente de un Brand existente
     *
     * @generated
     */
    @Test
    public void removeBicycleTest() {
        brandLogic.removeBicycle(data.get(0).getId(), bicycleData.get(0).getId());
        BicycleEntity response = brandLogic.getBicycle(data.get(0).getId(), bicycleData.get(0).getId());
        Assert.assertNull(response);
    }
}

