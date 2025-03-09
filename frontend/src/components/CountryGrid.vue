<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import type { Country } from '@/types/Country';
import CountryService from '@/services/CountryService';

const countries = ref<Country[]>([]);
const loading = ref(true);
const error = ref<string | null>(null);
const router = useRouter();

onMounted(async () => {
  try {
    loading.value = true;
    const fetchedCountries = await CountryService.getAllCountries();
    // Sort countries alphabetically by name
    countries.value = fetchedCountries.sort((a, b) =>
      a.name.localeCompare(b.name)
    );
  } catch (e) {
    error.value = 'Failed to load countries. Please try again later.';
    console.error(e);
  } finally {
    loading.value = false;
  }
});

const viewCountryDetails = (country: Country) => {
  router.push({
    name: 'country-details',
    params: { name: country.name.toLowerCase() }
  });
};
</script>

<template>
  <div class="country-grid-container">
    <h1>Flags Explorer</h1>
     <h3>Liyaqat Mugjenkar - March 2025</h3>

    <div v-if="loading" class="loading-message">
      Loading countries...
    </div>

    <div v-else-if="error" class="error-message">
      {{ error }}
    </div>

    <div v-else class="country-grid">
      <div
        v-for="country in countries"
        :key="country.name"
        class="country-card"
        @click="viewCountryDetails(country)"
      >
        <div class="flag-container">
          <img
            :src="country.flag"
            :alt="`Flag of ${country.name}`"
            class="flag-image"
          />
        </div>
        <div class="country-name">
          {{ country.name }}
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.country-grid-container {
  padding: 20px;
  max-width: 1400px;
  margin: 0 auto;
}

h1 {
  text-align: center;
  margin-bottom: 30px;
  font-size: 2rem;
  color: #2c3e50;
}

h3 {
  text-align: center;
  margin-bottom: 30px;
  font-size: 1rem;
  color: #2c3e50;
}

.country-grid {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 20px;
}

.country-card {
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  overflow: hidden;
  transition: transform 0.2s, box-shadow 0.2s;
  cursor: pointer;
  display: flex;
  flex-direction: column;
}

.country-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
}

.flag-container {
  height: 180px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.flag-image {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
}

.country-name {
  padding: 10px;
  text-align: center;
  font-weight: 500;
  color: #34495e;
  flex-grow: 1;
  display: flex;
  align-items: center;
  justify-content: center;
}

.loading-message,
.error-message {
  text-align: center;
  margin-top: 50px;
  font-size: 18px;
}

.error-message {
  color: #e53935;
}

@media (max-width: 1200px) {
  .country-grid {
    grid-template-columns: repeat(4, 1fr);
  }
}

@media (max-width: 900px) {
  .country-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media (max-width: 600px) {
  .country-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 400px) {
  .country-grid {
    grid-template-columns: 1fr;
  }
}
</style>