<script setup>
import { useLayout } from '@/layout/composables/layout';
import { ref, computed } from 'vue';
import { userLogin } from '@/api/UserAPI.js';
import { getUser } from '@/api/UserAPI.js';
import AppConfig from '@/layout/AppConfig.vue';
import { useRouter } from 'vue-router';
import { useTokenStore } from '@/store/token.js';
import { useToastStore } from '@/store/toast.js';
import { useCONST } from '@/config/const.js';
import { useUserStore } from '@/store/user';
import countries from 'i18n-iso-countries';
import enLocale from 'i18n-iso-countries/langs/en.json';

countries.registerLocale(enLocale);

function getCountryCodeByName(countryName, lang = 'en') {
    const countryCode = countries.getAlpha2Code(countryName, lang);
    return countryCode ? countryCode : 'Unknown';
}

const { layoutConfig } = useLayout();
const userStore = useUserStore();
const username = ref('');
const password = ref('');
const checked = ref(false);
const router = useRouter();
const tokenStore = useTokenStore();
const toastStore = useToastStore();
const CONST = useCONST();

const logoUrl = computed(() => {
    return `/layout/images/${layoutConfig.darkTheme.value ? 'logo-white' : 'logo-dark'}.svg`;
});

const initUserInformation = (username) => {
    getUser(username).then(res => {
        if (res.code == 200) {
            userStore.username = res.data.username;
            userStore.sex = res.data.sex;
            userStore.born_time = res.data.born_time;
            userStore.born_country = { name: res.data.born_country, code: getCountryCodeByName(res.data.born_country) };
            userStore.balance = res.data.balance;
            let serving_country = [];
            if (res.data.serving_country && res.data.serving_country.indexOf(',') != -1) {
                res.data.serving_country.split(',').forEach(e => {
                    serving_country.push({ name: e, code: getCountryCodeByName(e) });
                });
            }
            userStore.serving_country = serving_country;
        }
    });
};

async function login() {
    try {
        if (username.value === '' || password.value === '') throw new Error(CONST.USERNAME_OR_PASSWORD_CANNOT_BE_NULL);
        await userLogin(username.value, password.value).then((res) => {
            if (res.code !== 200) throw new Error(CONST.EXCEPTION_TIP);
            router.push('/');
            initUserInformation(username.value);
            tokenStore.setToken(res.data);
            toastStore.showSuccess(CONST.LOGIN_SUCCESSFULLY);
        });
    } catch (error) {
        toastStore.showInfo(error);
    }
}
</script>

<template>
    <div class="surface-ground flex align-items-center justify-content-center min-h-screen min-w-screen overflow-hidden">
        <div class="flex flex-column align-items-center justify-content-center">
            <img :src="logoUrl" alt="Sakai logo" class="mb-5 w-6rem flex-shrink-0" />
            <div style="border-radius: 56px; padding: 0.3rem; background: linear-gradient(180deg, var(--primary-color) 10%, rgba(33, 150, 243, 0) 30%)">
                <div class="w-full surface-card py-8 px-5 sm:px-8" style="border-radius: 53px">
                    <div class="text-center mb-5">
                        <!-- <img src="/demo/images/login/avatar.png" alt="Image" height="50" class="mb-3" /> -->
                        <div class="text-900 text-3xl font-medium mb-3">欢迎使用战舰管理系统</div>
                        <span class="text-600 font-medium">指挥官，您尚未登录，登录以继续...</span>
                    </div>

                    <div>
                        <label for="email1" class="block text-900 text-xl font-medium mb-2">权限识别ID</label>
                        <InputText id="email1" type="text" placeholder="键入权限ID..." class="w-full md:w-30rem mb-5" style="padding: 1rem" v-model="username" />

                        <label for="password1" class="block text-900 font-medium text-xl mb-2">密码</label>
                        <Password id="password1" v-model="password" placeholder="键入密码..." :toggleMask="true" class="w-full mb-3" inputClass="w-full" :inputStyle="{ padding: '1rem' }"
                                  :feedback="false"></Password>

                        <div class="flex align-items-center justify-content-between mb-5 gap-5">
                            <div class="flex align-items-center">
                                <Checkbox v-model="checked" id="rememberme1" binary class="mr-2"></Checkbox>
                                <label for="rememberme1">保持探针的注入状态</label>
                            </div>
                            <router-link class="font-medium no-underline ml-2 text-right cursor-pointer" style="color: var(--primary-color)" to="/auth/register">尚未拥有探针？</router-link>
                        </div>
                        <Button label="注入战舰管理探针" class="w-full p-3 text-xl" @click="login"></Button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <Toast position="top-center" />
    <AppConfig simple />
</template>

<style scoped>
.pi-eye {
    transform: scale(1.6);
    margin-right: 1rem;
}

.pi-eye-slash {
    transform: scale(1.6);
    margin-right: 1rem;
}
</style>
