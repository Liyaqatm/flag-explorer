package com.om.flagexplorer.util;

import com.om.flagexplorer.data.CountryEntity;
import com.om.model.Country;
import com.om.model.CountryDetails;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CountryMapperTest {

  private final CountryMapper mapper = new CountryMapper();

  @Test
  void toCountryDto_shouldMapCorrectly() {
    // Given
    CountryEntity entity = CountryEntity.builder()
        .name("Test Country")
        .flagUrl("https://example.com/flag.png")
        .capital("Test Capital")
        .population(1000000)
        .countryCode("TC")
        .flagVariants(Map.of("png", "https://example.com/flag.png"))
        .build();

    // When
    Country result = mapper.toCountryDto(entity);

    // Then
    assertNotNull(result);
    assertEquals("Test Country", result.getName());
    assertEquals("https://example.com/flag.png", result.getFlag());
  }

  @Test
  void toCountryDtoList_shouldMapCorrectly() {
    // Given
    CountryEntity entity1 = CountryEntity.builder()
        .name("Country 1")
        .flagUrl("https://example.com/flag1.png")
        .build();

    CountryEntity entity2 = CountryEntity.builder()
        .name("Country 2")
        .flagUrl("https://example.com/flag2.png")
        .build();

    // When
    List<Country> result = mapper.toCountryDtoList(List.of(entity1, entity2));

    // Then
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("Country 1", result.get(0).getName());
    assertEquals("Country 2", result.get(1).getName());
  }

  @Test
  void toCountryDetailsDto_shouldMapCorrectly() {
    // Given
    CountryEntity entity = CountryEntity.builder()
        .name("Test Country")
        .flagUrl("https://example.com/flag.png")
        .capital("Test Capital")
        .population(1000000)
        .countryCode("TC")
        .build();

    // When
    CountryDetails result = mapper.toCountryDetailsDto(entity);

    // Then
    assertNotNull(result);
    assertEquals("Test Country", result.getName());
    assertEquals("https://example.com/flag.png", result.getFlag());
    assertEquals("Test Capital", result.getCapital());
    assertEquals(1000000, result.getPopulation());
  }
}