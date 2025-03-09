package com.om.flagexplorer.controller;

import com.om.flagexplorer.data.CountryEntity;
import com.om.flagexplorer.util.CountryMapper;
import com.om.flagexplorer.service.FlagService;
import com.om.model.Country;
import com.om.model.CountryDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
class CountryControllerIntegrationTest {

  @Mock
  private FlagService flagService;

  @Mock
  private CountryMapper countryMapper;

  @InjectMocks
  private CountryController controller;

  @Test
  void getCountries_shouldReturnCountryList() {
    // Given
    List<CountryEntity> mockEntities = List.of(
        CountryEntity.builder()
            .name("South Africa")
            .flagUrl("https://example.com/southafrica.png")
            .capital("Pretoria")
            .population(60000000)
            .countryCode("ZA")
            .build(),
        CountryEntity.builder()
            .name("Mexico")
            .flagUrl("https://example.com/mexico.png")
            .capital("Mexico City")
            .population(126000000)
            .countryCode("MX")
            .build()
    );

    List<Country> mockDtos = List.of(
        new Country().name("South Africa").flag("https://example.com/southafrica.png"),
        new Country().name("Mexico").flag("https://example.com/mexico.png")
    );

    when(flagService.getAllCountries()).thenReturn(mockEntities);
    when(countryMapper.toCountryDtoList(mockEntities)).thenReturn(mockDtos);

    // When
    ResponseEntity<List<Country>> response = controller.getCountries();

    // Then
    assertEquals(OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(2, response.getBody().size());
    assertEquals("South Africa", response.getBody().get(0).getName());
    assertEquals("Mexico", response.getBody().get(1).getName());
  }

  @Test
  void getCountryByName_whenFound_shouldReturnCountryDetails() {
    // Given
    String countryName = "southafrica";
    CountryEntity mockEntity = CountryEntity.builder()
        .name("South Africa")
        .flagUrl("https://example.com/southafrica.png")
        .capital("Pretoria")
        .population(60000000)
        .countryCode("ZA")
        .flagVariants(Map.of(
            "png", "https://example.com/southafrica.png",
            "svg", "https://example.com/southafrica.svg"
        ))
        .build();

    CountryDetails mockDetails = new CountryDetails()
        .name("South Africa")
        .flag("https://example.com/southafrica.png")
        .capital("Pretoria")
        .population(60000000);

    when(flagService.findCountryByName(countryName)).thenReturn(mockEntity);
    when(countryMapper.toCountryDetailsDto(mockEntity)).thenReturn(mockDetails);

    // When
    ResponseEntity<CountryDetails> response = controller.getCountryByName(countryName);

    // Then
    assertEquals(OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("South Africa", response.getBody().getName());
    assertEquals("Pretoria", response.getBody().getCapital());
    assertEquals(60000000, response.getBody().getPopulation());
  }

  @Test
  void getCountryByName_whenNotFound_shouldReturn404() {
    // Given
    String countryName = "nonexistent";
    when(flagService.findCountryByName(countryName)).thenReturn(null);

    // When
    ResponseEntity<CountryDetails> response = controller.getCountryByName(countryName);

    // Then
    assertEquals(NOT_FOUND, response.getStatusCode());
    assertNull(response.getBody());
  }
}