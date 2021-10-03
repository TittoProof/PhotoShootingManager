package com.aegidea.photoshootingmanager.controller;

import com.aegidea.photoshootingmanager.dto.PhotographerDTO;
import com.aegidea.photoshootingmanager.entity.Photographer;
import com.aegidea.photoshootingmanager.service.PhotographerService;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Tiziano (Titto) Fortin <tiziano@proofy.co>
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "Admin operations")
public class OperatorController {
    
    private final static Logger LOG = LoggerFactory.getLogger(OperatorController.class);
    
    @Autowired
    private PhotographerService photographerService;
    
    @Autowired
    private ModelMapper modelMapper;   
    
    @Timed
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/init", method = RequestMethod.GET)
    @ApiOperation(value = "Init database with some photographers")
        @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid request or constraints not respected"),
        @ApiResponse(code = 201, message = "created")
    })
    public List<PhotographerDTO> populatePhotographers() {
        LOG.info("Init the photographer DB...");
        List<Photographer> results = this.photographerService.populatePhotographers();
        LOG.info("...Init finished with {} photographers", results.size());
        return results.stream().map(ph -> modelMapper.map(ph, PhotographerDTO.class)).collect(Collectors.toList());
    }

}
