package com.johnjadd.site;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johnjadd.dev.Dev;
import com.johnjadd.dev.DevService;
import com.johnjadd.util.Until;

@Service
public class SiteService {
	
	@Autowired
	private SiteRepository siteRepository;	
	
	@Autowired
	private DevService devService;
	//@Autowired
	//private IdGenService idGenService;
	
	public List<Site> getAll() {
		List<Site> sites = new ArrayList<>();
		siteRepository.findAll().forEach(sites::add);
		
		for(Site st : sites) {
			st.setDev(null);
		}
		return sites;
	}

	public Site getOne(Long id) {
		Site site = siteRepository.findOne(id);
		/*List<Dev> devsWaittoDelete = new ArrayList<>();
		for(Dev dev : site.getDevs()) {
			if(dev.getParent()!=null) {
				devsWaittoDelete.add(dev);
			}
		}
		for(Dev dev : devsWaittoDelete) {
			site.getDevs().remove(dev);
		}*/
		
		return site;
	}
	
	public void save(Site site) {			
		if(site==null) return;
		
		setSiteInDevs(site);
		if(correctId(site)) {
			siteRepository.save(site);
		}
	}
	
	public void patch(Site site) {
		if(site==null) return;
		
		fillNullProperties(site);

		if(site!=null) {
			siteRepository.save(site);
		}
	}
	
	public boolean correctId(Site site) {
		if(site == null) return false;
		
		/*if(site.getDevs() != null) {
			List<Dev> illegalIdDevs = new ArrayList<>();
			for (Dev dev : site.getDevs()) {
				if(devService.correctId(dev) == false) {
					illegalIdDevs.add(dev);
				}
			}
			site.getDevs().removeAll(illegalIdDevs);
		}*/
		devService.correctId(site.getDev());
		/*need to correct site ID???*/
		return true;
	}
		
	public boolean fillNullProperties(Site site) {
		if(site==null) return false;
		
		Site existed = siteRepository.findOne(site.getId());
		if(existed==null) return false;
		
		/*if(site.getDevs() != null) {			
			List<Dev> notInDBDevs = new ArrayList<>();
			for (Dev dev : site.getDevs()) {
				if(devService.fillNullProperties(dev)==false) {
					notInDBDevs.add(dev);
				}
			}
			site.getDevs().removeAll(notInDBDevs);
		}*/
		
	
		Until.copyNonNullProperties(site, existed);
		Until.copyProperties(existed, site);

		return true;
	}
	
	public void setSiteInDevs(Site site) {
		/*for (Dev dev : site.getDevs()) {
			dev.setSite(site);
			devService.setDevInVars(dev);
		}*/
	}
	
	public boolean isDevBelongs(Site site, Long devId) {
		/*if(site==null) return false;
		
		for(Dev dd: site.getDevs()) {
			if(dd.getId().equals(devId)) {
				return true;
			}
		}*/
		return false;
	}
	
}


