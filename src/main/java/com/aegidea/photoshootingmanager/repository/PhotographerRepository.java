package com.aegidea.photoshootingmanager.repository;

import com.aegidea.photoshootingmanager.entity.Photographer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Tiziano (Titto) Fortin <tiziano@proofy.co>
 */
public interface PhotographerRepository extends JpaRepository<Photographer, String>{

}
