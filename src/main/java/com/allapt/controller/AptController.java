package com.allapt.controller;

import com.allapt.service.AttackService;
import com.allapt.service.CapecService;
import com.allapt.service.NVDService;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * @author 罗叶妮
 * @version 1.0
 * @date 2020/10/14 21:41
 */
@Controller("AptController")
@RequestMapping(value = "/neo4j")
public class AptController {
    @Autowired
    CapecService capecService;
    @Autowired
    AttackService attackService;
    @Autowired
    NVDService nvdService;

    @RequestMapping(value = "/addCapec",method = {RequestMethod.GET}  )
    @ResponseBody
    public String saveCapec() throws IOException, DocumentException {
        capecService.doCapecXml();
          attackService.doAll();
          return "success";

    }
    @RequestMapping(value = "/addNvd",method = {RequestMethod.GET}  )
    @ResponseBody
    public String saveNvd() throws IOException, DocumentException {
        nvdService.doAll();
        return "success";

    }


}