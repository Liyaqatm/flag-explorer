package com.om.flagexplorer.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flag {
  private String name;
  private String country;
  private String image;
  private Map<String, String> flagImages; // For different formats (PNG, SVG)
  private String code; // Country code
}