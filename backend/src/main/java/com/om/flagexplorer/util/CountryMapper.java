package com.om.flagexplorer.util;

import com.om.flagexplorer.data.CountryEntity;
import com.om.model.Country;
import com.om.model.CountryDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CountryMapper {

  public Country toCountryDto(CountryEntity entity) {
    return new Country()
        .name(entity.getName())
        .flag(entity.getFlagUrl());
  }

  public List<Country> toCountryDtoList(List<CountryEntity> entities) {
    return entities.stream()
        .map(this::toCountryDto)
        .collect(Collectors.toList());
  }

  public CountryDetails toCountryDetailsDto(CountryEntity entity) {
    return new CountryDetails()
        .name(entity.getName())
        .flag(entity.getFlagUrl())
        .capital(entity.getCapital())
        .population((int) entity.getPopulation());
  }
}