package com.aegidea.photoshootingmanager.service;

import com.aegidea.photoshootingmanager.entity.Photographer;
import com.aegidea.photoshootingmanager.repository.PhotographerRepository;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Tiziano (Titto) Fortin <tiziano@proofy.co>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PhotographerServiceTest {
    
    @Autowired
    private PhotographerService photographerService;
    
    @Autowired
    private PhotographerRepository photographerRepository;
    
    @Before
    public void setupClass() {
        
    }
    
    @After
    public void tearDown() {
        this.photographerRepository.deleteAll();
    }
    
    @Test
    public void initTest() {
        List<Photographer> results = this.photographerService.populatePhotographers();
        assertEquals(5, results.size());
    }
    
    @Test
    public void initTestDB() {
        this.photographerService.populatePhotographers();
        List<Photographer> results = this.photographerRepository.findAll();
        assertEquals(5, results.size());
    }

}
