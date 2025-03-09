package com.om.flagexplorer.service;

import com.om.flagexplorer.data.CountryEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FlagService {

  private static final Logger log = LogManager.getLogger(FlagService.class);
  private final RestTemplate restTemplate;
  private final String API_BASE_URL = "https://restcountries.com/v3.1";

  @Autowired
  public FlagService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public List<CountryEntity> getAllCountries() {
    log.info("Getting all countries");
    try {
      String url = API_BASE_URL + "/all";
      Object[] countries = restTemplate.getForObject(url, Object[].class);

      if (countries == null || countries.length == 0) {
        log.warn("No countries returned from API");
        return Collections.emptyList();
      }

      log.info("Retrieved {} countries from API", countries.length);
      return convertToCountryEntities(countries);
    } catch (Exception e) {
      log.error("Error fetching countries from API", e);
      return Collections.emptyList();
    }
  }

  @SuppressWarnings("unchecked")
  private List<CountryEntity> convertToCountryEntities(Object[] countriesData) {
    List<CountryEntity> result = new ArrayList<>();

    for (Object countryObj : countriesData) {
      try {
        CountryEntity entity = extractCountryEntity(countryObj);
        if (entity != null) {
          result.add(entity);
        }
      } catch (Exception e) {
        log.warn("Error parsing country data: {}", e.getMessage());
      }
    }

    log.info("Successfully converted {} countries", result.size());
    return result;
  }

  public CountryEntity findCountryByName(String name) {
    log.info("Searching countries with name: {}", name);
    try {
      String url = API_BASE_URL + "/name/" + name;
      Object[] countries = restTemplate.getForObject(url, Object[].class);

      if (countries == null || countries.length == 0) {
        log.warn("No countries found with name: {}", name);
        return null;
      }

      log.info("Found {} countries matching '{}'", countries.length, name);
      return extractCountryEntity(countries[0]);
    } catch (Exception e) {
      log.error("Error searching countries with name: {}", name, e);
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  private CountryEntity extractCountryEntity(Object countryObj) {
    try {
      Map<String, Object> countryMap = (Map<String, Object>) countryObj;
      Map<String, Object> nameMap = (Map<String, Object>) countryMap.get("name");
      Map<String, Object> flagsMap = (Map<String, Object>) countryMap.get("flags");

      if (nameMap == null || flagsMap == null) {
        return null;
      }

      String countryName = nameMap.get("common").toString();
      String flagUrl = flagsMap.get("png").toString();

      // Extract capital (can be an array or missing)
      String capital = "Unknown";
      if (countryMap.containsKey("capital") && countryMap.get("capital") instanceof List) {
        List<String> capitals = (List<String>) countryMap.get("capital");
        if (!capitals.isEmpty()) {
          capital = capitals.get(0);
        }
      }

      // Extract population
      long population = 0;
      if (countryMap.containsKey("population") && countryMap.get("population") instanceof Number) {
        population = ((Number) countryMap.get("population")).longValue();
      }

      // Extract country code
      String countryCode = "";
      if (countryMap.containsKey("cca2")) {
        countryCode = countryMap.get("cca2").toString();
      }

      // Extract flag variants
      Map<String, String> flagVariants = new HashMap<>();
      for (Map.Entry<String, Object> entry : flagsMap.entrySet()) {
        if (entry.getValue() instanceof String) {
          flagVariants.put(entry.getKey(), (String) entry.getValue());
        }
      }

      return CountryEntity.builder()
          .name(countryName)
          .flagUrl(flagUrl)
          .capital(capital)
          .population(population)
          .countryCode(countryCode)
          .flagVariants(flagVariants)
          .build();
    } catch (Exception e) {
      log.warn("Error parsing country details: {}", e.getMessage());
      return null;
    }
  }
}