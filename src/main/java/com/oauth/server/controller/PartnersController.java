package com.oauth.server.controller;

import com.oauth.server.data.OAuthPartner;
import com.oauth.server.data.SplitCollectionEditor;
import com.oauth.server.repository.OAuthPartnerRepository;
import java.util.Collection;
import java.util.Set;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("partners")
@Log4j2
public class PartnersController {

    @Autowired
    private OAuthPartnerRepository partnerRepository;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Collection.class, new SplitCollectionEditor(Set.class, ","));
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showEditForm(@RequestParam(value = "partnerId", required = false) String partnerId, Model model) {

        OAuthPartner partner;
        if (partnerId != null) {
            partner = partnerRepository.findByPartnerId(partnerId);
        } else {
            partner = new OAuthPartner();
        }

        model.addAttribute("partner", partner);
        return "partnerForm";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editPartner(@ModelAttribute OAuthPartner partner) {
        partnerRepository.saveAndFlush(partner);
        return "redirect:/";
    }

    @RequestMapping(value = "{partner.partnerId}/delete")
    public String deletePartner(@PathVariable("partner.partnerId") String partnerId) {
        partnerRepository.deleteById(partnerId);
        return "redirect:/";
    }
}