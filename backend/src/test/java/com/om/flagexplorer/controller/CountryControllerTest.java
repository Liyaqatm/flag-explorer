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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryControllerTest {

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
        CountryEntity.builder().name("Country 1").flagUrl("url1").build(),
        CountryEntity.builder().name("Country 2").flagUrl("url2").build()
    );

    List<Country> mockDtos = List.of(
        new Country().name("Country 1").flag("url1"),
        new Country().name("Country 2").flag("url2")
    );

    when(flagService.getAllCountries()).thenReturn(mockEntities);
    when(countryMapper.toCountryDtoList(mockEntities)).thenReturn(mockDtos);

    // When
    ResponseEntity<List<Country>> response = controller.getCountries();

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(2, response.getBody().size());
    assertEquals("Country 1", response.getBody().get(0).getName());
    assertEquals("Country 2", response.getBody().get(1).getName());
  }

  @Test
  void getCountryByName_whenFound_shouldReturnCountryDetails() {
    // Given
    String countryName = "canada";
    CountryEntity mockEntity = CountryEntity.builder()
        .name("Canada")
        .flagUrl("flag-url")
        .capital("Ottawa")
        .population(38000000)
        .build();

    CountryDetails mockDetails = new CountryDetails()
        .name("Canada")
        .flag("flag-url")
        .capital("Ottawa")
        .population(38000000);

    when(flagService.findCountryByName(countryName)).thenReturn(mockEntity);
    when(countryMapper.toCountryDetailsDto(mockEntity)).thenReturn(mockDetails);

    // When
    ResponseEntity<CountryDetails> response = controller.getCountryByName(countryName);

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("Canada", response.getBody().getName());
    assertEquals("Ottawa", response.getBody().getCapital());
  }

  @Test
  void getCountryByName_whenNotFound_shouldReturn404() {
    // Given
    String countryName = "nonexistent";
    when(flagService.findCountryByName(countryName)).thenReturn(null);

    // When
    ResponseEntity<CountryDetails> response = controller.getCountryByName(countryName);

    // Then
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }
}