import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useTokenStore = defineStore(
    'token',
    () => {
        const token = ref('');

        const setToken = newToken => {
            token.value = newToken;
        };
        const removeToken = () => {
            setToken('');
        };
        return {
            token, setToken, removeToken
        };
    },
    {
        persist: true
    }
);
