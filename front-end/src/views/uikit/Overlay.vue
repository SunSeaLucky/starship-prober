<script setup>
import { FilterMatchMode } from 'primevue/api';
import { ref, onMounted, onBeforeMount } from 'vue';
import { ProductService } from '@/service/ProductService';
import { useToast } from 'primevue/usetoast';
import { WarshipAPI } from '@/api/WarshipAPI';
import countries from 'i18n-iso-countries';
import enLocale from 'i18n-iso-countries/langs/en.json';
import { useUserStore } from '@/store/user';
import { useToastStore } from '@/store/toast';
import { getUser } from '@/api/UserAPI';

const toast = useToast();

const toastStore = useToastStore();
const userStore = useUserStore();
const warshipAPI = new WarshipAPI();
const warships = ref(null);
const warship = ref({});
const products = ref(null);
const productDialog = ref(false);
const deleteProductDialog = ref(false);
const deleteProductsDialog = ref(false);
const updateAgentDialog = ref(false);
const product = ref({});
const selectedProducts = ref(null);
const dt = ref(null);
const filters = ref({});
const submitted = ref(false);
const statuses = ref([
    { label: 'INSTOCK', value: 'instock' },
    { label: 'LOWSTOCK', value: 'lowstock' },
    { label: 'OUTOFSTOCK', value: 'outofstock' }
]);

const productService = new ProductService();

const getBadgeSeverity = (inventoryStatus) => {
    switch (inventoryStatus) {
        case 5:
            return 'success';
        case 4:
            return 'warning';
        case 3:
            return 'danger';
        default:
            return 'info';
    }
};

const getSeverity = (status) => {
    switch (status) {
        case   1:
            return 'danger';

        case 2:
            return 'success';

        case 3:
            return 'info';

        case 4:
            return 'warning';

        case 5:
            return null;
    }
};
const getStatus = (status) => {
    switch (status) {
        case 0:
            return '正在维修';
        case   1:
            return '即将报废';

        case 2:
            return '状况极佳';

        case 3:
            return '舰体尚佳';

        case 4:
            return '出现损伤';

        case 5:
            return '强制冻结';
    }
};

onBeforeMount(() => {
    initFilters();
});
onMounted(() => {
    console.log(userStore.balance);
    productService.getProducts().then((data) => (products.value = data));
    refreshAllMyShips();

});
const formatCurrency = (value) => {
    return value.toLocaleString('en-US', { style: 'currency', currency: 'USD' });
};

const openNew = () => {
    product.value = {};
    submitted.value = false;
    productDialog.value = true;
};

const hideDialog = () => {
    productDialog.value = false;
    submitted.value = false;
};

const refreshAllMyShips = () => {
    warshipAPI.getAllMyWarships(userStore.username).then(res => {
        warships.value = res.data;
        warships.value.forEach(warship => {
            warship.product_date = new Date(warship.product_date);
        });
    });
};

const refreshBalance = () => {
    getUser(userStore.username).then(res => {
        if (res.code == 200) {
            userStore.balance = res.data.balance;
        }
    });
};

const buyWarship = () => {
    submitted.value = true;
    if (warship.value.name && warship.value.name.trim()) {
        console.log(warship.value);
        warshipAPI.buyWarship(userStore.username, warship.value.name).then(res => {
            try {
                hideDialog();
                if (res.code == 200) {
                    toastStore.showSuccess('购买成功！');
                    refreshAllMyShips();
                    refreshBalance();
                } else throw new Error(res.msg);
            } catch (e) {
                toastStore.showError('购买出现错误！您似乎已经购买过该星舰或是余额不足。系统排斥信息：\n\n' + e);
            }

        });
    }
};


const updateAgent = (warshipName, agentName) => {
    updateAgentDialog.value = false;
    warshipAPI.updateWarshipAgent(warshipName, agentName).then(res => {
        try {
            if (res.code === 200) {
                toastStore.showSuccess(res.data);
                refreshAllMyShips();
            }
        } catch (e) {
            toastStore.showError(e);
        }
    });
};

const saleWarship = () => {
    warshipAPI.saleWarship(userStore.username, product.value.name)
        .then(res => {
            try {
                deleteProductDialog.value = false;
                refreshAllMyShips();
                refreshBalance();
                toastStore.showSuccess(res.data);
            } catch (e) {
                toastStore.showError('购买出现错误！您似乎未购买过该星舰。系统排斥信息：\n\n' + e);
            }
        });
};

const editProduct = (editProduct) => {
    product.value = { ...editProduct };
    productDialog.value = true;
};


const confirmDeleteProduct = (editProduct) => {
    product.value = editProduct;
    deleteProductDialog.value = true;
};

const deleteProduct = () => {
    products.value = products.value.filter((val) => val.id !== product.value.id);
    deleteProductDialog.value = false;
    product.value = {};
    toast.add({ severity: 'success', summary: 'Successful', detail: 'Product Deleted', life: 3000 });
};

const findIndexById = (id) => {
    let index = -1;
    for (let i = 0; i < products.value.length; i++) {
        if (products.value[i].id === id) {
            index = i;
            break;
        }
    }
    return index;
};

const createId = () => {
    let id = '';
    const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    for (let i = 0; i < 5; i++) {
        id += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    return id;
};

const exportCSV = () => {
    dt.value.exportCSV();
};

const confirmDeleteSelected = () => {
    deleteProductsDialog.value = true;
};
const deleteSelectedProducts = () => {
    products.value = products.value.filter((val) => !selectedProducts.value.includes(val));
    deleteProductsDialog.value = false;
    selectedProducts.value = null;
    toast.add({ severity: 'success', summary: 'Successful', detail: 'Products Deleted', life: 3000 });
};

const initFilters = () => {
    filters.value = {
        global: { value: null, matchMode: FilterMatchMode.CONTAINS }
    };
};

function getCountryCodeByName(countryName, lang = 'en') {
    const countryCode = countries.getAlpha2Code(countryName, lang);
    return countryCode ? countryCode : 'Unknown';
}

countries.registerLocale(enLocale);
</script>

<template>
    <div class="grid">
        <div class="col-12">
            <div class="card">
                <h5>当前余额：{{ userStore.balance }} $</h5>
                <Toolbar class="mb-4">
                    <template v-slot:start>
                        <div class="my-2">
                            <Button label="购买星舰" icon="pi pi-plus" class="mr-2" severity="success" @click="openNew" />
                            <Button label="卖出星舰" icon="pi pi-trash" severity="danger" @click="confirmDeleteSelected" :disabled="!selectedProducts || !selectedProducts.length" />
                        </div>
                    </template>
                    <template v-slot:end>
                        <!--                        <FileUpload mode="basic" accept="image/*" :maxFileSize="1000000" label="导入星舰数据" chooseLabel="导入武器数据" class="mr-2 inline-block" />-->
                        <Button label="导出星舰数据" icon="pi pi-upload" severity="help" @click="exportCSV($event)" />
                    </template>
                </Toolbar>

                <DataTable
                    ref="dt"
                    :value="warships"
                    v-model:selection="selectedProducts"
                    dataKey="id"
                    :paginator="true"
                    :rows="10"
                    :filters="filters"
                    paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                    :rowsPerPageOptions="[5, 10, 25]"
                    currentPageReportTemplate="正在展示第 {first} 到 {last} 艘星舰，共 {totalRecords} 艘星舰"
                >
                    <template #header>
                        <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
                            <h5 class="m-0">管理我的星舰</h5>
                            <IconField iconPosition="left" class="block mt-2 md:mt-0">
                                <InputIcon class="pi pi-search" />
                                <InputText class="w-full sm:w-auto" v-model="filters['global'].value" placeholder="Search..." />
                            </IconField>
                        </div>
                    </template>

                    <Column selectionMode="multiple" headerStyle="width: 3rem"></Column>
                    <Column field="code" header="当前代理" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">Code</span>
                            {{ slotProps.data.duty_officer }}
                        </template>
                    </Column>
                    <Column field="name" header="星舰" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">Name</span>
                            {{ slotProps.data.name }}
                        </template>
                    </Column>
                    <Column header="星舰图片" headerStyle="width:14%; min-width:10rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">Image</span>
                            <img src="@/assets/images/enterprise.png" :alt="slotProps.data.image" class="p-card p-w-12rem" width="100" />
                        </template>
                    </Column>
                    <Column field="valuation" header="当前估值" :sortable="true" headerStyle="width:14%; min-width:8rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">Price</span>
                            {{ formatCurrency(slotProps.data.valuation) }}
                        </template>
                    </Column>
                    <Column field="activity" header="所属类别" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">Category</span>
                            {{ slotProps.data.activity }}
                        </template>
                    </Column>
                    <Column field="product_country" header="生产国家" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                        <template #body="{ data }">
                            <div class="flex align-items-center gap-2">
                                <img alt="flag" src="/demo/images/flag/flag_placeholder.png" :class="`flag flag-${getCountryCodeByName(data.product_country).toLowerCase()}`" style="width: 24px" />
                                <span>{{ data.product_country }}</span>
                            </div>
                        </template>
                    </Column>
                    <Column field="status" header="星舰状态" :sortable="true" headerStyle="width:14%; min-width:10rem; ">
                        <template #body="slotProps">
                            <span class="p-column-title">Status</span>
                            <Tag :severity="getSeverity(slotProps.data.status)">{{ getStatus(slotProps.data.status) }}</Tag>
                        </template>
                    </Column>
                    <Column headerStyle="min-width:10rem;">
                        <template #body="slotProps">
                            <Button icon="pi pi-pencil" class="mr-2" severity="success" rounded @click="updateAgentDialog = true;product.name=slotProps.data.name" />
                            <Button icon="pi pi-trash" class="mt-2" severity="warning" rounded @click="confirmDeleteProduct(slotProps.data)" />
                        </template>
                    </Column>
                </DataTable>

                <Dialog v-model:visible="productDialog" :style="{ width: '450px' }" header="星舰购买面板" :modal="true" class="p-fluid">
                    <div class="field">
                        <label for="name">星舰名称</label>
                        <InputText id="name" v-model.trim="warship.name" required="true" autofocus :invalid="submitted && !warship.name" />
                        <small class="p-invalid" v-if="submitted && !warship.name">必须输入星舰名称</small>
                    </div>
                    <template #footer>
                        <Button label="取消购买" icon="pi pi-times" text="" @click="hideDialog" />
                        <Button label="购买" icon="pi pi-check" text="" @click="buyWarship" />
                    </template>
                </Dialog>

                <Dialog v-model:visible="deleteProductDialog" :style="{ width: '450px' }" header="确认" :modal="true">
                    <div class="flex align-items-center justify-content-center">
                        <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
                        <span v-if="product"
                        >确定要售出星舰 <b>{{ product.name }}</b
                        >?</span
                        >
                    </div>
                    <template #footer>
                        <Button label="取消" icon="pi pi-times" text @click="deleteProductDialog = false" />
                        <Button label="确定" icon="pi pi-check" text @click="saleWarship" />
                    </template>
                </Dialog>


                <Dialog v-model:visible="updateAgentDialog" :style="{ width: '450px' }" header="代理人修改面板" :modal="true" class="p-fluid">
                    <div class="field">
                        <label for="name">修改代理人</label>
                        <InputText id="name" v-model.trim="warship.name" required="true" autofocus :invalid="submitted && !warship.name" />
                        <small class="p-invalid" v-if="submitted && !warship.name">必须输入代理人名称</small>
                    </div>
                    <template #footer>
                        <Button label="取消修改" icon="pi pi-times" text="" @click="updateAgentDialog=false" />
                        <Button label="修改" icon="pi pi-check" text="" @click="updateAgent(product.name,warship.name)" />
                    </template>
                </Dialog>

                <Dialog v-model:visible="deleteProductsDialog" :style="{ width: '450px' }" header="确认" :modal="true">
                    <div class="flex align-items-center justify-content-center">
                        <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
                        <span v-if="product">Are you sure you want to delete the selected products?</span>
                    </div>
                    <template #footer>
                        <Button label="No" icon="pi pi-times" text @click="deleteProductsDialog = false" />
                        <Button label="Yes" icon="pi pi-check" text @click="deleteSelectedProducts" />
                    </template>
                </Dialog>
            </div>
        </div>
    </div>
</template>
