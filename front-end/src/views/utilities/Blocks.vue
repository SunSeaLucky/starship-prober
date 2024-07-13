<script setup>
import { ref, onMounted, onBeforeMount } from 'vue';
import { CountryService } from '@/service/CountryService';
import { NodeService } from '@/service/NodeService';
import { useUserStore } from '@/store/user';
import router from '@/router';
import { commitPersonalInformation, getUser } from '@/api/UserAPI';
import { useToastStore } from '@/store/toast';
import countries from 'i18n-iso-countries';
import enLocale from 'i18n-iso-countries/langs/en.json';
countries.registerLocale(enLocale);
function getCountryCodeByName(countryName, lang = 'en') {
    const countryCode = countries.getAlpha2Code(countryName, lang);
    return countryCode ? countryCode : 'Unknown';
}
const toastStore = useToastStore();
const userStore = useUserStore();
const disableEdit = ref(true);


const dropdownValues = ref([
    { name: 'Australia', code: 'AU' },
    { name: 'Brazil', code: 'BR' },
    { name: 'China', code: 'CN' },
    { name: 'Egypt', code: 'EG' },
    { name: 'France', code: 'FR' },
    { name: 'Germany', code: 'DE' },
    { name: 'India', code: 'IN' },
    { name: 'Japan', code: 'JP' },
    { name: 'Spain', code: 'ES' },
    { name: 'United States', code: 'US' },
    { name: 'China', code: 'CN' }
]);

const multiselectValues = ref([
    { name: 'Australia', code: 'AU' },
    { name: 'Brazil', code: 'BR' },
    { name: 'China', code: 'CN' },
    { name: 'Egypt', code: 'EG' },
    { name: 'France', code: 'FR' },
    { name: 'Germany', code: 'DE' },
    { name: 'India', code: 'IN' },
    { name: 'Japan', code: 'JP' },
    { name: 'Spain', code: 'ES' },
    { name: 'United States', code: 'US' }
]);


const selectButtonValue1 = ref({ name: '关闭' });
const selectButtonValues1 = ref([{ name: '关闭' }, { name: '开启' }]);


const initUserInformation = (username) => {
    getUser(username).then(res => {
        if (res.code == 200) {
            userStore.username = res.data.username;
            userStore.sex = res.data.sex;
            userStore.born_time = res.data.born_time;
            userStore.born_country = { name: res.data.born_country, code: getCountryCodeByName(res.data.born_country) };
            userStore.balance = res.data.balance;
            console.log(userStore.balance);
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
onMounted(() => {

});

onBeforeMount(() => initUserInformation(userStore.username));


function changeDisableEdit() {
    disableEdit.value = selectButtonValue1.value.name == '关闭' ? true : false;
}

function quitLogin() {
    router.push('/auth/login');
}

function commitPI() {
    commitPersonalInformation().then(res => {
        try {
            if (res.code == 200) {
                toastStore.showSuccess('个人信息更新成功！');
            } else throw new Error('网络出现问题...');
        } catch (e) {
            toastStore.showError(e);
        }
    });
}
</script>
<template>
    <div class="card p-fluid">
        <h5><strong>指挥官基本信息</strong></h5>
        <div class="grid formgrid">
            <div class="col-12 mb-2 lg:col-4 lg:mb-0">
                <h6 class="p-1">昵称</h6>
                <InputText type="text" :placeholder='userStore.username' :disabled="disableEdit"></InputText>
            </div>
            <div class="col-12 mb-2 lg:col-4 lg:mb-0">
                <h6 class="p-1">性别</h6>
                <InputText type="text" :placeholder="userStore.sex?'男':'女'" :disabled="disableEdit"></InputText>
            </div>
        </div>

        <h6 class="p-1">出生星历</h6>
        <Calendar :showIcon="true" :showButtonBar="true" v-model="userStore.born_time" :disabled="disableEdit"></Calendar>
        <h6 class="p-1">出生国籍</h6>
        <Dropdown v-model="userStore.born_country" :options="dropdownValues" optionLabel="name" placeholder="Select" :disabled="disableEdit" />
        <h6 class="p-1">服役国家</h6>
        <MultiSelect v-model="userStore.serving_country" :options="multiselectValues" optionLabel="name" placeholder="Select Countries" :filter="true" :disabled="disableEdit">
            <template #value="slotProps">
                <div class="inline-flex align-items-center py-1 px-2 bg-primary text-primary border-round mr-2" v-for="option of slotProps.value" :key="option.code">
                    <span :class="'mr-2 flag flag-' + option.code.toLowerCase()" style="width: 18px; height: 12px" />
                    <div>{{ option.name }}</div>
                </div>
                <template v-if="!slotProps.value || slotProps.value.length === 0">
                    <div class="p-1">Select Countries</div>
                </template>
            </template>
            <template #option="slotProps">
                <div class="flex align-items-center">
                    <span :class="'mr-2 flag flag-' + slotProps.option.code.toLowerCase()" style="width: 18px; height: 12px" />
                    <div>{{ slotProps.option.name }}</div>
                </div>
            </template>
        </MultiSelect>

        <h6 class="p-1">启用编辑</h6>
        <SelectButton v-model="selectButtonValue1" :options="selectButtonValues1" optionLabel="name" @change="changeDisableEdit()" />
        <h6 class="mt-5"><strong>当前余额：{{ userStore.balance }} $</strong></h6>
        <div class="flex justify-content-between align-items-center max-w-30rem mx-auto mt-8">
            <Button label="提交修改" class="mt-8 max-w-10rem" @click="commitPI" />
            <Button label="退出登录" class="mt-8 max-w-10rem" severity="danger" @click="quitLogin" />
        </div>

    </div>
</template>
