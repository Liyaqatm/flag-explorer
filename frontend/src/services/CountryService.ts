// src/services/CountryService.ts
import type { Country } from '@/types/Country';
import type { CountryDetails } from '@/types/CountryDetails';

export default {
  /**
   * Fetch all countries from the backend API
   */
  async getAllCountries(): Promise<Country[]> {
    try {
      const response = await fetch('/api/countries');
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    } catch (error) {
      console.error('Error fetching countries:', error);
      return [];
    }
  },

  /**
   * Fetch a single country by name from the backend API
   */
  async getCountryByName(name: string): Promise<CountryDetails | null> {
    try {
      const response = await fetch(`/api/countries/${name}`);
      if (!response.ok) {
        if (response.status === 404) {
          return null;
        }
        throw new Error('Network response was not ok');
      }
      return response.json();
    } catch (error) {
      console.error(`Error fetching country ${name}:`, error);
      return null;
    }
  }
};