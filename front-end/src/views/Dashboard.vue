<script setup>
import { onMounted, reactive, ref, watch } from 'vue';
import { ProductService } from '@/service/ProductService';
import { useLayout } from '@/layout/composables/layout';
import { useTokenStore } from '@/store/token';
import { WarshipAPI } from '@/api/WarshipAPI';
import { useUserStore } from '@/store/user';
import { useToastStore } from '@/store/toast';
import router from '@/router';

const userStore = useUserStore();
const { isDarkTheme } = useLayout();
const tokenStore = useTokenStore();
const toastStore = useToastStore();
const warshipCount = ref(null);
const LVCWarshipCount = ref(null);
const warshipBattleRatio = ref(null);
const LVCWarshipBattleRatio = ref(null);
const crewNumber = ref(null);
const LVCCrewNumber = ref(null);
const message = ref(null);
const LVCMessage = ref(null);
const products = ref(null);
const warships = ref(null);
const lineData = reactive({
    labels: ['一月', '二月', '三月', '四月', '五月', '六月', '七月'],
    datasets: [
        {
            label: '星战收益',
            data: [65, 59, 80, 81, 56, 55, 40],
            fill: false,
            backgroundColor: '#2f4860',
            borderColor: '#2f4860',
            tension: 0.4
        },
        {
            label: '星战成本',
            data: [28, 48, 40, 19, 86, 27, 90],
            fill: false,
            backgroundColor: '#00bb7e',
            borderColor: '#00bb7e',
            tension: 0.4
        }
    ]
});
const items = ref([
    { label: 'Add New', icon: 'pi pi-fw pi-plus' },
    { label: 'Remove', icon: 'pi pi-fw pi-minus' }
]);

const lineOptions = ref(null);
const productService = new ProductService();
const warshipAPI = new WarshipAPI();

onMounted(() => {
    productService.getProductsSmall().then((data) => (products.value = data));
    initData();
});

const initData = async () => {
    try {
        await warshipAPI.getWarshipNumber(userStore.username).then(res => warshipCount.value = res.data);
        await warshipAPI.getWarshipBattleRatio(userStore.username).then(res => warshipBattleRatio.value = res.data);
        await warshipAPI.getCrewNumber(userStore.username).then(res => crewNumber.value = res.data);
        await warshipAPI.getMessage(userStore.username).then(res => message.value = res.data);

        await warshipAPI.getLVCWarshipNumber(userStore.username).then(res => LVCWarshipCount.value = res.data);
        await warshipAPI.getLVCWarshipBattleRatio(userStore.username).then(res => LVCWarshipBattleRatio.value = res.data);
        await warshipAPI.getLVCCrewNumber(userStore.username).then(res => LVCCrewNumber.value = res.data);
        await warshipAPI.getLVCMessage(userStore.username).then(res => LVCMessage.value = res.data);

        await warshipAPI.getAllMyWarships(userStore.username).then(res => warships.value = res.data);
    } catch (e) {
        // console.log('[UnloginError2] ' + e);
        toastStore.showInfo(e);
    }

};

const formatCurrency = (value) => {
    return value.toLocaleString('en-US', { style: 'currency', currency: 'USD' });
};
const applyLightTheme = () => {
    lineOptions.value = {
        plugins: {
            legend: {
                labels: {
                    color: '#495057'
                }
            }
        },
        scales: {
            x: {
                ticks: {
                    color: '#495057'
                },
                grid: {
                    color: '#ebedef'
                }
            },
            y: {
                ticks: {
                    color: '#495057'
                },
                grid: {
                    color: '#ebedef'
                }
            }
        }
    };
};

const applyDarkTheme = () => {
    lineOptions.value = {
        plugins: {
            legend: {
                labels: {
                    color: '#ebedef'
                }
            }
        },
        scales: {
            x: {
                ticks: {
                    color: '#ebedef'
                },
                grid: {
                    color: 'rgba(160, 167, 181, .3)'
                }
            },
            y: {
                ticks: {
                    color: '#ebedef'
                },
                grid: {
                    color: 'rgba(160, 167, 181, .3)'
                }
            }
        }
    };
};

watch(
    isDarkTheme,
    (val) => {
        if (val) {
            applyDarkTheme();
        } else {
            applyLightTheme();
        }
    },
    { immediate: true }
);
</script>

<template>
    <div class="grid">
        <div class="col-12 lg:col-6 xl:col-3">
            <div class="card mb-0">
                <div class="flex justify-content-between mb-3">
                    <div>
                        <span class="block text-500 font-medium mb-3">拥有星舰数量</span>
                        <div class="text-900 font-medium text-xl">{{ warshipCount }}</div>
                    </div>
                    <div class="flex align-items-center justify-content-center bg-blue-100 border-round" style="width: 2.5rem; height: 2.5rem">
                        <i class="pi pi-shopping-cart text-blue-500 text-xl"></i>
                    </div>
                </div>
                <span class="text-green-500 font-medium"> +{{ LVCWarshipCount }}</span>
                <span class="text-500"> 自上次访问后</span>
            </div>
        </div>
        <div class="col-12 lg:col-6 xl:col-3">
            <div class="card mb-0">
                <div class="flex justify-content-between mb-3">
                    <div>
                        <span class="block text-500 font-medium mb-3">星舰战损比</span>
                        <div class="text-900 font-medium text-xl">{{ warshipBattleRatio }}</div>
                    </div>
                    <div class="flex align-items-center justify-content-center bg-orange-100 border-round" style="width: 2.5rem; height: 2.5rem">
                        <i class="pi pi-map-marker text-orange-500 text-xl"></i>
                    </div>
                </div>
                <span class="text-green-500 font-medium">+{{ LVCWarshipBattleRatio }} </span>
                <span class="text-500"> 自上次访问后</span>
            </div>
        </div>
        <div class="col-12 lg:col-6 xl:col-3">
            <div class="card mb-0">
                <div class="flex justify-content-between mb-3">
                    <div>
                        <span class="block text-500 font-medium mb-3">供管理船员总数</span>
                        <div class="text-900 font-medium text-xl">{{ crewNumber }}</div>
                    </div>
                    <div class="flex align-items-center justify-content-center bg-cyan-100 border-round" style="width: 2.5rem; height: 2.5rem">
                        <i class="pi pi-inbox text-cyan-500 text-xl"></i>
                    </div>
                </div>
                <span class="text-green-500 font-medium">+{{ LVCCrewNumber }} </span>
                <span class="text-500"> 新注册成员</span>
            </div>
        </div>
        <div class="col-12 lg:col-6 xl:col-3">
            <div class="card mb-0">
                <div class="flex justify-content-between mb-3">
                    <div>
                        <span class="block text-500 font-medium mb-3">消息</span>
                        <div class="text-900 font-medium text-xl">{{ message }} 未读</div>
                    </div>
                    <div class="flex align-items-center justify-content-center bg-purple-100 border-round" style="width: 2.5rem; height: 2.5rem">
                        <i class="pi pi-comment text-purple-500 text-xl"></i>
                    </div>
                </div>
                <span class="text-green-500 font-medium">+{{ LVCMessage }} </span>
                <span class="text-500"> 未读消息</span>
            </div>
        </div>

        <div class="col-12 xl:col-6">
            <div class="card">
                <h5>我的所有战舰详情</h5>
                <DataTable :value="warships" :rows="5" :paginator="true" responsiveLayout="scroll">
                    <Column style="width: 15%">
                        <template #header> 星舰图</template>
                        <template #body="slotProps">
                            <div class="p-button-rounded">
                                <img src="@/assets/images/enterprise.png" :alt="slotProps.data.image" class="w-10rem p-card" />
                            </div>
                        </template>
                    </Column>
                    <Column field="name" header="星舰" :sortable="true" style="width: 35%"></Column>
                    <Column field="price" header="价值" :sortable="true" style="width: 35%">
                        <template #body="slotProps">
                            {{ formatCurrency(slotProps.data.valuation) }}
                        </template>
                    </Column>
                    <Column style="width: 15%">
                        <template #header> 详情</template>
                        <template #body>
                            <Button icon="pi pi-search" type="button" class="p-button-text" @click="router.push('uikit/invalidstate')"></Button>
                        </template>
                    </Column>
                </DataTable>
            </div>
            <div class="card">
                <div class="flex justify-content-between align-items-center mb-5">
                    <h5>中央系统运行情况</h5>
                    <div>
                        <Button icon="pi pi-ellipsis-v" class="p-button-text p-button-plain p-button-rounded" @click="$refs.menu2.toggle($event)"></Button>
                        <Menu ref="menu2" :popup="true" :model="items"></Menu>
                    </div>
                </div>
                <ul class="list-none p-0 m-0">
                    <li class="flex flex-column md:flex-row md:align-items-center md:justify-content-between mb-4">
                        <div>
                            <span class="text-900 font-medium mr-2 mb-1 md:mb-0">曲速引擎利用率</span>
                            <div class="mt-1 text-600">适中以提升星战收益</div>
                        </div>
                        <div class="mt-2 md:mt-0 flex align-items-center">
                            <div class="surface-300 border-round overflow-hidden w-10rem lg:w-6rem" style="height: 8px">
                                <div class="bg-orange-500 h-full" style="width: 50%"></div>
                            </div>
                            <span class="text-orange-500 ml-3 font-medium">%50</span>
                        </div>
                    </li>
                    <li class="flex flex-column md:flex-row md:align-items-center md:justify-content-between mb-4">
                        <div>
                            <span class="text-900 font-medium mr-2 mb-1 md:mb-0">冲量引擎利用率</span>
                            <div class="mt-1 text-600">适中以减少曲速引擎损耗</div>
                        </div>
                        <div class="mt-2 md:mt-0 ml-0 md:ml-8 flex align-items-center">
                            <div class="surface-300 border-round overflow-hidden w-10rem lg:w-6rem" style="height: 8px">
                                <div class="bg-cyan-500 h-full" style="width: 16%"></div>
                            </div>
                            <span class="text-cyan-500 ml-3 font-medium">%16</span>
                        </div>
                    </li>
                    <li class="flex flex-column md:flex-row md:align-items-center md:justify-content-between mb-4">
                        <div>
                            <span class="text-900 font-medium mr-2 mb-1 md:mb-0">燃料存量比</span>
                            <div class="mt-1 text-600">始终保持充足存量</div>
                        </div>
                        <div class="mt-2 md:mt-0 ml-0 md:ml-8 flex align-items-center">
                            <div class="surface-300 border-round overflow-hidden w-10rem lg:w-6rem" style="height: 8px">
                                <div class="bg-pink-500 h-full" style="width: 67%"></div>
                            </div>
                            <span class="text-pink-500 ml-3 font-medium">%67</span>
                        </div>
                    </li>
                    <li class="flex flex-column md:flex-row md:align-items-center md:justify-content-between mb-4">
                        <div>
                            <span class="text-900 font-medium mr-2 mb-1 md:mb-0">冬眠人员比</span>
                            <div class="mt-1 text-600">始终注意所需的最低人数</div>
                        </div>
                        <div class="mt-2 md:mt-0 ml-0 md:ml-8 flex align-items-center">
                            <div class="surface-300 border-round overflow-hidden w-10rem lg:w-6rem" style="height: 8px">
                                <div class="bg-green-500 h-full" style="width: 35%"></div>
                            </div>
                            <span class="text-green-500 ml-3 font-medium">%35</span>
                        </div>
                    </li>
                    <li class="flex flex-column md:flex-row md:align-items-center md:justify-content-between mb-4">
                        <div>
                            <span class="text-900 font-medium mr-2 mb-1 md:mb-0">低级警报占比</span>
                            <div class="mt-1 text-600">衡量系统健康程度</div>
                        </div>
                        <div class="mt-2 md:mt-0 ml-0 md:ml-8 flex align-items-center">
                            <div class="surface-300 border-round overflow-hidden w-10rem lg:w-6rem" style="height: 8px">
                                <div class="bg-purple-500 h-full" style="width: 75%"></div>
                            </div>
                            <span class="text-purple-500 ml-3 font-medium">%75</span>
                        </div>
                    </li>
                    <li class="flex flex-column md:flex-row md:align-items-center md:justify-content-between mb-4">
                        <div>
                            <span class="text-900 font-medium mr-2 mb-1 md:mb-0">中级警报占比</span>
                            <div class="mt-1 text-600">衡量系统健康程度</div>
                        </div>
                        <div class="mt-2 md:mt-0 ml-0 md:ml-8 flex align-items-center">
                            <div class="surface-300 border-round overflow-hidden w-10rem lg:w-6rem" style="height: 8px">
                                <div class="bg-teal-500 h-full" style="width: 40%"></div>
                            </div>
                            <span class="text-teal-500 ml-3 font-medium">%40</span>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
        <div class="col-12 xl:col-6">
            <div class="card">
                <h5>星战收益与星战成本</h5>
                <Chart type="line" :data="lineData" :options="lineOptions" />
            </div>
            <div class="card">
                <div class="flex align-items-center justify-content-between mb-4">
                    <h5>中央管理系统报警</h5>
                    <div>
                        <Button icon="pi pi-ellipsis-v" class="p-button-text p-button-plain p-button-rounded" @click="$refs.menu1.toggle($event)"></Button>
                        <Menu ref="menu1" :popup="true" :model="items"></Menu>
                    </div>
                </div>

                <span class="block text-600 font-medium mb-3">今日</span>
                <ul class="p-0 mx-0 mt-0 mb-4 list-none">
                    <li class="flex align-items-center py-2 border-bottom-1 surface-border">
                        <div class="w-3rem h-3rem flex align-items-center justify-content-center bg-blue-100 border-circle mr-3 flex-shrink-0">
                            <i class="pi pi-dollar text-xl text-blue-500"></i>
                        </div>
                        <span class="text-900 line-height-3">指挥官李值攻击了你的星舰群，导致你损失了 <span class="text-blue-500">7669$</span>
                        </span>
                    </li>
                    <li class="flex align-items-center py-2">
                        <div class="w-3rem h-3rem flex align-items-center justify-content-center bg-orange-100 border-circle mr-3 flex-shrink-0">
                            <i class="pi pi-download text-xl text-orange-500"></i>
                        </div>
                        <span class="text-700 line-height-3">为地球星舰哥伦比亚号 (NX-02) 的曲速引擎装载已完成</span>
                    </li>
                </ul>

                <span class="block text-600 font-medium mb-3">昨日</span>
                <ul class="p-0 m-0 list-none">
                    <li class="flex align-items-center py-2 border-bottom-1 surface-border">
                        <div class="w-3rem h-3rem flex align-items-center justify-content-center bg-blue-100 border-circle mr-3 flex-shrink-0">
                            <i class="pi pi-dollar text-xl text-blue-500"></i>
                        </div>
                        <span class="text-900 line-height-3">因你违反了法案 LK7-33，中央管理员强制冻结了你的银河级星舰，导致你损失了 <span class="text-blue-500">5999999$</span>
                        </span>
                    </li>
                    <li class="flex align-items-center py-2 border-bottom-1 surface-border">
                        <div class="w-3rem h-3rem flex align-items-center justify-content-center bg-pink-100 border-circle mr-3 flex-shrink-0">
                            <i class="pi pi-question text-xl text-pink-500"></i>
                        </div>
                        <span class="text-900 line-height-3">
                            指挥官张自向你发出了星舰调帧申请
                        </span>
                    </li>
                </ul>
            </div>
            <!--            <div-->
            <!--                class="px-4 py-5 shadow-2 flex flex-column md:flex-row md:align-items-center justify-content-between mb-3"-->
            <!--                style="border-radius: 1rem; background: linear-gradient(0deg, rgba(0, 123, 255, 0.5), rgba(0, 123, 255, 0.5)), linear-gradient(92.54deg, #1c80cf 47.88%, #ffffff 100.01%)"-->
            <!--            >-->
            <!--                <div>-->
            <!--                    <div class="text-blue-100 font-medium text-xl mt-2 mb-3">TAKE THE NEXT STEP</div>-->
            <!--                    <div class="text-white font-medium text-5xl">Try PrimeBlocks</div>-->
            <!--                </div>-->
            <!--                <div class="mt-4 mr-auto md:mt-0 md:mr-0">-->
            <!--                    <a href="https://www.primefaces.org/primeblocks-vue" class="p-button font-bold px-5 py-3 p-button-warning p-button-rounded p-button-raised"> Get Started </a>-->
            <!--                </div>-->
            <!--            </div>-->
        </div>
    </div>
</template>
