import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useUserStore = defineStore(
    'user',
    () => {
        const username = ref(null);
        const sex = ref(null);
        const born_time = ref(null);
        const born_country = ref(null);
        const serving_country = ref(null);
        const balance = ref(null);

        return { username, sex, born_time, born_country, serving_country, balance };
    },
    {
        persist: true
    });
