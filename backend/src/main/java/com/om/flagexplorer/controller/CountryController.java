package com.om.flagexplorer.controller;

import com.om.api.CountriesApi;
import com.om.flagexplorer.data.CountryEntity;
import com.om.flagexplorer.util.CountryMapper;
import com.om.flagexplorer.service.FlagService;
import com.om.model.Country;
import com.om.model.CountryDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CountryController implements CountriesApi {

  private final FlagService flagService;
  private final CountryMapper countryMapper;

  @Autowired
  public CountryController(FlagService flagService, CountryMapper countryMapper) {
    this.flagService = flagService;
    this.countryMapper = countryMapper;
  }

  @Override
  public ResponseEntity<List<Country>> getCountries() {
    List<CountryEntity> countryEntities = flagService.getAllCountries();
    List<Country> countryDtos = countryMapper.toCountryDtoList(countryEntities);
    return ResponseEntity.ok(countryDtos);
  }

  @Override
  public ResponseEntity<CountryDetails> getCountryByName(String name) {
    CountryEntity countryEntity = flagService.findCountryByName(name);

    if (countryEntity == null) {
      return ResponseEntity.notFound().build();
    }

    CountryDetails countryDetails = countryMapper.toCountryDetailsDto(countryEntity);
    return ResponseEntity.ok(countryDetails);
  }
}