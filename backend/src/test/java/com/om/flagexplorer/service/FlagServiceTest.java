package com.om.flagexplorer.service;

import com.om.flagexplorer.data.CountryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlagServiceTest {

  @Mock
  private RestTemplate restTemplate;

  @InjectMocks
  private FlagService flagService;

  private Object[] mockCountriesData;

  @BeforeEach
  void setUp() {
    // Create mock response from the REST Countries API
    Map<String, Object> countryData = new HashMap<>();

    // Name data
    Map<String, Object> nameData = new HashMap<>();
    nameData.put("common", "Test Country");
    countryData.put("name", nameData);

    // Flag data
    Map<String, Object> flagData = new HashMap<>();
    flagData.put("png", "https://example.com/flag.png");
    flagData.put("svg", "https://example.com/flag.svg");
    countryData.put("flags", flagData);

    // Capital data (as a list)
    countryData.put("capital", List.of("Test Capital"));

    // Population data
    countryData.put("population", 1000000);

    // Country code
    countryData.put("cca2", "TC");

    mockCountriesData = new Object[]{countryData};
  }

  @Test
  void getAllCountries_shouldReturnCountryList() {
    // Given
    when(restTemplate.getForObject(anyString(), eq(Object[].class)))
        .thenReturn(mockCountriesData);

    // When
    List<CountryEntity> result = flagService.getAllCountries();

    // Then
    assertNotNull(result);
    assertEquals(1, result.size());

    CountryEntity country = result.get(0);
    assertEquals("Test Country", country.getName());
    assertEquals("https://example.com/flag.png", country.getFlagUrl());
    assertEquals("Test Capital", country.getCapital());
    assertEquals(1000000, country.getPopulation());
    assertEquals("TC", country.getCountryCode());

    // Check flag variants
    Map<String, String> expectedVariants = Map.of(
        "png", "https://example.com/flag.png",
        "svg", "https://example.com/flag.svg"
    );
    assertEquals(expectedVariants, country.getFlagVariants());
  }

  @Test
  void getAllCountries_whenApiReturnsEmpty_shouldReturnEmptyList() {
    // Given
    when(restTemplate.getForObject(anyString(), eq(Object[].class)))
        .thenReturn(new Object[0]);

    // When
    List<CountryEntity> result = flagService.getAllCountries();

    // Then
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  void getAllCountries_whenApiThrowsException_shouldReturnEmptyList() {
    // Given
    when(restTemplate.getForObject(anyString(), eq(Object[].class)))
        .thenThrow(new RuntimeException("API error"));

    // When
    List<CountryEntity> result = flagService.getAllCountries();

    // Then
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  void findCountryByName_shouldReturnCountry() {
    // Given
    when(restTemplate.getForObject(anyString(), eq(Object[].class)))
        .thenReturn(mockCountriesData);

    // When
    CountryEntity result = flagService.findCountryByName("test");

    // Then
    assertNotNull(result);
    assertEquals("Test Country", result.getName());
    assertEquals("https://example.com/flag.png", result.getFlagUrl());
  }

  @Test
  void findCountryByName_whenNotFound_shouldReturnNull() {
    // Given
    when(restTemplate.getForObject(anyString(), eq(Object[].class)))
        .thenReturn(new Object[0]);

    // When
    CountryEntity result = flagService.findCountryByName("nonexistent");

    // Then
    assertNull(result);
  }
}