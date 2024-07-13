<script setup>
import { FilterMatchMode } from 'primevue/api';
import { ref, onMounted, onBeforeMount } from 'vue';
import { ProductService } from '@/service/ProductService';
import { useToast } from 'primevue/usetoast';
import { WeaponAPI } from '@/api/WeaponAPI';
import { useToastStore } from '@/store/toast';
import { useUserStore } from '@/store/user';
import { getUser } from '@/api/UserAPI';

const toastStore = useToastStore();
const userStore = useUserStore();
const toast = useToast();
const weaponAPI = new WeaponAPI();
const weapons = ref(null);
const products = ref(null);
const warship = ref(null);
const productDialog = ref(false);
const deleteProductDialog = ref(false);
const deleteProductsDialog = ref(false);
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
    switch (inventoryStatus.toLowerCase()) {
        case 'instock':
            return 'success';
        case 'lowstock':
            return 'warning';
        case 'outofstock':
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
    productService.getProducts().then((data) => (products.value = data));
    refreshAllWeapons();
});

const refreshAllWeapons = () => {
    weaponAPI.getAllWarshipWeapon(userStore.username).then(res => {
        weapons.value = res.data;
    });
};

const refreshBalance = () => {
    getUser(userStore.username).then(res => {
        if (res.code == 200) {
            userStore.balance = res.data.balance;
        }
    });
};

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

const saveProduct = () => {
    submitted.value = true;
    if (product.value.name && product.value.name.trim() && product.value.price) {
        if (product.value.id) {
            product.value.inventoryStatus = product.value.inventoryStatus.value ? product.value.inventoryStatus.value : product.value.inventoryStatus;
            products.value[findIndexById(product.value.id)] = product.value;
            toast.add({ severity: 'success', summary: 'Successful', detail: 'Product Updated', life: 3000 });
        } else {
            product.value.id = createId();
            product.value.code = createId();
            product.value.image = 'product-placeholder.svg';
            product.value.inventoryStatus = product.value.inventoryStatus ? product.value.inventoryStatus.value : 'INSTOCK';
            products.value.push(product.value);
            toast.add({ severity: 'success', summary: 'Successful', detail: 'Product Created', life: 3000 });
        }
        productDialog.value = false;
        product.value = {};
    }
};

const buyWeapon = (warship, weapon) => {
    weaponAPI.buyWeapon(userStore.username, warship, weapon).then(res => {
        hideDialog();
        try {
            if (res.code === 200) {
                toastStore.showSuccess('购买成功');
                refreshAllWeapons();
                refreshBalance();
            } else throw Error(res.msg);
        } catch (e) {
            toastStore.showError('余额不足！或是这艘战舰无法装配该种武器...\n\n' + e);
        }
    });
};

const saleWeapon = (warship, weapon) => {
    weaponAPI.saleWeapon(userStore.username, warship, weapon).then(res => {
        deleteProductDialog.value = false;
        try {
            if (res.code === 200) {
                toastStore.showSuccess('售卖成功');
                refreshAllWeapons();
                refreshBalance();
            } else throw Error(res.msg);
        } catch (e) {
            toastStore.showError('您似乎从未装配过这件武器...\n\n' + e);
        }
    });
};
const editProduct = (editProduct) => {
    product.value = { ...editProduct };
    productDialog.value = true;
};

const confirmDeleteProduct = (editProduct) => {
    product.value = editProduct;
    warship.value = editProduct.warship;
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

// const createId = () => {
//     let id = '';
//     const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
//     for (let i = 0; i < 5; i++) {
//         id += chars.charAt(Math.floor(Math.random() * chars.length));
//     }
//     return id;
// };

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
</script>

<template>
    <div class="grid">
        <div class="col-12">
            <div class="card">
                <h5>当前余额：{{ userStore.balance }} $</h5>
                <Toolbar class="mb-4">
                    <template v-slot:start>
                        <div class="my-2">
                            <Button label="购买武器" icon="pi pi-plus" class="mr-2" severity="success" @click="openNew" />
                            <Button label="卖出武器" icon="pi pi-trash" severity="danger" @click="confirmDeleteSelected" :disabled="!selectedProducts || !selectedProducts.length" />
                        </div>
                    </template>

                    <template v-slot:end>
                        <!--                        <FileUpload mode="basic" accept="image/*" :maxFileSize="1000000" label="导入武器数据" chooseLabel="Import" class="mr-2 inline-block" />-->
                        <Button label="导出武器数据表" icon="pi pi-upload" severity="help" @click="exportCSV($event)" />
                    </template>
                </Toolbar>

                <DataTable
                    ref="dt"
                    :value="weapons"
                    v-model:selection="selectedProducts"
                    dataKey="id"
                    :paginator="true"
                    :rows="10"
                    :filters="filters"
                    paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                    :rowsPerPageOptions="[5, 10, 25]"
                    currentPageReportTemplate="正在展示第 {first} 到 {last} 个舰载武器，共 {totalRecords} 个舰载武器"
                >
                    <template #header>
                        <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
                            <h5 class="m-0">管理我的武器</h5>
                            <IconField iconPosition="left" class="block mt-2 md:mt-0">
                                <InputIcon class="pi pi-search" />
                                <InputText class="w-full sm:w-auto" v-model="filters['global'].value" placeholder="Search..." />
                            </IconField>
                        </div>
                    </template>

                    <Column selectionMode="multiple" headerStyle="width: 3rem"></Column>
                    <Column field="code" header="所属星舰" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">Code</span>
                            {{ slotProps.data.warship }}
                        </template>
                    </Column>
                    <Column field="name" header="舰载武器" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">Name</span>
                            {{ slotProps.data.name }}
                        </template>
                    </Column>
                    <Column header="武器图片" headerStyle="width:14%; min-width:10rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">Image</span>
                            <img src="@/assets/images/DongFeng.png" :alt="slotProps.data.image" class="p-card p-w-12rem" width="100" />
                        </template>
                    </Column>
                    <Column field="price" header="当前估值" :sortable="true" headerStyle="width:14%; min-width:8rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">Price</span>
                            {{ formatCurrency(slotProps.data.price) }}
                        </template>
                    </Column>
                    <Column field="category" header="所属类别" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">Category</span>
                            {{ slotProps.data.category }}
                        </template>
                    </Column>
                    <Column field="reviews" header="船员评价" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">Rating</span>
                            <Rating :modelValue="slotProps.data.reviews" :readonly="true" :cancel="false" />
                        </template>
                    </Column>
                    <Column field="status" header="武器状态" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">Status</span>
                            <Tag :severity="getSeverity(slotProps.data.status)">{{ getStatus(slotProps.data.status) }}</Tag>
                        </template>
                    </Column>
                    <Column headerStyle="min-width:10rem;">
                        <template #body="slotProps">
                            <Button icon="pi pi-trash" class="mt-2" severity="warning" rounded @click="confirmDeleteProduct(slotProps.data)" />
                        </template>
                    </Column>
                </DataTable>

                <Dialog v-model:visible="productDialog" :style="{ width: '450px' }" header="购买详情" :modal="true" class="p-fluid">
                    <img :src="'/demo/images/product/' + product.image" :alt="product.image" v-if="product.image" width="150" class="mt-0 mx-auto mb-5 block shadow-2" />
                    <div class="field">
                        <label for="name">希望装配的星舰名称</label>
                        <InputText id="name" v-model.trim="warship" required="true" autofocus :invalid="submitted && !warship" />
                        <small class="p-invalid" v-if="submitted && !warship">必须输入星舰名称</small>
                    </div>
                    <div class="field">
                        <label for="name">欲购买舰载武器名称</label>
                        <InputText id="name" v-model.trim="product.name" required="true" autofocus :invalid="submitted && !product.name" />
                        <small class="p-invalid" v-if="submitted && !product.name">必须输入舰载武器名称</small>
                    </div>
                    <template #footer>
                        <Button label="取消" icon="pi pi-times" text="" @click="hideDialog" />
                        <Button label="购买" icon="pi pi-check" text="" @click="buyWeapon(warship,product.name)" />
                    </template>
                </Dialog>

                <Dialog v-model:visible="deleteProductDialog" :style="{ width: '450px' }" header="确认" :modal="true">
                    <div class="flex align-items-center justify-content-center">
                        <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
                        <span v-if="product"
                        >您确定要出售舰载武器 <b>{{ product.name }}</b
                        >?</span
                        >
                    </div>
                    <template #footer>
                        <Button label="不" icon="pi pi-times" text @click="deleteProductDialog = false" />
                        <Button label="是的" icon="pi pi-check" text @click="saleWeapon(warship,product.name)" />
                    </template>
                </Dialog>

                <Dialog v-model:visible="deleteProductsDialog" :style="{ width: '450px' }" header="Confirm" :modal="true">
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
