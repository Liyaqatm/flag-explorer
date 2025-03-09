package com.om.flagexplorer.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryEntity {
  private String name;
  private String flagUrl;
  private String countryCode;
  private String capital;
  private long population;
  private Map<String, String> flagVariants;
}