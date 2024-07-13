import { useTokenStore } from '@/store/token.js';
import router from '@/router';
import { useCONST } from '@/config/const';

export const request = async (input, init) => {
    const tokenStore = useTokenStore();
    const CONST = useCONST();
    if (tokenStore?.token === '' && !['/auth/login', '/auth/register'].includes(router.currentRoute._value.path)) {
        await router.push('/auth/login');
        throw new Error(CONST.EXCEPTION_TIP);
    }

    init.headers = {
        'Content-Type': 'application/x-www-form-urlencoded',
        Authorization: tokenStore.token
    };
    const response = await fetch(input, init);
    switch (response.status) {
        case 401:
            await router.push('/auth/login');
            throw new Error(CONST.EXCEPTION_TIP);
    }
    return response;
};
