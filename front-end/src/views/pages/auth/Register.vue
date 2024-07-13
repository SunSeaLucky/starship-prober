<script setup>
import { useToastStore } from '@/store/toast.js';
import { useLayout } from '@/layout/composables/layout';
import { ref, computed } from 'vue';
import { userRegister } from '@/api/UserAPI.js';
import AppConfig from '@/layout/AppConfig.vue';
import { useCONST } from '@/config/const.js';

const { layoutConfig } = useLayout();
const username = ref('');
const password = ref('');
const toastStore = useToastStore();
const CONST = useCONST();

const logoUrl = computed(() => {
    return `/layout/images/${layoutConfig.darkTheme.value ? 'logo-white' : 'logo-dark'}.svg`;
});

const register = async () => {
    try {
        let res = await userRegister(username.value, password.value);
        if (res.code !== 200) throw new Error(CONST.EXCEPTION_TIP);
        toastStore.showSuccess(CONST.REGISTER_SUCCESSFULLY);
    } catch (error) {
        toastStore.showInfo(error);
    }
};
</script>

<template>
    <div class="surface-ground flex align-items-center justify-content-center min-h-screen min-w-screen overflow-hidden">
        <div class="flex flex-column align-items-center justify-content-center">
            <img :src="logoUrl" alt="Sakai logo" class="mb-5 w-6rem flex-shrink-0" />
            <div style="border-radius: 56px; padding: 0.3rem; background: linear-gradient(180deg, var(--primary-color) 10%, rgba(33, 150, 243, 0) 30%)">
                <div class="w-full surface-card py-8 px-5 sm:px-8" style="border-radius: 53px">
                    <div class="text-center mb-5">
                        <div class="text-900 text-3xl font-medium mb-3">探针生成器</div>
                        <span class="text-600 font-medium">生成探针以继续...</span>
                    </div>

                    <div>
                        <label for="email1" class="block text-900 text-xl font-medium mb-2">权限识别ID</label>
                        <InputText id="email1" type="text" placeholder="键入权限ID..." class="w-full md:w-30rem mb-5" style="padding: 1rem" v-model="username" />

                        <label for="password1" class="block text-900 font-medium text-xl mb-2">密码</label>
                        <Password id="password1" v-model="password" placeholder="键入密码..." :toggleMask="true" class="w-full mb-3" inputClass="w-full" :inputStyle="{ padding: '1rem' }"></Password>
                        <div class="flex align-items-center justify-content-between mb-5 gap-5">
                            <router-link class="font-medium no-underline ml-2 text-right cursor-pointer" style="color: var(--primary-color)" to="/auth/login">返回注入器</router-link>
                        </div>
                        <Button label="生成战舰管理探针" class="w-full p-3 text-xl" @click="register"></Button>
                    </div>
                </div>
            </div>
        </div>
    </div>
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
