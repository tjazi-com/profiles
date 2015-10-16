package com.tjazi.profiles.service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

/**
 * Created by Krzysztof Wasiak on 10/10/15.
 */

@Controller
public class IndexController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    public String displayStatusPage() {

        log.debug("Requesting status page...");

        return "status";
    }

}
