package com.aegidea.photoshootingmanager.service;

import com.aegidea.photoshootingmanager.entity.Photographer;
import com.aegidea.photoshootingmanager.exception.PhotographerNotFoundException;
import com.aegidea.photoshootingmanager.repository.PhotographerRepository;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tiziano (Titto) Fortin <tiziano@proofy.co>
 */
@Service
public class PhotographerService {
    
    private final static Logger LOG = LoggerFactory.getLogger(PhotographerService.class);
    
    @Autowired
    private PhotographerRepository photographerRepository;
    
    /**
     * Populate a well complete list of Photographers.
     * Just few of them are created statically. Just in order to have an initial dataset.
     * 
     * @see Assumptions
     * @since 1.0
     * @return a list of Photographers
     */
    public List<Photographer> populatePhotographers() {
        LOG.info("Start creating 5 photographers.");
        List<Photographer> photographers = new ArrayList<>();
        for(int i = 1; i<=5; i++) {
            Photographer p = new Photographer();
            p.setName("photographer"+i);
            p.setOrders(new ArrayList<>());
            photographers.add(p);
        }
        return this.photographerRepository.saveAll(photographers);
    }

    /**
     * @see R3
     * @since 1.0
     * @param photographerId
     * @return 
     */
    Photographer findById(String photographerId) {
        LOG.info("Find a phogographer by id with id#{}", photographerId);
        return this.photographerRepository.findById(photographerId).orElseThrow(() -> new PhotographerNotFoundException("not Found"));
    }

}
