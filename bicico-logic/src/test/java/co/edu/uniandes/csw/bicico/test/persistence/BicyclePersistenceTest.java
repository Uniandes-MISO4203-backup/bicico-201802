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
package co.edu.uniandes.csw.bicico.test.persistence;
import co.edu.uniandes.csw.bicico.entities.BicycleEntity;
import co.edu.uniandes.csw.bicico.entities.PhotoAlbumEntity;
import co.edu.uniandes.csw.bicico.persistence.BicyclePersistence;
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
public class BicyclePersistenceTest {

    /**
     * @generated
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(BicycleEntity.class.getPackage())
                .addPackage(BicyclePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * @generated
     */

    /**
     * @generated
     */
    @Inject
    private BicyclePersistence bicyclePersistence;

    /**
     * @generated
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * @generated
     */
    @Inject
    UserTransaction utx;

    /**
     * Configuración inicial de la prueba.
     *
     * @generated
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
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
        em.createQuery("delete from PhotoAlbumEntity").executeUpdate();
        em.createQuery("delete from BicycleEntity").executeUpdate();
    }

    /**
     * @generated
     */
    private List<BicycleEntity> data = new ArrayList<BicycleEntity>();

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     *
     * @generated
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            BicycleEntity entity = factory.manufacturePojo(BicycleEntity.class);
            
            em.persist(entity);
            data.add(entity);
        }
    }
    /**
     * Prueba para crear un Bicycle.
     *
     * @generated
     */
    @Test
    public void createBicycleTest() {
        PodamFactory factory = new PodamFactoryImpl();
        BicycleEntity newEntity = factory.manufacturePojo(BicycleEntity.class);
        BicycleEntity result = bicyclePersistence.create(newEntity);

        Assert.assertNotNull(result);

        BicycleEntity entity = em.find(BicycleEntity.class, result.getId());

        Assert.assertEquals(newEntity.getName(), entity.getName());
    }

    /**
     * Prueba para consultar la lista de Bicycles.
     *
     * @generated
     */
    @Test
    public void getBicyclesTest() {
        List<BicycleEntity> list = bicyclePersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (BicycleEntity ent : list) {
            boolean found = false;
            for (BicycleEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Bicycle.
     *
     * @generated
     */
    @Test
    public void getBicycleTest() {
        BicycleEntity entity = data.get(0);
        BicycleEntity newEntity = bicyclePersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getName(), newEntity.getName());
    }

    /**
     * Prueba para eliminar un Bicycle.
     *
     * @generated
     */
    @Test
    public void deleteBicycleTest() {
        BicycleEntity entity = data.get(0);
        bicyclePersistence.delete(entity.getId());
        BicycleEntity deleted = em.find(BicycleEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Bicycle.
     *
     * @generated
     */
    @Test
    public void updateBicycleTest() {
        BicycleEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        BicycleEntity newEntity = factory.manufacturePojo(BicycleEntity.class);

        newEntity.setId(entity.getId());

        bicyclePersistence.update(newEntity);

        BicycleEntity resp = em.find(BicycleEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getName(), resp.getName());
    }
}
