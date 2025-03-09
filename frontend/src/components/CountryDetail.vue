<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import type { CountryDetails } from '@/types/CountryDetails';
import CountryService from '@/services/CountryService';

const route = useRoute();
const router = useRouter();
const country = ref<CountryDetails | null>(null);
const loading = ref(true);
const error = ref<string | null>(null);

onMounted(async () => {
  const countryName = route.params.name as string;
  try {
    loading.value = true;
    country.value = await CountryService.getCountryByName(countryName);
    if (!country.value) {
      error.value = 'Country not found';
    }
  } catch (e) {
    error.value = 'Failed to load country details. Please try again later.';
    console.error(e);
  } finally {
    loading.value = false;
  }
});

const goBack = () => {
  router.back();
};
</script>

<template>
  <div class="country-detail-wrapper">
    <div class="country-detail-container">
      <div class="header">
        <button @click="goBack" class="back-button">
          &larr; Back
        </button>
      </div>

      <div v-if="loading" class="loading">
        Loading country details...
      </div>

      <div v-else-if="error" class="error">
        {{ error }}
      </div>

      <div v-else-if="country" class="country-detail">
        <div class="flag-section">
          <img
            :src="country.flag"
            :alt="`Flag of ${country.name}`"
            class="detail-flag"
          />
        </div>

        <div class="info-section">
          <h1 class="country-name">{{ country.name }}</h1>

          <div class="details">
            <div class="detail-grid">
              <div class="detail-label">Capital</div>
              <div class="detail-value">{{ country.capital || 'N/A' }}</div>

              <div class="detail-label">Population</div>
              <div class="detail-value">
                {{ country.population.toLocaleString() }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.country-detail-wrapper {
  background-color: #f4f6f7;
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  padding: 20px 0;
}

.country-detail-container {
  background-color: white;
  border-radius: 12px;
  box-shadow: 0 10px 25px rgba(0,0,0,0.1);
  width: 100%;
  max-width: 900px;
  padding: 30px;
}

.header {
  margin-bottom: 30px;
}

.back-button {
  background: none;
  border: none;
  color: #2c3e50;
  font-size: 16px;
  cursor: pointer;
  padding: 8px 12px;
  border-radius: 6px;
  transition: background-color 0.3s ease;
}

.back-button:hover {
  background-color: #f0f0f0;
}

.country-detail {
  display: flex;
  gap: 40px;
  align-items: flex-start;
}

.flag-section {
  flex: 0 0 40%;
}

.detail-flag {
  width: 100%;
  border-radius: 8px;
  box-shadow: 0 6px 15px rgba(0,0,0,0.1);
}

.info-section {
  flex: 1;
}

.country-name {
  margin-top: 0;
  margin-bottom: 25px;
  color: #2c3e50;
  font-size: 2.2rem;
  font-weight: 700;
  border-bottom: 2px solid #e0e0e0;
  padding-bottom: 15px;
}

.details {
  background-color: #f9f9f9;
  border-radius: 8px;
  padding: 20px;
}

.detail-grid {
  display: grid;
  grid-template-columns: auto 1fr;
  gap: 15px;
}

.detail-label {
  font-weight: 600;
  color: #34495e;
  text-align: right;
  padding-right: 15px;
}

.detail-value {
  color: #2c3e50;
}

.loading, .error {
  text-align: center;
  margin-top: 50px;
  font-size: 18px;
}

.loading {
  color: #2980b9;
}

.error {
  color: #c0392b;
}

@media (max-width: 768px) {
  .country-detail {
    flex-direction: column;
    gap: 20px;
  }

  .detail-grid {
    grid-template-columns: 1fr;
    text-align: center;
  }

  .detail-label {
    text-align: center;
    padding-right: 0;
  }
}
</style>